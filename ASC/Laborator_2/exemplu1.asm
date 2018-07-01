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
    var dw 20
; our code starts here
segment code use32 class=code
    start:
        ; ...
        MOV EDX,10
        MOV EBX,15
        ADD EDX, EBX
        MOV AX, 12
        ADD AX,[var]
        MOV AL,2
        MOV DH,5
        MUL DH ;AX=2*5=10=Ah
        MOV EAX,100
        MOV EDX,0
        MOV EBX,3
        DIV EBX
        mov AL, 7
        mov AL, 12
        mov AX, 256
        mov AX, -1
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
