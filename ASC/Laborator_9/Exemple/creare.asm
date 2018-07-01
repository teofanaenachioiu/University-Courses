bits 32 
global start 
extern exit,fopen,fclose            
import exit msvcrt.dll   
import fopen msvcrt.dll
import fclose msvcrt.dll
segment data use32 class=data
    nume_fisier db "ana.txt",0
    mod_acces db "w",0
    descriptor dd -1
segment code use32 class=code
    start:
        ;deschide/creaza fisier
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp,8
        
        ;se iau in calcul eventualele erori
        cmp eax,0
        je final
        mov [descriptor],eax
        
        ;inchide fisier
        push dword [descriptor]
        call [fclose]
        add esp,4
        
        final:
        push dword 0     
        call [exit]      