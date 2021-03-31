/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.io.Serializable;

/**
 *
 * @author user-ubunto
 */
public class UsuariosFormularios implements Serializable{
    
    private String usuarios;
    private String formularios;    

    public UsuariosFormularios() {
        this.usuarios = "db.usuarios()";
        this.formularios = "db.formularios()";
    }

    public String getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(String usuarios) {
        this.usuarios = usuarios;
    }

    public String getFormularios() {
        return formularios;
    }

    public void setFormularios(String formularios) {
        this.formularios = formularios;
    }
    
    
    
}
