package application;

import java.net.URL;
import java.util.ResourceBundle;

import org.jongo.MongoCursor;

import utils.MongoAccess;
import models.Auteur;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Fiche_client_controller  implements Initializable{
	
	@FXML
	private ObservableList<Client> liste_clients;
	@FXML
	private ObservableList<Commande> liste_commandes;
	
	@FXML
	private ListView<Client> listView_client;
	@FXML
	private ListView<Commande> listView_commandes;
	@FXML
	private TextField nom_client_textField;
	@FXML
	private TextField nom_complet_client_textField;
	@FXML
	private TextField adresse_voie_textField;
	@FXML
	private TextField adresse_cp_textField;
	@FXML
	private TextField adresse_ville_textField;
	@FXML
	private TextArea remarques_client_textArea;
	@FXML
	private Button nouveau_client;
	@FXML
	private Button nouvelle_commande;
	@FXML
	private Button mise_a_jour_client;
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
	private Button versModelesButton;
	
	MongoCursor<Client> clientCursor;
	MongoCursor<Commande> commandeCursor ;
	Client clientSelectionne;
	Commande commandeSelectionne;
	
	Stage currentStage;
	
	Commande commande;
	Client client;
	
	private boolean edit = false;
	
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
	public void onAjoutCommande(){
		
		Main_BEA_BAZ.setCommande(null);
		Main_BEA_BAZ.setClient(clientSelectionne);
		
		Scene fiche_commande_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_commande.fxml"), 1275, 722);
		fiche_commande_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_commande_scene);
		

	}

	@FXML
	public void onClientSelect(){
		
		clientSelectionne = listView_client.getSelectionModel().getSelectedItem();
		Main_BEA_BAZ.setClient(clientSelectionne);
		affichageInfos();
		
	}
	
	@FXML
	public void onCommandeSelect(){
		
		commandeSelectionne = listView_commandes.getSelectionModel().getSelectedItem();
		Main_BEA_BAZ.setCommande(commandeSelectionne);
		
		Scene fiche_commande_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_commande.fxml"), 1275, 722);
		fiche_commande_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		currentStage.setScene(fiche_commande_scene);
		
		
	}
	
    private void affichageInfos(){

    	
    	nom_client_textField.setText(clientSelectionne.getNom());
    	nom_complet_client_textField.setText(clientSelectionne.getNom_complet());
    	adresse_voie_textField.setText(clientSelectionne.getAdresse_rue());
    	adresse_cp_textField.setText(clientSelectionne.getAdresse_cp());
    	adresse_ville_textField.setText(clientSelectionne.getAdresse_ville());
    	remarques_client_textArea.setText(clientSelectionne.getRemarques());
    	
    	liste_commandes.clear();
    	
    	if (clientSelectionne != null){
    		
    		commandeCursor = MongoAccess.request("commande", clientSelectionne).as(Commande.class);
    		
    		while (commandeCursor.hasNext()){
    			Commande enplus = commandeCursor.next();
    			
    			System.out.println(enplus);
    			System.out.println("_" + enplus.getDateCommande());
    			liste_commandes.add(enplus);
    		}
    		
    		listView_commandes.setItems(liste_commandes);
    		
    	}
    	
    	
    }
    
    public void onNouveauClientButton() {
    	
    	mise_a_jour_client.setText("Enregistrer");
    	nom_client_textField.setText("");
    	remarques_client_textArea.setText("");
    	nom_client_textField.setPromptText("saisir le nom du nouveau client");
    	remarques_client_textArea.setPromptText("éventuelles remarques");
    	nouveau_client.setVisible(false);
    	
    	clientSelectionne = new Client();
    	
    	edit = false;
    	annuler.setVisible(true);
    	editer.setVisible(false);
    	mise_a_jour_client.setVisible(true);
    	nom_client_textField.setEditable(true);
		remarques_client_textArea.setEditable(true);
    }
    
    public void onAnnulerButton() {
    	
    	mise_a_jour_client.setText("Mise à jour");
    	nom_client_textField.setText("");
    	remarques_client_textArea.setText("");
    	nom_client_textField.setPromptText("");
    	remarques_client_textArea.setPromptText("");
    	nouveau_client.setText("Nouveau client");
    	rafraichirAffichage();
    	listView_client.getSelectionModel().select(clientSelectionne);
    	affichageInfos();
    }
    
    public void rafraichirAffichage(){
    	
    	liste_clients = FXCollections.observableArrayList();
		liste_commandes  = FXCollections.observableArrayList();
		
		
		
		clientCursor = MongoAccess.request("client").as(Client.class);
		
		while (clientCursor.hasNext()){
			liste_clients.add(clientCursor.next());
		}
		
		listView_client.setItems(liste_clients);
    	
    }
    
    @FXML
    public void onEditerClientButton(){
    	

    	annuler.setVisible(true);
    	editer.setVisible(false);
    	mise_a_jour_client.setVisible(true);
    	nom_client_textField.setEditable(true);
    	nom_complet_client_textField.setEditable(true);
    	adresse_voie_textField.setEditable(true);
    	adresse_cp_textField.setEditable(true);
    	adresse_ville_textField.setEditable(true);
		remarques_client_textArea.setEditable(true);
		
		
		edit = true;

	
    }
    
    @FXML
    public void onAnnulerEditButton(){
    	
    	annuler.setVisible(false);
    	editer.setVisible(true);
    	mise_a_jour_client.setVisible(false);
    	nom_client_textField.setEditable(false);
    	nom_complet_client_textField.setEditable(false);
    	adresse_voie_textField.setEditable(false);
    	adresse_cp_textField.setEditable(false);
    	adresse_ville_textField.setEditable(false);
		remarques_client_textArea.setEditable(false);
		nouveau_client.setVisible(true);
		rafraichirAffichage();
		listView_client.getSelectionModel().select(clientSelectionne);
    	affichageInfos();
    	
    	edit = false;
    	
    }
    
    @FXML
    public void onMiseAJourClientButton(){
    	
    	if (clientSelectionne == null){
    		clientSelectionne = new Client();
    	}
    	
    	clientSelectionne.setNom(nom_client_textField.getText());
    	clientSelectionne.setRemarques(remarques_client_textArea.getText());
    	clientSelectionne.setNom_complet(nom_complet_client_textField.getText());
    	clientSelectionne.setAdresse_rue(adresse_voie_textField.getText());
    	clientSelectionne.setAdresse_cp(adresse_cp_textField.getText());
    	clientSelectionne.setAdresse_ville(adresse_ville_textField.getText());
    	
    	annuler.setVisible(false);
    	editer.setVisible(true);
    	mise_a_jour_client.setVisible(false);
    	nom_client_textField.setEditable(false);
    	nom_complet_client_textField.setEditable(false);
    	adresse_voie_textField.setEditable(false);
    	adresse_cp_textField.setEditable(false);
    	adresse_ville_textField.setEditable(false);
		remarques_client_textArea.setEditable(false);
		
		if (edit) {
			Client.update(clientSelectionne);
			//afficherClient();
			rafraichirAffichage();
			onAnnulerEditButton();
		}
		else {
			
			System.out.println(clientSelectionne);
			
		   Client.save(clientSelectionne);
		   //afficherClient();
		   onAnnulerEditButton();
		}
    	
    }
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		commande = Main_BEA_BAZ.getCommande();
		client = Main_BEA_BAZ.getClient();
		
		Main_BEA_BAZ.setAuteur(null);

		nom_client_textField.setEditable(false);
		nom_complet_client_textField.setEditable(false);
    	adresse_voie_textField.setEditable(false);
    	adresse_cp_textField.setEditable(false);
    	adresse_ville_textField.setEditable(false);
		remarques_client_textArea.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_client.setVisible(false);
		annuler.setVisible(false);
		
		versClientButton.setVisible(false);
		versCommandeButton.setVisible(false);
		versOeuvreButton.setVisible(false);
		versRapportButton.setVisible(false);
		
		liste_clients = FXCollections.observableArrayList();
		liste_commandes  = FXCollections.observableArrayList();
		
		currentStage = Main_BEA_BAZ.getStage();
		
		clientCursor = MongoAccess.request("client").as(Client.class);
		
		int indexClient = 0;
		int iClient = 0;
		
		while (clientCursor.hasNext()){
			
			Client client_ = clientCursor.next();
			liste_clients.add(client_);
			if (client != null && client_.getNom().equals(client.getNom())){
				indexClient = iClient;
			}
			iClient ++;
			
		}
		
		listView_client.setItems(liste_clients);
		listView_client.getSelectionModel().select(indexClient);
		
		
		if (client != null){
			
			commandeCursor = MongoAccess.request("commande", client).as(Commande.class);
			
			while (commandeCursor.hasNext()){
				Commande enplus = commandeCursor.next();
				liste_commandes.add(enplus);
			}
			
			listView_commandes.setItems(liste_commandes);
			
			clientSelectionne = client;

			affichageInfos();
			
		}
        
		
		
	
	}

}
