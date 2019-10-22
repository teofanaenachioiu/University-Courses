#include "pch.h"
#include "BigNumber.h"
#include <thread>
#include <iostream>
#include <mutex>              // std::mutex, std::unique_lock
#include <condition_variable> // std::condition_variable

using namespace std;

BigNumber::BigNumber()
{
	digits = vector<int>();
}

BigNumber::BigNumber(vector<int> digits)
{
	this->digits = digits;
}

BigNumber::BigNumber(int size)
{
	digits = vector<int>(size);
}

BigNumber::BigNumber(const BigNumber & otherNumber)
{
	digits = otherNumber.digits;
}


BigNumber::~BigNumber()
{
}

vector<int> BigNumber::getDigits()
{
	return this->digits;
}

int BigNumber::getDigit(int index)
{
	/*if (index < 0 || index >= this->digits.size())
		return -1;*/
	return this->digits.at(index);
}

int BigNumber::getSize()
{
	return this->digits.size();
}

void BigNumber::setDigit(int index, int value)
{
	this->digits.at(index) = value;
}

void BigNumber::addDigit(int value)
{
	this->digits.push_back(value);
}

BigNumber BigNumber::addSeqential(BigNumber otherNumber)
{
	BigNumber largeNumber = BigNumber();
	BigNumber smallNumber = BigNumber();

	if (this->getSize() > otherNumber.getSize()) {
		largeNumber.digits = this->digits;
		smallNumber.digits = otherNumber.digits;
	}
	else
	{
		largeNumber.digits = otherNumber.digits;
		smallNumber.digits = this->digits;
	}

	int index, sumDigits, carry = 0;

	for (index = 0; index < smallNumber.getSize(); index++) {
		sumDigits = largeNumber.getDigit(index) + smallNumber.getDigit(index) + carry;
		largeNumber.setDigit(index, sumDigits % 10);
		carry = sumDigits / 10;
	}

	while (index < largeNumber.getSize() && carry == 1) {
		sumDigits = largeNumber.getDigit(index) + carry;
		largeNumber.setDigit(index, sumDigits % 10);
		carry = sumDigits / 10;
		index++;
	}

	if (carry == 1) {
		largeNumber.addDigit(1);
	}

	return largeNumber;
}



void BigNumber::compute(int start, int end, int no_thread, BigNumber &largeNumber, BigNumber &smallNumber, vector<int> &carries) {

	int index, sumDigits, carry = 0;

	for (index = start; index < end; index++) {
		sumDigits = largeNumber.getDigit(index) + smallNumber.getDigit(index) + carry;
		largeNumber.setDigit(index, sumDigits % 10);
		carry = sumDigits / 10;
	}
	if (no_thread == carries.size() - 1 && carry == 1) {
		index = end;
		end = largeNumber.getSize();
		while (carry == 1 && index < end) {
			sumDigits = largeNumber.getDigit(index) + carry;
			largeNumber.setDigit(index, sumDigits % 10);
			carry = sumDigits / 10;
			index++;
		}
		if (carry == 1) {
			largeNumber.addDigit(1);
		}
	}

	carries.at(no_thread) = carry;

	wait(carries);

	if (no_thread > 0 && carries[no_thread - 1] == 1) {
		carry = carries[no_thread - 1];
		index = start;
		while (carry == 1 && index < end) {
			sumDigits = largeNumber.getDigit(index) + carry;
			largeNumber.setDigit(index, sumDigits % 10);
			carry = sumDigits / 10;
			index++;
		}
		/*if (no_thread == carries.size() - 1 && carries[no_thread] == 1) {
			index = end;
			end = largeNumber.getSize();
			carry = carries[no_thread];
			while (carry == 1 && index < end) {
				sumDigits = largeNumber.getDigit(index) + carry;
				largeNumber.setDigit(index, sumDigits % 10);
				carry = sumDigits / 10;
				index++;
			}
			if (carry == 1) {
				largeNumber.addDigit(1);
			}
		}*/
	}
}



void BigNumber::wait(vector<int> &carries)
{
	while (find(carries.begin(), carries.end(), -1) != carries.end()) {
		continue;
	}
}

BigNumber BigNumber::addParallelOptimised(BigNumber otherNumber, int no_threads)
{
	BigNumber largeNumber = BigNumber();
	BigNumber smallNumber = BigNumber();


	if (this->getSize() > otherNumber.getSize()) {
		largeNumber.digits = this->digits;
		smallNumber.digits = otherNumber.digits;
	}
	else
	{
		largeNumber.digits = otherNumber.digits;
		smallNumber.digits = this->digits;
	}

	int MAX = smallNumber.getSize();
	int start = 0;
	int finish = MAX / no_threads;
	int cat = MAX / no_threads;
	int rest = MAX % no_threads;
	vector<thread> threads = vector<thread>(no_threads);
	vector<int> carries = vector<int>(no_threads, -1);

	for (int i = 0; i < no_threads; i++) {
		if (rest > 0) {
			rest--;
			finish++;
		}

		threads[i] = thread(&BigNumber::compute, this, start, finish, i, ref(largeNumber), ref(smallNumber), ref(carries));

		start = finish;
		finish = start + cat;
	}


	for (int i = 0; i < no_threads; i++) {
		threads[i].join();
	}

	return largeNumber;
}



void BigNumber::computeClassification(int start, int end, int no_thread, BigNumber &largeNumber, BigNumber &smallNumber, vector<int> &carries) {
	int sum;
	for (int index = end - 1; index >= start; index--) {
		sum = largeNumber.getDigit(index) + smallNumber.getDigit(index);
		if (sum > 9) {
			carries[no_thread] = 1;
			break;
		}
		else if (sum < 9) {
			carries[no_thread] = 0;
			break;
		}
		else {
			carries[no_thread] = 2;
		}
	}

	if (no_thread == carries.size() - 1 && carries[no_thread] != 0) {
		start = end;
		end = largeNumber.getSize();
		int carry = carries[no_thread];
		for (int index = start; index < end; index++) {
			sum = largeNumber.getDigit(index);
			if (carry == 1) {
				sum++;
			}
			if (sum > 9) {
				carries[no_thread] = 1;
				break;
			}
			else if (sum < 9) {
				carries[no_thread] = 0;
				break;
			}
			else {
				carry = sum / 10;
				carries[no_thread] = 2;
			}
		}
	}

}

void BigNumber::computeAdd(int start, int end, int no_thread, BigNumber & largeNumber, BigNumber & smallNumber, vector<int>& carries)
{
	int index, sumDigits, carry = 0;
	if (no_thread > 0) {
		carry = carries[no_thread - 1];
	}

	for (index = start; index < end; index++) {
		sumDigits = largeNumber.getDigit(index) + smallNumber.getDigit(index) + carry;
		largeNumber.setDigit(index, sumDigits % 10);
		carry = sumDigits / 10;
	}

	if (no_thread == carries.size() - 1) {
		end = largeNumber.getSize();
		while (index<end && carry == 1) {
			sumDigits = largeNumber.getDigit(index) + carry;
			largeNumber.setDigit(index, sumDigits % 10);
			carry = sumDigits / 10;
			index++;
		}
	}
}

BigNumber BigNumber::addParallelClassification(BigNumber otherNumber, int no_threads)
{
	BigNumber largeNumber = BigNumber();
	BigNumber smallNumber = BigNumber();


	if (this->getSize() > otherNumber.getSize()) {
		largeNumber.digits = this->digits;
		smallNumber.digits = otherNumber.digits;
	}
	else
	{
		largeNumber.digits = otherNumber.digits;
		smallNumber.digits = this->digits;
	}

	int MAX = smallNumber.getSize();
	int start = 0;
	int finish = MAX / no_threads;
	int cat = MAX / no_threads;
	int rest = MAX % no_threads;
	vector<thread> threads = vector<thread>(no_threads);
	vector<int> carries = vector<int>(no_threads, -1);

	for (int i = 0; i < no_threads; i++) {
		if (rest > 0) {
			rest--;
			finish++;
		}

		threads[i] = thread(&BigNumber::computeClassification, this, start, finish, i, ref(largeNumber), ref(smallNumber), ref(carries));

		start = finish;
		finish = start + cat;
	}

	for (int i = 0; i < no_threads; i++) {
		threads[i].join();
	}

	start = 0;
	finish = MAX / no_threads;
	cat = MAX / no_threads;
	rest = MAX % no_threads;

	for (int i = 0; i < no_threads; i++) {


		if (rest > 0) {
			rest--;
			finish++;
		}

		if (i > 0 && carries[i] == 2) {
			carries[i] = carries[i - 1];
		}
		threads[i] = thread(&BigNumber::computeAdd, this, start, finish, i, ref(largeNumber), ref(smallNumber), ref(carries));

		start = finish;
		finish = start + cat;
	}

	for (int i = 0; i < no_threads; i++) {
		threads[i].join();
	}
	if (carries[no_threads - 1] == 1) {
		largeNumber.addDigit(1);
	}
	return largeNumber;
}

void BigNumber::printBigNumber()
{
	for (int index = 0; index < this->getSize(); index++) {
		cout << this->getDigit(index);
	}
	cout << endl;
}