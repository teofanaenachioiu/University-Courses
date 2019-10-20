#include "pch.h"
#include "Utils.h"
#include <iostream>
#include <fstream>
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
