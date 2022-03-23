package cata_mvc.cata_mvc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    Page<Produit> pageProd = pRepo.chercher("%"+mc+"%", PageRequest.of(p, s));

    model.addAttribute("listProd", pageProd.getContent());
    model.addAttribute("pages", pageProd.getTotalPages());
    model.addAttribute("active", ++p); 
    model.addAttribute("size", s);
    model.addAttribute("mc", mc);

    return "produit";
  }

  @RequestMapping(value = "/delete", method=RequestMethod.GET)
  public String delete(
    Model model, 
    @RequestParam(name = "id") Long id,
    @RequestParam(name = "page", defaultValue = "0") int p,
    @RequestParam(name = "size", defaultValue = "5") int s,
    @RequestParam(name = "mc", defaultValue = "") String mc) {
    pRepo.deleteById(id);
    return "redirect:/index?page="+p+"&size="+s+"&mc="+mc;
  }
}
