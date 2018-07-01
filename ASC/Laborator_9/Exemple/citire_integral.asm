; Codul de mai jos va deschide un fisier numit "input.txt" din directorul curent si va citi intregul text din acel fisier, in etape, cate 100 de caractere intr-o etapa.
; Deoarece un fisier text poate fi foarte lung, nu este intotdeauna posibil sa citim fisierul intr-o singura etapa pentru ca nu putem defini un sir de caractere suficient de lung pentru intregul text din fisier. De aceea, prelucrarea fisierelor text in etape este necesara.
; Programul va folosi functia fopen pentru deschiderea fisierului, functia fread pentru citirea din fisier si functia fclose pentru inchiderea fisierului creat.
; Deoarece in apelul functiei fopen programul foloseste modul de acces "r", daca un fisier numele dat nu exista in directorul curent,  apelul functiei fopen nu va reusi (eroare). Detalii despre modurile de acces sunt prezentate in sectiunea "Suport teoretic".

bits 32
global start
extern exit,fopen,fclose,fread,printf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll
import printf msvcrt.dll
segment data use32 class=data 
    nume_fisir db "input.txt",0
    mod_acces db "r",0
    descriptor dd -1
    len equ 100
    buffer times len db 0
    numar_caractere dd 0
    text times 500 db 0
    format db "%d: %s",0
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
        
        mov edi,text
        mov ebx,0
        ;citesc din fisier
        bucla:
            push dword [descriptor]
            push dword len
            push dword 1
            push dword buffer
            call [fread]
            add esp,4*4
                        
            cmp eax,0
            je afisare
            
            mov [numar_caractere],eax
            add ebx, [numar_caractere]
            mov esi,buffer
            
            mov ecx,[numar_caractere]
            repeta:
                movsb
            loop repeta
            
            jmp bucla
        ;afisez textul pe ecran
        afisare:
            push dword text
            push dword [numar_caractere]
            push dword format
            call [printf]
            add esp,4*3
           
        ;inchid fisierul
        push dword[descriptor]
        call [fclose]
        add esp,4
        
        final:
        push dword 0
        call [exit]