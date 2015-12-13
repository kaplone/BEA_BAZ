package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.CopyOption;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.TreeMap;

import org.bson.types.ObjectId;

import application.Fiche_commande_import_controller;
import application.Main_BEA_BAZ;
import models.Fichier;
import models.OeuvreTraitee;

public class Walk {
	
	public static void walking(Path p) {
		
		System.out.println(p.toString());

		FileVisitor<Path> fileVisitor = new FileSizeVisitor(new Long(50));
		try {
			Files.walkFileTree(p, fileVisitor);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class FileSizeVisitor implements FileVisitor<Path> {

		private Long size;

		public FileSizeVisitor(Long size) {
			this.size = size;
		}

		/**
		 * This is triggered before visiting a directory.
		 */
		@Override
		public FileVisitResult preVisitDirectory(Path path,
				BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		/**
		 * This is triggered when we visit a file.
		 */
		@Override
		public FileVisitResult visitFile(Path path, BasicFileAttributes attrs)
				throws IOException {

			if(path.toString().toLowerCase().endsWith(".jpg")){
			    distribuer(path);
			}
			

			return FileVisitResult.CONTINUE;
		}

		/**
		 * This is triggered if we cannot visit a Path We log the fact we cannot
		 * visit a specified path .
		 */
		@Override
		public FileVisitResult visitFileFailed(Path path, IOException exc)
				throws IOException {
			// We print the error
			System.err.println("ERROR: Cannot visit path: " + path);
			// We continue the folder walk
			return FileVisitResult.CONTINUE;
		}

		/**
		 * This is triggered after we finish visiting a specified folder.
		 */
		@Override
		public FileVisitResult postVisitDirectory(Path path, IOException exc)
				throws IOException {
			// We continue the folder walk
			return FileVisitResult.CONTINUE;
		}
		
		String base = System.getProperty("user.dir"); //retourne le chemin du package : /home/autor/workspace-scala/WALKING
		
		public void distribuer(Path path){

			String cote = path.getFileName().toString().split("\\.")[0];
			String temporalite = path.getFileName().toString().split("\\.")[1];
			String rang = path.getFileName().toString().split("\\.")[2];
			
			Fichier fichier = new Fichier();
			fichier.setFichierLie(path.toString());
			
			String legende = "";
			
            switch (temporalite){
            
            case "AR" : legende += "Avant traitement ";
            
                        switch (rang){
            
			            case "1" : legende += "recto";
			                        break;
			            case "2" : legende += "verso ";
			                        break;
			            default  : legende += "";
			                        break;
			            }
                        break;
            case "IR" : legende += "Durant traitement ";
                        break;
            case "PR" : legende += "Après traitement ";
            
			            switch (rang){
			            
			            case "1" : legende += "recto";
			                        break;
			            case "2" : legende += "verso ";
			                        break;
			            default  : legende += "";
			                        break;
			            }
                        break;
            }
            
			
			fichier.setLegende(legende);
			
			MongoAccess.save("fichier", fichier);

			OeuvreTraitee oeuvreConcernee = MongoAccess.request("oeuvreTraitee", "cote", cote).as(OeuvreTraitee.class);

			
			//Fiche_commande_import_controller.getBindLabel().set("Images en cours : " + path.getFileName().toString());
			
			if (oeuvreConcernee.getFichiers_id() == null){
				
				oeuvreConcernee.setFichiers_id(new TreeMap<String, ObjectId>());
			}
			
			oeuvreConcernee.getFichiers_id().put(Normalize.normalizeNormStringField(fichier.getNom()), fichier.get_id());
			
			OeuvreTraitee.update(oeuvreConcernee);
		}
		
		File repertoires(String ajout,Path path) {
			File file = new File (ajout + path);
			File parent = new File (ajout + path.getParent());
			parent.mkdirs();
			return file;
		}
		
//		private static BufferedReader getOutput(Process p) {
//	        return new BufferedReader(new InputStreamReader(p.getInputStream()));
//	    }
//
//	    private static BufferedReader getError(Process p) {
//	        return new BufferedReader(new InputStreamReader(p.getErrorStream()));
//	    }
//		
//		void convertir(Path path){
//			
//			String commande2 = "mencoder %s -ovc x264 -sws 9 -x264encopts nocabac:level_idc=30:bframes=0:bitrate=2000:threads=auto:turbo=1:global_header:threads=auto:subq=5:frameref=6:partitions=all:trellis=1:chroma_me:me=umh -oac faac -faacopts mpeg=4:object=2:raw:br=192 -of lavf -lavfopts format=mp4 -o %s";
//			String commande3 = String.format(commande2, path, (repertoires ("/home/autor/Desktop/EXPORT/temp", path)).toString());
//
//			
//            Process p = null;
//			
//			try {
//				p = Runtime.getRuntime().exec(commande3);
//				BufferedReader output = getOutput(p);
//	            BufferedReader error = getError(p);
//	            String ligne = "";
//
//	            while ((ligne = output.readLine()) != null) {
//	                System.out.println("- " + ligne);
//	            }
//	            
//	            while ((ligne = error.readLine()) != null) {
//	                System.out.println("+ " + ligne);
//	            }
//	            
//				p.waitFor();
//			} catch (IOException | InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
//		}
//		
//		void faststart(String pathIn, String pathOut) {
//			
//			String commande4 = "/home/autor/Desktop/faststart/qt-faststart %s %s";
//			String commande5 = String.format(commande4, pathIn, pathOut);
//			//System.out.println(commande3);
//			
//            Process p = null;
//			
//			try {
//				p = Runtime.getRuntime().exec(commande5);
//				BufferedReader output = getOutput(p);
//	            BufferedReader error = getError(p);
//	            String ligne = "";
//
//	            while ((ligne = output.readLine()) != null) {
//	                System.out.println("- " + ligne);
//	            }
//	            
//	            while ((ligne = error.readLine()) != null) {
//	                System.out.println("+ " + ligne);
//	            }
//	            
//				p.waitFor();
//			} catch (IOException | InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}

	}

}
