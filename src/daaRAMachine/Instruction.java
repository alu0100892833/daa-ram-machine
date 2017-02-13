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
            insLine = insLine.toUpperCase();
            String[] descomposition = insLine.split("\\s+");
            if ((descomposition.length > 3) || (descomposition.length < 1))
                throw new Exception("Formato de instrucción inadecuado");
            int iter = 0;
            if (descomposition[iter].charAt(descomposition[iter].length() - 1) == ':') {
                label = descomposition[iter];
                iter++;
            } 
            else
                label = "";
            type = descomposition[iter];
            addrType = 'N';
            pointing = "";
            if (!type.equals("HALT")) {
                iter++;
                if (descomposition[iter].charAt(0) == '=') {
                    descomposition[iter] = descomposition[iter].substring(1);
                    addrType = 'C';
                } else if (descomposition[iter].charAt(0) == '*') {
                    descomposition[iter] = descomposition[iter].substring(1);
                    addrType = 'I';
                } else
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
                    correct = basicInstruction();
                    break;
                case "STORE":
                case "READ":
                    correct = (basicInstruction()) && (addrType != 'C');    //las instrucciones STORE y LOAD no admiten constantes, solo direcciones
                    break;
                case "JUMP":
                case "JZERO":
                case "JGTZ":
                    correct = jumpingInstruction();
                    break;
                case "HALT":
                    correct = haltInstruction();
                    break;
                default:
                    throw new Exception("Instrucción no reconocida por esta máquina. ");
            }
            if (!correct)
                throw new Exception("Formato de instrucción inadecuado.");
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
