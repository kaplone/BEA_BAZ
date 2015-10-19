package models;

import java.util.Date;

import javafx.scene.image.ImageView;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import enums.EtatFinal;
import enums.Progression;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TacheTraitement extends Commun{
	
	private Progression fait;
	private Date date;
	private Oeuvre oeuvre;
	private Commande commande;
	private Complement complement;
	private Produit produit;
	private Traitement traitement;
	private Etat etat;
    private String progression;
	
	@JsonIgnore
	private ImageView icone_progression;
	
    
	public Progression getFait() {
		return fait;
	}
	public void setFait(Progression fait) {
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
	public String getProgression() {
		return progression;
	}
	public void setProgression(String progression) {
		this.progression = progression;
	}
	public ImageView getIcone_progression() {
		System.out.println(progression);
		
		return Progression.valueOf(progression).getUsedImage();
	}
	public void setIcone_progression(ImageView etat) {
		this.icone_progression = etat;
	}
	
	
	
}
