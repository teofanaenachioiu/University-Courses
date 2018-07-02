#pragma once
#include "service.h"
typedef struct
{
	Service s;
}Consola;

Consola creazaConsola();

Service getService(Consola cons);

void start();