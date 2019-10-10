

#include "pch.h"
#include <iostream>
#include <fstream>
#include <string>
#include <time.h>

using namespace std;

void createNewFile(string file_name, int size, int min, int max)
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
	srand(time(NULL));
	createNewFile(file_name, size, min, max);
}
