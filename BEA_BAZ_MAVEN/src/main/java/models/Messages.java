package models;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class Messages {
	
    private static Stage exportStage;
	
	private static String client;
	private static ObjectId client_id;
	private static Map<String, ObjectId> clients_id;
	
	private static String commande;
	private static ObjectId commande_id;
	private static Map<String, ObjectId> commandes_id;
	
	private static Oeuvre oeuvre;
	
	private static OeuvreTraitee oeuvreTraitee;
	private static ObservableList<OeuvreTraitee> observablOeuvresTraitees;
	private static Map<String, ObjectId> oeuvresTraitees_id;
	
	private static Traitement traitement;
	private static Map<String, ObjectId> traitements_id;

	private static TacheTraitement tacheTraitement;
	private static Map<String, ObjectId> tacheTraitements_id;
	
	private static List<TacheTraitement> traitementsAttendus;
	private static Map<String, ObjectId> traitementsAttendus_id;
	
	private static List<TacheTraitement> traitementsLies;
	private static Map<String, ObjectId> traitementslies_id;
	private static ObservableList<TacheTraitement> observableTacheTraitementsLiees;
	
	private static Produit produit;
	private static String nom_produit;
	private static Map<String, ObjectId> produits_id;
	
	private static Auteur auteur;
	private static String auteur_name;
	private static Map<String, ObjectId> auteurs_id;
	
	private static Map<String, ObjectId> techniques_id;
	
	private static Map<String, ObjectId> matieres_id;
	
	private static Model model;
	private static String model_name;
	private static Map<String, ObjectId> models_id;
	
	private static Fichier fichier;
	private static Map<String, ObjectId> fichiers_id;
	private static ObservableList<Fichier> observableFichiers;
	
	private static int oeuvre_index;
	
	public static Stage getStage(){
		return exportStage;
	}
	
	
	public static String getCommande() {
		return commande;
	}

	public static void setCommande(String commandeSelectionne) {
		Messages.commande = commandeSelectionne;
	}
	
	public static String getClient() {
		return client;
	}

	public static void setClient(String client) {
		Messages.client = client;
	}
	
	public static Traitement getTraitement() {
		return traitement;
	}

	public static void setTraitement(Traitement traitement) {
		Messages.traitement = traitement;
	}

	public static int getOeuvre_index() {
		return oeuvre_index;
	}

	public static void setOeuvre_index(int oeuvre_index) {
		Messages.oeuvre_index = oeuvre_index;
	}

	public static Auteur getAuteur() {
		return auteur;
	}

	public static void setAuteur(Auteur auteur) {
		Messages.auteur = auteur;
	}

	public static Model getModel() {
		return model;
	}

	public static void setModel(Model model) {
		Messages.model = model;
	}

	public static TacheTraitement getTacheTraitement() {
		return tacheTraitement;
	}

	public static void setTacheTraitement(TacheTraitement tacheTraitement) {
		Messages.tacheTraitement = tacheTraitement;
	}

	public static Fichier getFichier() {
		return fichier;
	}

	public static void setFichier(Fichier fichier) {
		Messages.fichier = fichier;
	}

	public static Stage getExportStage() {
		return exportStage;
	}


	public static void setExportStage(Stage exportStage) {
		Messages.exportStage = exportStage;
	}


	public static Produit getProduit() {
		return produit;
	}


	public static void setProduit(Produit produit) {
		Messages.produit = produit;
	}


	public static String getNom_produit() {
		return nom_produit;
	}


	public static void setNom_produit(String nom_produit) {
		Messages.nom_produit = nom_produit;
	}


	public static Map<String, ObjectId> getClients_id() {
		return clients_id;
	}


	public static void setClients_id(Map<String, ObjectId> clients_id) {
		Messages.clients_id = clients_id;
	}


	public static Map<String, ObjectId> getCommandes_id() {
		return commandes_id;
	}


	public static void setCommandes_id(Map<String, ObjectId> commandes_id) {
		Messages.commandes_id = commandes_id;
	}


	public static OeuvreTraitee getOeuvreTraitee() {
		return oeuvreTraitee;
	}


	public static void setOeuvreTraitee(OeuvreTraitee oeuvreTraitee) {
		Messages.oeuvreTraitee = oeuvreTraitee;
	}


	public static ObservableList<OeuvreTraitee> getObservablOeuvresTraitees() {
		return observablOeuvresTraitees;
	}


	public static void setObservablOeuvresTraitees(ObservableList<OeuvreTraitee> observablOeuvresTraitees) {
		Messages.observablOeuvresTraitees = observablOeuvresTraitees;
	}


	public static Map<String, ObjectId> getOeuvresTraitees_id() {
		return oeuvresTraitees_id;
	}


	public static void setOeuvresTraitees_id(Map<String, ObjectId> oeuvresTraitees_id) {
		Messages.oeuvresTraitees_id = oeuvresTraitees_id;
	}


	public static Map<String, ObjectId> getTraitements_id() {
		return traitements_id;
	}


	public static void setTraitements_id(Map<String, ObjectId> traitements_id) {
		Messages.traitements_id = traitements_id;
	}


	public static Map<String, ObjectId> getTacheTraitements_id() {
		return tacheTraitements_id;
	}


	public static void setTacheTraitements_id(Map<String, ObjectId> tacheTraitements_id) {
		Messages.tacheTraitements_id = tacheTraitements_id;
	}


	public static Map<String, ObjectId> getProduits_id() {
		return produits_id;
	}


	public static void setProduits_id(Map<String, ObjectId> produits_id) {
		Messages.produits_id = produits_id;
	}


	public static Map<String, ObjectId> getAuteurs_id() {
		return auteurs_id;
	}


	public static void setAuteurs_id(Map<String, ObjectId> auteurs_id) {
		Messages.auteurs_id = auteurs_id;
	}


	public static Map<String, ObjectId> getModels_id() {
		return models_id;
	}


	public static void setModels_id(Map<String, ObjectId> models_id) {
		Messages.models_id = models_id;
	}


	public static Map<String, ObjectId> getFichiers_id() {
		return fichiers_id;
	}


	public static void setFichiers_id(Map<String, ObjectId> fichiers_id) {
		Messages.fichiers_id = fichiers_id;
	}


	public static void setOeuvre(Oeuvre oeuvre) {
		Messages.oeuvre = oeuvre;
	}


	public static Map<String, ObjectId> getTraitementsAttendus_id() {
		return traitementsAttendus_id;
	}


	public static void setTraitementsAttendus_id(Map<String, ObjectId> traitementsAttendus_id) {
		Messages.traitementsAttendus_id = traitementsAttendus_id;
	}


	public static Oeuvre getOeuvre() {
		return oeuvre;
	}


	public static List<TacheTraitement> getTraitementsAttendus() {
		return traitementsAttendus;
	}


	public static void setTraitementsAttendus(List<TacheTraitement> traitementsAttendus) {
		Messages.traitementsAttendus = traitementsAttendus;
	}


	public static Map<String, ObjectId> getTechniques_id() {
		return techniques_id;
	}


	public static void setTechniques_id(Map<String, ObjectId> techniques_id) {
		Messages.techniques_id = techniques_id;
	}


	public static Map<String, ObjectId> getMatieres_id() {
		return matieres_id;
	}


	public static void setMatieres_id(Map<String, ObjectId> matieres_id) {
		Messages.matieres_id = matieres_id;
	}


	public static ObservableList<Fichier> getObservableFichiers() {
		return observableFichiers;
	}


	public static void setObservableFichiers(ObservableList<Fichier> observableFichiers) {
		Messages.observableFichiers = observableFichiers;
	}


	public static List<TacheTraitement> getTraitementsLies() {
		return traitementsLies;
	}


	public static void setTraitementsLies(List<TacheTraitement> traitementsLies) {
		Messages.traitementsLies = traitementsLies;
	}


	public static Map<String, ObjectId> getTraitementslies_id() {
		return traitementslies_id;
	}


	public static void setTraitementslies_id(Map<String, ObjectId> traitementslies_id) {
		Messages.traitementslies_id = traitementslies_id;
	}


	public static ObjectId getCommande_id() {
		return commande_id;
	}


	public static void setCommande_id(ObjectId commande_id) {
		Messages.commande_id = commande_id;
	}


	public static ObjectId getClient_id() {
		return client_id;
	}


	public static void setClient_id(ObjectId client_id) {
		Messages.client_id = client_id;
	}


	public static String getModel_name() {
		return model_name;
	}


	public static void setModel_name(String model_name) {
		Messages.model_name = model_name;
	}


	public static String getAuteur_name() {
		return auteur_name;
	}


	public static void setAuteur_name(String auteur_name) {
		Messages.auteur_name = auteur_name;
	}


	public static ObservableList<TacheTraitement> getObservableTacheTraitementsLiees() {
		return observableTacheTraitementsLiees;
	}


	public static void setObservableTacheTraitementsLiees(ObservableList<TacheTraitement> observableTacheTraitementsLiees) {
		Messages.observableTacheTraitementsLiees = observableTacheTraitementsLiees;
	}
    
}
