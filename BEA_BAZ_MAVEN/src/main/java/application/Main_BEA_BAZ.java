package application;
	
import models.Auteur;
import models.Client;
import models.Commande;
import models.Model;
import models.Oeuvre;
import models.OeuvreTraitee;
import models.Produit;
import models.Traitement;
import application.JfxUtils;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Main_BEA_BAZ extends Application {
	
	private static Stage exportStage;
	
	private static Commande commande;
	
	private static Client client;
	
	private static Traitement traitement;
	private static Traitement traitementEdited;
	
	private static Produit detail;
	
	private static Auteur auteur;
	
	private static Model model;
	
	private static OeuvreTraitee oeuvre;
	private static int oeuvre_index;
	
	

	@Override
	public void start(Stage primaryStage) {
		
		String cheminMongod = "C:\\Program Files\\MongoDB\\Server\\3.0\\bin\\mongod.exe";
		String cheminMongo = "C:\\Program Files\\MongoDB\\Server\\3.0\\bin\\mongo.exe";
		
		utils.MongoAccess.connect();
		
		exportStage = primaryStage;
		try {
			Pane root = new Pane();
			
			Scene fiche_client_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_client.fxml"), 1275, 722);
			fiche_client_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(fiche_client_scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Stage getStage(){
		return exportStage;
	}
	
	
	public static Commande getCommande() {
		return commande;
	}

	public static void setCommande(Commande commande) {
		Main_BEA_BAZ.commande = commande;
	}
	
	public static Client getClient() {
		return client;
	}

	public static void setClient(Client client) {
		Main_BEA_BAZ.client = client;
	}

	public static void main(String[] args) {
		launch(args);

	}

	public static Traitement getTraitement() {
		return traitement;
	}

	public static void setTraitement(Traitement traitement) {
		Main_BEA_BAZ.traitement = traitement;
	}

	public static Produit getDetail() {
		return detail;
	}

	public static void setDetail(Produit detail) {
		Main_BEA_BAZ.detail = detail;
	}

	public static Traitement getTraitementEdited() {
		return traitementEdited;
	}

	public static void setTraitementEdited(Traitement traitementEdited) {
		Main_BEA_BAZ.traitementEdited = traitementEdited;
	}

	public static OeuvreTraitee getOeuvre() {
		return oeuvre;
	}

	public static void setOeuvre(OeuvreTraitee oeuvreTraitee) {
		Main_BEA_BAZ.oeuvre = oeuvreTraitee;
	}

	public static int getOeuvre_index() {
		return oeuvre_index;
	}

	public static void setOeuvre_index(int oeuvre_index) {
		Main_BEA_BAZ.oeuvre_index = oeuvre_index;
	}

	public static Auteur getAuteur() {
		return auteur;
	}

	public static void setAuteur(Auteur auteur) {
		Main_BEA_BAZ.auteur = auteur;
	}

	public static Model getModel() {
		return model;
	}

	public static void setModel(Model model) {
		Main_BEA_BAZ.model = model;
	}
    
	
	
}
