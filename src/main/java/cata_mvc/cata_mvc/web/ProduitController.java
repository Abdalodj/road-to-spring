package cata_mvc.cata_mvc.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cata_mvc.cata_mvc.dao.ProduitRepository;
import cata_mvc.cata_mvc.entities.Produit;

@Controller
public class ProduitController {
  @Autowired
  private ProduitRepository pRepo;

  @RequestMapping(value = "/index")
  public String index(
      Model model,
      @RequestParam(name = "page", defaultValue = "0") int p,
      @RequestParam(name = "size", defaultValue = "5") int s,
      @RequestParam(name = "mc", defaultValue = "") String mc) {
    Page<Produit> pageProd = pRepo.chercher("%" + mc + "%", PageRequest.of(p, s));

    model.addAttribute("listProd", pageProd.getContent());
    model.addAttribute("pages", pageProd.getTotalPages());
    model.addAttribute("active", ++p);
    model.addAttribute("size", s);
    model.addAttribute("mc", mc);

    return "produit";
  }

  @RequestMapping(value = "/delete", method = RequestMethod.GET)
  public String delete(
      Model model,
      @RequestParam(name = "id") Long id,
      @RequestParam(name = "page", defaultValue = "0") int p,
      @RequestParam(name = "size", defaultValue = "5") int s,
      @RequestParam(name = "mc", defaultValue = "") String mc) {
    pRepo.deleteById(id);
    return "redirect:/index?page=" + 0 + "&size=" + s + "&mc=" + mc;
  }

  @GetMapping({ "/form", "/form/{id}" })
  public String formProduit(Model model, @PathVariable(required = false) Long id) {
    if (id != null) {
      Produit produit = pRepo.getById(id);
      model.addAttribute("produit", produit);
      model.addAttribute("edit", "edit");
      return "FormProduit";
    }
    model.addAttribute("produit", new Produit());
    return "FormProduit";
  }

  /*
   * @RequestMapping(value = "/form", method = RequestMethod.GET)
   * public String formProduit(Model model, @RequestParam(name="id") Long id) {
   * <div th:each="item : ${items}">
   * <a th:href="@{'/pathvars/single/' + ${item.id}}">
   * <span th:text="${item.name}"></span>
   * </a>
   * </div>
   * if (id != null) {
   * Produit produit = pRepo.getById(id);
   * model.addAttribute("produit", produit);
   * return "FormProduit";
   * }
   * model.addAttribute("produit", new Produit());
   * return "FormProduit";
   * }
   * <form th:action="${edit} != null ? @{modify} : @{save}" method="post">
   */

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String save(
      Model model,
      @Valid Produit produit,
      BindingResult bResult) {
    if (bResult.hasErrors()) {
      return "FormProduit";
    }
    pRepo.save(produit);
    return "Confirmation";
  }
}
