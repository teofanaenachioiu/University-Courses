%ifndef  _FACTORIAL_ASM_ ; continuam daca _FACTORIAL_ASM_ este nedefinit
%define  _FACTORIAL_ASM_ ; si ne asiguram ca devine definit
;  astfel %include permite doar o singura includere
;definire  procedura
factorial:   ; int _stdcall factorial(int n)
	mov eax, 1
	mov ecx, [esp + 4]
	;  mov ecx, [esp + 4] scoate de pe stiva parametrul procedurii
	; pentru explicatii a se vedea  lab11_procedura.asm
	repet: 
		mul ecx
	loop repet ; atentie, cazul ecx  = 0 nu e tratat!
	ret 4
%endif