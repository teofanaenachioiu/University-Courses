;Sa se citeasca de la tastatura un nume de fisier si un text. Sa se creeze un fisier cu numele dat in directorul curent si sa se scrie textul in acel fisier. 
;Observatii: Numele de fisier este de maxim 30 de caractere. Textul este de maxim 120 de caractere.

bits 32 
global start        

extern exit,scanf,printf,fprintf,fopen,fclose      
import exit msvcrt.dll   
import scanf msvcrt.dll
import printf msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll  
import fclose msvcrt.dll
segment data use32 class=data
    numeFisier times 30 db 0
    mod_acces db "w", 0 
    text times 120 db 0
    format db "%s",0
    format_citire db "%c",0
    caracter db 0
    descriptor_fis dd -1
    mesaj db "Nume fisier: ",0
    mesaj1 db "Text: ",0
segment code use32 class=code
    start:
        ;Nume fisier:
        push dword mesaj
        call [printf]
        add esp,4
        ;citesc numele fisierului
        push dword numeFisier
        push dword format
        call [scanf]
        add esp, 4*2
        ;creez/deschid fisierul
        push dword mod_acces     
        push dword numeFisier
        call [fopen]
        add esp, 4*2   
        
        mov [descriptor_fis], eax
        ;eventuale erori
        cmp eax, 0
        je final
       
        ;Text:
        push dword mesaj1
        call [printf]
        add esp,4
        ;Citesc textul
        mov al,0
        mov edi,text
        ;citesc enter-ul
        push dword caracter
        push dword format_citire
        call [scanf]
        add esp, 4*2
        
        citire:
            
            push dword caracter
            push dword format_citire
            call [scanf]
            add esp, 4*2
            
            mov al,byte[caracter]
            stosb
            cmp eax,10
            
            jne citire
            
        ;Printez pe ecran textul
        push dword text
        push dword format
        call [printf]
        add esp, 4*2
        ;Pun textul in fisier
        push dword text
        push dword format
        push dword [descriptor_fis]
        call [fprintf]
        add esp, 4*3
        ;Inchid fisierul
        push dword [descriptor_fis]
        call [fclose]
        add esp, 4
       
        final:
        push    dword 0    
        call    [exit]     
