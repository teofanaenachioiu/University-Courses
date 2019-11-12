
#include "pch.h"
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <math.h>
#include "mpi.h"
#include "Utils.h"
#include "Lab3_MPI.h"

int numarCaractereFisier(std::ifstream& fin) {
	fin.seekg(0, fin.end);
	int lenght = fin.tellg();
	fin.seekg(0, fin.beg);
	return lenght;
}

void calculCountDispls(int*& sendcounts, int*& displs, int world_size, int size_nr_small)
{
	sendcounts = new int[world_size];
	displs = new int[world_size];

	int rest = size_nr_small % world_size;
	int displ = 0;

	for (int i = 0; i < world_size; i++) {
		sendcounts[i] = size_nr_small / world_size;
		if (rest > 0) {
			sendcounts[i]++;
			rest--;
		}
		displs[i] = displ;
		displ += sendcounts[i];
	}
}

void readDataFromFiles(int &size_1, int &size_2, int * &bigNumber1, int * &bigNumber2)
{
	std::string file1 = "nr1.txt";
	std::string file2 = "nr2.txt";

	std::ifstream fin1(file1);
	std::ifstream fin2(file2);

	size_1 = numarCaractereFisier(fin1);
	size_2 = numarCaractereFisier(fin2);

	fin1.close();
	fin1.clear();
	fin2.close();
	fin2.clear();

	if (size_1 > size_2) {
		int aux = size_1;
		size_1 = size_2;
		size_2 = aux;
		std::swap(file1, file2);
	}

	size_2++;

	bigNumber1 = new int[size_1] { 0 };
	bigNumber2 = new int[size_2] { 0 };

	fin1.open(file1);
	fin2.open(file2);

	char ch;

	for (int i = 0; i < size_1; i++) {
		fin1 >> ch;
		bigNumber1[size_1 - i - 1] = ch - 48;
	}
	for (int i = 0; i < size_2 - 1; i++) {
		fin2 >> ch;
		bigNumber2[size_2 - i - 2] = ch - 48;
	}
}

void addCarry(int * rezArray, int size) {
	int carry = 1;
	int sum;
	for (int i = 0; i < size; i++) {
		sum = rezArray[i] + carry;
		rezArray[i] = sum % 10;
		carry = sum / 10;
		if (carry == 0) {
			break;
		}
	}
}

void addCarry1(int * rezArray, int size) {
	int carry = 1;
	int sum;
	for (int i = size-1; i >=0; i--) {
		sum = rezArray[i] + carry;
		rezArray[i] = sum % 10;
		carry = sum / 10;
		if (carry == 0) {
			break;
		}
	}
}


void addCarry(int * rezArray, int start, int size) {
	int carry = 1;
	int sum;
	for (int i = start; i < start + size; i++) {
		sum = rezArray[i] + carry;
		rezArray[i] = sum % 10;
		carry = sum / 10;
		if (carry == 0) {
			break;
		}
	}
}

int shiftCarries(int * carries, int dim) {
	int overflow = carries[dim - 1];
	for (int i = dim - 1; i > 0; i--) {
		carries[i] = carries[i - 1];
	}
	carries[0] = 0;
	return overflow;
}

int shiftCarries2(int * carries, int dim) {
	int overflow = carries[0];
	for (int i = 0; i < dim - 1; i++) {
		carries[i] = carries[i + 1];
	}
	carries[dim - 1] = 0;
	return overflow;
}

void manageOverflow(int overflow, int size_1, int &size_2, int * bigNumber2)
{
	if (overflow == 1) {
		int carry = overflow;
		int i = size_1;
		int sum = 0;
		while (i < size_2 && carry == 1) {
			sum = bigNumber2[i] + carry;
			bigNumber2[i] = sum % 10;
			carry = sum / 10;
			i++;
		}
	}
	
}

void metoda1(int argc, char* argv[]) {
	int world_size;
	int rank;

	int* bigNumber1 = nullptr; // numarul mai mic
	int* bigNumber2 = nullptr; // numarul mai mare

	int i, n;
	int size_1; // dimensiunea numarului mai maic
	int size_2; // dimensiunea numarului mai mare

	int* sendcounts = nullptr;
	int* displs = nullptr;

	int* carries = nullptr;

	int carry;

	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &world_size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	int* buf_recv_1 = nullptr;
	int* buf_recv_2 = nullptr;

	carries = new int[world_size];

	if (rank == 0) {
		readDataFromFiles(size_1, size_2, bigNumber1, bigNumber2);
	}

	MPI_Bcast(&size_1, 1, MPI_INT, 0, MPI_COMM_WORLD);
	MPI_Bcast(&size_2, 1, MPI_INT, 0, MPI_COMM_WORLD);

	calculCountDispls(sendcounts, displs, world_size, size_1);

	buf_recv_1 = new int[sendcounts[rank]];
	buf_recv_2 = new int[sendcounts[rank]];

	MPI_Scatterv(bigNumber1, sendcounts, displs, MPI_INT, buf_recv_1, sendcounts[rank], MPI_INT, 0, MPI_COMM_WORLD);
	MPI_Scatterv(bigNumber2, sendcounts, displs, MPI_INT, buf_recv_2, sendcounts[rank], MPI_INT, 0, MPI_COMM_WORLD);

	carry = 0;
	for (i = 0; i < sendcounts[rank]; i++) {
		int sum = buf_recv_1[i] + buf_recv_2[i] + carry;
		buf_recv_2[i] = sum % 10;
		carry = sum / 10;
	}

	// adun carriurile
	MPI_Gather(&carry, 1, MPI_INT, carries, 1, MPI_INT, 0, MPI_COMM_WORLD);

	// verific daca am overflow. 
	// in caz afirmativ, pun procesul 0 sa lucreze

	if (rank == 0) {
		int overflow = shiftCarries(carries, world_size);

		//le shiftez cu o pozitie la stanga
		manageOverflow(overflow, size_1, size_2, bigNumber2);
	}

	MPI_Scatter(carries, 1, MPI_INT, &carry, 1, MPI_INT, 0, MPI_COMM_WORLD);

	if (carry == 1) {
		addCarry(buf_recv_2, sendcounts[rank]);
	}

	MPI_Gatherv(buf_recv_2, sendcounts[rank], MPI_INT, bigNumber2, sendcounts, displs, MPI_INT, 0, MPI_COMM_WORLD);

	if (rank == 0) {
		Utils::writeBigNumberInFile(bigNumber2, size_2, "result_1.txt");
		cout << "Done1" << endl;
	}

	MPI_Finalize();
}

void metoda2(int argc, char* argv[]) {
	char file1[] = "nr1.txt";
	char file2[] = "nr2.txt";

	int world_size;
	int rank;

	int* number_result = nullptr;

	int size_small, size_large;

	int* sendcounts = nullptr;
	int* displs = nullptr;

	int* carries = nullptr;

	std::ifstream fin1;
	std::ifstream fin2;

	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &world_size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	int* partial_result = nullptr;

	carries = new int[world_size];

	if (rank == 0) {

		fin1.open(file1);
		fin2.open(file2);

		size_small = numarCaractereFisier(fin1);
		size_large = numarCaractereFisier(fin2);


		fin1.close();
		fin1.clear();
		fin2.close();
		fin2.clear();

		if (size_small > size_large) {
			int aux = size_small;
			size_small = size_large;
			size_large = aux;
			std::swap(file1, file2);
		}

		size_large++;

		number_result = new int[size_large] { 0 };

	}

	MPI_Bcast(&size_small, 1, MPI_INT, 0, MPI_COMM_WORLD);
	MPI_Bcast(&size_large, 1, MPI_INT, 0, MPI_COMM_WORLD);

	MPI_Bcast(file1, sizeof(file1), MPI_CHAR, 0, MPI_COMM_WORLD);
	MPI_Bcast(file2, sizeof(file2), MPI_CHAR, 0, MPI_COMM_WORLD);

	int diff = size_large - size_small - 1;

	calculCountDispls(sendcounts, displs, world_size, size_small);

	partial_result = new int[sendcounts[rank]];

	fin1.open(file1);
	fin2.open(file2);

	int carry = 0;

	for (int i = 0; i < sendcounts[rank]; i++) {
		int currentPoz = displs[rank] + sendcounts[rank] - 1;
		fin1.seekg(currentPoz - i, fin1.beg);
		fin2.seekg(diff + currentPoz - i, fin2.beg);

		char chr1, chr2;
		fin1 >> chr1;
		fin2 >> chr2;

		int intChr1 = chr1 - '0';
		int intChr2 = chr2 - '0';

		int sum = intChr1 + intChr2 + carry;

		partial_result[sendcounts[rank] - 1 - i] = sum % 10;
		carry = sum / 10;
	}

	

	fin1.close();
	fin1.clear();
	fin2.close();
	fin2.clear();

	MPI_Gather(&carry, 1, MPI_INT, carries, 1, MPI_INT, 0, MPI_COMM_WORLD);

	for (int i = 0; i < world_size; i++) {
		displs[i] += diff + 1;
	}

	MPI_Gatherv(partial_result, sendcounts[rank], MPI_INT, number_result, sendcounts, displs, MPI_INT, 0, MPI_COMM_WORLD);

	if (rank == 0) {
		fin2.open(file2);
		for (int i = 1; i <= diff; i++) {
			char chr;
			fin2 >> chr;
			number_result[i] = chr - '0';
		}

		fin2.close();
		fin2.clear();

		int overflow = shiftCarries2(carries, world_size);

		if (overflow == 1) {
			int poz = diff;
			int carry = 1;
			while (carry == 1 && poz > 0)
			{
				int sum = number_result[poz] + carry;
				number_result[poz] = sum % 10;
				carry = sum / 10;
				poz--;
			}
			if (poz == 0) {
				number_result[0] = 1;
			}
		}
	}
	MPI_Scatter(carries, 1, MPI_INT, &carry, 1, MPI_INT, 0, MPI_COMM_WORLD);

	if (carry == 1) {
		cout << "carry la rank " << rank << endl;
		addCarry1(partial_result, sendcounts[rank]);
	}

	for (int i = 0; i < sendcounts[rank]; i++) {
		cout << "rank " << rank << ": " << partial_result[i] << endl;
	}

	MPI_Gatherv(partial_result, sendcounts[rank], MPI_INT, number_result, sendcounts, displs, MPI_INT, 0, MPI_COMM_WORLD);


	if (rank == 0) {
		Utils::writeBigNumberInFile2(number_result, size_large, "result_2.txt");
		cout << "Done2" << endl;

	}

	MPI_Finalize();
}

void f2(int argc, char* argv[]) {
	char file1[] = "nr1.txt";
	char file2[] = "nr2.txt";

	int total_proc;
	int rank;
	int n_per_proc;

	int* a = nullptr;

	int i, n;
	int size_a, size_b;

	int* sendcounts = nullptr;
	int* displs = nullptr;

	int* carries = nullptr;

	std::ifstream fin1;
	std::ifstream fin2;

	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &total_proc);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	int* ap = nullptr;
	int* bp = nullptr;

	carries = new int[total_proc];

	if (rank == 0) {

		fin1.open(file1);
		fin2.open(file2);

		size_a = numarCaractereFisier(fin1);
		size_b = numarCaractereFisier(fin2);

		fin1.close();
		fin1.clear();
		fin2.close();
		fin2.clear();

		if (size_a > size_b) {
			int aux = size_a;
			size_a = size_b;
			size_b = aux;
			std::swap(file1, file2);
		}

		size_b++;

		a = new int[size_b] { 0 };

	}

	MPI_Bcast(&size_a, 1, MPI_INT, 0, MPI_COMM_WORLD);
	MPI_Bcast(&size_b, 1, MPI_INT, 0, MPI_COMM_WORLD);

	MPI_Bcast(file1, sizeof(file1), MPI_CHAR, 0, MPI_COMM_WORLD);
	MPI_Bcast(file2, sizeof(file2), MPI_CHAR, 0, MPI_COMM_WORLD);

	int diff = size_b - size_a - 1;

	sendcounts = new int[total_proc];
	displs = new int[total_proc];

	int rest = size_a % total_proc;
	int displ = 0;

	for (i = 0; i < total_proc; i++) {
		sendcounts[i] = size_a / total_proc;
		if (rest > 0) {
			sendcounts[i]++;
			rest--;
		}
		displs[i] = displ;
		displ += sendcounts[i];
	}

	ap = new int[sendcounts[rank]];

	fin1.open(file1);
	fin2.open(file2);

	int carry = 0;

	for (int i = 0; i < sendcounts[rank]; i++) {
		int currentPoz = displs[rank] + sendcounts[rank] - 1;
		fin1.seekg(currentPoz - i, fin1.beg);
		fin2.seekg(diff + currentPoz - i, fin2.beg);

		char chr1, chr2;
		fin1 >> chr1;
		fin2 >> chr2;

		int intChr1 = chr1 - '0';
		int intChr2 = chr2 - '0';

		int sum = intChr1 + intChr2 + carry;

		ap[sendcounts[rank] - 1 - i] = sum % 10;
		carry = sum / 10;
	}

	fin1.close();
	fin1.clear();
	fin2.close();
	fin2.clear();

	MPI_Gather(&carry, 1, MPI_INT, carries, 1, MPI_INT, 0, MPI_COMM_WORLD);

	for (int i = 0; i < total_proc; i++) {
		displs[i] += diff + 1;
	}

	MPI_Gatherv(ap, sendcounts[rank], MPI_INT, a, sendcounts, displs, MPI_INT, 0, MPI_COMM_WORLD);

	if (rank == 0) {
		fin2.open(file2);
		for (i = 1; i <= diff; i++) {
			char chr;
			fin2 >> chr;
			a[i] = chr - '0';
		}

		fin2.close();
		fin2.clear();

		for (i = total_proc - 1; i >= 0; i--) {

			if (carries[i] == 1) {
				int poz;
				if (i == 0) {
					poz = diff;
				}
				else {
					poz = displs[i] - 1;
				}

				int carry = 1;
				while (carry == 1 && poz > 0)
				{
					int sum = a[poz] + carry;
					a[poz] = sum % 10;
					carry = sum / 10;
					poz--;
				}
				if (poz == 0) {
					a[0] = 1;
				}
			}
		}
		Utils::writeBigNumberInFile2(a, size_b, "result_2.txt");
		std::cout << std::endl;
		for (int i = 0; i < size_b; i++) {
			std::cout << a[i];
		}
		std::cout << std::endl;
	}

	MPI_Finalize();
}

int main(int argc, char* argv[])
{

	int min = 100;
	int max = 150;
	//Utils::createNewFile("nr1.txt", 1, min, max);
	//Utils::createNewFile("nr2.txt", 1, min, max);
	//metoda1(argc, argv);
	metoda2(argc, argv);
	bool theSame = Utils::compareFiles("result_1.txt", "result_2.txt");
	cout << theSame << endl;
}
