#include "consola.h"
#include "validare.h"
#define _CRTDBG_MAP_ALLOC  
#include <stdlib.h>  
#include <crtdbg.h>  

void teste()
{
	//teste pentru Produs
	test_creazaProdus();
	test_comparaProduse();
	test_swap();
	test_atribuire();
	test_set();
	test_copieProdus();

	////teste pentru Vector
	test_creazaVector();
	test_getProdus();
	test_search();
	test_append();
	test_delete();
	test_swapElem();

	////teste pentru Validare
	test_validareNrIntreg();
	test_validareNrReal();
	test_validareCuvant();

	////teste pentru Repo
	test_store();
	test_del();
	test_find();
	test_update();
	test_getAll();

	//teste pentru Service
	test_adaugaProdus();
	test_stergeProdus();
	test_cautaProdus();
	test_actualizarePret();
	test_actualizareCantitate();
	test_iaTot();
	test_getProducatorList();
	test_getPretList();
	test_getCantitateList();
	test_sortarePret();
	test_sortareCantitate();

	//test pentru Consola
	test_getService();
}

int main()
{
	//Punctul de start in aplicatie
	teste();
	start();
	_CrtDumpMemoryLeaks();
	return 0;
}