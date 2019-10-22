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
	srand(time(NULL));
	Utils::createNewFile("input.txt", 2, 500000, 500002);

	vector<BigNumber> bigNumbers = Utils::readBigNumbersFromFile("input.txt");

	BigNumber bn1 = bigNumbers.at(0);
	BigNumber bn2 = bigNumbers.at(1);

	auto startTime = chrono::steady_clock::now();
	BigNumber sequentialSum = bn1.addSeqential(bn2);
	auto endTime = chrono::steady_clock::now();
	Utils::writeBigNumberInFile(sequentialSum, "seqvential.txt");
	auto diff = endTime - startTime;
	cout << "Time: " << chrono::duration<double, milli>(diff).count() << " ms" << endl;

	startTime = chrono::steady_clock::now();
	BigNumber parallelSum  = bn1.addParallelOptimised(bn2,4);
	endTime = chrono::steady_clock::now();
	Utils::writeBigNumberInFile(parallelSum, "parallel.txt");

	diff = endTime - startTime;
	cout << "Time: " << chrono::duration<double, milli>(diff).count() << " ms" << endl;

	startTime = chrono::steady_clock::now();
	BigNumber parallelV2Sum = bn1.addParallelClassification(bn2, 4);
	endTime = chrono::steady_clock::now();


	Utils::writeBigNumberInFile(parallelV2Sum, "parallelV2.txt");

	diff = endTime - startTime;
	cout << "Time: " << chrono::duration<double, milli>(diff).count() << " ms" << endl;

	bool equalsNumbers = Utils::compareFiles("parallelV2.txt", "parallel.txt");
	bool equalsNumbers1 = Utils::compareFiles("seqvential.txt", "parallel.txt");
	cout << equalsNumbers << endl;
	cout << equalsNumbers1 << endl;
}
