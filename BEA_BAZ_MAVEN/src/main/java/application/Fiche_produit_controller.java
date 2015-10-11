package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.jongo.MongoCursor;

import utils.MongoAccess;
import models.Client;
import models.Commande;
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
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Fiche_produit_controller  implements Initializable{

	@FXML
	private ObservableList<Produit> liste_produits;
	
	@FXML
	private ListView<Traitement> listView_traitements;
	@FXML
	private ListView<Produit> listView_produits;
	@FXML
	private Button nouveau_produit;
	@FXML
	private Button nouveau_detail;
	@FXML
	private Button mise_a_jour_traitement;
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
	private Button versOeuvreButton;
	@FXML
	private Button versRapportButton;
	@FXML
	private Button versFichierButton;
	@FXML
	private Button versProduitsButton;
	@FXML
	private TextField file_path_textField;
	
	@FXML
	private Label fiche_traitement_label;
	@FXML
	private Label nom_traitement_label;
	@FXML
	private TextField nom_produit_textField;
	@FXML
	private TextField nom_complet_produit_textField;
	@FXML
	private TextArea remarques_produit_textArea;
	
	private boolean edit = false;
	
	private File file;

	MongoCursor<Produit> produitCursor ;
	Produit produitSelectionne;
	
	Stage currentStage;

	Produit detail;
	
	@FXML
	public void onVersCommandeButton(){
		
		Scene fiche_commande_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_commande.fxml"), 1275, 722);
		fiche_commande_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_commande_scene);	
	}
	
	@FXML
	public void onVersClientButton(){
		
		Scene fiche_client_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_client.fxml"), 1275, 722);
		fiche_client_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_client_scene);	
	}
	
    protected File chooseExport(){
		
		Stage newStage = new Stage();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Fichier à importer");
		fileChooser.getExtensionFilters().addAll(
		         new FileChooser.ExtensionFilter("feuille de calcul", "*.xlsx"));
		File selectedFile = fileChooser.showOpenDialog(newStage);
		if (selectedFile != null) {
			 return selectedFile;
		}
		else {
			 return (File) null;
		}
		
	}
	
	@FXML
	public void on_select_file_button(){
		
		file = chooseExport();
		file_path_textField.setText(file.toString());
	}
	@FXML
	public void on_import_file_button(){
		try {
			Documents.init();
			Documents.read(file, "produit");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    private void affichageInfos(Produit produitSelectionne){

    	
    	nom_produit_textField.setText(produitSelectionne.getNom());
    	remarques_produit_textArea.setText(produitSelectionne.getRemarques());
    	
    	liste_produits.clear();
    	
    	if (produitSelectionne != null){
    		
    		produitCursor = MongoAccess.request("produit").as(Produit.class);
    		
    		while (produitCursor.hasNext()){
    			Produit enplus = produitCursor.next();
    			liste_produits.add(enplus);
    		}	
    		listView_produits.setItems(liste_produits);		
    	}	
    }
    
    public void onNouveauProduitButton() {
    	
    	mise_a_jour_traitement.setText("Enregistrer");
    	nom_produit_textField.setText("");
    	remarques_produit_textArea.setText("");
    	nom_produit_textField.setPromptText("saisir le nom du nouveau traitement");
    	remarques_produit_textArea.setPromptText("éventuelles remarques");
    	nouveau_produit.setVisible(false);
    	
    	produitSelectionne = new Produit();
    	
    	edit = false;
    	annuler.setVisible(true);
    	editer.setVisible(false);
    	mise_a_jour_traitement.setVisible(true);
    	nom_produit_textField.setEditable(true);
		remarques_produit_textArea.setEditable(true);
    	
    	
    }
    
    public void onAnnulerButton() {
    	
    	mise_a_jour_traitement.setText("Mise à jour");
    	nom_produit_textField.setText("");
    	remarques_produit_textArea.setText("");
    	nom_produit_textField.setPromptText("");
    	remarques_produit_textArea.setPromptText("");
    	nouveau_produit.setText("Nouveau traitement");
    	rafraichirAffichage();
    	listView_produits.getSelectionModel().select(produitSelectionne);
    	affichageInfos(produitSelectionne);
    	
    }
    
    public void rafraichirAffichage(){

		liste_produits  = FXCollections.observableArrayList();
		
		
		
		produitCursor = MongoAccess.request("produit").as(Produit.class);
		
		while (produitCursor.hasNext()){
			liste_produits.add(produitCursor.next());
		}
		
		listView_produits.setItems(liste_produits);
    	
    }
    
    @FXML
    public void onEditerTraitementButton(){
    	

    	annuler.setVisible(true);
    	editer.setVisible(false);
    	mise_a_jour_traitement.setVisible(true);
    	nom_produit_textField.setEditable(true);
		remarques_produit_textArea.setEditable(true);
		
		edit = true;

	
    }
    
    @FXML
    public void onAnnulerEditButton(){
    	
    	annuler.setVisible(false);
    	editer.setVisible(true);
    	mise_a_jour_traitement.setVisible(false);
    	nom_produit_textField.setEditable(false);
		remarques_produit_textArea.setEditable(false);
		nouveau_produit.setVisible(true);
		rafraichirAffichage();
		listView_produits.getSelectionModel().select(produitSelectionne);
    	affichageInfos(produitSelectionne);
    	
    	edit = false;
    	
    }
    
    @FXML
    public void onMiseAJourTraitementButton(){

    	if (produitSelectionne == null) {
    		produitSelectionne = new Produit();
    	}
    	
    	produitSelectionne.setNom(nom_produit_textField.getText());
    	produitSelectionne.setRemarques(remarques_produit_textArea.getText());
    	
    	annuler.setVisible(false);
    	editer.setVisible(true);
    	mise_a_jour_traitement.setVisible(false);
    	nom_produit_textField.setEditable(false);
		remarques_produit_textArea.setEditable(false);
		
		if (edit) {
			Produit.update(produitSelectionne);
			afficherProduit();
			rafraichirAffichage();
			onAnnulerEditButton();
		}
		else {
			
			System.out.println(produitSelectionne);
			
		   Produit.save(produitSelectionne);
		   afficherProduit();
		   onAnnulerEditButton();
		}
    	
    }
    
    public void afficherProduit(){

		remarques_produit_textArea.setEditable(false);
		nom_complet_produit_textField.setEditable(false);
		nom_produit_textField.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_traitement.setVisible(false);
		annuler.setVisible(false);
		
		fiche_traitement_label.setText("FICHE PRODUIT :");
		nom_traitement_label.setText(produitSelectionne.getNom());
		nom_produit_textField.setText(produitSelectionne.getNom());
		nom_complet_produit_textField.setText(produitSelectionne.getNom_complet());
		remarques_produit_textArea.setText(produitSelectionne.getRemarques());
		//rafraichirAffichage();
    }
    
    @FXML
    public void onVerstraitementButton(){}
    @FXML
    public void onVersOeuvreButton(){}
    @FXML
    public void onAjoutDetail(){}
    @FXML
    public void onVersFichierButton(){}
    @FXML
    public void onVersTraitementButton(){}
    @FXML
    public void onVersModeleButton(){}
    @FXML
    public void onProduitSelect(){
    	
    	produitSelectionne = listView_produits.getSelectionModel().getSelectedItem();
    	Main_BEA_BAZ.setDetail(produitSelectionne);
    	afficherProduit();	
    }    	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		produitSelectionne = Main_BEA_BAZ.getDetail();

		utils.MongoAccess.connect();
		
		nom_produit_textField.setEditable(false);
		remarques_produit_textArea.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_traitement.setVisible(false);
		annuler.setVisible(false);
		
		versCommandeButton.setVisible(true);
		versTraitementButton.setVisible(true);
		versModeleButton.setVisible(false);
		versOeuvreButton.setVisible(false);
		versRapportButton.setVisible(false);
		versFichierButton.setVisible(false);
		versProduitsButton.setVisible(false);
		
		nom_traitement_label.setText("");

		liste_produits  = FXCollections.observableArrayList();
		
		currentStage = Main_BEA_BAZ.getStage();
		
        produitCursor = MongoAccess.request("produit").as(Produit.class);
		
		while (produitCursor.hasNext()){
			liste_produits.add(produitCursor.next());
		}
		
		listView_produits.setItems(liste_produits);

	}

}
