#include "pch.h"
#include "Utils.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <regex>

using namespace std;

Utils::Utils()
{
}


Utils::~Utils()
{
}

vector<string>  Utils::split(string const &input) {
	istringstream buffer(input);
	vector<string> ret((istream_iterator<string>(buffer)),
		istream_iterator<string>());
	return ret;
}