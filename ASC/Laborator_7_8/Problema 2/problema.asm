;Se da un sir de dublucuvinte. Sa se ordoneze descrescator sirul cuvintelor inferioare ale acestor dublucuvinte. Cuvintele superioare raman neschimbate.


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
    sir dd 12345678h, 1256ABCDh, 12AB4344h
    len equ ($-sir)/4
    handleee db -1
    format db "%X ",0
segment code use32 class=code
    start:
        mov ecx,len
        jecxz final
        dec ecx
        jecxz afisare
        mov esi,sir
        mov ebx,4
        sortare:
            push ecx
            mov edi,sir
            add edi,ebx
            
            bucla:
                cmpsw
                jae next
                sub esi,2
                sub edi,2
                swap:
                    lodsw
                    mov dx,ax
                    sub esi,2
                    
                    xchg esi,edi
                    
                    lodsw
                    sub esi,2
                    
                    stosw
                    sub edi,2
                    
                    xchg esi,edi
                    mov ax,dx
                    stosw
                add edi,2
                jmp iesi
                next:
                    add edi,2
                    sub esi,2
                iesi:
            loop bucla
            pop ecx
            add ebx,4
            add esi,4
        loop sortare
        
        afisare:
            mov ecx,len
            mov esi,sir
            loop_afisare:
                lodsd 
                pusha
                push eax
                push dword format
                call [printf]
                add esp, 4*2
                popa
            loop loop_afisare
        final:
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
