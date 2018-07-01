bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,rename,fopen,fclose,remove               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import rename msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import remove msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    nume_fisier db "old.txt",0
    nume_nou db "nume.txt",0
    mod_acces db "w",0
    descriptor dd -1
    desc dd -1
segment code use32 class=code
    start:
        push dword mod_acces
        push dword nume_fisier
        
        call [fopen]
        add esp,8
        
        cmp eax,0
        je final
        
        mov [descriptor],eax
        
        push dword [descriptor]
        call [fclose]
        add esp,4
        
        push dword [descriptor]
        call [remove]
        add esp,4
        
        push dword nume_nou
        push dword nume_fisier
        call [rename]
        add esp,8    
        
        cmp eax,0
        je final
        
        mov [desc],eax
        
        push dword [desc]
        call [fclose]
        add esp,4
        
        final:
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
