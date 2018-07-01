;Se dau doua siruri de octeti S1 si S2. Sa se construiasca sirul D prin concatenarea elementelor din sirul S1 1uate de la stanga spre dreapta si a elementelor din sirul S2 luate de la dreapta spre stanga. 

bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf         ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll  ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s1 db 1, 2, 3, 4
    len1 equ $-s1
    s2 db 5, 6, 7
    len2 equ $-s2
    d times len1+len2 db 0
    handle db -1
    format db "%d "
; our code starts here
segment code use32 class=code
    start:
    
        ;punem in d sirul s1
        mov esi, s1
        mov edi, d
        cld ;de la stanga la dreapta
        mov ecx,len1
        jecxz sir2
        repeta:
            movsb 
        loop repeta
        sir2:
        ;punem in d sirul s2
        mov esi, s2+len2-1
        std ;de la drapta la stanga
        mov ecx,len2
        jecxz final
        repeta1:
            movsb 
            add edi,2
        loop repeta1
        
        ;afisarea sirului
        cld ;de la stanga la dreapta    
        mov esi,d  
        mov ecx,len1+len2
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
        ; exit(0)
        final:
        nop
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
