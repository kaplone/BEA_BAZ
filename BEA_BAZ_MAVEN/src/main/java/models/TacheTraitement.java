package models;

import java.util.Date;

import javafx.scene.image.ImageView;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import enums.Etats;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TacheTraitement extends Commun{
	
	private Etats fait;
	private Date date;
	private Oeuvre oeuvre;
	private Commande commande;
	private Complement complement;
	private Produit produit;
	private Traitement traitement;
	
    private String etat_current;
	
	@JsonIgnore
	private ImageView etat;
	
    
	public Etats getFait() {
		return fait;
	}
	public void setFait(Etats fait) {
		this.fait = fait;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Oeuvre getOeuvre() {
		return oeuvre;
	}
	public void setOeuvre(Oeuvre oeuvre) {
		this.oeuvre = oeuvre;
	}
	public Commande getCommande() {
		return commande;
	}
	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	public Complement getComplement() {
		return complement;
	}
	public void setComplement(Complement complement) {
		this.complement = complement;
	}
	public Produit getProduit() {
		return produit;
	}
	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	public Traitement getTraitement() {
		return traitement;
	}
	public void setTraitement(Traitement traitement) {
		this.traitement = traitement;
	}
	public String getEtat_current() {
		return etat_current;
	}
	public void setEtat_current(String etat_current) {
		this.etat_current = etat_current;
	}
	public ImageView getEtat() {
		System.out.println(etat_current);
		
		return Etats.valueOf(etat_current).getUsedImage();
	}
	public void setEtat(ImageView etat) {
		this.etat = etat;
	}
	
	
	
}
