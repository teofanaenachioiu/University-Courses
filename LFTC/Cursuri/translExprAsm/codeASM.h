#ifndef CODEASM_H
#define CODEASM_H

#define INCEPUT "\
ASSUME cs:code,ds:data \n\n\
"

#define DATASEGINCEPUT "\
; data - segmentul de date in care vom defini variabilele \n\
data SEGMENT \n\
"

#define DATASEGSFARSIT "\
\n\
data ENDS\n\n\
"

#define TEXTSEGINCEPUT "\
;code - numele segmentului de cod \n\
code SEGMENT\n\n\
"


#define TEXTSEGMIJLOC "\
start:\n\
mov ax, data\n\
mov ds, ax\n\n\
;urmeaza instructiunile programului nostru \n\
"


#define TEXTSEGSFARSIT "\
\n\
; e apelata intreruperea 21, cu ah incarcat cu 4C \n\
; adica sfarsitul executiei cu succes \n\
mov ax,4C00h\n\
int 21h\n\
code ENDS\n\n\n\
"

#define SFARSIT "\
END start\n\n\n\
"

#define ADD_ASM_FORMAT "\
mov ax, %s \n\
add ax, %s \n\
mov %s, ax \n\
" 

#define INT_ASM_FORMAT "\
%s dw ? \n\
"

#define PROC_AFISBAZA10 "\
AfisBaza10   PROC\n\
; definim o procedura care afiseaza registrul ax in baza 10 \n\
cmp ax, 0\n\
jge pozitiv\n\
\n\
; altfel, numarul e negativ si afisam caracterul '-' cu ajutorul functiei 02h a int 21h\n\
push ax\n\
mov ah, 02h\n\
mov dl, '-'\n\
int 21h\n\
pop ax\n\
neg ax              ; ax:= valoarea absoluta a lui ax\n\
\n\
pozitiv:\n\
\n\
; pentru a nu avea depasiri, vom imparti totdeauna pe DX:AX la 10. Tot \n\
; impartim la 10 si punem resturile obtinute (adica cifrele) pe stiva pentru \n\
; a inversa, in final, ordinea lor.\n\
\n\
mov cx, 0 ; in cx, vom retine numarul de cifre puse pe stiva\n\
\n\
repeta:\n\
 mov dx, 0\n\
 div zece\n\
 push dx\n\
 inc cx\n\
 cmp ax, 0\n\
ja repeta ; daca catul>0 jmp repeta\n\
\n\
; acuma vom scoate fiecare cifra de pe stiva si o vom afisa u ajutorul \n\
; functiei 02h a int 21h\n\
\n\
scoate:\n\
 pop dx\n\
 ; restul, adica cifra, se afla in DL (deoarece este un numar intre 0 si ;10)\n\
 add dl, '0' ; obtinem caracterul corespunzator cifrei\n\
 mov ah, 02h\n\
 int 21h\n\
loop scoate\n\
\n\
 ; sarim la linie noua pe ecran \n\
 mov ah, 09h \n\
 lea dx, LinieNoua \n\
 int 21h \n\
\n\
ret            ; revenirea din procedura\n\
\n\
AfisBaza10 ENDP\n\
\n\
\n\
"


#endif
