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


void Utils::createNewFile(string file_name, int size, int min, int max)
{
	ofstream outfile(file_name);
	int number;

	for (int i = 0; i < size; i++) {
		number = rand() % (max - min + 1) + min;
		outfile << number << " ";
	}

	outfile.close();
}

bool Utils::checkIsSameContentInFile(string file_name1, string file_name2)
{
	string line1;
	string line2;
	ifstream file1(file_name1);
	ifstream file2(file_name2);

	if (file1.is_open() && file2.is_open())
	{
		while (getline(file1, line1) && getline(file2, line2))
		{
			if (line1 != line2) {
				return false;
			}
		}
		if (getline(file1, line1) || getline(file2, line2)) {
			return false;
		}
		file1.close();
		file2.close();
	}
	else {
		cout << "Unable to open file";
		return false;
	}

	return true;
}

