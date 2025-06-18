/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 * Nodo que representa una letra del tablero y sus nodos vecinos adyacentes.
 * @author Jesús Schneider
 */
public class Nodo {
    private final char letra;
    private final int fila, columna;
    private final Nodo[] adyacentes = new Nodo[8];
    private int cantidadAdyacentes;

    /**
     * Crea un nuevo nodo.
     *
     * @param letra  Letra que representa el nodo.
     * @param fila   Fila en el tablero.
     * @param columna Columna en el tablero.
     */
    public Nodo(char letra, int fila, int columna) {
        this.letra = Character.toUpperCase(letra);
        this.fila = fila;
        this.columna = columna;
        this.cantidadAdyacentes = 0;
    }
    
    /**
     * Obtiene la letra del nodo.
     *
     * @return Letra del nodo.
     */
    public char getLetra() { 
        return letra; 
    }
    
    /**
     * Obtiene la fila del nodo.
     *
     * @return Índice de fila.
     */
    public int getFila() { 
        return fila; 
    }
    
    /**
     * Obtiene la columna del nodo.
     *
     * @return Índice de columna.
     */
    public int getColumna() { 
        return columna; 
    }

    /**
     * Agrega un nodo adyacente si no está ya en la lista de adyacentes.
     *
     * @param nodo Nodo vecino a agregar.
     */
    public void agregarAdyacente(Nodo nodo) {
        if (nodo == null) return;
        for (int i = 0; i < cantidadAdyacentes; i++)
            if (adyacentes[i] == nodo) return;
        if (cantidadAdyacentes < 8) adyacentes[cantidadAdyacentes++] = nodo;
    }

    /**
     * Obtiene los nodos adyacentes.
     *
     * @return Arreglo con los nodos vecinos.
     */
    public Nodo[] getAdyacentes() {
        Nodo[] res = new Nodo[cantidadAdyacentes];
        System.arraycopy(adyacentes, 0, res, 0, cantidadAdyacentes);
        return res;
    }
}

