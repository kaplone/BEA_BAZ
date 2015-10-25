package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import models.Auteur;
import models.Client;
import models.Commande;
import models.Etat;
import models.Model;
import models.Oeuvre;
import models.OeuvreTraitee;
import models.Produit;
import models.TacheTraitement;
import models.Traitement;
import enums.Classes;
import enums.Progression;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.types.ObjectId;
import org.jongo.marshall.MarshallingException;
import org.jongo.marshall.jackson.JacksonMapper;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import utils.MongoAccess;
import utils.Normalize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;

public class Documents {
	
	private static Commande commande;
	private static ObjectId commande_id;

	public static void read(File file_, String table_) throws IOException {
		
        FileInputStream file = new FileInputStream(file_);
		
		int index = 0;
		
		boolean titres = true;
		boolean update = false;
		
		ArrayList<String> string_produit_liste = new ArrayList<>();
		
		ObjectMapper mapper = new ObjectMapper();
		 
        //Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        //Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        
        String string_produit = "";
        
        Produit p;
        Traitement t;
        
        while (rowIterator.hasNext())
        {

        	Row row = rowIterator.next();
        	
        	String [] champs = new String[2];
        	
        	switch (table_){
        	
	        	case "produit" :
	
		        	try {
		                if (utils.MongoAccess.request(table_, "nom_complet", row.getCell(0).getStringCellValue()).as(Classes.valueOf(table_).getUsedClass()) != null){
		                	
		                	p = (Produit) utils.MongoAccess.request(table_, "nom_complet", row.getCell(0).getStringCellValue()).as(Classes.valueOf(table_).getUsedClass());
		                	update = true;
		                }
		                else {
		                	
		                	p = new Produit();
		                    string_produit = "";
		                    string_produit_liste.clear();
		                    
		                    update = false;
		                }
		        	}
		
		        	catch (NullPointerException mpe){
		        		
		                p = new Produit();
		                string_produit = "";
		                string_produit_liste.clear();
		                update = false;
		        		
		        	}
		        	
		        	if (update) {
		        		
		        	}
		        	else{
			            
			            //For each row, iterate through all the columns
			            Iterator<Cell> cellIterator = row.cellIterator();
			            
			            
			
			            while (cellIterator.hasNext())
			            {
			                Cell cell = cellIterator.next();
			                
			                // le premier passage est 'à vide'
			                // c'est la liste des champs
			                if (titres){
		
			            	}
			                // les valeurs suivantes servent à construire 
			                // une json string
			            	else {
			
			            		champs [index] = (String) Normalize.normalizeField(cell.getStringCellValue()); 	
		
				                }
				                index ++; // on  avance dans la liste des champs
			            	}
			            }
			            
		        	    
		        	
			            titres = false; // après le premier passage ce ne sera plus un titre
			            
			            index = 0; // on initialise pour la prochaine ligne
			            
			            if (champs[0] != null){
			            	
			            	string_produit = String.format("{\"nom_complet\" : \"%s\", \"nom\" : \"%s\"}", champs[0], champs[1]);

				            p = mapper.readValue(string_produit, Produit.class);
				            
				            utils.MongoAccess.save("produit", p);
			            	
			            }
			            
			            
	            break;
	        
	        case "traitement" :
	        	
	        	try {
	                if (utils.MongoAccess.request(table_, "nom_complet", row.getCell(0).getStringCellValue()).as(Classes.valueOf(table_).getUsedClass()) != null){
	                	
	                	t = (Traitement) utils.MongoAccess.request(table_, "nom_complet", row.getCell(0).getStringCellValue()).as(Classes.valueOf(table_).getUsedClass());
	                	update = true;
	                }
	                else {
	                	
	                	t = new Traitement();
	                    string_produit = "";
	                    string_produit_liste.clear();
	                    
	                    update = false;
	                }
	        	}
	
	        	catch (NullPointerException mpe){
	        		
	                t = new Traitement();
	                string_produit = "";
	                string_produit_liste.clear();
	                update = false;
	        		
	        	}
	        	
	        	if (update) {
	        		
	        	}
	        	else{
		            
		            //For each row, iterate through all the columns
		            Iterator<Cell> cellIterator = row.cellIterator();
		            
		            
		
		            while (cellIterator.hasNext())
		            {
		                Cell cell = cellIterator.next();
		                
		                // le premier passage est 'à vide'
		                // c'est la liste des champs
		                if (titres){
	
		            	}
		                // les valeurs suivantes servent à construire 
		                // une json string
		            	else {
		
		            		champs [index] = (String) Normalize.normalizeField(cell.getStringCellValue()); 	
	
			                }
			                index ++; // on  avance dans la liste des champs
		            	}
		            }
		            
	        	    
	        	
		            titres = false; // après le premier passage ce ne sera plus un titre
		            
		            index = 0; // on initialise pour la prochaine ligne
		            
		            if (champs[0] != null){
		            	
		            	string_produit = String.format("{\"nom_complet\" : \"%s\", \"nom\" : \"%s\"}", champs[0], champs[1]);

			            t = (Traitement) mapper.readValue(string_produit, Classes.valueOf(table_).getUsedClass());
			            
			            utils.MongoAccess.save(table_, t);
		            	
		            }
		            
		            
	        	
	            break;
	        
	        default : break;
	        	
	}
	        	
        
        workbook.close();
        file.close();

	}
		
	}

	public static void read(File file_, Commande commande_) throws IOException {
		
		commande_id = commande_.get_id();
		
		boolean titres = true;
		boolean update = false;
		
		ArrayList<String> noms_titres = new ArrayList<>();
		
		FileInputStream file = new FileInputStream(file_);
		
		int nb_colonnes = 0;
		
		int index = 0;
		
		String string_oeuvre = "";
		ArrayList<String> string_oeuvre_liste = new ArrayList<>();
		Oeuvre o = new Oeuvre();
		OeuvreTraitee ot = new OeuvreTraitee();

		ObjectMapper mapper = new ObjectMapper();
		 
        //Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        //Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(1);

        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()){

        	Row row = rowIterator.next();
        	
        	boolean st;

        	try {
        		// l'oeuvre existe dans la base
                if (utils.MongoAccess.request("oeuvre", "cote_archives_6s", row.getCell(1).getStringCellValue()).as(Oeuvre.class) != null){
                	
                	System.out.println("cas 1");
                	
                	o = utils.MongoAccess.request("oeuvre", "cote_archives_6s", row.getCell(1).getStringCellValue()).as(Oeuvre.class);
                	update = true;
                }
             // l'oeuvre n'existe pas encore dans la base
                else {
                	
                	System.out.println("cas 2");
                	
                	o = new Oeuvre();
                    string_oeuvre = "";
                    string_oeuvre_liste.clear();
                    string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "commande", commande_id));
                    string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "auteur", commande.getAuteur().get_id()));
                    string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "created_at", LocalDate.now()));
                    string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "updated_at", null));
                    string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "deleted_at", null));
                    
                    update = false;
                }
    
                
        	}
        	catch (IllegalStateException ise){
        		// l'oeuvre existe dans la base
        		if (utils.MongoAccess.request("oeuvre", "cote_archives_6s", row.getCell(1).getNumericCellValue() + "").as(Oeuvre.class) != null){
        			
        			System.out.println("cas 3");
        			
        			o = utils.MongoAccess.request("oeuvre", "cote_archives_6s", row.getCell(1).getNumericCellValue() + "").as(Oeuvre.class); 
        			update = true;
        		}
        		// l'oeuvre n'existe pas encore dans la base
        		else {
        			
        			System.out.println("cas 4");
        			
        			 o = new Oeuvre();
                     string_oeuvre = "";
                     string_oeuvre_liste.clear();
                     string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "commande", commande_id));
                     string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "auteur", commande.getAuteur().get_id()));
                     string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "created_at", LocalDate.now()));
                     string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "updated_at", null));
                     string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "deleted_at", null));
                     
                     update = false;
        		}
            }
        	catch (NullPointerException mpe){
        		
        		System.out.println("cas 5");
        		
        		// l'oeuvre n'existe pas encore dans la base
                o = new Oeuvre();
                string_oeuvre = "";
                string_oeuvre_liste.clear();
                string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "commande", commande_id));
                string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "created_at", LocalDate.now()));
                string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "updated_at", null));
                string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "deleted_at", null));
                string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "etat_current", "TODO_"));
                
                update = false;
        		
        	}
        	
        	if (update) {
        		
        	}
        	else{
	            
	            //For each row, iterate through all the columns
	            Iterator<Cell> cellIterator = row.cellIterator();
	
	            while (cellIterator.hasNext())
	            {
	                Cell cell = cellIterator.next();
	                
	                // le premier passage est 'à vide'
	                // c'est la liste des champs
	                if (titres){
	            		noms_titres.add(cell.getStringCellValue().equals("") ? String.format("field_%02d", nb_colonnes + 1) : Normalize.normalize(cell.getStringCellValue()));
	            		nb_colonnes = noms_titres.size();
	            	}
	                // les valeurs suivantes servent à construire 
	                // une json string
	            	else {
	
		                //Check the cell type and format accordingly
		                switch (cell.getCellType())
		                {
		                    case Cell.CELL_TYPE_NUMERIC:
	
	                            string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", noms_titres.get(index), cell.getNumericCellValue() + ""));
		                        break;
		                        
		                    case Cell.CELL_TYPE_STRING:
		                        
		                        if (index >=7 && index <= 22){
		                        	
		                        }
		                        else {
		                        	string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", noms_titres.get(index), Normalize.normalizeField(cell.getStringCellValue())));
		                        }
		                        
		                        break;
		                }
		                index ++; // on  avance dans la liste des champs
	            	}
	            }
	            
	            titres = false; // après le premier passage ce ne sera plus un titre
	            
	            index = 0; // on initialise pour la prochaine ligne
	
	            
	            string_oeuvre = string_oeuvre_liste.stream().collect(Collectors.joining(", ", "{", "}"));
	
	            o = mapper.readValue(string_oeuvre, Oeuvre.class);

	            utils.MongoAccess.save("oeuvre", o);
	            
	            listeDesTachesTraitement(o);
	            
	            
        	}
        	
        	ot = new OeuvreTraitee();
            ot.setOeuvre(o);
            ot.setCommande(commande_);
            ot.setProgressionOeuvreTraitee(Progression.TODO_);
            
            utils.MongoAccess.save("oeuvreTraitee", ot);
            
            System.out.println("ot.get_id() (juste apres le save() : "+ ot.get_id());
            
            ArrayList<ObjectId> traitementsEnCours = new ArrayList<>();
            
            for (Traitement t : commande_.getTraitements_attendus()) {
            	
            	TacheTraitement tt = new TacheTraitement();
            	tt.setTraitement(t);
            	tt.setOeuvreTraiteeId(ot.get_id());
            	tt.setFait_(Progression.TODO_);
            	tt.setNom(t.getNom());
            	
            	utils.MongoAccess.save("tacheTraitement", tt);
            	
            	
            	traitementsEnCours.add(tt.get_id());
            	
				
			}
			ot.setTraitementsAttendus(traitementsEnCours);
            
            utils.MongoAccess.save("oeuvreTraitee", ot);
            
        }
        
        workbook.close();
        file.close();

	}
	
	public static void listeDesTachesTraitement(Oeuvre oeuvre){
		
		commande = Main_BEA_BAZ.getCommande();
		
		System.out.println(oeuvre.getNom());
		
		//ArrayList<TacheTraitement> listeDesTaches = new ArrayList<>();
		
		for (Traitement t : commande.getTraitements_attendus()){
			
			TacheTraitement tt = new TacheTraitement();
			tt.setCommandeId(commande.get_id());
			tt.setTraitement(t);
			tt.setCreated_at(Date.from(Instant.now()));
			tt.setOeuvreTraiteeId(oeuvre.get_id());
			tt.setFait_(Progression.TODO_);
			
			MongoAccess.save("tacheTraitement", tt);
			
			//listeDesTaches.add(tt);
		}
		
		//return listeDesTaches.stream().map(t -> t.get_id().toString()).collect(Collectors.joining(",", "[", "]"));
		
		
		
	}
	
	public static void write(){
		//Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Employee Data");
          
        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[] {"ID", "NAME", "LASTNAME"});
        data.put("2", new Object[] {1, "Amit", "Shukla"});
        data.put("3", new Object[] {2, "Lokesh", "Gupta"});
        data.put("4", new Object[] {3, "John", "Adwards"});
        data.put("5", new Object[] {4, "Brian", "Schultz"});
          
        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File("/home/kaplone/Desktop/howtodoinjava_demo.xlsx"));
            workbook.write(out);
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}

}
