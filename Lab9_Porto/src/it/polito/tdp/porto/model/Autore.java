package it.polito.tdp.porto.model;

import java.util.*;

public class Autore {

	private int id;
	private String cognome;
	private String nome;
	
	public List<Articolo> articoli = new LinkedList<Articolo>();

	public Autore(int id, String cognome, String nome) {
		super();
		this.id = id;
		this.cognome = cognome;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Autore other = (Autore) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
