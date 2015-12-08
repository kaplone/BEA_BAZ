package models;

public class Settings {
	
	private static String adresse;
	private static String login;
	private static String pass;
	private static String port;
	private static String base;
	
	public Settings(){
		this(null, null, null, null, null);
	}
		
	public Settings(String adresse, String login, String pass, String port, String base) {
		Settings.adresse = adresse;
		Settings.login = login;
		Settings.pass = pass;
		Settings.port = port;
		Settings.base = base;
	}

	public static String getAdresse() {
		return adresse;
	}

	public static void setAdresse(String adresse) {
		Settings.adresse = adresse;
	}

	public static String getLogin() {
		return login;
	}

	public static void setLogin(String login) {
		Settings.login = login;
	}

	public static String getPass() {
		return pass;
	}

	public static void setPass(String pass) {
		Settings.pass = pass;
	}

	public static String getPort() {
		return port;
	}

	public static void setPort(String port) {
		Settings.port = port;
	}

	public static String getBase() {
		return base;
	}

	public static void setBase(String base) {
		Settings.base = base;
	}
	
	

}
