package application;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.jongo.MongoCursor;

import enums.Progression;
import utils.FreeMarkerMaker;
import utils.MongoAccess;
import models.Auteur;
import models.Client;
import models.Commande;
import models.Messages;
import models.Model;
import models.Oeuvre;
import models.OeuvreTraitee;
import models.TacheTraitement;
import models.Traitement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
	private DatePicker dateCommandePicker;
	@FXML
	private DatePicker dateDebutProjetPicker;
	@FXML
	private DatePicker dateFinProjetPicker;
	
	@FXML
	private RadioButton fait_radioButton;
	
	@FXML
	private ChoiceBox<String> modelChoiceBox;
	@FXML
	private ChoiceBox<String> auteursChoiceBox;
	
	@FXML
	private VBox commandeExportVbox;
	@FXML
	private TableView<OeuvreTraitee> tableOeuvre;
	@FXML
	private TableColumn<OeuvreTraitee, String> oeuvres_nom_colonne;
	@FXML
	private TableColumn<OeuvreTraitee, ImageView> oeuvres_fait_colonne;
	
	@FXML
	private GridPane traitementGrid;
	
	private ArrayList<ChoiceBox<String>> traitements_selectionnes;
	private ArrayList<String> traitements_attendus;
	private Map<String, ObjectId> traitements_attendus_id;

	private ObservableList<String> observableTraitements;
	private Map<String, ObjectId> traitements_id;
	
	private ObservableList<String> observableAuteurs;
	private Map<String, ObjectId> auteurs_id;
	
	private ObservableList<String> observableModeles;
	private Map<String, ObjectId> modeles_id;

	private List<OeuvreTraitee> oeuvresTraitees;
	private MongoCursor<OeuvreTraitee> oeuvresTraiteesCursor;
	ObservableList<OeuvreTraitee> obs_oeuvres;
	
	private Stage currentStage;
	
	private Commande commande;
	private Commande commandeSelectionne;
	
	private Client client;
	private Model model;
    private Auteur auteur;
    
    private String client_name;
	private String model_name;
    private String auteur_name;
    
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
    public void onVersFichiersButton(){}
    @FXML
    public void onVersTraitementButton(){
		Scene fiche_traitement_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_traitement.fxml"), 1275, 722);
		fiche_traitement_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_traitement_scene);
    }
    @FXML
    public void onVersModeleButton(){}
	
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

		if (Messages.getCommande() == null){
			commande = new Commande();
		}
		else{
			commande = MongoAccess.request("commande", Messages.getCommande_id()).as(Commande.class).next(); 
		}		

		commande.setDateCommande(dateCommandePicker.getValue());
		commande.setDateDebutProjet(dateDebutProjetPicker.getValue());
		commande.setDateFinProjet(dateFinProjetPicker.getValue());
		commande.setRemarques(remarques_client.getText());
		commande.setNom(nomCommandeTextField.getText());
		model_name = modelChoiceBox.getSelectionModel().getSelectedItem();
		model = MongoAccess.request("model", modeles_id.get(model_name)).as(Model.class).next();
		commande.setModele_id(modeles_id.get(model_name));
		auteur_name = auteursChoiceBox.getSelectionModel().getSelectedItem();
		auteur = MongoAccess.request("auteur", auteurs_id.get(auteur_name)).as(Auteur.class).next();
		commande.setAuteur_id(auteurs_id.get(auteur_name));
		
		Messages.setModel(model);
		Messages.setModel_name(model_name);
		Messages.setAuteur(auteur);
		Messages.setAuteur_name(auteur_name);
		
				
        traitements_attendus.clear();
        commande.setTraitements_attendus_id(new HashMap<String, ObjectId>());
		
		for (Node cb : traitementGrid.getChildren()){
			
			ChoiceBox<String> cbox = (ChoiceBox<String>) cb;
			String t = cbox.getValue();

			if (t != null && 
				!traitements_attendus.contains(t)){
				
				String traitement_attendu = ((ChoiceBox<String>) cb).getValue();
				
				traitements_attendus.add(traitement_attendu);
				commande.addTraitement_attendu_id(traitement_attendu, traitements_id.get(traitement_attendu));
				
			}
			
		}
		
		traitements_attendus_id = commande.getTraitements_attendus_id();
		Messages.setTraitementsAttendus_id(traitements_attendus_id);
		System.out.println("mise a jour : " + traitements_attendus_id.keySet());
	
		if (edit) {
			Commande.update(commande);
		}
		else {
		   Commande.save(commande);
		   
		   client.getCommandes_id().put(commande.getNom(), commande.get_id());
		   
		   MongoAccess.update("client", client);
		}
		
		Messages.setCommande_id(commande.get_id());
		Messages.setCommande(commande.getNom());
		
		Map<String, ObjectId> commandes_id;
		
		if (Messages.getCommandes_id() != null){
			commandes_id = Messages.getCommandes_id();
		}
		else {
			commandes_id = new TreeMap<>();
		}

		commandes_id.put(commande.getNom(), commande.get_id());
		Messages.setCommandes_id(commandes_id);
	
		afficherTraitements();
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
		
		if (Messages.getTraitementsAttendus_id() == null){
		    
			traitements_attendus_id = commande.getTraitements_attendus_id();
    		Messages.setTraitementsAttendus_id(traitements_attendus_id);
    		
    	}
    	else {
    		traitements_attendus_id = Messages.getTraitementsAttendus_id();
    	}
		

		for (ChoiceBox<String> cbt : traitements_selectionnes){
			cbt.getSelectionModel().clearSelection();
		}
        
        int i = 0;
        System.out.println("afficher : " + traitements_attendus_id.keySet());
        
        ObservableList<String> menuList = FXCollections.observableArrayList(traitements_attendus_id.keySet());        
        menuList.add(null);

		for (String t : traitements_attendus_id.keySet()){

			traitements_selectionnes.get(i).setItems(menuList);
			traitements_selectionnes.get(i).getSelectionModel().select(i);
			i++;
		}
		
		loadCommande(commande);
	}
	
    
	@FXML
    public void onExporterToutButton(){
		
		MongoCursor<OeuvreTraitee> oeuvresAExporterCursor;
		
		if(fait_radioButton.isSelected()){
			oeuvresAExporterCursor = MongoAccess.request("oeuvreTraitee", Progression.FAIT_, commande).as(OeuvreTraitee.class);
		}
		else {
			oeuvresAExporterCursor = MongoAccess.request("oeuvreTraitee", commande).as(OeuvreTraitee.class);
		}
		
		ArrayList<OeuvreTraitee> oeuvresAExporter = new ArrayList<>();
		
		while (oeuvresAExporterCursor.hasNext()){
			oeuvresAExporter.add(oeuvresAExporterCursor.next());
		}
		
		for ( OeuvreTraitee ot : oeuvresAExporter){
			
			Oeuvre o = ot.getOeuvre();
			FreeMarkerMaker.odt2pdf(o, ot);
		}
		
		 
	}
	@FXML
    public void onRapportsButton(){}
	@FXML
    public void onVersRapportButton(){}
	
	public void loadCommande(Commande c){
		
		if (c != null){
		
			dateCommandePicker.setValue(c.getDateCommande());;
			dateDebutProjetPicker.setValue(c.getDateDebutProjet());;
			dateFinProjetPicker.setValue(c.getDateFinProjet());
			remarques_client.setText(c.getRemarques());
			nom_commande_label.setText(c.getNom());
			nomCommandeTextField.setText(c.getNom());	
		}
	}
	
	
    
    public void afficherTraitements(){
    	
    	observableTraitements.clear();
    	
    	if (Messages.getTraitements_id() == null){
            
    		MongoCursor<Traitement> mongoCursor = MongoAccess.request("traitement").as(Traitement.class);
    		
    		while (mongoCursor.hasNext()){
    			
    			
    			Traitement t = mongoCursor.next();
    			traitements_id.put(t.getNom(), t.get_id());
    		}

    		Messages.setTraitements_id(traitements_id);
    		
    	}
    	else {
    		traitements_id = Messages.getTraitements_id();
    		
    	}
    	
    	observableTraitements.addAll(traitements_id.keySet());

		for (Node cb : traitementGrid.getChildren()){
			
			((ChoiceBox<String>) cb).setItems(observableTraitements);
			traitements_selectionnes.add(((ChoiceBox<String>) cb));
		}
    }
    
    public void afficherOeuvres(){
    	
    	oeuvresTraitees = FXCollections.observableArrayList();
    	
    	if (Messages.getObservablOeuvresTraitees() == null){
        	
    	    oeuvresTraitees = commandeSelectionne.getOeuvresTraitees_id()
    	    		                             .values()
    	    		                             .stream()
    	    		                             .map(a -> MongoAccess.request("oeuvreTraitee", a).as(OeuvreTraitee.class).next())
    	    		                             .collect(Collectors.toList());

    		obs_oeuvres = FXCollections.observableArrayList(oeuvresTraitees);
    		Messages.setObservablOeuvresTraitees(obs_oeuvres);
    	}

    	else if(Messages.getObservablOeuvresTraitees() != null){
    		
    		obs_oeuvres = Messages.getObservablOeuvresTraitees();	
    	}
    	
    	oeuvres_nom_colonne.setCellValueFactory(new PropertyValueFactory<OeuvreTraitee, String>("nom"));
		oeuvres_fait_colonne.setCellValueFactory(new PropertyValueFactory<OeuvreTraitee, ImageView>("icone_progression"));
    	
		tableOeuvre.setItems(obs_oeuvres);

	}
    
    public void afficherAuteurs(){
	
	     index = 0;
		
		observableAuteurs = FXCollections.observableArrayList();
		auteurs_id = new TreeMap<>();
		
		if (Messages.getAuteur() == null && Messages.getCommande_id()!= null){
			
			if (commande == null) {
				commande = MongoAccess.request("commande", Messages.getCommande_id()).as(Commande.class).next();
			}
	
			auteur = commande.getAuteur();
			Messages.setAuteur(auteur);
	
		}
		else {
			auteur = Messages.getAuteur();
		}
		
		
		if (Messages.getAuteurs_id() == null){

			MongoCursor<Auteur> auteurCursor = MongoAccess.request("auteur").as(Auteur.class);
	        
	        observableAuteurs.add(null);
			
			while (auteurCursor.hasNext()){
				Auteur auteur_ = auteurCursor.next();
				observableAuteurs.addAll(auteur_.getNom());
				auteurs_id.put(auteur_.getNom(), auteur_.get_id());
			}
	
			Messages.setAuteurs_id(auteurs_id);
		}
		else {
			auteurs_id = Messages.getAuteurs_id();
			observableAuteurs.addAll(auteurs_id.keySet());
		}
		
		for (String aut : observableAuteurs){
			
			if (auteur != null && auteur.getNom().equals(aut)){
				break;
			}
			index++;
		}

		auteursChoiceBox.setItems(observableAuteurs);
		auteursChoiceBox.getSelectionModel().select(index);
	}
    
    public void afficherModeles(){
    	
		
		index = 0;
    	
    	observableModeles = FXCollections.observableArrayList();
    	modeles_id = new TreeMap<>();
    	
    	if (Messages.getModel() == null && Messages.getCommande_id()!= null){
    		if (commande == null) {
				commande = MongoAccess.request("commande", Messages.getCommande_id()).as(Commande.class).next();
			}
	
			model = commande.getModel();
			Messages.setModel(model);
    	}
    	else {
    		model = Messages.getModel();
    	}
    	
    	if(Messages.getModels_id() == null){
    		
    		MongoCursor<Model> modelsCursor = MongoAccess.request("model").as(Model.class);
    		
    		observableModeles.add(null);
    		
    		while (modelsCursor.hasNext()){
    			
    			Model model_  = modelsCursor.next();
    			observableModeles.add(model_.getNom());
    			modeles_id.put(model_.getNom(), model_.get_id());
    		} 
    		Messages.setModels_id(modeles_id);
    	}
    	else {
    		modeles_id = Messages.getModels_id();
    		observableModeles.addAll(modeles_id.keySet());
    	}
    	
    	for (String mo: observableModeles){
    		
    		if (model != null && model.getNom().equals(mo)){
				break;
			}
			index++;
    	}

		modelChoiceBox.setItems(observableModeles);

		modelChoiceBox.getSelectionModel().select(index);
    }
    
    public void onOeuvreSelect(){
    	
    	Messages.setOeuvreTraitee((OeuvreTraitee) tableOeuvre.getSelectionModel().getSelectedItem());
    	Messages.setOeuvre_index(tableOeuvre.getSelectionModel().getSelectedIndex());
    	Messages.setOeuvre(Messages.getOeuvreTraitee().getOeuvre());
    	
    	Messages.setAuteur(null);
		Messages.setFichier(null);
		Messages.setFichiers_id(null);
		Messages.setTacheTraitement(null);
		Messages.setTacheTraitements_id(null);
    	
    	Scene fiche_oeuvre_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_oeuvre.fxml"), 1275, 722);
		fiche_oeuvre_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_oeuvre_scene);
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		client = MongoAccess.request("client", Messages.getClient_id()).as(Client.class).next(); 
		
		versClientButton.setVisible(true);
		versCommandeButton.setVisible(false);
		versOeuvreButton.setVisible(false);
		versRapportButton.setVisible(false);
		
		versModelesButton.setVisible(true);
		versTraitementsButton.setVisible(true);
		versFichiersButton.setVisible(true);
		versProduitsButton.setVisible(true);
		versAuteursButton.setVisible(true);
		
		currentStage = Messages.getStage();
		
		observableTraitements = FXCollections.observableArrayList();
		traitements_id = new TreeMap<>();
		traitements_selectionnes = new ArrayList<>();
		observableAuteurs = FXCollections.observableArrayList();
		oeuvresTraitees = new ArrayList<>();
		traitements_attendus = new ArrayList<>();
		traitements_attendus_id = new TreeMap<>();
		
		afficherTraitements();
		
        if (Messages.getCommande() != null) {
        	
        	commande = MongoAccess.request("commande", Messages.getCommande_id()).as(Commande.class).next(); 
        	commandeSelectionne = commande;	
			
        	Messages.setOeuvresTraitees_id(commande.getOeuvresTraitees_id());
        	
        	model = MongoAccess.request("model", commande.getModele_id()).as(Model.class).next();
        	Messages.setModel_name(model_name);
        	Messages.setModel(model);
        	
    		auteur = MongoAccess.request("auteur", commande.getAuteur_id()).as(Auteur.class).next();
    		Messages.setAuteur(auteur);
    		
    		traitements_attendus_id = commande.getTraitements_attendus_id();
    		traitements_attendus.addAll(traitements_attendus_id.keySet());
    		
    		afficherCommande();
    		afficherOeuvres();
		}

		else { 
			
			commande = new Commande();
			liste_oeuvres = FXCollections.observableArrayList();
			
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
			commandeExportVbox.setVisible(false);
			versRapportButton.setVisible(false);
			
			fiche_commande_label.setText("FICHE COMMANDE (nouvelle commande) :");
			nom_commande_label.setText("");

			try {
			    nomClientLabel.setText(client.getNom());
			    nomCommandeTextField.setText(client.getNom() + "_" + LocalDate.now());
			}
			catch (NullPointerException npe) {
				
			}
		}
		
		afficherAuteurs();
		afficherModeles();
		
	}
}
