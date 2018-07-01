nasm -fobj main.asm
nasm -fobj concatenare.asm
pause
alink main.obj concatenare.obj -oPE -subsys console -entry start
pause
main
pause