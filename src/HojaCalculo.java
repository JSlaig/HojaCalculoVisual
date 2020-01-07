import java.util.Scanner;

public class HojaCalculo {
	
	private int columnas;
	private int filas;
	
	public String lastPlace = "";
	
	public String[][] matrizHoja;
	public String[][] matrizResultados;

	public HojaCalculo(int rows, int columns){		
		
		if(columns < Integer.MAX_VALUE) {
		this.columnas = columns;
		}
		if(rows < Integer.MAX_VALUE) {
		this.filas = rows;
		}		
		this.matrizHoja = new String[filas][columnas];
		this.matrizResultados = new String[filas][columnas];
	}
	
	/**
	 * Funcion que se encarga de resolver las formulas y meterlas en la matriz de resultados
	 * @throws HojaCalculoException excepcion anidada de la funcion decode
	 */
	public void resolve(){
		for(int i = 0; i < this.filas; i++) {
			for(int j = 0; j < this.columnas; j++) {				
					this.matrizResultados[i][j] = decode(this.matrizHoja[i][j]);
			}			
		}		
	}
	
	/**
	 * Funcion que se encarga de comprobar que los valores introducidos en la hoja son correctos
	 * @param element elemento del cual se va a extraer la formula o numero
	 * @return valor numerico que retorna la funcion
	 * @throws HojaCalculoException Excepcion que se lanza si aparecen caracteres inesperados en el elemento
	 */
	public String decode(String element){
		//Falta el manejo de cuando una celda se llama a si misma
		if(lastPlace != element) {
		this.lastPlace = element;
		}else {
			System.err.println("Error:No se puede referenciar a la misma casilla.");
			CuadroDialogoMenu error = new CuadroDialogoMenu("Error:No se puede referenciar a la misma casilla.");
			System.exit(0);
		}
		
		//Manejo de formulas
		if(element.startsWith("=") == true) {
				element = element.substring(1);
				String[] parts = element.split("\\+");
				int[] num = new int[parts.length];
				int result = 0;
			
				if(element.isEmpty() == false) {
					for(int i = 0; i < parts.length; i++) {
						if(parts[i].matches("\\d+") == true) {
						
							num[i] = Integer.valueOf(decode(parts[i]));
						
						}else if(parts[i].matches("\\w+")){
							
							num[i] = Integer.valueOf(assign(parts[i]));
						
						}
						result += num[i];					
					}
				}
			
				return String.valueOf(result);				
		}		
		//Manejo de numeros normales que se deben almacenar en la matriz
		else if(element.matches("\\d+") == true) {
			return element;
		}else if(element.isEmpty() == true){
			return "";
		}else {
			return "#VALOR!";
		}
	}	
	
	/**
	 * Funcion que se encarga de devolver los valores de las celdas referenciadas en una funcion
	 * @throws HojaCalculoException Excepcion que se lanza si la casilla referenciada no comienza por la letra
	 */
	public String assign(String cell){	
		
		int row = -1;
		int column = -1;
		
		if(cell.startsWith("=") == true) {
			cell = cell.substring(1);
		}
		//Comprueba si comienza por una letra
		if(cell.codePointAt(0) <= 90 && cell.codePointAt(0) >= 65 || cell.codePointAt(0) <= 122 && cell.codePointAt(0) >= 97) {
			
			//Fallos de gestion de cadenas
			String letter = cell.substring(0, lastLetter(cell));
			letter = letter.toUpperCase();			
			
			String number = cell.substring(lastLetter(cell), (cell.length()));
			
			row = Integer.valueOf(number) - 1;		
			column = alphabet(letter);
		}
		if(row == -1 || column == -1) {
			System.err.println("Error: Para referenciar a una casilla se debe comenzar por la letra");
			CuadroDialogoMenu error = new CuadroDialogoMenu("Error: Para referenciar a una casilla se debe comenzar por la letra");
			System.exit(0);
		}
		
		return decode(this.matrizHoja[row][column]);
	}
	
	/**
	 * Funcion que se encarga de devolver la posicion de la ultima letra del string
	 * @param cell string del cual se quiere averiguar la ultima posicion con una letra
	 * @return posicion de la ultima letra
	 */
	public int lastLetter(String cell) {
		int n = 0;
		while(cell.codePointAt(n) <= 90 && cell.codePointAt(n) >= 65 || cell.codePointAt(n) <= 122 && cell.codePointAt(n) >= 97) {
			n++;
		}
		return n;
	}

	/**
	 * Metodo que se encarga de que mediante una o varias letras se obtenga el valor de columna correspondiente
	 * @param letter Secuencia de letras cuyo valor en columna se quiere lograr
	 * @return valor de columna correspondiente
	 * @throws HojaCalculoException Excepcion que se lanza en el caso de que se produzca un error al contar las columnas
	 */
	public int alphabet(String letter){
		if(letter.length() == 1) {
			return (letter.codePointAt(0) % 65);			
		} else if(letter.length() == 2) {
			
			String[] parts = letter.split("");	
			
			int firstLetter = ((parts[0].codePointAt(0) % 65)+1) * 26;
			int secondLetter = ((parts[1].codePointAt(0) % 65));
			int columnValue = firstLetter + secondLetter;
			     
			return columnValue;	
			
		}else{
			String[] parts = letter.split("");			
			
			int firstLetter = ((parts[0].codePointAt(0) % 65)+1) * 676;
		    int secondLetter = ((parts[1].codePointAt(0) % 65)+1) * 26;
		    int thirdLetter = ((parts[2].codePointAt(0) % 65));
		    int columnValue = firstLetter + secondLetter + thirdLetter;
		     
		    return columnValue;			
			
		}
	}	
}