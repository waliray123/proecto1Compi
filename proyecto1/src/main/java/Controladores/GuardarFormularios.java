/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.util.List;
import objetos.Componente;
import objetos.Formulario;

/**
 *
 * @author user-ubunto
 */
public class GuardarFormularios {
    private List<Formulario> todosFormularios;
    private String dbFormularios;

    public GuardarFormularios(List<Formulario> todosFormularios) {
        this.todosFormularios = todosFormularios;        
        this.dbFormularios = "";
        generarDB();
    }
    
    private void generarDB(){
        dbFormularios = "db.formularios(\n";
        int cont = 0;
        for (Formulario formulario : todosFormularios) {
            if (cont != 0) {
                dbFormularios += "\n,";
            }
            dbFormularios += "\n{";
            dbFormularios += "\n\"ID_FORMULARIO\" : " + formulario.getIdForm()+ " ,";
            dbFormularios += "\n\"TITULO\" : " + formulario.getTitulo()+ " ,";
            dbFormularios += "\n\"NOMBRE\" : " + formulario.getNombre()+ " ,";
            dbFormularios += "\n\"TEMA\" : " + formulario.getTema()+ " ,";
            dbFormularios += "\n\"USUARIO_CREACION\" : " + formulario.getUsuarioCrea()+ " ,";
            dbFormularios += "\n\"FECHA_CREACION\" : " + formulario.getFechaCrea();
            List<Componente> componentes = formulario.getComponentes();
            if (componentes.size() > 0) {
                dbFormularios +=" ,";
                dbFormularios += "\n\"ESTRUCTURA\":(";
                for (Componente componente : componentes) {
                    dbFormularios += "\n{";
                    dbFormularios += "\n\"ID_COMPONENTE\" : " + componente.getIdComp() + " ," ;
                    dbFormularios += "\n\"NOMBRE_CAMPO\" : " + componente.getNombre()+ " ,";
                    dbFormularios += "\n\"FORMULARIO\" : " + componente.getIdformulario()+ " ,";
                    dbFormularios += "\n\"CLASE\" : " + componente.getClase()+ " ,";
                    dbFormularios += "\n\"INDICE\" : " + componente.getIndice()+ " ,";
                    dbFormularios += "\n\"ALINEACION\" : " + componente.getAlineacion()+ " ,";
                    dbFormularios += "\n\"TEXTO_VISIBLE\" : " + componente.getTextoVisible();
                    if (componente.getRequerido() != null && componente.getRequerido().isEmpty() == false) {
                        dbFormularios +=" ,";
                        dbFormularios += "\n\"REQUERIDO\" : " + componente.getRequerido();
                    }
                    if (componente.getOpciones() != null && componente.getOpciones().isEmpty() == false) {
                        dbFormularios +=" ,";
                        dbFormularios += "\n\"OPCIONES\" : " + componente.getOpciones();
                    }
                    if (componente.getFilas() != null && componente.getFilas().isEmpty() == false) {
                        dbFormularios +=" ,";
                        dbFormularios += "\n\"FILAS\" : " + componente.getFilas();
                    }
                    if (componente.getColumnas() != null && componente.getColumnas().isEmpty() == false) {
                        dbFormularios +=" ,";
                        dbFormularios += "\n\"COLUMNAS\" : " + componente.getColumnas();
                    }
                    if (componente.getUrl() != null && componente.getUrl().isEmpty() == false) {
                        dbFormularios +=" ,";
                        dbFormularios += "\n\"URL\" : " + componente.getUrl();
                    }
                    dbFormularios += "\n}";
                }
                dbFormularios += "\n)";
            }
            dbFormularios += "\n}";
            cont++;
        }
        dbFormularios += ")";
    }

    public List<Formulario> getTodosFormularios() {
        return todosFormularios;
    }

    public void setTodosFormularios(List<Formulario> todosFormularios) {
        this.todosFormularios = todosFormularios;
    }

    public String getDbFormularios() {
        return dbFormularios;
    }

    public void setDbFormularios(String dbFormularios) {
        this.dbFormularios = dbFormularios;
    }
    
    
}
