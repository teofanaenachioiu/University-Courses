#include "pch.h"
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>
#include "Utils.h"
#include "BigNumber.h"

using namespace std;

void Utils::createNewFile(string file_name, int size, int min, int max)
{
	ofstream outfile(file_name);
	int nr_cifre;
	int cifra;

	for (int i = 0; i < size; i++) {

		nr_cifre = rand() % (max - min + 1) + min;

		for (int j = 0; j < nr_cifre; j++) {
			if (j == 0) {
				cifra = rand() % 9 + 1;
			}
			else {
				cifra = rand() % 10;
			}
			outfile << cifra;
		}

		outfile << endl;
	}

	outfile.close();
}

vector<BigNumber> Utils::readBigNumbersFromFile(string file_name)
{
	vector<BigNumber> bigNumbers = vector<BigNumber>();
	ifstream file(file_name);

	if (file.is_open())
	{
		string line;

		for (string line; getline(file, line); )
		{
			vector<int> arr = vector<int>();
			int digit;
			for (unsigned i = 0; i < line.size(); i++) {
				digit = (int)line[i] - 48;
				arr.push_back(digit);
			}
			bigNumbers.push_back(BigNumber(arr));
		}
	}
	else {
		cout << "Unable to open file";
	}
	return bigNumbers;
}

void Utils::writeBigNumberInFile(BigNumber bigNumber, string file_name)
{
	ofstream outfile(file_name);
	for (int i = 0; i < bigNumber.getSize(); i++) {
		outfile << bigNumber.getDigit(i);
	}
}

bool Utils::compareFiles(string file_name1, string file_name2) {
	ifstream file1(file_name1);
	ifstream file2(file_name2);

	if (file1.is_open() && file2.is_open())
	{
		vector<string> arr1, arr2;
		string element;

		while (file1 >> element)
		{
			arr1.push_back(element);
		}
		file1.close();

		while (file2 >> element)
		{
			arr2.push_back(element);
		}
		file2.close();

		sort(arr1.begin(), arr1.end());
		sort(arr2.begin(), arr2.end());
		return arr1 == arr2;
	}
	else {
		cout << "Unable to open file";
		return false;
	}
}

void swap(int *a, int *b) {
	int *tmp = a;
	a = b;
	b = tmp;
}
