package daaRAMachine;
import java.io.*;
import java.util.ArrayList;

/**
 * La clase OutputUnit representa la unidad de salida. Mantiene un ArrayList con los valores de la cinta de salida. 
 * Al finalizar el programa, los escribe en el fichero de salida.
 * @author oscardp96
 * @since 14-02-2017
 */
public class OutputUnit {
	private ArrayList<Integer> outputTape;
	String outputFile;
	
	
	public OutputUnit (String file) {
		outputTape = new ArrayList<Integer>();
		outputFile = file;
	}
	
	/**
	 * Escribe en la siguiente posición de la cinta.
	 * @param data Es el dato a escribir.
	 */
	public void write (Integer data) {
		outputTape.add(data);
	}
	
	/**
	 * Cuando el programa finaliza, por la razón que sea, este método es invocado
	 * para escribir la cinta de salida en el fichero de salida.
	 * @param filename Nombre del fichero de salida.
	 * @throws IOException En el caso de que haya algún problema en el manejo de ficheros.
	 */
	public void close (boolean error) throws IOException {
		String writeTape = new String();
		for (int i=0; i < outputTape.size(); i++)
			writeTape = writeTape + outputTape.get(i) + "  ";
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter (outputFile);
			bw = new BufferedWriter (fw);
			bw.write(writeTape);
			if (error) {
				bw.newLine();
				bw.write("IMPORTANTE: LA EJECUCIÓN NO FINALIZÓ CORRECTAMENTE");
			}
		}
		catch (IOException e) {
			System.out.println("Error en la apertura del fichero de salida");
			e.printStackTrace();
		}
		finally {
			try {
				if (bw != null) 
					bw.close();
				if (fw != null)
					fw.close();
			}
			catch (IOException e) {
				System.out.println("Error en el cierre del fichero de salida");
				e.printStackTrace();
			}
			
		}
	}
	
	
}
