package daaRAMachine;
import java.io.*;
import java.util.ArrayList;

public class InputUnit {
	private ArrayList<Integer> InputTape;
	
	
	public InputUnit (String filename) throws IOException {
		File readFile = null; 
		BufferedReader openedFile = null;
		try {
			readFile = new File (filename);
			openedFile = new BufferedReader (new FileReader (readFile));
			String input = openedFile.readLine();
			String[] splitting = input.split("\\s+");
			for (int i=0; i < splitting.length; i++) 
				InputTape.add(Integer.parseInt(splitting[i]));	
			if (openedFile.readLine() != null)
				throw new IOException();
		}
		catch (NumberFormatException|IOException e) {
			System.out.println("Error en la lectura de la cinta de entrada. Tal vez contenga elementos inadecuados.");
			e.printStackTrace();
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
}
