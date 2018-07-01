bits 32
global permutare
extern numar
segment code public use32 code
  
    call numarare
    ;ebx tine numar de cifre a numarului
    permutare:
        mov dx,word[numar]
        mov ax,word[numar+2]
        mov cx,10
        div cx
            