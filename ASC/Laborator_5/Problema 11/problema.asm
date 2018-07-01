;Sa se citeasca de la tastatura un numar in baza 16 si sa se afiseze valoarea acelui numar in baza 10.
;Exemplu: Se citeste: 1D; se afiseaza: 29
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf,scanf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
import scanf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    n times 1 dd 0
    format db "%x",0
    mesaj1 db "Dati un numar in baza 16: ",0
    mesaj2 db "Valoarea numarului in baza 10: %d",0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push mesaj1
        call [printf]
        add esp,4
        
        push dword n
        push format
        call [scanf]
        add esp, 4*2
        
        push dword [n]
        push mesaj2
        call [printf]
        add esp,6
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
