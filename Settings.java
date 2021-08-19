/**
 * 
 */
package fr.eni.papeterie.dal;

import java.io.IOException;
import java.util.Properties;


/**
 * Classe en charge de
 * @author user
 * @version PapeterieDao - V1.0
 * @date 3 août 2021 - 15:21:48
 */
public class Settings {

	private static Properties propriete;

	static{
		propriete= new Properties();
		try{
			propriete.load(Settings.class.getResourceAsStream("Settings.properties.txt"));

			//ou en xml : propriete.loadFromXml(Settings.class.getRessourceAsStream("Settings.xml"));
		}
		catch(IOException e){
			e.printStackTrace();
			
		}
	
	}
	
	

	public static String getPropriete(String cle){
	
		String parametre=propriete.getProperty(cle);
		return parametre;
	
	}
}
