;Se da un fisier text. Sa se citeasca continutul fisierului, sa se contorizeze numarul de vocale si sa se afiseze aceasta valoare. 
;Numele fisierului text este definit in segmentul de date.

bits 32
global start    
extern exit,fopen,fclose,fread  ,printf          
import exit msvcrt.dll 
import fopen msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll 
import printf msvcrt.dll 
segment data use32 class=data
    nume_fisier db "input.txt",0
    mod_acces db "r",0
    descriptor dd -1
    len equ 100
    buffer times len db 0
    nr_car_citite dd 0
    vocale db "AEIOUaeiou",0
    format db "%d",0
segment code use32 class=code
    start:
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp,4*2
        
        cmp eax,0
        je final
        
        mov [descriptor],eax
        
        bucla:
            
            push dword [descriptor]
            push dword len
            push dword 1
            push dword buffer
            call [fread]
            add esp, 4*4
            
            cmp eax, 0        
            je cleanup

            mov [nr_car_citite], eax        ; salvam numarul de caractere citie
            
            mov esi,buffer
            mov ebx,0
            
            mov ecx,[nr_car_citite]
            ;numaram vocalele din buffer
            repeta:
                lodsb
                push ecx
                mov edi,vocale
                mov ecx,10
                    caut:
                        scasb
                        je gasit
                    loop caut
                jmp gata
                gasit:
                    inc ebx
                gata:
                    pop ecx
            loop repeta
               
            jmp bucla
            
        cleanup:
       
        push ebx
        push dword format
        call [printf]
        add esp,8
        
        push dword [descriptor]
        call [fclose]
        add esp,4
        
        final:
        push    dword 0    
        call    [exit]     