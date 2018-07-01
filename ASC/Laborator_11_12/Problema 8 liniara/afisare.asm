%include "conversie.asm"

%ifndef _afisare_asm_
%define _afisare_asm_
afisare:
    call conversie
    mov ebx,127
    sub ebx,ecx
    pusha
    push ebx
    push dword [numar]
    push dword format_scriere1
    push dword [descriptor_o]
    call [fprintf]
    add esp,16
    popa
    
    ret
%endif