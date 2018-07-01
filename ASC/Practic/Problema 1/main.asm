;A string of double words is given. It is required to build and print the string of byte ranks that have the maximum value from each doubleword (considering them unsigned). 
;Also it is required to obtain and print on the screen the sum of these bytes (considering them this time as being signed). 

;Example: dd 1234A678h , 123456789h , 1AC3B47Dh, FEDC9876h  ... the corresponding string of bytes ranks being “3421”

bits 32
global start   
extern exit,printf,fprintf,fopen,fclose             
import exit msvcrt.dll  
import printf msvcrt.dll 
import fprintf msvcrt.dll 
import fopen msvcrt.dll
import fclose msvcrt.dll
segment data use32 class=data
    nume_fisier db "input.txt",0
    mod_acces db "w",0
    descriptor dd -1
    sir dd 1234A678h , 12345678h , 1AC3B47Dh, 0FEDC9876h
    len equ ($-sir)/4
    ranks times len db 0
    suma dw 0
    format_r db "%u",0
    format_s db 0ah,0dh,"Suma este: %d",0
    mesaj db "Sirul de ranguri: ",0
    max db 0
    poz db 0
segment code use32 class=code
    start:
        ;deschid fisier
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp,8    
        
        cmp eax,0
        je final1
        
        mov [descriptor],eax
        
        ;prelucrare sir
        mov ecx, len
        jecxz final
        mov esi, sir
        mov edi,ranks
        mov word [suma],0
        cld
        repeta:  
            
            lodsd
            mov dl,4 ;pozitia
            mov byte[poz],dl
            mov byte[max],0
            mov ebx,eax
            octet:
                cmp al,[max]
                jbe next
                
                mov [max],al ;actulaizez maximul
                mov byte[poz],dl ;salvez pozitia
                next:
                ror eax,8;urmatorul octet
                dec dl
                cmp ebx, eax
                jne octet
                
            mov al,[poz]
            stosb
            ;suma
            mov al,[max]
            mov ah,0
            add word[suma],ax
            
        loop repeta
        
        final:
        ;mesaj ranguri
        push dword mesaj
        push dword [descriptor]
        call [fprintf]
        add esp,8
        
        
        ;afisare ranguri
        mov ecx,len
        jecxz final1
        mov esi,ranks
        afisare:
            push ecx
            mov eax,0
            lodsb
            push dword eax
            push dword format_r
            push dword [descriptor]
            call [fprintf]
            add esp,12
            pop ecx
        loop afisare
        
        ;afisare suma
        mov ax,word[suma]
        cwde
        push eax
        push dword format_s
        push dword [descriptor]
        call [fprintf]
        add esp,12
        
        ;inchid fisier
        push dword [descriptor]
        call [fclose]
        add esp,4
        final1:
        
        push dword 0   
        call [exit]