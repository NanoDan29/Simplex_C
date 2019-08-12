/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeFuncionesTabla;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Elirpme
 */
public class Transformacion_FuncionesTablaDual {

    private Hashtable<String, Integer> diccVariablesExis;
    private List<String> listVariablesExis;
    // De esta forma creo mi diccionario en java
    private List<Hashtable<String, Double>> listDiccionario = null;
    private List<String> listVariblesDeCambio;
    private boolean simple_dosFases = false; //false -> simple .. true -> dos fases
    private boolean despeje = false;

    public Transformacion_FuncionesTablaDual(String ant) {
        this.listVariblesDeCambio = new ArrayList<>();
        this.listDiccionario = diccionarioFunciones(ant);
        this.diccVariablesExis = list_Dic(listVariablesExis);
        //Agregamos la ultima variable existente que es R
        //esto lo hacemos porque al momento de ordenar R no quedaria al final
        this.listVariablesExis.add("R");//esta lista sera ordenada pero como no existe R lo agregamos para asi
        //crear las columnas de la tabla
        this.diccVariablesExis.put("R", this.diccVariablesExis.size());//en este diccioario no existe R porque antes de
        //ordenar en la lista listVariablesExis no existia R
        //asi que nos toca agregar a R al diccionario y su valor va ser 
        //la ultima posicion 
    }

    /**
     * ESte metodo separa las funciones, variables y valores de las funciones
     * que se ingresa
     *
     * @param ent : Es de tipo String donde se encuentra las funciones
     * @return : retorna un diccionario donde el key = "las variables" y el
     * Objeto = el valor que tiene cada variable Ejm 3x1 key = "x1" y valor = 3
     */
//    public List<Hashtable<String, Double>> diccionarioFunciones(String ent) {
//        List<Hashtable<String, Double>> listDiccionario = new ArrayList<>(); //lista de diccionarios
//        Hashtable<String, Double> dic = null; //diccionario
//        ent = ent.replace(" ", "");
//        String funciones[] = ent.split("\n"); // separo las funciones mediante "\n"
//        String posl = "";
//        String aux = "";
//        int cont_S = 1;
//        int cont_A = 1;
//        int cont_E = 1;
//        for (String r : funciones) {
//            dic = new Hashtable<>(); // inicializo el diccionario
//            for (int i = 0; i < r.length(); i++) {
//                posl = r.charAt(i) + "";
//                if (posl.equals("+")) { // cuando encuentre un +
//                    setDiccionario(dic, aux);// agregamos al diccionario
//                    aux = "";
//                    i++;
//                } else if (i != 0 && posl.equals("-") && !(r.charAt(i - 1) + "").equals("=")) {// cuando encuentre un -
//                    setDiccionario(dic, aux);
//                    aux = "";
//                } else if (posl.equals("=") && !(r.charAt(i - 1) + "").equals("<") && !(r.charAt(i - 1) + "").equals(">")) {// cuando encuentre un =
//                    setDiccionario(dic, aux);
//                    //indexOf permite encontrat cadenas, en este caso quiero encontrat Z
//                    //silo encuentra retorna la posicion en donde lo encontro comenzando desde 0
//                    //caso contrario retorna -1 
//                    int resultado = r.indexOf(listVariablesExis.get(0)); // r funcion
//                    if (resultado == -1) { // si no encuentra Z significa que es el metodo de dos fases
//                        setDiccionario(dic, "A" + cont_A);
//                        this.listVariblesDeCambio.add("A" + cont_A); // Agregamos a las variables que seran de Cambio
//                        setSimple_dosFases(true);
//                        cont_A++;
//                    }
//                    aux = "";
//                    i++;
//                } else if (i < r.length() - 1) { // r.length() - 1 : porque i le vamos a incrementarle +1
//                    if (posl.equals("<") && !(r.charAt(i + 1) + "").equals("=")) { // cuando encuentre un <
//
//                    } else if (posl.equals(">") && !(r.charAt(i + 1) + "").equals("=")) { //cuando encuentre un >
//
//                    } else if ((posl + r.charAt(i + 1) + "").equals("<=")) {//cuando encuentre un <=
//                        setDiccionario(dic, aux);
//                        setDiccionario(dic, "S" + cont_S); // al encontrar <= debemos poner las variables de holgura
//                        this.listVariblesDeCambio.add("S" + cont_S);
//                        aux = "";
//                        cont_S++;
//                        i += 2;
//                    } else if ((posl + r.charAt(i + 1) + "").equals(">=")) {//cuando encuentre un >=
//                        setDiccionario(dic, aux);
//                        setDiccionario(dic, "-E" + cont_E);
//                        setDiccionario(dic, "A" + cont_A);
//                        this.listVariblesDeCambio.add("A" + cont_A);
//                        cont_E++;
//                        cont_A++;
//                        aux = "";
//                        i += 2;
//                    }
//                }
//                aux += r.charAt(i) + ""; // acumulador 
//            }
//            // capturar el valor de la ultima funcion ejm <= 8
//            setDiccionario(dic, aux);
//            aux = "";
//            listDiccionario.add(dic);// agregamos el diccionario a la lista
//        }
//        return listDiccionario; // retorno una lista de diccionarios
//    }
    public List<Hashtable<String, Double>> diccionarioFunciones(String ent) {
        List<Hashtable<String, Double>> listDiccionario = new ArrayList<>(); //lista de diccionarios
        Hashtable<String, Double> dic = null; //diccionario
        ent = ent.replace(" ", "");
        String funciones[] = ent.split("\n"); // separo las funciones mediante "\n"
        String posl = "";
        String aux = "";
        int cont_S = 1;
        int cont_A = 1;
        int cont_E = 1;
        boolean bool = false;
        for (String r : funciones) {
            dic = new Hashtable<>(); // inicializo el diccionario
            setDespeje(false);
            for (int i = 0; i < r.length(); i++) {
                posl = r.charAt(i) + "";
                System.out.print(posl + " . ");
                if (posl.equals("+")) { // cuando encuentre un +
                    System.out.println("aux:-> " + aux);
                    setDiccionario(dic, aux);// agregamos al diccionario
                    aux = "";
                    i++;
                } else if (i != 0 && posl.equals("-") && !(r.charAt(i - 1) + "").equals("=")) {// cuando encuentre un -
                    setDiccionario(dic, aux);
                    aux = "";
                } else if (posl.equals("=") && !(r.charAt(i - 1) + "").equals("<") && !(r.charAt(i - 1) + "").equals(">")) {// cuando encuentre un =
                    setDiccionario(dic, aux);
                    //indexOf permite encontrat cadenas, en este caso quiero encontrat Z
                    //silo encuentra retorna la posicion en donde lo encontro comenzando desde 0
                    //caso contrario retorna -1 
                    int resultado = r.indexOf(listVariablesExis.get(0)); // r funcion
                    if (resultado == -1) { // si no encuentra Z significa que es el metodo de dos fases
                        setDiccionario(dic, "A" + cont_A);
                        this.listVariblesDeCambio.add("A" + cont_A); // Agregamos a las variables que seran de Cambio
                        setSimple_dosFases(true);
                        cont_A++;
                    }
                    setDespeje(true);
                    aux = "";
                    i++;
                } else if (i < r.length() - 1) { // r.length() - 1 : porque i le vamos a incrementarle +1
                    if (posl.equals("<") && !(r.charAt(i + 1) + "").equals("=")) { // cuando encuentre un <

                    } else if (posl.equals(">") && !(r.charAt(i + 1) + "").equals("=")) { //cuando encuentre un >

                    } else if ((posl + r.charAt(i + 1) + "").equals("<=")) {//cuando encuentre un <=
                        setDiccionario(dic, aux);
                        setDiccionario(dic, "S" + cont_S); // al encontrar <= debemos poner las variables de holgura
                        this.listVariblesDeCambio.add("S" + cont_S);
                        setDespeje(true);
                        aux = "";
                        cont_S++;
                        i += 2;
                    } else if ((posl + r.charAt(i + 1) + "").equals(">=")) {//cuando encuentre un >=
                        setDiccionario(dic, aux);
                        setDiccionario(dic, "S" + cont_S); // al encontrar <= debemos poner las variables de holgura
                        this.listVariblesDeCambio.add("S" + cont_S);
                        setDespeje(true);
                        aux = "";
                        cont_S++;
                        i += 2;
                        bool = true;
                    }
                }
                aux += r.charAt(i) + ""; // acumulador 
            }
            // capturar el valor de la ultima funcion ejm <= 8
            setDiccionario(dic, aux);
            aux = "";
            listDiccionario.add(dic);// agregamos el diccionario a la lista
            if (bool) { // cuando encontramos >= entonces todos sus valores le multiplicamos por -1
                for (Map.Entry<String, Double> entry : dic.entrySet()) {
                    if (!"S".equals(entry.getKey().substring(0,1))) {
                        dic.put(entry.getKey(), (-1*entry.getValue()));
                    }                    
                }
            }
            bool = false;
        }
        return listDiccionario; // retorno una lista de diccionarios
    }

    /**
     * Este metodo comienza a llegar el diccionario de las funciones, separa las
     * variables(key) y los valores(objecto)
     *
     * @param dic : diicionario al cual estamos agregando los datos
     * @param s : las variables y sus valores Ejm 3x
     */
    public void setDiccionario(Hashtable<String, Double> dic, String s) {
        s = generarSigno(s); //para el despeje
        String aux = "";
        String key = "";
        double valor = 0;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i))) {// cuando encuentre letras
                if (i == 0) {// si encontro letras en la primera posicion, al valor le ponemos 1 Ejm x  key="x" y valor=1
                    valor = 1;
                } else {
                    valor = Double.parseDouble(aux);
                }
                key = s.substring(i, s.length());// desde la posicion que encontro la letra, entonces capturamos hasta el final de la cadena 
                variablesExistentesList(key); // lleno la lista donde las variables no se repiten
                dic.put(key, valor); // angrego los datos al diccionario
                break;
            }
            // Cuando encuetra el sigmo - y despues segido de una letra ejm: -X1
            if (i == 0 && "-".equals(s.charAt(i) + "") && Character.isLetter(s.charAt(i + 1))) {
                aux = "-1";
            } else {
                aux += s.charAt(i) + "";
            }

            if (i == s.length() - 1) { // no tiene ninguna variable ya que se encunetra en la columna R Ejm 18
                key = "R";
                valor = Double.parseDouble(aux);
                dic.put(key, valor);
            }
        }
    }

    /**
     * Cambia el sigo cuando no se haya realizado el despeje
     *
     * @param variable : es la variable que entra Ejm: -2X1, por lo cual lo
     * transforma en 2X1 si se encuentra en la parte derecha despues de haber
     * encontrado un = , <= o >=
     * @return : retorna la variable modificada
     */
    public String generarSigno(String variable) {
        String newVariable = variable;
        // hacemos un control, si encontramos variables ejm: -2X1 entonces debemos debemos cambiar el signo 
        if (getDespeje()) { // si cuando el cursor de la funcion se encuentra en la parde derecha despues de encontrar <=, >= o =
            try {//intentamos transformar a double la cadena que ingresa          
                double valor = Double.parseDouble(variable);
                return newVariable;
            } catch (Exception e) { // si no se puede significa que es una variable, entonces hacemos la siguiente validacion
                if ((variable.substring(0, 1)).equals("-")) {
                    newVariable = variable.substring(1, variable.length());
                } else if (Character.isLetter(variable.charAt(0)) || Character.isDigit(variable.charAt(0))) { //cuando encontramos letra o numero
                    newVariable = "-" + variable;
                } else if ("+".equals(variable.substring(0, 1))) { //cuando ecuentre un +
                    newVariable = "-" + variable.substring(1, variable.length());
                }
            }
        }
        return newVariable;
    }

    /**
     * Este metodo solo agrega a las variables que no se repiten Ejm x1 x2 s1 R
     *
     * @param s : variables
     */
    public void variablesExistentesList(String s) {
        if (this.listVariablesExis == null) { // si la lista esta vacia lo inicializo
            this.listVariablesExis = new ArrayList<>();
            this.listVariablesExis.add(s);
        } else {
            boolean t = true;
            for (String r : this.listVariablesExis) {
                if (s.equals(r)) {
                    t = false;
                    break;
                }
            }
            if (t) {
                this.listVariablesExis.add(s);
            }
        }
    }

    /**
     * La Lista donde contiene las variables unicas lo pasamos al diccionario
     * para establecer que columna corresponde
     *
     * @param l : lista de las variables unicas
     * @return : retorna el diccionario
     */
    public Hashtable<String, Integer> list_Dic(List<String> l) {
        Hashtable<String, Integer> dic = new Hashtable<>();
        l = ordenarList(l);

        int cont = 0;
        for (String key : l) {
            dic.put(key, cont);
            cont++;
        }
        return dic;
    }

    /**
     * Ordena la lista de forma descendente Ejm z y z w v ... a
     *
     * @param l : lista ha ser ordenada de forma descendente
     * @return : retorna la lista
     */
//    public List<String> ordenarList(List<String> l) {
//        String aux = "";
//        for (int i = 0; i < l.size(); i++) {
//            for (int j = 0; j < i; j++) {
//                if ((l.get(i).charAt(0)+"").compareTo((l.get(j).charAt(0)+"")) > 0) { // cojo la posicion 0 del string
//                    aux = l.get(i);
//                    l.set(i, l.get(j));
//                    l.set(j, aux);
//                }
//            }
//        }
//        return l;
//    }
    public List<String> ordenarList(List<String> l) {
        String aux = "";
        for (int i = 0; i < l.size(); i++) {
            for (int j = 0; j < i; j++) {
                if ((l.get(i)).compareTo((l.get(j))) > 2) { // cojo la posicion 0 del string
                    aux = l.get(i);
                    l.set(i, l.get(j));
                    l.set(j, aux);
                }
            }
        }
        String aux2 = l.get(1).substring(0, 1);
        int cont = 1;
        for (int i = 1; i < l.size(); i++) {
            if (aux2.equals(l.get(i).substring(0, 1))) {
                l.set(i, aux2 + cont);
                cont++;
            } else {
                aux2 = l.get(i).substring(0, 1);
                cont = 1;
                l.set(i, aux2 + cont);
                cont++;
            }
        }
        return l;
    }

    public Hashtable<String, Integer> getDiccVariablesExis() {
        return diccVariablesExis;
    }

    public List<Hashtable<String, Double>> getListDiccionario() {
        return listDiccionario;
    }

    public List<String> getListVariablesExis() {
        return listVariablesExis;
    }

    public List<String> getListVariblesDeCambio() {
        return listVariblesDeCambio;
    }

    public boolean getSimple_dosFases() {
        return simple_dosFases;
    }

    public void setSimple_dosFases(boolean simple_dosFases) {
        this.simple_dosFases = simple_dosFases;
    }

    public boolean getDespeje() {
        return despeje;
    }

    public void setDespeje(boolean despeje) {
        this.despeje = despeje;
    }

}
