// FileComparator.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>

#include "FileComparator.h"

using namespace std;

bool compareFiles(string file_name1, string file_name2) {
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

int main()
{
	string file_name1, file_name2;

	cout << "File name1: "; getline(cin, file_name1);
	cout << "File name2: "; getline(cin, file_name2);


	bool is_same = compareFiles(file_name1, file_name2);
	cout << is_same << endl;
	
}