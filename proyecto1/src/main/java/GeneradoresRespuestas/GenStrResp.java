/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneradoresRespuestas;

import java.util.List;
import objetos.ErrorCom;
import objetos.Respuesta;

/**
 *
 * @author user-ubunto
 */
public class GenStrResp {      
    private String resp;
    private String logU;
    private List<ErrorCom> erroresAcciones;
    private List<Respuesta> respuestasAcciones;

    public GenStrResp(List<ErrorCom> erroresAcciones, List<Respuesta> respuestasAcciones) {
        this.erroresAcciones = erroresAcciones;
        this.respuestasAcciones = respuestasAcciones;
        this.resp = "";
        this.logU = "";
        generarStr();
    }
    
    private void generarStr(){
        int cont = 1;
        for (ErrorCom errore : erroresAcciones) {
            this.resp += "ERROR No." + cont + "\n";
            this.resp += "  TIPO : " + errore.getTipo() + "\n";
            this.resp += "  DESCRIPCION : " + errore.getDesc() + "\n";
            if (errore.getLin() != null && errore.getLin().isEmpty() == false && errore.getLin().replace("\"", "").equals("null") == false) {
                this.resp += "  LINEA : " + errore.getLin() + "\n";
            }
            if (errore.getCol() != null && errore.getCol().isEmpty() == false && errore.getCol().replace("\"", "").equals("null") == false) {
                this.resp += "  COLUMNA : " + errore.getCol() + "\n";
            }
            if (errore.getLex() != null && errore.getLex().isEmpty() == false && errore.getLex().replace("\"", "").equals("null") == false) {
                this.resp += "  LEXEMA : " + errore.getLex() + "\n";
            }
            cont++;
        }
        this.resp += "\n";
        cont = 1;
        for (Respuesta respuesta : respuestasAcciones) {
            this.resp += "SOLICITUD : "+ cont + "\n";
            this.resp += "  TIPO : "+ respuesta.getTipo() + "\n";
            this.resp += "  DESCRIPCION : "+ respuesta.getDesc() + "\n";
            if (respuesta.getLogU() != null && respuesta.getLogU().isEmpty() == false) {
                this.resp += "  USUARIO LOGUEADO : " + respuesta.getLogU() + "\n";
                this.logU = respuesta.getLogU();
            }            
            cont++;
        }
    }

    public List<ErrorCom> getErroresAcciones() {
        return erroresAcciones;
    }

    public void setErroresAcciones(List<ErrorCom> erroresAcciones) {
        this.erroresAcciones = erroresAcciones;
    }

    public List<Respuesta> getRespuestasAcciones() {
        return respuestasAcciones;
    }

    public void setRespuestasAcciones(List<Respuesta> respuestasAcciones) {
        this.respuestasAcciones = respuestasAcciones;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getLogU() {
        return logU;
    }

    public void setLogU(String logU) {
        this.logU = logU;
    }
    
    
    
}
