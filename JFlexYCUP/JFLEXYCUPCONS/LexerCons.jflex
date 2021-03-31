package analizadores;
import java_cup.runtime.*; 
import analizadores.symC.*;
import java.util.List;
import java.util.ArrayList;
import objetos.ErrorCom;

%%

//Configuracion JFLEX
%public
%class LexerCons
%standalone
%line
%column
%cup

//Expresiones Regulares
blanco = [ ]

//Nombre de solicitudes

//Complementos de solicitud
selectT = "SELECT TO FORM"
where = "WHERE"
and = "AND"
or = "OR"
not = "NOT"
num = [0-9]
id = ("_"|"$"|"-")[a-z0-9A-Z_|$]+
cadena = \'[ :/.a-zA-Z0-9_-]+\'    
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
    
    {nombreP} {return new Symbol(sym.NOMBREP,yyline+1,yycolumn+1, yytext());}
    {nombreOpciones} {return new Symbol(sym.NOMBRE_OPCION,yyline+1,yycolumn+1, yytext());}
    {contCons} {return new Symbol(sym.CONTCONS,yyline+1,yycolumn+1, yytext());}
    ":" {return new Symbol(sym.DOSPUNT,yyline+1,yycolumn+1, yytext());}
    "!" {return new Symbol(sym.EXCL,yyline+1,yycolumn+1, yytext());}
    "<" {return new Symbol(sym.MEN,yyline+1,yycolumn+1, yytext());}
    ">" {return new Symbol(sym.MAY,yyline+1,yycolumn+1, yytext());}
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
