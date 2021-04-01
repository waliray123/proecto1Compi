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

//Complementos de solicitud
select = "SELECT TO FORM"
where = "WHERE"
and = "AND"
or = "OR"
not = "NOT"
num = [0-9]
id = ("_"|"$"|"-")[a-z0-9A-Z_|$]+
cadena = \'[ :/.a-zA-Z0-9_-]+\'
parametro = [:/.a-zA-Z0-9_-]+    
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
        erroresCom.add(new ErrorCom("Lexico","es un Parametro",String.valueOf(yyline+1),String.valueOf(yycolumn+1),lexeme));
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
    
    "->" {return new Symbol(symC.FLECHA,yyline+1,yycolumn+1, yytext());}      
    "<>" {return new Symbol(symC.DESIG,yyline+1,yycolumn+1, yytext());}    
    "<=" {return new Symbol(symC.MENIG,yyline+1,yycolumn+1, yytext());}    
    ">=" {return new Symbol(symC.MAYIG,yyline+1,yycolumn+1, yytext());}
    "<" {return new Symbol(symC.MEN,yyline+1,yycolumn+1, yytext());}
    ">" {return new Symbol(symC.MAY,yyline+1,yycolumn+1, yytext());}
    "=" {return new Symbol(symC.IGUAL,yyline+1,yycolumn+1, yytext());}    
    "[" {return new Symbol(symC.CORCHCUADI,yyline+1,yycolumn+1, yytext());}
    "]" {return new Symbol(symC.CORCHCUADD,yyline+1,yycolumn+1, yytext());}
    "," {return new Symbol(symC.COMA,yyline+1,yycolumn+1, yytext());}
    {select} {return new Symbol(symC.SELECTT,yyline+1,yycolumn+1, yytext());}
    {where} {return new Symbol(symC.WHERE,yyline+1,yycolumn+1, yytext());}    
    {and} {return new Symbol(symC.AND,yyline+1,yycolumn+1, yytext());}    
    {or} {return new Symbol(symC.OR,yyline+1,yycolumn+1, yytext());}    
    {not} {return new Symbol(symC.NOT,yyline+1,yycolumn+1, yytext());}        
    {num} {return new Symbol(symC.NUM,yyline+1,yycolumn+1, yytext());}    
    {id} {return new Symbol(symC.ID,yyline+1,yycolumn+1, yytext());}    
    {cadena} {return new Symbol(symC.CADENA,yyline+1,yycolumn+1, yytext());}    
    {parametro} {return new Symbol(symC.PARAM,yyline+1,yycolumn+1, yytext());}  
                     
    {blancos} {}


    

}

/* Error por cualquier otro simbolo*/
[^]
		{ error(yytext()); new Symbol(sym.error,yyline,yycolumn, yytext());}
