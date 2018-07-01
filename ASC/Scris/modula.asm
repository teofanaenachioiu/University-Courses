bits 32
global start, sir,subsir,len
extern exit,secventa,printf,nr
import exit msvcrt.dll
import printf msvcrt.dll
segment data use32 public
    sir db 1,4,5,0,8,3,1,4,2,0,4,0
    len equ $-sir
    subsir times len-1 db 99h
    format db "%d ",0
segment code use32 public
    start:
        mov ecx,len
        jecxz final
        
        call secventa
        add esp,4
        
        mov ecx,dword[nr]
        jecxz final
        
        mov esi,subsir
        
        bucla:
            lodsb
            cbw
            cwde
            pusha
            push eax
            push dword format
            call [printf]
            add esp,8
            popa
        loop bucla
        
        final:
        push dword 0
        call [exit]