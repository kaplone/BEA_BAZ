package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.bson.types.ObjectId;
import org.jongo.MongoCursor;

import enums.Progression;
import fr.opensagres.xdocreport.template.velocity.internal.Foreach;
import utils.MongoAccess;
import models.Commande;
import models.OeuvreTraitee;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Fiche_tache_traitement_controller  implements Initializable{
	
	@FXML
	private ObservableList<TacheTraitement> liste_traitements;
	@FXML
	private ObservableList<Produit> liste_produits;
	@FXML
	private TableView<TacheTraitement> traitements_associes_tableView;
	@FXML
	private TableColumn<TacheTraitement, String> traitements_associes_tableColumn;
	@FXML
	private TableColumn<TacheTraitement, ImageView> traitements_associes_faits_tableColumn;
	@FXML
	private TextField file_path_textField;
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
	private Button versTraitementsButton;
	@FXML
	private Button versOeuvreButton;
	@FXML
	private Button versRapportButton;
	
	@FXML
	private Label fiche_traitement_label;
	@FXML
	private Label nom_traitement_label;
	@FXML
	private Label ot_label;
	@FXML
	private Label t_label;
	@FXML
	private Label commande_label;
	@FXML
	private TextArea remarques_traitement_textArea;
	
	@FXML
	private RadioButton fait_radio;
	@FXML
	private RadioButton todo_radio;
	@FXML
	private RadioButton so_radio;
	
	@FXML
	private ImageView coche_fait;
	@FXML
	private ImageView coche_todo;
	@FXML
	private ImageView coche_so;
	
	@FXML
	private Label produitUtiliseLabel;
	@FXML
	private Label complementTraitementLabel;
	
	private boolean edit = false;
	
	MongoCursor<TacheTraitement> traitementCursor;
	MongoCursor<Produit> detailCursor ;
	TacheTraitement traitementSelectionne;
	Produit produitSelectionne;
	
	ArrayList<TacheTraitement> liste_tachesTraitements;
	
	Progression progres;
	
	Stage currentStage;

	Produit detail;
	
	Traitement traitementSource;
	OeuvreTraitee ot;
	Commande commande;
	
	private File file;
	
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
    public void onVersFichiersButton(){}
    @FXML
    public void onVersModelesButton(){}
	
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
		
		traitementSelectionne = traitements_associes_tableView.getSelectionModel().getSelectedItem();
		Main_BEA_BAZ.setTacheTraitementEdited(traitementSelectionne);
		affichageInfos();	
	}
	
	@FXML
	public void onProduitSelect(){
		
		produitSelectionne = listView_produits.getSelectionModel().getSelectedItem();
		
		if (produitSelectionne != null){
			Main_BEA_BAZ.setDetail(produitSelectionne);
			traitementSelectionne.setProduitUtilise(produitSelectionne);
			traitementSelectionne.update(traitementSelectionne);
			afficherTraitement();
            
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
			Documents.read(file, "traitement");
			afficherTraitements();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
    private void affichageInfos(){

    	
    	//nom_traitement_textField.setText(traitementSelectionne.getNom());
    	nom_traitement_label.setText(traitementSelectionne.getNom());
    	//nom_complet_traitement_textField.setText(traitementSelectionne.getNom_complet());
    	remarques_traitement_textArea.setText(traitementSelectionne.getRemarques());
    	
    	liste_produits.clear();
    	
    	if (traitementSelectionne != null){
    		
            liste_produits.addAll(traitementSelectionne.getProduits());
			
			listView_produits.setItems(liste_produits);		
    	}	
    }
    
    public void onNouveauTraitementButton() {
    	
    	mise_a_jour_traitement.setText("Enregistrer");
    	//nom_traitement_textField.setText("");
    	remarques_traitement_textArea.setText("");
    	//nom_traitement_textField.setPromptText("saisir le nom du nouveau traitement");
    	remarques_traitement_textArea.setPromptText("éventuelles remarques");
    	nouveau_traitement.setVisible(false);
    	
    	traitementSelectionne = new TacheTraitement();
    	
    	edit = false;
    	annuler.setVisible(true);
    	editer.setVisible(false);
    	mise_a_jour_traitement.setVisible(true);
    	//nom_traitement_textField.setEditable(true);
		remarques_traitement_textArea.setEditable(true);
    	
    	
    }
    
    public void onAnnulerButton() {
    	
    	mise_a_jour_traitement.setText("Mise à jour");
    	//nom_traitement_textField.setText("");
    	remarques_traitement_textArea.setText("");
    	//nom_traitement_textField.setPromptText("");
    	remarques_traitement_textArea.setPromptText("");
    	nouveau_traitement.setText("Nouveau traitement");
    	rafraichirAffichage();
    	traitements_associes_tableView.getSelectionModel().select(traitementSelectionne);
    	affichageInfos();
    	
    }
    
    public void rafraichirAffichage(){
    	
    	nom_traitement_label.setText(traitementSelectionne.getNom());
    	//nom_traitement_textField.setText(traitementSelectionne.getNom_complet());
    	remarques_traitement_textArea.setText(traitementSelectionne.getRemarques());
    	
    }
    
    @FXML
    public void onEditerTraitementButton(){
    	

    	annuler.setVisible(true);
    	editer.setVisible(false);
    	mise_a_jour_traitement.setVisible(true);
    	//nom_traitement_textField.setEditable(true);
		remarques_traitement_textArea.setEditable(true);
		
		edit = true;

	
    }
    
    @FXML
    public void onAnnulerEditButton(){
    	
    	annuler.setVisible(false);
    	editer.setVisible(true);
    	mise_a_jour_traitement.setVisible(false);
    	//nom_traitement_textField.setEditable(false);
		remarques_traitement_textArea.setEditable(false);
		nouveau_traitement.setVisible(true);
		rafraichirAffichage();
		traitements_associes_tableView.getSelectionModel().select(traitementSelectionne);
    	affichageInfos();
    	
    	edit = false;
    	
    }
    
    @FXML
    public void onMiseAJourTraitementButton(){

    	if (traitementSelectionne == null) {
    		traitementSelectionne = new TacheTraitement();
    	}
    	
    	
    	
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
    	
    }
    
    public void afficherTraitements(){

		remarques_traitement_textArea.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_traitement.setVisible(false);
		annuler.setVisible(false);
		fiche_traitement_label.setText("FICHE TACHE TRAITEMENT :");
		//nom_traitement_textField.setDisable(true);
		remarques_traitement_textArea.setDisable(true);
		
        liste_traitements.clear();
    	
    	if (traitementSelectionne != null){
    		
    		traitementCursor = MongoAccess.request("tacheTraitement").as(TacheTraitement.class);
    		
    		while (traitementCursor.hasNext()){
    			TacheTraitement enplus = traitementCursor.next();
    			liste_traitements.add(enplus);
    		}	
    		traitements_associes_tableView.setItems(liste_traitements);	
    		
    		rafraichirAffichage();
    	}
		
		
    }
    
    @FXML
    public void onVersOeuvreButton(){
    	Scene fiche_oeuvre_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_oeuvre.fxml"), 1275, 722);
		fiche_oeuvre_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_oeuvre_scene);
    }
    
    @FXML
    public void onAjoutProduit(){
    	
    	//Main_BEA_BAZ.setTacheTraitementEdited(listView_traitements.getSelectionModel().getSelectedItem());
    	
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
    
    public void onFait_radio(){
    	
    	traitementSelectionne.setFait_(Progression.FAIT_);
    	MongoAccess.update("tacheTraitement", traitementSelectionne);
    	afficherTraitementsAssocies();
    	checkIfCompleted();
    }
    public void onTodo_radio(){
    	
    	traitementSelectionne.setFait_(Progression.TODO_);
    	MongoAccess.update("tacheTraitement", traitementSelectionne);
    	afficherTraitementsAssocies();
    	checkIfCompleted();
    }
    public void onSo_radio(){
    	
    	traitementSelectionne.setFait_(Progression.NULL_);
    	MongoAccess.update("tacheTraitement", traitementSelectionne);
    	afficherTraitementsAssocies();
    	checkIfCompleted();
    }
    
    public void checkIfCompleted(){

    	progres = Progression.FAIT_;
    	
    	for (TacheTraitement ttt : traitements_associes_tableView.getItems()){
    		
    		System.out.println("ttt : " + ttt.getFait_());
    		
    		if (ttt.getFait_().equals(Progression.TODO_)){
    			progres = Progression.TODO_;
    			break;
    		}
    		
    	}
    	
    	System.out.println("progres : " + progres);

    	ot.setProgressionOeuvreTraitee(progres);
    	MongoAccess.update("oeuvreTraitee", ot);
    }

    public void afficherTraitementsAssocies(){
    	
    	if (traitementSelectionne == null){
    		traitementSelectionne = Main_BEA_BAZ.getTacheTraitement();
    	}
    	
//    	liste_tachesTraitements.clear();
//    	
//        traitementCursor = MongoAccess.request("tacheTraitement", "oeuvreTraiteeId", ot.get_id()).as(TacheTraitement.class);
//        
//        int indexTraitementAssocie = 0;
//        int i = 0;
//		
//		while (traitementCursor.hasNext()){
//			
//			TacheTraitement tta = traitementCursor.next();
//			liste_tachesTraitements.add(tta);
//			
//			if (tta.getNom().equals(traitementSelectionne.getNom())){
//				indexTraitementAssocie = i;
//			}
//			i++;
//				
//		}
		
		traitements_associes_tableColumn.setCellValueFactory(new PropertyValueFactory<TacheTraitement, String>("nom"));
		traitements_associes_faits_tableColumn.setCellValueFactory(new PropertyValueFactory<TacheTraitement, ImageView>("icone_progression"));
		//oeuvres_fait_colonne.setCellValueFactory(new PropertyValueFactory<OeuvreTraitee, String>("fait"));
		
		//ObservableList<TacheTraitement> obs_tt = FXCollections.observableArrayList(liste_tachesTraitements);
		
		ObservableList<TacheTraitement> obs_tt = FXCollections.observableArrayList();
		
		for (ObjectId ta_id : ot.getTraitementsAttendus()){
			obs_tt.add(MongoAccess.request("tacheTraitement", ta_id).as(TacheTraitement.class).next());
		}
		

		traitements_associes_tableView.setItems(obs_tt);
		
		//traitements_associes_tableView.getSelectionModel().select(indexTraitementAssocie);
		traitements_associes_tableView.isFocused();
    }
    
    public void onTraitementAssocieSelect(){
    	
    	traitementSelectionne = traitements_associes_tableView.getSelectionModel().getSelectedItem();
    	Main_BEA_BAZ.setTacheTraitement(traitementSelectionne);
    	afficherTraitementsAssocies();
    	afficherProgression();
    	afficherTraitement();
    	afficherProduits();
    	
    	
    }
    
    public void afficherTraitement(){
    	
    	remarques_traitement_textArea.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_traitement.setVisible(false);
		annuler.setVisible(false);
		fiche_traitement_label.setText("FICHE TRAITEMENT :");
		//nom_traitement_textField.setDisable(true);
		remarques_traitement_textArea.setDisable(true);
		nom_traitement_label.setText(traitementSelectionne.getNom());
		
		System.out.println(traitementSelectionne);
		System.out.println(traitementSelectionne.getTraitement());
		System.out.println(traitementSelectionne.getTraitement().getNom_complet());
		
		t_label.setText(traitementSelectionne.getTraitement().getNom_complet());
		produitUtiliseLabel.setText(traitementSelectionne.getProduitUtilise() != null ? traitementSelectionne.getProduitUtilise().getNom_complet() : "Aucun produit utilisé");
		rafraichirAffichage();
    }
    
    public void afficherProgression(){
    	

		progres = traitementSelectionne.getFait_();
    	
        switch (progres){
		
		case TODO_ : todo_radio.setSelected(true);
		             break;
		case NULL_ : so_radio.setSelected(true);
                     break;
		case FAIT_ : fait_radio.setSelected(true);
                     break;
		}
		
    }
    
    public void afficherProduits(){
    	liste_produits.clear();
    	liste_produits.addAll(traitementSelectionne.getTraitement().getProduits());
    	listView_produits.setItems(liste_produits);
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		coche_fait.setImage(new Image(Progression.FAIT_.getUsedImage()));
		coche_todo.setImage(new Image(Progression.TODO_.getUsedImage()));
		coche_so.setImage(new Image(Progression.NULL_.getUsedImage()));
		
		traitementSelectionne = Main_BEA_BAZ.getTacheTraitementEdited();

		//nom_traitement_textField.setEditable(false);
		remarques_traitement_textArea.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_traitement.setVisible(false);
		annuler.setVisible(false);
		
		versCommandeButton.setVisible(true);
		versOeuvreButton.setVisible(true);
		versRapportButton.setVisible(false);
		
		versTraitementsButton.setVisible(true);
				
		liste_tachesTraitements = new ArrayList<>();
		
		ot = MongoAccess.request("oeuvreTraitee", traitementSelectionne.getOeuvreTraiteeId()).as(OeuvreTraitee.class).next();
		commande = ot.getCommande();
		traitementSource = traitementSelectionne.getTraitement();
		
		ot_label.setText(ot.getNom());
		t_label.setText(traitementSource.getNom());
		commande_label.setText(commande.getNom());
		
		liste_traitements = FXCollections.observableArrayList();
		liste_produits  = FXCollections.observableArrayList();
		
		
		currentStage = Main_BEA_BAZ.getStage();

		traitementCursor = MongoAccess.request("tacheTraitement").as(TacheTraitement.class);
		
		try {
		    produitUtiliseLabel.setText(traitementSelectionne.getProduitUtilise().getNom_complet());
		}
		catch (NullPointerException npe){
			
		}
		
		
	
		afficherProgression();
		afficherTraitementsAssocies();
		afficherProduits();
		rafraichirAffichage();
		
		
//		if (traitementSelectionne != null){
//			
//			detailCursor = MongoAccess.request("produit", traitementSelectionne).as(Produit.class);
//			
//			while (detailCursor.hasNext()){
//				Produit enplus = detailCursor.next();
//				liste_details.add(enplus);
//			}
//			
//			listView_produits.setItems(liste_details);
//		}
        
		
		
	
	}

}
