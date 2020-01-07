import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class InterfazHojaCalculo extends JFrame {

	public static JPanel contentPane;
	private static JTextField txtIntroduzcaUnNumero;
	private final Action action = new SwingAction_1();
	private static JTextField textField;
	private static JTextField textField_1;

	public InterfazHojaCalculo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
			
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//Establecimiento de tamaño de la ventana y de la posicion
		setSize(screenSize.width/2, screenSize.height/2);	
		this.setLocationRelativeTo(null);
		
		//Establecemos titulo
		setTitle("Excel_Hacendado");
		
		//Establecemos el icono
		setIconImage(new ImageIcon(getClass().getResource("/etc/excel_logo.png")).getImage());
		
		//Establecemos el fondo de la ventana
		((JPanel)getContentPane()).setOpaque(false);
		contentPane.setLayout(null);
		
		Font f1 = new Font("Calibri", 3, 15);
		JLabel label = new JLabel("Numero de hojas de calculo");
		label.setFont(f1);
		label.setBounds(170, 27, 380, 90);
		contentPane.add(label);
		
		JButton btnDone = new JButton("Done");
		btnDone.setAction(action);
		btnDone.setBounds(270, 260, 110, 25);
		contentPane.add(btnDone);
		
		txtIntroduzcaUnNumero = new JTextField();
		txtIntroduzcaUnNumero.setBounds(427, 60, 110, 25);
		contentPane.add(txtIntroduzcaUnNumero);
		txtIntroduzcaUnNumero.setColumns(10);
		
		JLabel lblNumeroDeFilas = new JLabel("Numero de filas");
		lblNumeroDeFilas.setFont(f1);
		lblNumeroDeFilas.setBounds(200, 90, 144, 74);
		contentPane.add(lblNumeroDeFilas);
		
		textField = new JTextField();
		textField.setBounds(427, 115, 110, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNumeroDeColumnas = new JLabel("Numero de columnas");
		lblNumeroDeColumnas.setFont(f1);
		lblNumeroDeColumnas.setBounds(200, 145, 190, 70);
		contentPane.add(lblNumeroDeColumnas);
		
		textField_1 = new JTextField();
		textField_1.setBounds(427, 170, 110, 25);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		ImageIcon pattern = new ImageIcon(this.getClass().getResource("/etc/excel_background.jpg"));
		JLabel fondo = new JLabel();
		fondo.setIcon(pattern);
		getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
		fondo.setBounds(0, 0, pattern.getIconWidth(), pattern.getIconHeight());
	}
	
	public static String getText() {
		return txtIntroduzcaUnNumero.getText();
	}
	public static String getRows() {
		return textField.getText();
	}
	public static String getColumns() {
		return textField_1.getText();
	}
	
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "Done");
			putValue(SHORT_DESCRIPTION, "Lee el cuadro de texto cuando se pulsa el boton");
		}
		public void actionPerformed(ActionEvent e) {		
			try {
				MatrizExcel[] matriz = new MatrizExcel[Integer.valueOf(InterfazHojaCalculo.getText().replace(" ",""))];
				int rows = Integer.valueOf(InterfazHojaCalculo.getRows().replace(" ",""));
				int columns = Integer.valueOf(InterfazHojaCalculo.getColumns().replace(" ", ""));
				
				for(int i = 1; i <= Integer.valueOf(InterfazHojaCalculo.getText().replace(" ", "")); i++) {
					matriz[i-1] = new MatrizExcel(rows, columns);
					matriz[i-1].setTitle(i);
					matriz[i-1].setVisible(true);
				}
			}catch(NumberFormatException f) {
				System.err.println("Error: No se han introducido valores en todos los campos necesarios");
				//Crear ventana de error
				CuadroDialogoMenu error = new CuadroDialogoMenu("Error: No se han introducido valores en todos los campos necesarios");
				error.setVisible(true);
			}
			setVisible(false);
		}
	}
}
