package daaRAMachine;
import java.io.*;

/**
 * La clase ControlUnit gestiona el funcionamiento de la Máquina RAM. 
 * Guarda referencias a los demás elementos de la máquina, así como un contador de las instrucciones ejecutadas.
 * @author oscardp96
 * @since 14-02-2017
 */
public class ControlUnit {
	private int executedInstructions;
	private int instructionPointer;
	private InputUnit inTape;
	private OutputUnit outTape;
	private DataMemory registers;
	private ProgramMemory executing;
	
	
	/**
	 * El constructor inicializa todos los elementos de la máquina RAM de los que se vale la ControlUnit para realizar sus funciones.
	 * Lee el fichero con las instrucciones y, si no son comentarios, las inicializa como instrucciones y las carga en la memoria de programa.
	 * @param inputFile Nombre del fichero con la cinta de entrada.
	 * @param outputFile Nombre del fichero en el que se escribirá la cinta de salida.
	 * @param code Nombre del fichero con las instrucciones a ejecutar.
	 * @throws Exception Puede ser de varios tipos, siendo IOException la más común por errores en la apertura y cierre de ficheros, aunque también hay otras relativas a la creación de los objetos Instruction en el caso de que el formato de instrucción no sea correcto
	 */
	public ControlUnit (String inputFile, String outputFile, String code) {
		executedInstructions = 0;
		instructionPointer = 0;
		outTape = new OutputUnit (outputFile);
		registers = new DataMemory ();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			inTape = new InputUnit (inputFile);
			executing = new ProgramMemory();
			fr = new FileReader (code);
			br = new BufferedReader (fr);
			String readingLine;
			while ((readingLine = br.readLine()) != null)
				if (readingLine.charAt(0) != '#') {
					Instruction newIns = new Instruction (readingLine);
					executing.loadInstruction(newIns);
				}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (fr != null)
					fr.close();
				if (br != null)
					br.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getExecutedInstructions () {
		return executedInstructions;
	}
	
	/**
	 * Este método ejecuta el programa cargado en la ProgramMemory. 
	 * En caso de que se diera cualquier excepción, escribiría la cinta de salida en el fichero correspondiente.
	 */
	public void run () {
		try {
			while (instructionPointer < executing.size()) {
				Instruction toDo = executing.readInstruction(instructionPointer);
				execute(toDo);
				executedInstructions++;
			}
			outTape.close(false);
			System.out.println("El número de instrucciones ejecutadas fue de: " + this.getExecutedInstructions());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				outTape.close(true);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void execute (Instruction toDo) {
		switch (toDo.getType()) {
		case "LOAD": 
		}
	}
	
	
}












//END