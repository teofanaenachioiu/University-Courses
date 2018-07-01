;A string of doublewords is given. Display on the screen in base 16 the values if the strictly negative lower words multiple of 16 from the string of give doublewords.

bits 32 
global start        
extern exit,fopen,fclose,fprintf              
import exit msvcrt.dll   
import fopen msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
segment data use32 class=data
    nume_fisier db "output.txt",0
    mod_acces db "w",0
    descriptor dd -1
    sir dd 0a5639e30h,12345678h
    len equ ($-sir)/4
    dest times len dw -1
    len_dest dd 0
    impartitor dw 16
    format db "%x ",0
segment code use32 class=code
    start:
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp,8
        
        cmp eax, 0
        je final
        
        mov [descriptor],eax
        
        mov ecx,len
        jecxz final
        
        mov esi,sir
        mov edi,dest
        mov dword[len_dest],0
        
        bucla:
            lodsd
            cmp ax,0
            jge mai_departe
            
            mov bx,ax
            mov dx,0
            idiv word[impartitor]
            cmp dx,0
            jne mai_departe
            mov ax,bx
            stosw
            inc dword[len_dest]
            mai_departe:
        loop bucla
        
        ;afisare
        mov ecx,dword[len_dest]
        mov esi,dest
        repeta:
            mov eax,0
            lodsw
            push ecx
            push eax
            push dword format
            push dword [descriptor]
            call [fprintf]
            add esp,12
            pop ecx
        loop repeta
        
        push dword [descriptor]
        call [fclose]
        add esp,4
        
        final:
        push    dword 0     
        call    [exit]     
