// CsvAdd.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include <fstream>
#include <string>

using namespace std;

int readRow() {
	int row;
	cout << "Row: "; cin >> row;
	return row;
}

string readContent() {
	string content;
	cout << "Content: "; getline(cin, content);
	return content;
}


int main()
{
	ifstream fin("test.csv");
	ofstream fout("tmp.csv");

	if (fin.is_open()) {
		string content = readContent();

		int row = readRow();

		int index = 0;
		string line;

		while (fin >> line) {
			if (index == row) {
				fout << content<<endl;
			}
			fout << line << endl;
			index++;
		}
		fin.close();

		if (index < row) {
			fout << content << endl;
		}
		fout.close();

		remove("test.csv");

		rename("tmp.csv", "test.csv");
		
	}
	else {
		cout << "File not found" << endl;
	}
}
