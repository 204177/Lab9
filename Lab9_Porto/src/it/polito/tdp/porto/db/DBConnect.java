package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBConnect {

	//private static ComboPooledDataSource dataSource = null;
	private static String jdbcURL="jdbc:mysql://localhost/porto?user=root&password=";
		
		public static Connection getConnection() {
			Connection conn;
			try {
				conn = DriverManager.getConnection(jdbcURL);
				return conn;
				/*
				if(dataSource == null){
					dataSource = new ComboPooledDataSource();
					dataSource.setDriverClass("org.mysql.Driver");
					dataSource.setJdbcUrl(jdbcURL);
				}
				return dataSource.getConnection();
				*/
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Errore connessione database", e);
			}
		}
}
