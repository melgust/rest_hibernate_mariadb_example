package com.dtoskfer.presupuesto.resultado;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.dtoskfer.presupuesto.pojo.TcComputadora;

@XmlRootElement
public class ObjResultadoComputadora {

	private String status;
	private String message;
	private List<TcComputadora> data;
	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<TcComputadora> getData() {
		return data;
	}
	
	public void setData(List<TcComputadora> a) {
		this.data = a;
	}
	
}
