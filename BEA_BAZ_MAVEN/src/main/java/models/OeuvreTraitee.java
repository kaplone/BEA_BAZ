package models;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import enums.Progression;
import utils.MongoAccess;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OeuvreTraitee extends Oeuvre {
	
	private Oeuvre oeuvre;
	
	private ArrayList<ObjectId> traitementsAttendus;
	private ArrayList<ObjectId> traitementsSupplementaires;
	
	private Commande commande;
	
	private Etat etat;
	
	private ArrayList<String> alterations = new ArrayList<>();
	
	private ArrayList<ObjectId> fichiers,
                                rapports;
	
	
	private Progression progressionOeuvreTraitee;
//	@JsonIgnore
//	private ImageView icone_progression;
	


	@Override
	public String getNom(){
		return oeuvre.getNom();
	}
	public String getCote_archives_6s(){
		return oeuvre.getCote_archives_6s();
	}
	public String getTitre_de_l_oeuvre(){
		return oeuvre.getTitre_de_l_oeuvre();
	}
	public ObjectId getAuteur(){
		return oeuvre.getAuteur();
	}
	public String toString(){
		return oeuvre.getCote_archives_6s();
	}
	
	public static void update(OeuvreTraitee c){

		MongoAccess.update("oeuvreTraitee", c);
	}
	
    public static void save(OeuvreTraitee c){
		
		MongoAccess.save("oeuvreTraitee", c);
		
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
//	public Etat getEtat() {
//		return etat;
//	}
	public void setEtat(Etat etat) {
		this.etat = etat;
	}
	public ArrayList<ObjectId> getFichiers() {
		return fichiers;
	}
	public void setFichiers(ArrayList<ObjectId> fichiers) {
		this.fichiers = fichiers;
	}
	public ArrayList<ObjectId> getRapports() {
		return rapports;
	}
	public void setRapports(ArrayList<ObjectId> rapports) {
		this.rapports = rapports;
	}
	public Progression getProgressionOeuvreTraitee() {
		return progressionOeuvreTraitee;
	}
	
	public void setProgressionOeuvreTraitee(Progression progressionOeuvreTraitee) {
		this.progressionOeuvreTraitee = progressionOeuvreTraitee;
	}
	public ArrayList<ObjectId> getTraitementsAttendus() {
		return traitementsAttendus;
	}
	public void setTraitementsAttendus(
			ArrayList<ObjectId> traitementsAttendus) {
		this.traitementsAttendus = traitementsAttendus;
	}
	public ArrayList<ObjectId> getTraitementsSupplementaires() {
		return traitementsSupplementaires;
	}
	public void setTraitementsSupplementaires(
			ArrayList<ObjectId> traitementssupplementaires) {
		this.traitementsSupplementaires = traitementssupplementaires;
	}
	public ImageView getIcone_progression() {
		
        Image image = new Image(progressionOeuvreTraitee.getUsedImage().toURI().toString());
        
        ImageView usedImage = new ImageView();
        usedImage.setFitHeight(15);
        usedImage.setPreserveRatio(true);
        usedImage.setImage(image);
		
		return usedImage;
	}
	public ArrayList<String> getAlterations() {
		return alterations;
	}
	public void setAlterations(ArrayList<String> alterations) {
		this.alterations = alterations;
	}
	public Etat getEtat() {
		return etat;
	} 
	
}
