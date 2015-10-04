package application;
	
import application.JfxUtils;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;




public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = new Pane();
			
			Scene fiche_client_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_client.fxml"), 1275, 722);
			fiche_client_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Scene fiche_commande_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_commande.fxml"), 1275, 722);
			fiche_commande_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Scene fiche_oeuvre_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_oeuvre.fxml"), 1275, 722);
			fiche_oeuvre_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Scene fiche_fichier_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_fichier.fxml"), 1275, 722);
			fiche_fichier_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(fiche_client_scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);

	}
}
