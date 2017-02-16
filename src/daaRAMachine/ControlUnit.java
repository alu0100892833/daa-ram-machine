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
	public ControlUnit (String code, String inputFile, String outputFile) {
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
			while ((readingLine = br.readLine()) != null) {
				String trimedLine = new String (readingLine.trim());
				if (!trimedLine.equals(""))
					if (trimedLine.charAt(0) != '#') {
						Instruction newIns = new Instruction (trimedLine);
						executing.loadInstruction(newIns);
					}
			}
		}
		catch (IOException e) {
			System.out.println("Error en la apertura del programa RAM. Asegurése de que el fichero existe.");
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
		boolean stop = false;
		boolean error = false;
		try {
			while ((instructionPointer < executing.size()) && (!stop)) {
				Instruction toDo = executing.readInstruction(instructionPointer);
				instructionPointer++;
				execute(toDo, stop);
				executedInstructions++;
			}
			System.out.println("El número de instrucciones ejecutadas fue de: " + this.getExecutedInstructions());
		}
		catch (Exception e) {
			e.printStackTrace();
			error = true;
		}
		finally {
			try {
				outTape.close(error);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void execute (Instruction toDo, boolean stop) throws Exception {
		int operand = -1;
		switch (toDo.getType()) {
		case "LOAD": 
			operand = Integer.parseInt(toDo.getPointing());
			this.load(operand, toDo.getAddrType(), 0); 
			break;
		case "STORE":
			operand = Integer.parseInt(toDo.getPointing());
			this.store(operand, toDo.getAddrType());
			break;
		case "ADD":
			operand = Integer.parseInt(toDo.getPointing());
			this.add(operand, toDo.getAddrType(), false);
			break;
		case "SUB":
			operand = Integer.parseInt(toDo.getPointing());
			this.add(operand, toDo.getAddrType(), true);
			break;
		case "MUL":
			operand = Integer.parseInt(toDo.getPointing());
			this.mul(operand, toDo.getAddrType());
			break;
		case "DIV":
			operand = Integer.parseInt(toDo.getPointing());
			this.div(operand, toDo.getAddrType());
			break;
		case "READ":
			operand = Integer.parseInt(toDo.getPointing());
			this.read(operand, toDo.getAddrType());
			break;
		case "WRITE":
			if (toDo.getPointing().equals("0"))
				throw new Exception ("La instrucción WRITE no puede interactuar con el registro R0.");
			operand = Integer.parseInt(toDo.getPointing());
			this.write(operand, toDo.getAddrType());
			break;
		case "JUMP":
			this.jump(toDo.getPointing());
			break;
		case "JGTZ":
			if (registers.read(0) > 0)
				this.jump(toDo.getPointing());
			break;
		case "JZERO":
			if (registers.read(0) == 0)
				this.jump(toDo.getPointing());
			break;
		case "HALT":
			stop = true;
			break;
		}
	}
	
	private void load (int operand, char addrType, int direction) {
		Integer loadingData = null;
		if (addrType == 'C')
			loadingData = operand;
		else if (addrType == 'D')
			loadingData = registers.read(operand);
		if (addrType == 'I') {
			int directRegister = registers.read(operand);
			loadingData = registers.read(directRegister);
		}
		
		registers.write(direction, loadingData);
	}
	
	private void store (int operand, char addrType) {
		Integer loadingData = new Integer (registers.read(0));
		int destination = -1;
		if (addrType == 'D')
			destination = operand;
		else if (addrType == 'I') 
			destination = registers.read(operand);
		registers.write(destination, loadingData);
	}
	
	private void add (int operand, char addrType, boolean sub) {
		int adder = 0;
		if (addrType == 'C')
			adder = operand;
		else if (addrType == 'D')
			adder = registers.read(operand);
		if (addrType == 'I') {
			int directRegister = registers.read(operand);
			adder = registers.read(directRegister);
		}
		if (sub)
			adder *= -1;
		
		Integer result = registers.read(0) + adder;
		registers.write(0, result);
	}
	
	private void mul (int operand, char addrType) {
		int multiplier = 0;
		if (addrType == 'C')
			multiplier = operand;
		else if (addrType == 'D')
			multiplier = registers.read(operand);
		if (addrType == 'I') {
			int directRegister = registers.read(operand);
			multiplier = registers.read(directRegister);
		}
		
		Integer result = multiplier * registers.read(0);
		registers.write(0, result);
	}
	
	private void div (int operand, char addrType) {
		int divisor = 0;
		if (addrType == 'C')
			divisor = operand;
		else if (addrType == 'D')
			divisor = registers.read(operand);
		if (addrType == 'I') {
			int directRegister = registers.read(operand);
			divisor = registers.read(directRegister);
		}
		
		Integer result = registers.read(0) / divisor;
		registers.write(0, result);
	}
	
	private void read (int operand, char addrType) {
		int destination = -1;
		if (addrType == 'D')
			destination = operand;
		else if (addrType == 'I')
			destination = registers.read(operand);
		int data = inTape.accessTape();
		registers.write(destination, data);
	}
	
	private void write (int operand, char addrType) {
		int data = 0;
		if (addrType == 'C')
			data = operand;
		else if (addrType == 'D')
			data = registers.read(operand);
		else if (addrType == 'I') {
			int directRegister = registers.read(operand);
			data = registers.read(directRegister);
		}
		outTape.write(data);
	}
	
	private void jump (String label) {
		int index = executing.indexOfInstructionByLabel(label);
		instructionPointer = index;
	}
	
}












//END