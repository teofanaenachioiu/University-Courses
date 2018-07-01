;:A string of doubleword is given. Build a string of bite in the following way:
;take the most significant byte of the least significant word of each doubleword and if it is strictly negative than put it into the string. After that write your string of bites on the screen in base 2.
;Ex: sir dd 12345678h,1234abcdh,FF00FE33h
;your string should look like: AB,FE
;You should print on the screen: 1010 1011 1111 1110(no space needed beetwen the number)

bits 32 
global start        

extern exit,fprintf,fopen,fclose
import exit msvcrt.dll   
import fprintf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll   
segment data use32 class=data
    sir dd 12345678h,1234abcdh,0FF00FE33h
    len equ ($-sir)/4
    dest times len db -1
    nume_fisier db "output.txt",0
    mod_acces db "w",0
    descriptor dd -1
    doi db 2
    bit times len*8 db -3
    format db "%x",0
    nr dw 0

segment code use32 class=code
    conversie:
        mov ecx,8
        
        .repeta:
            mov ah,0
            div byte[doi]
            mov bl,al
            mov al,ah
            stosb
            mov al,bl
        loop .repeta
        ret
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
        
        mov esi,sir
        mov edi,dest
        bucla:
            lodsd
            cmp ah,0
            jge next
            mov al,ah
            inc word[nr]
            stosb
            next:
        loop bucla
        
        mov esi,dest
        mov edi,bit
        add edi,7
        mov cx,word[nr]
        baza:
            lodsb
            
            std
            pusha
            call conversie
            popa
            add edi,8
            cld
        loop baza
        
        mov ax,word[nr]
        mov cx,8
        mul cx
        push dx
        push ax
        pop eax
        
        mov ecx,eax
        mov esi,bit
        
        afis:
            mov eax,0
            lodsb
            push ecx
            push eax
            push dword format
            push dword [descriptor]
            call [fprintf]
            add esp,4*3
            pop ecx
        loop afis
        
        inchide:
        push dword [descriptor]
        call [fclose]
        add esp,4
        
        final:
        push    dword 0     
        call    [exit]     
