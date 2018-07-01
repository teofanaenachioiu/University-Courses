;Se da un sir de octeti 'input' si inca doua siruri de dimensiune N fiecare, 'src' si 'dst'. Sa se obtina un nou sir 'output' din sirul 'input' in care se vor inlocui toti octetii cu valoarea src[i] cu dst[i], unde i=1..N.

bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll     ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    input db 2,4,4,9,20,1,7,3,9,1
    l equ $-input
    src db 1,4,9
    dst db 3,6,11
    n equ $-dst
    output times l db 0
    mesaj db "Sirul este: ",0
    format db "%d ", 0
    ; our code starts here
segment code use32 class=code
    start:
        ;print mesaj de inceput
        push dword mesaj
        call [printf]
        add esp, 4*1
        
        ;copierea sirului input in output
        cld 
        mov ecx,l  
        jecxz final
        mov esi,input
        mov edi,output
        
        muta:
            movsb
        loop muta
        
        ;modificarea sirului output
        mov ecx,n 
        jecxz final
        mov esi,0
        
        bucla:
            pushad
            mov ecx,esi
            mov esi,src
            add esi,ecx
            lodsb ;al=src...
            popad
            mov edi,output
            mov ecx,l
            jecxz final
            cauta:
                scasb ;cmp al cu <es:edi>
                jne next
                
                egal:
                    mov bl,al ;retin in bl valoarea lui al
                    dec esi
                    lodsb ;al=dst
                    mov al,[dst+esi]
                    dec edi
                    stosb ;<es:esi>=al
                    mov al,bl ;reinitializez al
                next:
            loop cauta 
            
            inc esi
            pop ecx
            
        loop bucla
        
        ;afisarea sirului output
        mov ecx,l
        jecxz final
        mov esi,output
        
        afisare:
            lodsb
            cbw
            cwde
            pusha
            push eax
            push dword format
            call [printf]
            add esp, 4*2
            popa
        loop afisare
        
        final:
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
