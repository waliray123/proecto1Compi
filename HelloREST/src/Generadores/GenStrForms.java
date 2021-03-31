/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generadores;

import analizadores.LexerForms;
import analizadores.ParserForms;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import objetos.Formulario;
import objetos.UsuariosFormularios;

/**
 *
 * @author user-ubunto
 */
public class GenStrForms {
    
    private String formsStr;
    private List<Formulario> todosFormularios;    
    private String formularios;

    public GenStrForms(UsuariosFormularios userForms) {        
        this.formsStr = "";
        formularios = userForms.getFormularios();
        getTodosFormularios();
    }
    
    public void getFormulariosPorIdUsuario(String idUsuario){
        List<Formulario> formsUser = new ArrayList<>();
        for (Formulario formulario : todosFormularios) {
            if (formulario.getUsuarioCrea() != null && formulario.getUsuarioCrea().replace("\"", "").equals(idUsuario)) {
                formsUser.add(formulario);
            }
        }
        
        if (formsUser.isEmpty()) {
            this.formsStr = "EL USUARIO NO TIENE FORMULARIOS";            
        }else{
            int cont = 1;
            for (Formulario formulario : formsUser) {
                this.formsStr += "FORMULARIO DE USUARIO: " + idUsuario + "\n";
                this.formsStr += "________________________________________\n";
                this.formsStr += "Formulario No. " + cont + "\n";
                this.formsStr += "     ID     : " + formulario.getIdForm().replace("\"", "")+ "\n";
                this.formsStr += "     TITULO : " + formulario.getTitulo().replace("\"", "")+ "\n";
                this.formsStr += "     NOMBRE : " + formulario.getNombre().replace("\"", "")+ "\n";
                this.formsStr += "     TEMA   : " + formulario.getTema().replace("\"", "")+ "\n";
                this.formsStr += "     FECHA  : " + formulario.getFechaCrea().replace("\"", "")+ "\n";
                cont++;
            }
        }                            
    }

    public String getFormsStr() {
        return formsStr;
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
    
    
    
}
