;(a*b+2)/(a+7-c)+d+x
;a,c-byte; b-word; d-doubleword; x-qword
;CONVERSIE CU SEMN

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
    ;a resb 1
    ;b resw 1
    ;c resb 1
    ;d resd 1
    ;x resq 1
    a db 1
    b dw 3
    c db 5
    d dd 2
    x dq 4
    
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov al,byte[a] ;al = a
        cbw ;al -> ax = a(word)
        imul word[b] ; dx:ax = ax*b = a*b
        add ax,2 
        adc dx,0 ; dx:ax = dx:ax+2 = a*b+2 (dword)
        mov cx,ax ;cx=ax -> dx:cx = a*b+2 (dword)
        
        mov al,byte[a] ;al = a
        add al,7 ; al = al+7 = a+7
        sub al,byte[c] ; al = al-c = a+7-c (byte)
        cbw ;ax = a+7-c
        mov bx, ax ;bx=a+7-c
        mov ax,cx ;ax=cx
        idiv bx ;ax= dx:ax/ax = (a*b+2)/(a+7-c) (word)
        
        cwde ;ax -> eax (dword)
        add eax,dword[d] ;eax = eax+d = (a*b+2)/(a+7-c)+d
        cdq ;eax -> edx:eax (qword)
        add eax,dword[x]
        adc edx,dword[x+4] ;edx:eax = (a*b+2)/(a+7-c)+d+x
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
