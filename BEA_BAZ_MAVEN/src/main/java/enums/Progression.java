package enums;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum Progression {
	
	FAIT_ ("/home/kaplone/git/BEA_BAZ/BEA_BAZ_MAVEN/src/main/resources/images/coche_ok.png"),
	TODO_ ("/home/kaplone/git/BEA_BAZ/BEA_BAZ_MAVEN/src/main/resources/images/coche_todo.png"),
	NULL_ ("/home/kaplone/git/BEA_BAZ/BEA_BAZ_MAVEN/src/main/resources/images/coche_null.png");
	
    private ImageView usedImage;
	
	Progression(String i) {
		
		File f = new File(i);
        Image image = new Image(f.toURI().toString());
        
        usedImage = new ImageView();
        usedImage.setFitHeight(15);
        usedImage.setPreserveRatio(true);
        usedImage.setImage(image);
	}
	
	public ImageView getUsedImage() {

		return usedImage;
	}

}
