package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.scene.image.Image;

import org.bson.types.ObjectId;

import com.sun.javafx.iio.ImageStorage;

import application.Main_BEA_BAZ;
import models.Fichier;
import models.Oeuvre;
import models.OeuvreTraitee;
import models.Produit;
import models.TacheTraitement;
import models.Traitement;
import enums.Progression;
import fr.opensagres.xdocreport.converter.ConverterRegistry;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.IConverter;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.document.DocumentKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.ClassPathImageProvider;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.annotations.FieldMetadata;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import fr.opensagres.xdocreport.template.annotations.ImageMetadata;

public class FreeMarkerMaker {
	
	@FieldMetadata( images = { @ImageMetadata( name = "image_oeuvre" ) } )
    //public static File  getLogo(OeuvreTraitee ot)
    public static File  getLogo(ArrayList<Fichier> af)
    {
        //return new File("/home/kaplone/Desktop/BEABASE/Béa base/P1140344.JPG" );
        //return Main_BEA_BAZ.getCommande().getModele().getCheminVersModel().getParent().resolve("P1140344.JPG").toFile();

		//return MongoAccess.request("fichier", ot.getFichiers().get(ot.getFichiers().size() -2)).as(Fichier.class).next().getFichierLie();
        return new File(af.get(af.size() -2).getFichierLie());
    }

	public static void odt2pdf(Oeuvre o, OeuvreTraitee ot) {

		try {
		      // 1) Load Docx file by filling Velocity template engine and cache it to the registry
		      //InputStream in = new FileInputStream(new File("/home/kaplone/Desktop/BEABASE/Béa base/modele_rapport_v2_freemarker.odt"));
		      //InputStream in = new FileInputStream(new File("C:\\Users\\USER\\Desktop\\BEABAZ\\modele_rapport_v2_freemarker.odt"));

			  System.out.println(Main_BEA_BAZ.getCommande().getModele().getCheminVersModel().toString());  
			  
              ArrayList<Fichier> listeFichiers = new ArrayList<>();
		      
		      for (ObjectId fichier_id : ot.getFichiers()){
		    	  
		    	  listeFichiers.add(MongoAccess.request("fichier", fichier_id).as(Fichier.class).next());
		    	  
		    	  listeFichiers.sort(new Comparator<Fichier>() {

					@Override
					public int compare(Fichier o1, Fichier o2) {
					
						return String.format("%s_%02d", o1.getNom().split("\\.")[1], Integer.parseInt(o1.getNom().split("\\.")[2]))
					.compareTo(String.format("%s_%02d", o2.getNom().split("\\.")[1], Integer.parseInt(o2.getNom().split("\\.")[2])));
					}
				});
		      }
		      
			  Image img = new Image("file:" + listeFichiers.get(listeFichiers.size() -2).getFichierLie().toString());
 
			  InputStream in;
			  
			  if (img.getHeight() > img.getWidth()){
				  in = new FileInputStream(Main_BEA_BAZ.getCommande().getModeleVertical().toFile());
			  }
			  else{
				  in = new FileInputStream(Main_BEA_BAZ.getCommande().getModele().getCheminVersModel().toFile());
			  }

		      IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,TemplateEngineKind.Freemarker);

		      
		      FieldsMetadata metadata = report.createFieldsMetadata();
		      metadata.addFieldAsImage( "image_oeuvre", "image_oeuvre");
//		      metadata.addFieldAsList("fichiers.nom");
//              metadata.addFieldAsList("fichiers.legende");
		      report.setFieldsMetadata(metadata);
		      
		      System.out.println("__01");

		      IContext context = report.createContext();
		      context.put("inventaire", o.getCote_archives_6s());
		      context.put("titre", o.getTitre_de_l_oeuvre() != null ? o.getTitre_de_l_oeuvre() : "");
		      context.put("dimensions", o.getDimensions() != null ? o.getDimensions() : "");
		      context.put("technique", o.getTechnique() != null ? o.getTechnique() : "");
		      context.put("matiere", o.getMatiere() != null ? o.getMatiere() : "");
		      context.put("inscriptions", o.getInscriptions_au_verso() != null ? o.getInscriptions_au_verso() : "");
		      
		      context.put("etat_final", ot.getEtat() != null ? ot.getEtat().toString() : "");
		      
		      
		      ArrayList<String> traitementsEffectues = new ArrayList<>();
		      ArrayList<String> produitsAppliques = new ArrayList<>();
		      
		      for (ObjectId tt_id : ot.getTraitementsAttendus()){
		    	  
		    	  TacheTraitement tt = MongoAccess.request("tacheTraitement", tt_id).as(TacheTraitement.class).next();
		    	  
		    	  if(tt.getFait_() == Progression.FAIT_){
		    		  
		    		  traitementsEffectues.add(tt.getTraitement().getNom_complet() + (tt.getComplement() == null ? "" : " " + tt.getComplement().getNom_complet()));  
		    		  produitsAppliques.add(tt.getProduitUtilise() == null ? "" : tt.getProduitUtilise().getNom_complet());
		    		  
		    	  }
		      }
		      
		      context.put("produits", produitsAppliques.stream().collect(Collectors.joining(", ")));
		      
		      context.put("traitements", traitementsEffectues);
		      
		      context.put("alterations", ot.getAlterations());
		      
		      
		      
//		      ArrayList<String> listeFiles = new ArrayList<>();
//		      ArrayList<String> listeLegendes = new ArrayList<>();
//		      
//		      for (Fichier f : listeFichiers){
//		    	  
//		    	  listeFiles.add(f.getNom());
//		    	  listeLegendes.add(f.getLegende());
//		    	  
//		      }		      
//		      context.put("fichiers", listeFiles);
//		      context.put("legendes", listeLegendes);
		      
		      context.put("fichiers", listeFichiers);
		      
		      System.out.println("__02");
		      
		      context.put("bea", "Béatrice Alcade\n" +
                                 "Restauratrice du patrimoine\n" +
                                 "28 place Jean Jaurès\n" +
                                 "34400 Lunel - 06 21 21 15 40");
		      
		      context.put("client", "ARCHIVES MUNICIPALES DE LA SEYNE-SUR-MER\n" +
		    		                "Traverse Marius AUTRAN\n" +
		    		                "83 500 La Seyne-sur-Mer");
		      
		      context.put("commande", "RESTAURATION DE DOCUMENTS D'ARCHIVES FONDS CHARLY\n" + 
		                              "524 caricatures réalisées à la gouache, à l'aquarelle et au feutre");
		      
		      System.out.println("__03");

		      //context.put("image_oeuvre", getLogo(ot));	
		      context.put("image_oeuvre", getLogo(listeFichiers));

		      // 3) Generate report by merging Java model with the Docx
		      
		      //OutputStream out = new FileOutputStream(new File(String.format("/home/kaplone/Desktop/BEABASE/tests/%s_freemarker.odt", o.getCote_archives_6s())));
		      
		      Path p = Main_BEA_BAZ.getCommande().getModele().getCheminVersModel().getParent().resolve(String.format("%s_freemarker.odt", o.getCote_archives_6s()));
		      System.out.println(p);
		      
		      OutputStream out = new FileOutputStream(Main_BEA_BAZ.getCommande().getModele().getCheminVersModel().getParent().resolve(String.format("%s_freemarker.odt", o.getCote_archives_6s())).toFile());
		      
		      System.out.println("__04");
		      report.process(context, out);
		      
		      System.out.println("__05");

		      //OutputStream out2 = new FileOutputStream(new File(String.format("/home/kaplone/Desktop/BEABASE/tests/%s_freemarker.pdf", o.getCote_archives_6s())));
		      OutputStream out2 = new FileOutputStream(Main_BEA_BAZ.getCommande().getModele().getCheminVersModel().getParent().resolve(String.format("%s_freemarker.pdf", o.getCote_archives_6s())).toFile());
              // 1) Create options ODT 2 PDF to select well converter form the registry
              Options options = Options.getFrom(DocumentKind.ODT).to(ConverterTypeTo.PDF);

              // 2) Get the converter from the registry
              IConverter converter = ConverterRegistry.getRegistry().getConverter(options);
   
              report.convert(context, options, out2);

		    } catch (IOException e) {
		      e.printStackTrace();
		    } catch (XDocReportException e) {
		      e.printStackTrace();
		    }

	}

}
