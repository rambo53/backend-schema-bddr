/**
 * 
 */
package fr.eni.papeterie.dal;

import java.util.List;

import fr.eni.papeterie.bo.Article;


/**
 * Classe en charge de
 * @author user
 * @version PapeterieDao - V1.0
 * @date 3 août 2021 - 15:12:41
 */
public interface ArticleDAO {

	Article selectById(int id) throws DALException;
	
	List<Article> selectAll() throws DALException;
	
	List<Article> selectByMarque(String marque) throws DALException;
	
	List<Article> selectByMotCle(String motCle) throws DALException;
	
	void update(Article article) throws DALException;
	
	void insert(Article article) throws DALException;
	
	void delete(int id) throws DALException;
}
