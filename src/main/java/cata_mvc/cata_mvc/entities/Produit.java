package cata_mvc.cata_mvc.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
public class Produit implements Serializable {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Size(min=4, max=25)
  @NotBlank
  private String designation;

  @DecimalMin("100")
  private double prix;

  @DecimalMin("1")
  private int quantite;

  public Produit() {
    super();
  }

  public Produit(String designation, double prix, int quantite) {
    this.designation = designation;
    this.prix = prix;
    this.quantite = quantite;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDesignation() {
    return this.designation;
  }

  public void setDesignation(String designation) {
    this.designation = designation;
  }

  public double getPrix() {
    return this.prix;
  }

  public void setPrix(double prix) {
    this.prix = prix;
  }

  public int getQuantite() {
    return this.quantite;
  }

  public void setQuantite(int quantite) {
    this.quantite = quantite;
  }

  
}
