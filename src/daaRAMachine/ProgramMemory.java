package daaRAMachine;
import java.util.ArrayList;

public class ProgramMemory {
	private ArrayList<Instruction> tasks;
	
	
	public ProgramMemory () {
		tasks = new ArrayList<Instruction>();
	}
	
	public void loadInstruction (Instruction newIns) {
		tasks.add(newIns);
	}
	
	public Instruction readInstruction (int index) {
		return tasks.get(index);
	}
}
