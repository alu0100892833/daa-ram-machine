package daaRAMachine;

/**
 * Created by oscardp96 on 13/02/2017.
 * @author Óscar Darias Plasencia - alu0100892833
 * @since 2017-13-02
 * @version 1.0
 */
public class Instruction
{
    private String label;
    private String type;
    private String pointing;
    private char addrType;


    /** Constructor de objetos Instruction
     * @param insLine Cadena de caracteres de tipo String con la instrucción completa.
     * @throws Exception Si se detecta que el formato de la instrucción no es correcto.
     */
    public Instruction (String insLine) throws Exception {
        try {
        	//primero pasamos todo a mayúsculas y separamos los elementos del String por espacios
            insLine = insLine.toUpperCase();
            String[] descomposition = insLine.split("\\s+");
            if ((descomposition.length > 3) || (descomposition.length < 1))
                throw new Exception("Formato de instrucción inadecuado");
            
            //la variable iter tomará el nombre de la instrucción de descomposition[0] si no hay etiquetas, y de [1] si las hay
            int iter = 0;    
            if (descomposition[iter].charAt(descomposition[iter].length() - 1) == ':') {
                label = descomposition[iter];
                iter++;
            } else
                label = "";
            type = descomposition[iter];
            addrType = 'N';
            pointing = "";
            
            //si la instrucción no es HALT, tendrá un último campo
            if (!type.equals("HALT")) {
                iter++;
                if (descomposition[iter].charAt(0) == '=') {  //CONSTANTE
                    descomposition[iter] = descomposition[iter].substring(1);
                    addrType = 'C';
                } else if (descomposition[iter].charAt(0) == '*') {  //DIRECCIONAMIENTO INDIRECTO
                    descomposition[iter] = descomposition[iter].substring(1);
                    addrType = 'I';
                } else					//DIRECCIONAMIENTO DIRECTO
                    addrType = 'D';
                pointing = descomposition[iter];
            }
            checkIntegrity();
        }
        catch (Exception e) {
            System.out.println("ERROR leyendo una instrucción.");
            e.printStackTrace();
        }
    }

    public String getLabel () {
        return label;
    }

    public String getType () {
        return type;
    }

    public String getPointing () {
        return pointing;
    }

    public char getAddrType () {
        return addrType;
    }

    /**
     * Esta función comprueba la sintaxis de la instrucción, viendo si tiene lógica
     * @throws Exception Es lanzada cuando la instrucción no es reconocida o no tiene sentido
     */
    private void checkIntegrity () throws Exception {
        try {
            boolean correct = true;
            switch (type) {
                case "LOAD":
                case "ADD":
                case "SUB":
                case "MUL":
                case "DIV":
                case "WRITE":
                    correct = basicInstruction();   //este grupo de instrucciones tiene una estructura de instruccion + constante o direccionamiento (directo o indirecto).
                    break;
                case "STORE":
                case "READ":
                    correct = (basicInstruction()) && (addrType != 'C');    //las instrucciones STORE y LOAD no admiten constantes, solo direcciones. Tenemos que añadir una condición.
                    break;
                case "JUMP":
                case "JZERO":
                case "JGTZ":
                    correct = jumpingInstruction();  //las instrucciones de salto se componen del nombre de una instrucción y la etiqueta de la instrucción de destino
                    break;
                case "HALT":
                    correct = haltInstruction();     //la instrucción HALT va sola
                    break;
                default:
                    throw new Exception("Instrucción no reconocida por esta máquina. ");  //si no se corresponde con ninguno de los casos anteriores, no es una instrucción válida
            }
            
            if (!correct)
                throw new Exception("Formato de instrucción inadecuado.");   //si alguna de las comprobaciones ha fallado, es que la instrucción está reconocida pero no bien formulada
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean basicInstruction() {
        return ((!pointing.equals("")) && (addrType != 'N'));
    }

    private boolean jumpingInstruction() {
        return ((!pointing.equals("")) && (addrType == 'D'));
    }

    private boolean haltInstruction () {
        return ((pointing.equals("")) && (addrType == 'N'));
    }

}






















//END
