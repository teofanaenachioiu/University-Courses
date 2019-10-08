@echo off
:: Syntax - repeat [file_name] [loop_count]

set file_name=%1
set loopcount=%2


set to_compile=%file_name%.cpp
set to_execute=%file_name%.exe

g++ %to_compile% -o %to_execute%

FOR /L %%A IN (1,1,%loopcount%) DO (		
	%to_execute%
)


pause