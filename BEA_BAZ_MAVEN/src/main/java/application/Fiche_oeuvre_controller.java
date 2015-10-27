package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import org.bson.types.ObjectId;
import org.jongo.MongoCursor;

import enums.Progression;
import utils.FreeMarkerMaker;
import utils.MongoAccess;
import models.Auteur;
import models.Commande;
import models.Fichier;
import models.Oeuvre;
import models.OeuvreTraitee;
import models.Produit;
import models.TacheTraitement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Fiche_oeuvre_controller  implements Initializable{

	@FXML
	private TableView<TacheTraitement> traitements_attendus_tableView;
	@FXML
	private TableColumn<TacheTraitement, String> traitements_attendus_tableColumn;
	@FXML
	private TableColumn<TacheTraitement, ImageView> faits_attendus_tableColumn;
	//private TableColumn<TacheTraitement, String> faits_attendus_tableColumn;
	
	@FXML
	private TableView<TacheTraitement> traitements_supplementaires_tableView;
	@FXML
	private TableColumn<TacheTraitement, String> traitements_supplementaires_tableColumn;
	@FXML
	private TableColumn<TacheTraitement, ImageView> traitements_supp_faits_tableColumn;
	//private TableColumn<TacheTraitement, String> traitements_supp_faits_tableColumn;
	
	@FXML
	private ListView<Fichier> fichiers_listView;

	@FXML
	private Button annuler;
	@FXML
	private Button editer;
	@FXML
	private Button versOeuvreButton;
	@FXML
	private Button versTraitementsButton;
	@FXML
	private Button versModelesButton;
	@FXML
	private Button versRapportButton;
	@FXML
	private Button versFichiersButton;
	@FXML
	private Button versProduitsButton;
	@FXML
	private Button versAuteursButton;
	@FXML
	private Button mise_a_jour_oeuvre;
	
	@FXML
	private Polygon precedent_fleche;
	@FXML
	private Polygon suivant_fleche;
	
	@FXML
	private Label fiche_oeuvre_label;
	@FXML
	private Label nom_oeuvre_label;
	@FXML
	private TextArea remarques_oeuvre_textArea;
	@FXML
	private ChoiceBox<Auteur> auteursChoiceBox;
	@FXML
	private TextField numero_archive_6s_textField;
	@FXML
	private TextField titre_textField;
	@FXML
	private ChoiceBox<Auteur> auteur_choiceBox;
	@FXML
	private TextField date_oeuvre_textField;
	@FXML
	private TextField dimensions_textField;
	@FXML
	private TextArea inscriptions_textArea;
	@FXML
	private TextArea observations_textArea;
	@FXML
	private GridPane grid;
	
	@FXML
	private TableView<OeuvreTraitee> tableOeuvre;
	@FXML
	private TableColumn<OeuvreTraitee, String> oeuvres_nom_colonne;
	@FXML
	private TableColumn<OeuvreTraitee, ImageView> oeuvres_fait_colonne;
	//private TableColumn<OeuvreTraitee, String> oeuvres_fait_colonne;

	private List<OeuvreTraitee> oeuvresTraitees;
	
	private boolean edit = false;
	private Oeuvre oeuvreSelectionne;
	private OeuvreTraitee oeuvreTraiteeSelectionne;
	private MongoCursor<TacheTraitement> traitementCursor ;
	private ObservableList<TacheTraitement> traitementsAttendus;
	private ObservableList<TacheTraitement> traitementsSupplementaires;
	private ObservableList<Auteur> observableAuteurs;
	private ObservableList<Fichier> observableFichiers;
	private TacheTraitement traitementSelectionne;
	private Commande commandeSelectionne;
	
	private Stage currentStage;
	private Auteur auteur;
	
	boolean directSelect = false;
	
	private MongoCursor<OeuvreTraitee> oeuvresTraiteesCursor;
	
	@FXML
	public void onVersProduitsButton(){
		
		Scene fiche_produit_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_produit.fxml"), 1275, 722);
		fiche_produit_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_produit_scene);	
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
    public void onVersAuteursButton(){
    	Scene fiche_auteur_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_auteur.fxml"), 1275, 722);
		fiche_auteur_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_auteur_scene);
    }
	
	@FXML
	public void onVersCommandeButton(){
		
		Scene fiche_commande_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_commande.fxml"), 1275, 722);
		fiche_commande_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_commande_scene);	
	}	

	@FXML
	public void onTraitementSelect(){
		
		traitementSelectionne = traitements_supplementaires_tableView.getSelectionModel().getSelectedItem();
		Main_BEA_BAZ.setTacheTraitement(traitementSelectionne);
		//affichageInfos();	
	}
	
	@FXML
	public void onOeuvreSelect(){
		
		
		directSelect = true;
		reloadOeuvre();
	
	}
	
	@FXML
	public void onPrecedent_fleche(){

		tableOeuvre.getSelectionModel().select(tableOeuvre.getSelectionModel().getSelectedIndex() -1);
		
		reloadOeuvre();
		
		
		
	}
	@FXML
	public void onPrecedent_fleche_on(){
		
		precedent_fleche.setFill(Color.DEEPSKYBLUE);
	}
	@FXML
	public void onPrecedent_fleche_off(){

		precedent_fleche.setFill(Color.DODGERBLUE);
	}
	
	@FXML
	public void onSuivant_fleche(){

		tableOeuvre.getSelectionModel().select(tableOeuvre.getSelectionModel().getSelectedIndex() +1);
		
		
		reloadOeuvre();
		
	}
	@FXML
	public void onSuivant_fleche_on(){
		
		suivant_fleche.setFill(Color.DEEPSKYBLUE);
	}
	@FXML
	public void onSuivant_fleche_off(){

		suivant_fleche.setFill(Color.DODGERBLUE);
	}
	
	public void reloadOeuvre(){
        
		oeuvreTraiteeSelectionne = (OeuvreTraitee) tableOeuvre.getSelectionModel().getSelectedItem();
		oeuvreSelectionne = oeuvreTraiteeSelectionne.getOeuvre();
		
		Main_BEA_BAZ.setOeuvre(oeuvreTraiteeSelectionne);
		Main_BEA_BAZ.setOeuvre_index(tableOeuvre.getSelectionModel().getSelectedIndex());
		
		if (directSelect){
		   tableOeuvre.scrollTo(Main_BEA_BAZ.getOeuvre_index() -9);
		  // tableOeuvre.getSelectionModel().clearAndSelect(Main_BEA_BAZ.getOeuvre_index());
		  // tableOeuvre.getSelectionModel().focus(Main_BEA_BAZ.getOeuvre_index());
		}
		else {
			tableOeuvre.scrollTo(Main_BEA_BAZ.getOeuvre_index());
		}
		
		numero_archive_6s_textField.setText(oeuvreSelectionne.getCote_archives_6s());
		titre_textField.setText(oeuvreSelectionne.getTitre_de_l_oeuvre());

		date_oeuvre_textField.setText(oeuvreSelectionne.getDate());
		dimensions_textField.setText(oeuvreSelectionne.getDimensions());
		inscriptions_textArea.setText(oeuvreSelectionne.getInscriptions_au_verso());
		observations_textArea.setText(oeuvreSelectionne.get_observations());
		
		afficherAuteurs();
		afficherTraitements();
		afficherFichiers();
	}
	
	public void afficherTraitements(){
		
		traitementsSupplementaires.clear();
		traitementsAttendus.clear();
		
		//TODO
		// vérifier ici :
		
//        traitementCursor = MongoAccess.request("tacheTraitement", "oeuvreTraiteeId", oeuvreTraiteeSelectionne.get_id()).as(TacheTraitement.class);
//		
//		while (traitementCursor.hasNext()){
//			
//			TacheTraitement tt = traitementCursor.next();
//			
//			if(tt.isSupp()){
//				traitementsSupplementaires.add(tt);
//			}
//			else {
//				traitementsAttendus.add(tt);
//			}
//			
//		}
		
		for (ObjectId tt_id : oeuvreTraiteeSelectionne.getTraitementsAttendus()){
			
			traitementsAttendus.add(MongoAccess.request("tacheTraitement", tt_id).as(TacheTraitement.class).next());
		}
		
		//finir la migration -> objectId
		
		
		
		traitements_attendus_tableColumn.setCellValueFactory(new PropertyValueFactory<TacheTraitement, String>("nom"));
		faits_attendus_tableColumn.setCellValueFactory(new PropertyValueFactory<TacheTraitement, ImageView>("icone_progression"));
		//faits_attendus_tableColumn.setCellValueFactory(new PropertyValueFactory<TacheTraitement, String>("fait"));
		
		traitements_supplementaires_tableColumn.setCellValueFactory(new PropertyValueFactory<TacheTraitement, String>("nom"));
		//traitements_supp_faits_tableColumn.setCellValueFactory(new PropertyValueFactory<TacheTraitement, String>("fait"));
		traitements_supp_faits_tableColumn.setCellValueFactory(new PropertyValueFactory<TacheTraitement, ImageView>("icone_progression"));

		traitements_attendus_tableView.setItems(traitementsAttendus);
		
		traitements_supplementaires_tableView.setItems(traitementsSupplementaires);
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
			}
			i++;
		}
		
		auteursChoiceBox.setItems(observableAuteurs);
		auteursChoiceBox.getSelectionModel().select(index);
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
    public void onEditerOeuvreButton(){
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
    public void onMiseAJourOeuvreButton(){
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
    public void onVersOeuvreButton(){}
    @FXML
    public void onVersFichiersButton(){}
    @FXML
    public void onExporter_rapport_button(){
    	
    	FreeMarkerMaker.odt2pdf(oeuvreSelectionne, oeuvreTraiteeSelectionne);
    };
    @FXML
    public void onVersClientButton(){
    	Scene fiche_client_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_client.fxml"), 1275, 722);
		fiche_client_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_client_scene);
    }
    
    public void afficherOeuvres(){
    	
//        oeuvres = MongoAccess.distinct("tacheTraitement", "oeuvre", "commande._id", commandeSelectionne.get_id()).as(Oeuvre.class);
//		
//		oeuvres_nom_colonne.setCellValueFactory(new PropertyValueFactory<Oeuvre, String>("nom"));
//		
//		ObservableList<Oeuvre> obs_oeuvres = FXCollections.observableArrayList(oeuvres);
//		
//		oeuvres_nom_colonne.setCellValueFactory(new PropertyValueFactory<Oeuvre, String>("nom"));
//		//oeuvres_fait_colonne.setCellValueFactory(new PropertyValueFactory<Oeuvre, ImageView>("etat"));
//		
//		tableOeuvre.setItems(obs_oeuvres);
    	
        oeuvresTraiteesCursor = MongoAccess.request("oeuvreTraitee", commandeSelectionne).as(OeuvreTraitee.class);
		
		while (oeuvresTraiteesCursor.hasNext()){
			oeuvresTraitees.add(oeuvresTraiteesCursor.next());
		}
		
		oeuvres_nom_colonne.setCellValueFactory(new PropertyValueFactory<OeuvreTraitee, String>("nom"));
		oeuvres_fait_colonne.setCellValueFactory(new PropertyValueFactory<OeuvreTraitee, ImageView>("icone_progression"));
		//oeuvres_fait_colonne.setCellValueFactory(new PropertyValueFactory<OeuvreTraitee, String>("fait"));
		
		ObservableList<OeuvreTraitee> obs_oeuvres = FXCollections.observableArrayList(oeuvresTraitees);

		tableOeuvre.setItems(obs_oeuvres);
		
		tableOeuvre.getSelectionModel().clearAndSelect(Main_BEA_BAZ.getOeuvre_index());
		tableOeuvre.getSelectionModel().focus(Main_BEA_BAZ.getOeuvre_index());
		
	}
    
    public void afficherFichiers(){
    	
    	observableFichiers.clear();
    	
    	try {
    	
	    	for (ObjectId fichier_id : oeuvreTraiteeSelectionne.getFichiers()){
	    		
	    		observableFichiers.add(MongoAccess.request("fichier", fichier_id).as(Fichier.class).next());
	    	}
	    	
	    	observableFichiers.sort(new Comparator<Fichier>() {

				@Override
				public int compare(Fichier o1, Fichier o2) {
				
					return String.format("%s_%02d", o1.getNom().split("\\.")[1], Integer.parseInt(o1.getNom().split("\\.")[2]))
				.compareTo(String.format("%s_%02d", o2.getNom().split("\\.")[1], Integer.parseInt(o2.getNom().split("\\.")[2])));
				}
			});
	    	
	    	fichiers_listView.setItems(observableFichiers);
	    	
	    	Main_BEA_BAZ.setObservableFichiers(observableFichiers);
    	}
    	catch (NullPointerException npe ){
    		
    	}
    }
    
    @FXML
    public void onTraitementAttenduSelect(){
    	
    	TacheTraitement ttAtt = traitements_attendus_tableView.getSelectionModel().getSelectedItem();
    	Main_BEA_BAZ.setTacheTraitementEdited(ttAtt);
    	
    	Scene fiche_tache_traitement_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_tache_traitement.fxml"), 1275, 722);
		fiche_tache_traitement_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_tache_traitement_scene);
    	
    }
    @FXML
    public void onTraitementSuppSelect(){
    	
    }
    
    @FXML
    public void onFichierSelect(){
    	
    	Fichier fichier = fichiers_listView.getSelectionModel().getSelectedItem();
    	Main_BEA_BAZ.setFichier(fichier);
    	
    	Scene fiche_fichier_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_fichier.fxml"), 1275, 722);
		fiche_fichier_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_fichier_scene);
    	
    }

    	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Main_BEA_BAZ.setTacheTraitementEdited(null);

		traitementSelectionne = Main_BEA_BAZ.getTacheTraitement();
		oeuvreSelectionne = Main_BEA_BAZ.getOeuvre().getOeuvre();
		oeuvreTraiteeSelectionne = Main_BEA_BAZ.getOeuvre();
		commandeSelectionne = Main_BEA_BAZ.getCommande();
		auteur = Main_BEA_BAZ.getAuteur();
		
		numero_archive_6s_textField.setEditable(false);
		titre_textField.setEditable(false);
		date_oeuvre_textField.setEditable(false);
		dimensions_textField.setEditable(false);
		inscriptions_textArea.setEditable(false);
		observations_textArea.setEditable(false);

		numero_archive_6s_textField.setText(oeuvreSelectionne.getCote_archives_6s());
		titre_textField.setText(oeuvreSelectionne.getTitre_de_l_oeuvre());
		date_oeuvre_textField.setText(oeuvreSelectionne.getDate());
		dimensions_textField.setText(oeuvreSelectionne.getDimensions());
		inscriptions_textArea.setText(oeuvreSelectionne.getInscriptions_au_verso());
		observations_textArea.setText(oeuvreSelectionne.get_observations());

        editer.setVisible(true);
        mise_a_jour_oeuvre.setVisible(false);
		annuler.setVisible(false);

		versOeuvreButton.setVisible(false);
		versRapportButton.setVisible(false);

		
		traitementsAttendus = FXCollections.observableArrayList();
		traitementsSupplementaires = FXCollections.observableArrayList();
		observableFichiers = FXCollections.observableArrayList();
		
		oeuvresTraitees = new ArrayList<OeuvreTraitee>();

		
		currentStage = Main_BEA_BAZ.getStage();
        
		afficherFichiers();
		afficherOeuvres();
		reloadOeuvre();

	}
}
