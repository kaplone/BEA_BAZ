package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bson.types.ObjectId;
import enums.EtatFinal;
import enums.Progression;
import utils.MongoAccess;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OeuvreTraitee extends Commun {
	
	private ObjectId oeuvre_id;
	
	private Map<String, ObjectId> traitementsAttendus_id;

	private EtatFinal etat;
	private String complement_etat;
	
	private ArrayList<String> alterations;
	
	private ArrayList<Fichier> fichiers;
	
	private Progression progressionOeuvreTraitee;
	
    private String observations;
    
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
		return fichiers;
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
	public void addTraitementAttendu(Traitement traitementAttendu) {
		this.traitementsAttendus_id.put(traitementAttendu.getNom(), traitementAttendu.get_id());
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
	
}
