package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

import application.Main_BEA_BAZ;
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
    public static File  getLogo()
    {
        //return new File("/home/kaplone/Desktop/BEABASE/Béa base/P1140344.JPG" );
        return Main_BEA_BAZ.getCommande().getModele().getCheminVersModel().getParent().resolve("P1140344.JPG").toFile();
    }

	public static void odt2pdf(Oeuvre o, OeuvreTraitee ot) {

		try {
		      // 1) Load Docx file by filling Velocity template engine and cache it to the registry
		      //InputStream in = new FileInputStream(new File("/home/kaplone/Desktop/BEABASE/Béa base/modele_rapport_v2_freemarker.odt"));
		      //InputStream in = new FileInputStream(new File("C:\\Users\\USER\\Desktop\\BEABAZ\\modele_rapport_v2_freemarker.odt"));

			  System.out.println(Main_BEA_BAZ.getCommande().getModele().getCheminVersModel().toString());  
			
		      InputStream in = new FileInputStream(Main_BEA_BAZ.getCommande().getModele().getCheminVersModel().toFile());
		      IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,TemplateEngineKind.Freemarker);

		      
		      FieldsMetadata metadata = report.createFieldsMetadata();
		      metadata.addFieldAsImage( "image_oeuvre", "image_oeuvre");
		      report.setFieldsMetadata(metadata);
		      
		      System.out.println("__01");

		      IContext context = report.createContext();
		      context.put("inventaire", o.getCote_archives_6s());
		      context.put("titre", o.getTitre_de_l_oeuvre() != null ? o.getTitre_de_l_oeuvre() : "");
		      context.put("dimensions", o.getDimensions() != null ? o.getDimensions() : "");
		      context.put("technique", o.getDimensions() != null ? o.getDimensions() : "");
		      context.put("inscriptions", o.getInscriptions_au_verso() != null ? o.getInscriptions_au_verso() : "");
		      
		      context.put("etat_final", o.getDimensions() != null ? o.getDimensions() : "");
		      //context.put("traitements", ot.getTraitementsEnCours() != null ? ot.getTraitementsEnCours() : "");
		      
//		      ArrayList<String> arl = new ArrayList();
//		      arl.add("un");
//		      arl.add("deux");
//		      arl.add("trois");
		      
		      ArrayList<String> traitementsEffectues = new ArrayList<>();
		      ArrayList<String> produitsAppliques = new ArrayList<>();
		      
		      for (TacheTraitement tt : ot.getTraitementsAttendus()){
		    	  System.out.println(tt.getFait_());
		    	  if(tt.getFait_() == Progression.FAIT_){
		    		  traitementsEffectues.add(tt.getTraitement().getNom_complet() + tt.getComplement() != null ? " " + tt.getComplement().getNom_complet() : "");
		    		  produitsAppliques.add(tt.getProduitUtilise().getNom_complet());
		    		  
		    	  }
		      }
		      
		      System.out.println(traitementsEffectues);
		      System.out.println(produitsAppliques.stream().collect(Collectors.joining(",")));
		      
		      context.put("produits", produitsAppliques.stream().collect(Collectors.joining(",")));
		      
		      context.put("traitements", traitementsEffectues);
		      
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

		      context.put("image_oeuvre", getLogo());
		      
		      

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
