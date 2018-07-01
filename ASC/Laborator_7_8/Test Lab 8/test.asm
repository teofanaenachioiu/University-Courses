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
    a dw 1234h,5678h,90h
    l_a equ ($-a)/2
    b dw 4h,0abcdh,10h,1122h
    l_b equ ($-b)/2
    c times 7 db 0FFh
    d times 7 db 0AAh
    format db "%x ",0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ;concatenare
        mov esi,a
        mov edi,c
        mov ecx,l_a
        jecxz sari1
        repeta_a:
            lodsw ;ax=a+esi ;esi++
            stosb ;c+edi=al ;edi++
        loop repeta_a
        
        mov esi,b
        mov ecx,l_b
        jecxz sari1
        repeta_b:
            lodsw ;ax=a+esi ;esi++
            rol ax,8
            stosb ;c+edi=al ;edi++ 
        loop repeta_b
        
        ;sortare

        mov esi,0
        mov edi,0
        
        mov ecx,7
        jecxz sari
        sari1:
        sortare:
            mov al,[c+edi] ;max=al
            push ecx
            
            dec ecx
            jecxz final 
            mov esi,edi
            
            comparare:
                inc esi
                mov bl,[c+esi]
                
                cmp al,bl
                jg mai_mare
                
                mov byte[c+esi],al
                mov al,bl
                mov edi,esi
                mai_mare:
               
            loop comparare 
            
            final:
            mov dl,al
            mov [d+edi],al
            inc edi
            pop ecx
            
        loop sortare
        
        ;afisare
        mov esi,0
        mov ecx,7
        jecxz sari
        afisare:
             pushad
             mov al,[d+esi]
             inc esi
             cbw
             cwde
             push eax 
             push dword format
             call [printf] 
             add esp,4*2
             
             popad
        loop afisare
        sari:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
