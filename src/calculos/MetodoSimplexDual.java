/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elirpme
 */
public class MetodoSimplexDual {

    private List<String> listVariables;
    public double matriz[][];
    private String[] vectReferen;
    private List<String> listReferenDelect;
    private boolean bool[];
    private String res = "";
    private List<double[][]> listMatrizResultado;
    List<String[]> listVariablesCambiantes;
    List<Integer> filasPintar;
    List<Integer> columnasPintar;

    public MetodoSimplexDual(List<String> listVariables, double m[][], List<String> listVariablesDeCambio) {
        this.listVariables = listVariables;
        this.matriz = m;
        this.listVariablesCambiantes = new ArrayList<>();
        this.vectReferen = referencia(listVariables.get(0), listVariablesDeCambio);//posicion 0 se encuentra la Z
        this.listReferenDelect = new ArrayList<>();
        this.bool = new boolean[matriz[0].length - (vectReferen.length)];
        this.listMatrizResultado = new ArrayList<>();
        this.filasPintar = new ArrayList<>();
        this.columnasPintar = new ArrayList<>();
    }

    /**
     * Encuentra la columna pivoten del elemento mas negativo de la fila 0
     *
     * @param m matriz de tipo dounle, contiene los elemtos de un problema
     * @return la posicion de la columna con el elemento mas negativo, retorna
     * -1 cuando no encuentra elementos mas negativos
     */
    public int colum_min(double m[][], int fila) {
        double ini = 0;
        int aux = 0;
        double res = 0;
        System.out.println("fila: "+fila);
        for (int i = 0; i < m.length-1; i++) {
            System.out.println("--> " + m[0][i] + "  " + m[fila][i]);
            if ((m[0][i] > 0 && m[fila][i] < 0) || (m[0][i] < 0 && m[fila][i] > 0)) {
                res = m[0][i] / m[fila][i];
                System.out.println(m[0][i] + "/" + m[fila][i] + " = " + res);
                if (i > 0) {
                    if (res > ini) {
                        ini = res;
                        System.out.println("ini " + ini);
                        aux = i;
                    }
                }else{
                    ini = res;
                }
                
            }
        }
//        
//        if (aux > 0 && aux <= bool.length) {
//            bool[aux - 1] = true; // pongo un true cuando x son calculadas
//        }
//        if (ini >= 0 || aux > bool.length) { //elemento mas negativo verificar
        if (ini == 0) { //elemento mas negativo verificar
            return -1;
        }
        return aux;
    }

    /**
     * Encontrar la fila pivote del elemento menor respecto a la columna pivote,
     * realizando una operacion de division
     *
     * @param m matriz donde contiene los elementos
     * @return la fila en la cual se encuentra el elemento minimo
     */
    public int fila_min(double m[][]) {
        double ini = m[1][m[0].length - 1];
        System.out.println("ini : "+ini);
        int aux = 1; // 1 porque se comienza a calcular desde la fila 1
        for (int i = 2; i < m.length; i++) {
            if (ini > m[i][m[0].length - 1]) {
                ini = m[i][m[0].length - 1];
                aux = i;
            }
        }
        for(boolean t: bool)
            System.out.println("-> "+t);
        if (aux > 0 && aux <= bool.length) {
            if (bool[aux - 1]) { //si la columna seleccionada es true entonces se esta repitiendo la fila
                return -1;
            }
            bool[aux - 1] = true; // pongo un true cuando x son calculadas
        }
        //if (ini >= 0 || aux > bool.length) { //elemento mas negativo verificar
        System.out.println("aux--> "+aux);
        if (ini >= 0) { //elemento mas negativo verificar
            return -1;
        }
        return aux;
    }

    /**
     * Realiza las operacion basado en el metodo simplex
     *
     * @param newm es la matriz de entrada, sus valores estaran en constante
     * cambio por el metodo recursivo
     * @return la matriz resultante al realizar el metodo simplex
     */
    //METODO RECURSIVO
    public double[][] operar(double m[][], boolean t) {
        double newm[][] = new double[m.length][m[0].length];
        if (!t) {
            return m;
        }
        //1.- Seleccion de la fila y columna pivote
        int f = fila_min(m);
        if (f == -1) { //si el metodo colum_min retorna -1, es porque no encuentra el elemento mas negativo 
//            f = columnaFaltante();
//            if (f == -1) {
//                return m;
//            }
//            t = false;
            return newm;
        }
        System.out.println("f: " + f);
        int c = colum_min(m, f);
        System.out.println("c: " + c);
        this.filasPintar.add(f);
        this.columnasPintar.add(c);

        double prm = m[f][c];
        //res += "columna: " + c + "   fila: " + f + "  Elemento pivot " + prm + "\n";

        //si el elemento pivote es diferente de 1 entonce1s la fila pivote sera dividida entre el elemento pivote
        if (prm != 1) {
            for (int i = 0; i < m[0].length; i++) {
                newm[f][i] = (m[f][i] / prm);
            }
        } else {
            for (int i = 0; i < m[0].length; i++) {
                newm[f][i] = m[f][i];
            }
        }
        //2.- algoritmo metodo simplex 
        for (int i = 0; i < m.length; i++) {
            double aux = m[i][c];// obtenemos los elementos de la columna pivote
            if (i != f) { // pero debe ser diferente de ka fila pivote
                if (aux != 0) {//si la columna la que queremos hacer 0, el elemento seleccionado != 0 ejecute esto
                    for (int j = 0; j < m[0].length; j++) {
                        //System.out.print(m[i][j] + "-" + "(" + aux + "*" + m[f][j] + ") = ");
                        newm[i][j] = m[i][j] - (aux * newm[f][j]);
                        //System.out.print(m[i][j] + "\n");
                    }
                } else {// si el elemento seleccionado es 0 entonces solo copie igual de la matriz de ingreso
                    for (int j = 0; j < m[0].length; j++) {
                        newm[i][j] = m[i][j];
                    }
                }
            }
        }
        setVectReferen(f, c);
        this.listMatrizResultado.add(newm);
        return operar(newm, t);
    }

    public String imprimir(double m[][]) {
        String r = "\t";
        for (int i = 0; i < this.listVariables.size(); i++) {
            r += this.listVariables.get(i) + "\t";
        }
        r += "\n";
        for (int i = 0; i < m.length; i++) {
            for (int j = -1; j < m[0].length; j++) {
                if (j == -1) {
                    r += this.vectReferen[i] + "\t";
                } else {
                    r += m[i][j] + "\t";
                }
            }
            r += "\n";
        }
        return r;
    }

//    public String[] referencia(List<String> listV, int f) {
//        String newv[] = new String[matriz.length];
//        //llevo el vector con la letra la z
//        newv[0] = listV.get(0);
//        //obtengo la letra S
//        String aux = listV.get(listV.size() - 2);
//        for (int i = 0; i < f - 1; i++) {
//            newv[i + 1] = aux.substring(0, aux.length() - 1) + (i + 1);
//        }
//        
//        //Genero un nuevo vector de string por que si agrego el vector nev toda la lista sera remplazada por el
//        //ultimo que ingrese, por eso creo un nuevo vector 
//        String vect[] = new String[matriz.length];
//        for (int i = 0; i < newv.length; i++) {
//            vect[i] = newv[i];
//        }
//        this.listVariablesCambiantes.add(vect); // añado la primera posicion de las variables ejm z s1 s1 s3
//        
//        return newv;
//    }
    public String[] referencia(String Z, List<String> variableDeCambio) {
        String newv[] = new String[matriz.length];
        //llevo el vector con la letra la z
        newv[0] = Z;
        //obtengo la variables que eran constantemente cambiadas ejm: Z, S1, S2, S3
        for (int i = 0; i < variableDeCambio.size(); i++) {
            newv[i + 1] = variableDeCambio.get(i);
        }
        //Genero un nuevo vector de string por que si agrego el vector nev toda la lista sera remplazada por el
        //ultimo que ingrese, por eso creo un nuevo vector 
        String vect[] = new String[matriz.length];
        for (int i = 0; i < newv.length; i++) {
            vect[i] = newv[i];
        }
        this.listVariablesCambiantes.add(vect); // añado la primera posicion de las variables ejm z s1 s1 s3

        return newv;
    }

    public void setVectReferen(int f, int c) {
        System.out.println("f: "+f+" +++++  c: "+c);
        this.listReferenDelect.add(this.vectReferen[f]); // variables que se van ha encontrar
        this.vectReferen[f] = this.listVariables.get(c+1); // +1 porque en la pos 0 se encuentra la Z
        String newvr[] = new String[this.vectReferen.length];
        for (int i = 0; i < newvr.length; i++) {
            newvr[i] = this.vectReferen[i];
        }
        this.listVariablesCambiantes.add(newvr);
    }

    public List<String[]> getListReferen() {
        return this.listVariablesCambiantes;
    }

    public int columnaFaltante() {
        for (int i = 0; i < bool.length; i++) {
            if (!bool[i]) {
                bool[i] = true;
                return i + 1;
            }
        }
        return -1;
    }

    public String resultado(double m[][]) {
        String r = "\n";
        String aux = "";
        for (int i = 0; i < vectReferen.length; i++) {
            aux = vectReferen[i];
            r += aux + " = " + m[i][m[0].length - 1] + "\n";
        }
        for (int i = 0; i < this.listReferenDelect.size(); i++) {
            r += this.listReferenDelect.get(i) + " = 0\n";
        }
        return r;
    }

    public String calcular() {
        double aux[][] = null;
        this.listMatrizResultado.add(this.matriz);
        operar(this.matriz, true); //recursivo
        imprintList(this.listMatrizResultado);
        String respuesta = res + resultado(this.listMatrizResultado.get(this.listMatrizResultado.size() - 1));
        System.out.println("res " + respuesta);
        return respuesta;
    }

    public void imprintList(List<double[][]> lvect) {
        double m[][] = null;
        for (int i = 0; i < lvect.size(); i++) {
            m = lvect.get(i);
            for (int j = 0; j < m.length; j++) {
                for (int k = 0; k < m[0].length; k++) {
                    System.out.print(m[j][k] + "\t");
                }
                System.out.println("");
            }
            System.out.println("");
        }
    }

    public List<double[][]> getListMatrizResultado() {
        return listMatrizResultado;
    }

    public List<Integer> getFilasPintar() {
        return filasPintar;
    }

    public List<Integer> getColumnasPintar() {
        return columnasPintar;
    }

}
