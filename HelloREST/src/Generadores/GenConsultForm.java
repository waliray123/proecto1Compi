/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generadores;

import java.util.List;
import objetos.Campo;
import objetos.Formulario;

/**
 *
 * @author user-ubunto
 */
public class GenConsultForm {

    private String formsStr;
    private Formulario formulario;
    private List<Campo> campos;

    public GenConsultForm(Formulario formulario,List<Campo> campos) {
        formsStr = "";
        this.formulario = formulario;
        this.campos = campos;
        generarSTR();
    }

    private void generarSTR() {
        this.formsStr += "\n________________________________________\n";
        this.formsStr += "     ID     : " + formulario.getIdForm().replace("\"", "") + "\n";
        this.formsStr += "     TITULO : " + formulario.getTitulo().replace("\"", "") + "\n";
        this.formsStr += "     NOMBRE : " + formulario.getNombre().replace("\"", "") + "\n";
        this.formsStr += "     TEMA   : " + formulario.getTema().replace("\"", "") + "\n";
        this.formsStr += "     FECHA  : " + formulario.getFechaCrea().replace("\"", "") + "\n";
        this.formsStr += "     USUARIO: " + formulario.getUsuarioCrea().replace("\"", "") + "\n";
        this.formsStr += "     DATOS RECOPILADOS:\n";
        for (Campo campo : campos) {
            this.formsStr += "     NOMBRE CAMPO: "+campo.getNombreCampo().replace("\"", "")+"\n";
            List<String> registros = campo.getRegistros();
            int cont = 1;
            for (String registro : registros) {
                this.formsStr += "          REGISTRO: "+registro.replace("\"", "")+"\n";
            }
        }
    }

    public String getFormsStr() {
        return formsStr;
    }

}
