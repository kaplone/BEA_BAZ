package models;

import java.nio.file.Path;
import java.nio.file.Paths;


import utils.MongoAccess;

public class Model extends Commun{
	
    private String cheminVersModelSTR;
    
    public static void update(Model c){

		MongoAccess.update("model", c);
	}
	
    public static void save(Model c){
		
		MongoAccess.save("model", c);
		
	}

	public Path getCheminVersModel() {
		return Paths.get(cheminVersModelSTR);
	}
	public String getCheminVersModelSTR() {
		return cheminVersModelSTR;
	}

	public void setCheminVersModelSTR(String cheminVersModelSTR) {
		this.cheminVersModelSTR = cheminVersModelSTR;
	}

}
