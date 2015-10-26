package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.jongo.Find;
import org.jongo.MongoCursor;

import utils.MongoAccess;
import utils.Walk;
import models.Client;
import models.Commande;
import models.Oeuvre;
import models.OeuvreTraitee;
import models.TacheTraitement;
import models.Traitement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Fiche_commande_import_controller  implements Initializable{
	
	@FXML
	private ObservableList<Oeuvre> liste_oeuvres;
	@FXML
	private Label nomClientLabel;
	@FXML
	private Label fiche_commande_label;
	@FXML
	private Label nom_commande_label;
	@FXML
	private TextField nomCommandeTextField;
	@FXML
	private TableColumn<OeuvreTraitee, String> oeuvres_nom_colonne;
	@FXML
	private TableColumn<OeuvreTraitee, ImageView> oeuvres_fait_colonne;
	
	@FXML
	private TextArea remarques_client;
	@FXML
	private Button nouvelle_oeuvre;
	@FXML
	private Button mise_a_jour_commande;
	@FXML
	private Button annuler;
	@FXML
	private Button editer;
	@FXML
	private Button importCommandeButton;
	@FXML
	private Button versClientButton;
	@FXML
	private Button versCommandeButton;
	@FXML
	private Button versOeuvreButton;
	@FXML
	private Button versRapportButton;
	@FXML
	private Button versFichiersButton;
	@FXML
	private Button versModelesButton;
	@FXML
	private Button versTraitementsButton;
	@FXML
	private Button versAuteursButton;
	@FXML
	private Button versProduitsButton;
	@FXML
	private Button rapportsButton;
	@FXML
	private DatePicker dateCommandePicker;
	@FXML
	private DatePicker dateDebutProjetPicker;
	@FXML
	private DatePicker dateFinProjetPicker;
	
	@FXML
	private TextField file_path_textField;
	@FXML
	private Button select_file_button;
	@FXML
	private Button import_file_button;
	
	@FXML
	private GridPane grid;
	@FXML
	private HBox hbox_1;
	@FXML
	private HBox hbox_2;
	@FXML
	private HBox hbox_3;
	
	@FXML
	private VBox commandeExportVbox;
	
	@FXML
	private GridPane traitementGrid;
	
	@FXML
	private TableView<OeuvreTraitee> tableOeuvre;
	
	@FXML
	private Button import_rep_button;
	@FXML
	private Button select_rep_button;
	@FXML
	private TextField rep_path_textField;
	
	@FXML
	private Label message_label;
	
	private ArrayList<ChoiceBox<Traitement>> traitements_selectionnes;
	private ArrayList<Traitement> traitements_attendus;
	private ObservableList<Traitement> observableTraitements;

	
	private MongoCursor<OeuvreTraitee> oeuvresTraiteesCursor;
	private OeuvreTraitee oeuvreTraiteeSelectionne;
	
	private List<OeuvreTraitee> oeuvresTraitees;
	
	private Stage currentStage;
	
	private Commande commande;
	private Commande commandeSelectionne;
	
	private Client client;
	
	private File file;
	private File dir;
	
	private static StringProperty bindLabel;
	
	private boolean edit = false;
	
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
	public void onVersProduitsButton(){
		
		Scene fiche_produit_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_produit.fxml"), 1275, 722);
		fiche_produit_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_produit_scene);	
	}
	@FXML
    public void onVersAuteursButton(){
    	Scene fiche_auteur_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_auteur.fxml"), 1275, 722);
		fiche_auteur_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_auteur_scene);
    }
	@FXML
    public void onVersTraitementsButton(){
		Scene fiche_traitement_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_traitement.fxml"), 1275, 722);
		fiche_traitement_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_traitement_scene);
    }
    @FXML
    public void onVersModelesButton(){
    	Scene fiche_model_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_model.fxml"), 1275, 722);
		fiche_model_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_model_scene);
    }
    @FXML
    public void onVersFichiersButton(){}
	
	
	@FXML
	public void onEditerButton(){
		
		importCommandeButton.setDisable(true);
		dateCommandePicker.setEditable(true);
		dateDebutProjetPicker.setEditable(true);
		dateFinProjetPicker.setEditable(true);
		remarques_client.setEditable(true);
        editer.setVisible(false);
        mise_a_jour_commande.setText("Mise à jour");
        mise_a_jour_commande.setVisible(true);
		annuler.setVisible(true);
		rapportsButton.setVisible(false);
		commandeExportVbox.setVisible(false);
		versRapportButton.setVisible(false);
		
		versModelesButton.setVisible(false);
		versTraitementsButton.setVisible(false);
		versFichiersButton.setVisible(false);
		versProduitsButton.setVisible(false);
		versAuteursButton.setVisible(false);
		
		fiche_commande_label.setText("FICHE COMMANDE :");
		nom_commande_label.setText(commande.getNom());
		nomCommandeTextField.setDisable(false);
		edit = true;
		
	}
	
	@FXML
	public void onAnnulerButton(){
		
		importCommandeButton.setDisable(false);
		dateCommandePicker.setEditable(false);
		dateDebutProjetPicker.setEditable(false);
		dateFinProjetPicker.setEditable(false);
		remarques_client.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_commande.setVisible(false);
		annuler.setVisible(false);
		fiche_commande_label.setText("FICHE COMMANDE :");
		nomCommandeTextField.setDisable(true);
		
		
		if (edit) {
			afficherCommande();
		}
		else {
			onVersClientButton();
		}
		edit = false;
	}
	
	@FXML
	public void onMiseAJourButton(){
		
		if (commande == null){
			commande = new Commande();
		}
		else{
			commande = Main_BEA_BAZ.getCommande(); 
		}
		
		traitements_attendus.clear();
		
		
		commande.setClient(client);
		commande.setDateCommande(dateCommandePicker.getValue());
		commande.setDateDebutProjet(dateDebutProjetPicker.getValue());
		commande.setDateFinProjet(dateFinProjetPicker.getValue());
		commande.setRemarques(remarques_client.getText());
		commande.setNom(nomCommandeTextField.getText());
		
		traitements_attendus.clear();
		
		for (Node cb : traitementGrid.getChildren()){
			
			ChoiceBox<Traitement> cbox = (ChoiceBox<Traitement>) cb;
			Traitement t = cbox.getValue();

			if (t != null && 
				!traitements_attendus.stream().map(a -> a.get_id().toString()).collect(Collectors.toList()).contains(t.get_id().toString())){

				traitements_attendus.add(((ChoiceBox<Traitement>) cb).getValue());
			}
			
		}
		
		commande.setTraitements_attendus(traitements_attendus);
		
		Main_BEA_BAZ.setCommande(commande);
		
		if (edit) {
			Commande.update(commande);
			afficherCommande();
		}
		else {
		   Commande.save(commande);
		   onVersClientButton();
		}
		edit = false;
	}
	
	public void afficherCommande(){
		
		versModelesButton.setVisible(true);
		versTraitementsButton.setVisible(true);
		versFichiersButton.setVisible(true);
		versProduitsButton.setVisible(true);
		versAuteursButton.setVisible(true);
		
		importCommandeButton.setDisable(false);
		dateCommandePicker.setEditable(false);
		dateDebutProjetPicker.setEditable(false);
		dateFinProjetPicker.setEditable(false);
		remarques_client.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_commande.setVisible(false);
		annuler.setVisible(false);
		fiche_commande_label.setText("FICHE COMMANDE :");
		nomClientLabel.setText(client.getNom());
		nomCommandeTextField.setDisable(true);
		nomClientLabel.setText(client.getNom());
		
		if (commandeSelectionne != null){
			afficherOeuvres();
		}

		//listView_oeuvres.setItems(liste_oeuvres);
		
		try {
		
			for (ChoiceBox<Traitement> cbt : traitements_selectionnes){
				cbt.setItems(null);
				cbt.getSelectionModel().clearSelection();
			}
		}
		catch (NullPointerException npe){
			
		}
        
        int i = 0;

		for (Traitement t : commande.getTraitements_attendus()){
			traitements_selectionnes.get(i).setItems(FXCollections.observableArrayList(commande.getTraitements_attendus()));;
			traitements_selectionnes.get(i).getSelectionModel().select(i);
			i++;
		}

		
		//loadCommande(commande);
		
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
			Documents.read(file, commandeSelectionne);
			onVersCommandeButton();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	Service<String> imageLoadingService = new Service<String>(){
//
//	  @Override
//	  protected Task<String> createTask() {
//		  
//		  Task<String> imageLoadingTask = new Task<String>(){
//
//			  @Override
//			  protected String call() throws Exception {
//
//			    return "";
//			  }
//			};
//		return imageLoadingTask;
//
//	  }	
//	};
	
	
	
    @FXML
    public void onVersOeuvreButton(){}
    @FXML
    public void onVersRapportButton(){}
    @FXML
    public void onVersFichierButton(){}
    
	@FXML
    public void onExporterToutButton(){}
	@FXML
    public void onRapportsButton(){}
	
	public void afficherOeuvres(){
		
		//oeuvresTraitees = MongoAccess.distinct("tacheTraitement", "oeuvreTraitee", "commande._id", commandeSelectionne.get_id()).as(OeuvreTraitee.class);
		
		
		oeuvresTraiteesCursor = MongoAccess.request("oeuvreTraitee", commandeSelectionne).as(OeuvreTraitee.class);
		
		while (oeuvresTraiteesCursor.hasNext()){
			oeuvresTraitees.add(oeuvresTraiteesCursor.next());
		}
		
		oeuvres_nom_colonne.setCellValueFactory(new PropertyValueFactory<OeuvreTraitee, String>("nom"));
		
		ObservableList<OeuvreTraitee> obs_oeuvres = FXCollections.observableArrayList(oeuvresTraitees);

		tableOeuvre.setItems(obs_oeuvres);
		
	}
	
	public void loadCommande(Commande c){
		
		dateCommandePicker.setValue(c.getDateCommande());;
		dateDebutProjetPicker.setValue(c.getDateDebutProjet());;
		dateFinProjetPicker.setValue(c.getDateFinProjet());
		remarques_client.setText(c.getRemarques());
		nom_commande_label.setText(c.getNom());
		nomCommandeTextField.setText(c.getNom());
		
	}
	
	@FXML
	public void onImportImagesButton(){
		
		select_rep_button.setVisible(true);
		rep_path_textField.setVisible(true);
		import_rep_button.setVisible(true);
	}
	@FXML
	public void on_select_rep_button(){
		dir = chooseRep();
		rep_path_textField.setText(dir.toString());
	}
	@FXML
	public void on_import_rep_button(){

		Walk.walking(dir.toPath());
		onVersCommandeButton();	
	}
	
	
	
    protected File chooseRep(){
		
		Stage newStage = new Stage();
		
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setTitle("Dossier à importer");
		File selectedDir = dirChooser.showDialog(newStage);
		if (selectedDir != null) {
			 return selectedDir;
		}
		else {
			 return (File) null;
		}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		bindLabel = new SimpleStringProperty("En attente d'import ...");
		message_label.textProperty().bind(bindLabel);
		
		commande = Main_BEA_BAZ.getCommande();
		commandeSelectionne = Main_BEA_BAZ.getCommande();
		client = Main_BEA_BAZ.getClient();

		versClientButton.setVisible(true);
		versCommandeButton.setVisible(false);
		versOeuvreButton.setVisible(false);
		versRapportButton.setVisible(false);
		
		versModelesButton.setVisible(true);
		versTraitementsButton.setVisible(true);
		versFichiersButton.setVisible(true);
		versProduitsButton.setVisible(true);
		versAuteursButton.setVisible(true);
		
		grid.setVisible(false);
		hbox_1.setVisible(false);
		hbox_2.setVisible(false);
		hbox_3.setVisible(false);
		
		currentStage = Main_BEA_BAZ.getStage();
		
		liste_oeuvres = FXCollections.observableArrayList();
//		observableTraitements = FXCollections.observableArrayList();
//		
//		traitements_attendus = new ArrayList<>();
		traitements_selectionnes = new ArrayList<>();
		oeuvresTraitees = new ArrayList<>();
//		
//		MongoCursor<Traitement> mgCursor = MongoAccess.request("traitement").as(Traitement.class);
//		
//		while (mgCursor.hasNext()){
//			observableTraitements.addAll(mgCursor.next());
//		}
//		
//		
//		for (Node cb : traitementGrid.getChildren()){
//			
//			((ChoiceBox<Traitement>) cb).setItems(observableTraitements);
//			traitements_selectionnes.add(((ChoiceBox<Traitement>) cb));
//		}
        
		if (commande != null) {
			
			//afficherCommande();
			
		}
		else { 
			
			importCommandeButton.setDisable(true);
			dateCommandePicker.setEditable(true);
			dateDebutProjetPicker.setEditable(true);
			dateFinProjetPicker.setEditable(true);
			remarques_client.setEditable(true);
	        editer.setVisible(false);
	        mise_a_jour_commande.setText("Créer");
	        mise_a_jour_commande.setVisible(true);
			annuler.setVisible(true);
			rapportsButton.setVisible(false);
			commandeExportVbox.setVisible(false);
			versRapportButton.setVisible(false);
			
			versModelesButton.setVisible(false);
			versTraitementsButton.setVisible(false);
			versFichiersButton.setVisible(false);
			versProduitsButton.setVisible(false);
			versAuteursButton.setVisible(false);
			
			fiche_commande_label.setText("FICHE COMMANDE (nouvelle commande) :");
			nom_commande_label.setText("");

			try {
			    nomClientLabel.setText(client.getNom());
			    nomCommandeTextField.setText(client.getNom() + "_" + LocalDate.now());
			}
			catch (NullPointerException npe) {
				
			}
		}
	}
	
	public static StringProperty getBindLabel() {
		return bindLabel;
	}


}
