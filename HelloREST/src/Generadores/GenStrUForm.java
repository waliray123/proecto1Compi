/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generadores;

import analizadores.LexerForms;
import analizadores.ParserForms;
import java.io.StringReader;
import java.util.List;
import objetos.Campo;
import objetos.Componente;
import objetos.Formulario;

/**
 *
 * @author user-ubunto
 */
public class GenStrUForm {

    private String dbFormularios;
    private List<Formulario> todosFormularios;
    private String formularios;

    public GenStrUForm(String formularios, String idForm) {
        this.formularios = formularios;
        getTodosFormularios();
        //generarDB();
    }

    private void getTodosFormularios() {
        StringReader reader = new StringReader(this.formularios);
        LexerForms lexico = new LexerForms(reader);
        ParserForms parser = new ParserForms(lexico);

        try {
            parser.parse();
            this.todosFormularios = parser.getFormularios();
            //System.out.println("");
        } catch (Exception ex) {
//            System.out.println("ERROR EN GET USUARIOS DE ACCIONES");
//            System.out.println("Causa: " + ex.getCause());
//            System.out.println("Causa2: " + ex.toString());
        }
    }

    public String generarDB(Formulario formulario) {
        dbFormularios = "db.formularios(\n";
        dbFormularios += "\n{";
        dbFormularios += "\n\"ID_FORMULARIO\" : " + formulario.getIdForm() + " ,";
        dbFormularios += "\n\"TITULO\" : " + formulario.getTitulo() + " ,";
        dbFormularios += "\n\"NOMBRE\" : " + formulario.getNombre() + " ,";
        dbFormularios += "\n\"TEMA\" : " + "\"" + formulario.getTema() + "\"" + " ,";
        dbFormularios += "\n\"USUARIO_CREACION\" : " + "\"" + formulario.getUsuarioCrea() + "\"" + " ,";
        dbFormularios += "\n\"FECHA_CREACION\" : " + "\"" + formulario.getFechaCrea() + "\"";
        List<Componente> componentes = formulario.getComponentes();
        if (componentes.size() > 0) {
            dbFormularios += " ,";
            dbFormularios += "\n\"ESTRUCTURA\":(";
            int contComp = 0;
            for (Componente componente : componentes) {
                if (contComp > 0) {
                    dbFormularios += "\n,";
                }
                dbFormularios += "\n{";
                dbFormularios += "\n\"ID_COMPONENTE\" : " + componente.getIdComp() + " ,";
                dbFormularios += "\n\"NOMBRE_CAMPO\" : " + componente.getNombre() + " ,";
                dbFormularios += "\n\"FORMULARIO\" : " + componente.getIdformulario() + " ,";
                dbFormularios += "\n\"INDICE\" : " + componente.getIndice() + " ,";
                dbFormularios += "\n\"ALINEACION\" : " + "\"" + componente.getAlineacion() + "\"" + " ,";
                dbFormularios += "\n\"CLASE\" : " + "\"" + componente.getClase() + "\"" + " ,";
                dbFormularios += "\n\"TEXTO_VISIBLE\" : " + componente.getTextoVisible();
                if (componente.getRequerido() != null && componente.getRequerido().isEmpty() == false) {
                    dbFormularios += " ,";
                    dbFormularios += "\n\"REQUERIDO\" : " + componente.getRequerido();
                }
                if (componente.getOpciones() != null && componente.getOpciones().isEmpty() == false) {
                    dbFormularios += " ,";
                    dbFormularios += "\n\"OPCIONES\" : " + componente.getOpciones();
                }
                if (componente.getFilas() != null && componente.getFilas().isEmpty() == false) {
                    dbFormularios += " ,";
                    dbFormularios += "\n\"FILAS\" : " + componente.getFilas();
                }
                if (componente.getColumnas() != null && componente.getColumnas().isEmpty() == false) {
                    dbFormularios += " ,";
                    dbFormularios += "\n\"COLUMNAS\" : " + componente.getColumnas();
                }
                if (componente.getUrl() != null && componente.getUrl().isEmpty() == false) {
                    dbFormularios += " ,";
                    dbFormularios += "\n\"URL\" : " + componente.getUrl();
                }
                dbFormularios += "\n}";
                contComp++;
            }
            dbFormularios += "\n)";
        }
        dbFormularios += "\n}";
        dbFormularios += ")";
        return this.dbFormularios;
    }

    public String getDbFormularios() {
        return dbFormularios;
    }

    public void setDbFormularios(String dbFormularios) {
        this.dbFormularios = dbFormularios;
    }

    public void setTodosFormularios(List<Formulario> todosFormularios) {
        this.todosFormularios = todosFormularios;
    }

    public String getFormularios() {
        return formularios;
    }

    public void setFormularios(String formularios) {
        this.formularios = formularios;
    }

    public List<Formulario> getForms() {
        return this.todosFormularios;
    }

}
