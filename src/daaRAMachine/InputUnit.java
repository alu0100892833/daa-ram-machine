package daaRAMachine;
import java.io.*;
import java.util.ArrayList;


/**
 * La clase InputUnit representa la unidad de entrada, que lee de un fichero la cinta con los datos de entrada.
 * @author oscardp96
 * @since 14-02-2017
 */
public class InputUnit {
	private ArrayList<Integer> inputTape;
	private int pointer;
	
	/**
	 * El constructor prepara la cinta de entrada en un ArrayList a partir de los datos contenidos en un fichero.
	 * @param filename Nombre del fichero que contiene la cinta de entrada.
	 * @throws IOException En el caso de que haya algún error en la apertura, lectura o cierre del fichero.
	 * @throws NumberFormatException En el caso de que el contenido del fichero no sea exclusivamente de números enteros.
	 */
	public InputUnit (String filename) throws IOException, NumberFormatException {
		File readFile = null; 
		BufferedReader openedFile = null;
		inputTape = new ArrayList<Integer>();
		pointer = 0;
		try {
			readFile = new File (filename);
			openedFile = new BufferedReader (new FileReader (readFile));
			String input = openedFile.readLine();
			String[] splitting = input.split("\\s+");
			for (int i=0; i < splitting.length; i++) 
				inputTape.add(Integer.parseInt(splitting[i]));	
			if (openedFile.readLine() != null)
				throw new NumberFormatException();
		}
		catch (NumberFormatException e) {
			System.out.println("Error en la lectura de la cinta de entrada. Tal vez contenga elementos inadecuados.");
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("Error en la lectura de la cinta de entrada. Asegúrese de que el fichero existe.");
		}
		catch (Exception e) {
			System.out.println("ERROR en la lectura del fichero. Puede contener elementos inadecuados o tal vez no exista. ");
		}
		finally {
			try {
				if (openedFile != null)
					openedFile.close();
			}
			catch (IOException ioe) {
				System.out.println("Error al cerrar la lectura de la cinta de entrada.");
				ioe.printStackTrace();
			}
		}
	}
	
	public int getPointer () {
		return pointer;
	}
	
	public Integer accessTape () throws IndexOutOfBoundsException {
		try {
			if (pointer < inputTape.size()) {
				pointer++;
				return inputTape.get(pointer-1);
			}
			else {
				throw new IndexOutOfBoundsException();
			}
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("La cinta de entrada se ha vaciado y se ha intentado acceder de nuevo.");
			System.out.println("El programa podría no estar bien diseñado o la cinta de entrada podría ser incompatible.");
			return null;
		}
		
	}
	
	
}
