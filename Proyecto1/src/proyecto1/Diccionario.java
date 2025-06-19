/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 * Clase que representa un diccionario simple de palabras utilizando un arreglo fijo.
 * @author Jesús Schneider
 */
public class Diccionario {
    private final String[] palabras;
    private int cantidad;
    
    /**
     * Construye un nuevo diccionario con capacidad fija.
     *
     * @param capacidad Capacidad máxima de palabras que puede almacenar el diccionario.
     */

    public Diccionario(int capacidad) {
        palabras = new String[capacidad];
        cantidad = 0;
    }
    
    /**
     * Agrega una palabra al diccionario si no existe y cumple con los requisitos mínimos.
     *
     * @param palabra Palabra a agregar.
     * @return {@code true} si la palabra fue agregada correctamente, {@code false} en caso contrario.
     */
    public boolean agregarPalabra(String palabra) {
        palabra = palabra.trim().toUpperCase();
        if (palabra.length() < 3) return false;
        if (contiene(palabra)) return false;
        if (cantidad >= palabras.length) return false;
        palabras[cantidad++] = palabra;
        return true;
    }
    
    /**
     * Verifica si el diccionario contiene una palabra específica.
     *
     * @param palabra Palabra a verificar.
     * @return {@code true} si la palabra existe, {@code false} si no.
     */
    public boolean contiene(String palabra) {
        palabra = palabra.trim().toUpperCase();
        for (int i = 0; i < cantidad; i++) if (palabras[i].equals(palabra)) return true;
        return false;
    }
    
    /**
     * Obtiene todas las palabras almacenadas en el diccionario.
     *
     * @return Arreglo con todas las palabras cargadas.
     */
    public String[] obtenerPalabras() {
        String[] res = new String[cantidad];
        for (int i = 0; i < cantidad; i++) res[i] = palabras[i];
        return res;
    }
    
    /**
     * Ordena el diccionario alfabéticamente usando un algoritmo burbuja.
     */
    public void ordenarPalabras() {
        for (int i = 0; i < cantidad - 1; i++) {
            for (int j = 0; j < cantidad - 1 - i; j++) {
                if (palabras[j].compareTo(palabras[j + 1]) > 0) {
                    String tmp = palabras[j];
                    palabras[j] = palabras[j + 1];
                    palabras[j + 1] = tmp;
                }
            }
        }
    }
    
    /**
     * Devuelve la cantidad de palabras que contiene el diccionario.
     *
     * @return Número de palabras almacenadas.
     */
    public int getCantidad() {
        return cantidad;
    }
}



