// ConsoleApplication1.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include <mpi.h>


void generate(int a[100]) {
	for (int i = 0; i < 100; i++) {
		a[i] = i;
	}
}

int main()
{
	int a[100], min, max, rez[100], n=100;
	int nrProcese, idProces;// in cazul asta, astea doua se creeaza in fiecare proces, idProces = rank la profu

	MPI_Status status;

	int rc = MPI_Init(NULL, NULL); //sau argc si argv , aici creez contextul mpi
	if (rc != MPI_SUCCESS) {
		std::cout << "ERRRRORARE! Hopa ceva nu e ok la init mpi" << std::endl;
		MPI_Abort(MPI_COMM_WORLD, rc);
	}
	MPI_Comm_size(MPI_COMM_WORLD, &nrProcese);
	MPI_Comm_rank(MPI_COMM_WORLD, &idProces);
	//std::cout << idProces << " from " << nrProcese << std::endl;

	if (idProces == 0)
	{
		generate(a);
		int* mins = new int [nrProcese];
		int* maxs = new int [nrProcese];
		int dimPerProces = n / (nrProcese -1);
		int rest = n % (nrProcese-1);
		int start = 0;
		for (int i = 1; i < nrProcese; i++) {
			int cate = dimPerProces;
			if (rest != 0) {
				rest--;
				cate++;
			}
			MPI_Send(&cate, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
			//std::cout << "Am trims numarul " << cate << std::endl;
			MPI_Send(a + start, cate, MPI_INT, i, 0, MPI_COMM_WORLD);

			std::cout << "Am trims numarul si array-ul la procesul " << i << std::endl;
			start = start + cate;
		}


		dimPerProces = n / (nrProcese - 1);
		rest = n % (nrProcese - 1);
		start = 0;
		for (int i = 1; i < nrProcese; i++) {
			int cate = dimPerProces;
			if (rest != 0) {
				rest--;
				cate++;
			}
			MPI_Recv(a + start, cate, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
			MPI_Recv(mins + i - 1, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
			MPI_Recv(maxs + i - 1, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
			start = start + cate;
		}
		int min = 9999999;
		int max = 0;
		for (int i = 0; i < nrProcese - 1; i++) {
			std::cout << mins[i] << std::endl;
			std::cout << maxs[i] << std::endl;
			if (mins[i] < min) {
				min = mins[i];
			}
			if (maxs[i] > max) {
				max = maxs[i];
			}
		}
		std::cout << min << std::endl;
		std::cout << max << std::endl;
		/*for (int i = 0; i < n; i++) {
			std::cout << a[i] << std::endl;
		}*/
	}
	else
	{
		int b[100];
		int cate = 0;// aici pun ce primesc si e doar pentru procesul 1 e
		MPI_Recv(&cate, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		//std::cout << "Am primit un numar: " << cate << std::endl;
		MPI_Recv(b, cate, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		std::cout << "Am primit un array: " << std::endl;
		int min = 0;
		int max = 999;
		for (int i = 0; i < cate; i++) {
			if (b[i] < min) min = b[i];
			if (b[i] > max) max = b[i];
			b[i] = b[i] * 10;
		}

		MPI_Send(b, cate, MPI_INT, 0, 0, MPI_COMM_WORLD);
		MPI_Send(&min, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
		MPI_Send(&max, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
	}

	MPI_Finalize(); // distrugem contextul mpi
}
