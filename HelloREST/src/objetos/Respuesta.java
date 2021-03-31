/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

/**
 *
 * @author user-ubunto
 */
public class Respuesta {
    private String tipo;
    private String desc;
    private String logU;

    public Respuesta(String tipo, String desc) {
        this.tipo = tipo;
        this.desc = desc;
    }   
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLogU() {
        return logU;
    }

    public void setLogU(String user) {
        this.logU = user;
    }
    
    
    
}
