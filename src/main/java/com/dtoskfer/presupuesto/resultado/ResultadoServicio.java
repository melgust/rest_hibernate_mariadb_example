/**
 * define all stutus restful service and message response
 */
package com.dtoskfer.presupuesto.resultado;

/**
 * @author mcali
 *
 */
public enum ResultadoServicio {
	fail("error"), success("OK"), warning("warning");
	private final String value;
    private ResultadoServicio(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    
    public String getMessage() {
    	String msg;
    	switch(value.toLowerCase()) {
    	case "ok":
    		msg = "Proceso completado con éxito";
    		break;
    	case "warning":
    		msg = "El proceso se ejecuto con errores";
    		break;
    	default :
    		msg = "Error al realizar el proceso, favor de intentarlo en otro momento";
    		break;
    	}
    	return msg;
    }

}
