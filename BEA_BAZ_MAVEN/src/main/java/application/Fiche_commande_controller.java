package application;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.jongo.MongoCursor;

import com.mongodb.DBPortPool.NoMoreConnection;

import utils.MongoAccess;
import models.Auteur;
import models.Client;
import models.Commande;
import models.Model;
import models.Oeuvre;
import models.TacheTraitement;
import models.Traitement;
import models.TraitementsAttendus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;

public class Fiche_commande_controller  implements Initializable{
	
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
	private Button versProduitsButton;
	@FXML
	private Button versAuteursButton;
	@FXML
	private Button rapportsButton;
	@FXML
	private DatePicker dateCommandePicker;
	@FXML
	private DatePicker dateDebutProjetPicker;
	@FXML
	private DatePicker dateFinProjetPicker;
	
	@FXML
	private ChoiceBox<Model> modelChoiceBox;
	@FXML
	private ChoiceBox<Auteur> auteursChoiceBox;
	
	@FXML
	private VBox commandeExportVbox;
	@FXML
	private TableView tableOeuvre;
	@FXML
	private TableColumn<Oeuvre, String> oeuvres_nom_colonne;
	@FXML
	private TableColumn<Oeuvre, ImageView> oeuvres_fait_colonne;
	
	@FXML
	private GridPane traitementGrid;
	
	private ArrayList<ChoiceBox<Traitement>> traitements_selectionnes;
	private ArrayList<Traitement> traitements_attendus;
	
	private MongoCursor<TraitementsAttendus> traitementsCursor;
	private ObservableList<Traitement> observableTraitements;
	private ObservableList<Auteur> observableAuteurs;

	private List<Oeuvre> oeuvres;

	
	private MongoCursor<Oeuvre> oeuvresCursor;
	private Oeuvre oeuvreSelectionne;
	
	private Stage currentStage;
	
	private Commande commande;
	private Commande commandeSelectionne;
	
	private Client client;
	private Model model;
    private Auteur auteur;
    
    private int index;
    private int i;
	
	private boolean edit = false;
	
	@FXML
	public void onVersClientButton(){
		
		Scene fiche_client_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_client.fxml"), 1275, 722);
		fiche_client_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_client_scene);	
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
		commandeExportVbox.setVisible(false);
		versRapportButton.setVisible(false);
		versModelesButton.setVisible(false);
		versTraitementsButton.setVisible(false);
		versFichiersButton.setVisible(false);
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
			afficherModeles();
			afficherAuteurs();
			afficherOeuvres();
			
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
		
		commande.setClient(client.get_id());
		commande.setDateCommande(dateCommandePicker.getValue());
		commande.setDateDebutProjet(dateDebutProjetPicker.getValue());
		commande.setDateFinProjet(dateFinProjetPicker.getValue());
		commande.setRemarques(remarques_client.getText());
		commande.setNom(nomCommandeTextField.getText());
		commande.setModele(modelChoiceBox.getSelectionModel().getSelectedItem());
		commande.setAuteur(auteursChoiceBox.getSelectionModel().getSelectedItem());
		
		Main_BEA_BAZ.setModel((Model) modelChoiceBox.getSelectionModel().getSelectedItem());
		Main_BEA_BAZ.setAuteur((Auteur) auteursChoiceBox.getSelectionModel().getSelectedItem());
		
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
		
		auteur = auteursChoiceBox.getSelectionModel().getSelectedItem();
		
		Main_BEA_BAZ.setCommande(commande);
		Main_BEA_BAZ.setModel(model);
		Main_BEA_BAZ.setAuteur(auteur);
		
		if (edit) {
			Commande.update(commande);
		}
		else {
		   Commande.save(commande);
		}
		
		afficherCommande();
	    afficherModeles();
	    afficherAuteurs();
	    afficherOeuvres();
	}
	
	public void afficherCommande(){
		
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
			
//			oeuvresCursor = MongoAccess.request("oeuvre", commandeSelectionne).as(Oeuvre.class);
//			
//			while (oeuvresCursor.hasNext()){
//				liste_oeuvres.add(oeuvresCursor.next());
//			}
//			oeuvres_nom_colonne.setCellValueFactory(new PropertyValueFactory<Oeuvre, String>("nom"));
		}
		
		for (ChoiceBox<Traitement> cbt : traitements_selectionnes){
			cbt.getSelectionModel().clearSelection();
		}
		
		//listView_oeuvres.setItems(liste_oeuvres);
        
        int i = 0;
        
        ObservableList<Traitement> menuList = FXCollections.observableArrayList(commande.getTraitements_attendus());
        menuList.add(null);

		for (Traitement t : commande.getTraitements_attendus()){
			traitements_selectionnes.get(i).setItems(menuList);
			traitements_selectionnes.get(i).getSelectionModel().select(i);
			i++;
		}
		
		loadCommande(commande);
		
	}
	
    @FXML
    public void onVersCommandeButton(){}
    @FXML
    public void onVersOeuvreButton(){}
    @FXML
    public void onImporterButton(){
    	Scene fiche_commande_import_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_commande_import.fxml"), 1275, 722);
		fiche_commande_import_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_commande_import_scene);
    }
    @FXML
    public void onVersFichierButton(){}
    @FXML
    public void onVersTraitementButton(){
		Scene fiche_traitement_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_traitement.fxml"), 1275, 722);
		fiche_traitement_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_traitement_scene);
    }
    @FXML
    public void onVersModeleButton(){}
	@FXML
    public void onExporterToutButton(){}
	@FXML
    public void onRapportsButton(){}
	@FXML
    public void onVersRapportButton(){}
	
	public void loadCommande(Commande c){
		
		dateCommandePicker.setValue(c.getDateCommande());;
		dateDebutProjetPicker.setValue(c.getDateDebutProjet());;
		dateFinProjetPicker.setValue(c.getDateFinProjet());
		remarques_client.setText(c.getRemarques());
		nom_commande_label.setText(c.getNom());
		nomCommandeTextField.setText(c.getNom());
		
	}
	
	public void afficherAuteurs(){
		
		observableAuteurs = FXCollections.observableArrayList();
        MongoCursor<Auteur> auteurCursor = MongoAccess.request("auteur").as(Auteur.class);
        
        int index = 0;
        int i = 1;
        
        observableAuteurs.add(null);
		
		while (auteurCursor.hasNext()){
			Auteur auteur_ = auteurCursor.next();
			observableAuteurs.addAll(auteur_);
			if (auteur != null && auteur.getNom().equals(auteur_.getNom())){
				index = i;
				System.out.println("i : " + i);
			}
			i++;
		}
		
		auteursChoiceBox.setItems(observableAuteurs);
		auteursChoiceBox.getSelectionModel().select(index);
	}
	
    public void afficherOeuvres(){
    	
    	Oeuvre o;
    	
        //oeuvresCursor = MongoAccess.request("oeuvre", commandeSelectionne).as(Oeuvre.class);
    	if (commandeSelectionne != null){
			oeuvres = MongoAccess.distinct("tacheTraitement", "oeuvre", "commande._id", commandeSelectionne.get_id()).as(Oeuvre.class);
			

			//liste_oeuvres.add(o);
		}
		
		oeuvres_nom_colonne.setCellValueFactory(new PropertyValueFactory<Oeuvre, String>("nom"));
		oeuvres_fait_colonne.setCellValueFactory(new PropertyValueFactory<Oeuvre, ImageView>("etat"));
		
		
//        type.setCellValueFactory(new Callback<CellDataFeatures<CellFields, ImageView>, ObservableValue<ImageView>>() {
//	    	
//	    	
//	        public ObservableValue<ImageView> call(CellDataFeatures<CellFields, ImageView> p) {
//	        	
//	        	
//	        	ObjectProperty<ImageView> imageview = new SimpleObjectProperty<ImageView>();
//	        	image = p.getValue().getFieldImage();
//	        	
//	        	imageview.set(new ImageView(image));
//	        	imageview.get().setEffect(dropShadow);
//               imageview.get().setFitHeight(24);
//               imageview.get().setFitWidth(24);
//	            return imageview;
//	        }
//	     });
//	    
//	    nom.setCellValueFactory(
//	        new PropertyValueFactory<CellFields,String>("fieldNameFull")
//	    );
//	    taille.setCellValueFactory(
//		    new PropertyValueFactory<CellFields,Integer>("fieldSize")
//		);
//
//	    table.setItems(resArray);
		
		ObservableList<Oeuvre> obs_oeuvres = FXCollections.observableArrayList(oeuvres);
		
		tableOeuvre.setItems(obs_oeuvres);
		
	}
    
    public void afficherTraitements(){
    	
    	observableTraitements.clear();
        MongoCursor<Traitement> mgCursor = MongoAccess.request("traitement").as(Traitement.class);
		
		while (mgCursor.hasNext()){
			observableTraitements.addAll(mgCursor.next());
		}
		
		
		for (Node cb : traitementGrid.getChildren()){
			
			((ChoiceBox<Traitement>) cb).setItems(observableTraitements);
			traitements_selectionnes.add(((ChoiceBox<Traitement>) cb));
		}
    }
    
    public void afficherModeles(){
    	
    	ObservableList<Model> models = FXCollections.observableArrayList();
		MongoCursor<Model> modelsCursor = MongoAccess.request("model").as(Model.class);
		
		index = 0;
		i = 0;
		
		while (modelsCursor.hasNext()){
			
			Model model_  = modelsCursor.next();
			models.addAll(model_);
			if (model != null && model_.getNom().equals(model.getNom())){
				index = i; 
			}
			i++;
		}  
        
		modelChoiceBox.setItems(models);
		
		if (model != null){
			modelChoiceBox.getSelectionModel().select(index);
		}
    }
    
    public void onOeuvreSelect(){
    	
    	Main_BEA_BAZ.setOeuvre((Oeuvre) tableOeuvre.getSelectionModel().getSelectedItem());
    	Main_BEA_BAZ.setOeuvre_index(tableOeuvre.getSelectionModel().getSelectedIndex());
    	
    	Scene fiche_oeuvre_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_oeuvre.fxml"), 1275, 722);
		fiche_oeuvre_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_oeuvre_scene);
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		commande = Main_BEA_BAZ.getCommande();
		client = Main_BEA_BAZ.getClient();
		model = commande.getModele();
		auteur = commande.getAuteur();
		
		index = 0;
	    i = 0;
		
		if (model != null){
			Main_BEA_BAZ.setModel(model);
		}
		if (auteur != null){
			Main_BEA_BAZ.setAuteur(auteur);
		}

		versClientButton.setVisible(true);
		versCommandeButton.setVisible(false);
		versOeuvreButton.setVisible(false);
		versRapportButton.setVisible(false);
		
		versModelesButton.setVisible(true);
		versTraitementsButton.setVisible(true);
		versFichiersButton.setVisible(true);
		versProduitsButton.setVisible(true);
		versAuteursButton.setVisible(true);
		
		currentStage = Main_BEA_BAZ.getStage();
		
		liste_oeuvres = FXCollections.observableArrayList();
		observableTraitements = FXCollections.observableArrayList();
		observableAuteurs = FXCollections.observableArrayList();
		
		traitements_attendus = new ArrayList<>();
		traitements_selectionnes = new ArrayList<>();
	
		afficherTraitements();
		
		afficherAuteurs();
		
        
		if (commande != null) {
			
			commandeSelectionne = commande;
			
			afficherCommande();
			
		}
		else { 
			
			dateCommandePicker.setValue(LocalDate.now());
			dateDebutProjetPicker.setValue(LocalDate.now());
			dateFinProjetPicker.setValue(LocalDate.now());
			
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
		
		afficherModeles();
		
		

	}

}
