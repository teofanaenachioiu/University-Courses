@echo off
:: Syntax - hello [file_name] [loop_count]

set file_name=%1
set loopcount=%2

javac %file_name%

FOR /L %%A IN (1,1,%loopcount%) DO ( java %file_name% )

pause