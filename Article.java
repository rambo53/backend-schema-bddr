/**
 * 
 */
package fr.eni.papeterie.bo;

/**
 * Classe en charge de
 * @author user
 * @version Papeterie - V1.0
 * @date 29 juil. 2021 - 09:39:24
 */
public abstract class Article {

	private Integer idArticle;
	private String reference;
	private String marque;
	private String designation;
	private float prixUnitaire;
	private int stock;
	
///////////////////////constructeur//////////////////////
	
	public Article() {
		super();
	}
	


	public Article( String reference, String marque,String designation, float prixUnitaire, int stock) {
		super();
		this.reference = reference;
		this.marque = marque;
		this.designation = designation;
		this.prixUnitaire = prixUnitaire;
		this.stock = stock;
	}



	public Article(Integer idArticle,  String reference, String marque, String designation, float prixUnitaire, int stock) {
		this(reference,marque,designation,prixUnitaire,stock);
	}

///////////////////////methode//////////////////////

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Article : ");
		sb.append("idArticle="+this.getIdArticle()+", ");
		sb.append("Reference="+this.getReference()+", ");
		sb.append("Marque="+this.getMarque()+", ");
		sb.append("Designation="+this.getDesignation()+", ");
		sb.append("PrixUnitaire="+String.format("%.2f", this.getPrixUnitaire())+"€, ");
		sb.append("qteStock="+this.getStock()+" ");
		
		return sb.toString();
	}

///////////////////////getter setter//////////////////////

	public Integer getIdArticle() {
		return idArticle;
	}



	public void setIdArticle(Integer idArticle) {
		this.idArticle = idArticle;
	}



	public String getReference() {
		return reference;
	}



	public void setReference(String reference) {
		this.reference = reference;
	}



	public String getMarque() {
		return marque;
	}



	public void setMarque(String marque) {
		this.marque = marque;
	}



	public String getDesignation() {
		return designation;
	}



	public void setDesignation(String designation) {
		this.designation = designation;
	}



	public float getPrixUnitaire() {
		return prixUnitaire;
	}



	public void setPrixUnitaire(float prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}



	public int getStock() {
		return stock;
	}



	public void setStock(int stock) {
		this.stock = stock;
	}

	
	
	
	
}
