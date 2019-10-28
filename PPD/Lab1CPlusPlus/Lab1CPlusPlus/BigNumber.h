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
	void computeMultiply(int start, int end, BigNumber &smallNumber, BigNumber &largeNumber, BigNumber &result);
	void computeMultiply1(int start, int end);
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
	int addDigit(int position, int value);
	BigNumber addSeqential(BigNumber otherNumber);
	BigNumber multiplySequentially(BigNumber otherNumber);
	BigNumber multiplyParallel(BigNumber otherNumber, int no_threads);
	BigNumber addParallelClassification(BigNumber otherNumber, int no_threads);
	BigNumber addParallelOptimised(BigNumber otherNumber, int no_threads);
	void printBigNumber();
};

