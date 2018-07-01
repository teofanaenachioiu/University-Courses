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

extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    a dw 1234h,5678h,90h
    b dw 4h,0abcdh,10h,1122h
    c times ($-a)/2 db 0xff
    len_c equ $-c
    len_a equ (b-a)/2
    len_b equ (c-b)/2
    format db "%d, ", 0

segment code use32 class=code
	start:  
        mov ecx, len_a
        mov esi, a
        mov edi, c
        cmp ecx, 0
        jne bucla
        jmp final
        bucla:
            lodsw
            stosb
        loop bucla
        mov ecx, len_b
        mov esi, b
        cmp ecx, 0
        jne bucla_2
        jmp final
        bucla_2:
            lodsw
            mov al, ah
            stosb
        loop bucla_2
        
        ;sortarea
        mov ecx, len_c
        mov esi, 0
        dec ecx
        cmp ecx, 0
        jne main_sort
        jmp final
        main_sort:
            push ecx
            mov edi, esi
            inc edi
            bucla_sort:
                mov al, [c+esi]
                mov ah, [c+edi]
                cmp al, ah
                jg swap
                jmp final_bucla_sort
                swap:
                    mov bl, al
                    mov al, ah
                    mov ah, bl
                final_bucla_sort:
                mov [c+esi], al
                mov [c+edi], ah
                inc edi
            loop bucla_sort
            pop ecx
            inc esi
        loop main_sort
        
        mov ecx, len_c
        mov esi, 0
        bucla_print:
            pushad
            mov eax, 0
            mov al, [c+esi]
            push eax
            push dword format
            call [printf]
            add esp, 8
            popad
            inc esi
        loop bucla_print
        
        final:
	push dword 0
	call [exit]