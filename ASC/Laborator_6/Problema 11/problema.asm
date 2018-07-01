;Se dau doua siruri de caractere S1 si S2. Sa se construiasca sirul D ce contine toate elementele din S1 care nu apar in S2. 

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
    ; ...
    s1 db '+', '4', '2', 'a', '8', '4', 'X', '5'
    len1 equ $-s1
    s2 db 'a', '4', '5'
    len2 equ $-s2
    d times len1 db 0
    handle db -1
    format db "%c ",0
segment code use32 class=code
    start:
        mov esi,0
        mov ecx, len1
        jecxz final
        mov ebx,0
        element:
            push ecx
            mov ecx, len2
            jecxz tot
            mov al,[s1+esi]
            mov edi,s2
            cautare:
               scasb
               jz gasit
            loop cautare
            
            mov edi,ebx
            inc ebx
            mov [d+edi],al
            gasit:
                inc esi
                pop ecx
        loop element
        
       
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
            tot:
            mov ecx, len1
            mov esi,s1
            jecxz final
            repeta:
               movsb
            loop repeta
        final:
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
