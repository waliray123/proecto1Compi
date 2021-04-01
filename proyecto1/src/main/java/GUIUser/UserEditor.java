/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIUser;

import Controladores.Acciones;
import Generadores.GenRespuestas;
import Generadores.GenStrErrors;
import GeneradoresRespuestas.GenStrResp;
import analizadores.LexerForms;
import analizadores.LexerIndigo;
import analizadores.ParserForms;
import analizadores.ParserIndigo;
import analizadoresRespuestas.LexerResp;
import analizadoresRespuestas.ParserResp;
import java.awt.HeadlessException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import objetos.Campo;
import objetos.ControlSemantico;
import objetos.ErrorCom;
import objetos.Formulario;
import objetos.Respuesta;
import objetos.Solicitud;
import objetos.UsuariosFormularios;

/**
 *
 * @author user-ubunto
 */
public class UserEditor extends javax.swing.JFrame {

    private int lin;
    private int col;
    private UsuariosFormularios dbUsuariosForms;
    private String usuarioLog;
    private String pathCargado;
    private List<ErrorCom> erroresAcciones;
    private List<Respuesta> respuestasAcciones;

    /**
     * Creates new form UserEditor
     */
    public UserEditor() {
        initComponents();

        //Se crea una db de usuarios 
        this.dbUsuariosForms = new UsuariosFormularios();
        this.usuarioLog = "";
        this.jLabel6.setText(usuarioLog);

        this.pathCargado = "";
        this.jTextArea1.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                int pos = e.getDot();
                try {
                    lin = jTextArea1.getLineOfOffset(pos) + 1;
                    col = pos - jTextArea1.getLineStartOffset(lin - 1) + 1;
                    setLineaYColumna();
                } catch (BadLocationException exc) {
                    System.out.println(exc);
                }
            }
        });
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
        );

        jLabel1.setText("Linea");

        jLabel2.setText("Columna");

        jLabel3.setText("1");
        jLabel3.setName("labelLIN"); // NOI18N

        jLabel4.setText("1");
        jLabel4.setName("labelCOL"); // NOI18N

        jLabel5.setText("Usuario:");

        jLabel6.setText("jLabel6");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(73, 73, 73))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 5, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)))
        );

        jMenu1.setText("Archivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setText("Cargar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem2.setText("Guardar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem3.setText("GuardarComo");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Compilar");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        jMenuItem4.setText("jMenuItem4");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        String documento = "";
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Archivo de Texto", "txt"));
            fileChooser.setAcceptAllFileFilterUsed(false);
            int seleccion = fileChooser.showOpenDialog(this);
            if (seleccion == APPROVE_OPTION) {
                String pathFile = fileChooser.getSelectedFile().getPath();
                FileReader entrada = new FileReader(pathFile);
                int cont = entrada.read();
                while (cont != -1) {
                    documento += (char) cont;
                    cont = entrada.read();
                }
                this.pathCargado = pathFile;
            }
            this.jTextArea1.setText(documento);
            if (seleccion == APPROVE_OPTION) {
                JOptionPane.showMessageDialog(this, "Archivo cargado correctamente", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (HeadlessException | FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Info", JOptionPane.ERROR_MESSAGE);
            this.pathCargado = "";
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Info", JOptionPane.ERROR_MESSAGE);
            this.pathCargado = "";
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jTextArea1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea1KeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextArea1KeyPressed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (pathCargado.equals("")) {
            JOptionPane.showMessageDialog(this, "No existe un archivo cargado", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            File file = new File(this.pathCargado);
            try {
                if (!file.exists()) {
                    file.delete();
                }
                BufferedWriter wr = new BufferedWriter(new FileWriter(file));
                FileWriter escribirArchivo = new FileWriter(file, true);
                BufferedWriter buffer = new BufferedWriter(escribirArchivo);
                buffer.write(this.jTextArea1.getText());
                buffer.newLine();
                buffer.close();
                wr.close();
                escribirArchivo.close();
            } catch (Exception e) {

            }

        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        //JFileChooser.DIRECTORIES_ONLY
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(0);
            fileChooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("TXT", "txt");
            fileChooser.setFileFilter(filtroImagen);
            fileChooser.setDialogTitle("Guardar archivo");
            int seleccion = fileChooser.showOpenDialog(this);
            if (seleccion == APPROVE_OPTION) {
                String pathFile = fileChooser.getSelectedFile().getPath() + ".txt";
                //System.out.println(pathFile);

                File file = new File(pathFile);
                if (!file.exists()) {
                    file.createNewFile();
                }

                BufferedWriter wr = new BufferedWriter(new FileWriter(file));
                FileWriter escribirArchivo = new FileWriter(file, true);
                BufferedWriter buffer = new BufferedWriter(escribirArchivo);
                buffer.write(this.jTextArea1.getText());
                buffer.newLine();
                buffer.close();
                wr.close();
                escribirArchivo.close();
                this.pathCargado = pathFile;
//                FileWriter fw = new FileWriter(file);
//                BufferedWriter bw = new BufferedWriter(fw);
//                bw.write(this.jTextArea1.getText());
//                bw.close();

            }

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Info", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(UserEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
//        String entrada = this.jTextArea1.getText();
//        StringReader reader = new StringReader(entrada);
//        LexerIndigo lexico = new LexerIndigo(reader);
//        ParserIndigo parser = new ParserIndigo(lexico);
//        
//        try {
//            parser.parse();
//            //mostrar Nmostrar = new mostrar(parserSuma);
//        } catch (Exception ex) {
//            System.out.println("Error irrecuperable");
//            System.out.println("Causa: " + ex.getCause());
//            System.out.println("Causa2: " + ex.toString());
//        }
//        List<ErrorCom> errores = parser.getErroresCom();
//        if (errores.isEmpty()) {
//            
//        }else{
//            JFReporteErrores reportesErrores = new JFReporteErrores(errores);
//            reportesErrores.setVisible(true);
//        }
//        System.out.println("Se termino de validar");
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed

//        String entrada = this.jTextArea1.getText();
//        String respuestas = compilar(entrada);
//        obtenerRespuestas(respuestas);
//                
//        GenStrResp genStrResp = new GenStrResp(this.erroresAcciones,this.respuestasAcciones);
//        String resp2 = genStrResp.getResp();
//        String logUResp =genStrResp.getLogU().replace("\"", "");
//        if (logUResp.isEmpty() == false) {
//            this.usuarioLog = logUResp;
//        }        
//        this.jLabel6.setText(usuarioLog);
//        ReporteRespuestas repResp = new ReporteRespuestas(resp2);
//        repResp.setVisible(true);
        
        String entrada = this.jTextArea1.getText();
        StringReader reader = new StringReader(entrada);
        LexerForms lexico = new LexerForms(reader);
        ParserForms parser = new ParserForms(lexico);
        List<Formulario> formularios = new ArrayList<>();
        try {
            parser.parse();
            formularios = parser.getFormularios();
            System.out.println("");
        } catch (Exception ex) {

        }
        for (Formulario formulario : formularios) {
            List<Campo> campos = formulario.getCampos();
            for (Campo campo : campos) {
                System.out.println("CAMPO: " + campo.getNombreCampo());
                List<String> registros = campo.getRegistros();
                for (String registro : registros) {
                    System.out.println("Registro: " + registro);
                }
            }
        }

        //System.out.println("");
        //JOptionPane.showMessageDialog(this, "Compilacion terminada", "Info", JOptionPane.INFORMATION_MESSAGE);
        //System.out.println("Se termino de validar");
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void obtenerRespuestas(String entrada) {

        StringReader reader = new StringReader(entrada);
        LexerResp lexico = new LexerResp(reader);
        ParserResp parser = new ParserResp(lexico);
        try {
            parser.parse();
            erroresAcciones = parser.getErrores();
            respuestasAcciones = parser.getRespuestas();
            System.out.println("");
        } catch (Exception ex) {
//            System.out.println("Error irrecuperable");
//            System.out.println("Causa: " + ex.getCause());
//            System.out.println("Causa2: " + ex.toString());
        }

    }

    private void setLineaYColumna() {
        this.jLabel3.setText(String.valueOf(lin));
        this.jLabel4.setText(String.valueOf(col));
    }

    private String compilar(String entrada) {
        String respuestas = "";
        StringReader reader = new StringReader(entrada);
        LexerIndigo lexico = new LexerIndigo(reader);
        ParserIndigo parser = new ParserIndigo(lexico);

        try {
            parser.parse();
        } catch (Exception ex) {
            // System.out.println("Causa: " + ex.getCause());
            // System.out.println("Causa2: " + ex.toString());
        }
        List<ErrorCom> errores = parser.getErroresCom();
        List<Solicitud> solicitudes = parser.getSolicitudes();
        //Llamar control semantico
        ControlSemantico controlSemantico = new ControlSemantico(solicitudes, errores, usuarioLog);
        errores = controlSemantico.getErrores();

        if (errores.isEmpty()) {

            solicitudes = controlSemantico.getSolicitudes();
            Acciones acciones = new Acciones(solicitudes, this.dbUsuariosForms);
            String usuarios = acciones.getUsuarios();
            String formularios = acciones.getFormularios();
            this.dbUsuariosForms.setUsuarios(usuarios);
            this.dbUsuariosForms.setFormularios(formularios);
            System.out.println("USUARIOS");
            System.out.println(this.dbUsuariosForms.getUsuarios());
            System.out.println("\nFORMULARIOS");
            System.out.println(this.dbUsuariosForms.getFormularios());

            //Generar Respuestas
            List<ErrorCom> erroresAcciones1 = acciones.getErroresAcciones();
            List<Respuesta> respuestasAcciones1 = acciones.getRespuestasAcciones();
            if (erroresAcciones1.size() > 0) {
                GenStrErrors genStrErrors = new GenStrErrors(erroresAcciones1);
                respuestas = genStrErrors.getErroresStr();
            } else {
                GenRespuestas genResp = new GenRespuestas(respuestasAcciones1);
                respuestas = genResp.getRespuestasStr();
            }
        } else {
            //Generar Respuestas Errores
            GenStrErrors genStrErrors = new GenStrErrors(errores);
            respuestas = genStrErrors.getErroresStr();
            //JFReporteErrores reportesErrores = new JFReporteErrores(errores);
            //reportesErrores.setVisible(true);
        }
        return respuestas;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
