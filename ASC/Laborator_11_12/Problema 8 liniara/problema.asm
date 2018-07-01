;Sa se afiseze, pentru fiecare numar de la 32 la 126, valoarea numarului (in baza 8) si caracterul cu acel cod ASCII.

bits 32 
global start
extern exit,fprintf,fopen,fclose,fread    
import exit msvcrt.dll   
import fprintf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll

%include "afisare.asm"
%include "conversie.asm"
segment data use32 class=data
    fisier_input db "input.txt",0
    fisier_output times 30 db 0
    mod_r db "r",0
    mod_w db "w",0
    descriptor_i dd -1
    descriptor_o dd -2
    format_citire db "%s",0
    format_scriere_1 db "%x",0
    format_scriere_2 db " %c",0ah
    doi db 2
    opt db 8
    numar times 8 db 0
segment code use32 class=code
    start:
        push dword mod_r
        push dword fisier_input
        call [fopen]
        add esp,8
        
        cmp eax, 0
        je final
        mov [descriptor_i],eax
        
        push dword [descriptor_i]
        push dword 30
        push dword 1
        push dword fisier_output
        call [fread]
        add esp,4*4
        
        push dword mod_w
        push dword fisier_output
        call [fopen]
        add esp,8
        
        cmp eax, 0
        je final1
        mov [descriptor_o],eax
        
        mov ecx,126-31
        repeta:
            
            call afisare
        loop repeta
        
        ;inchidere fisiere
        push dword [descriptor_o]
        call [fclose]
        add esp, 4
        
        final1:
        push dword [descriptor_i]
        call [fclose]
        add esp,4
        
        final:
        push    dword 0      
        call    [exit]    