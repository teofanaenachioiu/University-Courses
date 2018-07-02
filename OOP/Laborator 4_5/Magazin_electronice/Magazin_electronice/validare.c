#include <string.h>
#include <assert.h>
#include <stdlib.h>
#include "validare.h"

int validareNrIntreg(char* str)
{
	/*
	Functia verifica daca sirul dat ca parametru contine doar cifre
	str - string
	Se returneaza valoarea 1 daca sirul contine doar cifre sau 0, in caz contrar
	*/
	for (unsigned i = 0; i<strlen(str)-1; i++)
		if (str[i]<'0' || str[i]>'9')
			return 0;
	return 1;
}

int validareNrReal(char* str)
{
	/*
	Functia verifica daca sirul dat ca parametru contine doar cifre sau caracterul . (punct)
	str - string
	Se returneaza valoarea 1 daca sirul contine doar cifre sau 0, in caz contrar
	*/
	for (unsigned i = 0; i < strlen(str)-1; i++)
		if (strchr("0123456789.", str[i]) == NULL)
			return 0;
	return 1;
}

int validareCuvant(char* str)
{
	/*
	Functia verifica daca sirul dat ca parametru contine doar litere sau caracterul spatiu
	str - string
	Se returneaza valoarea 1 daca sirul contine doar litere sau 0, in caz contrar
	*/
	for (unsigned i = 0; i<strlen(str)-1; i++)
		if (!((str[i] >= 'a' && str[i] <= 'z') || (str[i] == ' ') || (str[i] >= 'A' && str[i] <= 'Z')))
			return 0;
	return 1;
}

void test_validareCuvant()
{
	/*Test pentru functia validareCuvant*/
	assert(validareCuvant("teo14n4") == 0);
	assert(validareCuvant("14n4") == 0);
	assert(validareCuvant("teofana") == 1);
	assert(validareCuvant("teofana enachioiu") == 1);
}

void test_validareNrIntreg()
{
	/*Test pentru functia validareNrIntreg*/
	assert(validareNrIntreg("teo14n4") == 0);
	assert(validareNrIntreg("teofana") == 0);
	assert(validareNrIntreg("123") == 1);
}

void test_validareNrReal()
{
	/*Test pentru functia validareNrReal*/
	assert(validareNrReal("teo14n4") == 0);
	assert(validareNrReal("teofana") == 0);
	assert(validareNrReal("123") == 1);
	assert(validareNrReal("12.3") == 1);
	assert(atof("12.3") == 12.3);
}