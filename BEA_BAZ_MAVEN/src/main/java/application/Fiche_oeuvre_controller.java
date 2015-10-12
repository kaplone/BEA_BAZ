package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.jongo.MongoCursor;

import utils.MongoAccess;
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

public class Fiche_oeuvre_controller  implements Initializable{
	
	@FXML
	private ObservableList<Traitement> liste_traitements;
	@FXML
	private ObservableList<Produit> liste_details;
	@FXML
	private TextField file_path_textField;
	@FXML
	private ListView<Traitement> listView_traitements;
	@FXML
	private ListView<Produit> listView_produits;
	@FXML
	private Button nouveau_traitement;
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
	private Label fiche_traitement_label;
	@FXML
	private Label nom_traitement_label;
	@FXML
	private TextField nom_traitement_textField;
	@FXML
	private TextField nom_complet_traitement_textField;
	@FXML
	private TextArea remarques_traitement_textArea;
	
	private boolean edit = false;
	
	MongoCursor<Traitement> traitementCursor;
	MongoCursor<Produit> detailCursor ;
	Traitement traitementSelectionne;
	Produit detailSelectionne;
	
	Stage currentStage;

	Produit detail;
	
	private File file;
	
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
	public void onAjoutTraitement(){
	}

	@FXML
	public void onTraitementSelect(){
		
		traitementSelectionne = listView_traitements.getSelectionModel().getSelectedItem();
		Main_BEA_BAZ.setTraitement(traitementSelectionne);
		affichageInfos();	
	}
	
	@FXML
	public void onDetailSelect(){
		
		detailSelectionne = listView_produits.getSelectionModel().getSelectedItem();
		
		if (detailSelectionne != null){
			Main_BEA_BAZ.setDetail(detailSelectionne);
			
			Scene fiche_detail_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_detail.fxml"), 1275, 722);
			fiche_detail_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			currentStage.setScene(fiche_detail_scene);	
		}
		
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
			Documents.read(file, "traitement");
			afficherTraitements();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
    private void affichageInfos(){

    	
    	nom_traitement_textField.setText(traitementSelectionne.getNom());
    	nom_traitement_label.setText(traitementSelectionne.getNom());
    	nom_complet_traitement_textField.setText(traitementSelectionne.getNom_complet());
    	remarques_traitement_textArea.setText(traitementSelectionne.getRemarques());
    	
    	liste_details.clear();
    	
    	if (traitementSelectionne != null){
    		
            liste_details.addAll(traitementSelectionne.getProduits());
			
			listView_produits.setItems(liste_details);		
    	}	
    }
    
    public void onNouveauTraitementButton() {
    	
    	mise_a_jour_traitement.setText("Enregistrer");
    	nom_traitement_textField.setText("");
    	remarques_traitement_textArea.setText("");
    	nom_traitement_textField.setPromptText("saisir le nom du nouveau traitement");
    	remarques_traitement_textArea.setPromptText("éventuelles remarques");
    	nouveau_traitement.setVisible(false);
    	
    	traitementSelectionne = new Traitement();
    	
    	edit = false;
    	annuler.setVisible(true);
    	editer.setVisible(false);
    	mise_a_jour_traitement.setVisible(true);
    	nom_traitement_textField.setEditable(true);
		remarques_traitement_textArea.setEditable(true);
    	
    	
    }
    
    public void onAnnulerButton() {
    	
    	mise_a_jour_traitement.setText("Mise à jour");
    	nom_traitement_textField.setText("");
    	remarques_traitement_textArea.setText("");
    	nom_traitement_textField.setPromptText("");
    	remarques_traitement_textArea.setPromptText("");
    	nouveau_traitement.setText("Nouveau traitement");
    	rafraichirAffichage();
    	listView_traitements.getSelectionModel().select(traitementSelectionne);
    	affichageInfos();
    	
    }
    
    public void rafraichirAffichage(){
    	
    	liste_traitements = FXCollections.observableArrayList();
		liste_details  = FXCollections.observableArrayList();
		
		
		
		traitementCursor = MongoAccess.request("traitement").as(Traitement.class);
		
		while (traitementCursor.hasNext()){
			liste_traitements.add(traitementCursor.next());
		}
		
		listView_traitements.setItems(liste_traitements);
    	
    }
    
    @FXML
    public void onEditerTraitementButton(){
    	

    	annuler.setVisible(true);
    	editer.setVisible(false);
    	mise_a_jour_traitement.setVisible(true);
    	nom_traitement_textField.setEditable(true);
		remarques_traitement_textArea.setEditable(true);
		
		edit = true;

	
    }
    
    @FXML
    public void onAnnulerEditButton(){
    	
    	annuler.setVisible(false);
    	editer.setVisible(true);
    	mise_a_jour_traitement.setVisible(false);
    	nom_traitement_textField.setEditable(false);
		remarques_traitement_textArea.setEditable(false);
		nouveau_traitement.setVisible(true);
		rafraichirAffichage();
		listView_traitements.getSelectionModel().select(traitementSelectionne);
    	affichageInfos();
    	
    	edit = false;
    	
    }
    
    @FXML
    public void onMiseAJourTraitementButton(){

    	if (traitementSelectionne == null) {
    		traitementSelectionne = new Traitement();
    	}
    	
    	traitementSelectionne.setNom(nom_traitement_textField.getText());
    	traitementSelectionne.setRemarques(remarques_traitement_textArea.getText());
    	
    	annuler.setVisible(false);
    	editer.setVisible(true);
    	mise_a_jour_traitement.setVisible(false);
    	nom_traitement_textField.setEditable(false);
		remarques_traitement_textArea.setEditable(false);
		
		if (edit) {
			Traitement.update(traitementSelectionne);
			afficherTraitement();
			rafraichirAffichage();
			onAnnulerEditButton();
		}
		else {
			
			System.out.println(traitementSelectionne);
			
		   Traitement.save(traitementSelectionne);
		   afficherTraitement();
		   onAnnulerEditButton();
		}
    	
    }
    
    public void afficherTraitement(){

		remarques_traitement_textArea.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_traitement.setVisible(false);
		annuler.setVisible(false);
		fiche_traitement_label.setText("FICHE TRAITEMENT :");
		nom_traitement_label.setText(traitementSelectionne.getNom());
		nom_traitement_textField.setDisable(true);
		remarques_traitement_textArea.setDisable(true);
		nom_traitement_label.setText(traitementSelectionne.getNom());
		rafraichirAffichage();
    }
    
    public void afficherTraitements(){

		remarques_traitement_textArea.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_traitement.setVisible(false);
		annuler.setVisible(false);
		fiche_traitement_label.setText("FICHE TRAITEMENT :");
		nom_traitement_textField.setDisable(true);
		remarques_traitement_textArea.setDisable(true);
		
        liste_traitements.clear();
    	
    	if (traitementSelectionne != null){
    		
    		traitementCursor = MongoAccess.request("traitement").as(Traitement.class);
    		
    		while (traitementCursor.hasNext()){
    			Traitement enplus = traitementCursor.next();
    			liste_traitements.add(enplus);
    		}	
    		listView_traitements.setItems(liste_traitements);	
    		
    		rafraichirAffichage();
    	}
		
		
    }
    
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
		
		detail = Main_BEA_BAZ.getDetail();
		traitementSelectionne = Main_BEA_BAZ.getTraitement();

		utils.MongoAccess.connect();
		
		nom_traitement_textField.setEditable(false);
		remarques_traitement_textArea.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_traitement.setVisible(false);
		annuler.setVisible(false);
		
		versCommandeButton.setVisible(false);
		versTraitementButton.setVisible(false);
		versModeleButton.setVisible(false);
		versOeuvreButton.setVisible(false);
		versRapportButton.setVisible(false);
		versFichierButton.setVisible(false);
		
		
		liste_traitements = FXCollections.observableArrayList();
		liste_details  = FXCollections.observableArrayList();
		
		currentStage = Main_BEA_BAZ.getStage();
		
		traitementCursor = MongoAccess.request("traitement").as(Traitement.class);
		
		while (traitementCursor.hasNext()){
			liste_traitements.add(traitementCursor.next());
		}
		
		listView_traitements.setItems(liste_traitements);
		
		
		if (traitementSelectionne != null){
			
			detailCursor = MongoAccess.request("produit", traitementSelectionne).as(Produit.class);
			
			while (detailCursor.hasNext()){
				Produit enplus = detailCursor.next();
				liste_details.add(enplus);
			}
			
			listView_produits.setItems(liste_details);
		}
        
		
		
	
	}

}
