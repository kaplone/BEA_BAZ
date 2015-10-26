package models;

import java.io.File;

public class Fichier extends Commun{
	
	private File fichierLie;
	
	private String legende;

	public File getFichierLie() {
		return fichierLie;
	}

	public void setFichierLie(File fichierLie) {
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
		return fichierLie.toPath().getFileName().toString();
	}
	

}
