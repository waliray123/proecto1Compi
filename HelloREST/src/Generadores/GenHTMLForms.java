/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generadores;

import analizadores.LexerForms;
import analizadores.ParserForms;
import java.io.StringReader;
import java.util.List;
import objetos.Formulario;
import objetos.FormularioHTML;

/**
 *
 * @author user-ubunto
 */
public class GenHTMLForms {
    private List<FormularioHTML> formsHtml;
    private List<Formulario> todosFormularios;
    private Formulario formularioAct;
    private String formulariosStr;
    private String formStr;
    private String url;
    private String idForm;

    public GenHTMLForms(List<FormularioHTML> formsHtml, String formulariosStr) {
        this.formsHtml = formsHtml;
        this.formulariosStr = formulariosStr;
        this.idForm = idForm;
        getTodosFormularios();
        validarFormularios();        
    }
    
    private void validarFormularios(){
        boolean estaGuardado;        
        for (FormularioHTML formularioHTML : formsHtml) {
            estaGuardado = false;
            String idFormHTML = formularioHTML.getFormulario().getIdForm();
            for (Formulario formulario : todosFormularios) {
                if (formulario.getIdForm().equals(idFormHTML)) {
                    estaGuardado = true;
                    break;
                }
            }
            if (estaGuardado == false) {
                this.formsHtml.remove(formularioHTML);
            }
        }
        
    }
    
    public String generarHTMLForm(String idForm){
        boolean estaGuardado = false;
        for (Formulario formulario : todosFormularios) {
            if (formulario.getIdForm().replace("\"", "").equals(idForm)) {
                formularioAct = formulario;
                estaGuardado = true;
                break;
            }
        }
        if (estaGuardado == false) {
            url = "NO EXISTE ESE FORMULARIO";
        }else{
            generarStrForm();
            FormularioHTML formHTML = new FormularioHTML();
            formHTML.setFormHTML(formStr);
            formHTML.setFormulario(this.formularioAct);
            this.formsHtml.add(formHTML);
            url = "http://localhost:8080/HelloREST/Servlet1?idForm="+idForm.replace("\"", "");
        }
        return url;
    }
    
    private void generarStrForm(){
        this.formStr = "<!DOCTYPE html> \n";
        this.formStr += "<html> \n";
        this.formStr += "<head> \n";
        this.formStr += "<title>" + this.formularioAct.getTitulo().replace("\"", "")+"</title> \n";        
        this.formStr += "<body> \n";
        this.formStr += "<h1>" + this.formularioAct.getNombre().replace("\"", "")+"</h1> \n";        
        this.formStr += "</body> \n";
        this.formStr += "</head> \n";
        this.formStr += "</html> \n";
    }

    public List<FormularioHTML> getFormsHtml() {
        return formsHtml;
    }

    public void setFormsHtml(List<FormularioHTML> formsHtml) {
        this.formsHtml = formsHtml;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    private void getTodosFormularios() {
        StringReader reader = new StringReader(this.formulariosStr);
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
