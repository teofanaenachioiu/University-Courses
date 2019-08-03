win_flex pascal.lxi
win_bison -dy pascal.y
gcc lex.yy.c y.tab.c -o analizor
