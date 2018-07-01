;Se da un sir de caractere S. Sa se construiasca sirul D care sa contina toate caracterele cifre din sirul S. 

bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s db '+', '4', '2', 'a', '8', '4', 'X', '5'
    len equ $-s
    cifre db '012345789'
    d times len db 0
    handle db -1
    format db "%c ",0
; our code starts here
segment code use32 class=code
    start:
        mov ecx, len
        mov esi,0
        mov ebx,0
        jecxz final
        repeta:
            push ecx
            mov al,[s+esi]
            mov ecx,10
            mov edi,cifre
            cautare:
                scasb
                jz gasit
            loop cautare
            jmp next
            gasit:
                mov edi,ebx
                inc ebx
                mov [d+edi],al
            next:   
                inc esi
                pop ecx
        loop repeta
        mov esi,d
        mov ecx,ebx
        afisare:
            lodsb 
            cbw 
            cwde
            pusha
            push eax
            push dword format
            call [printf]
            add esp,4*2
            popa
        loop afisare 
        final:
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
