package models;

import java.io.File;
import java.nio.file.Paths;

import utils.MongoAccess;

public class Fichier extends Commun{
	
	private String fichierLie;
	
	private String legende;
	
	public static void update(Fichier c){

		MongoAccess.update("fichier", c);
	}
	
    public static void save(Fichier c){
		
		MongoAccess.save("fichier", c);
		
	}
    
    @Override
    public String toString(){
    	
    	return Paths.get(this.fichierLie).getFileName().toString();
    	
    }

	public String getFichierLie() {
		return fichierLie;
	}

	public void setFichierLie(String fichierLie) {
		this.fichierLie = fichierLie;
	}

	public String getLegende() {
		return legende;
	}

	public void setLegende(String legende) {
		this.legende = legende;
	}
	
	@Override
	public String getNom(){
		return Paths.get(this.fichierLie).getFileName().toString();
	}
	

}
