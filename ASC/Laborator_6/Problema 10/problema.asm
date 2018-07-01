;Se da un sir de octeti S. Sa se obtina in sirul D multimea elementelor din S.

bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll  ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    s db 1, 4, 2, 4, 8, 2, 1, 1
    len equ $-s
    d times len db 0
    handle db -1
    format db "%d ",0
; our code starts here
segment code use32 class=code
    start:
        mov esi,s
        mov ecx,len ;lungimea sirului s
        mov ebx,0 ;lungimea sirului d
        
        jecxz final
        repeta: 
            push ecx
            mov ecx,ebx
            lodsb
            mov edi,d
            mov dl,al
            jecxz pune
            cautare:
                scasb
                jz iesi
            loop cautare
            
            pune:
                stosb
                inc ebx
            iesi:
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
