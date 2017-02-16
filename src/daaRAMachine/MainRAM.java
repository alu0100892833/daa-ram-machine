package daaRAMachine;

public class MainRAM {

	public static void main(String[] args) throws Exception {
		ControlUnit ramMachine = new ControlUnit (args[0], args[1], args[2]);
		System.out.println("Iniciando la ejecución del programa.");
		ramMachine.run();
	}

}
