package com.dtoskfer.presupuesto.negocio;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.dtoskfer.presupuesto.pojo.TcComputadora;

/*
 * Es obligatorio heredar la clase BsBase que contiene el metodo que lee el archivo de parametros.
 * */

public class BsComputadora extends BsBase {
	
	/*
	 * SessionFactory factory
	 * No se declara como variable global debido a que no todos los metodos lo usaran.
	 * */
	
	public BsComputadora() throws Exception {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public List<TcComputadora> getLista() {
		List<TcComputadora> lista = null;
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session sesion = factory.openSession();
		try {
			Query query = sesion.createQuery("FROM TcComputadora");
		    List<?> data = query.list();
		    lista = new ArrayList<TcComputadora>();
		    if (data.size() > 0) {
		    	lista = (List<TcComputadora>) data;
		    }
		} catch (Exception e) {
			lista = null;
			setMessage(e.getMessage());
		} finally {
			sesion.close();
			factory.close();
		}
		return lista;
	}
	
	public TcComputadora getComputadora(int id) {
		TcComputadora v = null;
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session sesion = factory.openSession();
		try {
			Query query = sesion.createQuery("FROM TcComputadora WHERE computadoraId = :id");
			query.setParameter("id", id);
		    List<?> data = query.list();
		    if (data.size() > 0) {
		    	v = (TcComputadora) data.get(0);
		    } else {
		    	setMessage("No hay datos");
		    }
		} catch (Exception e) {
			setMessage(e.getMessage());
		} finally {
			sesion.close();
			factory.close();
		}
		return v;
	}
	
		
	public int setComputadora(TcComputadora v) {
		int result = 0;
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session sesion = factory.openSession();
		try {
			Transaction tx = null;
			tx = sesion.beginTransaction();
			try {
				result = (int) sesion.save(v);
			    tx.commit();
			} catch (Exception e) {
				if (tx != null) { 
					tx.rollback();
				}
				result = 0;
				setMessage(e.getMessage());
			}
		} catch (JDBCException ex) {
			result = 0;
			setMessage(ex.getSQLState());
		} finally {
			sesion.close();
			factory.close();
		}
		return result;
	}
	
	public boolean updComputadora(TcComputadora v) {
		boolean result = false;
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session sesion = factory.openSession();
		try {
			Transaction tx = null;
			tx = sesion.beginTransaction();
			try {
				sesion.update(v);
				tx.commit();
			    result = true;
			} catch (Exception e) {
				if (tx != null) { 
					tx.rollback();
				}
				result = false;
				setMessage(e.getMessage());
			}
		} catch (Exception ex) {
			result = false;
			setMessage(ex.getMessage());
		} finally {
			sesion.close();
			factory.close();
		}
		return result;
	}
	
	public boolean delComputadora(int id) {
		boolean result = false;
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session sesion = factory.openSession();
		try {
			Transaction tx = null;
			tx = sesion.beginTransaction();
			try {
				TcComputadora v;
				Query query = sesion.createQuery("FROM TcComputadora WHERE computadoraId = :id");
				query.setParameter("id", id);
			    List<?> data = query.list();
			    if (data.size() > 0) {
			    	v = (TcComputadora) data.get(0);
			    	sesion.update(v);
			    } else {
			    	setMessage("No existe datos para el identificador indicado");
			    }
			    tx.commit();
			    result = true;
			} catch (Exception e) {
				if (tx != null) { 
					tx.rollback();
				}
				result = false;
				setMessage(e.getMessage());
			}
		} catch (Exception ex) {
			result = false;
			setMessage(ex.getMessage());
		} finally {
			sesion.close();
			factory.close(); 
		}
		return result;
	}
	
	/*
	 * para obtener datos sin usar hibernate
	 * Recomendable usarlo cuando se utilice JOIN
	 * 
	 * */
	
	public List<TcComputadora> getListado() {
		List<TcComputadora> lista = null;
		ClsConexion ClData;
		try {
			ClData = new ClsConexion();
			if (ClData.openDB(getGblServer(), getGblUsuario(), getGblPassword())) {
				try {
					String sql = "SELECT computadora_id, hostname, direccion_ip, fecha_registro "
							+ "FROM tc_computadora";
					ResultSet rs = ClData.executeSelect(sql);
					lista = new ArrayList<TcComputadora>();
					TcComputadora v;
					while (rs.next()) {
						v = new TcComputadora();
						v.setComputadoraId(rs.getInt(1));
						v.setHostname(rs.getString(2));
						v.setDireccionIp(rs.getString(3));
						v.setFechaRegistro(rs.getTimestamp(4));
				        lista.add(v);
					}
				} catch (Exception e) {
					lista = null;
					setMessage(e.getMessage());
				}
			} else {
				setMessage("Error al conectar a la base de datos: " + ClData.getMessage());
			}
			ClData.closeDB();
		} catch (Exception e1) {
			setMessage(e1.getMessage());
		}
		return lista;
	}
	
}
