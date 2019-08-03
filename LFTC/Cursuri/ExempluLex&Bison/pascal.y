%{
#include <stdio.h>
#include <stdlib.h>
#define YYDEBUG 1

#define TIP_INT 1
#define TIP_REAL 2
#define TIP_CAR 3

double stiva[20];
int sp;

void push(double x)
{ stiva[sp++]=x; }

double pop()
{ return stiva[--sp]; }

%}

%union {
  	int l_val;
	char *p_val;
}

%token BEGINN
%token CONST
%token DO
%token ELSE
%token END
%token IF
%token PROGRAM
%token READ
%token WRITE
%token THEN
%token VAR
%token WHILE
%token ARRAY
%token OFF
%token FOR
%token TO


%token ID
%token <p_val> CONST_INT
%token <p_val> CONST_REAL
%token <p_val> CONST_CAR
%token CONST_SIR

%token CHAR
%token INTEGER
%token REAL
%token BOOLEAN

%token ATRIB

%token LESS
%token GREATER

%left '+' '-'
%left DIV MOD '*' '/'
%left OR
%left AND
%left NOT

%type <val> constanta

%%
prog_sursa:	PROGRAM ID ';' bloc '.'
		;
bloc:		 sect_var instr_comp
		;

sect_var:	/* empty */
		| VAR lista_var
		;
lista_var:	decl_var
		| lista_var decl_var
		;

decl_var:	lista_id ':' tip ';'
		;
lista_id:	ID
		| lista_id ',' ID
		;
tip:		tip_simplu
		;
tip_simplu:	INTEGER
		| REAL
		| CHAR
                | BOOLEAN 
		;

constanta:	CONST_INT	{
			
			push(atoi($1));
				}
		| CONST_REAL	{
			
			push(atof($1));
				}
		| CONST_CAR	{
			
			push((double)$1[0]);
				}
           	;
instr_comp:	BEGINN lista_instr END
		;
lista_instr:	instr
		| lista_instr ';' instr
		;
instr:		/* empty */
		| instr_atrib
		| instr_if
		| instr_while
		| instr_comp
		| instr_read
                | instr_write
		| instr_for
		;
instr_atrib:	variabila ATRIB expresie
		;
variabila:	ID
		| ID '[' expresie ']'
		;
expresie:	factor
		| expresie '+' expresie
		| expresie '-' expresie
		| expresie '*' expresie
		| expresie '/' expresie
		;
factor:		ID
		| constanta {}
		| ID '(' lista_expr ')'
		| '(' expresie ')'
		;
lista_expr:	expresie
		| lista_expr ',' expresie
		;
instr_if:	IF conditie THEN instr ramura_else
		;
ramura_else:	/* empty */
		ELSE instr
		;
conditie:	expr_logica
		| expresie op_rel expresie
		;
expr_logica:	factor_logic
         	| expr_logica AND expr_logica
		| expr_logica OR expr_logica
		;
factor_logic:	'(' conditie ')'
		| NOT factor_logic
		;
op_rel:		'='
		| '<'
		| '>'
		| LESS
		| GREATER
		;
instr_while:	WHILE conditie DO instr
		;
instr_write:	WRITE '(' lista_elem ')'
		;
lista_elem:	element
		| lista_elem ',' element
		;
element:	expresie
		| CONST_SIR
		;
instr_read:	READ '(' lista_variab ')'
		;
lista_variab:	variabila
		| lista_variab ',' variabila
		;
instr_for:      FOR conditiefor DO instr
conditiefor:    ID ATRIB expresie TO expresie  
                ;  

%%

yyerror(char *s)
{
  printf("%s\n", s);
}

extern FILE *yyin;

main(int argc, char **argv)
{
  if(argc>1) yyin = fopen(argv[1], "r");
  if((argc>2)&&(!strcmp(argv[2],"-d"))) yydebug = 1;
  if(!yyparse()) fprintf(stderr,"\tO.K.\n");
}


