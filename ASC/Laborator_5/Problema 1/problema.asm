;Sa se citeasca de la tastatura doua numere (in baza 10) si sa se calculeze produsul lor. Rezultatul inmultirii se va salva in memorie in variabila "rezultat" (definita in segmentul de date).

bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,scanf,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import scanf msvcrt.dll   ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    mesaj db "Dati numar: ",0
    f1 times 1 dw 0
    f2 times 2 dw 0
    format db "%d",0
    rezultat times 1 dd 0
    final db "Rezultatul este: ",0
; our code starts here
segment code use32 class=code
    start:
        
        push dword mesaj
        call [printf]
        add esp,4
        
        push dword f1
        push dword format
        call [scanf]
        add esp,4*2
        
        push dword mesaj
        call [printf]
        add esp,4
        
        push dword f2
        push dword format
        call [scanf]
        add esp,4*2
        
        mov ax,word[f2]
        imul word[f1]
        
        mov [rezultat],eax
        
        push dword final
        call [printf]
        add esp,4
        
        push dword [rezultat]
        push dword format
        call [printf]
        add esp,4*2
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
