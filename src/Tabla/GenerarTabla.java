/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tabla;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Elirpme
 */
public class GenerarTabla {

    private String v[];
    private int filas;

    public GenerarTabla(String v[]) {
        this.v = v;
        this.filas = calcularFilas(v);
    }

    public void crearTabla(JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel();
        for (int i = 0; i < v.length; i++) {
            modelo.addColumn(v[i]);
        }

        for (int i = 0; i < filas; i++) {
            modelo.addRow(new String[]{});
        }
        
        tabla.setModel(modelo);
    }

    private int calcularFilas(String v[]) {
        int f = 1;
        //Obtengo la ultima variable de holgura 
        String cap = v[v.length - 2];
        cap = cap.substring(0, cap.length() - 1);
        String aux = "";
        for (int i = 3; i < v.length - 1; i++) { // i=3 porque desde esa posicion comienza las variables de holgura 
            aux = v[i].substring(0, v[i].length() - 1);
            if (cap.equals(aux)) {
                f++;
            }
        }
        return f;
    }

    public double[][] capturarTabla(TableModel tabla) {
        int filas = tabla.getRowCount();
        int columnas = tabla.getColumnCount();
        double m[][] = new double[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                m[i][j] = Double.parseDouble(tabla.getValueAt(i, j).toString());
            }
        }
        return m;
    }

    public void llenarTabla(TableModel tabla) {
        int c = v.length;
        int f = filas;
        int filas_t = tabla.getRowCount();
        int columnas = tabla.getColumnCount();
        int r = c - f; // columna de la primera variable de holgura 
        int aux = -1;
        tabla.setValueAt(0, 0, columnas-1); // lleno con 0 la columna R de la fila 0
        for (int i = 0; i < filas_t; i++) {
            //llenar la columna z
            if (i == 0) {
                tabla.setValueAt(1, i, 0);
            }else{
                tabla.setValueAt(0, i, 0);
            }
            //lleno las variables de holgura 
            for (int j = r; j < columnas-1; j++) { // columnas-1 -> para no rellenar la columna R
                if (i == 0) {// en la primera fila todo es 0
                    tabla.setValueAt(0, i, j);
                } else {
                    // lleno la diagona de 1
                    if (j == r + aux) { // aux -> me permite generar la diagonal por su incremento
                        tabla.setValueAt(1, i, j);
                    } else { // relleno con 0
                        tabla.setValueAt(0, i, j);
                    }
                }
            }
            aux++;
        }
    }
    
    public void llenarValoresTabla(TableModel tabla, double m[][]){
        int f = tabla.getRowCount();
        int c = tabla.getColumnCount();
        for (int i = 0; i < f; i++) {
            for (int j = 0; j < c; j++) {
                tabla.setValueAt(m[i][j], i, j);
            }
        }
    }
    
    
}
