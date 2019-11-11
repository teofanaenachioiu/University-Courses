
#include "pch.h"
#include "mpi.h"
#include <time.h>
#include <iostream>
#include <math.h>

const int SIZE1 = 10;
const int SIZE2 = 15;


int addNumbers(int *array1, int *array2, int* arrayRez, int size) {
	int carry = 0, sum;
	for (int i = 0; i < size; i++) {
		sum = array1[i] + array2[i] + carry;
		arrayRez[i] = sum % 10;
		carry = sum / 10;
	}
	return carry;
}


int addNumbers(int *array, int size) {
	int sum = 0;
	for (int i = 0; i < size; i++) {
		sum += array[i];
	}
	return sum;
}

void getData(int *data1, int *data2) {
	for (int i = 0; i < SIZE1; i++) {
		data1[i] = i % 10;
	}

	for (int i = 0; i < SIZE2; i++) {
		data2[i] = i % 10;
	}
}

void calculatePartitions(int *sendcounts, int *displs, int size, int noProc) {
	int sum = 0;        // Sum of counts. Used to calculate displacements

	int rem = SIZE1 % noProc;
	sendcounts[0] = 0;
	displs[0] = 0;

	for (int i = 1; i < size; i++) {
		sendcounts[i] = SIZE1 / noProc;
		if (rem > 0) {
			sendcounts[i]++;
			rem--;
		}
		displs[i] = sum;
		sum += sendcounts[i];
	}
}

int main(int argc, char *argv[])
{
	int rank, size;     // for storing this process' rank, and the number of processes
	int *sendcounts;    // array describing how many elements to send to each process
	int *displs;        // array describing the displacements where each segment begins
	int *rez;
	int rec_buf1[SIZE1];  // buffer where the received data should be stored
	int rec_buf2[SIZE1];
	int nrProcExtra;


	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	sendcounts = new int[size];
	displs = new int[size];
	rez = NULL;
	nrProcExtra = size - 1;

	calculatePartitions(sendcounts, displs, size, nrProcExtra);

	if (0 == rank) {
		int data1[SIZE1];
		int data2[SIZE2];
		int dataRez[SIZE1];

		getData(data1, data2);

		MPI_Scatterv(&data1, sendcounts, displs, MPI_INT, &rec_buf1, SIZE1, MPI_INT, 0, MPI_COMM_WORLD);
		MPI_Scatterv(&data2, sendcounts, displs, MPI_INT, &rec_buf2, SIZE1, MPI_INT, 0, MPI_COMM_WORLD);

		rez = new int[size];

		MPI_Gather(NULL, 0, MPI_INT, rez, 1, MPI_INT, 0, MPI_COMM_WORLD);

		MPI_Gatherv(NULL, 0, MPI_INT, dataRez, sendcounts, displs, MPI_INT, 0, MPI_COMM_WORLD);


		/*printf("\n");

		for (int i = 0; i < SIZE1; i++) {
			printf("%d", data1[i]);
		}
		printf("\n");

		for (int i = 0; i < SIZE2; i++) {
			printf("%d", data2[i]);
		}
		printf("\n");

		for (int i = 0; i < SIZE1; i++) {
			printf("%d", dataRez[i]);
		}
		printf("\n");*/
	}
	else {
		MPI_Scatterv(NULL, NULL, NULL, MPI_INT, &rec_buf1, SIZE1, MPI_INT, 0, MPI_COMM_WORLD);
		MPI_Scatterv(NULL, NULL, NULL, MPI_INT, &rec_buf2, SIZE1, MPI_INT, 0, MPI_COMM_WORLD);

		//printf("sendcounts[%d] = %d\tdispls[%d] = %d\n", rank, sendcounts[rank], rank, displs[rank]);

		//printf("%d: ", rank);
		//for (int i = 0; i < sendcounts[rank]; i++) {
		//	printf("%d\t", rec_buf1[i]);
		//}
		//for (int i = 0; i < sendcounts[rank]; i++) {
		//	printf("%d\t", rec_buf2[i]);
		//}

		int* rezArray = new int[sendcounts[rank]];

		int carryPartition = addNumbers(rec_buf1, rec_buf2, rezArray, sendcounts[rank]);

		MPI_Gather(&carryPartition, 1, MPI_INT, NULL, NULL, MPI_INT, 0, MPI_COMM_WORLD);

		MPI_Gatherv(rezArray, sendcounts[rank], MPI_INT, NULL, sendcounts, displs, MPI_INT, 0, MPI_COMM_WORLD);

		delete rezArray;
	}

	MPI_Finalize();

	delete sendcounts;
	delete displs;
	delete rez;

	return 0;
}

