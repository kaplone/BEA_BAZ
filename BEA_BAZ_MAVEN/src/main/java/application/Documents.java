package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import models.Auteur;
import models.Client;
import models.Commande;
import models.Etat;
import models.Job;
import models.Modele;
import models.Oeuvre;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.types.ObjectId;
import org.jongo.marshall.MarshallingException;
import org.jongo.marshall.jackson.JacksonMapper;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;

public class Documents {
	
	private static Client client;
	private static ObjectId client_id;
	private static Auteur auteur;
	private static ObjectId auteur_id;
	private static Commande commande;
	private static ObjectId commande_id;
	private static Etat etat;
	private static ObjectId etat_id;
	
	public static void init(){
		
		ObjectMapper mapper = new ObjectMapper();
		
		//if(utils.MongoAccess.checkIfExists("auteur", "nom", "Jean-Claude Golvin")){
		try {
			auteur = utils.MongoAccess.request("auteur", "nom", "Jean-Claude Golvin").as(Auteur.class);
			auteur_id = auteur.get_id();
			
		}
		catch (MarshallingException | NullPointerException MEME)  {
			auteur = new Auteur();
			auteur.setNom("Jean-Claude Golvin");
			utils.MongoAccess.save("auteur", auteur);
			auteur_id = auteur.get_id();
		}
		
	}

	public static void read(File file_) throws IOException {
		
		boolean titres = true;
		boolean update = false;
		
		ArrayList<String> noms_titres = new ArrayList<>();
		
		FileInputStream file = new FileInputStream(file_);
		
		int nb_colonnes = 0;
		
		int index = 0;
		
		String string_oeuvre = "";
		ArrayList<String> string_oeuvre_liste = new ArrayList<>();
		Oeuvre o = new Oeuvre();

		ObjectMapper mapper = new ObjectMapper();
		 
        //Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        //Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(1);

        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext())
        {

        	Row row = rowIterator.next();
        	
        	boolean st;

        	try {
                if (utils.MongoAccess.request("oeuvre", "cote_archives_6s", row.getCell(1).getStringCellValue()).as(Oeuvre.class) != null){
                	
                	o = utils.MongoAccess.request("oeuvre", "cote_archives_6s", row.getCell(1).getStringCellValue()).as(Oeuvre.class);
                	update = true;
                }
                else {
                	o = new Oeuvre();
                    string_oeuvre = "";
                    string_oeuvre_liste.clear();
                    
                    System.out.println(auteur_id);
                    
                    string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "auteur", auteur_id));
                    
                    update = false;
                }
        	}
        	catch (IllegalStateException ise){
        		
        		if (utils.MongoAccess.request("oeuvre", "cote_archives_6s", row.getCell(1).getNumericCellValue() + "").as(Oeuvre.class) != null){
        			o = utils.MongoAccess.request("oeuvre", "cote_archives_6s", row.getCell(1).getNumericCellValue() + "").as(Oeuvre.class); 
        			update = true;
        		}
        		else {
        			 o = new Oeuvre();
                     string_oeuvre = "";
                     string_oeuvre_liste.clear();
                     
                     System.out.println(auteur_id);
                     
                     string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "auteur", auteur_id));
                     update = false;
        		}
            }
        	catch (NullPointerException mpe){
        		
                o = new Oeuvre();
                string_oeuvre = "";
                string_oeuvre_liste.clear();
  
                string_oeuvre_liste.add(String.format("\"%s\" : \"%s\"", "auteur", auteur_id));
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
	            
	            System.out.println(string_oeuvre);
	
	            o = mapper.readValue(string_oeuvre, Oeuvre.class);
	            
	            utils.MongoAccess.save("oeuvre", o);
        	}
            
        }
        
        workbook.close();
        file.close();

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
