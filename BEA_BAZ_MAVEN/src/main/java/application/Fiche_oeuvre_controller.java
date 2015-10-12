package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.jongo.MongoCursor;

import utils.MongoAccess;
import models.Auteur;
import models.Commande;
import models.Oeuvre;
import models.Produit;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Fiche_oeuvre_controller  implements Initializable{

	@FXML
	private ListView<Traitement> listView_traitements;
	@FXML
	private ListView<Produit> listView_produits;
	@FXML
	private ListView<Oeuvre> listView_oeuvres;

	@FXML
	private Button annuler;
	@FXML
	private Button editer;
	@FXML
	private Button versCommandeButton;
	@FXML
	private Button versTraitementButton;
	@FXML
	private Button versModeleButton;
	@FXML
	private Button versRapportButton;
	@FXML
	private Button versFichierButton;
	@FXML
	private Button mise_a_jour_traitement;
	
	@FXML
	private Label fiche_oeuvre_label;
	@FXML
	private Label nom_oeuvre_label;
	@FXML
	private TextArea remarques_oeuvre_textArea;
	
	@FXML
	private TextField numero_origine_textField;
	@FXML
	private TextField numero_archive_6s_textField;
	@FXML
	private TextField titre_textField;
	@FXML
	private TextField auteur_textField;
	@FXML
	private TextField date_oeuvre_textField;
	@FXML
	private TextField dimensions_textField;
	@FXML
	private TextField conditionnement_textField;
	@FXML
	private TextArea inscriptions_textArea;
	@FXML
	private TextArea observations_textArea;
	@FXML
	private GridPane grid;
	
	private boolean edit = false;
	
	MongoCursor<Oeuvre> oeuvreCursor;
	Oeuvre oeuvreSelectionne;
	MongoCursor<Traitement> traitementCursor ;
	ObservableList<Traitement> traitementsSelectionne;
	ObservableList<Traitement> liste_traitements;
	Traitement traitementSelectionne;
	
	Stage currentStage;
	
	@FXML
	public void onVersProduitsButton(){
		
		Scene fiche_produit_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_produit.fxml"), 1275, 722);
		fiche_produit_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_produit_scene);	
	}
	
	@FXML
	public void onVersCommandeButton(){
		
		Scene fiche_commande_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_commande.fxml"), 1275, 722);
		fiche_commande_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_commande_scene);	
	}	

	@FXML
	public void onTraitementSelect(){
		
		traitementSelectionne = listView_traitements.getSelectionModel().getSelectedItem();
		Main_BEA_BAZ.setTraitement(traitementSelectionne);
		//affichageInfos();	
	}
	
	@FXML
	public void onOeuvreSelect(){
		
		oeuvreSelectionne = listView_oeuvres.getSelectionModel().getSelectedItem();
		Main_BEA_BAZ.setOeuvre(oeuvreSelectionne);
	
	}	
	
//    private void affichageInfos(){
//
//    	
//    	nom_traitement_textField.setText(traitementSelectionne.getNom());
//    	nom_traitement_label.setText(traitementSelectionne.getNom());
//    	nom_complet_traitement_textField.setText(traitementSelectionne.getNom_complet());
//    	remarques_traitement_textArea.setText(traitementSelectionne.getRemarques());
//    	
//    	liste_details.clear();
//    	
//    	if (traitementSelectionne != null){
//    		
//            liste_details.addAll(traitementSelectionne.getProduits());
//			
//			listView_produits.setItems(liste_details);		
//    	}	
//    }

	@FXML
    public void onNouveauTraitementButton() {
//    	
//    	mise_a_jour_traitement.setText("Enregistrer");
//    	nom_traitement_textField.setText("");
//    	remarques_traitement_textArea.setText("");
//    	nom_traitement_textField.setPromptText("saisir le nom du nouveau traitement");
//    	remarques_traitement_textArea.setPromptText("éventuelles remarques");
//    	nouveau_traitement.setVisible(false);
//    	
//    	traitementSelectionne = new Traitement();
//    	
//    	edit = false;
//    	annuler.setVisible(true);
//    	editer.setVisible(false);
//    	mise_a_jour_traitement.setVisible(true);
//    	nom_traitement_textField.setEditable(true);
//		remarques_traitement_textArea.setEditable(true);
//    	
//    	
    }
//  
	@FXML
    public void onAnnulerButton() {
//    	
//    	mise_a_jour_traitement.setText("Mise à jour");
//    	nom_traitement_textField.setText("");
//    	remarques_traitement_textArea.setText("");
//    	nom_traitement_textField.setPromptText("");
//    	remarques_traitement_textArea.setPromptText("");
//    	nouveau_traitement.setText("Nouveau traitement");
//    	rafraichirAffichage();
//    	listView_traitements.getSelectionModel().select(traitementSelectionne);
//    	affichageInfos();
//    	
    }
//    
//    public void rafraichirAffichage(){
//    	
//    	liste_traitements = FXCollections.observableArrayList();
//		liste_details  = FXCollections.observableArrayList();
//		
//		
//		
//		traitementCursor = MongoAccess.request("traitement").as(Traitement.class);
//		
//		while (traitementCursor.hasNext()){
//			liste_traitements.add(traitementCursor.next());
//		}
//		
//		listView_traitements.setItems(liste_traitements);
//    	
//    }
//    
    @FXML
    public void onEditerTraitementButton(){
//    	
//
//    	annuler.setVisible(true);
//    	editer.setVisible(false);
//    	mise_a_jour_traitement.setVisible(true);
//    	nom_traitement_textField.setEditable(true);
//		remarques_traitement_textArea.setEditable(true);
//		
//		edit = true;
//
//	
    }
//    
    @FXML
    public void onAnnulerEditButton(){
//    	
//    	annuler.setVisible(false);
//    	editer.setVisible(true);
//    	mise_a_jour_traitement.setVisible(false);
//    	nom_traitement_textField.setEditable(false);
//		remarques_traitement_textArea.setEditable(false);
//		nouveau_traitement.setVisible(true);
//		rafraichirAffichage();
//		listView_traitements.getSelectionModel().select(traitementSelectionne);
//    	affichageInfos();
//    	
//    	edit = false;
//    	
    }
//    
    @FXML
    public void onMiseAJourTraitementButton(){
//
//    	if (traitementSelectionne == null) {
//    		traitementSelectionne = new Traitement();
//    	}
//    	
//    	traitementSelectionne.setNom(nom_traitement_textField.getText());
//    	traitementSelectionne.setRemarques(remarques_traitement_textArea.getText());
//    	
//    	annuler.setVisible(false);
//    	editer.setVisible(true);
//    	mise_a_jour_traitement.setVisible(false);
//    	nom_traitement_textField.setEditable(false);
//		remarques_traitement_textArea.setEditable(false);
//		
//		if (edit) {
//			Traitement.update(traitementSelectionne);
//			afficherTraitement();
//			rafraichirAffichage();
//			onAnnulerEditButton();
//		}
//		else {
//			
//			System.out.println(traitementSelectionne);
//			
//		   Traitement.save(traitementSelectionne);
//		   afficherTraitement();
//		   onAnnulerEditButton();
//		}
//    	
    }
//    
//    public void afficherTraitement(){
//
//		remarques_traitement_textArea.setEditable(false);
//        editer.setVisible(true);
//        mise_a_jour_traitement.setVisible(false);
//		annuler.setVisible(false);
//		fiche_traitement_label.setText("FICHE TRAITEMENT :");
//		nom_traitement_label.setText(traitementSelectionne.getNom());
//		nom_traitement_textField.setDisable(true);
//		remarques_traitement_textArea.setDisable(true);
//		nom_traitement_label.setText(traitementSelectionne.getNom());
//		rafraichirAffichage();
//    }
//    
//    public void afficherTraitements(){
//
//		remarques_traitement_textArea.setEditable(false);
//        editer.setVisible(true);
//        mise_a_jour_traitement.setVisible(false);
//		annuler.setVisible(false);
//		fiche_traitement_label.setText("FICHE TRAITEMENT :");
//		nom_traitement_textField.setDisable(true);
//		remarques_traitement_textArea.setDisable(true);
//		
//        liste_traitements.clear();
//    	
//    	if (traitementSelectionne != null){
//    		
//    		traitementCursor = MongoAccess.request("traitement").as(Traitement.class);
//    		
//    		while (traitementCursor.hasNext()){
//    			Traitement enplus = traitementCursor.next();
//    			liste_traitements.add(enplus);
//    		}	
//    		listView_traitements.setItems(liste_traitements);	
//    		
//    		rafraichirAffichage();
//    	}
//		
//		
//    }
    
    @FXML
    public void onVerstraitementButton(){}
    @FXML
    public void onVersOeuvreButton(){}
    @FXML
    public void onVersFichierButton(){}
    @FXML
    public void onVersTraitementButton(){}
    @FXML
    public void onVersModeleButton(){}
    @FXML
    public void onAjoutProduit(){
    	
    	Main_BEA_BAZ.setTraitementEdited(listView_traitements.getSelectionModel().getSelectedItem());
    	
    	Scene fiche_produit_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_produit.fxml"), 1275, 722);
		fiche_produit_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_produit_scene);
    }
    @FXML
    public void onVersClientButton(){
    	Scene fiche_client_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_client.fxml"), 1275, 722);
		fiche_client_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_client_scene);
    }

    	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Main_BEA_BAZ.setTraitementEdited(null);

		traitementSelectionne = Main_BEA_BAZ.getTraitement();
		oeuvreSelectionne = Main_BEA_BAZ.getOeuvre();

		utils.MongoAccess.connect();
		
		
		numero_origine_textField.setEditable(false);
		numero_archive_6s_textField.setEditable(false);
		titre_textField.setEditable(false);
		auteur_textField.setEditable(false);
		date_oeuvre_textField.setEditable(false);
		dimensions_textField.setEditable(false);
		conditionnement_textField.setEditable(false);
		inscriptions_textArea.setEditable(false);
		observations_textArea.setEditable(false);
		
		numero_origine_textField.setText(oeuvreSelectionne.getN_d_origine());
		numero_archive_6s_textField.setText(oeuvreSelectionne.getCote_archives_6s());
		titre_textField.setText(oeuvreSelectionne.getTitre_de_l_oeuvre());
		MongoCursor<Auteur> auteurCursor = MongoAccess.request("auteur", oeuvreSelectionne.getAuteur()).as(Auteur.class);
		Auteur auteur = auteurCursor.next();
		auteur_textField.setText(auteur.getNom());
		date_oeuvre_textField.setText(oeuvreSelectionne.getDate());
		dimensions_textField.setText(oeuvreSelectionne.getDimensions());
		conditionnement_textField.setText(oeuvreSelectionne.getFormat_de_conditionnement());
		inscriptions_textArea.setText(oeuvreSelectionne.getInscriptions_au_verso());
		observations_textArea.setText(oeuvreSelectionne.get_observations());

        editer.setVisible(true);
        mise_a_jour_traitement.setVisible(false);
		annuler.setVisible(false);
		
		versCommandeButton.setVisible(false);
		versTraitementButton.setVisible(false);
		versModeleButton.setVisible(false);
		versRapportButton.setVisible(false);
		versFichierButton.setVisible(false);
		
		
		liste_traitements = FXCollections.observableArrayList();

		
		currentStage = Main_BEA_BAZ.getStage();
		
		traitementCursor = MongoAccess.request("traitement").as(Traitement.class);
		
		while (traitementCursor.hasNext()){
			liste_traitements.add(traitementCursor.next());
		}
		
		listView_traitements.setItems(liste_traitements);

	}

}
