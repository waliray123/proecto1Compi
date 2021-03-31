package net.codejava;

import Controladores.Acciones;
import Controladores.GuardarFormularios;
import Generadores.GenHTMLForms;
import Generadores.GenRespuestas;
import Generadores.GenStrErrors;
import Generadores.GenStrForms;
import Generadores.GenStrUForm;
import analizadores.LexerForms;
import analizadores.LexerIndigo;
import analizadores.ParserForms;
import analizadores.ParserIndigo;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import objetos.ControlSemantico;
import objetos.ErrorCom;
import objetos.Formulario;
import objetos.FormularioHTML;
import objetos.GuardarDatos;
import objetos.Respuesta;
import objetos.Solicitud;
import objetos.UsuariosFormularios;

@Path("/bonjour")
public class HelloResource {

    private String pruebaDB = "<html><title>Hello</title><body><h1>PRUEBA AB BIENVENIDO</h1><body></html>";
    private String dbForm;
    private UsuariosFormularios userformsSB = new UsuariosFormularios();
    private GuardarDatos guardarDB = new GuardarDatos();

//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public String direBonjour() {
//        return "Bonjour, tout le monde!";
//    }
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String sayJsonHello() {
//        //return "{\"name\":\"greeting\", \"message\":\"BIENVENIDO AL SERVIDOR!\"}";
//        return pruebaDB;
//    }
//    @GET
//    @Produces(MediaType.TEXT_HTML)
//    public String sayHTMLHello() {
//        return pruebaDB;
//    }
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/prueba")
    public String obtenerDB(@QueryParam("db") String id) {
        //return "El dbForm es: " + dbForm;

        return this.guardarDB.getAllUsers();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/dbForms")
    public String obtenerDBForms(@QueryParam("db") String id) {
        //return "El dbForm es: " + dbForm;        
        return this.guardarDB.getAllForms();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/forms")
    public String obtenerFormulariosPorIdUsuario(@QueryParam("id") String idUser) {
        //return "El dbForm es: " + dbForm; 
        UsuariosFormularios userforms = this.guardarDB.getUsuariosFormularios();
        GenStrForms genstrForms = new GenStrForms(userforms);
        genstrForms.getFormulariosPorIdUsuario(idUser);
        return genstrForms.getFormsStr();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/createForm")
    public String crearFormulario(@QueryParam("id") String id) {
        return crearFormularioHTML(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getFormsHTML")
    public List<FormularioHTML> getFormulariosHTML(@QueryParam("id") String id) {
        List<FormularioHTML> formsHtml = this.guardarDB.getFormulariosHTML();
        return formsHtml;
    }

    @POST
    @Path("/getForm")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String getFormPorId(@FormParam("idF") String id) {
        String formularioR = obtenerFormPorId(id);
        return formularioR;
    }

    @POST
    @Path("/cargarForm")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String cargarForm(@FormParam("strForm") String strForm) {        
        String respuesta = cargarNuevoForm(strForm);
        return respuesta;
    }

    @POST
    @Path("/guardarForms")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String guardarFormularios(@FormParam("strF") String strForms, @FormParam("strU") String strUsers) {
        this.guardarDB.guardarUsuariosyFormularios(strUsers, strForms);
        return "";
    }

    @POST
    @Path("/prueba")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String addPerson(@FormParam("entr") String entrada, @FormParam("userLog") String usuarioLog) throws IOException {
        //dbForm = prueba;
        //userformsSB.setFormularios("db.usuarios("+prueba+")");
        String respuestas = compilar(entrada, usuarioLog);
        return respuestas;
    }

    private String crearFormularioHTML(String idForm) {
        String url = "";
        List<FormularioHTML> formsHtml = this.guardarDB.getFormulariosHTML();
        String todosFormularios = this.guardarDB.getAllForms();
        GenHTMLForms genHTMLForm = new GenHTMLForms(formsHtml, todosFormularios);
        genHTMLForm.generarHTMLForm(idForm);
        url = genHTMLForm.getUrl();
        return url;
    }

    private String compilar(String entrada, String usuarioLog) {
        String respuestas = "";
        StringReader reader = new StringReader(entrada);
        LexerIndigo lexico = new LexerIndigo(reader);
        ParserIndigo parser = new ParserIndigo(lexico);

        try {
            parser.parse();
        } catch (Exception ex) {
            // System.out.println("Causa: " + ex.getCause());
            // System.out.println("Causa2: " + ex.toString());
        }
        List<ErrorCom> errores = parser.getErroresCom();
        List<Solicitud> solicitudes = parser.getSolicitudes();
        //Llamar control semantico
        ControlSemantico controlSemantico = new ControlSemantico(solicitudes, errores, usuarioLog);
        errores = controlSemantico.getErrores();

        if (errores.isEmpty()) {

            solicitudes = controlSemantico.getSolicitudes();
            Acciones acciones = new Acciones(solicitudes, this.guardarDB.getUsuariosFormularios());
            String usuarios = acciones.getUsuarios();
            String formularios = acciones.getFormularios();
            this.guardarDB.guardarUsuariosyFormularios(usuarios, formularios);

            //Generar Respuestas
            List<ErrorCom> erroresAcciones1 = acciones.getErroresAcciones();
            List<Respuesta> respuestasAcciones1 = acciones.getRespuestasAcciones();
            if (erroresAcciones1.size() > 0) {
                GenStrErrors genStrErrors = new GenStrErrors(erroresAcciones1);
                respuestas = genStrErrors.getErroresStr();
            } else {
                GenRespuestas genResp = new GenRespuestas(respuestasAcciones1);
                respuestas = genResp.getRespuestasStr();
            }
        } else {
            //Generar Respuestas Errores
            GenStrErrors genStrErrors = new GenStrErrors(errores);
            respuestas = genStrErrors.getErroresStr();
            //JFReporteErrores reportesErrores = new JFReporteErrores(errores);
            //reportesErrores.setVisible(true);
        }
        return respuestas;
    }

    private String obtenerFormPorId(String id) {
        String form = "";
        String todosFormularios = this.guardarDB.getAllForms();
        GenStrUForm generarUForm = new GenStrUForm(todosFormularios, id);
        List<Formulario> formularios = generarUForm.getForms();
        Formulario formGen = null;
        for (Formulario formulario : formularios) {
            if (formulario.getIdForm().replace("\"", "").equals(id)) {
                formGen = formulario;
            }
        }
        if (formGen == null) {
            form = "FORMNE";
        } else {
            form = generarUForm.generarDB(formGen);
        }
        return form;
    }

    private String cargarNuevoForm(String strForm) {
        String respuesta = "";
        StringReader reader = new StringReader(strForm);
        LexerForms lexico = new LexerForms(reader);
        ParserForms parser = new ParserForms(lexico);
        List<Formulario> formularios = null;
        try {
            parser.parse();
            formularios = parser.getFormularios();
            //System.out.println("");
        } catch (Exception ex) {
//            System.out.println("ERROR EN GET USUARIOS DE ACCIONES");
//            System.out.println("Causa: " + ex.getCause());
//            System.out.println("Causa2: " + ex.toString());
        }
        if (formularios.size() == 0) {
            respuesta = "No se pudo guardar el formulario";
        }else{
            String usuarios = this.guardarDB.getAllUsers();
            String formulariosStr = this.guardarDB.getAllForms();
            List<Formulario> formulariosIngr = getTodosFormularios(formulariosStr);
            for (Formulario formulario : formularios) {
                formulariosIngr.add(formulario);
            }
            GuardarFormularios guarF = new GuardarFormularios(formulariosIngr);
            formulariosStr = guarF.getDbFormularios();
            this.guardarDB.guardarUsuariosyFormularios(usuarios, formulariosStr);
            respuesta = "Formulario cargado correctamente";
        }
        return respuesta;
    }
    
    private List<Formulario> getTodosFormularios(String fomulariosStr) {
        StringReader reader = new StringReader(fomulariosStr);
        LexerForms lexico = new LexerForms(reader);
        ParserForms parser = new ParserForms(lexico);

        try {
            parser.parse();
            return parser.getFormularios();
            //System.out.println("");
        } catch (Exception ex) {
//            System.out.println("ERROR EN GET USUARIOS DE ACCIONES");
//            System.out.println("Causa: " + ex.getCause());
//            System.out.println("Causa2: " + ex.toString());
        }
        return null;
    }

//    @GET
//    @Produces(MediaType.TEXT_HTML)
//    public String imprimirForm() {
//        return "<html><title>Hello</title><body><h1>IMPRIMIR FORM</h1><body></html>";
//    }
}
