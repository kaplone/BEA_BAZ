package models;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import enums.Progression;
import utils.MongoAccess;
import javafx.collections.ObservableList;

public class OeuvreTraitee extends Oeuvre {
	
	private Oeuvre oeuvre;
	
	@JsonIgnore
	private ObservableList<TacheTraitement> traitementsEnCours;
	
	private ArrayList<TacheTraitement> traitementsAttendus;
	private ArrayList<TacheTraitement> traitementssupplementaires;
	
	private Commande commande;
	
	private Etat etat;
	
	private ArrayList<ObjectId> fichiers,
                                rapports;
	
	
	private Progression progressionOeuvreTraitee;


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
//	public String getDimensions(){
//		return oeuvre.getDimensions();
//	}
//	public String getInscriptions_au_verso(){
//		return oeuvre.getInscriptions_au_verso();
//	}
//	public String get_observations(){
//		return oeuvre.get_observations();
//	}
	
	
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
	public ObservableList<TacheTraitement> getTraitementsEnCours() {
		return traitementsEnCours;
	}
	public void setTraitementsEnCours(
			ObservableList<TacheTraitement> traitementsEnCours) {
		this.traitementsEnCours = traitementsEnCours;
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
	public ArrayList<TacheTraitement> getTraitementsAttendus() {
		return traitementsAttendus;
	}
	public void setTraitementsAttendus(
			ArrayList<TacheTraitement> traitementsAttendus) {
		this.traitementsAttendus = traitementsAttendus;
	}
	public ArrayList<TacheTraitement> getTraitementssupplementaires() {
		return traitementssupplementaires;
	}
	public void setTraitementssupplementaires(
			ArrayList<TacheTraitement> traitementssupplementaires) {
		this.traitementssupplementaires = traitementssupplementaires;
	}    
    
}
