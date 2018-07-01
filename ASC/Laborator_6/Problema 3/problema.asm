;Se da un sir de octeti S. Sa se construiasca sirul D astfel: sa se puna mai intai elementele de pe pozitiile pare din S iar apoi elementele de pe pozitiile impare din S. 

bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    s db 1, 2, 3, 4, 5, 6, 7, 8
    len equ $-s
    d times len db 0
    handle db -1
    format db "%d "
; our code starts here
segment code use32 class=code
    start:
    
        ;punem in d numerele de pe pozitii pare
        mov esi,s
        mov edi,d
        mov ecx,(len+1)//2
        jecxz final
        
        repeta:
            movsb
            inc esi
        loop repeta
    
        ;punem in d numerele de pe pozitii impare
        mov esi,s
        inc esi
        mov ecx,(len+1)//2
        
        repeta1:
            movsb
            inc esi
        loop repeta1
        
        ;afisare
        mov esi,d  
        mov ecx,len
        afisare:    
            lodsb
            cbw
            cwde
            pusha
            push eax
            push format
            call [printf]          
            add esp, 4*2
            popa
        loop afisare
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
