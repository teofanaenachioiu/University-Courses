;  programul calculeaza factorialul unui numar si afiseaza in consola rezultatul
;  procedura factorial este definita in fisierul factorial.asm
bits  32
global  start
import  printf msvcrt.dll
import  exit msvcrt.dll
extern  printf, exit
;  codul definit in factorial.asm va fi copiat aici
%include  "factorial.asm"
segment  data use32 class=data
	format_string db  "factorial=%d", 10, 13, 0
segment  code use32 class=code
start:
	push dword 5
	call factorial
	push eax
	push format_string
	call [printf]
	add esp, 2*4
	push 0
	call [exit]