;Se da un numar a reprezentat pe 32 biti fara semn. Se cere sa se afiseze reprezentarea in baza 16 a lui a, precum si rezultatul permutarilor circulare ale cifrelor sale.

bits 32 

global start,numar    

extern exit, reprezentare              
import exit msvcrt.dll   

segment data use32 public data
    numar dd 1234567
segment code use32 public code
    start:
        call reprezentare
          
        push    dword 0     
        call    [exit]   
