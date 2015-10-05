package utils;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;
import org.jongo.Find;
import org.jongo.FindOne;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.jongo.Update;

import models.Client;
import models.Commande;
import models.Commun;
import models.Modele;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import enums.classes;

public class MongoAccess {
	
	static MongoClient mc;
	static DB db;
	static Jongo jongo;
	static MongoCollection collec;
	
	public static void connect(){
		
		
		
		try {
			
			MongoClientURI uri  = new MongoClientURI("mongodb://bea:beabaz@ds055852.mongolab.com:55852/heroku_g9z5lnn2"); 
			MongoClient client = new MongoClient(uri);
			db = client.getDB(uri.getDatabase());
			
			//mc = new MongoClient();
			//db = mc.getDB("test");		
			jongo = new Jongo(db);
			
			
		}
		catch (UnknownHostException UHE){
					System.out.println("erreur " + UHE);
		}
	}
	
	public static boolean checkIfExists (String table, String field, String valeur) {

		collec = jongo.getCollection(table);
		return  collec.findOne(String.format("{\"%s\" : \"%s\"}", field, valeur)).as(classes.valueOf(table).getUsedClass()) != null;
		//return  collec.findOne(String.format("{\"%s\" : \"%s\"}", field, valeur)).as(Client.class) != null;
		
		
	}
	
    public static Find request(String table) {	
		
		Find find = null;
		collec = jongo.getCollection(table);
		find = collec.find();

		return find;
	}
    
    public static Find request(String table, Commande commande) {	
		
		Find find = null;
		collec = jongo.getCollection(table);
		find = collec.find("{commande :  commande.get_id()}");

		return find;
	}
    
    public static Find request(String table, Client client) {	
		
		Find find = null;
		collec = jongo.getCollection(table);
		find = collec.find("{client : #}", client.get_id());

		return find;
	}
	
	public static FindOne request(String table, String field, String valeur) {	
		
		FindOne one = null;
		collec = jongo.getCollection(table);
		one = collec.findOne(String.format("{\"%s\" : \"%s\"}", field, valeur));

		return one;
	}
		
	
	public static void update (int i) {
		//collec.update("{long1 : 466}").with("{$set : {long2 : #}}", i);
		
	}
	
	public static void insert (String table, Object m) {
		collec = jongo.getCollection(table);
		collec.insert(m);
		
	}
	
	public static void save (String table, Object m) {
		collec = jongo.getCollection(table);
		collec.save(m);
		
	}



	public static void drop() {
		collec.drop();
		
	}

	public static void update(String table, Commun c) {
		collec = jongo.getCollection(table);	
		collec.save(c);
	}


}
