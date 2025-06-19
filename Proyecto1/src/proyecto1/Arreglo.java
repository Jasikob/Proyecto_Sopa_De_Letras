/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 * Clase para gestionar un arreglo simple de Strings
 * @author Jesús Schneider
 */
public class Arreglo {
    private String[] arr;
    private int size;

    /**
     * Constructor que crea un arreglo con una capacidad inicial.
     *
     * @param capacidadInicial capacidad inicial del arreglo.
     */
    public Arreglo(int capacidadInicial) {
        arr = new String[capacidadInicial];
        size = 0;
    }
    
    /**
     * Agrega una cadena al arreglo, ampliando si es necesario.
     *
     * @param s la cadena a agregar.
     */

    public void agregar(String s) {
        if (size == arr.length) {
            String[] nuevo = new String[arr.length*2];
            System.arraycopy(arr,0,nuevo,0,arr.length);
            arr = nuevo;
        }
        arr[size++] = s;
    }

    /**
     * Obtiene un arreglo con las cadenas almacenadas.
     *
     * @return arreglo con las cadenas agregadas.
     */
    public String[] getArreglo() {
        String[] res = new String[size];
        System.arraycopy(arr,0,res,0,size);
        return res;
    }

    /**
     * Ordena las cadenas en orden alfabético.
     */
    public void ordenar() {
        for (int i=0; i<size-1; i++) {
            for (int j=0; j<size-1-i; j++) {
                if (arr[j].compareTo(arr[j+1])>0) {
                    String tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
            }
        }
    }
}


