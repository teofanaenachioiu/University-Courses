;Se da un sir de octeti S de lungime l. Sa se construiasca sirul D de lungime l-1 astfel incat elementele din D sa reprezinte diferenta dintre fiecare 2 elemente consecutive din S. 

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
    s db 1, 2, 4, 6, 10, 20, 17
    len equ $-s
    d times len-1 db 0
    handle db -1
    format db "%d ",0
; our code starts here
segment code use32 class=code
    start:
        mov ecx,len
        jecxz final
        
        mov edi,d
        mov esi,0
        repeta:
            mov al,[s+esi+1]
            mov bl,[s+esi]
            sub al,bl
            stosb
            inc esi
        loop repeta
        
        mov esi,d
        mov ecx,len-1
        afisare:
            lodsb
            cbw
            cwde
            
            pusha 
            
            push eax
            push format
            call [printf]
            add esp,4*2
            popa
        loop afisare
        final:
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
