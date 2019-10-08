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