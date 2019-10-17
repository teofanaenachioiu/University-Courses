// Seminar2.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>       // std::cout
#include <thread>         // std::thread
#include <chrono>         

// this_thread (namespace ce are mai multe functii folositoare pentru thread uri)
// false sharing

using namespace std;
 
struct
alignas(16)
Element {
	uint32_t val;
};

const int MAX = 100, P = 4;
thread* threads = new thread[P];

int a[MAX], b[MAX], c[MAX];

void compute(int start, int end, int a[], int b[], int c[]) {
	for (int index = start; index < end; index++) {
		c[index] = a[index] + b[index];
	}
}

void computeV2(int start, int step, int a[], int b[], int c[]) {
	for (int index = start; index < MAX; index = index + step) {
		c[index] = a[index] + b[index];
	}
}

int main()
{
	for (int index = 0; index < MAX; index++) {
		a[index] = index;
		b[index] = MAX - index;
	}
	
	// PARALEL LINIAR

	auto startTime = chrono::steady_clock::now();
	int start = 0;
	int finish = MAX / P;
	int cat = MAX / P;
	int rest = MAX % P;

	for (int i = 0; i < P; i++) {
		if (rest > 0) {
			rest--;
			finish++;
		}

		threads[i] = thread(compute, start, finish, a, b, c);

		start = finish;
		finish = start + cat;
	}


	for (int i = 0; i < P; i++) {
		threads[i].join();
	}

	auto endTime = chrono::steady_clock::now();

	auto diff = endTime - startTime;
	cout << "Computation time paralel - distributie liniara: " << chrono::duration<double, milli>(diff).count() << " ms" << endl;

	// PARALEL CICLIC

	startTime = chrono::steady_clock::now();

	for (int i = 0; i < P; i++) {
		threads[i] = thread(computeV2, i, P, a, b, c);
	}


	for (int i = 0; i < P; i++) {
		threads[i].join();
	}

    endTime = chrono::steady_clock::now();

	diff = endTime - startTime;
	cout << "Computation time paralel - distributie ciclica: " << chrono::duration<double, milli>(diff).count() << " ms" << endl;

	// SECVENTIAL

	startTime = chrono::steady_clock::now();
	for (int i = 0; i < MAX; i++) {
		c[i] = a[i] + b[i];
	}

	endTime = chrono::steady_clock::now();
	diff = endTime - startTime;
	cout << "Computation time secvential: " << chrono::duration<double, milli>(diff).count() << " ms" << endl;

	return 0;
}
