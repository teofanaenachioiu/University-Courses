bits 32
global start
extern exit,fopen,fclose,fread,printf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll
import printf msvcrt.dll
segment data use32 class=data 
    nume_fisir db "ana.txt",0
    mod_acces db "r",0
    descriptor dd -1
    len equ 100
    text times len db 0
    format db "%c",0
segment code use32 class=code
    start:
        ;deschid fisierul
        push dword mod_acces
        push dword nume_fisir
        call [fopen]
        add esp,8
        
        ;erori
        cmp eax, 0
        je final
        mov [descriptor],eax
        
        ;citesc din fisier
        push dword [descriptor]
        push dword len
        push dword 1
        push dword text
        call [fread]
        add esp,4*4
        
        ;afisez pe ecran textul
        mov esi,text
        lodsb
        afisare:
            cbw
            cwde
            
            pusha
            push eax
            push dword format
            call [printf]
            add esp,4*2
           
            popa
            lodsb
            cmp al,0
            jne afisare
        
        ;inchid fisierul
        push dword[descriptor]
        call [fclose]
        add esp,4
        
        final:
        push dword 0
        call [exit]