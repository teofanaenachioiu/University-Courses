// Lab1CPlusPlus.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include "BigNumber.h"
#include "Utils.h"
#include <iostream>
#include <vector>
#include <time.h>
#include <chrono>

int main()
{
	int no_th = 8;
	srand(time(NULL));
	//Utils::createNewFile("input.txt", 2, 100, 1100);

	vector<BigNumber> bigNumbers = Utils::readBigNumbersFromFile("input.txt");

	BigNumber bn1 = bigNumbers.at(0);
	BigNumber bn2 = bigNumbers.at(1);


	/*auto starttime = chrono::steady_clock::now();
	bn1.multiplySequentially(bn2);
	auto endtime = chrono::steady_clock::now();

	auto diff = endtime - starttime;
	cout << "time: " << chrono::duration<double, milli>(diff).count() << endl;
*/

	auto startTime1 = chrono::steady_clock::now();
	bn1.multiplyParallel(bn2,no_th);
	auto endTime1 = chrono::steady_clock::now();

	auto diff1 = endTime1 - startTime1;
	cout << "Time: " << chrono::duration<double, milli>(diff1).count() << endl;

}
