package analizadoresRespuestas;
import java_cup.runtime.*; 
import analizadoresRespuestas.symR.*;
import java.util.List;
import java.util.ArrayList;
import objetos.ErrorCom;

%%

//Configuracion JFLEX
%public
%class LexerResp
%standalone
%line
%column
%cup

//Expresiones Regulares
blanco = [ ]

iniResps = "ini_respuestas"
iniResp = "ini_respuesta"
finResps = "fin_respuestas"
finResp = "fin_respuesta"

//NOMBRE DE SOLICITUDES
err = \""ERROR"\"
sol = \""RESPUESTA_SOLICITUD"\"


//NOMBRE DE LOS PARAMETROS
tipo = \""TIPO"\"
desc = \""DESCRIPCION"\"
lin = \""LINEA"\"
col = \""COLUMNA"\"
lex = \""LEXEMA"\"
logU = \""LOG_U"\"

contenido = \"[ \[\]><=\':|$)/(!&,.a-zA-Z0-9_-]+\"

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
    
    {iniResps} {return new Symbol(symR.INI_RESPS,yyline+1,yycolumn+1, yytext());}
    {iniResp} {return new Symbol(symR.INI_RESP,yyline+1,yycolumn+1, yytext());}
    {finResps} {return new Symbol(symR.FIN_RESPS,yyline+1,yycolumn+1, yytext());}
    {finResp} {return new Symbol(symR.FIN_RESP,yyline+1,yycolumn+1, yytext());}
    {err} {return new Symbol(symR.ERR,yyline+1,yycolumn+1, yytext());}
    {sol} {return new Symbol(symR.SOL,yyline+1,yycolumn+1, yytext());}
    {tipo} {return new Symbol(symR.TIPO,yyline+1,yycolumn+1, yytext());}
    {desc} {return new Symbol(symR.DESC,yyline+1,yycolumn+1, yytext());}
    {lin} {return new Symbol(symR.LIN,yyline+1,yycolumn+1, yytext());}
    {col} {return new Symbol(symR.COL,yyline+1,yycolumn+1, yytext());}
    {lex} {return new Symbol(symR.LEX,yyline+1,yycolumn+1, yytext());}
    {logU} {return new Symbol(symR.LOGU,yyline+1,yycolumn+1, yytext());}
    {contenido} {return new Symbol(symR.CONT,yyline+1,yycolumn+1, yytext());}
    "<" {return new Symbol(symR.MEN,yyline+1,yycolumn+1, yytext());}
    ">" {return new Symbol(symR.MAY,yyline+1,yycolumn+1, yytext());}
    ":" {return new Symbol(symR.DOSPUNT,yyline+1,yycolumn+1, yytext());}
    "{" {return new Symbol(symR.CORCHI,yyline+1,yycolumn+1, yytext());}
    "}" {return new Symbol(symR.CORCHD,yyline+1,yycolumn+1, yytext());}
    "," {return new Symbol(symR.COMA,yyline+1,yycolumn+1, yytext());}  
    "!" {return new Symbol(symR.EXCL,yyline+1,yycolumn+1, yytext());}  

    {palabra} {errorPalabra(yytext());}
    {blancos} {}


    

}

/* Error por cualquier otro simbolo*/
[^]
		{ error(yytext()); new Symbol(symF.error,yyline,yycolumn, yytext());}