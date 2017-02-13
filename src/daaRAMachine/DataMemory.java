package daaRAMachine;
import java.util.ArrayList;

/**
 * La clase DataMemory representa la memoria de datos de la máquina RAM, es decir, un conjunto infinito de registros.
 * Utilizamos la clase ArrayList para no tener problemas con el número de registros.
 * @author oscardp96
 * 
 */
public class DataMemory {
	private ArrayList<Integer> data;
	
	
	public DataMemory () {
		data = new ArrayList<Integer> (1);
		data.set(0, 0);
	}
	
	public void write (int index, Integer newData) {
		data.add(index, newData);
	}
	
	public Integer read (int index) {
		return data.get(index);
	}
}
