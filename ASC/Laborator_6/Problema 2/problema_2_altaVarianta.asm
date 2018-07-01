bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll                           ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s1 db 1, 2, 3, 4
    s2 db  5, 6, 7, 8
    l equ $-s2
    dest times l db 0
    mesaj dd "Sirul este: ",0
    format db "%d ",0 
segment code use32 class=code
    start:
        mov ecx, l ;Retinem lungimea sirului in ECX 
        mov esi, 0 ;Retinem in ESI pozitia curenta (initial 0)
        
        jecxz Sfarsit ;Verificam ECX != 0, pentru a nu intra in bucla infinita
        
        push mesaj 
        call[printf] ;Apelam functia printf care va afisa mesajul
        add esp,4*1 ;Eliberam stiva
        
        Repeta:
            mov al,[s1+esi] ;al = s1 [esi]
            mov bl,[s2+esi] ;bl = s2 [esi]
            
            test esi,1 ; esi AND 1 => 1(ESI impar), 0 (ESI par)
            
            jnz impar ;ZF=0 se sare la eticheta impar
            jz par ;ZF=1 se sare la eticheta par
            
            par: 
                add al,bl ;al = al + bl
                jmp iesire ;elementul de adaugat(al) in sirul destinatie e calculat si se sare la ultimul pas - adaugarea in sir
            
            impar:
                sub al,bl ;al = al - bl
                
            iesire:
                mov [dest+esi],al ;dest[esi] = al
                inc esi ;esi = esi+1 (pregatim poziti pentru urmatorul element)
        loop Repeta        
          
        mov edi,0
        mov ecx,l
        afisare:
            mov al,[dest+edi]
            cbw ;al -> ax
            cwde ;ax -> eax
            
            push dword eax
            push dword format
            call [printf] ;Apelam functia printf care va afisa elementul
            add esp, 4*1 ;Eliberam stiva   
        loop afisare
        
        Sfarsit:
        push dword 0      ; push the parameter for exit onto the stack
        call [exit]       ; call exit to terminate the program
