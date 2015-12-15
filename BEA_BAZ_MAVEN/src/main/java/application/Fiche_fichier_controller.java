package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import org.jongo.MongoCursor;

import utils.MongoAccess;
import models.Fichier;
import models.Messages;
import models.Client;
import models.Commande;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Fiche_fichier_controller  implements Initializable{
	
	private ObservableList<Fichier> liste_fichiers;
	@FXML
	private ListView<Fichier> fichiers_listView;

	@FXML
	private TextField nom_fichier_textField;
	@FXML
	private TextField fichier_legende_textField;
	@FXML
	private TextArea remarques_fichier_textArea;
	@FXML
	private Button nouveau_fichier;
	@FXML
	private Button mise_a_jour_fichier;
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
	private Button versModelsButton;
	@FXML
	private ImageView fichier_imageView;
	
	@FXML
	private Label chemin_fichier_label;
	
	MongoCursor<Fichier> fichierCursor;
	Fichier fichierSelectionne;
	
	Stage currentStage;

	Fichier fichier;
	
	private File file;
	
	private boolean edit = false;
	
	@FXML
	public void onVersCommandeButton(){
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
	public void onVersClientButton(){
		
		Scene fiche_client_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_client.fxml"), 1275, 722);
		fiche_client_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_client_scene);
		
	}
	
	@FXML
	public void onFichierSelect(){
		
		fichierSelectionne = fichiers_listView.getSelectionModel().getSelectedItem();
		Messages.setFichier(fichierSelectionne);		
		affichageInfos();
		
	}
	
    private void affichageInfos(){

    	
    	nom_fichier_textField.setText(fichierSelectionne.getNom());
    	remarques_fichier_textArea.setText(fichierSelectionne.getRemarques());
    	fichier_legende_textField.setText(fichierSelectionne.getLegende());
    	chemin_fichier_label.setText(fichierSelectionne.getFichierLie().toString());
    	fichier_imageView.setImage(new Image(String.format("file:%s" ,fichierSelectionne.getFichierLie().toString())));
    	
	
    }
    
    public void onNouveauFichierButton() {
    	
    	mise_a_jour_fichier.setText("Enregistrer");
    	nom_fichier_textField.setText("");
    	remarques_fichier_textArea.setText("");
    	nom_fichier_textField.setPromptText("saisir le nom du nouvel model");
    	remarques_fichier_textArea.setPromptText("éventuelles remarques");
    	nouveau_fichier.setVisible(false);
    	
    	fichierSelectionne = new Fichier();
    	
    	edit = false;
    	annuler.setVisible(true);
    	editer.setVisible(false);
    	mise_a_jour_fichier.setVisible(true);
    	nom_fichier_textField.setEditable(true);
		remarques_fichier_textArea.setEditable(true);
    }
    
    public void onAnnulerButton() {
    	
    	mise_a_jour_fichier.setText("Mise à jour");
    	nom_fichier_textField.setText("");
    	remarques_fichier_textArea.setText("");
    	nom_fichier_textField.setPromptText("");
    	remarques_fichier_textArea.setPromptText("");
    	nouveau_fichier.setText("Nouvel model");
    	rafraichirAffichage();
    	fichiers_listView.getSelectionModel().select(fichierSelectionne);
    	affichageInfos();
    }
    
    public void rafraichirAffichage(){
    	
    	liste_fichiers = FXCollections.observableArrayList();
		
		
		
		fichierCursor = MongoAccess.request("model").as(Fichier.class);
		
		while (fichierCursor.hasNext()){
			liste_fichiers.add(fichierCursor.next());
		}
		
		fichiers_listView.setItems(liste_fichiers);
    	
    }
    
    @FXML
    public void onEditerFichierButton(){
    	

    	annuler.setVisible(true);
    	editer.setVisible(false);
    	mise_a_jour_fichier.setVisible(true);
    	nom_fichier_textField.setEditable(true);
		remarques_fichier_textArea.setEditable(true);
		
		edit = true;

	
    }
    
    @FXML
    public void onAnnulerEditButton(){
    	
    	annuler.setVisible(false);
    	editer.setVisible(true);
    	mise_a_jour_fichier.setVisible(false);
    	nom_fichier_textField.setEditable(false);
		remarques_fichier_textArea.setEditable(false);
		nouveau_fichier.setVisible(true);
		rafraichirAffichage();
		fichiers_listView.getSelectionModel().select(fichierSelectionne);
    	affichageInfos();
    	
    	edit = false;
    	
    }
    
    @FXML
    public void onMiseAJourFichierButton(){
    	
    	if (fichierSelectionne == null){
    		fichierSelectionne = new Fichier();
    	}
    	
    	fichierSelectionne.setNom(nom_fichier_textField.getText());
    	fichierSelectionne.setRemarques(remarques_fichier_textArea.getText());
    	//fichierSelectionne.setCheminVersModelSTR(fichier_legende_textField.getText());
    	
    	annuler.setVisible(false);
    	editer.setVisible(true);
    	mise_a_jour_fichier.setVisible(false);
    	nom_fichier_textField.setEditable(false);
		remarques_fichier_textArea.setEditable(false);
		
		if (edit) {
			Fichier.update(fichierSelectionne);
			//afficherClient();
			rafraichirAffichage();
			onAnnulerEditButton();
		}
		else {
			
		   Fichier.save(fichierSelectionne);
		   //afficherClient();
		   onAnnulerEditButton();
		}
    	
    }
    
protected File chooseExport(){
		
		Stage newStage = new Stage();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Fichier à importer");
		fileChooser.getExtensionFilters().addAll(
		         new FileChooser.ExtensionFilter("image jpg", "*.jpg"));
		File selectedFile = fileChooser.showOpenDialog(newStage);
		if (selectedFile != null) {
			 return selectedFile;
		}
		else {
			 return (File) null;
		}
		
	}
	
	@FXML
	public void onCheminVersModelButton(){
		
		file = chooseExport();
		fichier_legende_textField.setText(file.toString());
		nom_fichier_textField.setText(file.getName().split("\\.")[0]);
	}
	@FXML
	public void on_import_file_button(){
		try {
			Documents.read(file, "produit");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		fichier = Messages.getFichier();
		
		if (fichier != null){
			nom_fichier_textField.setEditable(false);
			remarques_fichier_textArea.setEditable(false);
	        editer.setVisible(true);
	        mise_a_jour_fichier.setVisible(false);
			annuler.setVisible(false);
		}
		else {
			nom_fichier_textField.setEditable(true);
			remarques_fichier_textArea.setEditable(true);
	        editer.setVisible(false);
	        mise_a_jour_fichier.setVisible(true);
	        mise_a_jour_fichier.setText("Enregistrer");
			annuler.setVisible(false);
		}
		
		versClientButton.setVisible(true);
		versCommandeButton.setVisible(false);
		versOeuvreButton.setVisible(false);
		versRapportButton.setVisible(false);
		
		versModelsButton.setVisible(false);

		currentStage = Messages.getStage();
		
		liste_fichiers = Messages.getObservableFichiers();
		fichiers_listView.setItems(liste_fichiers);
		
		int index = 0;
		
		for (Fichier f : liste_fichiers){
			if (f.getNom().equals(fichier.getNom())){
				break;
			}
			else {
				index ++;
			}
		}
		
		fichiers_listView.getSelectionModel().select(index);
		fichierSelectionne = fichier;
		affichageInfos();
	}

}
