;(e+f)*g  e,f,g-word
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    e dw 5
    f dw 25
    g dw 10
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ax,[e] ;ax = e
        add ax,[f] ;ax = e+f 
        mov bx,[g] ;bx = g
        mul bx ;eax = ax*g = (e+f)*g
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
