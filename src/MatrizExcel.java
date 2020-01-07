import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import java.awt.GridBagLayout;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

public class MatrizExcel extends JFrame {

	private JPanel matrix;
	private JTextField[][] textField;
	private HojaCalculo hoja;
	private int rows;
	private int columns;
	public boolean buleana = false;

	public int getRows() {
		return this.rows;
	}
	public int getColumns() {
		return this.columns;
	}
	
	public void setTitle(int i) {
		setTitle("Set de hojas Excel_Hacendado: hoja "+i);
	}
	
	public MatrizExcel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public MatrizExcel(int rows, int columns) {
		
		this.rows = rows;
		this.columns = columns;
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		//Establecimiento de tamaño de la ventana y de la posicion
		setSize(columns * 250, rows * 100);	
		this.setLocationRelativeTo(null);
		
		//Establecemos el tipo de layout que nos interesa
		JPanel ventana = new JPanel(new BorderLayout());
		matrix = new JPanel(new GridLayout(rows+1, columns+1));		
		matrix.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(ventana);
		
		ventana.add(matrix, BorderLayout.CENTER);
		
		JTextField msg = new JTextField("Para actualizar la hoja de calculo pulse ENTER");
		Font f1 = new Font("Calibri", 3, 15);
		msg.setFont(f1);
		msg.setBackground(Color.GRAY);
		ventana.add(msg, BorderLayout.SOUTH);
		
		//Menu
		JMenuBar menuBar = new JMenuBar();	
		
		//Boton
		JMenu archivo = new JMenu("Archivo");
		
		//Items del boton
		JMenuItem novo = new JMenuItem("New");
			novo.addActionListener(novoExcel);
		JMenuItem save = new JMenuItem("Save");
		
		JMenuItem load = new JMenuItem("Load");
		
		
		//Boton
		JMenu edit = new JMenu("Edit");
		
		//Items del boton
		JMenuItem undo = new JMenuItem("Undo");
		
		
		menuBar.add(archivo);
		archivo.add(novo);
		archivo.add(save);
		archivo.add(load);
		
		menuBar.add(edit);
		edit.add(undo);
		
		ventana.add(menuBar, BorderLayout.NORTH);
		 
		//Creamos la matriz con el tamaño que nos interesa
		textField = new JTextField[rows+1][columns+1];
		
		//Creamos la hoja de calculo en la que se almacenaran los datos y se procesaran segun se vayan introduciendo
		hoja = new HojaCalculo(rows, columns);
				
		/*Rellenamos cada uno de los textField con un string vacio y le asignamos un action listener
		 * para que se realize la accion de enviar el valor introducido en el textfield hacia la hoja
		 * de calculo y este se intente procesar
		 */
		for(int i = 0; i < rows+1; i++) {
			for(int j = 0; j < columns+1; j++) {
				textField[i][j] = new JTextField("");				
				matrix.add(textField[i][j]);
				
				//Setteamos una accion en cada textfield que determinara que se actualice toda la hoja de calculo en el caso de que se pulse intro
				textField[i][j].addActionListener(action);
				
				textField[i][j].setColumns(10);
				textField[i][j].setBounds(100, 100, 500, 500);
			}
		}
		
		//Modificamos la casilla de la esquina superior izquierda
		textField[0][0].setEditable(false);
		textField[0][0].setBackground(Color.gray);
		
		//Asignamos numeros a las filas
		for(int i = 1; i < rows + 1; i++) {
			textField[i][0].setText(String.valueOf(i));
			textField[i][0].setEditable(false);
		}
		//Asignamos letras a las columnas
		for(int i = 1; i < columns + 1; i++) {
			textField[0][i].setText(reverseAlphabet(i));
			textField[0][i].setEditable(false);
		}
	}
	
	/*"Error: No se han introducido valores en todos los campos necesarios"
	 * Esta funcion la usamos con la finalidad de codificar correctamente las letras correspondientes
	 * a todas las columnas que tengamos en la hoja, hace el trabajo inverso a la funcion alphabet de 
	 * la clase hojaCalculo que decodifica el numero a partir de la letra
	 */
	public String reverseAlphabet(int num){
		if(num > 0 && num <= 26) {
			
			char letter = (char)(64 + num);
			
			String s = "" + letter;
			return s;
		} else if(num > 26 && num < 703) {	
			
			char firstLetter = (char) (64 + (num / 26));
			char secondLetter = (char) (64 + (num % 26));
			
			String s = "" + firstLetter + secondLetter;			     
			return s;	
			
		} else if(num >= 703 && num <= 18278) {
			
			char firstLetter = (char) (64 + (num / 676));
		    char secondLetter = (char) (64 + ((num / 26) % 26));
		    char thirdLetter = (char) (64 + (num % 26));
		    
		    String s = "" +  firstLetter + secondLetter + thirdLetter;
		     
		    return s;			
			
		} else {
			System.err.println("Error: Valores fuera del limite representable");
			CuadroDialogoMenu error = new CuadroDialogoMenu("Error: Valores fuera del limite representable");
			return "";
		}
	}
	
	AbstractAction action = new AbstractAction()
	{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;


		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			//Actualizamos todas las casillas, no es lo mas optimo, pero es lo mas sencillo
			//Debido a que el paso de parametros para actualizar una sola casilla lo complica demasiado
			for(int i = 0; i < getRows(); i++) {
				for(int j = 0; j < getColumns(); j++) {
					hoja.matrizHoja[i][j] = textField[i+1][j+1].getText();
					System.out.println("VALOR INTRODUCIDO EN LA POSICION "+(i+1)+", "+(j+1)+": "+hoja.matrizHoja[i][j]);					
				}
			}
			
			//Llamamos en cada actualizacion para que intente resolver toda la matriz			
			hoja.resolve();
			System.out.println("\n\n");
			
			for(int i = 0; i < getRows(); i++) {
				for(int j = 0; j < getColumns(); j++) {
					textField[i+1][j+1].setText(hoja.matrizResultados[i][j]);
					System.out.println("VALOR RESUELTO EN LA POSICION "+(i+1)+", "+(j+1)+": "+hoja.matrizResultados[i][j]);					
				}
			}
		}
	};
	
	AbstractAction novoExcel = new AbstractAction()
	{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;


		@Override
		public void actionPerformed(ActionEvent arg0) {
			//No vuelve a visibilizar el menu de nueva hoja de calculo ni con llamada estatica al metodo publico
			InterfazHojaCalculo nuevaHoja = new InterfazHojaCalculo();
			nuevaHoja.setVisible(true);
		}
	};
}
