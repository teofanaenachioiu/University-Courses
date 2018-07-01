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
        mov ah,0 ;ah=0 -> ax = a (word)
        mul word[b] ; dx:ax = ax*b = a*b (dword)
        add ax, 2 ; ax = ax+2 = a*b+2 (dword)
        adc dx,0 ;dx=dx+0 ->dx:ax = a*b+2 (dword)    
        
        mov bl,byte[a] ;bl = a
        add bl,7 ; bl = bl+7 = a+7
        sub bl,byte[c] ; bl = bl-c = a+7-c (byte)
        mov bh,0 ;bx=a+7-c (word)
        
        div bx ;ax = dx:ax/bx = (a*b+2)/(a+7-c) (word)
        mov cx,ax ;cx = ax = (a*b+2)/(a+7-c)
        mov eax,0 ;eax = 0
        mov ax,cx ;eax=cx=(a*b+2)/(a+7-c) (dword)
        
        add eax,dword[d] ;eax = eax+d = (a*b+2)/(a+7-c)+d
        mov edx,0 ;edx:eax = (a*b+2)/(a+7-c)+d
        add eax,dword[x]
        adc edx,dword[x+4] ;edx:eax = (a*b+2)/(a+7-c)+d+x
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
