/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user-ubunto
 */
public class Campo implements Serializable{
    private String nombreCampo;
    private List<String> registros;

    public Campo() {
        registros = new ArrayList<>();
    }
    
    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    public List<String> getRegistros() {
        return registros;
    }

    public void setRegistros(List<String> registros) {
        this.registros = registros;
    }
    
    public void setResgistro(String registro){
        registros.add(registro);
    }
    
}
