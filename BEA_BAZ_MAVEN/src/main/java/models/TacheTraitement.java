package models;

import java.util.ArrayList;
import java.util.Date;

import org.bson.types.ObjectId;

import utils.MongoAccess;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import enums.EtatFinal;
import enums.Progression;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TacheTraitement extends Commun{
	
	private Progression fait_;
	
//	@JsonIgnore
//	private ImageView icone_progression;
	
	private Date date;
	private ObjectId oeuvreTraiteeId;
	private ObjectId commandeId;
	private Produit produitUtilise;
	private Traitement traitement;
	private Etat etat;
	private String complement;
	
	private ArrayList<Produit> produits;
	private ArrayList<Produit> produitsLies;
    
    private boolean supp; 
    
    public TacheTraitement(){
    	produits = new ArrayList<>();
    	produitsLies = new ArrayList<>();
    }
    
    public static void update(TacheTraitement c){

		MongoAccess.update("tacheTraitement", c);
	}
	
    public static void save(TacheTraitement c){
		
		MongoAccess.save("tacheTraitement", c);
		
	}
    
    public void addProduit(Produit p){
    	
    	if (! produitsLies.contains(p)){
    		produitsLies.add(p);
    	}
    	
    }
    
    public void deleteProduit(Produit p){
    	
    	Produit produit_ = null;
    	
    	for (Produit p_ : produitsLies){
    		if (p.getNom().equals(p_.getNom())){
    			produit_ = p_;
    			produitsLies.remove(p_);
    			break;
    		}
    	} 	
    }

	public String getFait() {
		return fait_.toString();
		
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
	public String getComplement() {
		return complement;
	}
	public void setComplement(String complement) {
		this.complement = complement;
	}
    
	public ArrayList<Produit> getProduits() {
		return getTraitement().getProduits();
	}
	public Produit getProduitUtilise() {
		return this.produitUtilise;
	}
	public void setProduitUtilise(Produit produit) {
		this.produitUtilise = produit;
	}
	public Traitement getTraitement() {
		return traitement;
	}
	public void setTraitement(Traitement traitement) {
		this.traitement = traitement;
	}
	
	public ImageView getIcone_progression() {
		
		
        Image image = new Image(fait_.getUsedImage());
        
        ImageView usedImage = new ImageView();
        usedImage.setFitHeight(15);
        usedImage.setPreserveRatio(true);
        usedImage.setImage(image);
		
		return usedImage;
	}

	public boolean isSupp() {
		return supp;
	}

	public void setSupp(boolean supp) {
		this.supp = supp;
	}
	
	public String getNom_complet(){
		
		return traitement.getNom_complet();
	}
	
	public Progression getFait_(){
		return fait_;
	}
	public void setFait_(Progression p){
		fait_ = p;
	}

	public ArrayList<Produit> getProduitsLies() {
		return produitsLies;
	}

	public void setProduitsLies(ArrayList<Produit> produitsLies) {
		this.produitsLies = produitsLies;
	}

	public void setProduits(ArrayList<Produit> produits) {
		this.produits = produits;
	}
	
	
}
