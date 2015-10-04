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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

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
	
	MongoCursor<Client> clientCursor;
	Client clientSelectionne;
	

	@FXML
	public Commande ajoutCommande(){
		
		Commande c = new Commande();
		
		return c;
		
	}

	@FXML
	public void onClientSelect(){
		
		clientSelectionne = listView_client.getSelectionModel().getSelectedItem();
		affichageInfos(clientSelectionne);
	}
	
    private void affichageInfos(Client clientSelectionne){

    	
    	nom_client.setText(clientSelectionne.getNom());
    	remarques_client.setText(clientSelectionne.getRemarques());
    	
    }
    
    public void onNouveauClientButton() {
    	
    	mise_a_jour_client.setText("Enregistrer");
    	nom_client.setPromptText("saisir le nom du nouveau client");
    	remarques_client.setPromptText("éventuelles remarques");
    	nouveau_client.setText("Annuler");
    	
    	mise_a_jour_client.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Client.save(new Client(nom_client.getText(), remarques_client.getText()));
				rafraichirAffichage();
				onAnnulerButton();
			}
		});
    	
        nouveau_client.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				onAnnulerButton();
			}
		});
    }
    
    public void onAnnulerButton() {
    	
    	mise_a_jour_client.setText("Mise à jour");
    	nom_client.setText("");
    	remarques_client.setText("");
    	nom_client.setPromptText("");
    	remarques_client.setPromptText("");
    	nouveau_client.setText("Nouveau client");
    	
    	nouveau_client.setOnAction(new EventHandler<ActionEvent>() {
			
    		@Override
			public void handle(ActionEvent event) {
				onNouveauClientButton();
			}
		});
    	
        mise_a_jour_client.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				rafraichirAffichage();
			}
		});
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
    	
    }
    
    @FXML
    public void onMiseAJourClientButton(){
    	
    	clientSelectionne.setNom(nom_client.getText());
    	clientSelectionne.setRemarques(remarques_client.getText());
    	
    	Client.update(clientSelectionne);
    	rafraichirAffichage();
    	
    	annuler.setVisible(false);
    	editer.setVisible(true);
    	mise_a_jour_client.setVisible(false);
    	nom_client.setEditable(false);
		remarques_client.setEditable(false);
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		utils.MongoAccess.connect();
		
		nom_client.setEditable(false);
		remarques_client.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_client.setVisible(false);
		annuler.setVisible(false);
		
		liste_clients = FXCollections.observableArrayList();
		liste_commandes  = FXCollections.observableArrayList();
		
		
		
		clientCursor = MongoAccess.request("client").as(Client.class);
		
		while (clientCursor.hasNext()){
			liste_clients.add(clientCursor.next());
		}
		
		listView_client.setItems(liste_clients);
		
		
	
	}

}
