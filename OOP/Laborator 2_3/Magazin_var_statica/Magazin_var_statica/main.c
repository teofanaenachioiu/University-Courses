#include "consola.h"

void teste()
{
	//teste pentru Produs
	test_creazaProdus();
	test_comparaProduse();
	test_swap();
	test_atribuire();

	//teste pentru Vector
	test_creazaVector();
	test_getProdus();
	test_search();
	test_append();
	test_delete();
	test_swapElem();
	test_set();

	//teste pentru Validare
	test_validareNrIntreg();
	test_validareNrReal();
	test_validareCuvant();

	//teste pentru Repo
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
	test_listaSortata();
	test_sortarePret();
	test_sortareCantitate();
}

int main()
{
	teste();
	start();
	return 0;
}