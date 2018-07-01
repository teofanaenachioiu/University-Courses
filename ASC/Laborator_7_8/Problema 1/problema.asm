bits 32
global start 
extern exit
import exit msvcrt.dll
segment data use32 class=data
    s1 db 'aghty'
    s2 db 'blmpyz'
    len1 equ s2-s1
    len2 equ $-s2
    s3 times len1+len2 db -1
    nr1 db len1
    nr2 db len2
    nr3 dd 0
segment code use32 class=code 
    memo:
        sub esi,s1
        sub edi,s2
        add edi,esi
        dec edi
        add edi,s3
        ret
        
    start:
        mov esi,s1
        mov edi,s2
        cld
        interclasare:
            cmpsb
            ja sir2
            sir1:
                dec edi
                mov ebx,esi
                mov edx,edi
                
                call memo
                
                mov esi, ebx
                dec esi
                
                movsb
                
                mov esi,ebx
                mov edi,edx
                dec byte[nr1]
                jmp mai_departe
            sir2:
                 
                dec esi
                mov ebx,esi
                mov edx,edi
                
                call memo
                
                mov esi, edx
                dec esi
                
                movsb
                
                mov esi,ebx
                mov edi,edx
                dec byte[nr2]
            mai_departe:
            inc dword[nr3]
            cmp byte[nr1],0
            jle completeaza_sir2
            cmp byte[nr2],0
            jle completeaza_sir1
            jmp interclasare
            
        completeaza_sir2:
            mov esi,s2
            mov eax,0
            mov al,[nr2]
            add esi,len2
            ;dec esi
            sub esi,eax
            
            mov edi,s3
            add edi,dword[nr3]
            mov ecx,eax
            rep movsb
            
        completeaza_sir1:
            mov esi,s1
            mov eax,0
            mov al,[nr1]
            add esi,len1
            ;dec esi
            sub esi,eax
            
            mov edi,s3
            add edi,dword[nr3]
            mov ecx,eax
            rep movsb
            
        push dword 0
        call [exit]
