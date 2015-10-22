package models;

import java.util.ArrayList;
import java.util.Date;

import org.bson.types.ObjectId;

import utils.MongoAccess;
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
	private ArrayList<Produit> produits;
	private Traitement traitement;
	private Etat etat;
    private Progression progressionTacheTraitement;
    
    private boolean supp; 
    
    public static void update(TacheTraitement c){

		MongoAccess.update("tacheTraitement", c);
	}
	
    public static void save(TacheTraitement c){
		
		MongoAccess.save("tacheTraitement", c);
		
	}
	
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
    
	public ArrayList<Produit> getProduits() {
		return produits;
	}
	public void setProduits(ArrayList<Produit> produits) {
		this.produits = produits;
	}
	public void setIcone_progression(ImageView icone_progression) {
		this.icone_progression = icone_progression;
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

	public boolean isSupp() {
		return supp;
	}

	public void setSupp(boolean supp) {
		this.supp = supp;
	}
	
}
