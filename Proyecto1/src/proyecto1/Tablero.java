/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 * Clase que representa el tablero 4x4 como un grafo de nodos con conexión a vecinos adyacentes.
 * @author Jesús Schneider
 */
public class Tablero {
    private final Nodo[][] matrizNodos = new Nodo[4][4];

    /**
     * Crea el tablero y conecta cada nodo con sus vecinos adyacentes (8 posibles).
     *
     * @param letras Matriz 4x4 de letras.
     */
    public Tablero(char[][] letras) {
        if (letras == null || letras.length != 4)
            throw new IllegalArgumentException("Matriz debe ser 4x4");
        for (int i = 0; i < 4; i++)
            if (letras[i] == null || letras[i].length != 4)
                throw new IllegalArgumentException("Matriz debe ser 4x4");

        for (int f = 0; f < 4; f++)
            for (int c = 0; c < 4; c++)
                matrizNodos[f][c] = new Nodo(letras[f][c], f, c);

        for (int f = 0; f < 4; f++)
            for (int c = 0; c < 4; c++) {
                Nodo nodo = matrizNodos[f][c];
                for (int df = -1; df <= 1; df++)
                    for (int dc = -1; dc <= 1; dc++) {
                        int nf = f + df, nc = c + dc;
                        if (nf >= 0 && nf < 4 && nc >= 0 && nc < 4 && !(df == 0 && dc == 0))
                            nodo.agregarAdyacente(matrizNodos[nf][nc]);
                    }
            }
    }

    /**
     * Devuelve la matriz de nodos.
     *
     * @return Matriz 4x4 de nodos.
     */
    public Nodo[][] getMatrizNodos() {
        return matrizNodos;
    }
}



