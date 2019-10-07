@echo off
:: Syntax - repeat [java/cpp] [file_name] [loop_count]

set file_type=%1
set file_name=%2
set loopcount=%3

IF %file_type%==java (
    	set to_compile=%file_name%.java
	javac %to_compile%
	FOR /L %%A IN (1,1,%loopcount%) DO (		
  		java %file_name%
	)
)

IF %file_type%==cpp (
    	set to_compile=%file_name%.cpp
	set to_execute=%file_name%.exe
	g++ %to_compile% -o %to_execute%
	FOR /L %%A IN (1,1,%loopcount%) DO (		
  		%to_execute%
	)
)

pause