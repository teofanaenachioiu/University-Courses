;Se dau cuvintele A si B. Sa se obtina dublucuvantul C:
;bitii 0-4 ai lui C coincid cu bitii 11-15 ai lui A
;bitii 5-11 ai lui C au valoarea 1
;bitii 12-15 ai lui C coincid cu bitii 8-11 ai lui B
;bitii 16-31 ai lui C coincid cu bitii lui A

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
    a dw 0A756h
    b dw 094f1h
    c times 1 dd 0 
    x db -1
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ecx, 0
        mov ax, [a]
        and ax, 0F800h
        rol ax, 5
        or cx, ax
        mov [c],ecx
        
        or ecx, 0fe0h
        mov [c],ecx
         
        mov bx,[b]
        and bx,0f00h
        rol bx,4
        or cx,bx
        mov [c],ecx
        
        mov eax,0
        mov ax,word[a]
        rol eax,16
        or ecx, eax
        mov [c],ecx
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
