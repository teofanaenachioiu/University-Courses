;Se dau un nume de fisier si un text (definite in segmentul de date). Textul contine litere mici, litere mari, cifre si caractere speciale. Sa se transforme toate literele mici din textul dat in litere mari. Sa se creeze un fisier cu numele dat si sa se scrie textul obtinut in fisier.

bits 32
global start     
extern exit,fopen,fclose,fwrite              
import exit msvcrt.dll  
import fopen msvcrt.dll
import fclose msvcrt.dll
import fwrite msvcrt.dll
segment data use32 class=data
    nume_fisier db "output.txt",0
    mod_acces db "w",0
    descriptor dd -1
    text db "Sa se transforme toate literele mici din textul dat in litere mari. :0",0
    len equ $-text
segment code use32 class=code
    start:
        ;deschid fisierul
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp,8
        
        ;erori
        cmp eax,0
        je final
        
        mov [descriptor],eax
        
        ;prelucram textul
        mov ecx,len
        jecxz final
        
        mov esi,text
        
        mov bl,'a'
        mov dl,'z'
        construct:
            lodsb
            cmp al,bl
            jl pune
            
            cmp al,dl
            jg pune
            
            sub al,32
            
            pune:
                mov edi,esi
                dec edi
                stosb
        loop construct
        
        ;pun textul in fisier
        push dword [descriptor]
        push dword len
        push dword 1
        push dword text
        call [fwrite]
        add esp,16
        
        ;inchid fisier
        push dword [descriptor]
        call [fclose]
        add esp,4
        
        final:
        push    dword 0      
        call    [exit]       