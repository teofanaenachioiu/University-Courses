bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 0xCD,0CDh,0000;
    b dw 55584
    c resb 1
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov bl,0 ;bl=00000000b
        
        mov al,[a] ;al=11001101b
        and al,00111100b ;al=00001100b izolam bitii 2-5 ai lui <<a>>
        mov cl,2 ;cl=2
        ror al,cl ;al=00000011b
        
        or bl,al ;bl=00000011b
        
        mov ax,[b] ;ax=1101100100100000b
        and ax,0000001111000000b ;ax=0000000100000000b
        mov cl,2 ;cl=2
        ror ax,cl ;ax=0000000001000000b
        
        or bl,al ;bl=01000011b
        
        mov [c],bl ;c=01000011b=43h
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
