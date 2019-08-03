/* A Bison parser, made by GNU Bison 2.7.  */

/* Bison interface for Yacc-like parsers in C
   
      Copyright (C) 1984, 1989-1990, 2000-2012 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

#ifndef YY_YY_Y_TAB_H_INCLUDED
# define YY_YY_Y_TAB_H_INCLUDED
/* Enabling traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     BEGINN = 258,
     CONST = 259,
     DO = 260,
     ELSE = 261,
     END = 262,
     IF = 263,
     PROGRAM = 264,
     READ = 265,
     WRITE = 266,
     THEN = 267,
     VAR = 268,
     WHILE = 269,
     ARRAY = 270,
     OFF = 271,
     FOR = 272,
     TO = 273,
     ID = 274,
     CONST_INT = 275,
     CONST_REAL = 276,
     CONST_CAR = 277,
     CONST_SIR = 278,
     CHAR = 279,
     INTEGER = 280,
     REAL = 281,
     BOOLEAN = 282,
     ATRIB = 283,
     LESS = 284,
     GREATER = 285,
     MOD = 286,
     DIV = 287,
     OR = 288,
     AND = 289,
     NOT = 290
   };
#endif
/* Tokens.  */
#define BEGINN 258
#define CONST 259
#define DO 260
#define ELSE 261
#define END 262
#define IF 263
#define PROGRAM 264
#define READ 265
#define WRITE 266
#define THEN 267
#define VAR 268
#define WHILE 269
#define ARRAY 270
#define OFF 271
#define FOR 272
#define TO 273
#define ID 274
#define CONST_INT 275
#define CONST_REAL 276
#define CONST_CAR 277
#define CONST_SIR 278
#define CHAR 279
#define INTEGER 280
#define REAL 281
#define BOOLEAN 282
#define ATRIB 283
#define LESS 284
#define GREATER 285
#define MOD 286
#define DIV 287
#define OR 288
#define AND 289
#define NOT 290



#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
{
/* Line 2058 of yacc.c  */
#line 21 "pascal.y"

  	int l_val;
	char *p_val;


/* Line 2058 of yacc.c  */
#line 133 "y.tab.h"
} YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

extern YYSTYPE yylval;

#ifdef YYPARSE_PARAM
#if defined __STDC__ || defined __cplusplus
int yyparse (void *YYPARSE_PARAM);
#else
int yyparse ();
#endif
#else /* ! YYPARSE_PARAM */
#if defined __STDC__ || defined __cplusplus
int yyparse (void);
#else
int yyparse ();
#endif
#endif /* ! YYPARSE_PARAM */

#endif /* !YY_YY_Y_TAB_H_INCLUDED  */
