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
    s db 'asd112ds32as266df4d5s'
    t db '0123456789'
    len_s equ t-s
    len_t equ 10
    contor db 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ecx,len_s
        jecxz final
        mov esi,s
        repeta:
            mov edi,t
            push ecx
            mov ecx, len_t
            repnz scasb
            sub edi,t
            cmp byte[s+edi],al
            jne mai_departe
            inc byte[contor]
            mai_departe:
            
            pop ecx
        loop repeta
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
