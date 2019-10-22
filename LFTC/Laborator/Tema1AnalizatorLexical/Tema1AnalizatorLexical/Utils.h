#pragma once
#include <string>
#include <vector>
using namespace std;

class Utils
{
public:
	Utils();
	~Utils();
	static vector<string> getStringsLine(string filename);
	static void printStrings(vector<string> vector);
	static vector<string>  split(string const &input);
};

