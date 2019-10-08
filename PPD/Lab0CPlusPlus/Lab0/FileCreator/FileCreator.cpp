

#include "pch.h"
#include <iostream>
#include <fstream>
#include <string>

using namespace std;

void createNewFile(string file_name, int size, int min, int max)
{
	ofstream outfile(file_name);
	int number;

	for (int i = 0; i < size; i++) {
		number = rand() % (max - min + 1) + min;
		outfile << number << " ";
	}

	outfile.close();
}

void swap(int *a, int *b) {
	int *tmp = a;
	a = b;
	b = tmp;
}

int main()
{
	string file_name;
	int size, min, max;

	cout << "File name: "; getline(cin, file_name);
	cout << "Size: "; cin >> size;
	cout << "Min: "; cin >> min;
	cout << "Max: "; cin >> max;

	if (min > max) {
		swap(min, max);
	}

	createNewFile(file_name, size, min, max);
}
