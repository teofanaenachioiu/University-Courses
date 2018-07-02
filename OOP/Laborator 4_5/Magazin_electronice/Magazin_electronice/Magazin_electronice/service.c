#include "repository.h"
#include "service.h"
#include "produs.h"
#include <assert.h>
#include <stdio.h>
#include <malloc.h>
#include <string.h>
#define UNREFERENCED_PARAMETER

Service* creareService()
{
	/*Se creaza service*/
	Service* serv = (Service*)malloc(sizeof(Service));
	serv->r = makeRepo();
	serv->undo = creareVector();
	return serv;
	
}

void distrugeService(Service * serv)
{
	/*Functia de distrugere service.*/
	for (int i = 0; i < getDim(serv->undo); i++) {
		//printf("%d", getDim(get(serv->undo, i)));
		distrugeVectorProduse(get(serv->undo, i));
	}
	free(serv->undo->list);
	free(serv->undo);
	destroyRepo(serv->r);
	free(serv);
}

int adaugare(Service * serv, Produs * prod)
{
	Vector* copie = copyList(serv->r->v);
	
	append(serv->undo,copie);
	/*Se adauga un produs in service.*/
	
	return store(serv->r, prod);
}

int actualizarePret(Service * serv, Produs * prod, double pret)
{
	/*Se actualizeaza pretul unui produs.*/
	Vector* copie = copyList(serv->r->v);

	append(serv->undo, copie);
	return updatePret(serv->r, prod, pret);
}

int actualizareCantitate(Service * serv, Produs * prod, int cantitate)
{
	Vector* copie = copyList(serv->r->v);

	append(serv->undo, copie);
	/*Se actualizeaza stocul produsului*/
	return updateCantitate(serv->r, prod, cantitate);
}

int stergere(Service * serv, Produs * prod)
{
	Vector* copie = copyList(serv->r->v);

	append(serv->undo, copie);
	/*Se sterge un produs din service.*/
	return del(serv->r, prod);
	
}

int cauta(Service * serv, Produs * prod)
{
	/*Se cauta un produs in service.*/
	return find(serv->r, getId(prod));
}

Vector* iaTot(Service * serv)
{
	/*Se iau toate produsele din service.*/
	return getAll(serv->r);
}

Vector* sortareCantitate(Service * serv, int reverse)
{
	/*
	Functie de sortare in functie de stoc.
	reverse - nr intreg: 0 (ordonare crescatoare), 1(ordonare descrescatoare)
	Se returneaza service-ul sortat.
	*/
	int i, j;
	for (i = 0; i < getDim(iaTot(serv)) - 1; i++)
	{
		for (j = i + 1; j < getDim(iaTot(serv)); j++)
		{
			if ((reverse == 0) && (getCantitate(get(iaTot(serv), j)) < getCantitate(get(iaTot(serv), i))))
			{
				swapElem(iaTot(serv), i, j);
			}
			else {
				if ((reverse == 1) && (getCantitate(get(iaTot(serv), j)) > getCantitate(get(iaTot(serv), i))))
				{
					swapElem(iaTot(serv), i, j);
				}
			}
		}
	}
	return iaTot(serv);
}

Vector* sortarePret(Service  * serv, int reverse)
{
	/*
	Functie de sortare in functie de pretul produselor din service.
	reverse - nr intreg: 0 (ordonare crescatoare), 1(ordonare descrescatoare)
	Se returneaza service-ul sortat.
	*/
	int i, j;
	for (i = 0; i < getDim(iaTot(serv)) - 1; i++)
	{
		for (j = i + 1; j < getDim(iaTot(serv)); j++)
		{
			if ((reverse == 0) && (getPret(get(iaTot(serv), j)) < getPret(get(iaTot(serv), i))))
			{
				swapElem(iaTot(serv), i, j);
			}
			else {
				if ((reverse == 1) && (getPret(get(iaTot(serv), j)) > getPret(get(iaTot(serv), i))))
				{
					swapElem(iaTot(serv), i, j);
				}
			}
		}
	}
	return iaTot(serv);
}

Vector* listaSortata(Service * serv, char mod)
{
	/*Se apeleaza convenabil functiile de sortare
	mod - numar intreg
	mod: 1(sortare crescatoare dupa pret),
	2(sortare descrescatoare dupa pret),
	3(sortare crescatoare dupa cantitate),
	4(sortare descrescatoare dupa cantitate)
	*/
	if(mod=='1')
		return sortarePret(serv, 0);
	else {
		if (mod == '2')
		{
			return sortarePret(serv, 1);}
		else {
			if (mod == '3')
				return sortareCantitate(serv, 0);
		}
	}
	return sortareCantitate(serv, 1); 
	
}

void getProducatorList(Vector * list, Service * serv, char *producator)
{
	/*
	Se cauta acele produse care au producatorul dat.
	list - vector de Produse
	producator - string
	Se returneaza lista cu produsele care au producatorul specificat.
	*/
	int i;
	for (i = 0; i < getDim(iaTot(serv)); i++)
	{
		if (strcmp(getProducator(get(iaTot(serv), i)), producator) == 0)
		{
			append(list, get(iaTot(serv), i));
		}
	}
}
void getModelList(Vector * list, Service * serv, char *model)
{
	/*
	Se cauta acele produse care au modelul dat.
	list - vector de Produse
	producator - string
	Se returneaza lista cu produsele care au producatorul specificat.
	*/
	int i;
	for (i = 0; i < getDim(iaTot(serv)); i++)
	{
		if (strcmp(getModel(get(iaTot(serv), i)),model) == 0)
		{
			append(list, get(iaTot(serv), i));
		}
	}
}

void getPretList(Vector* list, Service * serv, double pret)
{
	/*
	Se cauta acele produse care au pretul dat.
	list - vector de Produse
	pret - numar real
	Se returneaza lista cu produsele care au producatorul specificat.
	*/
	for (int i = 0; i < getDim(iaTot(serv)); i++)
	{
		if (getPret(get(iaTot(serv), i)) == pret)
		{
			append(list, get(iaTot(serv), i));
		}
	}
}
//face undo la nivelul service-ului si returneaza 1 daca nu se mai poate face
int makeUndo(Service* serv) {
	if (getDim(serv->undo) == 0) {
		return 1;
	}
	Vector* l;
	l=removeLast(serv->undo);
	distrugeVectorProduse(serv->r->v);
	serv->r->v = l;
	return 0;
}

void getCantitateList(Vector* list, Service * serv, int cantitate)
{
	/*
	Se cauta acele produse care au cantitatea data.
	list - vector de Produse
	cantiatate - numar intreg
	Se returneaza lista cu produsele care au producatorul specificat.
	*/
	for (int i = 0; i < getDim(iaTot(serv)); i++)
	{
		if (getCantitate(get(iaTot(serv), i)) == cantitate)
		{
			append(list, get(iaTot(serv), i));
		}
	}

}

void test_adaugaProdus()
{
	/*Test pentru functia de adaugare produs.*/
	Service* serv = creareService();
	Produs* prod = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	assert(adaugare(serv, prod) == 1);
	distrugeService(serv);
}

void test_stergeProdus()
{
	/*Test pentru functia de stergere produs.*/
	Service* serv = creareService();
	Produs* prod = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	adaugare(serv, prod);
	assert(stergere(serv, prod) == 1);
	assert(stergere(serv, prod) == 0);
	distrugeService(serv);
}

void test_cautaProdus()
{
	/*Test pentru functia de cautare.*/
	Service *serv = creareService();
	Produs *prod1, *prod2;
	prod1 = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	prod2 = creazaProdus(102, "Mouse", "Alfa", "Hama", 35, 20);
	adaugare(serv, prod1);
	assert(cauta(serv, prod1) == 0);
	assert(cauta(serv, prod2) == -1);
	makeUndo(serv);
	assert(getDim(serv->r->v) == 0);
	assert(makeUndo(serv) == 1);
	distrugeProdus(prod2);
	distrugeService(serv);
}

void test_actualizarePret()
{
	/*Test pentru functia de actualizare produs.*/
	Service* serv = creareService();
	Produs *prod1, *prod2;
	prod1 = creazaProdus(101, "Casti", "Bass", "Sony", 20, 50);
	prod2 = creazaProdus(102, "Mouse", "Alfa", "Hama", 35, 20);
	adaugare(serv, prod1);
	assert(actualizarePret(serv, prod1, 15) == 1);
	assert(actualizarePret(serv, prod2, 15) == 0);
	distrugeProdus(prod2);
	distrugeService(serv);
}

void test_actualizareCantitate()
{
	/*Test pentru functia de actualizare cantitate*/
	Service* serv = creareService();
	Produs *prod1, *prod2;
	prod1 = creazaProdus(101, "Casti", "Bass", "Sony", 20, 50);
	prod2 = creazaProdus(102, "Mouse", "Alfa", "Hama", 35, 20);
	adaugare(serv, prod1);
	assert(actualizareCantitate(serv, prod1, 10) == 1);
	assert(actualizareCantitate(serv, prod2, 10) == 0);
	distrugeProdus(prod2);
	distrugeService(serv);
}

void test_iaTot()
{
	/*Test pentru functia iaTot*/
	Service *serv = creareService();
	Produs *prod1, *prod2, *prod3;
	prod1 = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	prod2 = creazaProdus(102, "Mouse", "Alfa", "Hama", 35, 20);
	prod3 = creazaProdus(103, "Tastatura", "Luna", "HP", 80, 10);
	adaugare(serv, prod1);
	adaugare(serv, prod2);
	adaugare(serv, prod3);

	assert(getDim(iaTot(serv)) == 3);

	assert(comparaProduse(get(iaTot(serv), 0), prod1) == 0);
	assert(comparaProduse(get(iaTot(serv), 1), prod2) == 0);
	assert(comparaProduse(get(iaTot(serv), 2), prod3) == 0);
	distrugeService(serv);
}

void test_getProducatorList()
{
	/*Test pentru functia getProducator*/
	Service *serv = creareService();

	Produs *prod1, *prod2, *prod3;
	prod1 = creazaProdus(101, "Casti", "Bass", "Sony", 20, 50);
	adaugare(serv, prod1);
	prod2 = creazaProdus(101, "Mouse", "001", "Hp", 55, 30);
	adaugare(serv, prod2);

	Vector *list = creareVector();
	getProducatorList(list, serv, "Sony");
	assert(getDim(list) == 1);
	prod3 = creazaProdus(101, "Mouse", "Yoda", "Sony", 40, 10);
	adaugare(serv, prod3);
	free(list->list);
	free(list);

	Vector *list1 = creareVector();
	getProducatorList(list1, serv, "Sony");
	assert(getDim(list1) == 2);
	free(list1->list);
	free(list1);

	distrugeService(serv);
}

void test_getPretList()
{
	/*Test pentru funcia getPretList*/
	Service *serv = creareService();

	Produs *prod1, *prod2, *prod3;
	prod1 = creazaProdus(101, "Casti", "Bass", "Sony", 20, 50);
	adaugare(serv, prod1);
	prod2 = creazaProdus(101, "Mouse", "001", "Hp", 40, 30);
	adaugare(serv, prod2);

	assert(getDim(iaTot(serv)) == 2);
	Vector *list = creareVector();
	getPretList(list, serv, 40);
	assert(getDim(list) == 1);
	prod3 = creazaProdus(101, "Mouse", "Yoda", "Sony", 40, 10);
	adaugare(serv, prod3);
	free(list->list);
	free(list);

	Vector *list1 = creareVector();
	getPretList(list1, serv, 40);
	assert(getDim(list1) == 2);
	free(list1->list);
	free(list1);

	distrugeService(serv);
}

void test_getCantitateList()
{
	/*Test pentru functia getCantitateList*/
	Service *serv = creareService();

	Produs *prod1, *prod2, *prod3;
	prod1 = creazaProdus(101, "Casti", "Bass", "Sony", 20, 50);
	adaugare(serv, prod1);
	prod2 = creazaProdus(101, "Mouse", "001", "Hp", 40, 30);
	adaugare(serv, prod2);

	Vector *list = creareVector();
	getCantitateList(list, serv, 30);
	assert(getDim(list) == 1);
	prod3 = creazaProdus(101, "Mouse", "Yoda", "Sony", 40, 30);
	adaugare(serv, prod3);
	getModelList(list, serv, "Yoda");
	free(list->list);
	free(list);

	Vector *list1 = creareVector();
	getCantitateList(list1, serv, 30);
	assert(getDim(list1) == 2);
	free(list1->list);
	free(list1);

	distrugeService(serv);
}


void test_sortarePret()
{
	/*Test pentru functia sortarePret*/
	Service* serv1 = creareService();

	Produs *prod1, *prod2, *prod3;
	prod1 = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 40);
	prod2 = creazaProdus(102, "Mouse", "Alfa", "Hama", 99, 30);
	prod3 = creazaProdus(103, "Tastatura", "Luna", "HP", 80, 10);
	adaugare(serv1, prod1);
	adaugare(serv1, prod2);
	adaugare(serv1, prod3);

	assert(getId(get(listaSortata(serv1, '1'), 0)) == 101);
	assert(getId(get(listaSortata(serv1, '1'), 1)) == 103);
	assert(getId(get(listaSortata(serv1, '1'), 2)) == 102);

	distrugeService(serv1);

	Service* serv2 = creareService();
	Produs *p1, *p2, *p3;
	p1 = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 40);
	p2 = creazaProdus(102, "Mouse", "Alfa", "Hama", 35, 30);
	p3 = creazaProdus(103, "Tastatura", "Luna", "HP", 80, 10);
	adaugare(serv2, p1);
	adaugare(serv2, p2);
	adaugare(serv2, p3);

	assert(getId(get(listaSortata(serv2, '2'), 0)) == 103);
	assert(getId(get(listaSortata(serv2, '2'), 1)) == 102);
	assert(getId(get(listaSortata(serv2, '2'), 2)) == 101);

	distrugeService(serv2);
}

void test_sortareCantitate()
{
	/*Test pentru funcita de sortareCantitate*/
	Service* serv1 = creareService();

	Produs *prod1, *prod2, *prod3;
	prod1 = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 40);
	prod2 = creazaProdus(102, "Mouse", "Alfa", "Hama", 35, 30);
	prod3 = creazaProdus(103, "Tastatura", "Luna", "HP", 80, 10);
	adaugare(serv1, prod1);
	adaugare(serv1, prod2);
	adaugare(serv1, prod3);

	assert(getId(get(listaSortata(serv1, '3'), 0)) == 103);
	assert(getId(get(listaSortata(serv1, '3'), 1)) == 102);
	assert(getId(get(listaSortata(serv1, '3'), 2)) == 101);

	distrugeService(serv1);

	Service* serv2 = creareService();
	Produs *p1, *p2, *p3;
	p1 = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 40);
	p2 = creazaProdus(102, "Mouse", "Alfa", "Hama", 35, 30);
	p3 = creazaProdus(103, "Tastatura", "Luna", "HP", 80, 35);
	adaugare(serv2, p1);
	adaugare(serv2, p2);
	adaugare(serv2, p3);

	assert(getId(get(listaSortata(serv2, '4'), 0)) == 101);
	assert(getId(get(listaSortata(serv2, '4'), 1)) == 103);
	assert(getId(get(listaSortata(serv2, '4'), 2)) == 102);

	distrugeService(serv2);
}