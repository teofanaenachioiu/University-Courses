;Se da un sir de N cuvinte. Sa se tipareasca in baza 16 catul si restul impartirii fara semn A/B, unde A este maximul valorilor octetilor inferiori ai sirului de cuvinte date, iar B este minimul valorilor octetilor superiori ai sirului de cuvinte date. 
;sir_cuv dw 21520,-6,"xy",0f5b2h,-129
;Daca A=194 si B=10, la iesire se va afisa pe ecran catul = 13h si restul = 04h

bits 32 
global start        
    
extern exit,fprintf,fopen,fclose
import exit msvcrt.dll   
import fprintf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
segment data use32 class=data
    sir_cuv dw 21520,-6,"xy",0f5b2h,-129
    len equ ($-sir_cuv)/2
    max db 0
    min db 0ffh
    nume_fisier db "output.txt",0
    mod_acces db "w",0
    descriptor dd -1
    format db "catul=%x restul=%x",0
segment code use32 class=code
    start:
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp,8
        
        cmp eax,0
        je final
        
        mov [descriptor],eax
        
        mov ecx,len
        jecxz inchide
        
        mov esi,sir_cuv
        bucla:
            lodsw
            cmp al,byte[max]
            jbe next
            mov [max],al
            
            next:
            cmp ah,byte[min]
            jae mai_departe
            mov [min],ah
           
            mai_departe:
        loop bucla
        
        mov eax,0
        mov al,byte[max]
        mov cl,byte[min]
        div cl
        
        mov bl,al
        mov bh,ah
        
        mov eax,0
        mov al,bl
        mov edx,0
        mov dl,bh
        
        push edx
        push eax
        push dword format
        push dword [descriptor]
        call [fprintf]
        add esp,16
        
        
        inchide:
        push dword [descriptor]
        call [fclose]
        add esp,4
        
        final:
        push    dword 0     
        call    [exit]     
