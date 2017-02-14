package daaRAMachine;
import java.util.HashMap;

/**
 * La clase DataMemory representa la memoria de datos de la máquina RAM, es decir, un conjunto infinito de registros.
 * Utilizamos la clase ArrayList para no tener problemas con el número de registros.
 * @author oscardp96
 * 
 */
public class DataMemory {
	private HashMap<Integer, Integer> data;
	
	
	public DataMemory () {
		data = new HashMap<Integer, Integer> ();
		data.put(0, 0);
	}
	
	public void write (int index, Integer newData) {
		data.put(index, newData);
	}
	
	public Integer read (int index) {
		return data.get(index);
	}
}
