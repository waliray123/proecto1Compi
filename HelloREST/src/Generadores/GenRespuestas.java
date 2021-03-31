/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generadores;

import java.util.List;
import objetos.Respuesta;

/**
 *
 * @author user-ubunto
 */
public class GenRespuestas {
    private List<Respuesta> respuestas;
    private String respuestasStr;

    public GenRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
        this.respuestasStr = "";
        crearStr();
    }
    
    private void crearStr(){
        if (this.respuestas.size() > 1) {
            this.respuestasStr += "<!ini_respuestas> \n";
        }
        
        for (Respuesta respuesta : respuestas) {
            this.respuestasStr += "<!ini_respuesta : \"RESPUESTA_SOLICITUD\" >\n";
            this.respuestasStr += "{\n";
            this.respuestasStr += "\"TIPO\" : " + "\"" +respuesta.getTipo()+ "\"" + " , \n";
            this.respuestasStr += "\"DESCRIPCION\" : "+ "\"" + respuesta.getDesc()+ "\"";
            if (respuesta.getLogU() != null) {
                this.respuestasStr += " , \n";
                this.respuestasStr += "\"LOG_U\" : "+ "\"" + respuesta.getLogU()+ "\"";
            }
            this.respuestasStr += "}\n";
            this.respuestasStr += "<!fin_respuesta>\n";
        }        
        
        if (this.respuestas.size() > 1) {
            this.respuestasStr += "<!fin_respuestas>";
        }
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public String getRespuestasStr() {
        return respuestasStr;
    }

    public void setRespuestasStr(String respuestasStr) {
        this.respuestasStr = respuestasStr;
    }
    
    
    
}
