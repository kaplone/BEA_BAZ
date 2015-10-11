package enums;

import models.Auteur;
import models.Client;
import models.Oeuvre;
import models.Produit;
import models.Traitement;


public enum Classes {
	
	client (Client.class),
	auteur (Auteur.class),
	traitement (Traitement.class),
	produit (Produit.class),
	oeuvre (Oeuvre.class);
	
	private Class usedClass;
	
	Classes(Class c){
		this.usedClass = c;	
	}

	public Class getUsedClass() {
		return usedClass;
	}

}
