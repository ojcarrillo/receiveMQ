package com.example.demo;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
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
	
	private final static String PATH = File.separator + "volume" + File.separator + "serv_contabilidad" + File.separator;
	private final static String BILL = "bill.txt";
	private final static String INVOICE = "invoice.txt";
	private final static String RECEIPTS = "recepits.txt";
	
	public void aggregarAlXML(String msg) throws IOException {
		/* primer caracter determina el tipo de transaccion B,I,R */
		String opc = msg.substring(0, 1);
		String msgAdd = msg.substring(1, 51); 
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
		if(fileName!=null) {
			aggMsg(msgAdd, PATH + fileName);
		} else {
			System.out.println("=============> formato no valido!!!");
		}
	}
	
	public void aggMsg(String msgAdd, String fileName) throws IOException {
		File file = new File(fileName);
		/* valida si existe el archivo, sino lo crea */
		if(!file.exists()) {			
//			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		/* agrega el nuevo mensaje al archivo */
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName), StandardOpenOption.APPEND)) {
		    writer.write(msgAdd);
		} catch (IOException ioe) {
		    System.err.format("IOException: %s%n", ioe);
		}
	}

}
