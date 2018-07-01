bits 32
global secventa
extern sir,subsir
segment data use32 public
    
    max db -1
segment code use32 public
    secventa:
        mov esi,sir
        mov edi,subsir
        bucla:
            lodsd
            mov [max],al
            shr eax,8
            cmp al,[max]
            jna next1
            mov [max],al
            next1:
            shr eax,8
            cmp al,[max]
            jna next2
            mov [max],al
            next2:
            shr eax,8
            cmp al,[max]
            jna next3
            mov [max],al
            next3:
            mov al,[max]
            stosb
            
        loop bucla
        
        ret