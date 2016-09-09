package com.dtoskfer.presupuesto.negocio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BsBase {

	private static String gblPath = "";
	private static boolean gblIsLinux;
	private boolean gblDescErr;
	private static String gblServer = "";
	private static String gblUsuario = "";
	private static String gblPassword = "";
	private String gblWS = "";
	private int gblPort = 3306;
	private String gblEsquema = "";
	private String seedKey = "secret";
	private String message = "";
	private String almacenAnexos;

	public BsBase() throws Exception {
		if (leerParametros()) {
			gblServer = "jdbc:mysql://" + gblServer + ":" + String.valueOf(gblPort) + "/" + gblEsquema;
		} else {
			throw new Exception("Not found file params... :-( " + getPathFromURL("fileparams.xml"));
		}
	}

	public String getMessage() {
		if (this.message == null) {
			this.message = "Existen valores nulos para columnas obligatorias";
		} else {
			if (this.message.length() <= 0) {
				this.message = "Existen valores nulos para columnas obligatorias";
			}
		}
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getGblWS() {
		return gblWS;
	}

	public String getSeedKey() {
		return seedKey;
	}

	public String getGblEsquema() {
		return gblEsquema;
	}

	public boolean getGblDescErr() {
		return this.gblDescErr;
	}

	private boolean leerParametros() {
		boolean resultado = false;
		String filePath = getPathFromURL("fileparams.xml");
		File xmlFile = new File(filePath);
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList parametros = doc.getElementsByTagName("Parametro");
			Element parametro = null;
			int i;
			String strCodigo, valor;
			int codigo;
			if (parametros.getLength() > 0) {
				for (i = 0; i < parametros.getLength(); i++) {
					parametro = (Element) parametros.item(i);
					Node nodo = parametro.getElementsByTagName("Codigo").item(0).getFirstChild();
					strCodigo = nodo.getNodeValue();
					Node nodo_valor = parametro.getElementsByTagName("Valor").item(0).getFirstChild();
					valor = nodo_valor.getNodeValue();
					codigo = Integer.parseInt(strCodigo);
					switch (codigo) {
					case 0: // server db
						gblServer = valor;
						break;
					case 1: // user db
						gblUsuario = valor;
						break;
					case 2: // user password db
						gblPassword = valor;
						break;
					case 3: // schema name
						gblEsquema = valor;
						break;
					case 4: // url web service if required
						gblWS = valor;
						break;
					case 5: // Key seed for encrypt and decrypt text
						seedKey = valor;
						break;
					case 6: // port of oracle
						gblPort = Integer.parseInt(valor);
						break;
					case 7: // flag if required desc error or return generic
							// message
						gblDescErr = valor.toLowerCase().equals("true");
						break;
					case 8:
						almacenAnexos = valor;
						break;
					}
				}
				resultado = true;
			} else {
				resultado = false;
			}
		} catch (SAXException | ParserConfigurationException | IOException e1) {
			saveLog(e1.getMessage());
			resultado = false;
		}
		return resultado;
	}

	@SuppressWarnings("finally")
	public String getPathFromURL(String fileparams) {
		if (gblPath.length() <= 0) {
			URL url = this.getClass().getClassLoader().getResource(fileparams);
			File file = null;
			try {
				file = new File(url.toURI());
			} catch (URISyntaxException e) {
				file = new File(url.getPath());
			} finally {
				gblPath = file.getAbsolutePath();
				return gblPath;
			}
		} else {
			return gblPath;
		}
	}

	public static String getCurrentPath() {
		if (gblPath.length() > 0) {
			return gblPath;
		} else {
			gblPath = System.getProperty("user.dir");
			if (gblIsLinux) {
				if (!gblPath.endsWith("/")) {
					gblPath = gblPath + "/";
				}
			} else {
				if (!gblPath.endsWith("\\")) {
					gblPath = gblPath + "\\";
				}
			}

			if (gblPath.contains(".")) {
				gblPath = gblPath.replace(".", "");
			}
			return gblPath;
		}
	}

	public static void saveLog(String message) {
		try {
			String filename = gblPath + getStringCurrentDate() + ".log";
			FileWriter fw = new FileWriter(filename, true);
			fw.write(getStringCurrentDateTime() + "     " + message + "\n");
			fw.close();
		} catch (IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	public static String getStringCurrentDate() {
		String strDateTime = "";
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		Date date = new Date();
		strDateTime = dateFormat.format(date);
		return strDateTime;
	}

	public static String getStringCurrentDateTime() {
		String strDateTime = "";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		strDateTime = dateFormat.format(date);
		return strDateTime;
	}

	public static String getGblUsuario() {
		return gblUsuario;
	}

	public static String getGblPassword() {
		return gblPassword;
	}

	public static String getGblServer() {
		return gblServer;
	}

	public String getAlmacenAnexos() {
		return almacenAnexos;
	}

}
