/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import analizadores.LexerForms;
import analizadores.LexerUsers;
import analizadores.ParserForms;
import analizadores.ParserUsers;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import objetos.Componente;
import objetos.ErrorCom;
import objetos.Formulario;
import objetos.Parametro;
import objetos.Respuesta;
import objetos.Solicitud;
import objetos.Usuario;
import objetos.UsuariosFormularios;

/**
 * Clase que realiza las acciones de las solicitudes siguiendo un orden
 * estructurado
 *
 * @author user-ubunto
 */
public class Acciones {

    private List<Solicitud> solicitudes;
    private String usuarios;
    private String formularios;
    private List<Usuario> todosUsuarios;
    private List<Formulario> todosFormularios;
    private List<ErrorCom> erroresAcciones;
    private List<Respuesta> respuestasAcciones;

    public Acciones(List<Solicitud> solicitudes, UsuariosFormularios userForms) {
        this.todosUsuarios = new ArrayList<>();
        this.todosFormularios = new ArrayList<>();
        this.erroresAcciones = new ArrayList<>();
        this.respuestasAcciones = new ArrayList<>();
        this.solicitudes = solicitudes;
        this.usuarios = userForms.getUsuarios();
        this.formularios = userForms.getFormularios();
        //this.formularios = pruebaForms();
        getTodosFormularios();
        getTodosUsuarios();
        realizarAcciones();
        generarDbUsuarios();
        generarDbFormularios();
    }

    public List<Solicitud> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<Solicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public String getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(String usuarios) {
        this.usuarios = usuarios;
    }

    public String getFormularios() {
        return formularios;
    }

    public void setFormularios(String formularios) {
        this.formularios = formularios;
    }

    private void realizarAcciones() {
        //Revisar el tipo de acciones y redirigir segun su tipo
        for (Solicitud solicitud : solicitudes) {
            String tipoSol = solicitud.getTipo();
            switch (tipoSol) {
                case "CREAR_U":
                    crearU(solicitud);
                    break;
                case "MOD_U":
                    modU(solicitud);
                    break;
                case "ELIM_U":
                    elimU(solicitud);
                    break;
                case "LOG_U":
                    logU(solicitud);
                    break;
                case "NUEVO_FORM":
                    nuevoForm(solicitud);
                    break;
                case "ELIM_FORM":
                    elimForm(solicitud);
                    break;
                case "MOD_FORM":
                    modForm(solicitud);
                    break;
                case "AGRE_COMP":
                    agreComp(solicitud);
                    break;
                case "ELIM_COMP":
                    elimComp(solicitud);
                    break;
                case "MOD_COMP":
                    modComp(solicitud);
                    break;
                case "CONSULT":
                    //TODO
                    break;
                default:
                    break;
            }

        }

    }

    /**
     * Metodo que realiza la solicitud de Crear Usuario
     *
     * @param solicitud
     */
    private void crearU(Solicitud solicitud) {
        List<Parametro> parametros = solicitud.getParametros();
        Parametro paramUsuario = buscarParametrosPorNombre(parametros, "USUARIO");
        Parametro paramPass = buscarParametrosPorNombre(parametros, "PASS");
        Parametro paramFecha = buscarParametrosPorNombre(parametros, "FECHA_CREA");
        String nombreU = paramUsuario.getContenido();
        int indexU = getIndexUsuarioPorNombre(nombreU);
        if (indexU == -1) {
            Usuario usuario = new Usuario();
            usuario.setUsuario(paramUsuario.getContenido());
            usuario.setPassword(paramPass.getContenido());
            usuario.setFechaCrea(paramFecha.getContenido());
            this.todosUsuarios.add(usuario);
            //Se crea el usuario con exito
            insertarRespuesta("CREAR_USUARIO", "Se creo el usuario: " + paramUsuario.getContenido().replace("\"", ""));
        } else {
            //Error el usuario ya existe
            insertarError("EL USUARIO YA EXISTE CON ESE NOMBRE");
        }
    }

    /**
     * Metodo que realiza la solicitud de Modificar Usuario
     *
     * @param solicitud
     */
    private void modU(Solicitud solicitud) {
        List<Parametro> parametros = solicitud.getParametros();
        Parametro paramUsuarioAnti = buscarParametrosPorNombre(parametros, "USUARIO_ANTI");
        Parametro paramUsuarioNuev = buscarParametrosPorNombre(parametros, "USUARIO_NUEVO");
        Parametro paramPass = buscarParametrosPorNombre(parametros, "NUEVO_PASS");
        Parametro paramFecha = buscarParametrosPorNombre(parametros, "FECHA_MOD");
        String nombreUAnti = paramUsuarioAnti.getContenido();
        String nombreUNuev = paramUsuarioNuev.getContenido();
        int indexUAnti = getIndexUsuarioPorNombre(nombreUAnti);
        int indexUNuev = getIndexUsuarioPorNombre(nombreUNuev);
        if (indexUAnti == -1) {
            //Error el usuario no existe
            insertarError("EL USUARIO NO EXISTE");
        } else {
            if (indexUNuev == -1) {
                Usuario usuario = todosUsuarios.get(indexUAnti);
                usuario.setUsuario(paramUsuarioNuev.getContenido());
                usuario.setPassword(paramPass.getContenido());
                usuario.setFechaCrea(paramFecha.getContenido());
                //Se modifica el usuario con exito
                insertarRespuesta("CREAR_USUARIO", "Se modifico el usuario: " + paramUsuarioAnti.getContenido().replace("\"", "") + " a "
                        + paramUsuarioNuev.getContenido().replace("\"", ""));
            } else {
                //Error el usuario ya existe con ese nombre
                insertarError("EL USUARIO YA EXISTE CON ESE NOMBRE");
            }
        }
    }

    /**
     * Metodo que realiza la solicitud de Eliminar Usuario
     *
     * @param solicitud
     */
    private void elimU(Solicitud solicitud) {
        List<Parametro> parametros = solicitud.getParametros();
        Parametro paramUsuario = buscarParametrosPorNombre(parametros, "USUARIO");
        String nombreU = paramUsuario.getContenido();
        int indexU = getIndexUsuarioPorNombre(nombreU);
        if (indexU != -1) {
            this.todosUsuarios.remove(indexU);
            //Se elimina exito
            insertarRespuesta("CREAR_USUARIO", "Se elimino el usuario: " + paramUsuario.getContenido().replace("\"", ""));
        } else {
            //Error el usuario no existe
            insertarError("EL USUARIO NO EXISTE");
        }

    }

    /**
     * Metodo que realiza la solicitud de Loguear Usuario
     *
     * @param solicitud
     */
    private void logU(Solicitud solicitud) {
        List<Parametro> parametros = solicitud.getParametros();
        Parametro paramUsuario = buscarParametrosPorNombre(parametros, "USUARIO");
        Parametro paramPass = buscarParametrosPorNombre(parametros, "PASS");
        String nombreU = paramUsuario.getContenido();
        int indexU = getIndexUsuarioPorNombre(nombreU);
        if (indexU != -1) {
            Usuario usuarioLog = this.todosUsuarios.get(indexU);
            if (usuarioLog.getPassword().equals(paramPass.getContenido())) {
                //Loguear usuario TODO
                Respuesta resp = new Respuesta("LOGIN_USUARIO", "Usuario correctamente logueado");
                resp.setLogU(nombreU.replace("\"", ""));
                this.respuestasAcciones.add(resp);
            } else {
                insertarError("NO SE LOGRO INGRESAR YA QUE LA CONTRASENIA ES INCORRECTA");
            }
        } else {
            //Error el usuario no existe
            insertarError("EL USUARIO NO EXISTE");
        }
    }

    /**
     * Metodo que realiza la solicitud de insertar un nuevo formulario
     *
     * @param solicitud
     */
    private void nuevoForm(Solicitud solicitud) {
        List<Parametro> parametros = solicitud.getParametros();
        Parametro paramIdForm = buscarParametrosPorNombre(parametros, "ID");
        String idForm = paramIdForm.getContenido();
        int indexF = getIndexFormularioPorId(idForm);
        if (indexF == -1) {
            //Crear formulario
            Parametro paramTitul = buscarParametrosPorNombre(parametros, "TITUL");
            Parametro paramNombre = buscarParametrosPorNombre(parametros, "NOMBRE");
            Parametro paramTema = buscarParametrosPorNombre(parametros, "TEMA");
            Parametro paramFecha = buscarParametrosPorNombre(parametros, "FECHA_CREA");
            Parametro paramUsuarioCrea = buscarParametrosPorNombre(parametros, "USUARIO_CREA");
            Formulario formTemp = new Formulario();
            formTemp.setIdForm(idForm);
            formTemp.setTitulo(paramTitul.getContenido());
            formTemp.setNombre(paramNombre.getContenido());
            formTemp.setTema(paramTema.getContenido());
            formTemp.setFechaCrea(paramFecha.getContenido());
            formTemp.setUsuarioCrea(paramUsuarioCrea.getContenido());
            this.todosFormularios.add(formTemp);
            insertarRespuesta("NUEVO_FORM", "Se creo el formulario: " + paramIdForm.getContenido().replace("\"", ""));
        } else {
            //Error el formulario ya existe
            insertarError("YA EXISTE UN FORMULARIO CON ESE ID");
        }
    }

    /**
     * Metodo que elimina un formulaio segun su id
     *
     * @param solicitud
     */
    private void elimForm(Solicitud solicitud) {
        List<Parametro> parametros = solicitud.getParametros();
        Parametro paramIdForm = buscarParametrosPorNombre(parametros, "ID");
        String idForm = paramIdForm.getContenido();
        int indexF = getIndexFormularioPorId(idForm);
        if (indexF != -1) {
            //Eliminar formulario
            this.todosFormularios.remove(indexF);
            insertarRespuesta("ELIM_FORMULARIO", "Se elimino el formulario: " + paramIdForm.getContenido().replace("\"", ""));
        } else {
            //Error el formulario no existe
            insertarError("EL FORMULARIO NO EXISTE POR LO QUE NO SE PUEDE ELIMINAR");
        }
    }

    private void modForm(Solicitud solicitud) {
        List<Parametro> parametros = solicitud.getParametros();
        Parametro paramIdForm = buscarParametrosPorNombre(parametros, "ID");
        String idForm = paramIdForm.getContenido();
        int indexF = getIndexFormularioPorId(idForm);
        if (indexF != -1) {
            //Modificar formulario
            Formulario formTemp = this.todosFormularios.get(indexF);
            Parametro paramTitul = buscarParametrosPorNombre(parametros, "TITUL");
            Parametro paramNombre = buscarParametrosPorNombre(parametros, "NOMBRE");
            Parametro paramTema = buscarParametrosPorNombre(parametros, "TEMA");
            if (paramTitul != null) {
                formTemp.setTitulo(paramTitul.getContenido());
            }
            if (paramNombre != null) {
                formTemp.setNombre(paramNombre.getContenido());
            }
            if (paramTema != null) {
                formTemp.setTema(paramTema.getContenido());
            }
            //formulario modificado
            insertarRespuesta("MOD_FORM", "Se modifico el formulario: " + paramIdForm.getContenido().replace("\"", ""));
        } else {
            //Error el formulario no existe
            insertarError("EL FORMULARIO NO EXISTE POR LO QUE NO SE PUEDE MODIFICAR");
        }
    }

    private void agreComp(Solicitud solicitud) {
        List<Parametro> parametros = solicitud.getParametros();
        Parametro paramIdForm = buscarParametrosPorNombre(parametros, "FORM");
        String idForm = paramIdForm.getContenido();
        int indexF = getIndexFormularioPorId(idForm);
        if (indexF != -1) {
            Formulario formMod = this.todosFormularios.get(indexF);
            Parametro paramIdComp = buscarParametrosPorNombre(parametros, "ID");
            String idComp = paramIdComp.getContenido();
            int indexComp = getIndexComponentePorId(formMod, idComp);
            if (indexComp == -1) {
                //Lista de componentes 
                List<Componente> componentesForm = formMod.getComponentes();
                //Agregar componente
                Parametro paramNombreCamp = buscarParametrosPorNombre(parametros, "NOMBRE_CAMPO");
                Parametro paramClase = buscarParametrosPorNombre(parametros, "CLASE");
                Parametro paramTextVis = buscarParametrosPorNombre(parametros, "TEXTO_VIS");
                Parametro paramIndice = buscarParametrosPorNombre(parametros, "INDICE");
                Parametro paramAlinea = buscarParametrosPorNombre(parametros, "ALINEA");
                int indice = -1;
                if (paramIndice == null) {
                    indice = getNuevoIndice(componentesForm, indice);
                } else {
                    String indiceStr = paramIndice.getContenido().replace("\"", "");
                    indice = Integer.parseInt(indiceStr);
                    indice = getNuevoIndice(componentesForm, indice);
                }
                boolean indiceCorrecto = true;
                Componente compIns = new Componente();
                compIns.setIdComp(idComp);
                compIns.setIdformulario(idForm);
                compIns.setNombre(paramNombreCamp.getContenido());
                compIns.setClase(paramClase.getContenido());
                compIns.setTextoVisible(paramTextVis.getContenido());
                compIns.setAlineacion(paramAlinea.getContenido());
                if (indice != -1) {
                    compIns.setIndice("\"" + String.valueOf(indice) + "\"");
                } else {
                    //Error el ya es usado
                    insertarError("EL INDICE QUE SE INGRESO YA ES USADO");
                    indiceCorrecto = false;
                }
                Parametro paramTemp;
                paramTemp = buscarParametrosPorNombre(parametros, "URL");
                if (paramTemp != null) {
                    compIns.setUrl(paramTemp.getContenido());
                    paramTemp = null;
                }
                paramTemp = buscarParametrosPorNombre(parametros, "FILAS");
                if (paramTemp != null) {
                    compIns.setFilas(paramTemp.getContenido());
                    paramTemp = null;
                }
                paramTemp = buscarParametrosPorNombre(parametros, "COLUMNAS");
                if (paramTemp != null) {
                    compIns.setColumnas(paramTemp.getContenido());
                    paramTemp = null;
                }
                paramTemp = buscarParametrosPorNombre(parametros, "OPCIONES");
                if (paramTemp != null) {
                    compIns.setOpciones(paramTemp.getContenido());
                    paramTemp = null;
                }
                paramTemp = buscarParametrosPorNombre(parametros, "REQUE");
                if (paramTemp != null) {
                    compIns.setRequerido(paramTemp.getContenido());
                    paramTemp = null;
                }
                if (indiceCorrecto == true) {
                    componentesForm.add(compIns);
                    formMod.setComponentes(componentesForm);
                    insertarRespuesta("AGREGAR_COMPONENTE", "Se agrego el componente: " + paramIdComp.getContenido().replace("\"", "")
                            + "al formulario: " + paramIdForm.getContenido().replace("\"", ""));
                }
            } else {
                // el 
                insertarError("YA EXISTE UN COMPONENTE CON ESE ID");
            }

        } else {
            //El formulario al que se agregara no existe
            insertarError("EL FORMULARIO AL QUE SE AGREGARA EL COMPONENTE NO EXISTE");
        }
    }

    private void elimComp(Solicitud solicitud) {
        boolean eliminado = false;
        List<Parametro> parametros = solicitud.getParametros();
        Parametro paramIdForm = buscarParametrosPorNombre(parametros, "FORM");
        String idForm = paramIdForm.getContenido();
        int indexF = getIndexFormularioPorId(idForm);
        if (indexF != -1) {
            Parametro paramIdComp = buscarParametrosPorNombre(parametros, "ID");
            Formulario formulario = todosFormularios.get(indexF);
            List<Componente> componentes = formulario.getComponentes();
            int index = 0;
            for (Componente componente : componentes) {
                if (componente.getIdComp().equals(paramIdComp.getContenido())) {
                    formulario.elimComponente(index);
                    eliminado = true;
                    break;
                }
                index++;
            }
            if (eliminado == false) {
                insertarError("NO SE ENCONTRO EL COMPONENTE A ELIMINAR");
            } else {
                insertarRespuesta("ELIMINAR_COMPONENTE", "Se elimino el componente: " + paramIdComp.getContenido().replace("\"", ""));
            }
        } else {
            insertarError("NO SE ENCONTRO EL FORMULARIO DE DONDE ELIMINAR");
        }

    }

    private void modComp(Solicitud solicitud) {
        List<Parametro> parametros = solicitud.getParametros();
        Parametro paramIdComp = buscarParametrosPorNombre(parametros, "ID");
        Parametro paramIdForm = buscarParametrosPorNombre(parametros, "FORM");
        String idForm = paramIdForm.getContenido();
        int indexF = getIndexFormularioPorId(idForm);
        if (indexF != -1) {
            Formulario formMod = this.todosFormularios.get(indexF);
            String idComp = paramIdComp.getContenido();
            int indexComp = getIndexComponentePorId(formMod, idComp);
            if (indexComp != -1) {
                //Lista de componentes 
                List<Componente> componentesForm = formMod.getComponentes();
                //Modificar componente                                                    
                Componente compIns = componentesForm.get(indexComp);

                Parametro paramNombreCamp = buscarParametrosPorNombre(parametros, "NOMBRE_CAMPO");
                Parametro paramClase = buscarParametrosPorNombre(parametros, "CLASE");
                Parametro paramTextVis = buscarParametrosPorNombre(parametros, "TEXTO_VIS");
                Parametro paramIndice = buscarParametrosPorNombre(parametros, "INDICE");
                Parametro paramAlinea = buscarParametrosPorNombre(parametros, "ALINEA");

                if (paramNombreCamp != null) {
                    compIns.setNombre(paramNombreCamp.getContenido());
                }

                if (paramClase != null) {
                    compIns.setClase(paramClase.getContenido());
                }
                if (paramTextVis != null) {
                    compIns.setTextoVisible(paramTextVis.getContenido());
                }
                if (paramAlinea != null) {
                    compIns.setAlineacion(paramAlinea.getContenido());
                }

                int indice = -1;
                boolean indiceCorrecto = true;
                if (paramIndice != null) {
                    String indiceStr = paramIndice.getContenido().replace("\"", "");
                    indice = Integer.parseInt(indiceStr);
                    indice = getNuevoIndice(componentesForm, indice);
                    if (indice != -1) {
                        compIns.setIndice("\"" + String.valueOf(indice) + "\"");
                    } else {
                        //Error el ya es usado
                        indiceCorrecto = false;
                        insertarError("EL INDICE DEL COMPONENTE A MODIFICAR ES YA ESTA EN USO");
                    }
                }
                Parametro paramTemp;
                paramTemp = buscarParametrosPorNombre(parametros, "URL");
                if (paramTemp != null) {
                    compIns.setUrl(paramTemp.getContenido());
                    paramTemp = null;
                }
                paramTemp = buscarParametrosPorNombre(parametros, "FILAS");
                if (paramTemp != null) {
                    compIns.setFilas(paramTemp.getContenido());
                    paramTemp = null;
                }
                paramTemp = buscarParametrosPorNombre(parametros, "COLUMNAS");
                if (paramTemp != null) {
                    compIns.setColumnas(paramTemp.getContenido());
                    paramTemp = null;
                }
                paramTemp = buscarParametrosPorNombre(parametros, "OPCIONES");
                if (paramTemp != null) {
                    compIns.setOpciones(paramTemp.getContenido());
                    paramTemp = null;
                }
                paramTemp = buscarParametrosPorNombre(parametros, "REQUE");
                if (paramTemp != null) {
                    compIns.setRequerido(paramTemp.getContenido());
                    paramTemp = null;
                }
                if (indiceCorrecto == true) {
                    componentesForm.add(compIns);
                    formMod.setComponentes(componentesForm);
                    insertarRespuesta("MODIFICAR_COMPONENTE", "Se modifico el componente: " + paramIdComp.getContenido().replace("\"", "")
                            + "al formulario: " + paramIdForm.getContenido().replace("\"", ""));
                }
            }

        } else {
            //El formulario al que se agregara no existe
            insertarError("EL FORMULARIO AL QUE SE MODIFICARA NO EXISTE");
        }
    }

    private int getNuevoIndice(List<Componente> componentesForm, int indiceBus) {
        int index = -1;
        List<String> indices = new ArrayList<>();
        for (Componente componente : componentesForm) {
            String strIndice = componente.getIndice();
            String indiceInt = strIndice.replace("\"", "");
            indices.add(indiceInt);
        }
        int indicesInt[] = new int[indices.size()];
        int cont = 0;
        for (String indice : indices) {
            indicesInt[cont] = Integer.parseInt(indice);
            cont++;
        }
        indicesInt = ordenarListaIndices(indicesInt, cont);
        if (indiceBus != -1) {
            for (int i = 0; i < cont; i++) {
                if (indicesInt[i] == indiceBus) {
                    return index;
                }
            }
            return indiceBus;
        } else {
            int indexN = 1;
            boolean estaUsado = false;
            for (int i = 0; i < cont; i++) {
                estaUsado = false;
                for (int j = 0; j < cont; j++) {
                    if (indicesInt[j] == indexN) {
                        estaUsado = true;
                        break;
                    }
                }
                if (estaUsado == false) {
                    return indexN;
                }
                indexN++;
            }
        }

        return index;
    }

    private int[] ordenarListaIndices(int[] indicesInt, int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                int primerInt = indicesInt[j];
                int sigInt = indicesInt[j + 1];
                if (primerInt > sigInt) {
                    int temp = primerInt;
                    indicesInt[j] = sigInt;
                    indicesInt[j + 1] = temp;
                }
            }

        }
        return indicesInt;
    }

    /**
     * Metodo que extrae el index del la lista de usuarios que se creo al leer
     * los datos de los usuarios guardados en el db
     *
     * @param nombre
     * @return
     */
    private int getIndexUsuarioPorNombre(String nombre) {
        int indexTemp = 0;
        for (Usuario usuario : todosUsuarios) {
            if (usuario.getUsuario().equals(nombre)) {
                return indexTemp;
            }
            indexTemp++;
        }
        return -1;
    }

    /**
     * Metodo que extrae el index del la lista de formulario que se creo al leer
     * los datos de los formularios guardados en el db
     *
     * @param id
     * @return
     */
    private int getIndexFormularioPorId(String id) {
        int indexTemp = 0;
        for (Formulario formulario : todosFormularios) {
            if (formulario.getIdForm().equals(id)) {
                return indexTemp;
            }
            indexTemp++;
        }
        return -1;
    }

    /**
     * Metodo que extrae el index de algun componente en la lista de componentes
     * de algun formulario
     *
     * @param formulario
     * @param id
     * @return
     */
    private int getIndexComponentePorId(Formulario formulario, String id) {
        int indexTemp = 0;
        List<Componente> componentes = formulario.getComponentes();
        for (Componente componente : componentes) {
            if (componente.getIdComp().equals(id)) {
                return indexTemp;
            }
            indexTemp++;
        }
        return -1;
    }

    /**
     * Metodo que busca y retorna un parametro segun su nombre en una lista de
     * parametros enviada
     *
     * @param parametros
     * @param nombre
     * @return
     */
    private Parametro buscarParametrosPorNombre(List<Parametro> parametros, String nombre) {
        for (Parametro parametro1 : parametros) {
            if (parametro1.getNombre().equals(nombre)) {
                return parametro1;
            }
        }
        return null;
    }

    /**
     * Metodo que obtiene a todos los usuarios insertados en la base de datos
     */
    private void getTodosUsuarios() {
        StringReader reader = new StringReader(this.usuarios);
        LexerUsers lexico = new LexerUsers(reader);
        ParserUsers parser = new ParserUsers(lexico);

        try {
            parser.parse();
            this.todosUsuarios = parser.getUsuarios();
            //System.out.println("");
        } catch (Exception ex) {
//            System.out.println("ERROR EN GET USUARIOS DE ACCIONES");
//            System.out.println("Causa: " + ex.getCause());
//            System.out.println("Causa2: " + ex.toString());
        }
    }

    private void generarDbUsuarios() {
        GuardarUsuarios guardarU = new GuardarUsuarios(this.todosUsuarios);
        this.usuarios = guardarU.getDbUsuarios();
    }

    private void getTodosFormularios() {
        StringReader reader = new StringReader(this.formularios);
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

    private void generarDbFormularios() {
        GuardarFormularios guardarF = new GuardarFormularios(this.todosFormularios);
        this.formularios = guardarF.getDbFormularios();
    }

    private void insertarError(String desc) {
        ErrorCom errorC = new ErrorCom("COMPILACION", desc, null, null, null);
        this.erroresAcciones.add(errorC);
    }

    private void insertarRespuesta(String tipo, String desc) {
        Respuesta resp = new Respuesta(tipo, desc);
        this.respuestasAcciones.add(resp);
    }

    public List<ErrorCom> getErroresAcciones() {
        return erroresAcciones;
    }

    public List<Respuesta> getRespuestasAcciones() {
        return respuestasAcciones;
    }

    public void setRespuestasAcciones(List<Respuesta> respuestasAcciones) {
        this.respuestasAcciones = respuestasAcciones;
    }

    private String pruebaForms() {
        return "db.formularios(\n"
                + "	{\n"
                + "		\"ID_FORMULARIO\": \"$id_form1\",\n"
                + "		\"TITULO\": \"Formulario para encuesta 1\",\n"
                + "		\"NOMBRE\": \"formulario_encuesta_1\",\n"
                + "		\"TEMA\": \"DARK\",\n"
                + "		\"USUARIO_CREACION\": \"juanito619\",\n"
                + "		\"FECHA_CREACION\" : \"2021-02-02\"\n"
                + "		)\n"
                + "	}\n"
                + "	\n"
                + ")";
    }
}
