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
	
	public int indexOfInstructionByLabel (String label) {
		for (int i=0; i < tasks.size(); i++)
			if (tasks.get(i).getLabel().equals(label))
				return i;
		return -1;
	}
	
	public int size () {
		return tasks.size();
	}
}
