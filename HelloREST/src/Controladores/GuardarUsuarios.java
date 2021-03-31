/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.util.List;
import objetos.Usuario;

/**
 * Clase que funciona para crear un string que se usara para guardar los usuarios
 * 
 * @author user-ubunto
 */
public class GuardarUsuarios {
    private List<Usuario> todosUsuarios;
    private String dbUsuarios;

    public GuardarUsuarios(List<Usuario> todosUsuarios) {
        this.todosUsuarios = todosUsuarios;
        generarDB();
    }
    
    
    private void generarDB(){
        dbUsuarios = "db.usuarios(\n";
        for (Usuario usuario : todosUsuarios) {
            dbUsuarios += "\n{";
            dbUsuarios += "\n \"USUARIO\" : " + usuario.getUsuario() + " ,";
            dbUsuarios += "\n \"PASSWORD\" : " + usuario.getPassword() + " ,";
            dbUsuarios += "\n \"FECHA_CREACION\" : " + "\""+usuario.getFechaCrea()+"\"";
            dbUsuarios += "\n}";
        }
        dbUsuarios += "\n)";
    }

    public String getDbUsuarios() {
        return dbUsuarios;
    }

    public void setDbUsuarios(String dbUsuarios) {
        this.dbUsuarios = dbUsuarios;
    }
    
    
}
