package enums;

import models.Auteur;
import models.Client;
import models.Oeuvre;


public enum classes {
	
	client (Client.class),
	auteur (Auteur.class),
	oeuvre (Oeuvre.class);
	
	private Class usedClass;
	
	classes(Class c){
		this.usedClass = c;	
	}

	public Class getUsedClass() {
		return usedClass;
	}

}
