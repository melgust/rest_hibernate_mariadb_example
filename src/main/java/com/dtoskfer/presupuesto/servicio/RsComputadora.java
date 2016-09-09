package com.dtoskfer.presupuesto.servicio;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.dtoskfer.presupuesto.negocio.BsComputadora;
import com.dtoskfer.presupuesto.pojo.TcComputadora;
import com.dtoskfer.presupuesto.resultado.ObjResultadoComputadora;
import com.dtoskfer.presupuesto.resultado.ResultadoServicio;

/**
 * Root resource (exposed at "menu" path)
 */
@Path("/computadora")
public class RsComputadora {
	
	@GET
	@Path("/lista")
	@Produces("application/json;charset=UTF-8")
	public ObjResultadoComputadora getLista() {
		ObjResultadoComputadora result = new ObjResultadoComputadora();
		try {
			List<TcComputadora> lista = new ArrayList<TcComputadora>();
			BsComputadora data = new BsComputadora();
			lista = data.getLista();
			if (lista == null) {
				result.setStatus(ResultadoServicio.fail.getValue());
				if (data.getGblDescErr()) {
					result.setMessage(data.getMessage());
				} else {
					result.setMessage(ResultadoServicio.fail.getMessage());
				}
			} else {
				result.setData(lista);
				result.setStatus(ResultadoServicio.success.getValue());
				result.setMessage(ResultadoServicio.success.getMessage());
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setStatus(ResultadoServicio.fail.getValue());
		}
		return result;
	}
	
	@GET
	@Path("/listado")
	@Produces("application/json;charset=UTF-8")
	public ObjResultadoComputadora getListado() {
		ObjResultadoComputadora result = new ObjResultadoComputadora();
		try {
			List<TcComputadora> lista = new ArrayList<TcComputadora>();
			BsComputadora data = new BsComputadora();
			lista = data.getListado();
			if (lista == null) {
				result.setStatus(ResultadoServicio.fail.getValue());
				if (data.getGblDescErr()) {
					result.setMessage(data.getMessage());
				} else {
					result.setMessage(ResultadoServicio.fail.getMessage());
				}
			} else {
				result.setData(lista);
				result.setStatus(ResultadoServicio.success.getValue());
				result.setMessage(ResultadoServicio.success.getMessage());
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setStatus(ResultadoServicio.fail.getValue());
		}
		return result;
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json;charset=UTF-8")
	public ObjResultadoComputadora getComputadora(@PathParam("id") int id) {
		ObjResultadoComputadora result = new ObjResultadoComputadora();
		try {
			BsComputadora data = new BsComputadora();
			TcComputadora v = data.getComputadora(id);
			if (v == null) {
				result.setStatus(ResultadoServicio.fail.getValue());
				if (data.getGblDescErr()) {
					result.setMessage(data.getMessage());
				} else {
					result.setMessage(ResultadoServicio.fail.getMessage());
				}
			} else {
				List<TcComputadora> lista = new ArrayList<TcComputadora>();
				lista.add(v);
				result.setData(lista);
				result.setStatus(ResultadoServicio.success.getValue());
				result.setMessage(ResultadoServicio.success.getMessage());
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setStatus(ResultadoServicio.fail.getValue());
		}
		return result;
	}
			
	@POST
	@Path("/agregar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json;charset=UTF-8")
	public ObjResultadoComputadora setComputadora(TcComputadora v) {
		ObjResultadoComputadora result = new ObjResultadoComputadora();
		try {
			BsComputadora data = new BsComputadora();
			int id = data.setComputadora(v);
			if (id <= 0) {
				result.setStatus(ResultadoServicio.fail.getValue());
				if (data.getGblDescErr()) {
					result.setMessage(data.getMessage());
				} else {
					result.setMessage(ResultadoServicio.fail.getMessage());
				}
			} else {
				List<TcComputadora> lista = new ArrayList<TcComputadora>();
				lista.add(v);
				result.setData(lista);
				result.setStatus(ResultadoServicio.success.getValue());
				result.setMessage(ResultadoServicio.success.getMessage());
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setStatus(ResultadoServicio.fail.getValue());
		}
		return result;
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json;charset=UTF-8")
	public ObjResultadoComputadora updComputadora(@PathParam("id") int id, TcComputadora v) {
		ObjResultadoComputadora result = new ObjResultadoComputadora();
		try {
			if (id == v.getComputadoraId()) {
				BsComputadora data = new BsComputadora();
				boolean res = data.updComputadora(v);
				if (!res) {
					result.setStatus(ResultadoServicio.fail.getValue());
					if (data.getGblDescErr()) {
						result.setMessage(data.getMessage());
					} else {
						result.setMessage(ResultadoServicio.fail.getMessage());
					}
				} else {
					result.setStatus(ResultadoServicio.success.getValue());
					result.setMessage(ResultadoServicio.success.getMessage());
				}
			} else {
				result.setMessage("Los identificadores no coinciden");
				result.setStatus(ResultadoServicio.fail.getValue());
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setStatus(ResultadoServicio.fail.getValue());
		}
		return result;
	}
	
	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json;charset=UTF-8")
	public ObjResultadoComputadora delComputadora(@PathParam("id") int id) {
		ObjResultadoComputadora result = new ObjResultadoComputadora();
		try {
			BsComputadora data = new BsComputadora();
			boolean res = data.delComputadora(id);
			if (!res) {
				result.setStatus(ResultadoServicio.fail.getValue());
				if (data.getGblDescErr()) {
					result.setMessage(data.getMessage());
				} else {
					result.setMessage(ResultadoServicio.fail.getMessage());
				}
			} else {
				result.setStatus(ResultadoServicio.success.getValue());
				result.setMessage(ResultadoServicio.success.getMessage());
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setStatus(ResultadoServicio.fail.getValue());
		}
		return result;
	}
	
}
