package application;

import java.net.URL;
import java.util.ResourceBundle;

import org.jongo.MongoCursor;

import utils.MongoAccess;
import models.Client;
import models.Commande;
import models.Oeuvre;
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

public class Fiche_commande_controller  implements Initializable{
	
	@FXML
	private ObservableList<Oeuvre> liste_oeuvres;
	
	@FXML
	private ListView<Oeuvre> listView_oeuvres;
	@FXML
	private TextField nom_client;
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
	
	MongoCursor<Oeuvre> oeuvresCursor;
	Oeuvre oeuvreSelectionne;
	
	Stage currentStage;
	

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		utils.MongoAccess.connect();
		
		nom_client.setEditable(false);
		remarques_client.setEditable(false);
        editer.setVisible(true);
        mise_a_jour_commande.setVisible(false);
		annuler.setVisible(false);
		
		liste_oeuvres = FXCollections.observableArrayList();

		oeuvresCursor = MongoAccess.request("oeuvre").as(Oeuvre.class);
		
		while (oeuvresCursor.hasNext()){
			liste_oeuvres.add(oeuvresCursor.next());
		}
		
		listView_oeuvres.setItems(liste_oeuvres);
		
		
	
	}

}
