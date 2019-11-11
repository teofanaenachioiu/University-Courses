
#include "pch.h"
#include "mpi.h"
#include <time.h>
#include <iostream>
#include <math.h>

const int SIZE = 10;


int addNumbers(int *array1, int *array2, int* arrayRez, int size) {
	int carry = 0, sum;
	for (int i = 0; i < size; i++) {
		if (array1[i] == NULL) {
			array1[i] = 0;
		}
		if (array2[i] == NULL) {
			array2[i] = 0;
		}
		sum = array1[i] + array2[i] + carry;
		arrayRez[i] = sum % 10;
		carry = sum / 10;
	}
	return carry;
}


int addNumbers(int *array, int size) {
	int sum =0 ;
	for (int i = 0; i < size; i++) {
		sum += array[i];
	}
	return sum;
}

int main(int argc, char *argv[])
{
	int rem;			// elements remaining after division among processes
	int rank, size;     // for storing this process' rank, and the number of processes
	int *sendcounts;    // array describing how many elements to send to each process
	int *displs;        // array describing the displacements where each segment begins
	int sum = 0;        // Sum of counts. Used to calculate displacements
	int rec_buf[SIZE];  // buffer where the received data should be stored


	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	sendcounts = new int[size];
	displs = new int[size];
	
	int nrProcExtra = size - 1;

	int* rez = NULL;

	rem = SIZE % nrProcExtra;

	sendcounts[0] = 0;
	displs[0] = 0;

	for (int i = 1; i < size; i++) {
		sendcounts[i] = SIZE / nrProcExtra;
		if (rem > 0) {
			sendcounts[i]++;
			rem--;
		}
		displs[i] = sum;
		sum += sendcounts[i];
	}

	if (0 == rank) {
		int data[SIZE];		

		for (int i = 0; i < SIZE; i++) {
			data[i] = i;
		}

		MPI_Scatterv(&data, sendcounts, displs, MPI_INT, &rec_buf, SIZE, MPI_INT, 0, MPI_COMM_WORLD);

		rez = new int[size];
	
		int mylen = 0;

		MPI_Gather(&mylen, 1, MPI_INT,
			rez, 1, MPI_INT,
			0, MPI_COMM_WORLD);

		printf("\n");

		for (int i = 0; i < size; i++) {
			printf("Proc %d: sum = %d \n", i, rez[i]);
		}
	}
	else {
		MPI_Scatterv(NULL, NULL, NULL, MPI_INT, &rec_buf, SIZE, MPI_INT, 0, MPI_COMM_WORLD);

		printf("sendcounts[%d] = %d\tdispls[%d] = %d\n", rank, sendcounts[rank], rank, displs[rank]);

		printf("%d: ", rank);
		for (int i = 0; i < sendcounts[rank]; i++) {
			printf("%d\t", rec_buf[i]);
		}

		int mylen = addNumbers(rec_buf, sendcounts[rank]);

		MPI_Gather(&mylen, 1, MPI_INT, NULL, NULL, MPI_INT, 0, MPI_COMM_WORLD);
	}

	MPI_Finalize();

	delete sendcounts;
	delete displs;
	delete rez;

	return 0;
}

