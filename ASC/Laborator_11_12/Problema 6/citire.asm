bits 32

extern printf,scanf     
import printf msvcrt.dll
import scanf msvcrt.dll

segment data use32 data    
    mesaj db "Dati sirul %d: ",0
    format db "%s",0
segment code use32 public code
    global citire
    
    citire:
        pushad
        push ebx
        push dword mesaj
        call [printf]
        add esp,4*2  
        
        popad
        push eax
        push dword format
        call [scanf]
        add esp, 4*2

        inc ebx               ; adaugam terminatorul de sir din al
        ret

