%ifndef _conversie_asm_
%define _conversie_asm_
conversie:
    mov bl,127
    sub bl,cl
    push ecx
    mov ecx,8
    mov dx,0
    biti:
        mov al,0
        rol bl,1
        adc al,0
        
        push ecx
        putere:
            mul byte[doi]
        loop putere
        add dx,ax
        pop ecx
        
    loop biti
   
    mov eax,0
    mov ax,dx
    
    mov edi,numar
    cld
    impartiri:
        div byte [opt]
        mov dl,al
        mov al,ah
        stosb
        mov al,dl
        mov ah,0
        cmp al,0
        jne impartiri
    iesi:
    pop ecx
    
    ret
%endif