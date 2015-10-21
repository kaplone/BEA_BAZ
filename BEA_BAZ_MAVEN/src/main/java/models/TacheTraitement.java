package models;

import java.util.Date;

import org.bson.types.ObjectId;

import javafx.scene.image.ImageView;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import enums.EtatFinal;
import enums.Progression;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TacheTraitement extends Commun{
	
	private Progression fait;
	private Date date;
	private ObjectId oeuvreTraiteeId;
	private ObjectId commandeId;
	private Complement complement;
	private Produit produit;
	private Traitement traitement;
	private Etat etat;
    private Progression progressionTacheTraitement;
	
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
    
	public ObjectId getOeuvreTraiteeId() {
		return oeuvreTraiteeId;
	}
	public void setOeuvreTraiteeId(ObjectId oeuvreTraiteeId) {
		this.oeuvreTraiteeId = oeuvreTraiteeId;
	}
	
	public Etat getEtat() {
		return etat;
	}
	public void setEtat(Etat etat) {
		this.etat = etat;
	}
	public ObjectId getCommandeId() {
		return commandeId;
	}
	public void setCommandeId(ObjectId commandeId) {
		this.commandeId = commandeId;
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
	
	public Progression getProgressionTacheTraitement() {
		return progressionTacheTraitement;
	}
	public void setProgressionTacheTraitement(Progression progressionTacheTraitement) {
		this.progressionTacheTraitement = progressionTacheTraitement;
	}
	public ImageView getIcone_progression() {
		System.out.println(progressionTacheTraitement);
		
		return progressionTacheTraitement.getUsedImage();
	}
}
