/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author ASUS i7
 */
public class principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //operacion c = new operacion();
        //operacion2 c2 = new operacion2();
        
//        double m[][] = {{1, -300, -400, 0, 0, 0, 0},
//                        {0, 2, 3, 1, 0, 0, 900},
//                        {0, 1, 0, 0, 1, 0, 300},
//                        {0, 0, 1, 0, 0, 1, 200}};
////        
//        double m[][] = {{1, -10, -20, 0, 0, 0, 0},
//                        {0, 4, 2, 1, 0, 0, 20},
//                        {0, 8, 8, 0, 1, 0, 20},
//                        {0, 0, 2, 0, 0, 1, 10}};

//        double m[][] = {{1, -18, -10, -12, 0, 0, 0, 0},
//                        {0, 4, 3, 2, 1, 0, 0, 400},
//                        {0, 8, 7, 12, 0, 1, 0, 1000},
//                        {0, 7, 9, 2, 0, 0, 1, 800}};

//        double m[][] = {{1, -18, -10, -12, 0, 0, 0, 0},
//                        {0, 4, 3, 2, 1, 0, 0, 400},
//                        {0, 8, 7, 12, 0, 1, 0, 1000},
//                        {0, 7, 9, 2, 0, 0, 1, 800}};
    
//        Object m[][] = {{"Z", "X1", "X2", "S1", "S2", "S3", "R"},
//                        {1, -300, -400, 0, 0, 0, 0},
//                        {0, 2, 3, 1, 0, 0, 900},
//                        {0, 1, 0, 0, 1, 0, 300},
//                        {0, 0, 1, 0, 0, 1, 200}};

//        Object m[][] = {{"Z", "X1", "X2", "S1", "S2", "S3", "R"},
//                        {1, -10, -20, 0, 0, 0, 0},
//                        {0, 4, 2, 1, 0, 0, 20},
//                        {0, 8, 8, 0, 1, 0, 20},
//                        {0, 0, 2, 0, 0, 1, 10}};

//        Object m[][] = {{"Z", "X1", "X2", "S1", "S2", "S3", "R"},
//                        {1, -300, -400, 0, 0, 0, 0},
//                        {0, 2, 3, 1, 0, 0, 900},
//                        {0, 1, 0, 0, 1, 0, 300},
//                        {0, 0, 1, 0, 0, 1, 200}};
//
        Object m[][] = {{"Z", "X1", "X2", "X3", "S1", "S2", "S3", "R"},
                        {1, -18, -10, -12, 0, 0, 0, 0},
                        {0, 4, 3, 2, 1, 0, 0, 400},
                        {0, 8, 7, 12, 0, 1, 0, 1000},
                        {0, 7, 9, 2, 0, 0, 1, 800}};

//        System.out.println("");
//        Object m[][] = {{"Z", "X1", "X2", "S1", "S2", "S3", "S4", "R"},
//                        {1, -3, -4, 0, 0, 0, 0, 0, 0},
//                        {0, 0.3, 0.5, 1, 0, 0, 0, 300},
//                        {0, 1, 1.5, 0, 1, 0, 0, 750},
//                        {0, 0, 0.5, 0, 0, 1, 0, 200},
//                        {0, 1, 1, 0, 0, 0, 1, 600}};
//        

        //c2.operar(m);
//        operacion3 c3 = new operacion3(m);
//        System.out.println(c3.imprimir(c3.matriz));
//        c3.calcular();
        //SUPER IMPORTANTE
        /*List<int[]> l = new ArrayList<>();
        int v1[] = {1,3,3,7,6};
        l.add(v1);
        v1[0]=20;
        l.add(v1);
        v1[0]=30;
        l.add(v1)*/;
//        int v2[] = {2,3,3,7,6};
//        l.add(v2);
//        int v3[] = {3,3,3,7,6};
//        l.add(v3);
        
        /*int aux[] = null;
        for (int i = 0; i < l.size(); i++) {
            aux = l.get(i);
            for (int j = 0; j < aux.length; j++) {
                System.out.print(aux[j]+"  ");
            }
            System.out.println("");
        }*/

         String a="eeijen sq";
         System.out.println("dsds "+a.substring(0,1));
            int intIndex = a.indexOf("sql");
            System.out.println("intIndex: "+intIndex);
          if(intIndex == - 1){
             System.out.println("palabra encontrada");
          }else{
             System.out.println("palabra no encontrada"
             + intIndex);
          }
    }
}
