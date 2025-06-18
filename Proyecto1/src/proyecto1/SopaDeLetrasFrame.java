package proyecto1;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

/**
 *
 * @author Jesús Schneider
 */

/**
 * JFrame que representa la interfaz gráfica principal de la aplicación Sopa de Letras.
 * Permite cargar un archivo con el tablero y el diccionario, realizar búsquedas
 * mediante DFS o BFS, y mostrar resultados en la interfaz.
 */
public class SopaDeLetrasFrame extends javax.swing.JFrame {

    private Tablero tablero;
    private Diccionario diccionario;
    private Buscador buscador;
    
    /**
     * Constructor por defecto que inicializa la ventana y configura los componentes.
     */
    public SopaDeLetrasFrame() {
        initComponents(); // NO MODIFICAR: generado por IDE
        configurarComponentes();
        configurarEventos();
    }
    
    /**
     * Configura la apariencia y propiedades iniciales de los componentes,
     * incluyendo la agrupación de los botones de opción y el formato de la tabla.
     */
    private void configurarComponentes() {
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioDFS);
        bg.add(radioBFS);
        radioDFS.setSelected(true);
        tableTablero.setRowHeight(50);
        tableTablero.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 24));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableTablero.setDefaultRenderer(Object.class, centerRenderer);
        for (int i=0; i<4; i++) {
            tableTablero.getColumnModel().getColumn(i).setPreferredWidth(50);
        }
        tableTablero.setEnabled(false);
        btnBuscarTodas.setEnabled(false);
        btnBuscarPalabra.setEnabled(false);
    }
    
    /**
     * Asocia los eventos de los botones para capturar acciones del usuario.
     */
    private void configurarEventos() {
        btnCargarArchivo.addActionListener(e -> cargarArchivo());
        btnBuscarTodas.addActionListener(e -> buscarTodas());
        btnBuscarPalabra.addActionListener(e -> buscarPalabra());
    }
    
    
    
    
    /**
     * Abre un selector de archivos para cargar el archivo con tablero y diccionario.
     * Procesa el archivo y actualiza la interfaz con los datos cargados.
     */
    private void cargarArchivo() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivo de texto (.txt)", "txt"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                List<String> lines = Files.readAllLines(file.toPath());
                ProcesadorArchivo procesador = new ProcesadorArchivo();
                procesador.procesar(lines.toArray(new String[0]));
                tablero = new Tablero(procesador.getMatrizLetras());
                diccionario = procesador.getDiccionario();
                buscador = new Buscador(tablero, diccionario);
                mostrarTablero();
                mostrarDiccionario();
                btnBuscarTodas.setEnabled(true);
                btnBuscarPalabra.setEnabled(true);
                textAreaResultado.setText("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar archivo:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
}

    /**
     * Muestra la matriz de letras del tablero en la tabla de la interfaz.
     */
    private void mostrarTablero() {
        Nodo[][] matriz = tablero.getMatrizNodos();
        DefaultTableModel model = (DefaultTableModel) tableTablero.getModel();
        model.setRowCount(4);
        model.setColumnCount(4);
        for (int f=0; f<4; f++) {
            for (int c=0; c<4; c++) {
                model.setValueAt(String.valueOf(matriz[f][c].getLetra()), f, c);
            }
        }
    }
    
    /**
     * Muestra las palabras del diccionario en el área de texto correspondiente.
     */
    private void mostrarDiccionario() {
        diccionario.ordenarPalabras();
        StringBuilder sb = new StringBuilder();
        String[] palabras = diccionario.obtenerPalabras();
        for (int i=0; i<diccionario.getCantidad(); i++) {
            if (palabras[i]!=null) sb.append(palabras[i]).append("\n");
        }
        textAreaDiccionario.setText(sb.toString());
    }
    
     /**
     * Realiza la búsqueda de todas las palabras en el diccionario y muestra los resultados.
     */
    private void buscarTodas() {
        if (buscador==null) {
            JOptionPane.showMessageDialog(this, "Primero carga un archivo.");
            return;
        }
        boolean usarDFS = radioDFS.isSelected();
        textAreaResultado.setText("Buscando todas las palabras con " + (usarDFS ? "DFS" : "BFS") + "...\n");
        long start = System.currentTimeMillis();
        String[] encontradas = usarDFS ? buscador.buscarTodasPalabrasDFS() : buscador.buscarTodasPalabrasBFS();
        long dur = System.currentTimeMillis()-start;
        for (String p : encontradas) {
            if (p!=null) textAreaResultado.append(p+"\n");
        }
        textAreaResultado.append("Tiempo total: "+dur+" ms\n");
    }
    
    /**
     * Busca una palabra específica en el tablero y muestra resultado.
     */
    private void buscarPalabra() {
        if (buscador==null) {
            JOptionPane.showMessageDialog(this, "Primero carga un archivo.");
            return;
        }
        String palabra = textFieldPalabra.getText().trim().toUpperCase();
        if (palabra.length()<3) {
            JOptionPane.showMessageDialog(this, "La palabra debe tener al menos 3 letras.");
            return;
        }
        boolean usarDFS = radioDFS.isSelected();
        textAreaResultado.setText("Buscando palabra \""+palabra+"\" con "+(usarDFS ? "DFS" : "BFS")+"...\n");
        long start = System.currentTimeMillis();
        boolean encontrada;
        if (usarDFS) {
            encontrada = buscador.buscarPalabraDFS(palabra);
        } else {
            encontrada = buscador.buscarPalabraBFS(palabra);
            if (encontrada) buscador.mostrarArbolBFS(palabra);
        }
        long dur = System.currentTimeMillis() - start;
        if (encontrada) {
            textAreaResultado.append("¡Encontrada!\n");
        } else {
            textAreaResultado.append("No encontrada.\n");
        }
        textAreaResultado.append("Tiempo de búsqueda: "+dur+" ms\n");
    }
    
    
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnCargarArchivo = new javax.swing.JButton();
        radioBFS = new javax.swing.JRadioButton();
        radioDFS = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        textFieldPalabra = new javax.swing.JTextField();
        btnBuscarPalabra = new javax.swing.JButton();
        btnBuscarTodas = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableTablero = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        textAreaDiccionario = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaResultado = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCargarArchivo.setText(" Cargar archivo");
        btnCargarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarArchivoActionPerformed(evt);
            }
        });
        jPanel1.add(btnCargarArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        radioBFS.setText("BFS");
        jPanel1.add(radioBFS, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, -1));

        radioDFS.setText("DFS");
        jPanel1.add(radioDFS, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, -1, -1));

        jLabel2.setText("Palabra:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, 20));
        jPanel1.add(textFieldPalabra, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 100, -1));

        btnBuscarPalabra.setText("Buscar Palabra");
        jPanel1.add(btnBuscarPalabra, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, -1));

        btnBuscarTodas.setText("Buscar Todas");
        jPanel1.add(btnBuscarTodas, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 660, 40));

        jLabel1.setText("Sopa De Letras");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, -1, -1));

        jPanel2.setLayout(new java.awt.GridLayout(1, 2));

        tableTablero.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "A", "B", "C", "D"
            }
        ));
        tableTablero.setRowHeight(50);
        tableTablero.setShowHorizontalLines(true);
        tableTablero.setShowVerticalLines(true);
        jScrollPane3.setViewportView(tableTablero);

        jPanel2.add(jScrollPane3);

        textAreaDiccionario.setColumns(20);
        textAreaDiccionario.setRows(5);
        jScrollPane4.setViewportView(textAreaDiccionario);

        jPanel2.add(jScrollPane4);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 660, 280));

        jLabel3.setText("Diccionario");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, -1, -1));

        jLabel4.setText("Tablero");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        textAreaResultado.setColumns(20);
        textAreaResultado.setRows(5);
        jScrollPane1.setViewportView(textAreaResultado);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 660, 100));

        jLabel5.setText("Resultados");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCargarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarArchivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCargarArchivoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SopaDeLetrasFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SopaDeLetrasFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SopaDeLetrasFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SopaDeLetrasFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SopaDeLetrasFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarPalabra;
    private javax.swing.JButton btnBuscarTodas;
    private javax.swing.JButton btnCargarArchivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JRadioButton radioBFS;
    private javax.swing.JRadioButton radioDFS;
    private javax.swing.JTable tableTablero;
    private javax.swing.JTextArea textAreaDiccionario;
    private javax.swing.JTextArea textAreaResultado;
    private javax.swing.JTextField textFieldPalabra;
    // End of variables declaration//GEN-END:variables
}
/**
 * Clase auxiliar para procesar archivo txt y extraer datos de tablero y diccionario.
 */
