#include "pch.h"
#include "Utils.h"
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;
const int MAX = 10000000;

Utils::Utils()
{
}


Utils::~Utils()
{
}

int Utils::readNumbersFromFile(int * vect, string file_name)
{
	ifstream file(file_name);
	int index = 0;
	if (file.is_open())
	{
		string line;

		for (string line; getline(file, line); )
		{
			int numar = atoi(line.c_str());
			if (numar < 0) break;
			vect[index] = numar;
			index++;
		}
	}
	else {
		cout << "Unable to open file";
	}

	return index;
}

void Utils::writeNumberInFile(int  number, string file_name)
{
	ofstream outfile(file_name);
	outfile << number;
	cout << endl;
}
