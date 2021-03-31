/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generadores;

import java.util.List;
import objetos.ErrorCom;

/**
 *
 * @author user-ubunto
 */
public class GenStrErrors {
    /*
    private String tipo;
    private String desc;
    private String lin;
    private String col;
    private String lex;
    */
    
    private List<ErrorCom> errores;
    private String erroresStr;

    public GenStrErrors(List<ErrorCom> errores) {
        this.errores = errores;
        this.erroresStr = "";
        crearStr();
    }   
    
    public List<ErrorCom> getErrores() {
        return errores;
    }

    public void setErrores(List<ErrorCom> errores) {
        this.errores = errores;
    }

    public String getErroresStr() {
        return erroresStr;
    }

    public void setErroresStr(String erroresStr) {
        this.erroresStr = erroresStr;
    }        
    
    private void crearStr(){
        if (errores.size() > 1) {
            erroresStr += "<!ini_respuestas> \n";
        }
        for (ErrorCom error : errores) {
            erroresStr += "<!ini_respuesta: \"ERROR\">\n";
            erroresStr += "{\n";
            erroresStr += "\"TIPO\" : "+ "\"" +error.getTipo()+ "\"" +" ,\n";
            erroresStr += "\"DESCRIPCION\" : "+ "\""+ error.getDesc()+ "\"" +" ,\n";
            erroresStr += "\"LINEA\" : "+ "\""+ error.getLin()+ "\"" +" ,\n";
            erroresStr += "\"COLUMNA\" : "+ "\""+ error.getCol()+ "\"" +" ,\n";
            erroresStr += "\"LEXEMA\" : "+ "\""+ error.getLex()+ "\"";
            erroresStr += "}\n";
            erroresStr += "<!fin_respuesta>\n";
        }        
        if (errores.size() > 1) {
            erroresStr += "<!fin_respuestas>";
        }
    }
    
}
