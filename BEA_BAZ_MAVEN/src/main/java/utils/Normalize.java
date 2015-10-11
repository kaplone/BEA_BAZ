package utils;

import java.util.ArrayList;
import java.util.Arrays;

public class Normalize {

	public static String normalize (String s ){
		
		return s.replace('\'', '_')
		        .replace('\n', '_')
		        .replace(' ', '_')
		        .replace("°", "")
		        .replace("é", "e")
		        .replace("à", "a")
		        .replace("(", "")
		        .replace(")", "")
		        .replace("/", "_")
		        .replace("=", "_")
		        .toLowerCase();
	}

	public static Object normalizeField(String s) {
		return s.replace('"', ' ');
	}
	
}
