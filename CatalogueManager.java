/**
 * 
 */
package fr.eni.papeterie.bll;

import java.util.List;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.ArticleDAO;
import fr.eni.papeterie.dal.DALException;
import fr.eni.papeterie.dal.DAOFactory;


/**
 * Classe en charge de
 * @author user
 * @version PapeterieBll - V1.0
 * @date 5 août 2021 - 10:00:55
 */
public class CatalogueManager {
	
	private static CatalogueManager instance;
	private ArticleDAO artDao = DAOFactory.getArticleDAO("jdbc");
	

	private CatalogueManager() {
		super();
	}
	
	
	public static CatalogueManager getInstance() {
		if(CatalogueManager.instance==null) {
			CatalogueManager.instance=new CatalogueManager();
		}

		return instance;
	}
	

	/**
	 * Methode
	 * @param art
	 */
	public void addArticle(Article art) throws BLLException{
		validerArticle(art);
		
		try {
			//List<Article> lstArticles=artDao.selectByMotCle(art.getReference());
			
			//if(lstArticles==null) {
				
			artDao.insert(art);
		
			//}
			
		} catch (DALException e) {
		
			e.printStackTrace();
		}
		
	}
	
	public Article getArticle(int index) throws BLLException {

		try {
			return artDao.selectById(index);
			
		} catch (DALException e) {
			throw new BLLException("erreur getarticle",e);
		}
	}

	/**
	 * Methode
	 * @return
	 */
	public List<Article> getCatalogue() throws BLLException{
		List<Article> lstArticles=null;
		
		try {
			lstArticles=artDao.selectAll();
		} catch (DALException e) {
			
			e.printStackTrace();
		}
		
		return lstArticles;
	}

	/**
	 * Methode
	 * @param stylo
	 */
	public void updateArticle(Article article) throws BLLException{
		validerArticle(article);
		
		try {
			artDao.update(article);
		} catch (DALException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Methode
	 * @param idArticle
	 */
	public void removeArticle(Integer idArticle) throws BLLException{
		
		try {
			artDao.delete(idArticle);
		} catch (DALException e) {

			e.printStackTrace();
		}
		
	}
	
	public void validerArticle(Article art) throws BLLException{
		boolean erreur=false;
		StringBuilder sb = new StringBuilder();
		sb.append("Problème :\n");
		
		if(art.getReference()==null || art.getReference().isBlank()) {
			sb.append("- réf vide \n");
			erreur=true;
		}
		
		if(art.getMarque()==null || art.getMarque().isBlank()) {
			sb.append("- marque vide \n");
			erreur=true;
		}
		
		if(art.getDesignation()==null || art.getDesignation().isBlank()) {
			sb.append("- Designation vide \n");
			erreur=true;
		}
		if(art.getPrixUnitaire()<0) {
			sb.append("- prix negatif \n");
			erreur=true;
		}
		
		if(art.getStock()<0) {
			sb.append("- stock negatif \n");
			erreur=true;
		}
		
		if(art instanceof Stylo && ((Stylo)art).getCouleur()==null && ((Stylo)art).getCouleur().isBlank()) {
			sb.append("- couleur non renseignée \n");
			erreur=true;
		}
			
		if(art instanceof Ramette && ((Ramette)art).getGrammage()<0) {
			sb.append("- grammage negatif \n");
			erreur=true;
		}
		
		if(erreur) {
			throw new BLLException(sb.toString());
		}
	}

}
