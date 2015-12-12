package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.bson.types.ObjectId;
import org.jongo.MongoCursor;

import utils.MongoAccess;
import models.Auteur;
import models.Client;
import models.Commande;
import models.Messages;
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
	private ObservableList<String> liste_clients;
	@FXML
	private ObservableList<String> liste_commandes;
	
	@FXML
	private ListView<String> listView_client;
	@FXML
	private ListView<String> listView_commandes;
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
	String clientSelectionne;
	String commandeSelectionne;
	
	Map <String, ObjectId> clients_id;
	Map <String, ObjectId> commandes_id;
	
	Stage currentStage;
	
	Commande commande;
	String client;
	Client c;
	
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
	public void onAjoutCommande(){
		
		Messages.setCommande(null);
		Messages.setClient(clientSelectionne);
		Messages.setClient_id(clients_id.get(clientSelectionne));
		Messages.setClients_id(clients_id);

		Scene fiche_commande_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_commande.fxml"), 1275, 722);
		fiche_commande_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_commande_scene);
		

	}

	@FXML
	public void onClientSelect(){

		clientSelectionne = listView_client.getSelectionModel().getSelectedItem();
		Messages.setClient(clientSelectionne);
		Messages.setClient_id(clients_id.get(clientSelectionne));
		
		Messages.setAuteur(null);
		Messages.setCommande(null);
		Messages.setCommandes_id(null);
		Messages.setFichier(null);
		Messages.setFichiers_id(null);
		Messages.setModel(null);
		Messages.setObservablOeuvresTraitees(null);
		Messages.setOeuvre(null);
		Messages.setOeuvre_index(0);
		Messages.setOeuvresTraitees_id(null);
		Messages.setOeuvreTraitee(null);
		Messages.setTacheTraitement(null);
		Messages.setTacheTraitements_id(null);
		Messages.setTraitementsAttendus(null);
		Messages.setTraitementsAttendus_id(null);
		
		affichageInfos();
		
	}
	
	@FXML
	public void onCommandeSelect(){
		
		commandeSelectionne = listView_commandes.getSelectionModel().getSelectedItem();
		Messages.setCommande(commandeSelectionne);
		Messages.setCommande_id(commandes_id.get(commandeSelectionne));
		
		Messages.setAuteur(null);
		Messages.setFichier(null);
		Messages.setFichiers_id(null);
		Messages.setModel(null);
		Messages.setObservablOeuvresTraitees(null);
		Messages.setOeuvre(null);
		Messages.setOeuvre_index(0);
		Messages.setOeuvresTraitees_id(null);
		Messages.setOeuvreTraitee(null);
		Messages.setTacheTraitement(null);
		Messages.setTacheTraitements_id(null);
		Messages.setTraitementsAttendus(null);
		Messages.setTraitementsAttendus_id(null);
		
		Scene fiche_commande_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_commande.fxml"), 1275, 722);
		fiche_commande_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		currentStage.setScene(fiche_commande_scene);
		
		
	}
	
    private void affichageInfos(){
    	
    	liste_commandes.clear();
    	
    	if (Messages.getClient() != null){
    		
    		c = MongoAccess.request("client", clients_id.get(clientSelectionne)).as(Client.class).next();
    		
    		nom_client_textField.setText(clientSelectionne);
    		nom_complet_client_textField.setText(c.getNom_complet());
        	adresse_voie_textField.setText(c.getAdresse_rue());
        	adresse_cp_textField.setText(c.getAdresse_cp());
        	adresse_ville_textField.setText(c.getAdresse_ville());
        	remarques_client_textArea.setText(c.getRemarques());
    		
        	commandes_id = c.getCommandes_id(); 
    		liste_commandes.addAll(commandes_id.keySet());
    		
    		listView_commandes.setItems(liste_commandes);
    		
    	}
    	
    	
    }
    
    public void onNouveauClientButton() {
    	
    	mise_a_jour_client.setText("Enregistrer");
    	nom_client_textField.setText("");
    	nom_complet_client_textField.setText("");
    	adresse_voie_textField.setText("");
    	adresse_cp_textField.setText("");
    	adresse_ville_textField.setText("");
    	remarques_client_textArea.setText("");
    	
    	nom_client_textField.setPromptText("saisir le nom affiché dans les menus de l'interface");
    	nom_complet_client_textField.setPromptText("saisir le nom complet pour l'export");
    	adresse_voie_textField.setPromptText("n° dans le voie et nom de la voie");
    	adresse_cp_textField.setPromptText("code postal");
    	adresse_ville_textField.setPromptText("ville");
    	remarques_client_textArea.setPromptText("éventuelles remarques");
    	nouveau_client.setVisible(false);
    	
    	edit = false;
    	annuler.setVisible(true);
    	editer.setVisible(false);
    	mise_a_jour_client.setVisible(true);
    	nom_client_textField.setEditable(true);
    	nom_complet_client_textField.setEditable(true);
    	adresse_voie_textField.setEditable(true);
    	adresse_cp_textField.setEditable(true);
    	adresse_ville_textField.setEditable(true);
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
		clients_id.clear();

		clientCursor = MongoAccess.request("client").as(Client.class);
		
		while (clientCursor.hasNext()){
			
			Client c = clientCursor.next();
			
			liste_clients.add(c.getNom());
			clients_id.put(c.getNom(), c.get_id());
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
    	
    	if (! edit){
    		c = new Client();
    	}
    	else {
    		c = MongoAccess.request("client", clients_id.get(clientSelectionne)).as(Client.class).next();
    	}
    	
    	c.setNom(nom_client_textField.getText());
    	c.setRemarques(remarques_client_textArea.getText());
    	c.setNom_complet(nom_complet_client_textField.getText());
    	c.setAdresse_rue(adresse_voie_textField.getText());
    	c.setAdresse_cp(adresse_cp_textField.getText());
    	c.setAdresse_ville(adresse_ville_textField.getText());
    	
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
			
			Client.update(c);
			rafraichirAffichage();
			onAnnulerEditButton();
		}
		else {
			
		   Client.save(c);
		   onAnnulerEditButton();
		}
    	
    }
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		currentStage = Messages.getStage();
		clientSelectionne = Messages.getClient();
		Messages.setCommande(null);
		
		liste_clients = FXCollections.observableArrayList();
		liste_commandes  = FXCollections.observableArrayList();
		
		clients_id = Messages.getClients_id();
		
		commandes_id = Messages.getCommandes_id();

		if (clients_id == null){
			
			clients_id = new TreeMap<>();
			
			ArrayList<Client> clients = new ArrayList<>();
			clientCursor = MongoAccess.request("client").as(Client.class);
			
			int indexClient = 0;
			int iClient = 0;
			
			while (clientCursor.hasNext()){
				
				Client c = clientCursor.next();
				
				liste_clients.add(c.getNom());
				clients_id.put(c.getNom(), c.get_id());
				if (client != null && c.getNom().equals(client)){
					indexClient = iClient;
				}
				iClient ++;	
			}
			
			Messages.setClients_id(clients_id);
		}
		else {
			liste_clients.addAll(Messages.getClients_id().keySet());
		}
		
		listView_client.setItems(liste_clients);
		
		if (client != null) {
			listView_client.getSelectionModel().select(liste_clients.indexOf(client));
		}
		else if (liste_clients.size() > 0){
			listView_client.getSelectionModel().select(0);
		}
		
		
        if (commandes_id == null){
        	
        	if(Messages.getClient_id() != null){
        	    commandes_id = MongoAccess.request("client", Messages.getClient_id()).as(Client.class).next().getCommandes_id();    
        	}
			
        	Messages.setCommandes_id(commandes_id);
			
		}
		else {
			liste_commandes.addAll(Messages.getCommandes_id().keySet());
		}

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

		if (clientSelectionne == null){
			listView_client.getSelectionModel().select(0);
			clientSelectionne = listView_client.getSelectionModel().getSelectedItem();
			
			Messages.setClient(clientSelectionne);
			Messages.setClient_id(clients_id.get(clientSelectionne));

		}

		affichageInfos();


	}

}
