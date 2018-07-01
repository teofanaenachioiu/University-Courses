bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s1 db 1, 3, 5, 7
    s2 db 2, 6, 9, 4
    len equ $-s2
    d times len*2 db 0
    handle db -1
    format db "%d ",0
; our code starts here
segment code use32 class=code
    start:
        mov esi,0
        mov ecx,len*2
        mov edi,0
        
        jecxz final
        
        merge:
            mov al,[s1+esi]
            mov bl,[s2+edi]
            cmp al,bl
            jl el1
            el2:
               mov [d+esi+edi],bl
               cmp edi,len
               jge el1
               inc edi
               jmp next
            el1:
                mov [d+esi+edi],al
                cmp esi,len
                jge el2
                inc esi
                jmp next
            next:
        loop merge
        
        mov esi,d
        mov ecx,len*2
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
