package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import models.Oeuvre;
import models.TacheTraitement;
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
        return new File("/home/kaplone/Desktop/BEABASE/Béa base/P1140344.JPG" );
    }

	public static void odt2pdf(Oeuvre o) {

		try {
		      // 1) Load Docx file by filling Velocity template engine and cache it to the registry
		      InputStream in = new FileInputStream(new File("/home/kaplone/Desktop/BEABASE/Béa base/modele_rapport_v2_freemarker.odt"));
		      IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,TemplateEngineKind.Freemarker);

		      
		      FieldsMetadata metadata = report.createFieldsMetadata();
		      metadata.addFieldAsImage( "image_oeuvre", "image_oeuvre");
		      report.setFieldsMetadata(metadata);

		      IContext context = report.createContext();
		      
		      context.put("inventaire", o.getCote_archives_6s());
		      context.put("titre", o.getTitre_de_l_oeuvre() != null ? o.getTitre_de_l_oeuvre() : "");
		      context.put("dimensions", o.getDimensions() != null ? o.getDimensions() : "");
		      context.put("technique", o.getDimensions() != null ? o.getDimensions() : "");
		      context.put("inscriptions", o.getInscriptions_au_verso() != null ? o.getInscriptions_au_verso() : "");
		      context.put("produits", o.getDimensions() != null ? o.getDimensions() : "");
		      context.put("etat_final", o.getDimensions() != null ? o.getDimensions() : "");
		      
		      context.put("bea", "Béatrice Alcade\n" +
                                 "Restauratrice du patrimoine\n" +
                                 "28 place Jean Jaurès\n" +
                                 "34400 Lunel - 06 21 21 15 40");
		      context.put("client", "Restauration des dessins de Jean-Claude Golvin\n" +
                                    "Musée Départemental Arles antique\n" +
                                    "Avril 2015");

		      context.put("image_oeuvre", getLogo());

		      // 3) Generate report by merging Java model with the Docx
		      OutputStream out = new FileOutputStream(new File(String.format("/home/kaplone/Desktop/BEABASE/tests/%s_freemarker.odt", o.getCote_archives_6s())));
		      report.process(context, out);

              OutputStream out2 = new FileOutputStream(new File(String.format("/home/kaplone/Desktop/BEABASE/tests/%s_freemarker.pdf", o.getCote_archives_6s())));
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
