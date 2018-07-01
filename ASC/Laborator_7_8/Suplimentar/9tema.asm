
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    sir db "bcdefgha"
    len equ $-sir
    var db 0
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
    sub BL,BL ;bl=0
    Add BL,[sir+len-1] ;bl=ultimul element(cel care trebuie mutat pe prima pozitie)
    Add [var],BL ;[var]=bl=ultimul element
    
    sub Ecx,ECx ;ecx=0
    add ECX,sir+len-1 ;ecx=pozitia ultimului element
    
    aici:
        Sub DL,DL ;dl=0
        Add DL,[ecx] ;dl=[ecx]=un caracter, incepand cu ultimul
        Sub [ecx],DL ;[ecx]=0
        
        Sub ECX,1 ;pozitia anterioara
        Sub AL,AL ;al=0
        Add AL,[ecx] ;al=[ecx]=un caracter, incepand cu penultimul
        
        Add ECx,1 ;pozitia urmatoara
        Add [ECX],AL ;pun pe pozitia umatoare elementul curent
        
        Sub ECX,1 ;pregatesc pentru umatoarea prelucrare
        cmp ECX,sir
    jne aici
    ;pun pe prima pozitie 0
    Sub AL,AL ;al=0
    Add AL,[sir] ;al=primul element
    Sub[sir],AL ;primul element=0
    ;pun pe prima poztie [var]
    Sub Al,Al ;al=0
    Add AL,[var] ;al=[var]
    Add [sir],AL ;[sir]=al=[var]
    
    
    ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
