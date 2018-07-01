;Se da un numar natural a (a: dword, definit in segmentul de date). Sa se citeasca un numar natural b si sa se calculeze: a + a\b. Sa se afiseze rezultatul operatiei. Valorile vor fi afisate in format decimal (baza 10) cu semn.

bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,scanf,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    a dd 0f5h
    b times 1 dd 0
    format db "%d",0
    mesaj db "Dati un numar: ",0
    final db "Rezultat: %d",0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push dword b
        push dword format
        call [scanf]
        add esp, 4*2
        
        mov edx,0
        mov eax,[a]
        idiv dword[b]
        
        add eax,dword[a]
        adc edx,0
        
        push eax
        push final
        call [printf]
        add esp,4*2
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
