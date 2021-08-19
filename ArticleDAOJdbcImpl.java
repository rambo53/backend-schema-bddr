/**
 * 
 */
package fr.eni.papeterie.dal.jdbc;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.ArticleDAO;
import fr.eni.papeterie.dal.DALException;

/**
 * Classe en charge de
 * @author user
 * @version Papeterie - V1.0
 * @date 2 août 2021 - 09:44:39
 */
public class ArticleDAOJdbcImpl implements ArticleDAO{	
	
	private Connection con = null;
	private PreparedStatement pst=null;
	private ResultSet rs=null;
	
	//je met mes string d'accession à ma base de donnée en constante
	//pour éviter le risque d'injection sql malveillant
	
	private static final String SQL_INSERT = "INSERT INTO dbo.Articles"
									+"(reference,marque,designation,prixUnitaire,qteStock,grammage,couleur,type)"
									+" VALUES (?,?,?,?,?,?,?,?)";
	
	private static final String SQL_SELECT_BY_ID="SELECT * FROM dbo.Articles"
									+ " WHERE idArticle=?";
	
	private static final String SQL_SELECT_ALL="SELECT idArticle,reference,marque,designation,prixUnitaire,qteStock,grammage,couleur,type"
									+ " FROM dbo.Articles";
	
	private static final String SQL_SELECT_BY_MARQUE="SELECT * FROM dbo.Articles WHERE marque=?";
	
	private static final String SQL_SELECT_BY_MOT_CLE="SELECT * FROM dbo.Articles WHERE designation LIKE ?";
	
	private static final String SQL_UPDATE="UPDATE dbo.Articles "
									+ "SET reference=?,"
									+ "marque=?,"
									+ "designation=?,"
									+ "prixUnitaire=?,"
									+ "qteStock=?,"
									+ "grammage=?,"
									+ "couleur=?"
									+ " WHERE idArticle=?";
	
	private static final String SQL_DELETE ="DELETE FROM dbo.Articles WHERE idArticle=?";
	
	/////////////////CONSTRUCTEUR//////////////////
	
	public ArticleDAOJdbcImpl() {
		super();
	}

	/////////////////METHODE//////////////////
	
	private void fermetureConPstRs() throws SQLException {
		if(con!=null) {
			con.close();
		}
		if(pst!=null) {
			pst.close();
		}
		if(rs!=null) {
			rs.close();
		}
	}

	/////////////////MANIPULATION DATABASE//////////////////
	
	
	/**
	 * 
	 * Methode qui me retourne un article avec un id précis
	 * @param id
	 * @return
	 */
	public Article selectById(int id) throws DALException{
		
		Article article=null;
		
		try {
			con = JdbcTools.getConnection();
			
			pst=con.prepareStatement(SQL_SELECT_BY_ID);

			pst.setInt(1, id);

			rs = pst.executeQuery();

			if(rs.next()) {
				if(rs.getString("type").trim().equalsIgnoreCase("stylo")) {
					article=new Stylo();
				}
				else {
					article=new Ramette();
				}
				article.setIdArticle(rs.getInt("idArticle"));
				article.setReference(rs.getString("reference"));
				article.setMarque(rs.getString("marque"));
				article.setDesignation(rs.getString("designation"));
				article.setPrixUnitaire(rs.getFloat("prixUnitaire"));
				article.setStock(rs.getInt("qteStock"));
				if(article instanceof Stylo) {
					((Stylo) article).setCouleur(rs.getString("couleur"));
				}
				else if(article instanceof Ramette) {
					((Ramette) article).setGrammage(rs.getInt("grammage"));
				}
			}
		
			
			}
		catch (SQLException e) {
			throw new DALException("Problème exécution query dans selectById");
		}
		finally{
			try {
				fermetureConPstRs();
	
			} catch (SQLException e) {
				throw new DALException("Problème fermeture dans selectById");
			}
		}
		return article;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Methode qui me retourne une liste d'articles 
	 * @return
	 */
	public List<Article> selectAll() throws DALException{
		
		List<Article> articles=new ArrayList<>();
		Article article=null;

		try {
			con = JdbcTools.getConnection();
			
			pst=con.prepareStatement(SQL_SELECT_ALL);
			rs = pst.executeQuery();
		
			while(rs.next()) {
				if(rs.getString("type").trim().equalsIgnoreCase("Stylo")) {
					article=new Stylo();
				}
				else {
					article=new Ramette();
				}
				article.setIdArticle(rs.getInt("idArticle"));
				article.setReference(rs.getString("reference"));
				article.setMarque(rs.getString("marque"));
				article.setDesignation(rs.getString("designation"));
				article.setPrixUnitaire(rs.getFloat("prixUnitaire"));
				article.setStock(rs.getInt("qteStock"));
				if(article instanceof Stylo) {
					((Stylo) article).setCouleur(rs.getString("couleur"));
				}
				else if(article instanceof Ramette) {
					((Ramette) article).setGrammage(rs.getInt("grammage"));
				}
				articles.add(article);
			}
		
		} 
		catch (SQLException e) {
			throw new DALException("Problème exécution query dans selectAll");
		}
		finally{
			try {
				fermetureConPstRs();
				
			} catch (SQLException e) {
				throw new DALException("Problème fermeture dans selectAll");
			}
		}
		
		
		return articles;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Methode qui me met à jour un article
	 */
	
	public void update(Article article) throws DALException{

		try {
			con = JdbcTools.getConnection();
			pst =con.prepareStatement(SQL_UPDATE);
			
			pst.setString(1, article.getReference());
			pst.setString(2, article.getMarque());
			pst.setString(3, article.getDesignation());
			pst.setFloat(4, article.getPrixUnitaire());
			pst.setInt(5, article.getStock());
			if(article instanceof Ramette) {
				pst.setInt(6, ((Ramette) article).getGrammage());
				pst.setString(7, null);
			}
			else if(article instanceof Stylo) {
				pst.setInt(6, 0);
				pst.setString(7, ((Stylo) article).getCouleur());

			}
			pst.setInt(8, article.getIdArticle());
			pst.executeUpdate();
			
		} 
		catch (SQLException e) {
			throw new DALException("Problème exécution query dans update");
		}
		finally{
			try {
				fermetureConPstRs();
				
			} catch (SQLException e) {
				throw new DALException("Problème fermeture dans update");
			}
		}
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Methode qui me crée un nouvel article dans ma batabase
	 */
	
	
	public void insert(Article article) throws DALException{

		try {
			con = JdbcTools.getConnection();
			
			pst =con.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);
			
			//chaque chiffre correspond à un ? dans ma requète et !!!dans l'ordre!!!
			pst.setString(1, article.getReference());
			pst.setString(2, article.getMarque());
			pst.setString(3, article.getDesignation());
			pst.setFloat(4, article.getPrixUnitaire());
			pst.setInt(5, article.getStock());
			if(article instanceof Ramette) {
				pst.setInt(6, ((Ramette) article).getGrammage());
				pst.setString(7, null);
				pst.setString(8, "Ramette");
			}
			else if(article instanceof Stylo) {
				pst.setInt(6, 0);
				pst.setString(7, ((Stylo) article).getCouleur());
				pst.setString(8, "Stylo");
			}
			pst.executeUpdate();
			
			rs = pst.getGeneratedKeys();
			
			//me retourne ma clée auto généré et me permet de l'affecter à mon article
			
			if (rs.next()) {
                article.setIdArticle(rs.getInt(1));
            }
		} 
		catch (SQLException e) {
			throw new DALException("Problème exécution query dans insert");
		}
		finally{
			try {
				fermetureConPstRs();

			} catch (SQLException e) {
				throw new DALException("Problème fermeture dans insert");
			}
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * methode pour effacer un article
	 */
	
	public void delete(int id) throws DALException{
		
		try {
			con = JdbcTools.getConnection();
			pst=con.prepareStatement(SQL_DELETE);
			pst.setInt(1, id);
			pst.executeUpdate();
		} 
		catch (SQLException e) {
			throw new DALException("Problème exécution query dans delete");

		}
		finally{
			try {
				fermetureConPstRs();
			} catch (SQLException e) {
				throw new DALException("Problème fermeture dans delete");
			}
		}
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public List<Article> selectByMarque(String marque) throws DALException {
		List<Article> listParMarque=new ArrayList<Article>();
		Article article=null;

		try {
			con=JdbcTools.getConnection();

			pst=con.prepareStatement(SQL_SELECT_BY_MARQUE);
			pst.setString(1, marque);
			rs = pst.executeQuery();

			while(rs.next()) {
				if(rs.getString("type").trim().equalsIgnoreCase("stylo")) {
					article=new Stylo();
				}
				else {
					article=new Ramette();
				}
				article.setIdArticle(rs.getInt("idArticle"));
				article.setReference(rs.getString("reference"));
				article.setMarque(rs.getString("marque"));
				article.setDesignation(rs.getString("designation"));
				article.setPrixUnitaire(rs.getFloat("prixUnitaire"));
				article.setStock(rs.getInt("qteStock"));
				if(article instanceof Stylo) {
					((Stylo) article).setCouleur(rs.getString("couleur"));
				}
				else if(article instanceof Ramette) {
					((Ramette) article).setGrammage(rs.getInt("grammage"));
				}
				listParMarque.add(article);
			}
			
			
		} catch (SQLException e) {
			
			throw new DALException("Problème exécution query dans selectByMarque");
		}
		finally{
			try {
				fermetureConPstRs();
				
			} catch (SQLException e) {
				throw new DALException("Problème fermeture dans selectByMarque");
			}
		}
		
		return listParMarque;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<Article> selectByMotCle(String motCle) throws DALException {
		List<Article> listParMotCle=new ArrayList<Article>();
		Article article=null;

		try {
			con=JdbcTools.getConnection();
			
			pst=con.prepareStatement(SQL_SELECT_BY_MOT_CLE);
			pst.setString(1, "%"+motCle+"%");
			rs = pst.executeQuery();
				
			while(rs.next()) {
				if(rs.getString("type").trim().equalsIgnoreCase("stylo")) {
					article=new Stylo();
				}
				else {
					article=new Ramette();
				}
				article.setIdArticle(rs.getInt("idArticle"));
				article.setReference(rs.getString("reference"));
				article.setMarque(rs.getString("marque"));
				article.setDesignation(rs.getString("designation"));
				article.setPrixUnitaire(rs.getFloat("prixUnitaire"));
				article.setStock(rs.getInt("qteStock"));
				if(article instanceof Stylo) {
					((Stylo) article).setCouleur(rs.getString("couleur"));
				}
				else if(article instanceof Ramette) {
					((Ramette) article).setGrammage(rs.getInt("grammage"));
				}
				listParMotCle.add(article);
			}
			
		} catch (SQLException e) {
			
			throw new DALException("Problème exécution query dans selectByMotCle");
		}
		finally{
			try {
				fermetureConPstRs();
				
			} catch (SQLException e) {
				throw new DALException("Problème fermeture dans selecByMotCle");
			}
		}
		
		
		return listParMotCle;
	}
}
