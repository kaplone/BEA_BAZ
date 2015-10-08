package application;

import java.net.URL;
import java.util.ResourceBundle;

import org.jongo.MongoCursor;

import utils.MongoAccess;
import models.Commande;
import models.Complement;
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
import javafx.stage.Stage;

public class Fiche_traitement_controller  implements Initializable{
	
	@FXML
	private ObservableList<Traitement> liste_traitements;
	@FXML
	private ObservableList<Complement> liste_details;
	
	@FXML
	private ListView<Traitement> listView_traitements;
	@FXML
	private ListView<Complement> listView_details;
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
	private TextArea remarques_traitement_textArea;
	
	private boolean edit = false;
	
	MongoCursor<Traitement> traitementCursor;
	MongoCursor<Complement> detailCursor ;
	Traitement traitementSelectionne;
	Complement detailSelectionne;
	
	Stage currentStage;

	Complement detail;
	

	@FXML
	public void onAjoutTraitement(){
	}

	@FXML
	public void onTraitementSelect(){
		
		traitementSelectionne = listView_traitements.getSelectionModel().getSelectedItem();
		affichageInfos(traitementSelectionne);	
	}
	
	@FXML
	public void onDetailSelect(){
		
		detailSelectionne = listView_details.getSelectionModel().getSelectedItem();
		Main_BEA_BAZ.setDetail(detailSelectionne);
		
		Scene fiche_detail_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_detail.fxml"), 1275, 722);
		fiche_detail_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		currentStage.setScene(fiche_detail_scene);	
	}
	
    private void affichageInfos(Traitement traitementSelectionne){

    	
    	nom_traitement_textField.setText(traitementSelectionne.getNom());
    	remarques_traitement_textArea.setText(traitementSelectionne.getRemarques());
    	
    	traitementSelectionne = Main_BEA_BAZ.getTraitement();
    	
    	liste_details.clear();
    	
    	if (traitementSelectionne != null){
    		
    		detailCursor = MongoAccess.request("detail", traitementSelectionne).as(Complement.class);
    		
    		while (detailCursor.hasNext()){
    			Complement enplus = detailCursor.next();
    			liste_details.add(enplus);
    		}	
    		listView_details.setItems(liste_details);		
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
    	affichageInfos(traitementSelectionne);
    	
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
    	affichageInfos(traitementSelectionne);
    	
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
    public void onVersClientButton(){}

    	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		detail = Main_BEA_BAZ.getDetail();
		traitementSelectionne = Main_BEA_BAZ.getTraitement();

		utils.MongoAccess.connect();
		
		nom_traitement_textField.setEditable(false);
		remarques_traitement_textArea.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_traitement.setVisible(false);
		annuler.setVisible(false);
		
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
			
			detailCursor = MongoAccess.request("detail", traitementSelectionne).as(Complement.class);
			
			while (detailCursor.hasNext()){
				Complement enplus = detailCursor.next();
				liste_details.add(enplus);
			}
			
			listView_details.setItems(liste_details);
		}
        
		
		
	
	}

}
