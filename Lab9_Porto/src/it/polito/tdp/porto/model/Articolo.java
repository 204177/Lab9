package it.polito.tdp.porto.model;

import java.util.*;

public class Articolo {
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Articolo other = (Articolo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	private long id;
	private int anno;
	private String titolo;
	
	List<Autore> autori = new LinkedList<Autore>();
	
	public Articolo(long id, int anno, String titolo) {
		super();
		this.id = id;
		this.anno = anno;
		this.titolo = titolo;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

}
