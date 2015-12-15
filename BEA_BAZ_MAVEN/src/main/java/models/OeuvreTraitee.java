package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import enums.EtatFinal;
import enums.Progression;
import utils.MongoAccess;
import utils.Normalize;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OeuvreTraitee extends Commun {
	
	private ObjectId commande_id;
	
	private ObjectId oeuvre_id;
	
	private Map<String, ObjectId> traitementsAttendus_id;

	private EtatFinal etat;
	private String complement_etat;
	
	private ArrayList<String> alterations;
	
	private ArrayList<Fichier> fichiers;
	private Map<String, ObjectId> fichiers_id;
	
	private Progression progressionOeuvreTraitee;
	
    private String observations;
    
    private String key1;
    private String key2;
    
    private String cote;
    
    public OeuvreTraitee(){
    	
    	traitementsAttendus_id = new HashMap<>();
    	alterations = new ArrayList<>();
    	fichiers = new ArrayList<>();
    	
    }

	public static void update(OeuvreTraitee c){
		MongoAccess.update("oeuvreTraitee", c);
	}
	
    public static void save(OeuvreTraitee c){		
		MongoAccess.save("oeuvreTraitee", c);	
	}

	public void setEtat(EtatFinal etat) {
		this.etat = etat;
	}
	public ArrayList<Fichier> getFichiers() {
		
		ArrayList<Fichier> fichiers = new ArrayList<>();
		
		for (String s : fichiers_id.keySet()) {
			fichiers.add(MongoAccess.request("fichier", fichiers_id.get(s)).as(Fichier.class).next());
		}
		
		return fichiers;
	}
	
	public Fichier getFichierAffiche () {
		
		Fichier fichier = null;
		
		for (String s : fichiers_id.keySet()) {
			
			if (Normalize.normalizeDenormStringField(s).endsWith(".PR.1.JPG")){
				fichier = MongoAccess.request("fichier", fichiers_id.get(s)).as(Fichier.class).next();
			}
		}
		
		return fichier;
		
	}
	
	public Map<String, ObjectId> getFichiers_id() {
		return fichiers_id;
	}
	public Set<String> getFichiers_names() {
		return fichiers_id.keySet();
	}
	public void setFichiers(ArrayList<Fichier> fichiers) {
		this.fichiers = fichiers;
	}

	public Progression getProgressionOeuvreTraitee() {
		return progressionOeuvreTraitee;
	}
	
	public void setProgressionOeuvreTraitee(Progression progressionOeuvreTraitee) {
		this.progressionOeuvreTraitee = progressionOeuvreTraitee;
	}
	public Set<String> getTraitementsAttendus_names() {
		return traitementsAttendus_id.keySet();
	}
	public Collection<ObjectId> getTraitementsAttendus_id() {
		return traitementsAttendus_id.values();
	}
	public void addTraitementAttendu(Traitement traitementAttendu) {
		this.traitementsAttendus_id.put(traitementAttendu.getNom(), traitementAttendu.get_id());
	}
	public void addTraitementAttendu(String nom, ObjectId id) {
		this.traitementsAttendus_id.put(nom, id);
	}

	public ImageView getIcone_progression() {
		
        Image image = new Image(progressionOeuvreTraitee.getUsedImage());
        
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
	public EtatFinal getEtat() {
		return etat;
	}
	public String getObservations() {
		return observations;
	}
	public void setObservations(String observations) {
		this.observations = observations;
	} 

	public String getComplement_etat() {
		return complement_etat;
	}
	public void setComplement_etat(String complement_etat) {
		this.complement_etat = complement_etat;
	}
	
	public Oeuvre getOeuvre(){
		
		return MongoAccess.request("oeuvre", oeuvre_id).as(Oeuvre.class).next();
	}

	public ObjectId getOeuvre_id() {
		return oeuvre_id;
	}

	public void setOeuvre_id(ObjectId oeuvre_id) {
		this.oeuvre_id = oeuvre_id;
	}

	public void setTraitementsAttendus_id(Map<String, ObjectId> traitementsAttendus_id) {
		this.traitementsAttendus_id = traitementsAttendus_id;
	}

	public void setFichiers_id(Map<String, ObjectId> fichiers_id) {
		this.fichiers_id = fichiers_id;
	}

	public ObjectId getCommande_id() {
		return commande_id;
	}

	public void setCommande_id(ObjectId commande_id) {
		this.commande_id = commande_id;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getCote() {
		return cote;
	}

	public void setCote(String cote) {
		this.cote = cote;
	}
	
	
	
}
