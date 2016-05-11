package it.polito.tdp.porto.model;

public class Connessione {
	
	private int id;
	private long idArticolo;
	private int idAutore;
	
	public Connessione(int id, long idArticolo, int idAutore) {
		super();
		this.id = id;
		this.idArticolo = idArticolo;
		this.idAutore = idAutore;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getIdArticolo() {
		return idArticolo;
	}

	public void setIdArticolo(long idArticolo) {
		this.idArticolo = idArticolo;
	}

	public int getIdAutore() {
		return idAutore;
	}

	public void setIdAutore(int idAutore) {
		this.idAutore = idAutore;
	}


}
