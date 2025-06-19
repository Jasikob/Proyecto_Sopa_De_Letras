/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 * Implementación de búsquedas DFS y BFS,
 * incluye visualización de árbol BFS con GraphStream.
 */
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

public class Buscador {
    private final Tablero tablero;
    private final Diccionario diccionario;

    /**
     * Constructor que inicializa el Buscador con un tablero y un diccionario.
     * 
     * @param tablero El tablero de letras para buscar.
     * @param diccionario El diccionario con las palabras a buscar.
     */
    public Buscador(Tablero tablero, Diccionario diccionario) {
        this.tablero = tablero;
        this.diccionario = diccionario;
    }

    /**
     * Busca todas las palabras del diccionario en el tablero usando DFS.
     * 
     * @return un arreglo con las palabras encontradas.
     */
    public String[] buscarTodasPalabrasDFS() {
        String[] palabras = diccionario.obtenerPalabras();
        Arreglo encontradas = new Arreglo(1000);
        for (int i = 0; i < diccionario.getCantidad(); i++) {
            String palabra = palabras[i];
            if (palabra.length() < 3) continue;
            if (buscarPalabraDFS(palabra)) {
                encontradas.agregar(palabra);
            }
        }
        encontradas.ordenar();
        return encontradas.getArreglo();
    }

    /**
     * Busca todas las palabras del diccionario en el tablero usando BFS.
     * 
     * @return un arreglo con las palabras encontradas.
     */
    public String[] buscarTodasPalabrasBFS() {
        String[] palabras = diccionario.obtenerPalabras();
        Arreglo encontradas = new Arreglo(1000);
        for (int i = 0; i < diccionario.getCantidad(); i++) {
            String palabra = palabras[i];
            if (palabra.length() < 3) continue;
            if (buscarPalabraBFS(palabra)) {
                encontradas.agregar(palabra);
            }
        }
        encontradas.ordenar();
        return encontradas.getArreglo();
    }

    /**
     * Busca una palabra específica en el tablero usando DFS.
     * 
     * @param palabra La palabra a buscar.
     * @return verdadero si la palabra fue encontrada, falso en caso contrario.
     */
    public boolean buscarPalabraDFS(String palabra) {
        Nodo[][] matriz = tablero.getMatrizNodos();
        for (int f = 0; f < 4; f++) {
            for (int c = 0; c < 4; c++) {
                if (matriz[f][c].getLetra() == palabra.charAt(0)) {
                    boolean[][] visitado = new boolean[4][4];
                    if (dfs(matriz[f][c], palabra, 0, visitado)) return true;
                }
            }
        }
        return false;
    }

    /**
     * Método recursivo auxiliar para la búsqueda DFS.
     */
    private boolean dfs(Nodo nodo, String palabra, int index, boolean[][] visitado) {
        int f = nodo.getFila();
        int c = nodo.getColumna();
        if (visitado[f][c]) return false;
        if (nodo.getLetra() != palabra.charAt(index)) return false;
        if (index == palabra.length() - 1) return true;
        visitado[f][c] = true;
        Nodo[] vecinos = nodo.getAdyacentes();
        for (int i = 0; i < vecinos.length; i++) {
            if (dfs(vecinos[i], palabra, index + 1, visitado)) {
                visitado[f][c] = false;
                return true;
            }
        }
        visitado[f][c] = false;
        return false;
    }

    /**
     * Busca una palabra específica en el tablero usando BFS.
     * 
     * @param palabra La palabra a buscar.
     * @return verdadero si la palabra fue encontrada, falso en caso contrario.
     */
    public boolean buscarPalabraBFS(String palabra) {
        Nodo[][] matriz = tablero.getMatrizNodos();
        for (int f = 0; f < 4; f++) {
            for (int c = 0; c < 4; c++) {
                if (matriz[f][c].getLetra() == palabra.charAt(0)) {
                    boolean encontrada = bfs(matriz[f][c], palabra);
                    if (encontrada) {
                        mostrarArbolBFS(palabra);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Método auxiliar para la búsqueda BFS.
     */
    private boolean bfs(Nodo inicio, String palabra) {
        ColaEstados cola = new ColaEstados(10000);
        boolean[][] visitadoIni = new boolean[4][4];
        visitadoIni[inicio.getFila()][inicio.getColumna()] = true;
        cola.encolar(new Estado(inicio, 0, copiarMatriz(visitadoIni)));

        while (!cola.estaVacia()) {
            Estado e = cola.desencolar();
            if (e.nodo.getLetra() != palabra.charAt(e.index)) continue;
            if (e.index == palabra.length() - 1) return true;
            Nodo[] vecinos = e.nodo.getAdyacentes();
            for (int i = 0; i < vecinos.length; i++) {
                int rf = vecinos[i].getFila();
                int rc = vecinos[i].getColumna();
                if (!e.visitado[rf][rc]) {
                    boolean[][] nuevoVisitado = copiarMatriz(e.visitado);
                    nuevoVisitado[rf][rc] = true;
                    cola.encolar(new Estado(vecinos[i], e.index + 1, nuevoVisitado));
                }
            }
        }
        return false;
    }

    private boolean[][] copiarMatriz(boolean[][] original) {
        boolean[][] copia = new boolean[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(original[i], 0, copia[i], 0, 4);
        }
        return copia;
    }

    /**
     * Muestra el árbol de recorrido BFS para la palabra dada usando GraphStream.
     *
     * @param palabra La palabra para la que se muestra el árbol.
     */
    public void mostrarArbolBFS(String palabra) {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("Árbol BFS");

        for (int i = 0; i < palabra.length(); i++) {
            Node node = graph.addNode(String.valueOf(i));
            node.setAttribute("ui.label", String.valueOf(palabra.charAt(i)));
        }
        for (int i = 0; i < palabra.length() - 1; i++) {
            graph.addEdge(i + "-" + (i + 1), String.valueOf(i), String.valueOf(i + 1));
        }

        graph.setAttribute("ui.stylesheet",
                "node { fill-color: #007bff; size: 35px; text-size: 22px; text-color: white; }" +
                      "edge { fill-color: #555555; }");
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");

        Viewer viewer = graph.display();
        viewer.enableAutoLayout();
    }

    /**
     * Clase interna para representar un estado en BFS.
     */
    private static class Estado {
        Nodo nodo;
        int index;
        boolean[][] visitado;

        Estado(Nodo nodo, int index, boolean[][] visitado) {
            this.nodo = nodo;
            this.index = index;
            this.visitado = visitado;
        }
    }

    /**
     * Implementación básica de una cola circular para estados BFS.
     */
    private static class ColaEstados {
        private final Estado[] cola;
        private int frente = 0;
        private int fin = 0;
        private int tamaño = 0;

        ColaEstados(int capacidad) {
            cola = new Estado[capacidad];
        }

        void encolar(Estado e) {
            if (tamaño == cola.length)
                throw new RuntimeException("Cola llena");
            cola[fin] = e;
            fin = (fin + 1) % cola.length;
            tamaño++;
        }

        Estado desencolar() {
            if (tamaño == 0) return null;
            Estado e = cola[frente];
            frente = (frente + 1) % cola.length;
            tamaño--;
            return e;
        }

        boolean estaVacia() {
            return tamaño == 0;
        }
    }
}

