;(c-a-d)+(c-b)-a  a,b,c-byte
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 8; ...
    b db 10
    c db 17
    d db 2
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ah,[c] ;ah = c
        mov al,[a] ;al = a
        sub ah,al ;ah = ah-al = c-a
        sub ah,[d] ;ah = ah-d = c-a-d
        mov bh,[c] ;bh = c
        sub bh,[b]  ;bh = bh-b=c-b
        add ah,bh ;ah = ah+bh = (c-a-d)+(c-b)
        sub ah, al ;ah = ah-al = (c-a-d)+(c-b)-a
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
