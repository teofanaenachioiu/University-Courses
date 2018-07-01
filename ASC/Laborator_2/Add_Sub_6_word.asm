;c-(d+a)+(b+c)  a,b,c -word
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 8
    b dw 10
    c dw 17
    d dw 2; ...

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov dx,[d] ;dx = d
        add dx,[a] ;dx = dx+a = d+a
        mov bx,[b] ;bx = b
        mov cx,[c] ;cx = c
        add bx,cx ;bx = bx+cx = b+c
        sub cx,dx ;cx = cx-dx = c-(d+a) 
        add cx,bx ;cx= cx+bx = c-(d+a)+(b+c)
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
