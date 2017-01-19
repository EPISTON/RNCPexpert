package com.courtalon.qualimetrieExercice1Form;

public class Produit {
	private int id;
	private String nom;
	private double prix;
	private double poids;
	private int stock;
	
	public int getId() {return id;}
	public void setId(int id) {
		if (id < 0)
			throw new IdException("id du produit negative");
		this.id = id;
	}
	public String getNom() {return nom;}
	public void setNom(String nom) {
		if (nom == null || nom.length() < 3 || nom.length() > 50)
			throw new NomException("nom du produit invalide");
		this.nom = nom;
	}
	public double getPrix() {return prix;}
	public void setPrix(double prix) {
		if (prix < 0.0 || prix > 10000)
			throw new PrixException("le prix du produit est invalide");
		this.prix = prix;
	}
	public double getPoids() {return poids;}
	public void setPoids(double poids) {
		if (poids < 0.01 || poids > 50.0)
			throw new PoidsException("poids du produit invalide");
		this.poids = poids;
	}
	public int getStock() {return stock;}
	public void setStock(int stock) {
		if (stock < 0)
			throw new StockException("le stock ne peut etre negatif");
		this.stock = stock;
	}
	
	public Produit() { this(0, "", 0.0, 0.01, 0);}
	public Produit(int id, String nom, double prix, double poids, int stock) {
		super();
		setId(id);
		setNom(nom);
		setPrix(prix);
		setPoids(poids);
		setStock(stock);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		long temp;
		temp = Double.doubleToLongBits(poids);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(prix);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + stock;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produit other = (Produit) obj;
		if (id != other.id)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (Double.doubleToLongBits(poids) != Double.doubleToLongBits(other.poids))
			return false;
		if (Double.doubleToLongBits(prix) != Double.doubleToLongBits(other.prix))
			return false;
		if (stock != other.stock)
			return false;
		return true;
	}

}
