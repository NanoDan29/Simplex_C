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
public class MetodoDosFases {
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

    public MetodoDosFases(List<String> listVariables, double m[][], List<String> listVariablesDeCambio) {
        this.listVariables = listVariables;
        this.matriz = m;
        this.listVariablesCambiantes = new ArrayList<>();
        this.vectReferen = referencia(listVariables.get(0), listVariablesDeCambio);//posicion 0 se encuentra la Z
        this.listReferenDelect = new ArrayList<>();
        this.bool = new boolean[matriz[0].length - (vectReferen.length + 1)];
        this.listMatrizResultado = new ArrayList<>();    
        this.filasPintar = new ArrayList<>();
        this.columnasPintar = new ArrayList<>();
    }
    
    public double[][] hacerCero_fila0(double m[][]){
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j]+"\t");
            }
            System.out.println("");
        }
        double newm[][] = new double[m.length][m[0].length];
        String sV[] = getListVariablesCambiantes().get(0);
        for (int i = 0; i < sV.length; i++) {
            if ("A".equals(sV[i].charAt(0)+"")) {
                System.out.println("i == "+i);
                for (int j = 0; j < newm.length; j++) {
                    for (int k = 0; k < newm[0].length; k++) {
                        if (j == 0) {
                        System.out.println(m[0][k]+" "+m[i][k]);
                            newm[0][k] += m[0][k]+m[i][k];
                            System.out.println("-> "+newm[0][k]);
                            
                        }else{
                            newm[j][k] = m[j][k];
                        }
                    }
                     
                }
            }
        }
        return newm;
    }

    /**
     * Encuentra la columna pivoten del elemento mas negativo de la fila 0
     *
     * @param m matriz de tipo dounle, contiene los elemtos de un problema
     * @return la posicion de la columna con el elemento mas negativo, retorna
     * -1 cuando no encuentra elementos mas negativos
     */
    public int colum_min(double m[][]) {
        //System.out.println("Comuna mayor");
//        for (int i = 0; i < m.length; i++) {
//            for (int j = 0; j < m[0].length; j++) {
//                System.out.print(m[i][j]+"\t");
//            }
//            System.out.println("");
//        }
//        
        double ini = m[0][0];
        int aux = 0;
        for (int i = 1; i < m[0].length-1; i++) {
            if (ini < m[0][i]) {
                ini = m[0][i];
                aux = i;
            }
        }
        if (aux > 0 && aux <= bool.length) {
            bool[aux - 1] = true; // pongo un true cuando x son calculadas
        }
        if (ini <= 0 || aux > bool.length) { //elemento mas negativo verificar
            //System.out.println("-->-<-<> "+(-1));
            return -1;
        }
        //System.out.println("aux ---->  "+aux);
        return aux;
    }

    /**
     * Encontrar la fila pivote del elemento menor respecto a la columna pivote,
     * realizando una operacion de division
     *
     * @param m matriz donde contiene los elementos
     * @return la fila en la cual se encuentra el elemento minimo
     */
    public int fila_min(double m[][], int colum) {
        double ini = m[1][m[0].length - 1] / m[1][colum];
        double aux = 0;
        int c = 1;
        for (int i = 2; i < m.length; i++) {
            aux = m[i][m[0].length - 1] / m[i][colum];
            if (ini > aux && aux > 0) {
                ini = aux;
                c = i;
            } else if (ini <= 0) { // si ini es menos q 0 entonces replazo ini por el siguiente elemento 
                ini = aux;
                c = i;
            }
        }
        return c;
    }

    /**
     * Realiza las operacion basado en el metodo simplex
     *
     * @param newm es la matriz de entrada, sus valores estaran en constante cambio
     * por el metodo recursivo
     * @return la matriz resultante al realizar el metodo simplex
     */
    //METODO RECURSIVO
    public double[][] operar(double m[][], boolean t) {
        double newm[][] = new double[m.length][m[0].length];
        if (!t) {
            return m;
        }
        //1.- Seleccion de la fila y columna pivote
        int c = colum_min(m);
        System.out.println("colum1 : "+c);
        if (c == -1) { //si el metodo colum_mayor retorna -1, es porque no encuentra el elemento mas negativo 
            c = columnaFaltante();
            System.out.println("colum2 : "+c);
            if (c == -1) {
                return m;
            }
            t = false;
        }
        int f = fila_min(m, c);
        System.out.println("fila : "+f);
        this.filasPintar.add(f);
        this.columnasPintar.add(c);

        double prm = m[f][c];
        //res += "columna: " + c + "   fila: " + f + "  Elemento pivot " + prm + "\n";

        //si el elemento pivote es diferente de 1 entonce1s la fila pivote sera dividida entre el elemento pivote
        if (prm != 1) {
            for (int i = 0; i < m[0].length; i++) {
                newm[f][i] = (m[f][i] / prm);
            }
        }else{
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
                }else{// si el elemento seleccionado es 0 entonces solo copie igual de la matriz de ingreso
                    for (int j = 0; j < m[0].length; j++) {
                        newm[i][j] = m[i][j];
                    }
                }
            }
        }
        setVectReferen(f, c);
        this.listMatrizResultado.add(newm);
        
        imprimir(newm);
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
        for (int i = 0; i < variableDeCambio.size(); i++){
            newv[i+1] = variableDeCambio.get(i);
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
        this.listReferenDelect.add(this.vectReferen[f]); // variables que se van ha encontrar
        this.vectReferen[f] = this.listVariables.get(c);
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
        double newm[][] = hacerCero_fila0(this.matriz);
        this.listMatrizResultado.add(newm);
        System.out.println("***************");
        for (int i = 0; i < this.matriz.length; i++) {
            for (int j = 0; j < this.matriz[0].length; j++) {
                System.out.print(newm[i][j]+"\t");
            }
            System.out.println("");
        }
        System.out.println("---------------");
        //operar(newm, true); //recursivo
        System.out.println("-------5-----");
        //imprintList(this.listMatrizResultado);
        //String respuesta = res + resultado(this.listMatrizResultado.get(this.listMatrizResultado.size()-1));
        //System.out.println("res "+respuesta);
        return "";
        //return respuesta;
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

    public String[] getVectReferen() {
        return vectReferen;
    }

    public List<String[]> getListVariablesCambiantes() {
        return listVariablesCambiantes;
    }

    public double[][] getMatriz() {
        return matriz;
    }
    
    
    
}
