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
public class FormularioHTML implements Serializable{
    private Formulario formulario;
    private String formHTML;

    public FormularioHTML() {
        formHTML = "";
    }
  
    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public String getFormHTML() {
        return formHTML;
    }

    public void setFormHTML(String formHTML) {
        this.formHTML = formHTML;
    }
    
    
    
}
