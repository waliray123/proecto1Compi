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
public class Componente {
    private String idComp;
    private String nombre;
    private String idformulario;
    private String indice;
    private String clase;            
    private String textoVisible;
    private String alineacion;
    //Atributos Especiales por clase
    private String requerido;
    private String opciones;
    private String filas;
    private String columnas;
    private String url;
    
//
//    /**
//     * Constructor de componente basico
//     * @param idComp
//     * @param nombre
//     * @param idformulario
//     * @param indice
//     * @param clase
//     * @param textoVisible
//     * @param alineacion 
//     */
//    public Componente(String idComp, String nombre, String idformulario, String indice, String clase, String textoVisible, String alineacion) {
//        this.idComp = idComp;
//        this.nombre = nombre;
//        this.idformulario = idformulario;
//        this.indice = indice;
//        this.clase = clase;
//        this.textoVisible = textoVisible;
//        this.alineacion = alineacion;
//    }

    public String getIdComp() {
        return idComp;
    }

    public void setIdComp(String idComp) {
        this.idComp = idComp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdformulario() {
        return idformulario;
    }

    public void setIdformulario(String idformulario) {
        this.idformulario = idformulario;
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getTextoVisible() {
        return textoVisible;
    }

    public void setTextoVisible(String textoVisible) {
        this.textoVisible = textoVisible;
    }

    public String getAlineacion() {
        return alineacion;
    }

    public void setAlineacion(String alineacion) {
        this.alineacion = alineacion;
    }

    public String getRequerido() {
        return requerido;
    }

    public void setRequerido(String requerido) {
        this.requerido = requerido;
    }

    public String getOpciones() {
        return opciones;
    }

    public void setOpciones(String opciones) {
        this.opciones = opciones;
    }

    public String getFilas() {
        return filas;
    }

    public void setFilas(String filas) {
        this.filas = filas;
    }

    public String getColumnas() {
        return columnas;
    }

    public void setColumnas(String columnas) {
        this.columnas = columnas;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
    
}
