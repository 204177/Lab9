package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.porto.model.Articolo;
import it.polito.tdp.porto.model.Autore;
import it.polito.tdp.porto.model.Connessione;

public class PortoDAO {
	
	public List<Autore> getAutori() {
		List<Autore> lista = new ArrayList<Autore>();
		
		Connection conn = DBConnect.getConnection();
		String sql = "select * from creator";
		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) 
			{
				Autore c = new Autore(res.getInt("id_creator"), res.getString("family_name"), res.getString("given_name"));
				lista.add(c);
			}
			
			res.close();
			conn.close();
			
			return lista;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Connessione> getConnessioni(){
		
		List<Connessione> lista = new ArrayList<Connessione>();
		
		Connection conn = DBConnect.getConnection();
		String sql = "select * from authorship";
		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) 
			{
				Connessione c = new Connessione(res.getInt("id_authorship"), res.getLong("eprintid"), res.getInt("id_creator"));
				lista.add(c);
			}
			
			res.close();
			conn.close();
			
			return lista;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Articolo> getArticoli(){
		
		List<Articolo> lista = new ArrayList<Articolo>();
		
		Connection conn = DBConnect.getConnection();
		String sql = "select * from article";
		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) 
			{
				Articolo c = new Articolo(res.getLong("eprintid"), res.getInt("year"), res.getString("title"));
				lista.add(c);
			}
			
			res.close();
			conn.close();
			
			return lista;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
