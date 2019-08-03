%{
#include <stdio.h> /* for (f)printf() */
#include <stdlib.h> /* for exit() */
#include <string.h>
#include "attrib.h"
#include "codeASM.h"

int  lineno = 1; /* number of current source line */
extern int yylex(); /* lexical analyzer generated from lex.l */
extern char *yytext; /* last token, defined in lex.l  */

void
yyerror(char *s) {
  printf("\n \n \nMy error: \n");
  printf( "Syntax error on line #%d: %s\n", lineno, s);
  printf( "Last token was \"%s\"\n", yytext);
  exit(1);
}

int tempnr = 1;
void newTempName(char* s){
  sprintf(s,"temp%d",tempnr);
  tempnr++;
}

char datasegmentbuffer[500];
char tempbuffer[250];

void addTemps2String(char* str){
 int i;
 for(i=1;i<tempnr;i++){
    sprintf(tempbuffer,"temp%d dw ?\n",i);
    strcat(str,tempbuffer);
 }
}

int tempboolnr = 1;
void newTempBoolName(char* s){
  sprintf(s,"tempbool%d",tempnr);
  tempboolnr++;
}

char tempBoolBuffer[250];
void buildTempBoolBuffers(){
 int i;
 if (tempboolnr>1){
	strcpy(tempBoolBuffer,"\n");
	for(i=1;i<tempboolnr;i++){
		sprintf(tempBoolBuffer,"%stemp%d db ?\n",tempBoolBuffer,i);
		}
 }
}

%}


%union {
 char varname[10];
 attributes attrib;
 char strCode[250];
}


%token VAR
%token BEGINPAR
%token ENDPAR
%token INTEGER

%token LPAREN
%token RPAREN
%token LBRACE
%token RBRACE
%token ASSIGN
%token SEMICOLON
%token PLUS
%token MINUS
%token DECLARE
%token WRITE
%token READ

%token COMMA
%token COLON
%token DOT

%token <varname> NAME 
%token <varname> NUMBER 
%type <varname> variable
%type <attrib> term
%type <attrib> expression



%%
program         : 	BEGINPAR 
				statement_list 
				ENDPAR   
				DOT			
				;

				
statement_list  : statement_list SEMICOLON statement
                | statement
                ;
                                                                                   
statement       : assignment  	
                ;
                                        
				
assignment      : variable ASSIGN expression
                  {
                    printf("%s\n",$3.code);
                    printf("mov ax, %s\n",$3.varn);
                    printf("mov %s,ax\n",$1);
                   }
                ;


expression      : term {
                      strcpy($$.code,$1.code);
                      strcpy($$.varn,$1.varn);
                       }
                | expression PLUS term { 
                     newTempName($$.varn);
                     sprintf($$.code,"%s\n%s\n",$1.code,$3.code);
                     sprintf(tempbuffer,ADD_ASM_FORMAT,$1.varn,$3.varn,$$.varn);
                     strcat($$.code,tempbuffer);
                     }
                ;

term            : NUMBER {
                      strcpy($$.code,"");
                      strcpy($$.varn,$1); 
                      }
                | variable { 
                        strcpy($$.code,"");
                        strcpy($$.varn,$1); 
                      }
                | LPAREN expression RPAREN {
                        strcpy($$.code, $2.code);
                        strcpy($$.varn,$2.varn);
                      }
                     
                ;

variable        : NAME    {
                      strcpy($$,$1);
                  }
                ;
%%

int
main(int argc,char *argv[]) {
  return yyparse();
} 
