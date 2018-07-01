bits 32

extern sFinal 
              
segment code use32 public code
    global concatenare
    
    concatenare:
        mov edi, sFinal         ; destinatie = sFinal
        mov esi, eax            ; sursa = primul sir
            
        .sir1Loop:
            lodsb               ; luam octetul urmator
            test al, al         ; este terminatorul de sir (=0)?
            jz .sir2            ; daca da, trecem la sirul al doilea
            stosb               ; (altfel) copiem in destinatie
            jmp .sir1Loop       ; si continuam pana la nul
            
        .sir2:
            mov esi, ebx        ; sursa = sirul al doilea
            
        .sir2Loop:
            lodsb               ; acelasi proces pentru noul sir
            test al, al
            jz .sir3
            stosb
            jmp .sir2Loop
        
        .sir3:
            mov esi, edx        ; sursa = sirul al treilea
            
        .sir3Loop:
            lodsb               ; acelasi proces pentru noul sir
            test al, al
            jz .gata
            stosb
            jmp .sir3Loop
            
        .gata:
            mov al,0
            stosb               ; adaugam terminatorul de sir din al
            ret

