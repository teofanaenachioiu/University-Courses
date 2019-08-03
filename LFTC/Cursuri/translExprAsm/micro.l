%{
/* 
* Lexical analyzer for the toy language 
*/
#include <string.h> /* for strcpy */

#include "attrib.h"
#include "micro.tab.h" /* token type definitions from .y file */

extern int lineno;  /* defined in micro.y */

%}

alpha   [A-Za-z]
digit   [0-9]
alphanum  [A-Za-z0-9]

%%
[ \t\r]        /*   ignore white spaces */
[\n]                    lineno++;
                       
"var"                   return VAR;
"integer"               return INTEGER;
"begin"                 return BEGINPAR;
"end"                   return ENDPAR;
"read"                  return READ;
"writeln"               return WRITE;

"("                     return LPAREN;
")"                     return RPAREN;
"{"                     return LBRACE;
"}"                     return RBRACE;
":="                     return ASSIGN;
";"                     return SEMICOLON;
":"                     return COLON;
"+"                     return PLUS;
"-"                     return MINUS;
","                     return COMMA;
"."                     return DOT;

{alpha}{alphanum}*      {
                         strcpy(yylval.varname,yytext);
                         return NAME;
                         }

{digit}+                {strcpy(yylval.varname,yytext);    
                        return NUMBER;
                        }



.                       { 
                        printf( "My: Illegal character \n %s \n on line #%d\n",
                                yytext,lineno);
                        
                        }

%%

int
yywrap() {
  return 1; /* tell scanner no further files need to be processed */
}

