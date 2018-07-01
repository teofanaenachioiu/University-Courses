;Dandu-se doua siruri de octeti sa se calculeze toate pozitiile unde al doilea sir apare ca subsir in primul sir.
;NEREZOLVATA!!!

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
    s1 db 5, 9, 6, 9, 4, 6, 5, 11, 15, 3, 9, 6, 9, 6
    len1 equ $-s1
    handle db -1
    s2 db 9, 6, 9
    len2 equ $-s2
    format db "%d ",0
; our code starts here
segment code use32 class=code
    start:
        mov esi,s1
        mov ecx,len1-len2+1
        mov ebx,0
        jecxz final
        mov edi,s2
        repeta:
            cmpsb
            
            jne next
            posibil_subsir:
                push ecx
                mov ecx,len2-1
                jecxz afiseaza
                mov edx,0
                subsir:
                    cmpsb
                    jne iesi
                    inc edx
                loop subsir
                afiseaza:
                    pusha
                    push ebx
                    push dword format
                    call [printf]
                    add esp,8
                    popa
                iesi:
                    pop ecx
                    sub ecx,edx
                    add ebx,edx 
            next:
                mov edi,s2
                inc ebx
                
        loop repeta
        final:
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
