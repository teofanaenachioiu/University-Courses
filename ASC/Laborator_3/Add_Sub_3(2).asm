;(b+b+d)-(c+a)  
;a - byte, b - word, c - double word, d - qword 
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
    a resb 1
    b resw 1
    c resd 1
    d resq 1
    ; ...
    ;a db 4
    ;b dw 5
    ;c dw 6
    ;d dq 7


; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ax,word[b] ;ax = b
        mov bx,ax ;bx = ax = b
        add ax,bx ;ax = ax + bx = b+b
        cwde ; ax -> eax = b+b (dword)
        cdq ;eax -> edx:eax = b+b (qword)
        add eax,dword[d] 
        adc edx,dword[d+4] ;edx:eax = b+b+d
        
        mov ebx,eax ;ebx = eax
        mov ecx,edx ;ecx = edx -> ecx:ebx = b+b+d
        
        mov al,byte[a] ;al = a 
        cbw ;al -> ax = a (word)
        cwde ;ax -> eax = a (dword)
        add eax,dword[c] ;eax = eax+c = a+c
        cdq ;eax -> edx:eax = a+c
        
        add eax,ebx 
        adc edx,ecx ;edx:eax = (b+b+d)-(a+c)
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
