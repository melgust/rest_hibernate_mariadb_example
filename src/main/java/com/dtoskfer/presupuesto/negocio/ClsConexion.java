package com.dtoskfer.presupuesto.negocio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClsConexion {
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ClsConexion() {
		
	}

	private Statement comando;
	private Connection conexion;
	
	public boolean openDB(String server, String usuario, String password) {
		boolean result = false;
		conexion = null;
		try {
			/*If use oracle client*/
			//System.setProperty("oracle.net.tns_admin", gblTNS);
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(server, usuario, password);
			comando = conexion.createStatement();
			result = true;
		} catch (Exception e) {
			setMessage(e.getMessage());
		}
		return result;
	}
	
	public void closeDB() {
		try {
			comando.close();
			conexion.close();
			conexion = null;
		} catch (SQLException e) {
			setMessage(e.getMessage());
		}
	}
	
	public ResultSet executeSelect(String sql) throws SQLException {
		ResultSet rs = comando.executeQuery(sql);
		return rs;
	}
	
}
