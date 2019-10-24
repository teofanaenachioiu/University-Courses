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
	int no_th = 1;
	srand(time(NULL));
	Utils::createNewFile("input.txt", 2, 500000, 500010);

	vector<BigNumber> bigNumbers = Utils::readBigNumbersFromFile("input.txt");

	BigNumber bn1 = bigNumbers.at(0);
	BigNumber bn2 = bigNumbers.at(1);

	auto startTime = chrono::steady_clock::now();
	BigNumber sequentialSum = bn1.addSeqential(bn2);
	auto endTime = chrono::steady_clock::now();
	Utils::writeBigNumberInFile(sequentialSum, "seqvential.txt");
	auto diff = endTime - startTime;
	cout << "Time: " << chrono::duration<double, milli>(diff).count() << " ms" << endl;

	auto startTime1 = chrono::steady_clock::now();
	BigNumber parallelSum  = bn1.addParallelOptimised(bn2,no_th);
	auto endTime1 = chrono::steady_clock::now();
	Utils::writeBigNumberInFile(parallelSum, "parallel.txt");

	auto diff1 = endTime1 - startTime1;
	cout << "Time: " << chrono::duration<double, milli>(diff1).count() << " ms" << endl;

	auto startTime2 = chrono::steady_clock::now();
	BigNumber parallelV2Sum = bn1.addParallelClassification(bn2, no_th);
	auto endTime2 = chrono::steady_clock::now();


	Utils::writeBigNumberInFile(parallelV2Sum, "parallelV2.txt");

	auto diff2 = endTime2 - startTime2;
	cout << "Time: " << chrono::duration<double, milli>(diff2).count() << " ms" << endl;

}
