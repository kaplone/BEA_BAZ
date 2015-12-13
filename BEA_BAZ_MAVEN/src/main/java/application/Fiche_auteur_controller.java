package application;

import java.net.URL;
import java.util.ResourceBundle;

import org.jongo.MongoCursor;

import utils.MongoAccess;
import models.Auteur;
import models.Client;
import models.Commande;
import models.Messages;
import models.Traitement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Fiche_auteur_controller  implements Initializable{
	
	@FXML
	private ObservableList<Auteur> liste_auteurs;
	@FXML
	private ListView<Auteur> listView_auteur;

	@FXML
	private TextField nom_auteur_textField;
	@FXML
	private TextArea remarques_auteur_textArea;
	@FXML
	private Button nouveau_auteur;
	@FXML
	private Button mise_a_jour_auteur;
	@FXML
	private Button annuler;
	@FXML
	private Button editer;
	@FXML
	private Button versClientButton;
	@FXML
	private Button versCommandeButton;
	@FXML
	private Button versOeuvreButton;
	@FXML
	private Button versRapportButton;
	@FXML
	private Button versTraitementsButton;
	@FXML
	private Button versProduitsButton;
	@FXML
	private Button versModelesButton;
	@FXML
	private Button versFichiersButton;
	@FXML
	private Button versAuteursButton;
	@FXML
	private TextField nom_complet_auteur_textField;
	
	MongoCursor<Auteur> auteurCursor;
	Auteur auteurSelectionne;
	
	Stage currentStage;

	Auteur auteur;
	
	private boolean edit = false;
	
	@FXML
	public void onVersCommandeButton(){
		
		Scene fiche_commande_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_commande.fxml"), 1275, 722);
		fiche_commande_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_commande_scene);
		
	}
	
	@FXML
	public void onVersProduitsButton(){
		
		Scene fiche_produit_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_produit.fxml"), 1275, 722);
		fiche_produit_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_produit_scene);	
	}
	@FXML
    public void onVersFichiersButton(){}
    @FXML
    public void onVersTraitementsButton(){
    	Scene fiche_traitement_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_traitement.fxml"), 1275, 722);
		fiche_traitement_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_traitement_scene);
    }
    @FXML
    public void onVersModelesButton(){
    	Scene fiche_modele_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_model.fxml"), 1275, 722);
		fiche_modele_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_modele_scene);
    }
        

	@FXML
	public void onVersClientButton(){
		
		Scene fiche_client_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_client.fxml"), 1275, 722);
		fiche_client_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_client_scene);
		
	}
	@FXML
    public void onVersAuteursButton(){
    	Scene fiche_auteur_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_auteur.fxml"), 1275, 722);
		fiche_auteur_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_auteur_scene);
    }
	@FXML
    public void onMatieres_button(){
    	Scene fiche_matiere_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_matiere.fxml"), 1275, 722);
		fiche_matiere_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_matiere_scene);
    }
    @FXML
    public void onTechniques_button(){
    	Scene fiche_technique_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_technique.fxml"), 1275, 722);
		fiche_technique_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_technique_scene);
    }
	
	@FXML
	public void onAuteurSelect(){
		
		System.out.println(listView_auteur.getSelectionModel().getSelectedItem());
		
		if (listView_auteur.getSelectionModel().getSelectedItem() == null){
            
			System.out.println("-");
			editer.setVisible(false);	
		}
		else {
			
			if (listView_auteur.getSelectionModel().getSelectedItem().getNom() != null){

				auteurSelectionne = listView_auteur.getSelectionModel().getSelectedItem();
				Messages.setAuteur(auteurSelectionne);		
				affichageInfos(auteurSelectionne);
				editer.setVisible(true);
    		}
    		else {
    			editer.setVisible(false);
    		}
			
		}
		
		
	}
	
    private void affichageInfos(Auteur auteurSelectionne){
        
    	if (listView_auteur.getSelectionModel().getSelectedItem() != null){
    		nom_auteur_textField.setText(auteurSelectionne.getNom());
    		nom_complet_auteur_textField.setText(auteurSelectionne.getNom_complet());
        	remarques_auteur_textArea.setText(auteurSelectionne.getRemarques());
    	}	
    	else {
    		nom_auteur_textField.setText("");
    		nom_complet_auteur_textField.setText("");
        	remarques_auteur_textArea.setText("");
    	}
    }
    
    public void onNouveauAuteurButton() {
    	
    	mise_a_jour_auteur.setText("Enregistrer");
    	nom_auteur_textField.setText("");
    	nom_complet_auteur_textField.setText("");
    	remarques_auteur_textArea.setText("");
    	nom_auteur_textField.setPromptText("saisir le nom affiché du nouvel auteur");
    	nom_complet_auteur_textField.setPromptText("saisir le nom complet du nouvel auteur");
    	remarques_auteur_textArea.setPromptText("éventuelles remarques");
    	nouveau_auteur.setVisible(false);
    	
    	auteurSelectionne = new Auteur();
    	
    	edit = false;
    	annuler.setVisible(true);
    	editer.setVisible(false);
    	mise_a_jour_auteur.setVisible(true);
    	nom_auteur_textField.setEditable(true);
    	nom_complet_auteur_textField.setEditable(true);
		remarques_auteur_textArea.setEditable(true);

    }
    
    public void onAnnulerButton() {
    	
    	mise_a_jour_auteur.setText("Mise à jour");
    	nom_auteur_textField.setText("");
    	remarques_auteur_textArea.setText("");
    	nom_complet_auteur_textField.setText("");
    	nom_auteur_textField.setPromptText("");
    	nom_complet_auteur_textField.setPromptText("");
    	remarques_auteur_textArea.setPromptText("");
    	nouveau_auteur.setText("Nouvel auteur");
    	nom_auteur_textField.setEditable(false);
    	nom_complet_auteur_textField.setEditable(false);
    	remarques_auteur_textArea.setEditable(false);
    	if (listView_auteur.getSelectionModel().getSelectedItem() == null){
    		editer.setVisible(false);
    	}
    	else {
    		if (listView_auteur.getSelectionModel().getSelectedItem().getNom() == null){
    			editer.setVisible(true);
    		}
    		else {
    			editer.setVisible(false);
    		}
    		
    	}
    	rafraichirAffichage();
    	listView_auteur.getSelectionModel().select(auteurSelectionne);
    	affichageInfos(auteurSelectionne);
    	
    }
    
    public void rafraichirAffichage(){
    	
    	liste_auteurs = FXCollections.observableArrayList();

		auteurCursor = MongoAccess.request("auteur").as(Auteur.class);
		
		while (auteurCursor.hasNext()){
			liste_auteurs.add(auteurCursor.next());
		}
		
		listView_auteur.setItems(liste_auteurs);
    	
    }
    
    @FXML
    public void onEditerAuteurButton(){
    	

    	annuler.setVisible(true);
    	editer.setVisible(false);
    	mise_a_jour_auteur.setVisible(true);
    	nom_auteur_textField.setEditable(true);
    	nom_complet_auteur_textField.setEditable(true);
		remarques_auteur_textArea.setEditable(true);
		
		edit = true;

	
    }
    
    @FXML
    public void onAnnulerEditButton(){
    	
    	annuler.setVisible(false);
    	if (listView_auteur.getSelectionModel().getSelectedItem() == null){
    		editer.setVisible(false);
    	}
    	else {
    		if (listView_auteur.getSelectionModel().getSelectedItem().getNom() == null){
    			editer.setVisible(true);
    		}
    		else {
    			editer.setVisible(false);
    		}
    		
    	}
    	mise_a_jour_auteur.setVisible(false);
    	nom_auteur_textField.setEditable(false);
    	nom_complet_auteur_textField.setEditable(false);
		remarques_auteur_textArea.setEditable(false);
		nouveau_auteur.setVisible(true);
		
		nom_auteur_textField.setText("");
    	remarques_auteur_textArea.setText("");
    	nom_complet_auteur_textField.setText("");
    	nom_auteur_textField.setPromptText("");
    	nom_complet_auteur_textField.setPromptText("");
    	remarques_auteur_textArea.setPromptText("");
    	
    	edit = false;
    	
    }
    
    @FXML
    public void onMiseAJourAuteurButton(){
    
    	Messages.setAuteurs_id(null);
    	
    	if (auteurSelectionne == null){
    		auteurSelectionne = new Auteur();
    	}
    	
    	auteurSelectionne.setNom(nom_auteur_textField.getText());
    	auteurSelectionne.setNom_complet(nom_complet_auteur_textField.getText());
    	auteurSelectionne.setRemarques(remarques_auteur_textArea.getText());
    	
    	annuler.setVisible(false);
    	if (listView_auteur.getSelectionModel().getSelectedItem() == null){
    		editer.setVisible(false);
    	}
    	else {
    		if (listView_auteur.getSelectionModel().getSelectedItem().getNom() == null){
    			editer.setVisible(true);
    		}
    		else {
    			editer.setVisible(false);
    		}
    		
    	}
    	mise_a_jour_auteur.setVisible(false);
    	nom_auteur_textField.setEditable(false);
    	nom_complet_auteur_textField.setEditable(false);
		remarques_auteur_textArea.setEditable(false);
		
		if (edit) {
			Auteur.update(auteurSelectionne);
			//afficherClient();
			rafraichirAffichage();
			onAnnulerEditButton();
		}
		else {
			
			System.out.println(auteurSelectionne);
			
		   Auteur.save(auteurSelectionne);
		   //afficherClient();
		   rafraichirAffichage();
		   onAnnulerEditButton();
		}
    	
    }
    
    	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		auteur = Messages.getAuteur();

		nom_auteur_textField.setEditable(false);
		nom_complet_auteur_textField.setEditable(false);
		remarques_auteur_textArea.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_auteur.setVisible(false);
		annuler.setVisible(false);
		
		versClientButton.setVisible(true);
		versOeuvreButton.setVisible(false);
		versRapportButton.setVisible(false);
		
		versAuteursButton.setVisible(false);
		editer.setVisible(false);
		
		if (Messages.getCommande() != null){
			versCommandeButton.setVisible(true);
		}
		else {
			versCommandeButton.setVisible(false);
		}
		
		liste_auteurs = FXCollections.observableArrayList();
		
		currentStage = Messages.getStage();
		
		auteurCursor = MongoAccess.request("auteur").as(Auteur.class);
		
		while (auteurCursor.hasNext()){
			liste_auteurs.add(auteurCursor.next());
		}
		
		listView_auteur.setItems(liste_auteurs);

	}

}
