;[2*(a+b)-5*c]*(d-3)  a,b,c-byte, d-word
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
    a db 7
    b db 3
    c db 2
    d dw 15
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov al,[a] ;al = a
        add al,[b] ;al = al+b = a+b
        mov ah,2 ;ah = 2
        mul ah ;ax = al*ah = (a+b)*2
        mov dx,ax ;dx = ax = (a+b)*2
        
        mov al,[c] ;al = c
        mov ah,5 ;ah = 5 
        mul  ah ;ax = al*ah = c*5
        
        sub dx,ax ;dx = dx-ax = (a+b)*2-c*5
        mov ax,dx ;ax = dx = (a+b)*2-c*5
        
        mov cx,[d] ;cx = d
        sub cx,3 ;cx = cx-3 = d-3

        mul cx; dx:ax = ax * cx = [(a+b)*2-c*5]*(d-3)
        push  dx ;se pune pe stiva partea high din double word-ul DX:AX
        push  ax ;se pune pe stiva partea low din double word-ul DX:AX
        pop  eax ;eax = dx:ax = [(a+b)*2-c*5]*(d-3)
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
