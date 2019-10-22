#pragma once
#include <vector>
using namespace std;

class BigNumber
{
private:
	vector<int> digits;
	void compute(int start, int end, int no_thread, BigNumber& largeNumber, BigNumber& smallNumber, vector<int>& carries);
	void computeAdd(int start, int end, int no_thread, BigNumber& largeNumber, BigNumber& smallNumber, vector<int>& carries);
	void computeClassification(int start, int end, int no_thread, BigNumber &largeNumber, BigNumber &smallNumber, vector<int> &carries);
	void wait(vector<int>& carries);
public:
	BigNumber();
	BigNumber(vector<int> digits);
	BigNumber(int size);
	BigNumber(const BigNumber &otherNumber);
	~BigNumber();

	vector<int> getDigits();
	int getDigit(int index);
	int getSize();
	void setDigit(int index, int value);
	void addDigit(int value);
	BigNumber addSeqential(BigNumber otherNumber);
	BigNumber addParallelClassification(BigNumber otherNumber, int no_threads);
	BigNumber addParallelOptimised(BigNumber otherNumber, int no_threads);
	void printBigNumber();

};

