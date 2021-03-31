package analizadores;
import java_cup.runtime.*; 
import analizadores.symF.*;
import java.util.List;
import java.util.ArrayList;
import objetos.ErrorCom;

%%

//Configuracion JFLEX
%public
%class LexerForms
%standalone
%line
%column
%cup

//Expresiones Regulares
blanco = [ ]

inicio = "db.formularios"

//Nombres de Paramentros
idForm = \""ID_FORMULARIO"\"
estructura = \""ESTRUCTURA"\"
titul = \""TITULO"\"
nombre = \""NOMBRE"\"
tema = \""TEMA"\"
usuarioCrea = \""USUARIO_CREACION"\"
fechaCrea = \""FECHA_CREACION"\"
id2 = \""ID_COMPONENTE"\"
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
campoText = \""CAMPO_TEXT"\"
areaText = \""AREA_TEXT"\"
checkBox = \""CHECK_BOX"\"
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
nombreP = \"[ :./a-zA-Z0-9_-]+\"
nombreOpciones = \"[ a-zA-Z0-9|]+\"
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
    {inicio} {return new Symbol(symF.INI,yyline+1,yycolumn+1, yytext());}
    {idForm} {return new Symbol(symF.ID_FORM,yyline+1,yycolumn+1, yytext());}
    {estructura} {return new Symbol(symF.ESTRUCT,yyline+1,yycolumn+1, yytext());}
    {titul} {return new Symbol(symF.TITUL,yyline+1,yycolumn+1, yytext());}
    {nombre} {return new Symbol(symF.NOMBRE,yyline+1,yycolumn+1, yytext());}
    {tema} {return new Symbol(symF.TEMA,yyline+1,yycolumn+1, yytext());}
    {usuarioCrea} {return new Symbol(symF.USUARIO_CREA,yyline+1,yycolumn+1, yytext());}
    {fechaCrea} {return new Symbol(symF.FECHA_CREA,yyline+1,yycolumn+1, yytext());}
    {id2} {return new Symbol(symF.ID_COMP,yyline+1,yycolumn+1, yytext());}
    {nombreCamp} {return new Symbol(symF.NOMBRE_CAMPO,yyline+1,yycolumn+1, yytext());}
    {form} {return new Symbol(symF.FORM,yyline+1,yycolumn+1, yytext());}
    {clase} {return new Symbol(symF.CLASE,yyline+1,yycolumn+1, yytext());}
    {indice} {return new Symbol(symF.INDICE,yyline+1,yycolumn+1, yytext());}
    {textVi} {return new Symbol(symF.TEXTO_VIS,yyline+1,yycolumn+1, yytext());}
    {alinea} {return new Symbol(symF.ALINEA,yyline+1,yycolumn+1, yytext());}
    {reque} {return new Symbol(symF.REQUE,yyline+1,yycolumn+1, yytext());}
    {opciones} {return new Symbol(symF.OPCIONES,yyline+1,yycolumn+1, yytext());}
    {filas} {return new Symbol(symF.FILAS,yyline+1,yycolumn+1, yytext());}
    {columnas} {return new Symbol(symF.COLUMNAS,yyline+1,yycolumn+1, yytext());}
    {url} {return new Symbol(symF.URL,yyline+1,yycolumn+1, yytext());}    
    {campoText} {return new Symbol(symF.CAMPO_TEXT,yyline+1,yycolumn+1, yytext());}
    {areaText} {return new Symbol(symF.AREA_TEXT,yyline+1,yycolumn+1, yytext());}
    {checkBox} {return new Symbol(symF.CHECK_BOX,yyline+1,yycolumn+1, yytext());}
    {radioB} {return new Symbol(symF.RADIO,yyline+1,yycolumn+1, yytext());}
    {fichero} {return new Symbol(symF.FICHERO,yyline+1,yycolumn+1, yytext());}
    {imagen} {return new Symbol(symF.IMAGEN,yyline+1,yycolumn+1, yytext());}
    {comboB} {return new Symbol(symF.COMBO,yyline+1,yycolumn+1, yytext());}
    {boton} {return new Symbol(symF.BOTON,yyline+1,yycolumn+1, yytext());}
    {centro} {return new Symbol(symF.CENTRO,yyline+1,yycolumn+1, yytext());}
    {izquierda} {return new Symbol(symF.IZQUIERDA,yyline+1,yycolumn+1, yytext());}
    {derecha} {return new Symbol(symF.DERECHA,yyline+1,yycolumn+1, yytext());}
    {just} {return new Symbol(symF.JUSTIFICAR,yyline+1,yycolumn+1, yytext());}
    {blue} {return new Symbol(symF.BLUE,yyline+1,yycolumn+1, yytext());}
    {dark} {return new Symbol(symF.DARK,yyline+1,yycolumn+1, yytext());}
    {white} {return new Symbol(symF.WHITE,yyline+1,yycolumn+1, yytext());}
    {id} {return new Symbol(symF.ID,yyline+1,yycolumn+1, yytext());}
    {nombreP} {return new Symbol(symF.NOMBREP,yyline+1,yycolumn+1, yytext());}
    {nombreOpciones} {return new Symbol(symF.NOMBRE_OPCION,yyline+1,yycolumn+1, yytext());}
    ":" {return new Symbol(symF.DOSPUNT,yyline+1,yycolumn+1, yytext());}
    "{" {return new Symbol(symF.CORCHI,yyline+1,yycolumn+1, yytext());}
    "}" {return new Symbol(symF.CORCHD,yyline+1,yycolumn+1, yytext());}
    "(" {return new Symbol(symF.PARI,yyline+1,yycolumn+1, yytext());}
    ")" {return new Symbol(symF.PARD,yyline+1,yycolumn+1, yytext());}
    "," {return new Symbol(symF.COMA,yyline+1,yycolumn+1, yytext());}  

    {palabra} {errorPalabra(yytext());}
    {blancos} {}


    

}

/* Error por cualquier otro simbolo*/
[^]
		{ error(yytext()); new Symbol(symF.error,yyline,yycolumn, yytext());}