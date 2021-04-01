/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user-ubunto
 */
public class Consulta {
    private String idFormulario;
    private List<String> parametrosMostrar;
    private List<ParamComp> parametrosComparacion;
    private List<String> conectoresRelacional;

    public Consulta() {
        this.parametrosMostrar = new ArrayList<>();
        this.parametrosComparacion = new ArrayList<>();
        this.conectoresRelacional = new ArrayList<>();
    }
    
    

    public String getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(String idFormulario) {
        this.idFormulario = idFormulario;
    }

    public List<String> getParametrosMostrar() {
        return parametrosMostrar;
    }

    public void setParametrosMostrar(List<String> parametrosMostrar) {
        this.parametrosMostrar = parametrosMostrar;
    }

    public List<ParamComp> getParametrosComparacion() {
        return parametrosComparacion;
    }

    public void setParametrosComparacion(List<ParamComp> parametrosComparacion) {
        this.parametrosComparacion = parametrosComparacion;
    }

    public List<String> getConectoresRelacional() {
        return conectoresRelacional;
    }

    public void setConectoresRelacional(List<String> conectoresRelacional) {
        this.conectoresRelacional = conectoresRelacional;
    }
    
    public void setNuevoParametroMostrar(String paramM){
        this.parametrosMostrar.add(paramM);
    }
    
    public void setNuevoParametroComp(ParamComp paramComp){
        this.parametrosComparacion.add(paramComp);
    }
    
    public void setNuevoConectorRelacional(String conect){
        this.conectoresRelacional.add(conect);
    }
}
