/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import analizadores.LexerForms;
import analizadores.ParserForms;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import objetos.Campo;
import objetos.Componente;
import objetos.Formulario;
import objetos.FormularioHTML;

/**
 *
 * @author user-ubunto
 */
@WebServlet(name = "Servlet1", urlPatterns = {"/Servlet1"})
public class Servlet1 extends HttpServlet {

    private String todosFormularios;
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/HelloREST/rest";
    private String paramIdForm;
    private String htmlStr;
    private List<Formulario> formularios;
    private Formulario formularioAct;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(htmlStr);
        }
    }

    private String getFormulariosServer(String db) {
        WebTarget resource = webTarget;
        if (db != null) {
            resource = resource.queryParam("db", db);
        }
        resource = resource.path("dbForms");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("bonjour");
        todosFormularios = " ";
        this.paramIdForm = request.getParameter("idForm");
        todosFormularios = getFormulariosServer(paramIdForm);
        obtenerFormularios();
        obtenerHTML(paramIdForm);        
        System.out.println(request.getParameter("utiles"));
        String utiles = request.getParameter("utiles");
        String cliente = request.getParameter("Cliente");
        System.out.println("");
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //System.out.println(request.getParameter("utiles"));
        //guardarDatos(request);
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void obtenerFormularios() {
        StringReader reader = new StringReader(this.todosFormularios);
        LexerForms lexico = new LexerForms(reader);
        ParserForms parser = new ParserForms(lexico);

        try {
            parser.parse();
            this.formularios = parser.getFormularios();
            //System.out.println("");
        } catch (Exception ex) {

        }
    }

    private void obtenerHTML(String idForm) {
        //Sacar el formulario a utilizar
        for (Formulario formulario : formularios) {
            if (formulario.getIdForm().replace("\"", "").equals(idForm)) {
                this.formularioAct = formulario;
            }
        }
        if (formularioAct != null) {
            String tema = this.formularioAct.getTema().replace("\"", "");
            String css = "";
            if (tema.equals("DARK")) {
                css = "<link rel=\"stylesheet\"  href=\"/HelloREST/css/bootstrapDark.css\">";
            } else if (tema.equals("BLUE")) {
                css = "<link rel=\"stylesheet\"  href=\"/HelloREST/css/bootstrapDark.css\">";
            } else if (tema.equals("WHITE")) {
                css = "<link rel=\"stylesheet\"  href=\"/HelloREST/css/bootstrap.css\">";
            }

            this.htmlStr = "<!DOCTYPE html> \n";
            this.htmlStr += "<html> \n";
            this.htmlStr += "<head> \n";
            this.htmlStr += css + "\n";
            this.htmlStr += "<title>" + this.formularioAct.getNombre().replace("\"", "") + "</title> \n";
            this.htmlStr += "</center></head> \n";
            this.htmlStr += "<body> \n";
            this.htmlStr += "<center><h1>" + this.formularioAct.getTitulo().replace("\"", "") + "</h1> </center>\n";
            this.htmlStr += "<form> \n";
            //Insertar Componentes
            insertarComponentes();
            this.htmlStr += "</form> \n";
            this.htmlStr += "</body> \n";
            this.htmlStr += "</html> \n";
        } else {
            this.htmlStr = "NO EXISTE EL FORMULARIO";
        }
    }

    private void insertarComponentes() {
        List<Componente> componentes = formularioAct.getComponentes();
        componentes = ordenarComponentes(componentes);
        String requerido = "";
        String opcionesStr ="";
        String[] opciones = null;
        String idComp = "";
        String nombre = "";
        for (Componente componente : componentes) {
            String alinea = componente.getAlineacion().replace("\"", "");
            if (alinea.equals("CENTRO")) {
                alinea = "center";
            } else if (alinea.equals("IZQUIERDA")) {
                alinea = "left";
            } else if (alinea.equals("DERECHA")) {
                alinea = "right";
            } else if (alinea.equals("JUSTIFICAR")) {
                alinea = "justify";
            }
            String clase = componente.getClase().replace("\"", "");
            switch (clase) {
                case "CAMPO_TEXT":
                    requerido = componente.getRequerido();
                    if (requerido != null) {
                        requerido = requerido.replace("\"", "");
                        if (requerido.equalsIgnoreCase("SI")) {
                            requerido = "required";
                        } else {
                            requerido = "";
                        }
                    }else{
                        requerido = "";
                    }
                    this.htmlStr += "<div align = \"" + alinea + "\" class=\"mb-3\"> \n";
                    this.htmlStr += "<label for=\"male\" class=\"form-label\">" + componente.getTextoVisible().replace("\"", "") + "</label>\n";
                    this.htmlStr += "<input type=\"text\" id=\"" + componente.getIdComp().replace("\"", "")
                            + "\" name=\"" + componente.getNombre().replace("\"", "") + "\"" + requerido + "></input>";
                    this.htmlStr += "</div>\n";
                    break;
                case "AREA_TEXT":
                    requerido = componente.getRequerido();
                    if (requerido != null) {
                        requerido = requerido.replace("\"", "");
                        if (requerido.equalsIgnoreCase("SI")) {
                            requerido = "required";
                        } else {
                            requerido = "";
                        }
                    }else{
                        requerido = "";
                    }
                    String filas = componente.getFilas();
                    String columnas = componente.getColumnas();
                    if (filas == null) {
                        filas = "";
                    } else {
                        filas = filas.replace("\"", "");
                    }
                    if (columnas == null) {
                        columnas = "";
                    } else {
                        columnas = columnas.replace("\"", "");
                    }
                    this.htmlStr += "<div align = \"" + alinea + "\" class=\"mb-3\"> \n";
                    this.htmlStr += "<label for=\"male\" class=\"form-label\">" + componente.getTextoVisible().replace("\"", "") + "</label>\n";
                    this.htmlStr += "<textarea id=\"" + componente.getIdComp().replace("\"", "") + "\""
                            + "rows = \"" + filas + "\"" + "cols= \"" + columnas + "\""
                            + " name=\"" + componente.getNombre().replace("\"", "") + "\"" + requerido + "></textarea>";
                    this.htmlStr += "</div>\n";
                    break;
                case "BOTON":
                    this.htmlStr += "<div align = \"" + alinea + "\" class=\"mb-3\"> \n";
                    this.htmlStr += "<input type=\"submit\" value=\"" + componente.getTextoVisible().replace("\"", "") + "\"/> \n";
                    this.htmlStr += "</div>\n";
                    break;
                case "IMAGEN":
                    this.htmlStr += "<div align = \"" + alinea + "\" class=\"mb-3\"> \n";
                    this.htmlStr += "<label for=\"male\" class=\"form-label\">" + componente.getTextoVisible().replace("\"", "") + "</label>\n";
                    this.htmlStr += "<input type=\"image\" id=\"" + componente.getIdComp().replace("\"", "")
                            + "\" src=\"" + componente.getUrl().replace("\"", "") + "\" />";
                    this.htmlStr += "</div>\n";
                    break;
                case "FICHERO":
                    requerido = componente.getRequerido();
                    if (requerido != null) {
                        requerido = requerido.replace("\"", "");
                        if (requerido.equalsIgnoreCase("SI")) {
                            requerido = "required";
                        } else {
                            requerido = "";
                        }
                    }else{
                        requerido = "";
                    }
                    this.htmlStr += "<div align = \"" + alinea + "\" class=\"mb-3\"> \n";
                    this.htmlStr += "<label for=\"male\" class=\"form-label\">" + componente.getTextoVisible().replace("\"", "") + "</label>\n";
                    this.htmlStr += "<input type=\"file\" id=\"" + componente.getIdComp().replace("\"", "")
                            + "\" value=\"valor\" name=\"" + componente.getNombre().replace("\"", "") + "\""+ requerido+"/> \n";
                    this.htmlStr += "</div>\n";
                    break;
                case "RADIO":
                    requerido = componente.getRequerido();
                    if (requerido != null) {
                        requerido = requerido.replace("\"", "");
                        if (requerido.equalsIgnoreCase("SI")) {
                            requerido = "required";
                        } else {
                            requerido = "";
                        }
                    }else{
                        requerido = "";
                    }
                    opcionesStr = componente.getOpciones().replace("\"", "");
                    opciones = obtenerOpciones(opcionesStr);
                    idComp = componente.getIdComp().replace("\"", "");
                    nombre = componente.getNombre().replace("\"", "");
                    this.htmlStr += "<div align = \"" + alinea + "\" class=\"mb-3\"> \n";
                    this.htmlStr += "<label for=\"male\" class=\"form-label\">" + componente.getTextoVisible().replace("\"", "") + "</label>\n";
                    for (int i = 0; i < opciones.length; i++) {
                        String opcion = opciones[i];
                        this.htmlStr += "<input type=\"radio\" id=\"" + idComp + "\" value=\"" + opcion + "\" name=\"" + nombre + "\""+ requerido+"/>" + opcion + "\n";
                    }
                    this.htmlStr += "</div>\n";
                    break;
                case "CHECK_BOX":
                    requerido = componente.getRequerido();
                    if (requerido != null) {
                        requerido = requerido.replace("\"", "");
                        if (requerido.equalsIgnoreCase("SI")) {
                            requerido = "required";
                        } else {
                            requerido = "";
                        }
                    }else{
                        requerido = "";
                    }
                    opcionesStr = componente.getOpciones().replace("\"", "");
                    opciones = obtenerOpciones(opcionesStr);
                    idComp = componente.getIdComp().replace("\"", "");
                    nombre = componente.getNombre().replace("\"", "");
                    this.htmlStr += "<div align = \"" + alinea + "\" class=\"mb-3\"> \n";
                    this.htmlStr += "<label for=\"male\" class=\"form-label\">" + componente.getTextoVisible().replace("\"", "") + "</label>\n";
                    for (int i = 0; i < opciones.length; i++) {
                        String opcion = opciones[i];
                        this.htmlStr += "<input type=\"checkbox\" id=\"" + idComp + "\" value=\"" + opcion + "\" name=\"" + nombre + "\"/>" + opcion + "\n";
                    }
                    this.htmlStr += "</div>\n";
                    break;
                case "COMBO":
                    requerido = componente.getRequerido();
                    if (requerido != null) {
                        requerido = requerido.replace("\"", "");
                        if (requerido.equalsIgnoreCase("SI")) {
                            requerido = "required";
                        } else {
                            requerido = "";
                        }
                    }else{
                        requerido = "";
                    }
                    opcionesStr = componente.getOpciones().replace("\"", "");
                    opciones = obtenerOpciones(opcionesStr);
                    idComp = componente.getIdComp().replace("\"", "");
                    nombre = componente.getNombre().replace("\"", "");
                    this.htmlStr += "<div align = \"" + alinea + "\" class=\"mb-3\"> \n";
                    this.htmlStr += "<label for=\"male\" class=\"form-label\">" + componente.getTextoVisible().replace("\"", "") + "</label>\n";
                    this.htmlStr += "<select id=\""+idComp+"\" name=\""+nombre+"\""+requerido+" >";
                    for (int i = 0; i < opciones.length; i++) {
                        String opcion = opciones[i];
                        this.htmlStr += "<option>"+opcion+"</option>\n";
                    }
                    this.htmlStr += "</select>";
                    this.htmlStr += "</div>\n";
                    break;
                default:
                    break;
            }
        }

    }

    private List<Componente> ordenarComponentes(List<Componente> componentes) {

        for (int i = 0; i < componentes.size() - 1; i++) {
            for (int j = 0; j < componentes.size() - i - 1; j++) {
                int primerInt = Integer.parseInt(componentes.get(j).getIndice().replace("\"", ""));
                int sigInt = Integer.parseInt(componentes.get(j + 1).getIndice().replace("\"", ""));
                if (primerInt > sigInt) {
                    Componente temp = componentes.get(j);
                    Componente temp2 = componentes.get(j + 1);
                    componentes.set(j, temp2);
                    componentes.set(j + 1, temp);
                }
            }

        }

        return componentes;
    }

    private String[] obtenerOpciones(String opcionesStr) {
        String[] opciones = null;
        opciones = opcionesStr.split("[|]");
        return opciones;
    }
    
    private void guardarDatos(HttpServletRequest request){
        List<Componente> componentes = formularioAct.getComponentes();
        List<Campo> campos = new ArrayList<>();
        for (Componente componente : componentes) {
            String nombreCamp = componente.getNombre().replace("\"", "");
            String registro = request.getParameter(nombreCamp);
        }
    
    }

}
