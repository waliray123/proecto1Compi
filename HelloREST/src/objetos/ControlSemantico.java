/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author user-ubunto
 */
public class ControlSemantico {

    private List<Solicitud> solicitudes;
    private List<ErrorCom> errores;    

//    private final int CANTMI_CREAR_U = 2;
//    private final int CANTMI_MOD_U = 3;
//    private final int CANTMI_ELIM_U = 1;
//    private final int CANTMI_LOG_U = 2;
//    private final int CANTMI_NUEVO_FORM = 4;
//    private final int CANTMI_ELIM_FORM = 1;
//    private final int CANTMI_MOD_FORM = 1;
//    private final int CANTMI_AGRE_COMP = 4;
//    private final int CANTMI_ELIM_COMP = 1;
//    private final int CANTMI_MOD_COMP = 2;

    private String lin;
    private String col;
    private String usuario;

    public ControlSemantico(List<Solicitud> solicitudes, List<ErrorCom> errores,String usuario) {
        this.usuario = usuario;
        this.solicitudes = solicitudes;
        this.errores = errores;
        revisarSolicitudes();
    }

    private void revisarSolicitudes() {
        if (this.solicitudes.isEmpty() == false) {
            String nombreSol = "";
            for (Solicitud solicitud : solicitudes) {
                nombreSol = solicitud.getTipo();
                this.lin = solicitud.getLin();
                this.col = solicitud.getCol();
                switch (nombreSol) {
                    case "CREAR_U":
                        validarParamCrearU(solicitud);
                        break;
                    case "MOD_U":
                        validarParamModU(solicitud);
                        break;
                    case "ELIM_U":
                        validarParamElimU(solicitud);
                        break;
                    case "LOG_U":
                        validarParamLogU(solicitud);
                        break;
                    case "NUEVO_FORM":
                        if(usuario.equals("")){
                            insertarError("No se puede realizar la solicitud NUEVO_FORMULARIO sino esta logueado");
                        }
                        validarParamNuevoForm(solicitud);
                        break;
                    case "ELIM_FORM":
                        if(usuario.equals("")){
                            insertarError("No se puede realizar la solicitud"+  nombreSol + "sino esta logueado");
                        }
                        validarParamElimForm(solicitud);
                        break;
                    case "MOD_FORM":
                        if(usuario.equals("")){
                            insertarError("No se puede realizar la solicitud"+  nombreSol + "sino esta logueado");
                        }
                        validarParamModForm(solicitud);
                        break;
                    case "AGRE_COMP":
                        if(usuario.equals("")){
                            insertarError("No se puede realizar la solicitud"+  nombreSol + "sino esta logueado");
                        }
                        validarParamAgreComp(solicitud);
                        break;
                    case "ELIM_COMP":
                        if(usuario.equals("")){
                            insertarError("No se puede realizar la solicitud"+  nombreSol + "sino esta logueado");
                        }
                        validarParamElimComp(solicitud);
                        break;
                    case "MOD_COMP":
                        if(usuario.equals("")){
                            insertarError("No se puede realizar la solicitud"+  nombreSol + "sino esta logueado");
                        }
                        validarParamModComp(solicitud);
                        break;
                    case "CONSULT":
                        //TODO
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void revisarParamOblig(List<Parametro> parametros, String nombrep, String nombreError) {
        int cantP = cantidadRepeticionesSegunNombreParam(parametros, nombrep);
        if (cantP != 1) {
            insertarErrorParamOblg(nombreError, cantP);
        }
    }
    
    private void revisarParamNoOblig(List<Parametro> parametros, String nombrep, String nombreError){
        int cantP = cantidadRepeticionesSegunNombreParam(parametros, nombrep);
        if (cantP > 1) {
            insertarErrorParamOblg(nombreError, cantP);
        }
    }
    
    private void revisarParamsInnecesario(List<String> paramsRevisar, List<Parametro> parametros){
        for (String nomP : paramsRevisar) {
            for (Parametro parametro : parametros) {
                if (parametro.getNombre().equals(nomP)) {
                    insertarError("Cantidad de"+ nomP +"es innecesario");
                }
            }
        }
    
    }

    private void validarParamCrearU(Solicitud solicitud) {
        List<Parametro> parametros = solicitud.getParametros();
        //Revision de parametros obligatorios        
        revisarParamOblig(parametros, "USUARIO", "USUARIO");
        revisarParamOblig(parametros, "PASS", "PASSWORD");
        
        // Validar FECHA_CREACION
        int cantFech = cantidadRepeticionesSegunNombreParam(parametros, "FECHA_CREA");
        if (cantFech > 1) {
            insertarError("Cantidad de FECHA_CREACION excede su limite");
        } else if (cantFech == 0) {
            Parametro paramF = new Parametro();
            paramF.setNombre("FECHA_CREA");
            paramF.setContenido(getFechaAct());
            solicitud.setNewParametro(paramF);
        }
    }
    
    private void validarParamModU(Solicitud solicitud){
        List<Parametro> parametros = solicitud.getParametros();
        //Revision de parametros obligatorios
        revisarParamOblig(parametros, "USUARIO_ANTI", "USUARIO_ANTIGUO");
        revisarParamOblig(parametros, "USUARIO_NUEVO", "USUARIO_NUEVO");
        revisarParamOblig(parametros, "NUEVO_PASS", "NUEVO_PASSWORD");
        
        //validar FECHA_MOD
        int cantFech = cantidadRepeticionesSegunNombreParam(parametros, "FECHA_MOD");
        if (cantFech > 1) {
            insertarError("Cantidad de FECHA_MODIFICACION excede su limite");
        }else if(cantFech == 0){
            Parametro paramF = new Parametro();
            paramF.setNombre("FECHA_MOD");
            paramF.setContenido(getFechaAct());
            solicitud.setNewParametro(paramF);
        }
        
    }
    
    private void validarParamElimU(Solicitud solicitud){
        List<Parametro> parametros = solicitud.getParametros();
        revisarParamOblig(parametros, "USUARIO", "USUARIO");
    }
    
    private void validarParamLogU(Solicitud solicitud){
        List<Parametro> parametros = solicitud.getParametros();
        //Revision de parametros obligatorios        
        revisarParamOblig(parametros, "USUARIO", "USUARIO");
        revisarParamOblig(parametros, "PASS", "PASSWORD");
        
        int cantFech = cantidadRepeticionesSegunNombreParam(parametros, "FECHA_CREA");
        if (cantFech > 0) {
            insertarError("FECHA_CREACION no debe ir en esta solicitud");
        }
    }
    
    private void validarParamNuevoForm(Solicitud solicitud){
        List<Parametro> parametros = solicitud.getParametros();
        //Revision parametros obligatorios
        revisarParamOblig(parametros, "ID", "ID");
        revisarParamOblig(parametros, "TITUL", "TITULO");
        revisarParamOblig(parametros, "NOMBRE", "NOMBRE");
        revisarParamOblig(parametros, "TEMA", "TEMA");
        
        //validar FECHA_CREACION
        int cantFech = cantidadRepeticionesSegunNombreParam(parametros, "FECHA_CREA");
        if (cantFech > 1) {
            insertarError("Cantidad de FECHA_CREACION excede su limite");
        }else if(cantFech == 0){
            Parametro paramF = new Parametro();
            paramF.setNombre("FECHA_CREA");
            paramF.setContenido(getFechaAct());
            solicitud.setNewParametro(paramF);
        }
        
        //validar USUARIO_CREA
        int cantUs = cantidadRepeticionesSegunNombreParam(parametros, "USUARIO_CREA");
        if (cantUs > 1) {
            insertarError("Cantidad de USUARIO_CREACION excede su limite");
        }else if(cantUs == 0){
            Parametro paramF = new Parametro();
            paramF.setNombre("USUARIO_CREA");
            paramF.setContenido(this.usuario);
            solicitud.setNewParametro(paramF);
        }   
    }
    
    private void validarParamElimForm(Solicitud solicitud){
        List<Parametro> parametros = solicitud.getParametros();
        //Revision parametros obligatorios
        revisarParamOblig(parametros, "ID", "ID");        
    }
    
    private void validarParamModForm(Solicitud solicitud){
        List<Parametro> parametros = solicitud.getParametros();
        //Revision parametros obligatorios
        revisarParamOblig(parametros, "ID", "ID");        
        //Revisar parametros no obligatorios
        revisarParamNoOblig(parametros, "TITUL", "TITULO");
        revisarParamNoOblig(parametros, "NOMBRE", "NOMBRE");
        revisarParamNoOblig(parametros, "TEMA", "TEMA");
        
        
    }
    
    private void validarParamAgreComp(Solicitud solicitud){
        List<Parametro> parametros = solicitud.getParametros();
        //Revision parametros obligatorios
        revisarParamOblig(parametros, "ID", "ID");
        revisarParamOblig(parametros, "NOMBRE_CAMPO", "NOMBRE_CAMPO");
        revisarParamOblig(parametros, "FORM", "FORM");
        revisarParamOblig(parametros, "CLASE", "CLASE");
        revisarParamOblig(parametros, "TEXTO_VIS", "TEXTO_VISIBLE");
        //Revisar parametros no obligatorios
        revisarParamNoOblig(parametros, "INDICE", "INDICE");        
        
        //Revisar ALINEACION
        int cantUs = cantidadRepeticionesSegunNombreParam(parametros, "ALINEA");
        if (cantUs > 1) {
            insertarError("Cantidad de ALINEACION excede su limite");
        }else if(cantUs == 0){
            Parametro paramF = new Parametro();
            paramF.setNombre("ALINEA");
            paramF.setContenido("CENTRO");
            solicitud.setNewParametro(paramF);
        }
        validarParamPorClaseComp(parametros);
    }
    
    private void validarParamPorClaseComp(List<Parametro> parametros){
        String tipoClase = getParamClase(parametros);
        List<String> paramsRev;
        switch (tipoClase) {
                    case "IMAGEN":
                        revisarParamOblig(parametros, "URL", "URL");
                        paramsRev = Arrays.asList("REQUE", "FILAS", "COLUMNAS","OPCIONES");
                        revisarParamsInnecesario(paramsRev,parametros);
                        break;
                    case "BOTON":                        
                        paramsRev = Arrays.asList("OPCIONES","REQUE","URL", "FILAS", "COLUMNAS");
                        revisarParamsInnecesario(paramsRev,parametros);
                        break;
                    case "CAMPO_TEXT":                        
                        paramsRev = Arrays.asList("OPCIONES","URL", "FILAS", "COLUMNAS");
                        revisarParamNoOblig(parametros, "REQUE", "REQUERIDO");
                        revisarParamsInnecesario(paramsRev,parametros);
                        break;
                    case "AREA_TEXT":                        
                        revisarParamNoOblig(parametros, "FILAS", "FILAS");
                        revisarParamNoOblig(parametros, "COLUMNAS", "COLUMNAS");
                        revisarParamNoOblig(parametros, "REQUE", "REQUERIDO");
                        paramsRev = Arrays.asList("OPCIONES","URL");
                        revisarParamsInnecesario(paramsRev,parametros);
                        break;
                    case "CHECK_BOX":       
                        paramsRev = Arrays.asList("URL", "FILAS", "COLUMNAS");
                        revisarParamNoOblig(parametros, "REQUE", "REQUERIDO");
                        revisarParamOblig(parametros, "OPCIONES", "OPCIONES");
                        revisarParamsInnecesario(paramsRev,parametros);
                        break;
                    case "RADIO":       
                        paramsRev = Arrays.asList("URL", "FILAS", "COLUMNAS");
                        revisarParamNoOblig(parametros, "REQUE", "REQUERIDO");
                        revisarParamOblig(parametros, "OPCIONES", "OPCIONES");
                        revisarParamsInnecesario(paramsRev,parametros);
                        break;
                    case "COMBO":       
                        paramsRev = Arrays.asList("URL", "FILAS", "COLUMNAS");
                        revisarParamNoOblig(parametros, "REQUE", "REQUERIDO");
                        revisarParamOblig(parametros, "OPCIONES", "OPCIONES");
                        revisarParamsInnecesario(paramsRev,parametros);
                        break;
                    case "FICHERO":       
                        paramsRev = Arrays.asList("URL", "FILAS", "COLUMNAS","OPCIONES");
                        revisarParamNoOblig(parametros, "REQUE", "REQUERIDO");                        
                        revisarParamsInnecesario(paramsRev,parametros);
                        break;
                    default:
                        break;
        }
    }
    
    private void validarParamElimComp(Solicitud solicitud){
        List<Parametro> parametros = solicitud.getParametros();
        //Revision parametros obligatorios
        revisarParamOblig(parametros, "ID", "ID");  
        revisarParamOblig(parametros, "FORM", "FORMULARIO");  
    }
    
    private void validarParamModComp(Solicitud solicitud){
        List<Parametro> parametros = solicitud.getParametros();
        //Revision parametros obligatorios
        revisarParamOblig(parametros, "ID", "ID");     
        revisarParamOblig(parametros, "FORM", "FORM");        
        //Revisar parametros no obligatorios
        revisarParamNoOblig(parametros, "INDICE", "INDICE");
        revisarParamNoOblig(parametros, "CLASE", "CLASE");
        revisarParamNoOblig(parametros, "TEXTO_VIS", "TEXTO_VISIBLE");
        revisarParamNoOblig(parametros, "NOMBRE_CAMPO", "NOMBRE_CAMPO");
        revisarParamNoOblig(parametros, "ALINEA", "ALINEACION");
        
        int cantClass = cantidadRepeticionesSegunNombreParam(parametros, "CLASE");
        if (cantClass == 1) {
            validarParamPorClaseComp(parametros);
        }
        
    }
    
    private String getParamClase(List<Parametro> parametros){
        for (Parametro parametro : parametros) {
            if (parametro.getNombre().equals("CLASE")) {
                return parametro.getContenido();
            }
        }
        return "";
    }

    private int cantidadRepeticionesSegunNombreParam(List<Parametro> parametros, String paramB) {
        int cant = 0;
        for (Parametro parametro : parametros) {
            if (paramB.equals(parametro.getNombre())) {
                cant++;
            }
        }
        return cant;
    }

    private void insertarErrorParamOblg(String nombreP, int cantP) {
        String nombreError = "";
        if (cantP > 1) {
            //Cantidad de parametro nombreP excede su limite
            nombreError = "Cantidad de parametro " + nombreP + " excede su limite";
        } else if (cantP <= 0) {
            //nombreP es necesario para realizar la solicitud
            nombreError = nombreP + " es necesario para realizar la solicitud";
        }
        insertarError(nombreError);
    }

    private void insertarError(String nombreError) {
        ErrorCom errorCom = new ErrorCom("Semantico", nombreError, this.lin, this.col, "");
        this.errores.add(errorCom);
    }

    private String getFechaAct() {
        String fecha = "";
        Calendar c = Calendar.getInstance();
        String dia = Integer.toString(c.get(Calendar.DATE));
        String mes = Integer.toString(c.get(Calendar.MONTH));
        String annio = Integer.toString(c.get(Calendar.YEAR));
//        System.out.println("DIA: " + dia);
//        System.out.println("MES: " + mes);
//        System.out.println("ANNIO: " + annio);
        //yyyy-MM-dd
        fecha = annio + "-" + mes + "-" + dia;
//        System.out.println(fecha);
        return fecha;
    }

    public List<ErrorCom> getErrores() {
        return errores;
    }

    public List<Solicitud> getSolicitudes() {
        return solicitudes;
    }

}
