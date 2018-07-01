bits 32 

global reprezentare

extern printf,numar  
import printf msvcrt.dll  

segment data use32 public data
    format db ' %x ',0
segment code use32 public code
    
    reprezentare:
        
        mov eax,dword[numar]
        push dword eax
        push dword format
        call [printf]
        add esp, 4*2
        
        ret 4     
