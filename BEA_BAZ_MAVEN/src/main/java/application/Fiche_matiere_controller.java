package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.jongo.MongoCursor;

import utils.MongoAccess;
import models.Client;
import models.Commande;
import models.Matiere;
import models.Messages;
import models.Produit;
import models.TacheTraitement;
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

public class Fiche_matiere_controller  implements Initializable{

	@FXML
	private ObservableList<Matiere> liste_matieres;
	@FXML
	private ListView<Matiere> listView_matieres;
	@FXML
	private Button nouvelle_matiere;
	@FXML
	private Button mise_a_jour_matiere;
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
	private Button matieres_button;
	@FXML
	private Button techniques_button;
	@FXML
	private Button importerButton;
	@FXML
	private TextField file_path_textField;
	
	@FXML
	private Label fiche_matiere_label;
	@FXML
	private Label nom_matiere_label;
	@FXML
	private TextField nom_matiere_textField;
	@FXML
	private TextField nom_complet_matiere_textField;
	@FXML
	private TextArea remarques_matiere_textArea;
	
	private boolean edit = false;
	
	private File file;

	MongoCursor<Matiere> matiereCursor ;
	Matiere matiereSelectionne;
	
	Stage currentStage;

	Produit detail;
	
	@FXML
    public void onVersClientButton(){
    	Scene fiche_client_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_client.fxml"), 1275, 722);
		fiche_client_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_client_scene);
    }
	@FXML
	public void onVersCommandeButton(){
		
		Scene fiche_commande_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_commande.fxml"), 1275, 722);
		fiche_commande_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_commande_scene);	
	}
	@FXML
    public void onVersOeuvreButton(){
    	Scene fiche_oeuvre_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_oeuvre.fxml"), 1275, 722);
		fiche_oeuvre_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_oeuvre_scene);
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
    public void onVersModelesButton(){
    	Scene fiche_model_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_model.fxml"), 1275, 722);
		fiche_model_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_model_scene);
    }
	@FXML
	public void onVersTraitementsButton(){
		
		Scene fiche_traitement_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_traitement.fxml"), 1275, 722);
		fiche_traitement_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_traitement_scene);	
	}
	@FXML
    public void onVersAuteursButton(){
    	Scene fiche_auteur_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_auteur.fxml"), 1275, 722);
		fiche_auteur_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_auteur_scene);
    }
    @FXML
    public void onTechniques_button(){
    	Scene fiche_technique_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_technique.fxml"), 1275, 722);
		fiche_technique_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_technique_scene);
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
		
		if (file != null){
			file_path_textField.setVisible(true);
			file_path_textField.setText(file.toString());
			importerButton.setVisible(true);
		}
		
	}
	@FXML
	public void on_import_file_button(){
		try {
			Documents.read(file, "matiere");
			file_path_textField.setVisible(false);
			importerButton.setVisible(false);
			rafraichirAffichage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    private void affichageInfos(Matiere matiereSelectionne){

    	
    	nom_matiere_textField.setText(matiereSelectionne.getNom());
    	remarques_matiere_textArea.setText(matiereSelectionne.getRemarques());
    	
    	liste_matieres.clear();
    	
    	if (matiereSelectionne != null){
    		
    		matiereCursor = MongoAccess.request("matiere").as(Matiere.class);
    		
    		while (matiereCursor.hasNext()){
    			Matiere enplus = matiereCursor.next();
    			liste_matieres.add(enplus);
    		}	
    		listView_matieres.setItems(liste_matieres);		
    	}	
    }
    
    public void onNouvelle_matiereButton() {
    	
    	mise_a_jour_matiere.setText("Enregistrer");
    	nom_matiere_textField.setText("");
    	nom_complet_matiere_textField.setText("");
    	remarques_matiere_textArea.setText("");
    	nom_matiere_textField.setPromptText("saisir le nom affiché du nouveau traitement");
    	nom_complet_matiere_textField.setPromptText("saisir le nom complet du nouveau traitement");
    	remarques_matiere_textArea.setPromptText("éventuelles remarques");
    	nouvelle_matiere.setVisible(false);
    	
    	matiereSelectionne = new Matiere();
    	
    	edit = false;
    	annuler.setVisible(true);
    	editer.setVisible(false);
    	mise_a_jour_matiere.setVisible(true);
    	nom_matiere_textField.setEditable(true);
    	nom_complet_matiere_textField.setEditable(true);
		remarques_matiere_textArea.setEditable(true);
    	
    	
    }
    
    public void onAnnulerButton() {
    	
    	nom_matiere_textField.setEditable(false);
    	nom_complet_matiere_textField.setEditable(false);
		remarques_matiere_textArea.setEditable(false);
    	
    	mise_a_jour_matiere.setText("Mise à jour");
    	nom_matiere_textField.setText("");
    	remarques_matiere_textArea.setText("");
    	nom_matiere_textField.setPromptText("");
    	nom_complet_matiere_textField.setPromptText("");
    	remarques_matiere_textArea.setPromptText("");
    	nouvelle_matiere.setText("Nouveau traitement");
    	rafraichirAffichage();
    	listView_matieres.getSelectionModel().select(matiereSelectionne);
    	affichageInfos(matiereSelectionne);
    	
    }
    
    public void rafraichirAffichage(){

		liste_matieres  = FXCollections.observableArrayList();
		
		
		
		matiereCursor = MongoAccess.request("matiere").as(Matiere.class);
		
		while (matiereCursor.hasNext()){
			liste_matieres.add(matiereCursor.next());
		}
		
		listView_matieres.setItems(liste_matieres);
    	
    }
    
    @FXML
    public void onEditerTraitementButton(){
    	

    	annuler.setVisible(true);
    	editer.setVisible(false);
    	mise_a_jour_matiere.setVisible(true);
    	nom_matiere_textField.setEditable(true);
    	nom_complet_matiere_textField.setEditable(true);
		remarques_matiere_textArea.setEditable(true);
		
		edit = true;

	
    }
    
    @FXML
    public void onAnnulerEditButton(){
    	
    	annuler.setVisible(false);
    	editer.setVisible(true);
    	mise_a_jour_matiere.setVisible(false);
    	nom_matiere_textField.setEditable(false);
    	nom_complet_matiere_textField.setEditable(false);
		remarques_matiere_textArea.setEditable(false);
		nouvelle_matiere.setVisible(true);
		rafraichirAffichage();
		listView_matieres.getSelectionModel().select(matiereSelectionne);
    	
    	edit = false;
    	
    }
    
    @FXML
    public void onMiseAJourMatiereButton(){

    	if (matiereSelectionne == null) {
    		matiereSelectionne = new Matiere();
    	}
    	
    	matiereSelectionne.setNom(nom_matiere_textField.getText());
    	matiereSelectionne.setRemarques(remarques_matiere_textArea.getText());
    	matiereSelectionne.setNom_complet(nom_complet_matiere_textField.getText());
    	
    	listView_matieres.getSelectionModel().select(matiereSelectionne);

		
		if (edit) {
			Matiere.update(matiereSelectionne);
			afficherMatiere();
			rafraichirAffichage();
			onAnnulerEditButton();
		}
		else {
			
		   Matiere.save(matiereSelectionne);
		   afficherMatiere();
		   onAnnulerEditButton();
		}
    	
    }
    
    public void afficherMatiere(){

		remarques_matiere_textArea.setEditable(false);
		nom_complet_matiere_textField.setEditable(false);
		nom_matiere_textField.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_matiere.setVisible(false);
		annuler.setVisible(false);
		
		fiche_matiere_label.setText("FICHE PRODUIT :");
		nom_matiere_label.setText(matiereSelectionne.getNom());
		nom_matiere_textField.setText(matiereSelectionne.getNom());
		nom_complet_matiere_textField.setText(matiereSelectionne.getNom_complet());
		remarques_matiere_textArea.setText(matiereSelectionne.getRemarques());
		//rafraichirAffichage();
    }

    @FXML
    public void onMatiereSelect(){
    	
    	matiereSelectionne = listView_matieres.getSelectionModel().getSelectedItem();
    	
    	if(matiereSelectionne != null){
    		afficherMatiere();
    	}
    		
    }   

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nom_matiere_textField.setEditable(false);
		nom_complet_matiere_textField.setEditable(false);
		remarques_matiere_textArea.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_matiere.setVisible(false);
		annuler.setVisible(false);
		
		if(Messages.getCommande() != null){
			versCommandeButton.setVisible(true);
		}
		else {
			versCommandeButton.setVisible(false);
		}
		
		if(Messages.getOeuvreTraitee() != null){
			versOeuvreButton.setVisible(true);
		}
		else {
			versOeuvreButton.setVisible(false);
		}
		versRapportButton.setVisible(false);
		matieres_button.setVisible(false);
		
		file_path_textField.setVisible(false);
		importerButton.setVisible(false);


		liste_matieres  = FXCollections.observableArrayList();
		
		currentStage = Messages.getStage();
		
        matiereCursor = MongoAccess.request("matiere").as(Matiere.class);
		
		while (matiereCursor.hasNext()){
			liste_matieres.add(matiereCursor.next());
		}
		
		listView_matieres.setItems(liste_matieres);
		
		listView_matieres.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			onMatiereSelect();
		});
		
		listView_matieres.getSelectionModel().select(0);
		matiereSelectionne = listView_matieres.getSelectionModel().getSelectedItem();

	}

}
