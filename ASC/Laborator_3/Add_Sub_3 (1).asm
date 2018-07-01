;(c+d)-(a+d)+b  
;a - byte, b - word, c - double word, d - qword
;CONVERSII FARA SEMN


bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
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
        mov eax,[c] ;eax = c 
        mov edx,0 ;edx = 0 -> edx:eax = c
        add eax, dword[d]
        adc edx, dword[d+4] ;edx:eax = edx:eax + d = c+d (qword)
        
        mov bl,byte[a] ;bl = a
        mov bh,0 ;bh = 0 -> bx = a (word)
        mov cx,bx ;cx = bx = a 
        mov ebx,0 ;ebx = 0
        mov bx,cx ;bx = cx = a -> ebx = a (double word)
        mov ecx,0 ;ecx = 0 -> ecx:ebx = a (qword)
        add ebx, dword[d]
        adc ecx, dword[d+4] ;ecx:ebx = a+d 
        
        sub eax,ebx ;eax = eax-ebx
        sbb edx,ecx ;edx = edx-ecx -> edx:eax = (c+d)-(a+d) (qword)
        
        mov bx,[b] ;bx=b (word)
        mov cx,bx ;cx = bx = b
        mov ebx,0 ;ebx = 0
        mov bx,cx ;bx = cx = b -> ebx = b (double word)
        mov ecx,0 ;ecx = 0 -> ecx:ebx = b
        add eax,ebx ;eax = eax+ebx 
        adc edx,ecx ;edx = edx+ecx ->(c+d)-(a+d)+b
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
