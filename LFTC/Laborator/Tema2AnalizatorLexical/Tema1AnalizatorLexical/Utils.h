#pragma once
#include <string>
#include <vector>
#include "StateMachine.h"
using namespace std;

class Utils
{
public:
	Utils();
	~Utils();
	static vector<string> getStringsLine(string filename);
	static void printStrings(vector<string> vector);
	static vector<string>  split(string const &input);
	static string& ltrim(std::string& s, const char* t = " \t\n\r\f\v");
	static string& rtrim(std::string& s, const char* t = " \t\n\r\f\v");
	static string& trim(std::string& s, const char* t = " \t\n\r\f\v");
};

