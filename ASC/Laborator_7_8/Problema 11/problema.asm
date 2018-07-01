;Se da un sir de dublucuvinte. Sa se obtina sirul format din octetii superiori ai cuvintelor inferioare din elementele sirului de dublucuvinte, care sunt multiplii de 10.

bits 32
global start        
extern exit              
import exit msvcrt.dll
segment data use32 class=data
    s dd 12345678h, 1A2B3C4Dh, 0FE98DC76h
    handle db -3
    len equ ($-s)/4
    d times len db -1
    
segment code use32 class=code
    start:
        mov ecx,len
        jecxz final
        mov esi,s
        mov edi,d
        mov bl,10
        repeta:
            lodsd ;extrag eax
            verifica:
                mov dl,ah
                mov ax,0
                mov al,dl
                div bl
                cmp ah,0
                jnz sari
            construire:
                mov al,dl
                stosb
            sari:
        loop repeta  
        final:
        push    dword 0     
        call    [exit]      