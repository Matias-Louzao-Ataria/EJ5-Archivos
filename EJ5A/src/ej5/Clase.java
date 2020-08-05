package ej5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Clase extends JFrame implements ActionListener {

    private JFileChooser chooser = new JFileChooser();
    private JLabel label = new JLabel("Resultado :");
    private JButton boton = new JButton("Crear un archivo con números aleatórios!");
    private JButton botonChooser = new JButton("Pulsame para escoger un archivo!");
    private String home = System.getProperty("user.home");
    private String separador = System.getProperty("file.separator");
    private JTextField lineas = new JTextField(
            "Escriba aquí el número de líneas que quiere que tenga el nuevo archivo.");
    private int numfilas;

    /**
     * Constructor que selecciona el método de distribución de los elementos, añade
     * elementos al JFrame y le pone título al JFrame.
     */
    public Clase() {
        super("Ejercicio 5");
        this.setLayout(new FlowLayout());
        this.add(label);
        boton.addActionListener(this);
        this.add(boton);
        botonChooser.addActionListener(this);
        this.add(botonChooser);
        this.add(lineas);
    }

    /**
     * Se encarga de los eventos generados por los botones.
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == botonChooser) {
            if (chooser.showOpenDialog(this) == 0) {
                try (Scanner sc = new Scanner(chooser.getSelectedFile())) {
                    if (comprobarArchivo(sc)) {
                        mostrarCalculo();
                    } else {
                        label.setText("Error");
                        JOptionPane.showMessageDialog(null,
                                "Error en el archivo, el archivo contiene errores de formato!");
                    }
                } catch (IOException | SecurityException e) {
                    JOptionPane.showMessageDialog(null, "Error de acceso al archivo");
                }
            }
        } else {
            try (PrintWriter escritor = new PrintWriter(new FileWriter(new File(home + separador + "num.txt"), true))) {
                generarArchivo(escritor);
            } catch (IOException | SecurityException e) {
                JOptionPane.showMessageDialog(null, "Error al crear el archivo");
            }
        }
    }

    /**
     * Genera un archivo con el formato correcto para los cálculos.
     */
    private void generarArchivo(PrintWriter escritor) {
        try {
            lineas.setSize(lineas.getPreferredSize());
            for (int i = 0; i < Integer.parseInt(lineas.getText()); i++) {
                escritor.append(String.valueOf((int) (Math.random() * 100 + 10)) + ",");
                escritor.append(String.valueOf((Math.random() * 1000)) + ",");
                escritor.append(String.valueOf((Math.random() * 10000 + 100)));
                escritor.append("\n");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "No se ha introducido un número válido de filas.");
        }
    }

    /**
     * Comprueba que el archivo tiene el formato adecuado para realizar los
     * calculos.
     * 
     * @param sc Scanner que lee del archivo para comprobarlo.
     * @return Devuelde true si el archivo tiene un formato adecuado y false si no.
     */
    private boolean comprobarArchivo(Scanner sc) {
        numfilas = 0;
        try {
            while (sc.hasNextLine()) {
                if (comprobarFila(sc.nextLine())) {
                    numfilas++;
                } else {
                    return false;
                }
            }
            return true;
        } catch (NumberFormatException e) {
            label.setText("Error");
            JOptionPane.showMessageDialog(null, "Error en el archivo,el archivo contiene errores de formato!");
            return false;
        }
    }

    /**
     * Muestra el resultado de los cálculos en una label.
     */
    private void mostrarCalculo() {
        ArrayList<String> columnas = new ArrayList<String>();
        ArrayList<String> filas = new ArrayList<String>();
        try (Scanner sc2 = new Scanner(chooser.getSelectedFile())) {
            label.setText("<html>");
            for (int i = 0; i < 3; i++) {
                label.setText(label.getText() + calculo(filas, columnas,sc2,i) + "<br>");
                columnas = new ArrayList<String>();
            }
            label.setText(label.getText() + "</html>");
        } catch (IOException | SecurityException e) {
            JOptionPane.showMessageDialog(null, "Error de acceso al archivo");
        }
    }
	
	
	/**
	*Separa en columnas los números y calcula las medias de esas columnas.
	*@param filas Fila de que se extrae un número.
	*@param columnas Columna con los números extraidos.
	*@param sc Scanner que está leyendo el archivo con los números.
	*@param vuelta Columna que se está separando.
	*@return Devuelve el resultado de la media.
	*/
    private double calculo(ArrayList<String> filas, ArrayList<String> columnas,Scanner sc,int vuelta) {
        double res = 0;
        for (int i = 0; i < numfilas; i++) {
            // if(filas.size() < numfilas--){
                // }
            if(vuelta != 0){

            }else{
                filas.add(sc.nextLine());
            }
            if(vuelta != 2){
                columnas.add(filas.get(i).substring(0, filas.get(i).indexOf(',')));
                filas.set(i, filas.get(i).replace(filas.get(i).substring(0, filas.get(i).indexOf(',') + 1), ""));
            }else{
                columnas.add(filas.get(i).substring(0,filas.get(i).length()-1));
                filas.set(i, filas.get(i).replace(filas.get(i).substring(0, filas.get(i).length()-1), ""));
            }
        }
        for (String string : columnas) {
            res += Double.parseDouble(string);
        }
        res = res / columnas.size();
        return res;
    }


    /**
     * Comprueba si una fila tiene el formato aducado.
     * 
     * @param str Fila que se quiere comprobar.
     * @return Devuelve true si tiene el formato correcto y false si no lo es.
     */
    public boolean comprobarFila(String str) {
        int cont = 0;
        for (int i = 0; i < str.length(); i++) {
            if ((str.charAt(i) == ',' || !(Character.getNumericValue(str.charAt(i)) >= 0
                    && Character.getNumericValue(str.charAt(i)) <= 9)) && str.charAt(i) != '.') {
                cont++;
            }
        }
        if (cont != 2) {
            return false;
        } else {
            return true;
        }
    }

}
