/**
 * 
 */
package fr.eni.papeterie.dal;

import fr.eni.papeterie.dal.jdbc.ArticleDAOJdbcImpl;

/**
 * Classe en charge de
 * @author user
 * @version PapeterieDao - V1.0
 * @date 3 août 2021 - 15:12:57
 */
public class DAOFactory {

	public static ArticleDAO getArticleDAO(String enregistrement) {
		if("jdbc".equals(enregistrement)) {
			return new ArticleDAOJdbcImpl();
		}
		
		return null;		
	}
}
