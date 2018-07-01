bits 32
global secventa,nr
extern subsir,sir
segment data use32 public 
    nr dd 0
    max dd 0
    poz dd 0
segment code use32 public
    secventa:
        mov esi,sir
        repeta:
            lodsb
            cmp al,0
            je next
            
            inc dword[nr]
            mov edx,[nr]
            cmp edx,dword[max]
            jna sari
            
            mov dword[max],edx
            mov ebx,esi
            sub ebx,sir
            sub ebx,dword[nr]
            mov dword [poz],ebx
            jmp sari
            
            next:
                mov dword[nr],0
            sari:
        loop repeta    
        
        mov edx,dword[max]
        cmp edx,0
        
        je final1
        
        mov esi,sir
        add esi,dword[poz]
        
        mov edi,subsir
        
        mov ecx,dword[max]
        rep movsb
        
        final1:
        mov edx,dword[max]
        mov dword[nr],edx
        ret