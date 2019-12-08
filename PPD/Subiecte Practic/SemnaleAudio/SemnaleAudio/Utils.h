#pragma once
#include<string>
using namespace std;

class Utils
{
public:
	Utils();
	~Utils();
	static int readNumbersFromFile(int * vect, string file_name);
	static void writeNumberInFile(int number, string file_name);
};

