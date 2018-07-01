;a. Se dau doua siruri de cuvinte a si b. Sa se obtina sirul c astfel: 
;- concatenati sirul octetilor low din primul sir cu sirul octetilor high din al doilea sir. 
;- sirul c se va ordona crescator
;- numerele din siruri se vor interpreta cu semn
;- afisati sirul c (numere in baza 16)

	;Exemplu: 

		;a dw 1234h,5678h,90h

		;b dw 4h,0abcdh,10h,1122h

		;c resb 7

	;Reprezentarea in memorie:

		;a -> | 34h| 12h| 78h| 56h| 90h| 00h|

		;b -> | 04h| 00h| 0cdh| 0abh| 10h| 00h| 22h| 11h|

	;Dupa concatenare sirul c va fi: 34h,78h,90h,00h,0abh,00h,11h. 
	;Apoi se face ordonarea octetilor.
	

bits 32 
global start        
extern exit,printf               
import exit msvcrt.dll 
import printf msvcrt.dll   
segment data use32 class=data
    a dw 1234h,5678h,90h
    len_a equ ($-a)/2
    b dw 4h,0abcdh,10h,1122h
    len_b equ ($-b)/2
    len_c equ len_a+len_b
    handle1 db -1
    c times len_c db 0
    handle2 db -1
    format db "%x ",0
segment code use32 class=code
    start:
        mov edi,c
        
        ;incarcarea octetilor low din a
        mov esi,a
        mov ecx, len_a
        jecxz final1
        repeta_low:
            movsb
            inc esi
        loop repeta_low
        final1:
        
        ;incarcarea octetilor high din b
        mov esi,b
        mov ecx, len_b
        jecxz final2
        repeta_high:
            inc esi
            movsb
        loop repeta_high
        final2:
        
        ;sortare
        mov ecx,len_c
        dec ecx
        jecxz final
        mov esi,c
        mov ebx,1
        sortare:
            push ecx
            mov edi,c
            add edi,ebx
            repeta:
                cmpsb
                jle next
                
                dec edi
                dec esi
                
                swap:
                    lodsb
                    mov ah,al
                    dec esi
                    
                    xchg esi,edi
                    
                    lodsb
                    dec esi
                    
                    stosb
                    dec edi
                    
                    mov al,ah
                    xchg esi,edi
                    stosb 
                    jmp iesi
                next:
                    dec esi
                iesi:
            loop repeta  
            inc esi
            inc ebx
            pop ecx  
        loop sortare
        
        ;afisare
        mov ecx, len_c
        mov esi, c
        bucla_print:
            mov eax,0
            lodsb
            pushad
            push eax
            push dword format
            call [printf]
            add esp, 8
            popad
        loop bucla_print
        final:
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
