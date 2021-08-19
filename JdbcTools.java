/**
 * 
 */
package fr.eni.papeterie.dal.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.eni.papeterie.dal.Settings;

/**
 * Classe en charge de
 * @author user
 * @version Papeterie - V1.0
 * @date 3 août 2021 - 15:24:50
 */
public class JdbcTools {

	private static String url;
	private static String user;
	private static String password;


	static{
		url = Settings.getPropriete("url");
		user = Settings.getPropriete("user");
		password = Settings.getPropriete("mdp");
	}

	public static Connection getConnection() throws SQLException{
		
		return DriverManager.getConnection(url, user, password);

	}
	
}
