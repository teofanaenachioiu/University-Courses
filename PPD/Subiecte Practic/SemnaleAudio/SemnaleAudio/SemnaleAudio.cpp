// SemnaleAudio.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include "mpi.h"
#include "Utils.h"
#include "SemnaleAudio.h"
const int MAX = 20;
using namespace std;

void calculCountDispls(int*& sendcounts, int*& displs, int world_size, int nr_frecvente_bruiaj)
{
	sendcounts = new int[world_size];
	displs = new int[world_size];

	int rest = nr_frecvente_bruiaj % world_size;
	int displ = 0;

	for (int i = 0; i < world_size; i++) {
		sendcounts[i] = nr_frecvente_bruiaj / world_size;
		if (rest > 0) {
			sendcounts[i]++;
			rest--;
		}
		displs[i] = displ;
		displ += sendcounts[i];
	}
}

void sterge(int* semnale, int poz, int end) {
	for (int i = poz; i < end-1; i++) {
		semnale[i] = semnale[i + 1];
	}
}

void verifica(int rank, int* frecvente, int endFrecvente, int* semnale, int& sizeSemnale) {
	cout << sizeSemnale;
	for (int i = 0; i < endFrecvente; i++) {
		int j = 0;
		while (j < sizeSemnale) {
			if (semnale[j] % frecvente[i] == 0) {
				cout << rank<<" -> "<<semnale[j] << " divizibil la " << frecvente[i] << endl;
				sterge(semnale, j, sizeSemnale);
				sizeSemnale--;
			}
			else {
				j++;
			}
		}
	}
}

int main(int argc, char* argv[])
{
	int world_size;
	int rank;

	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &world_size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	int* semnale = new int[MAX];
	int* frecvente = new int[MAX];
	int* sizeSemnale = new int[world_size];
	int** rez = nullptr; 
	rez = new int*[MAX];

	int nr_semnale = 0;
	int nr_frecvente = 0;

	int* recv_frecvente = new int[MAX];
	int* recv_semnale = new int[MAX];

	int* sendcountsFrecvente = nullptr;
	int* displsFrecvente = nullptr;

	if (rank == 0) {
		nr_semnale = Utils::readNumbersFromFile(semnale, "semnale_in.txt");
		nr_frecvente = Utils::readNumbersFromFile(frecvente, "bruiaj.txt");
		for (int i = 0; i < world_size; i++) {
			sizeSemnale[i] = nr_semnale;
			rez[i] = semnale;
		}
	}

	MPI_Bcast(&nr_frecvente, 1, MPI_INT, 0, MPI_COMM_WORLD);

	calculCountDispls(sendcountsFrecvente, displsFrecvente, world_size, nr_frecvente);

	MPI_Scatterv(frecvente, sendcountsFrecvente, displsFrecvente, MPI_INT, recv_frecvente, sendcountsFrecvente[rank], MPI_INT, 0, MPI_COMM_WORLD);

	MPI_Bcast(&nr_semnale, 1, MPI_INT, 0, MPI_COMM_WORLD);
	MPI_Bcast(semnale, nr_semnale, MPI_INT, 0, MPI_COMM_WORLD);

	//MPI_Scatter(rez, nr_semnale, MPI_INT, recv_semnale, MAX, MPI_INT, 0, MPI_COMM_WORLD);

	int nr_sem = nr_semnale;

	/*for (int i = 0; i < nr_sem; i++) {
		cout << recv_semnale[i] << " ";
	}
	cout << endl;*/
	
	verifica(rank, recv_frecvente, sendcountsFrecvente[rank], semnale, nr_sem);

	MPI_Gather(&nr_sem, 1, MPI_INT, sizeSemnale, 1, MPI_INT, 0, MPI_COMM_WORLD);


	//MPI_Gather(semnale, nr_sem, MPI_INT, rez, nr_semnale, MPI_INT, 0, MPI_COMM_WORLD);
	MPI_Gatherv(semnale, nr_sem, MPI_INT, rez, sizeSemnale, sizeSemnale, MPI_INT, 0, MPI_COMM_WORLD);

	if (rank == 0) {
		
		cout << "Ce a ramas din semnale" << endl;
		for (int i = 0; i < world_size; i++) {
			cout << i << " -> " << sizeSemnale[i] << endl;
			//cout << rez[i] << endl;
			for (int j = 0; j < sizeSemnale[i]; j++) {
				//cout << "DA ";
				cout << rez[i][j] << " ";
			}
			cout << endl;
		}
	}
	
	MPI_Finalize();
}