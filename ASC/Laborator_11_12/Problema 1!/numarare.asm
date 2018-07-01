bits 32 
global numarare
extern numar
segment code public use32 code
    numarare:
        mov dx,word[numar]
        mov ax,word[numar+2]
        mov ebx,0
        mov cx,10
        .repeta:
            div cx
            inc ebx
            cmp ax,0
            jne .repeta
        ret
    