#! /bin/bash 
echo "INICIANDO COMPILACION JFLEX "
java -jar jflex-full-1.8.2.jar LexerCons.jflex
echo "INICIANDO COMPILACION JAVA CUP"
java -jar java-cup-11b.jar -parser ParserCons -symbols symC ParserCons.cup