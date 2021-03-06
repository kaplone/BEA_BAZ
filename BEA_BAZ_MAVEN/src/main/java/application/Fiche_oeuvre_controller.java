package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.jongo.MongoCursor;

import enums.EtatFinal;
import enums.Progression;
import utils.FreeMarkerMaker;
import utils.MongoAccess;
import models.Auteur;
import models.Commande;
import models.Fichier;
import models.Matiere;
import models.Oeuvre;
import models.OeuvreTraitee;
import models.Produit;
import models.TacheTraitement;
import models.Technique;
import models.Traitement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
	private TextArea degradations_textArea;
	@FXML
	private TextArea remarques_textArea;
	@FXML
	private TextArea observations_textArea;
	@FXML
	private HBox matieres_hbox;
	@FXML
	private HBox techniques_hbox;
	@FXML
	private ListView<Matiere> matieres_listView;
	@FXML
	private ListView<Technique> techniques_listView;
	
	@FXML
	private ImageView preview_imageView;
	
	
	@FXML
	private GridPane grid;
	@FXML
	private ChoiceBox<EtatFinal> etat_final_choiceBox;
	@FXML
	private TextArea complement_etat_textArea;
	
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
	private MongoCursor<Matiere> matieresCursor;
	private MongoCursor<Technique> techniquesCursor;
	
	private ObservableList<Matiere> matieres;
	private ObservableList<Technique> techniques;
	
	private ArrayList<Matiere> matieresUtilisees;
	private ArrayList<Technique> techniquesUtilisees;
	
	private Matiere matiere;
	private Technique technique;
	
	private Matiere matiereSelectionne;
	private Technique techniqueSelectionne;
	
	
	private ObservableList<EtatFinal> etatsFinaux;
	
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

	
	public void reloadOeuvre(){
        
		oeuvreTraiteeSelectionne = (OeuvreTraitee) tableOeuvre.getSelectionModel().getSelectedItem();
		oeuvreSelectionne = oeuvreTraiteeSelectionne.getOeuvre();
		
		matieresUtilisees = oeuvreSelectionne.getMatieresUtilisees();
		techniquesUtilisees = oeuvreSelectionne.getTechniquesUtilisees();
		
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
		degradations_textArea.setText(oeuvreTraiteeSelectionne.getAlterations().stream()
				                                                               .map(o -> o.replace("oui/non", ""))
				                                                               .collect(Collectors.joining("\n")));
		observations_textArea.setText(oeuvreTraiteeSelectionne.getObservations());
		remarques_textArea.setText(oeuvreTraiteeSelectionne.getRemarques());
		
		matieres_hbox.getChildren().clear();
		techniques_hbox.getChildren().clear();
		
		if (oeuvreSelectionne.getMatieresUtilisees() != null){
			for (Matiere m : oeuvreSelectionne.getMatieresUtilisees()){
				matiereSelectionne = m;
				affichageMatieresUtilises();
			}
		}
		
		if (oeuvreSelectionne.getTechniquesUtilisees() != null){
			for (Technique t : oeuvreSelectionne.getTechniquesUtilisees()){
				techniqueSelectionne = t;
				affichageTechniquesUtilises();
			}
		}
		
		Fichier fichierSelectionne = MongoAccess.request("fichier", "fichierLie", oeuvreSelectionne.getCote_archives_6s(), true).as(Fichier.class);
		
		System.out.println(fichierSelectionne.getFichierLie().toString());
		
		preview_imageView.setImage(new Image(String.format("file:%s" ,fichierSelectionne.getFichierLie().toString())));
		
		etat_final_choiceBox.getSelectionModel().select(oeuvreTraiteeSelectionne.getEtat());
		complement_etat_textArea.setText(oeuvreTraiteeSelectionne.getComplement_etat());
		
		editer.setVisible(true);
        mise_a_jour_oeuvre.setVisible(false);
		annuler.setVisible(false);

		afficherAuteurs();
		afficherTraitements();
		afficherFichiers();
		
	}
	
	public void afficherTraitements(){
		
		traitementsSupplementaires.clear();
		traitementsAttendus.clear();

		for (ObjectId tt_id : oeuvreTraiteeSelectionne.getTraitementsAttendus()){
			
			traitementsAttendus.add(MongoAccess.request("tacheTraitement", tt_id).as(TacheTraitement.class).next());
		}
		
		//finir la migration -> objectId
		
		
		
		traitements_attendus_tableColumn.setCellValueFactory(new PropertyValueFactory<TacheTraitement, String>("nom"));
		faits_attendus_tableColumn.setCellValueFactory(new PropertyValueFactory<TacheTraitement, ImageView>("icone_progression"));
		//faits_attendus_tableColumn.setCellValueFactory(new PropertyValueFactory<TacheTraitement, String>("fait"));
		traitements_attendus_tableView.setItems(traitementsAttendus);

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

    @FXML
    public void onEditerOeuvreButton(){
    	

    	annuler.setVisible(true);
    	editer.setVisible(false);
    	mise_a_jour_oeuvre.setVisible(true);
    	
    	numero_archive_6s_textField.setEditable(true);
		titre_textField.setEditable(true);
		date_oeuvre_textField.setEditable(true);
		dimensions_textField.setEditable(true);
		inscriptions_textArea.setEditable(true);
		degradations_textArea.setEditable(true);
		observations_textArea.setEditable(true);
		remarques_textArea.setEditable(true);

    }
//    
    @FXML
    public void onAnnulerEditButton(){
    	
    	annuler.setVisible(false);
    	editer.setVisible(true);
        mise_a_jour_oeuvre.setVisible(false);
    	
    	numero_archive_6s_textField.setEditable(false);
		titre_textField.setEditable(false);
		date_oeuvre_textField.setEditable(false);
		dimensions_textField.setEditable(false);
		inscriptions_textArea.setEditable(false);
		degradations_textArea.setEditable(false);
		observations_textArea.setEditable(false);
		remarques_textArea.setEditable(false);
		
		reloadOeuvre();
    	
    }
    
    @FXML
    public void onMiseAJourOeuvreButton(){

    	oeuvreTraiteeSelectionne.setCote_archives_6s(numero_archive_6s_textField.getText());
    	oeuvreTraiteeSelectionne.setTitre_de_l_oeuvre(titre_textField.getText());
    	oeuvreTraiteeSelectionne.setDate(date_oeuvre_textField.getText());
    	oeuvreTraiteeSelectionne.setDimensions(dimensions_textField.getText());
    	oeuvreTraiteeSelectionne.setInscriptions_au_verso(inscriptions_textArea.getText());
    	oeuvreTraiteeSelectionne.setAlterations(new ArrayList(Arrays.asList(degradations_textArea.getText().split(System.getProperty("line.separator")))));
    	oeuvreTraiteeSelectionne.setObservations(observations_textArea.getText());
    	oeuvreTraiteeSelectionne.setRemarques(remarques_textArea.getText());
    	oeuvreTraiteeSelectionne.setEtat(etat_final_choiceBox.getSelectionModel().getSelectedItem());
    	oeuvreTraiteeSelectionne.setComplement_etat(complement_etat_textArea.getText());

		OeuvreTraitee.update(oeuvreTraiteeSelectionne);
		
		onAnnulerEditButton();

    	
    }


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
    
    public void afficherTechniques(){
    	
       techniques.clear();
    	
       techniquesCursor = MongoAccess.request("technique").as(Technique.class);
		
		while (techniquesCursor.hasNext()){
			techniques.add(techniquesCursor.next());
		}
		techniques_listView.setItems(techniques);
//		
//		tableOeuvre.getSelectionModel().clearAndSelect(Main_BEA_BAZ.getOeuvre_index());
//		tableOeuvre.getSelectionModel().focus(Main_BEA_BAZ.getOeuvre_index());
		
	}
    
   public void afficherMatieres(){
   	
       matieres.clear();
    	
       matieresCursor = MongoAccess.request("matiere").as(Matiere.class);
		
		while (matieresCursor.hasNext()){
			matieres.add(matieresCursor.next());
		}
		matieres_listView.setItems(matieres);
//		
//		tableOeuvre.getSelectionModel().clearAndSelect(Main_BEA_BAZ.getOeuvre_index());
//		tableOeuvre.getSelectionModel().focus(Main_BEA_BAZ.getOeuvre_index());
		
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
    
    @FXML
	public void onMatiereSelect(){
		
		matiereSelectionne = matieres_listView.getSelectionModel().getSelectedItem();
	
		oeuvreSelectionne.addMatiere(matiereSelectionne);
		oeuvreTraiteeSelectionne.addMatiere(matiereSelectionne);

		MongoAccess.update("oeuvre", oeuvreSelectionne);
		MongoAccess.update("oeuvreTraitee", oeuvreTraiteeSelectionne);

		matieres_hbox.getChildren().clear();
		
		for (Matiere m : oeuvreSelectionne.getMatieresUtilisees()){
			matiereSelectionne = m;
			affichageMatieresUtilises();
		}
		
		
	}
    
    @FXML
	public void onTechniqueSelect(){
		
        techniqueSelectionne = techniques_listView.getSelectionModel().getSelectedItem();
			
		oeuvreSelectionne.addTechnique(techniqueSelectionne);
		oeuvreTraiteeSelectionne.addTechnique(techniqueSelectionne);

		MongoAccess.update("oeuvre", oeuvreSelectionne);
		MongoAccess.update("oeuvreTraitee", oeuvreTraiteeSelectionne);
		
		techniques_hbox.getChildren().clear();
		
		for (Technique t : oeuvreSelectionne.getTechniquesUtilisees()){
			techniqueSelectionne = t;
			affichageTechniquesUtilises();
		}
		
	}
    
    public void affichageMatieresUtilises(){

		ImageView iv = new ImageView(new Image(Progression.NULL_.getUsedImage()));
		iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);
        iv.setFitWidth(15);
		
		
		Button b = new Button(matiereSelectionne.getNom());
		Button b2 = new Button("", iv);
		
		b2.setOnAction((event) -> deleteMatiereLie((Button)event.getSource()));

		matieres_hbox.getChildren().add(b);
		matieres_hbox.getChildren().add(b2);
		HBox.setMargin(b2, new Insets(0,10,0,0));
	}
    
    public void deleteMatiereLie(Button e){
		
		int index = matieres_hbox.getChildren().indexOf(e);

		Matiere matiere = MongoAccess.request("matiere", "nom",  ((Button) matieres_hbox.getChildren().get(index -1)).getText()).as(Matiere.class);
		
		matieres_hbox.getChildren().remove(index, index +1);
		
		oeuvreSelectionne.deleteMatiere(matiere);
		oeuvreTraiteeSelectionne.deleteMatiere(matiere);
		
		OeuvreTraitee.update(oeuvreTraiteeSelectionne);
		Oeuvre.update(oeuvreSelectionne);
		
		matieres_hbox.getChildren().clear();
		for (Matiere m : oeuvreSelectionne.getMatieresUtilisees()){
			matiereSelectionne = m;
			affichageMatieresUtilises();
			
		}
		//affichageMatieresUtilisees();
	}

    // pareil pour technique (deleteTechnique liee(), deleteTechnique, getTechniques, affichageTechniqueUtilisées)
    
    public void deleteTechniqueLie(Button e){
		
		int index = techniques_hbox.getChildren().indexOf(e);

		Technique technique = MongoAccess.request("technique", "nom",  ((Button) techniques_hbox.getChildren().get(index -1)).getText()).as(Technique.class);
		
		techniques_hbox.getChildren().remove(index -1, index +1);
		
		oeuvreSelectionne.deleteTechnique(technique);
		oeuvreTraiteeSelectionne.deleteTechnique(technique);
		
		OeuvreTraitee.update(oeuvreTraiteeSelectionne);
		Oeuvre.update(oeuvreSelectionne);
		
		techniques_hbox.getChildren().clear();
		for (Technique t : oeuvreSelectionne.getTechniquesUtilisees()){
			techniqueSelectionne = t;
			affichageTechniquesUtilises();	
		}
		//
	}

    public void affichageTechniquesUtilises(){

		ImageView iv = new ImageView(new Image(Progression.NULL_.getUsedImage()));
		iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);
        iv.setFitWidth(15);
		
		
		Button b = new Button(techniqueSelectionne.getNom());
		Button b2 = new Button("", iv);
		
		b2.setOnAction((event) -> deleteTechniqueLie((Button)event.getSource()));

		techniques_hbox.getChildren().add(b);
		techniques_hbox.getChildren().add(b2);
		HBox.setMargin(b2, new Insets(0,10,0,0));
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
		degradations_textArea.setEditable(false);
		observations_textArea.setEditable(false);
		remarques_textArea.setEditable(false);
		
		complement_etat_textArea.textProperty().addListener((observable, oldValue, newValue) -> {
			onEditerOeuvreButton();
		});

		numero_archive_6s_textField.setText(oeuvreSelectionne.getCote_archives_6s());
		titre_textField.setText(oeuvreSelectionne.getTitre_de_l_oeuvre());
		date_oeuvre_textField.setText(oeuvreSelectionne.getDate());
		dimensions_textField.setText(oeuvreSelectionne.getDimensions());
		inscriptions_textArea.setText(oeuvreSelectionne.getInscriptions_au_verso());
		degradations_textArea.setText(oeuvreSelectionne.get_observations());

        editer.setVisible(true);
        mise_a_jour_oeuvre.setVisible(false);
		annuler.setVisible(false);

		versOeuvreButton.setVisible(false);
		versRapportButton.setVisible(false);
		
		matieres = FXCollections.observableArrayList();
		techniques = FXCollections.observableArrayList();
	
		etatsFinaux = FXCollections.observableArrayList(EtatFinal.values());
		etat_final_choiceBox.setItems(etatsFinaux);
	
		traitementsAttendus = FXCollections.observableArrayList();
		traitementsSupplementaires = FXCollections.observableArrayList();
		observableFichiers = FXCollections.observableArrayList();
		
		oeuvresTraitees = new ArrayList<OeuvreTraitee>();

		
		currentStage = Main_BEA_BAZ.getStage();
        
		afficherFichiers();
		afficherOeuvres();
		afficherMatieres();
		afficherTechniques();
		reloadOeuvre();

	}
}
