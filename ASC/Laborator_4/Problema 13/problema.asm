;Dandu-se 4 octeti, sa se obtina in AX suma numerelor intregi reprezentate de bitii 4-6 ai celor 4 octeti.

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
    a db 056h
    b db 0f1h
    c db 0a7h
    d db 03bh
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ax,0
        
        mov bx,0 ;punem 0 in bx, apoi valoarea data in bl
        mov bl,byte[a]
        rol bl,4
        and bl,07h
        
        add ax,bx
        
        mov bx,0 ;punem 0 in bx, apoi valoarea data in bl
        mov bl,byte[b]
        rol bl,4
        and bl,07h
        
        add ax,bx
        
        mov bx,0 ;punem 0 in bx, apoi valoarea data in bl
        mov bl,byte[c]
        rol bl,4
        and bl,07h
        
        add ax,bx
        
        mov bx,0 ;punem 0 in bx, apoi valoarea data in bl
        mov bl,byte[d]
        rol bl,4
        and bl,07h
        
        add ax,bx
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
