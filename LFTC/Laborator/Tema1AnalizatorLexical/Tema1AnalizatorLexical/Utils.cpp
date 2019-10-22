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

vector<string> Utils::getStringsLine(string filename)
{
	string line;
	vector<string> keywords;
	ifstream myfile(filename);
	if (myfile.is_open())
	{
		while (getline(myfile, line))
		{
			keywords.push_back(line);
		}
		myfile.close();

	}

	else cout << "Unable to open file";
	return keywords;
}



void Utils::printStrings(vector<string> vector)
{
	for (int i = 0; i < vector.size(); i++) {
		cout << vector[i] << " ";
	}
	cout << endl;
}

vector<string>  Utils::split(string const &input) {
	istringstream buffer(input);
	vector<string> ret((istream_iterator<string>(buffer)),
		istream_iterator<string>());
	return ret;
}