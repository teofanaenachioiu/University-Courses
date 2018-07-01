bits 32

global start,sFinal 
       
extern exit,scanf,printf
extern concatenare 
extern citire 
            
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll
            
segment data use32 data
    ;mesaj db "Dati sirul %d: ",0
    ;format db "%s",0
    s1 times 30 db 0
    s2 times 30 db 0
    s3 times 30 db 0
    sFinal times 100 db 0
    mesajFinal db "Sirul rezultat prin concatenare este: %s",0
 
segment code use32 public code
    start:
        mov ebx, 1
            
        mov eax,s1
        call citire
           
        mov eax,s2
        call citire

        mov eax,s3
        call citire
        
        mov eax, s1
        mov ebx, s2
        mov edx, s3
        call concatenare
        
        push dword sFinal
        push dword mesajFinal
        call [printf]
        add esp, 4*2
        
        push dword 0     
        call [exit]     
