#pragma once
#include "service.h"
typedef struct
{
	Service* s;
}Consola;

Consola* creazaConsola();

Service* getService(Consola* cons);

void adauga(Consola* cons);

void sterge(Consola * cons);

void actualizeaza(Consola * cons);

void filtreaza(Consola * cons);

void afiseaza(Consola * cons);

void distrugeConsola(Consola *cons);

void start();

void test_getService();