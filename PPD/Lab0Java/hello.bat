@echo off
:: Syntax - hello[file_name] [loop_count]

set file_name=%1
set loopcount=%2

set full_path=src/main/java/%file_name%

javac %full_path% 

FOR /L %%A IN (1,1,%loopcount%) DO ( java %full_path% )



pause