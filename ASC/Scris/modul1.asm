bits 32
global start, sir,subsir
extern exit,printf,secventa
import exit msvcrt.dll
import printf msvcrt.dll
segment data use32 public
    sir dd 12345678h,12F54769h,4145A36Fh
    len equ ($-sir)/4
    subsir times len db -2
    format db "%x ",0
segment code use32 public
    start:
        mov ecx,len
        jecxz final
        
        call secventa
        
        mov ecx, len
        mov esi, subsir
        repeta:
            lodsb
            mov bl,al
            mov eax,0
            mov al,bl
            pusha
            push eax
            push dword format
            call [printf]
            add esp,8
            popa
        loop repeta
        
        final:
        push dword 0
        call [exit]