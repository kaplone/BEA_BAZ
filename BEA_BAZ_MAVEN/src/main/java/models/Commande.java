package models;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.ObservableList;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import utils.MongoAccess;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Commande  extends Commun{
	
	private String nom;
	
	private String remarques;
	
	//@JsonSerialize(using = LocalDateSerializer.class)
	//@JsonDeserialize(using = LocalDateDeserializer.class)
	private Date dateCommande;
	
	//@JsonSerialize(using = LocalDateSerializer.class)
	//@JsonDeserialize(using = LocalDateDeserializer.class)
	private Date dateDebutProjet;
    
	//@JsonSerialize(using = LocalDateSerializer.class)
	//@JsonDeserialize(using = LocalDateDeserializer.class)
	private Date dateFinProjet;

	private ObjectId client;
	
	private ArrayList<String> oeuvres;
	private ArrayList<Traitement> traitements_attendus;
	private ArrayList<Traitement> tous_les_traitements;
	
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

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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

	public ObjectId getClient() {
		return client;
	}

	public void setClient(ObjectId client) {
		this.client = client;
	}

	public ArrayList<String> getOeuvres() {
		return oeuvres;
	}

	public void setOeuvres(ArrayList<String> oeuvres) {
		this.oeuvres = oeuvres;
	}

	public ArrayList<Traitement> getTraitements_attendus() {
		return traitements_attendus;
	}

	public void setTraitements_attendus(ArrayList<Traitement> traitements_attendus2) {
		this.traitements_attendus = traitements_attendus2;
	}

	public ArrayList<Traitement> getTous_les_traitements() {
		return tous_les_traitements;
	}

	public void setTous_les_traitements(ArrayList<Traitement> tous_les_traitements) {
		this.tous_les_traitements = tous_les_traitements;
	}
	
	

}
