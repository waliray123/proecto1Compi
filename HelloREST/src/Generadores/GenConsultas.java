/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generadores;

import analizadores.LexerCons;
import analizadores.ParserCons;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import objetos.Campo;
import objetos.Componente;
import objetos.Consulta;
import objetos.ErrorCom;
import objetos.Formulario;
import objetos.ParamComp;
import objetos.Parametro;
import objetos.Respuesta;
import objetos.Solicitud;

/**
 *
 * @author user-ubunto
 */
public class GenConsultas {
    private List<Formulario> todosFormularios;
    private List<Solicitud> consultasSols;
    private List<ErrorCom> erroresAcciones;
    private List<Respuesta> respuestasAcciones;

    /**\
     * 
     * @param todosFormularios
     * @param consultasSols
     * @param erroresAcciones
     * @param respuestasAcciones 
     */
    public GenConsultas(List<Formulario> todosFormularios, List<Solicitud> consultasSols, List<ErrorCom> erroresAcciones, List<Respuesta> respuestasAcciones) {
        this.todosFormularios = todosFormularios;
        this.consultasSols = consultasSols;
        this.erroresAcciones = erroresAcciones;
        this.respuestasAcciones = respuestasAcciones;   
        if (consultasSols != null && consultasSols.isEmpty() == false) {
            for (Solicitud consultasSol : consultasSols) {
                consult(consultasSol);
            }
        }
    }
        

    public List<Formulario> getTodosFormularios() {
        return todosFormularios;
    }

    public void setTodosFormularios(List<Formulario> todosFormularios) {
        this.todosFormularios = todosFormularios;
    }

    public List<Solicitud> getConsultasSols() {
        return consultasSols;
    }

    public void setConsultasSols(List<Solicitud> consultasSols) {
        this.consultasSols = consultasSols;
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
    

               
    
    private void consult(Solicitud solicitud) {
        String strRespuesta = "";
        List<Parametro> parametros = solicitud.getParametros();
        for (Parametro parametro : parametros) {
            //Obtener consulta
            Consulta consulta = null;
            String strCons = parametro.getContenido().replace("\"", "");
            StringReader reader = new StringReader(strCons);
            LexerCons lexico = new LexerCons(reader);
            ParserCons parser = new ParserCons(lexico);
            List<ErrorCom> errores;
            try {
                parser.parse();
                consulta = parser.getConsulta();
                errores = parser.getErroresCom();
            } catch (Exception ex) {
                System.out.println(ex);
            }
            if (consulta == null) {
                insertarError("LA CONSULTA NO SE PUDO REALIZAR");
            } else {
                Formulario formCons = null;
                for (Formulario formulario : todosFormularios) {
                    if (formulario.getIdForm().replace("\"", "").equals(consulta.getIdFormulario())) {
                        formCons = formulario;
                    }
                }
                if (formCons == null) {
                    insertarError("LA CONSULTA NO SE PUDO REALIZAR DEBIDO A QUE"
                            + "EL FORMULARIO: " + consulta.getIdFormulario() + " NO EXISTE");
                } else {
                    Formulario formEdit = formCons;
                    List<Campo> camposEdit = new ArrayList<>();
                    List<String> parametrosMostrar = consulta.getParametrosMostrar();
                    List<ParamComp> parametrosComp = consulta.getParametrosComparacion();
                    List<String> conectoresRel = consulta.getConectoresRelacional();
                    if (parametrosMostrar.size() == 0) {
                        camposEdit = formEdit.getCampos();
                    } else {
                        List<Campo> camposForm = formEdit.getCampos();
                        for (String nombreParamM : parametrosMostrar) {
                            for (Campo campo : camposForm) {
                                if (campo.getNombreCampo().replace("\"", "").equals(nombreParamM.replace("\"", ""))) {
                                    camposEdit.add(campo);
                                }
                                break;
                            }
                        }
                    }
                    if (parametrosComp.size() > 0) {
                        List<Campo> camposMod = camposEdit;
                        camposEdit = obtenerCamposAConsultar(camposMod, formEdit, parametrosComp,conectoresRel);
                    }
                    GenConsultForm genStrCons = new GenConsultForm(formEdit, camposEdit);
                    strRespuesta = genStrCons.getFormsStr();
                }
                insertarRespuesta("CONSULTAS", strRespuesta);
            }
        }
    }

    private List<Campo> obtenerCamposAConsultar(List<Campo> camposEdit, Formulario formulario, List<ParamComp> parametrosComp,List<String> conectoresRel) {        
        List<Campo> camposR = new ArrayList<>();
        List<Integer> indexRegistros = new ArrayList<>();
        int cont = -1;
        for (ParamComp paramComp : parametrosComp) {
            if (cont == -1) {
                Campo campoEv = null;
                String nombreCampo = paramComp.getNombreParametro();
                List<Campo> campos = formulario.getCampos();
                for (Campo componente : campos) {
                    if (nombreCampo.replace("\"", "").equals(componente.getNombreCampo().replace("\"", ""))) {
                        campoEv = componente;
                        break;
                    }
                }
                if (campoEv != null) {
                    indexRegistros = getIndexReg(paramComp, campoEv, formulario);
                } else {
                    insertarError("CAMPO A EVALUAR: " + nombreCampo + " INEXISTENTE");
                }
            }else{
                List<Integer> indexRegistros2 = new ArrayList<>();
                Campo campoEv = null;
                String nombreCampo = paramComp.getNombreParametro();
                List<Campo> campos = formulario.getCampos();
                for (Campo componente : campos) {
                    if (nombreCampo.replace("\"", "").equals(componente.getNombreCampo().replace("\"", ""))) {
                        campoEv = componente;
                        break;
                    }
                }
                if (campoEv != null) {
                    indexRegistros2 = getIndexReg(paramComp, campoEv, formulario);
                } else {
                    insertarError("CAMPO A EVALUAR: " + nombreCampo + " INEXISTENTE");
                }
                String conectorRel = conectoresRel.get(cont);
                List<Integer> indexRegistrosTemp = new ArrayList<>();
                if (conectorRel.equals("AND")) {
                    for (Integer index2 : indexRegistros2) {
                        for (Integer index1 : indexRegistros) {
                            if (index1 == index2) {
                                indexRegistrosTemp.add(index1);
                                break;
                            }
                        }
                    }                    
                }else if (conectorRel.equals("OR")) {
                    indexRegistrosTemp = indexRegistros;
                    for (Integer index2 : indexRegistros2) {
                        for (Integer index1 : indexRegistros) {
                            if (index1 != index2) {
                                indexRegistrosTemp.add(index1);
                                break;
                            }
                        }
                    }                    
                }else if (conectorRel.equals("NOT")) {                    
                    for (Integer index1 : indexRegistros) {
                        boolean agregar = false;
                        for (Integer index2 : indexRegistros2) {
                            if (index1 == index2) {
                                agregar = true;
                                break;
                            }
                        }
                        if (agregar == false) {
                            
                        }
                    }                    
                }
                indexRegistros = indexRegistrosTemp;
            }
            cont++;
        }
        
        for (Campo campo : camposEdit) {
            List<String> registros = new ArrayList<>();            
            for (Integer indexRegistro : indexRegistros) {
                String registro =  campo.getRegistros().get(indexRegistro);
                if (registro != null && registro.isEmpty() == false) {
                    registros.add(registro);
                }else{
                    registros.add("");
                }
            }
            campo.setRegistros(registros);
            camposR.add(campo);
        }
        return camposR;
    }

    private List<Integer> getIndexReg(ParamComp paramComp, Campo campoEv, Formulario formulario) {
        List<Integer> indexRegistros = new ArrayList<>();
        List<String> registros = campoEv.getRegistros();
        String operadorLog = paramComp.getOperadorLogico();
        String claseCamp = getClaseCampo(formulario, campoEv.getNombreCampo());
        int index = 0;
        for (String registro : registros) {
            if (operadorLog.replace("\"", "").equals("IGUAL")) {
                String valorReg = registro.replace("\"", "");
                String valorComp = paramComp.getValorBuscar().replace("\'", "");
                if (claseCamp.replace("\"", "").equals("CHECK_BOX")) {
                    String[] valoresReg = valorReg.split(",");
                    for (int i = 0; i < valoresReg.length; i++) {
                        if (valoresReg[i].equals(valorComp)) {
                            indexRegistros.add(index);
                        }
                    }
                } else {
                    if (valorReg.equals(valorComp)) {
                        indexRegistros.add(index);
                    }
                }
            } else if (operadorLog.replace("\"", "").equals("DESIG")) {
                String valorReg = registro.replace("\"", "");
                String valorComp = paramComp.getValorBuscar().replace("\'", "");
                if (claseCamp.replace("\"", "").equals("CHECK_BOX")) {
                    String[] valoresReg = valorReg.split(",");
                    for (int i = 0; i < valoresReg.length; i++) {
                        if (valoresReg[i].equals(valorComp) == false) {
                            indexRegistros.add(index);
                        }
                    }
                } else {
                    if (valorReg.equals(valorComp) == false) {
                        indexRegistros.add(index);
                    }
                }
            } else if (operadorLog.replace("\"", "").equals("MAYIG")) {
                String valorReg = registro.replace("\"", "");
                Double valorComp = Double.parseDouble(paramComp.getValorBuscar().replace("\'", ""));
                if (claseCamp.replace("\"", "").equals("CHECK_BOX")) {
                    String[] valoresReg = valorReg.split(",");
                    for (int i = 0; i < valoresReg.length; i++) {
                        if (Double.parseDouble(valoresReg[i]) >= valorComp) {
                            indexRegistros.add(index);
                        }
                    }
                } else {
                    Double valor = Double.parseDouble(registro.replace("\"", ""));
                    if (valor >= valorComp) {
                        indexRegistros.add(index);
                    }
                }
            } else if (operadorLog.replace("\"", "").equals("MENIG")) {
                String valorReg = registro.replace("\"", "");
                Double valorComp = Double.parseDouble(paramComp.getValorBuscar().replace("\'", ""));
                if (claseCamp.replace("\"", "").equals("CHECK_BOX")) {
                    String[] valoresReg = valorReg.split(",");
                    for (int i = 0; i < valoresReg.length; i++) {
                        if (Double.parseDouble(valoresReg[i]) <= valorComp) {
                            indexRegistros.add(index);
                        }
                    }
                } else {
                    Double valor = Double.parseDouble(registro.replace("\"", ""));
                    if (valor <= valorComp) {
                        indexRegistros.add(index);
                    }
                }
            } else if (operadorLog.replace("\"", "").equals("MAY")) {
                String valorReg = registro.replace("\"", "");
                Double valorComp = Double.parseDouble(paramComp.getValorBuscar().replace("\'", ""));
                if (claseCamp.replace("\"", "").equals("CHECK_BOX")) {
                    String[] valoresReg = valorReg.split(",");
                    for (int i = 0; i < valoresReg.length; i++) {
                        if (Double.parseDouble(valoresReg[i]) > valorComp) {
                            indexRegistros.add(index);
                        }
                    }
                } else {
                    Double valor = Double.parseDouble(registro.replace("\"", ""));
                    if (valor > valorComp) {
                        indexRegistros.add(index);
                    }
                }
            } else if (operadorLog.replace("\"", "").equals("MEN")) {
                String valorReg = registro.replace("\"", "");
                Double valorComp = Double.parseDouble(paramComp.getValorBuscar().replace("\'", ""));
                if (claseCamp.replace("\"", "").equals("CHECK_BOX")) {
                    String[] valoresReg = valorReg.split(",");
                    for (int i = 0; i < valoresReg.length; i++) {
                        if (Double.parseDouble(valoresReg[i]) < valorComp) {
                            indexRegistros.add(index);
                        }
                    }
                } else {
                    Double valor = Double.parseDouble(registro.replace("\"", ""));
                    if (valor < valorComp) {
                        indexRegistros.add(index);
                    }
                }
            }
            index++;
        }

        return indexRegistros;
    }

    private String getClaseCampo(Formulario formulario, String nombreCampo) {
        String tipoComp = "";
        List<Componente> componentes = formulario.getComponentes();
        for (Componente componente : componentes) {
            String nombreComp1 = componente.getNombre();
            if (nombreComp1.replace("\"", "").equals(nombreCampo.replace("\"", ""))) {
                return componente.getClase().replace("\"", "");
            }
        }
        return tipoComp;
    }
    
    private void insertarError(String desc) {
        ErrorCom errorC = new ErrorCom("COMPILACION", desc, null, null, null);
        this.erroresAcciones.add(errorC);
    }

    private void insertarRespuesta(String tipo, String desc) {
        Respuesta resp = new Respuesta(tipo, desc);
        this.respuestasAcciones.add(resp);
    }
}
