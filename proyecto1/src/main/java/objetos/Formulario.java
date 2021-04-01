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
public class Formulario implements Serializable{
    private String idForm;
    private String titulo;
    private String nombre;
    private String tema;
    private String usuarioCrea;
    private String fechaCrea;
    private List<Componente> componentes;
    private List<Campo> campos;

//    public Formulario(String idForm, String titulo, String nombre, String tema, String usuarioCrea, String fechaCrea) {
//        this.idForm = idForm;
//        this.titulo = titulo;
//        this.nombre = nombre;
//        this.tema = tema;
//        this.usuarioCrea = usuarioCrea;
//        this.fechaCrea = fechaCrea;
//        this.componentes = new ArrayList<>();
//    }

    public Formulario() {
        this.componentes = new ArrayList<>();
        this.campos = new ArrayList<>();
    }

    public List<Campo> getCampos() {
        return campos;
    }

    public void setCampos(List<Campo> campos) {
        this.campos = campos;
    }        

    public String getIdForm() {
        return idForm;
    }

    public void setIdForm(String idForm) {
        this.idForm = idForm;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(String usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public String getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(String fechaCrea) {
        this.fechaCrea = fechaCrea;
    }

    public List<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<Componente> componentes) {
        this.componentes = componentes;
    }
    
    public void setNuevoComponente(Componente componente){
        this.componentes.add(componente);
    }
    
    public void elimComponente(int index){
        this.componentes.remove(index);
    }
    
    public void setNuevoCampo(Campo campo){
        this.campos.add(campo);
    }
}
