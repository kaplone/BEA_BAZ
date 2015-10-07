package application;

import java.net.URL;
import java.util.ResourceBundle;

import org.jongo.MongoCursor;

import utils.MongoAccess;
import models.Client;
import models.Commande;
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
	private TextField nom_client;
	@FXML
	private TextArea remarques_client;
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
	
	MongoCursor<Client> clientCursor;
	MongoCursor<Commande> commandeCursor ;
	Client clientSelectionne;
	Commande commandeSelectionne;
	
	Stage currentStage;
	
	Commande commande;
	Client client;
	
	@FXML
	public void onVersCommandeButton(){
		
		Scene fiche_commande_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_commande.fxml"), 1275, 722);
		fiche_commande_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(fiche_commande_scene);	
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
		affichageInfos(clientSelectionne);
		
	}
	
	@FXML
	public void onCommandeSelect(){
		
		commandeSelectionne = listView_commandes.getSelectionModel().getSelectedItem();
		Main_BEA_BAZ.setCommande(commandeSelectionne);
		
		Scene fiche_commande_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_commande.fxml"), 1275, 722);
		fiche_commande_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		currentStage.setScene(fiche_commande_scene);
		
		
	}
	
    private void affichageInfos(Client clientSelectionne){

    	
    	nom_client.setText(clientSelectionne.getNom());
    	remarques_client.setText(clientSelectionne.getRemarques());
    	
    	client = Main_BEA_BAZ.getClient();
    	
    	liste_commandes.clear();
    	
    	if (client != null){
    		
    		commandeCursor = MongoAccess.request("commande", client).as(Commande.class);
    		
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
    	nom_client.setText("");
    	remarques_client.setText("");
    	nom_client.setPromptText("saisir le nom du nouveau client");
    	remarques_client.setPromptText("éventuelles remarques");
    	nouveau_client.setVisible(false);
    	
    	clientSelectionne = new Client();
    	
    	onEditerClientButton();
    	
//    	mise_a_jour_client.setOnAction(new EventHandler<ActionEvent>() {
//			
//			@Override
//			public void handle(ActionEvent event) {
//				Client.save(new Client(nom_client.getText(), remarques_client.getText()));
//				rafraichirAffichage();
//				onAnnulerEditButton();
//			}
//		});
//    	
//        nouveau_client.setOnAction(new EventHandler<ActionEvent>() {
//			
//			@Override
//			public void handle(ActionEvent event) {
//				onAnnulerEditButton();
//			}
//		});
    }
    
    public void onAnnulerButton() {
    	
    	mise_a_jour_client.setText("Mise à jour");
    	nom_client.setText("");
    	remarques_client.setText("");
    	nom_client.setPromptText("");
    	remarques_client.setPromptText("");
    	nouveau_client.setText("Nouveau client");
    	rafraichirAffichage();
    	listView_client.getSelectionModel().select(clientSelectionne);
    	affichageInfos(clientSelectionne);
    	
//    	nouveau_client.setOnAction(new EventHandler<ActionEvent>() {
//			
//    		@Override
//			public void handle(ActionEvent event) {
//				onNouveauClientButton();
//			}
//		});
//    	
//        mise_a_jour_client.setOnAction(new EventHandler<ActionEvent>() {
//			
//			@Override
//			public void handle(ActionEvent event) {
//				rafraichirAffichage();
//			}
//		});
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
    	nom_client.setEditable(true);
		remarques_client.setEditable(true);

	
    }
    
    @FXML
    public void onAnnulerEditButton(){
    	
    	annuler.setVisible(false);
    	editer.setVisible(true);
    	mise_a_jour_client.setVisible(false);
    	nom_client.setEditable(false);
		remarques_client.setEditable(false);
		nouveau_client.setVisible(true);
		rafraichirAffichage();
		listView_client.getSelectionModel().select(clientSelectionne);
    	affichageInfos(clientSelectionne);
    	
    }
    
    @FXML
    public void onMiseAJourClientButton(){
    	
    	clientSelectionne.setNom(nom_client.getText());
    	clientSelectionne.setRemarques(remarques_client.getText());
    	
    	Client.update(clientSelectionne);
    	rafraichirAffichage();
		onAnnulerEditButton();
    	
    	annuler.setVisible(false);
    	editer.setVisible(true);
    	mise_a_jour_client.setVisible(false);
    	nom_client.setEditable(false);
		remarques_client.setEditable(false);
    	
    }
    
    @FXML
    public void onVersClientButton(){}
    @FXML
    public void onVersOeuvreButton(){}
    @FXML
    public void onVersRapportButton(){}
    @FXML
    public void onVersFichierButton(){}
    @FXML
    public void onVersTraitementButton(){}
    @FXML
    public void onVersModeleButton(){}
    	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		commande = Main_BEA_BAZ.getCommande();
		client = Main_BEA_BAZ.getClient();

		utils.MongoAccess.connect();
		
		nom_client.setEditable(false);
		remarques_client.setEditable(false);
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
		
		while (clientCursor.hasNext()){
			liste_clients.add(clientCursor.next());
		}
		
		listView_client.setItems(liste_clients);
		
		
		if (client != null){
			
			commandeCursor = MongoAccess.request("commande", client).as(Commande.class);
			
			while (commandeCursor.hasNext()){
				Commande enplus = commandeCursor.next();
				liste_commandes.add(enplus);
			}
			
			listView_commandes.setItems(liste_commandes);
		}
        
		
		
	
	}

}
