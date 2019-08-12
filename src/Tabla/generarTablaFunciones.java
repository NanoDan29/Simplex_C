/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tabla;

import java.awt.Color;
import java.awt.Component;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Elirpme
 */
public class generarTablaFunciones extends DefaultTableCellRenderer {

    private List<String> listVariablesColumnas;
    private int filas;
    private int filaPintar = 0;
    private int columnaPintar = 0;
    private int metodo;

    public generarTablaFunciones(List<String> l, int filas, int metodo) {
        this.listVariablesColumnas = l;
        this.filas = filas;
        this.metodo = metodo;
    }
    
    public generarTablaFunciones() {
    }

//    public void crearTabla(JTable tabla) {
//        DefaultTableModel modelo = new DefaultTableModel();
//        String v[] = new String[list.size()+1];
//
//        modelo.addColumn(""); // porque tenemos que crear una columna para las variables cambiantes
//        for (int i = 0; i < list.size(); i++) { // +1 porque tenemos que crear una columna para las variables cambiantes
//            modelo.addColumn(list.get(i));
//            v[i] = "0";
//        }
//        v[list.size()] = "0";
//
//        for (int i = 0; i < filas; i++) {
//            modelo.addRow(v);
//        }
//
//        tabla.setModel(modelo);
//    }
    public void crearTabla(JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel();
        String v[] = null;
        //System.out.println("v .. "+v.length);
        modelo.addColumn(""); // porque tenemos que crear una columna para las variables cambiantes
//        if (metodo) {//para el metodo de dos fases
//            v = new String[list.size()]; // sin Z
//            for (int i = 1; i < list.size(); i++) { // +1 porque tenemos que crear una columna para las variables cambiantes
//                modelo.addColumn(list.get(i));
//                v[i] = "0";
//            }
//        }
        if (this.metodo == 0) {// metodo simplex
            v = new String[listVariablesColumnas.size() + 1]; // con Z
            for (int i = 0; i < listVariablesColumnas.size(); i++) { // +1 porque tenemos que crear una columna para las variables cambiantes
                modelo.addColumn(listVariablesColumnas.get(i));
                v[i] = "0";
            }
            v[listVariablesColumnas.size()] = "0";

        } else if (this.metodo == 1) { // metodo simplex dual
            v = new String[listVariablesColumnas.size()]; // sin Z
            for (int i = 1; i < listVariablesColumnas.size(); i++) { // +1 porque tenemos que crear una columna para las variables cambiantes
                modelo.addColumn(listVariablesColumnas.get(i));
                v[i] = "0";
            }
        }
        System.out.println("v .. " + v.length);

        for (int i = 0; i < filas; i++) {
            modelo.addRow(v);
        }

        tabla.setModel(modelo);
    }

    public void llenarTablaPorFunciones(TableModel tabla, String Z, Hashtable<String, Integer> diccVariables, List<Hashtable<String, Double>> listDiccionarioFun,
            List<String> listVariblesDeCambio) {

//        if (simplex_dosFases) { // dos fases
//            llenarTablaPorFuncionesDosFases(tabla, Z, diccVariables, listDiccionarioFun, listVariblesDeCambio);
//        }
        if (this.metodo == 0) { // simplex
            llenarTablaPorFuncionesSimplex(tabla, Z, diccVariables, listDiccionarioFun, listVariblesDeCambio);
        } else if (this.metodo == 1) { // dos fases
            generarMatrizSimplexDual(tabla, Z, diccVariables, listDiccionarioFun, listVariblesDeCambio);
            //generarMatrizInvertidaSimplexDual(Z, diccVariables, listDiccionarioFun, listVariblesDeCambio);
        }

    }

    //metodo Simplex
    public void llenarTablaPorFuncionesSimplex(TableModel tabla, String Z, Hashtable<String, Integer> diccVariables, List<Hashtable<String, Double>> listDiccionarioFun,
            List<String> listVariblesDeCambio) {
        int f = 0;
        tabla.setValueAt(Z, f, 0);
        for (Hashtable<String, Double> dic : listDiccionarioFun) {

            // De esta forma podemos recorrer la lista de las variables junto a su valor
            for (Entry<String, Double> entry : dic.entrySet()) {
                tabla.setValueAt(entry.getValue(), f, diccVariables.get(entry.getKey()) + 1); // mediante diccVatiables me permite
                // encontrar las columnas ya que mediante por la
                // busqueda del key su valor es la columna con la ayuda
                // del key de dic de la lista de diccionario
            }
            f++;
            if (f < filas) {
                tabla.setValueAt(listVariblesDeCambio.get(f - 1), f, 0);
            }
        }
    }

    //Metodo dos fases
    public void llenarTablaPorFuncionesDosFases(TableModel tabla, String Z, Hashtable<String, Integer> diccVariables, List<Hashtable<String, Double>> listDiccionarioFun,
            List<String> listVariblesDeCambio) {
        int f = 0;
        tabla.setValueAt(Z, f, 0);
        for (Hashtable<String, Double> dic : listDiccionarioFun) {

            if (f >= 1) {
                // De esta forma podemos recorrer la lista de las variables junto a su valor
                for (Entry<String, Double> entry : dic.entrySet()) {
                    tabla.setValueAt(entry.getValue(), f, diccVariables.get(entry.getKey())); // mediante diccVatiables me permite
                    // encontrar las columnas ya que mediante por la
                    // busqueda del key su valor es la columna con la ayuda
                    // del key de dic de la lista de diccionario
                }
            } else {
                for (String vc : listVariblesDeCambio) {
                    if ((vc.substring(0, 1)).equals("A")) {
                        tabla.setValueAt(-1, f, diccVariables.get(vc));
                    }
                }
            }
            f++;
            if (f < filas) {
                tabla.setValueAt(listVariblesDeCambio.get(f - 1), f, 0);
            }
        }
    }

    //metodo SimplexDual
    public void generarMatrizSimplexDual(TableModel tabla, String Z, Hashtable<String, Integer> diccVariables, List<Hashtable<String, Double>> listDiccionarioFun,
            List<String> listVariblesDeCambio) {
        int columna = diccVariables.size() - 1; //-1 por el registro Z
        int fila = listVariblesDeCambio.size() + 1; // + porque en la variable de camnios no existe el registro Z
        double M[][] = new double[fila][columna];
        int f = 0;
        System.out.println("f: " + fila);
        System.out.println("c: " + columna);
        for (Hashtable<String, Double> dic : listDiccionarioFun) {

            // De esta forma podemos recorrer la lista de las variables junto a su valor
            for (Entry<String, Double> entry : dic.entrySet()) {
                tabla.setValueAt(entry.getValue(), f, diccVariables.get(entry.getKey())); // mediante diccVatiables me permite
                if (diccVariables.get(entry.getKey()) > 0) {
                    M[f][diccVariables.get(entry.getKey()) - 1] = entry.getValue();
                }

                // encontrar las columnas ya que mediante por la
                // busqueda del key su valor es la columna con la ayuda
                // del key de dic de la lista de diccionario
            }
            f++;
            if (f < filas) {
                tabla.setValueAt(listVariblesDeCambio.get(f - 1), f, 0);
            }
        }
        tabla.setValueAt(Z, 0, 0);
    }

    public String[] generarMatrizInvertidaSimplexDual(String Z, Hashtable<String, Integer> diccVariables, List<Hashtable<String, Double>> listDiccionarioFun,
            List<String> listVariblesDeCambio) {
        String v[] = new String[2];
        int columna = diccVariables.size() - 1; //-1 por el registro Z
        int fila = listVariblesDeCambio.size() + 1; // + porque en la variable de camnios no existe el registro Z
        double M[][] = new double[fila][columna];
        int f = 0;
        System.out.println("f: " + fila);
        System.out.println("c: " + columna);
        for (Hashtable<String, Double> dic : listDiccionarioFun) {

            // De esta forma podemos recorrer la lista de las variables junto a su valor
            for (Entry<String, Double> entry : dic.entrySet()) {
                if (diccVariables.get(entry.getKey()) > 0) {
                    M[f][diccVariables.get(entry.getKey()) - 1] = entry.getValue();
                }

                // encontrar las columnas ya que mediante por la
                // busqueda del key su valor es la columna con la ayuda
                // del key de dic de la lista de diccionario
            }
            f++;
            
        }
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                System.out.print(M[i][j] + "\t");
            }
            System.out.println("");
        }
        
        System.out.println("invertir matriz");
        //invertir matriz
        M = invertirMatriz2(M, false);
        String r = matriz_a_funciones(M, Z, listVariblesDeCambio, true);
        System.out.println("---1---");
        System.out.println(r);
        v[0] = r;
        //multiplicar matriz por -1
        M = matriz_x_menos_uno(M);
        r = matriz_a_funciones(M, Z, listVariblesDeCambio, false);
        System.out.println("---2---");
        System.out.println(r);
        v[1]=r;
        return v;
    }

    public double[][] invertirMatriz(double m[][], boolean x_menos_uno) {
        double newM[][] = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                newM[i][j] = m[i][j];
            }
        }

        for (int i = 0; i < m.length; i++) {
            if (i == 0) {
                for (int j = 1; j < m.length; j++) {// j = 1 e salto una fila
                    newM[j][m[0].length - 1] = -1 * m[i][j - 1];// lleno la columna R
                    // -1 * porque la fila Z esta despejada, entonces lo desejo de nuevo                        
                    newM[i][j - 1] = m[j][m[0].length - 1]; // lleno la fila Z
                }
            } else {
                for (int j = 1; j < m.length; j++) {//
                    //fila          columna
                    if (x_menos_uno) {
                        newM[i][j - 1] = -1 * m[j][i - 1];
                    } else {
                        newM[i][j - 1] = m[j][i - 1];
                    }
                }
            }
        }
        for (int i = 0; i < newM.length; i++) {
            for (int j = 0; j < newM[0].length; j++) {
                System.out.print(newM[i][j] + "\t");
            }
            System.out.println("");
        }
        return newM;
    }

    public double[][] invertirMatriz2(double m[][], boolean x_menos_uno) {
        //int filas = m[0].length-1; //-1 porque no incluimos R
        int filas = m[0].length;
        //int columnas = m.length-1; // -1 porque no uncluimos la fila de la Z
        int columnas = m.length;
        double newM[][] = new double[filas][columnas];
//        for (int i = 0; i < m.length; i++) {
//            for (int j = 0; j < m[0].length; j++) {
//                newM[i][j] = m[i][j];
//            }
//        }

        for (int i = 0; i < filas; i++) {
            if (i == 0) {
                for (int j = 1; j < filas; j++) {// j = 1 e salto una fila
                    newM[j][columnas - 1] = -1 * m[i][j - 1];// lleno la columna R                                                               // -1 * porque la fila Z esta despejada, entonces lo desejo de nuevo                        

                }
                for (int j = 1; j < columnas; j++) {
                    newM[i][j - 1] = m[j][m[0].length - 1]; // lleno la fila Z
                }
            } else {
                for (int j = 1; j < columnas; j++) {//
                    //      fila          columna
                    if (x_menos_uno) {
                        newM[i][j - 1] = -1 * m[j][i - 1];
                    } else {
                        newM[i][j - 1] = m[j][i - 1];
                    }
                }
            }
        }
        for (int i = 0; i < newM.length; i++) {
            for (int j = 0; j < newM[0].length; j++) {
                System.out.print(newM[i][j] + "\t");
            }
            System.out.println("");
        }
        return newM;
    }

    public String matriz_a_funciones(double m[][], String Z, List<String> list_igualacion, boolean cambiar_igualacion) {
        String r = Z + "=";
        if (!cambiar_igualacion) {
            r = "-" + Z + "=";
        }

        int cont = 0;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (m[i][j] != 0) {// evitamos los campos con 0
                    if (j == m[0].length - 1) { // columna R
                        if (cambiar_igualacion) {
                            if ("<=".equals(list_igualacion.get(cont))) {
                                r += ">=" + m[i][j];
                            } else {
                                r += "<=" + m[i][j];
                            }

                        } else {
                            r += list_igualacion.get(cont) + m[i][j];
                        }

                        cont++;
                    } else {
                        if (m[i][j] > 0) { // cuando no hay 0 en una celda de la tabla
                            if (j == 0) {// cuando recien empiza entonces no podemos poner emj: +5x1
                                r += m[i][j] + this.listVariablesColumnas.get(j + 1);
                            } else { // caundo ya fue evaluado la primera variabke, entonces si exite +, lo ponemos como textp
                                r += "+" + m[i][j] + this.listVariablesColumnas.get(j + 1);
                            }

                        } else { // caundo son negativos no importa poner el signo como texo
                            r += m[i][j] + this.listVariablesColumnas.get(j + 1);
                        }
                    }
                }
            }
            r += "\n";
            cont = 0;
        }
        return r;
    }

    public double[][] matriz_x_menos_uno(double m[][]) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                m[i][j] = -1 * m[i][j];
            }
        }
        return m;
    }

//    public void llenarTablaPorFuncionesDosFases(TableModel tabla, String Z, Hashtable<String, Integer> diccVariables, List<Hashtable<String, Double>> listDiccionarioFun,
//            List<String> listVariblesDeCambio, boolean simplex_dosFases) {
//        int verificarMetodo = (simplex_dosFases == true) ? 1 : 0;
//        int f = 0;
//        tabla.setValueAt(Z, f, 0);
//        for (Hashtable<String, Double> dic : listDiccionarioFun) {
//
//            if (f >= verificarMetodo) {
//                // De esta forma podemos recorrer la lista de las variables junto a su valor
//                for (Entry<String, Double> entry : dic.entrySet()) {
//                    tabla.setValueAt(entry.getValue(), f, diccVariables.get(entry.getKey())); // mediante diccVatiables me permite
//                    // encontrar las columnas ya que mediante por la
//                    // busqueda del key su valor es la columna con la ayuda
//                    // del key de dic de la lista de diccionario
//                }
//            } else {
//                for (String vc : listVariblesDeCambio) {
//                    if ((vc.substring(0, 1)).equals("A")) {
//                        tabla.setValueAt(-1, f, diccVariables.get(vc));
//                    }
//                }
//            }
//            f++;
//            if (f < filas) {
//                tabla.setValueAt(listVariblesDeCambio.get(f - 1), f, 0);
//            }
//        }
//    }
    public double[][] capturarTabla(TableModel tabla) {
        int filas = tabla.getRowCount();
        int columnas = tabla.getColumnCount();
        double m[][] = new double[filas][columnas - 1];// -1 porque creamos una columna para las variables cambuantes
        System.out.println("f " + filas + "  c " + columnas);
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas - 1; j++) {// -1 porque creamos una columna para las variables cambuantes
                m[i][j] = Double.parseDouble(tabla.getValueAt(i, j + 1).toString());
            }
            System.out.println("");
        }
        return m;
    }

    public void llenarTabla(TableModel tabla) {
        int c = listVariablesColumnas.size();
        int f = filas;
        int filas_t = tabla.getRowCount();
        int columnas = tabla.getColumnCount();
        int r = c - f; // columna de la primera variable de holgura 
        int aux = -1;
        tabla.setValueAt(0, 0, columnas - 1); // lleno con 0 la columna R de la fila 0
        for (int i = 0; i < filas_t; i++) {
            //llenar la columna z
            if (i == 0) {
                tabla.setValueAt(1, i, 0);
            } else {
                tabla.setValueAt(0, i, 0);
            }
            //lleno las variables de holgura 
            for (int j = r; j < columnas - 1; j++) { // columnas-1 -> para no rellenar la columna R
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

    public void llenarValoresTabla(TableModel tabla, double m[][], String s[]) {
        int f = tabla.getRowCount();
        int c = tabla.getColumnCount();
        for (int i = 0; i < f; i++) {
            tabla.setValueAt(s[i], i, 0);
            for (int j = 0; j < c - 1; j++) { // -1 para restar una columna que es vacia 
                tabla.setValueAt(m[i][j], i, j + 1);
            }
        }
//        for (int i = 0; i < f; i++) {
//            tabla.setValueAt(s[i], i, 0);
//            for (int j = 0; j < c - 1; j++) { // -1 para restar una columna que es vacia 
//                tabla.setValueAt(m[i][j], i, j + 1);
//            }
//        }
    }

    public void vaciarTabla(TableModel tabla) {
        int f = tabla.getRowCount();
        int c = tabla.getColumnCount();
        for (int i = 0; i < f; i++) {
            for (int j = 0; j < c; j++) { // -1 para restar una columna que es vacia 
                tabla.setValueAt("", i, j);
            }
        }
    }

    /**
     * metodo Component: Cambia de colo en la fila ultima
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        //fila
//        if (row == filaPintar) {
//            cell.setBackground(Color.green);
//        }else{
//            cell.setBackground(Color.white);
//        }
//        
        //columna
        if (column == columnaPintar) {
            cell.setBackground(Color.ORANGE);
        } else {
            //fila
            if (row == filaPintar) {
                cell.setBackground(Color.green);
            } else {
                cell.setBackground(Color.white);
            }
        }

        //return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, 2, column); //To change body of generated methods, choose Tools | Templates.
        return cell;
    }

    public int getFilaPintar() {
        return filaPintar;
    }

    public void setFilaPintar(int filaPintar) {
        this.filaPintar = filaPintar;
    }

    public void setColumnaPintar(int columna) {
        this.columnaPintar = columna;
    }

}
