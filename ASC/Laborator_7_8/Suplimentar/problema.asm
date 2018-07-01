;Se da un sir de caractere. Sa se mute caracterul aflat la cea mai mare adresa inaintea caracterului aflat la cea mai mica adresa, folosind doar instructiunile cmp, jump-uri si instructiuni aritmetice simple (add,sub, adc, sbb) 
;a db 'bcdefgha' => 'abcdefgh'

bits 32 
global start        
extern exit              
import exit msvcrt.dll    
segment data use32 class=data
    a db 'bcdefgha'
    len equ $-a
segment code use32 class=code
    start:
        sbb ecx,ecx
        add ecx,len
        jecxz final
        
        sbb eax,eax
        add al,byte[a+len-1]
        
        sbb esi,esi
        add esi,len
        sub esi,1
        
        sbb edi,edi
        add edi,len
        sub esi,2
        
        repeta:
            add ah,byte[a+esi]
            sub byte[a+esi],ah
            
        loop repeta
        final:
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
