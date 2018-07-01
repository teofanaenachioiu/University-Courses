;Sa se citeasca de la tastatura doua numere a si b (in baza 10) si sa se calculeze: (a+b) / (a-b). Catul impartirii se va salva in memorie in variabila "rezultat" (definita in segmentul de date). Valorile se considera cu semn.

bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf,scanf              ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import scanf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dd 0; ...
    b dd 0
    mesaj1 db "a=",0
    mesaj2 db "b=",0
    format db "%d",0
    rezultat resd 1
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push dword mesaj1
        call [printf]
        add esp, 4*1
        
        push dword a      
        push dword format
        call [scanf]       
        add esp, 4 * 2 
        
        push dword mesaj2
        call [printf]
        add esp, 4*1
        
        push dword b      
        push dword format
        call [scanf]       
        add esp, 4 * 2 
        
        mov eax,[a] ;eax = a
        add eax,[b] ;eax = a+b
        cdq ;edx:eax = a+b
        mov ebx,[a]
        sub ebx,[b] ;ebx = a-b 
        idiv ebx ;eax = (a+b)/(a-b)
        mov [rezultat], ebx
        
       
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
