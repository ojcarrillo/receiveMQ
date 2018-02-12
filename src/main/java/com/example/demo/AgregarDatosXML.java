package com.example.demo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Permite manejar los mensajes recibidos y los agrega a los archivos de transacciones
 * @author Administrador
 *
 */
public class AgregarDatosXML {
	
	private final static String PATH = File.separator + "data" + File.separator + "tmp" + File.separator;
	private final static String BILL = "bill.txt";
	private final static String INVOICE = "invoice.txt";
	private final static String RECEIPTS = "recepits.txt";
	
	public void aggregarAlXML(String msg) {
		/* primer caracter determina el tipo de transaccion B,I,R */
		String opc = msg.substring(0, 1);
		String msgAdd = msg.substring(1, msg.length()); 
		String fileName = null;
		/* arma el nombre del archivo para agregar el mensaje */
		if("B".equals(opc)) {
			/* Bills */
			fileName = BILL;
		}else if("I".equals(opc)) {
			/* Invoice */
			fileName = INVOICE;
		}else if("R".equals(opc)) {
			/* Recepit */
			fileName = RECEIPTS;
		}
		/* agrega la transaccion al archivo correspondiente */
		aggMsg(msgAdd, fileName);
	}
	
	public void aggMsg(String msgAdd, String fileName) {
		try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(Paths.get(PATH + fileName), StandardOpenOption.APPEND))) {
		    out.write(msgAdd.getBytes());
		    out.close();
		} catch (IOException e) {
		    System.err.println(e);
		}
	}

}