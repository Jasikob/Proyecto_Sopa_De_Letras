/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 * Clase responsable de procesar un archivo de texto que contiene
 * un diccionario de palabras y un tablero de letras para la sopa de letras.
 * @author Jesús Schneider
 */
public class ProcesadorArchivo {
    private String[] diccionario = new String[1000];
    private int cantidadDic = 0;
    private char[][] matrizLetras = new char[4][4];
    
    /**
     * Procesa las líneas del archivo de texto para extraer el diccionario y la matriz de letras.
     */

    public void procesar(String[] lineas) {
        boolean enDiccionario = false;
        boolean enTablero = false;
        int filaTablero = 0;

        for (String linea : lineas) {
            linea = linea.trim();
            if (linea.equalsIgnoreCase("dic")) {
                enDiccionario = true;
                continue;
            }
            if (linea.equalsIgnoreCase("/dic")) {
                enDiccionario = false;
                continue;
            }
            if (linea.equalsIgnoreCase("tab")) {
                enTablero = true;
                filaTablero = 0;
                continue;
            }
            if (linea.equalsIgnoreCase("/tab")) {
                enTablero = false;
                continue;
            }

            if (enDiccionario && !linea.isEmpty()) {
                if (cantidadDic >= diccionario.length) {
                    throw new IllegalArgumentException("El diccionario es demasiado grande");
                }
                diccionario[cantidadDic++] = linea.toUpperCase();
            } else if (enTablero) {
                String[] letras = linea.split(",");
                if (letras.length != 4) {
                    throw new IllegalArgumentException("Cada fila del tablero debe tener 4 letras");
                }
                if (filaTablero >= 4) {
                    throw new IllegalArgumentException("El tablero debe contener exactamente 4 filas");
                }
                for (int col = 0; col < 4; col++) {
                    matrizLetras[filaTablero][col] = letras[col].charAt(0);
                }
                filaTablero++;
            }
        }
        if (filaTablero != 4) {
            throw new IllegalArgumentException("Faltan filas en el tablero");
        }
    }
    
    /**
     * Obtiene el diccionario de palabras almacenadas tras el procesamiento.
     *
     * @return Instancia de {@link Diccionario} con las palabras cargadas.
     */
    public Diccionario getDiccionario() {
        Diccionario dic = new Diccionario(cantidadDic);
        for (int i = 0; i < cantidadDic; i++) {
            dic.agregarPalabra(diccionario[i]);
        }
        return dic;
    }
    
    /**
     * Obtiene la matriz 4x4 de letras del tablero tras el procesamiento.
     *
     * @return Matriz de caracteres 4x4 que representa el tablero.
     */
    public char[][] getMatrizLetras() {
        return matrizLetras;
    }
}

