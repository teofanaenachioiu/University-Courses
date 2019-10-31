// ConsoleApplication1.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include <mpi.h>

int main()
{
	int nrProcese, idProces;// in cazul asta, astea doua se creeaza in fiecare proces, idProces = rank la profu
	MPI_Status status;
	int rc = MPI_Init(NULL, NULL); //sau argc si argv , aici creez contextul mpi
	if (rc != MPI_SUCCESS) {
		std::cout << "ERRRRORARE! Hopa ceva nu e ok la init mpi" << std::endl;
		MPI_Abort(MPI_COMM_WORLD, rc);
	}
	MPI_Comm_size(MPI_COMM_WORLD, &nrProcese);
	MPI_Comm_rank(MPI_COMM_WORLD, &idProces);
	std::cout << idProces << " from " << nrProcese << std::endl;

	if (idProces == 0)
	{
		int buf = 587; // variabila asta va fi doar pentru procesul 0
		MPI_Send(&buf, 1, MPI_INT, 1, 0, MPI_COMM_WORLD);
		std::cout << "Am trims mesajul! PAAA!" << std::endl;
	}
	else if (idProces == 1)
	{
		int buff = 0;// aici pun ce primesc si e doar pentru procesul 1 e
		std::cout << buff << std::endl;
		MPI_Recv(&buff, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		std::cout << "Am primit ceva: " << buff << std::endl;
	}
	else {

	}

	MPI_Finalize(); // distrugem contextul mpi
}
