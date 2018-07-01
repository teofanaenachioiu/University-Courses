;Se da un fisier text. Sa se citeasca continutul fisierului, sa se determine cifra cu cea mai mare frecventa si sa se afiseze acea cifra impreuna cu frecventa acesteia. 
;Numele fisierului text este definit in segmentul de date

bits 32 
global start    
extern exit,fread,fopen,fclose,printf               
import exit msvcrt.dll    
import fread msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
segment data use32 class=data
    nume_fisire db "input.txt",0
    mod_acces db "r",0
    descriptor dd -1
    len equ 100
    buffer times len db 0
    nr_caractere db 0
    cifre db "0123456789"
    handle db -1
    frecventa times 10 db 0
    format db "Cifra cu cea mai mare frecventa: %d. Frecventa: %d",0
segment code use32 class=code
    start:
        ;deschid fisierul
        push dword mod_acces
        push dword nume_fisire
        call [fopen]
        add esp,8
        ;erori
        cmp eax,0
        je final
        
        mov [descriptor],eax
        ;citesc textul
        bucla:
            ;citesc maxim 100 de caractere
            push dword [descriptor]
            push dword len
            push dword 1
            push dword buffer
            call [fread]
            add esp, 4*4
            ;salvez numarul de caractere citite
            mov [nr_caractere],eax
            
            
            mov ecx,[nr_caractere]
            jecxz iesi
            ;caut fiecare caracter din buffer in sirul cu cifre
            mov esi,0
            repeta:
                push ecx
                mov edi,0
                mov bl,byte[buffer+esi]
                mov ecx,10
                    ;caut caracterul curent in sirul cu cifre 
                    caut:
                        cmp bl,byte[cifre+edi]
                        je gasit
                        inc edi
                    loop caut
                jmp gata    
                gasit:
                    inc byte[frecventa+edi] ;am gasit o cifra
                gata:
                    pop ecx
                    inc esi ;trec la urmatorul caracter din buffer
            loop repeta
            ;zerorizez spatul rezervat pentru buffer pentru a pregati urmatoarea citire    
            mov edi,buffer
            mov ecx,len
            jecxz iesi
            mov al,0
            
            zerorizare:
                stosb
            loop zerorizare
            ;verific daca mai am de facut citiri
            mov eax,[nr_caractere]
            cmp eax,0
        jne bucla
        ;caut cifra cu cea mai mare frecventa (stocat in sirul frecventa)
        iesi:
        mov ecx,10 ;lungimea sirului de cifre
        mov al,0 ;presupunem ca al=max
        mov edi,0 ;incepem de la 0
        mov esi,0
        max:
            mov dl,byte[frecventa+edi] 
            cmp al,dl ;compar cifra curenta cu maximul
            jge next
            mov al,dl ;actualizez maximul
            mov esi,edi
            next:
                inc edi
        loop max
        ;afisez cifra si frecventa sa
        afisare:
            cbw
            cwde
            push eax
            push esi
            push dword format
            call [printf]
            add esp,8
        
        push dword [descriptor]
        call [fclose]
        add esp,4
        
        final:
        push    dword 0     
        call    [exit]     
