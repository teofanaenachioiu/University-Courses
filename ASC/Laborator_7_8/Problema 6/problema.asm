;Dandu-se un sir de cuvinte sa se obtina sirul (de octeti) cifrelor in baza zece ale fiecarui cuvant din acest sir.

bits 32 
global start   
extern exit,printf              
import exit msvcrt.dll    
import printf msvcrt.dll
segment data use32 class=data
    sir dw 12345, 20778, 4596 
    len equ ($-sir)/2
    handle db 0
    cifre times len*8 db -1
    format db "%d ",0
segment code use32 class=code
    start:
        mov esi,sir
        mov edi,cifre
        mov ecx,len
        jecxz final
        cuvant:
            push ecx
            
            mov dx,0
            lodsw ;ax=primul cuvant
            sub esi,2
            mov ecx,0 ; numar in ecx cate cifre are numarul
            mov bx,10
            numarare:
                div bx ;impart la 10
                inc ecx 
                mov dx,0 ;dx il fac 0 pentru a putea trece la urmatoarea impartire
                cmp ax,0 ;fac operatia cat timp cat!=0
                jnz numarare
            std ;parcurgere de la dreapta spre stanga
            mov dx,0
            lodsw ;ax=primul cuvant
            dec ecx
            add edi,ecx 
            construire:
                mov bx,10
                div bx
                mov bx,ax
                mov ax,dx
                stosb ;incarc octetul al in sirul cifre
                mov dx,0
                mov ax,bx
                cmp ax,0
                jnz construire
            cld    
            inc ecx
            add edi,ecx 
            inc edi ;trec la urmatoarea pozitie
            add esi,4 ;habar n-am de ce trebuie sa fac asta.....
            pusha 
            push dword cifre 
            push dword format
            call [printf]
            add esp,8
            popa
            pop ecx
        loop cuvant
        final:
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
