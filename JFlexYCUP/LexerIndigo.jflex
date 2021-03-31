package analizadores;
import java_cup.runtime.*; 
import analizadores.sym.*;
import java.util.List;
import java.util.ArrayList;
import objetos.ErrorCom;

%%

//Configuracion JFLEX
%public
%class LexerIndigo
%standalone
%line
%column
%cup

//Expresiones Regulares
blanco = [ ]

//Expresiones XML
iniSols = ("i"|"I")("n"|"N")("i"|"I")"_"("s"|"S")("o"|"O")("l"|"L")("i"|"I")("c"|"C")("i"|"I")("t"|"T")("u"|"U")("d"|"D")("e"|"E")("s"|"S")
iniSol = ("i"|"I")("n"|"N")("i"|"I")"_"("s"|"S")("o"|"O")("l"|"L")("i"|"I")("c"|"C")("i"|"I")("t"|"T")("u"|"U")("d"|"D")

finSols = ("f"|"F")("i"|"I")("n"|"N")"_"("s"|"S")("o"|"O")("l"|"L")("i"|"I")("c"|"C")("i"|"I")("t"|"T")("u"|"U")("d"|"D")("e"|"E")("s"|"S")
finSol = ("f"|"F")("i"|"I")("n"|"N")"_"("s"|"S")("o"|"O")("l"|"L")("i"|"I")("c"|"C")("i"|"I")("t"|"T")("u"|"U")("d"|"D")

//Nombre de solicitudes
crearU = \"{blanco}*"CREAR_USUARIO"{blanco}*\"
modU = \"{blanco}*"MODIFICAR_USUARIO"{blanco}*\"
elimU = \"{blanco}*"ELIMINAR_USUARIO"{blanco}*\"
logU = \"{blanco}*"LOGIN_USUARIO"{blanco}*\"
nuevoForm = \"{blanco}*"NUEVO_FORMULARIO"{blanco}*\"
elimForm = \"{blanco}*"ELIMINAR_FORMULARIO"{blanco}*\"
modForm = \"{blanco}*"MODIFICAR_FORMULARIO"{blanco}*\"
agreComp = \"{blanco}*"AGREGAR_COMPONENTE"{blanco}*\"
elimComp = \"{blanco}*"ELIMINAR_COMPONENTE"{blanco}*\"
modComp = \"{blanco}*"MODIFICAR_COMPONENTE"{blanco}*\"
consult = \"{blanco}*"CONSULTAR_DATOS"{blanco}*\"

//Complementos de solicitud
credenU = \""CREDENCIALES_USUARIO"\"
paramsForm = \""PARAMETROS_FORMULARIO"\"
paramsComp = \""PARAMETROS_COMPONENTE"\"
consults = \""CONSULTAS"\"
//consultan = \""CONSULTA-"[0-9]\"
consultan = \""CONSULTA"\"

//Nombres de Paramentros
usuarioAnti = \""USUARIO_ANTIGUO"\"
usuarioNuev = \""USUARIO_NUEVO"\"
nuevoPass = \""NUEVO_PASSWORD"\"
fechaMod = \""FECHA_MODIFICACION"\"
usuario = \""USUARIO"\"
pass = \""PASSWORD"\"

titul = \""TITULO"\"
nombre = \""NOMBRE"\"
tema = \""TEMA"\"
usuarioCrea = \""USUARIO_CREACION"\"
fechaCrea = \""FECHA_CREACION"\"
id2 = \""ID"\"
nombreCamp = \""NOMBRE_CAMPO"\"
form = \""FORMULARIO"\"
clase = \""CLASE"\"
indice = \""INDICE"\"
textVi = \""TEXTO_VISIBLE"\"
alinea = \""ALINEACION"\"
reque = \""REQUERIDO"\"
opciones = \""OPCIONES"\"
filas = \""FILAS"\"
columnas = \""COLUMNAS"\"
url = \""URL"\"

//NOMBRE DE PARAMETROS ESPECIALES
campoText = \""CAMPO_TEXTO"\"
areaText = \""AREA_TEXTO"\"
checkBox = \""CHECKBOX"\"
radioB = \""RADIO"\"
fichero = \""FICHERO"\"
imagen = \""IMAGEN"\"
comboB = \""COMBO"\"
boton = \""BOTON"\"
centro = \""CENTRO"\"
izquierda = \""IZQUIERDA"\"
derecha = \""DERECHA"\"
just = \""JUSTIFICAR"\"

//NOMBRE DE LOS TEMAS
blue = \""BLUE"\"
dark = \""DARK"\"
white = \""WHITE"\"

id = \"("_"|"$"|"-")[a-z0-9A-Z_|$]+\"
nombreP = \"[ :/.a-zA-Z0-9_-]+\"
nombreOpciones = \"[ a-zA-Z0-9|]+\"
contCons = \"[ $\[\]><=\':/.a-zA-Z0-9_-]+\"
palabra = [a-zA-Z0-9]+
blancos = [ \r\t\b\f\n]+



//Codigo Incrustado
%{
    private List<ErrorCom> erroresCom;

    private void error(String lexeme) {
        erroresCom.add(new ErrorCom("Lexico","Simbolo no existe en el lenguaje",String.valueOf(yyline+1),String.valueOf(yycolumn+1),lexeme));
    }    

    private void errorPalabra(String lexeme) {
        erroresCom.add(new ErrorCom("Lexico","Palabra no existe en el lenguaje",String.valueOf(yyline+1),String.valueOf(yycolumn+1),lexeme));
    }

    private void errorNOMP(String lexeme) {
        erroresCom.add(new ErrorCom("Lexico","es un nombrep",String.valueOf(yyline+1),String.valueOf(yycolumn+1),lexeme));
    }

    private void errorNOMO(String lexeme) {
        erroresCom.add(new ErrorCom("Lexico","es un nombreopciones",String.valueOf(yyline+1),String.valueOf(yycolumn+1),lexeme));
    }

    private void errorID(String lexeme) {
        erroresCom.add(new ErrorCom("Lexico","es un ID",String.valueOf(yyline+1),String.valueOf(yycolumn+1),lexeme));
    }

    private void errorFINS(String lexeme) {
        erroresCom.add(new ErrorCom("Lexico","es un fin sols",String.valueOf(yyline+1),String.valueOf(yycolumn+1),lexeme));
    }

    public List<ErrorCom> getErroresCom() {
        return erroresCom;
    }


%}

%init{
    erroresCom = new ArrayList<>();
%init}

%%


//Reglas Lexicas
<YYINITIAL> {
    {iniSols} {return new Symbol(sym.INISOLS,yyline+1,yycolumn+1, yytext());}
    {iniSol} {return new Symbol(sym.INISOL,yyline+1,yycolumn+1, yytext());}
    {finSol} {return new Symbol(sym.FINSOL,yyline+1,yycolumn+1, yytext());}
    {finSols} {return new Symbol(sym.FINSOLS,yyline+1,yycolumn+1, yytext());}
    {crearU} {return new Symbol(sym.CREAR_U,yyline+1,yycolumn+1, yytext());}
    {modU} {return new Symbol(sym.MOD_U,yyline+1,yycolumn+1, yytext());}
    {elimU} {return new Symbol(sym.ELIM_U,yyline+1,yycolumn+1, yytext());}
    {logU} {return new Symbol(sym.LOG_U,yyline+1,yycolumn+1, yytext());}
    {nuevoForm} {return new Symbol(sym.NUEVO_FORM,yyline+1,yycolumn+1, yytext());}
    {elimForm} {return new Symbol(sym.ELIM_FORM,yyline+1,yycolumn+1, yytext());}
    {modForm} {return new Symbol(sym.MOD_FORM,yyline+1,yycolumn+1, yytext());}
    {agreComp} {return new Symbol(sym.AGRE_COMP,yyline+1,yycolumn+1, yytext());}
    {elimComp} {return new Symbol(sym.ELIM_COMP,yyline+1,yycolumn+1, yytext());}
    {modComp} {return new Symbol(sym.MOD_COMP,yyline+1,yycolumn+1, yytext());}
    {consult} {return new Symbol(sym.CONSULT,yyline+1,yycolumn+1, yytext());}
    {credenU} {return new Symbol(sym.CREDEN_U,yyline+1,yycolumn+1, yytext());}
    {paramsForm} {return new Symbol(sym.PARAMS_FORM,yyline+1,yycolumn+1, yytext());}
    {paramsComp} {return new Symbol(sym.PARAMS_COMP,yyline+1,yycolumn+1, yytext());}
    {consults} {return new Symbol(sym.CONSULTS,yyline+1,yycolumn+1, yytext());}
    {consultan} {return new Symbol(sym.CONSULTAN,yyline+1,yycolumn+1, yytext());}
    {usuarioAnti} {return new Symbol(sym.USUARIO_ANTI,yyline+1,yycolumn+1, yytext());}
    {usuarioNuev} {return new Symbol(sym.USUARIO_NUEVO,yyline+1,yycolumn+1, yytext());}
    {nuevoPass} {return new Symbol(sym.NUEVO_PASS,yyline+1,yycolumn+1, yytext());}
    {usuario} {return new Symbol(sym.USUARIO,yyline+1,yycolumn+1, yytext());}
    {pass} {return new Symbol(sym.PASS,yyline+1,yycolumn+1, yytext());}
    {titul} {return new Symbol(sym.TITUL,yyline+1,yycolumn+1, yytext());}
    {nombre} {return new Symbol(sym.NOMBRE,yyline+1,yycolumn+1, yytext());}
    {tema} {return new Symbol(sym.TEMA,yyline+1,yycolumn+1, yytext());}
    {usuarioCrea} {return new Symbol(sym.USUARIO_CREA,yyline+1,yycolumn+1, yytext());}
    {fechaCrea} {return new Symbol(sym.FECHA_CREA,yyline+1,yycolumn+1, yytext());}
    {id2} {return new Symbol(sym.ID2,yyline+1,yycolumn+1, yytext());}
    {nombreCamp} {return new Symbol(sym.NOMBRE_CAMPO,yyline+1,yycolumn+1, yytext());}
    {form} {return new Symbol(sym.FORM,yyline+1,yycolumn+1, yytext());}
    {clase} {return new Symbol(sym.CLASE,yyline+1,yycolumn+1, yytext());}
    {indice} {return new Symbol(sym.INDICE,yyline+1,yycolumn+1, yytext());}
    {textVi} {return new Symbol(sym.TEXTO_VIS,yyline+1,yycolumn+1, yytext());}
    {alinea} {return new Symbol(sym.ALINEA,yyline+1,yycolumn+1, yytext());}
    {reque} {return new Symbol(sym.REQUE,yyline+1,yycolumn+1, yytext());}
    {opciones} {return new Symbol(sym.OPCIONES,yyline+1,yycolumn+1, yytext());}
    {filas} {return new Symbol(sym.FILAS,yyline+1,yycolumn+1, yytext());}
    {columnas} {return new Symbol(sym.COLUMNAS,yyline+1,yycolumn+1, yytext());}
    {url} {return new Symbol(sym.URL,yyline+1,yycolumn+1, yytext());}
    {fechaMod} {return new Symbol(sym.FECHA_MOD,yyline+1,yycolumn+1, yytext());}
    {campoText} {return new Symbol(sym.CAMPO_TEXT,yyline+1,yycolumn+1, yytext());}
    {areaText} {return new Symbol(sym.AREA_TEXT,yyline+1,yycolumn+1, yytext());}
    {checkBox} {return new Symbol(sym.CHECK_BOX,yyline+1,yycolumn+1, yytext());}
    {radioB} {return new Symbol(sym.RADIO,yyline+1,yycolumn+1, yytext());}
    {fichero} {return new Symbol(sym.FICHERO,yyline+1,yycolumn+1, yytext());}
    {imagen} {return new Symbol(sym.IMAGEN,yyline+1,yycolumn+1, yytext());}
    {comboB} {return new Symbol(sym.COMBO,yyline+1,yycolumn+1, yytext());}
    {boton} {return new Symbol(sym.BOTON,yyline+1,yycolumn+1, yytext());}
    {centro} {return new Symbol(sym.CENTRO,yyline+1,yycolumn+1, yytext());}
    {izquierda} {return new Symbol(sym.IZQUIERDA,yyline+1,yycolumn+1, yytext());}
    {derecha} {return new Symbol(sym.DERECHA,yyline+1,yycolumn+1, yytext());}
    {just} {return new Symbol(sym.JUSTIFICAR,yyline+1,yycolumn+1, yytext());}
    {blue} {return new Symbol(sym.BLUE,yyline+1,yycolumn+1, yytext());}
    {dark} {return new Symbol(sym.DARK,yyline+1,yycolumn+1, yytext());}
    {white} {return new Symbol(sym.WHITE,yyline+1,yycolumn+1, yytext());}
    {id} {return new Symbol(sym.ID,yyline+1,yycolumn+1, yytext());}
    {nombreP} {return new Symbol(sym.NOMBREP,yyline+1,yycolumn+1, yytext());}
    {nombreOpciones} {return new Symbol(sym.NOMBRE_OPCION,yyline+1,yycolumn+1, yytext());}
    {contCons} {return new Symbol(sym.CONTCONS,yyline+1,yycolumn+1, yytext());}
    ":" {return new Symbol(sym.DOSPUNT,yyline+1,yycolumn+1, yytext());}
    "!" {return new Symbol(sym.EXCL,yyline+1,yycolumn+1, yytext());}
    "<" {return new Symbol(sym.MEN,yyline+1,yycolumn+1, yytext());}
    ">" {return new Symbol(sym.MAY,yyline+1,yycolumn+1, yytext());}
    "{" {return new Symbol(sym.CORCHI,yyline+1,yycolumn+1, yytext());}
    "}" {return new Symbol(sym.CORCHD,yyline+1,yycolumn+1, yytext());}
    "[" {return new Symbol(sym.CORCHCUADI,yyline+1,yycolumn+1, yytext());}
    "]" {return new Symbol(sym.CORCHCUADD,yyline+1,yycolumn+1, yytext());}
    "_" {return new Symbol(sym.GUIONB,yyline+1,yycolumn+1, yytext());}
    "-" {return new Symbol(sym.GUIONM,yyline+1,yycolumn+1, yytext());}
    "$" {return new Symbol(sym.DOLL,yyline+1,yycolumn+1, yytext());}
    "," {return new Symbol(sym.COMA,yyline+1,yycolumn+1, yytext());}
    //"\"" {return new Symbol(sym.COMILLADOB,yyline+1,yycolumn+1, yytext());}
    "\'" {return new Symbol(sym.COMILLASIM,yyline+1,yycolumn+1, yytext());}    

    {palabra} {errorPalabra(yytext());}
    {blancos} {}


    

}

/* Error por cualquier otro simbolo*/
[^]
		{ error(yytext()); new Symbol(sym.error,yyline,yycolumn, yytext());}
