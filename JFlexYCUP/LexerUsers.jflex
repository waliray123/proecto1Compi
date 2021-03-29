package analizadores;
import java_cup.runtime.*; 
import analizadores.symU.*;
import java.util.List;
import java.util.ArrayList;
import objetos.ErrorCom;

%%

//Configuracion JFLEX
%public
%class LexerUsers
%standalone
%line
%column
%cup

//Expresiones Regulares
blanco = [ ]

//Inicio de expresion
iniExpr = "db.usuarios"

//Nombres de Paramentros
fechaCrea = \""FECHA_CREACION"\"
usuario = \""USUARIO"\"
pass = \""PASSWORD"\"




nombreP = \"[ :a-zA-Z0-9_-]+\"
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
    {iniExpr} {return new Symbol(symU.INI,yyline+1,yycolumn+1, yytext());}
    {fechaCrea} {return new Symbol(symU.FECHA_CREA,yyline+1,yycolumn+1, yytext());}
    {usuario} {return new Symbol(symU.USUARIO,yyline+1,yycolumn+1, yytext());}
    {pass} {return new Symbol(symU.PASS,yyline+1,yycolumn+1, yytext());}
    {nombreP} {return new Symbol(symU.NOMBREP,yyline+1,yycolumn+1, yytext());}    
    "(" {return new Symbol(symU.PARI,yyline+1,yycolumn+1, yytext());}
    ")" {return new Symbol(symU.PARD,yyline+1,yycolumn+1, yytext());}
    ":" {return new Symbol(symU.DOSPUNT,yyline+1,yycolumn+1, yytext());}
    "{" {return new Symbol(symU.CORCHI,yyline+1,yycolumn+1, yytext());}
    "}" {return new Symbol(symU.CORCHD,yyline+1,yycolumn+1, yytext());}
    "," {return new Symbol(symU.COMA,yyline+1,yycolumn+1, yytext());}

    {palabra} {errorPalabra(yytext());}
    {blancos} {}
    {blanco} {}

    

}

/* Error por cualquier otro simbolo*/
[^]
		{ error(yytext()); new Symbol(sym.error,yyline,yycolumn, yytext());}
