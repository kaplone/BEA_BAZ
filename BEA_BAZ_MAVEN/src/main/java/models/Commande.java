package models;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;

import utils.MongoAccess;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Commande  extends Commun{
	
	private String nom_affichage;
	
	private String nom_complet;
	
	private String complement;
	
	private String remarques;

	private Date dateCommande;

	private Date dateDebutProjet;

	private Date dateFinProjet;
	
	private ObjectId modele_id;
	
	private ObjectId auteur_id;
	
	private Map<String, ObjectId> oeuvresTraitees_id;
	private Map<String, ObjectId> traitements_attendus_id;
	
	public Commande(){
		oeuvresTraitees_id = new HashMap<>();
		traitements_attendus_id = new HashMap<>();
	}
	
	public static void update(Commande c){

		MongoAccess.update("commande", c);
	}
	
    public static void save(Commande c){
		
		MongoAccess.save("commande", c);
		
	}
    
    public Commande get(){
		
		return this;
		
	}
    
    public String toString(){
    	
    	return this.getNom();
    }

	public String getRemarques() {
		return remarques;
	}

	public void setRemarques(String remarques) {
		this.remarques = remarques;
	}

	public LocalDate getDateCommande() {
		Instant instant = Instant.ofEpochMilli(dateCommande.getTime());
		LocalDate res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		return res;
	}

	public void setDateCommande(LocalDate dateCommande) {
		Instant instant = dateCommande.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		Date res = Date.from(instant);
		this.dateCommande = res;
	}

	public LocalDate getDateDebutProjet() {
		Instant instant = Instant.ofEpochMilli(dateDebutProjet.getTime());
		LocalDate res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		return res;
	}

	public void setDateDebutProjet(LocalDate dateDebutProjet) {
		Instant instant = dateDebutProjet.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		Date res = Date.from(instant);
		this.dateDebutProjet = res;
	}

	public LocalDate getDateFinProjet() {
		Instant instant = Instant.ofEpochMilli(dateFinProjet.getTime());
		LocalDate res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		return res;
	}

	public void setDateFinProjet(LocalDate dateFinProjet) {
		Instant instant = dateFinProjet.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		Date res = Date.from(instant);
		this.dateFinProjet = res;
	}

	public Set<String> getOeuvresTraitees_names() {
		return oeuvresTraitees_id.keySet();
	}

	public void addOeuvreTraitee(OeuvreTraitee oeuvreTraitee) {
		this.oeuvresTraitees_id.put(oeuvreTraitee.toString(), oeuvreTraitee.get_id());
	}

	public Set<String> getTraitements_attendus_names() {
		return traitements_attendus_id.keySet();
	}

	public void addTraitement_attendu_id(String traitement_attendu, ObjectId id) {
		this.traitements_attendus_id.put(traitement_attendu, id);
	}

	public String getNom_affichage() {
		return nom_affichage;
	}

	public void setNom_affichage(String nom_affichage) {
		this.nom_affichage = nom_affichage;
	}

	public String getNom_complet() {
		return nom_complet;
	}

	public void setNom_complet(String nom_complet) {
		this.nom_complet = nom_complet;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	public void setDateDebutProjet(Date dateDebutProjet) {
		this.dateDebutProjet = dateDebutProjet;
	}

	public void setDateFinProjet(Date dateFinProjet) {
		this.dateFinProjet = dateFinProjet;
	}

	public ObjectId getModele_id() {
		return modele_id;
	}

	public void setModele_id(ObjectId modele) {
		this.modele_id = modele;
	}

	public ObjectId getAuteur_id() {
		return auteur_id;
	}

	public void setAuteur_id(ObjectId auteur) {
		this.auteur_id = auteur;
	}
	
	public Model getModel(){
		return MongoAccess.request("model", modele_id).as(Model.class).next();
	}
	
	public Auteur getAuteur(){
		return MongoAccess.request("auteur", auteur_id).as(Auteur.class).next();
	}

	public Map<String, ObjectId> getOeuvresTraitees_id() {
		return oeuvresTraitees_id;
	}

	public void setOeuvresTraitees_id(Map<String, ObjectId> oeuvresTraitees_id) {
		this.oeuvresTraitees_id = oeuvresTraitees_id;
	}

	public Map<String, ObjectId> getTraitements_attendus_id() {
		return traitements_attendus_id;
	}

	public void setTraitements_attendus_id(Map<String, ObjectId> traitements_attendus_id) {
		this.traitements_attendus_id = traitements_attendus_id;
	}
    
}
