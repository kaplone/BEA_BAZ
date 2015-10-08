package application;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.jongo.MongoCursor;

import com.mongodb.DBPortPool.NoMoreConnection;

import utils.MongoAccess;
import models.Client;
import models.Commande;
import models.Oeuvre;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	private ListView<Oeuvre> listView_oeuvres;
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
	private Button versFichierButton;
	@FXML
	private Button versModeleButton;
	@FXML
	private Button versTraitementButton;
	@FXML
	private Button rapportsButton;
	@FXML
	private DatePicker dateCommandePicker;
	@FXML
	private DatePicker dateDebutProjetPicker;
	@FXML
	private DatePicker dateFinProjetPicker;
	
	@FXML
	private VBox commandeExportVbox;
	
	@FXML
	private GridPane traitementGrid;
	
	private ArrayList<ChoiceBox<Traitement>> traitements_selectionnes;
	private ArrayList<Traitement> traitements_attendus;
	private MongoCursor<TraitementsAttendus> traitementsCursor;
	private ObservableList<Traitement> observableTraitements;

	
	private MongoCursor<Oeuvre> oeuvresCursor;
	private Oeuvre oeuvreSelectionne;
	
	private Stage currentStage;
	
	private Commande commande;
	
	private Client client;
	
	
	private boolean edit = false;
	
	@FXML
	public void onVersClientButton(){
		
		Scene fiche_client_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_client.fxml"), 1275, 722);
		fiche_client_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_client_scene);	
	}
	
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
		versModeleButton.setVisible(false);
		versTraitementButton.setVisible(false);
		versFichierButton.setVisible(false);
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
		
		traitements_attendus.clear();
		
		commande = Main_BEA_BAZ.getCommande(); 
		commande.setClient(client.get_id());
		commande.setDateCommande(dateCommandePicker.getValue());
		commande.setDateDebutProjet(dateDebutProjetPicker.getValue());
		commande.setDateFinProjet(dateFinProjetPicker.getValue());
		commande.setRemarques(remarques_client.getText());
		commande.setNom(nomCommandeTextField.getText());
		for (Node cb : traitementGrid.getChildren()){
			
			ChoiceBox<Traitement> cbox = (ChoiceBox<Traitement>) cb;
			Traitement t = cbox.getValue();

			if (t != null){
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
		oeuvresCursor = MongoAccess.request("oeuvre", commande).as(Oeuvre.class);
		
		while (oeuvresCursor.hasNext()){
			liste_oeuvres.add(oeuvresCursor.next());
		}
		
		listView_oeuvres.setItems(liste_oeuvres);
        
        int i = 0;

		for (Traitement t : commande.getTraitements_attendus()){
			traitements_selectionnes.get(i).setItems(FXCollections.observableArrayList(commande.getTraitements_attendus()));;
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
    public void onVersRapportButton(){}
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
	
	public void loadCommande(Commande c){
		
		dateCommandePicker.setValue(c.getDateCommande());;
		dateDebutProjetPicker.setValue(c.getDateDebutProjet());;
		dateFinProjetPicker.setValue(c.getDateFinProjet());
		remarques_client.setText(c.getRemarques());
		nom_commande_label.setText(c.getNom());
		nomCommandeTextField.setText(c.getNom());
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		commande = Main_BEA_BAZ.getCommande();
		client = Main_BEA_BAZ.getClient();

		utils.MongoAccess.connect();

		versClientButton.setVisible(true);
		versCommandeButton.setVisible(false);
		versOeuvreButton.setVisible(false);
		versRapportButton.setVisible(false);
		
		currentStage = Main_BEA_BAZ.getStage();
		
		liste_oeuvres = FXCollections.observableArrayList();
		observableTraitements = FXCollections.observableArrayList();
		
		traitements_attendus = new ArrayList<>();
		traitements_selectionnes = new ArrayList<>();
		
		MongoCursor<Traitement> mgCursor = MongoAccess.request("traitement").as(Traitement.class);
		
		while (mgCursor.hasNext()){
			observableTraitements.addAll(mgCursor.next());
		}
		
		
		for (Node cb : traitementGrid.getChildren()){
			
			((ChoiceBox<Traitement>) cb).setItems(observableTraitements);
			traitements_selectionnes.add(((ChoiceBox<Traitement>) cb));
		}
        
		if (commande != null) {
			
			afficherCommande();
			
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
			versModeleButton.setVisible(false);
			versTraitementButton.setVisible(false);
			versFichierButton.setVisible(false);
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

}
