#include "repository.h"
#include "service.h"
#include <assert.h>
#include <stdio.h>
Service creareService()
{
	Service serv;
	serv.r = makeRepo();
	return serv;
}

int adaugare(Service * serv, Produs * prod)
{
	return store(&(*serv).r,prod);
}

int actualizarePret(Service * serv, Produs * prod, float pret)
{
	return updatePret(&(*serv).r,prod,pret);
}

int actualizareCantitate(Service * serv, Produs * prod, int cantitate)
{
	return updateCantitate(&(*serv).r, prod,cantitate);
}

int stergere(Service * serv, Produs * prod)
{
	return del(&(*serv).r, prod);
}

int cauta(Service * serv, Produs * prod)
{
	return find(&(*serv).r, getId(prod));
}

Vector iaTot(Service * serv)
{
	return (*serv).r.v;
}

Vector sortareCantitate(Vector* lista,Service * serv, int reverse)
{
	/*
	Functie de sortare in functie de cantitate pentru elementele din lista.
	reverse - nr intreg: 0 (ordonare crescatoare), 1(ordonare descrescatoare)
	Se returneaza lista sortata.
	*/
	int i, j;
	Produs prod1,prod2;
	for (i = 0; i < getDim(lista) - 1; i++)
	{
		prod1 = getProdus(lista, i);
		for (j = i + 1; j < getDim(lista); j++)
		{
			prod2 = getProdus(lista, j);
			if ((reverse == 0) && (getCantitate(&prod2) < getCantitate(&prod1)))
			{
				swapElem(lista, i, j);
			}
			else {
				if ((reverse == 1) && (getCantitate(&prod2) > getCantitate(&prod1)))
				{
					swapElem(lista, i, j);
				}
			}
		}
	}
	Produs p1 = getProdus(lista, 0);
	Produs p2 = getProdus(lista, 1);
	Produs p3 = getProdus(lista, 2);
	return *lista;
}

Vector sortarePret(Vector * lista, Service  * serv, int reverse)
{
	int i, j;
	Produs prod1, prod2;
	for (i = 0; i < getDim(lista) - 1; i++)
	{
		prod1 = getProdus(lista, i);
		for (j = i + 1; j < getDim(lista); j++)
		{
			prod2 = getProdus(lista, j);
			if ((reverse == 0) && (getPret(&prod2) < getPret(&prod1)))
			{
				swapElem(lista, i, j);
			}
			else {
				if ((reverse == 1) && (getPret(&prod2) > getPret(&prod1)))
				{
					swapElem(lista, j, i);
				}
			}
		}
	}
	Produs p1 = getProdus(lista, 0);
	Produs p2 = getProdus(lista, 1);
	Produs p3 = getProdus(lista, 2);
	return *lista;
}

Vector listaSortata(Service * serv, int mod)
{
	Vector lista = iaTot(serv);
	switch (mod)
	{
	case 1:
	{sortarePret(&lista, serv, 0); break; }
	case 2:
	{sortarePret(&lista, serv, 1); break; }
	case 3:
	{sortareCantitate(&lista,serv, 0); break; }
	case 4:
	{sortareCantitate(&lista,serv, 1); break; }
	default:
		break;
	}
	return lista;
}

Vector getProducatorList(Service * serv, char producator[20])
{
	/*
	Se cauta acele produse care au producatorul dat.
	memory - ListaProduse
	producator - string
	Se returneaza lista cu produsele care au producatorul specificat.
	*/
	int i;
	Vector memory, listaNoua;
	memory = iaTot(serv);
	listaNoua = creareVector();
	Produs p;
	for (i = 0; i < getDim(&memory); i++)
	{
		p = getProdus(&memory, i);
		if (stricmp(getProducator(&p), producator) == 0)
		{
			append(&listaNoua, &p);
		}
	}
	
	return listaNoua;
}

Vector getPretList(Service * serv, float pret)
{
	Vector memory, listaNoua;
	memory = iaTot(serv);
	listaNoua = creareVector();
	Produs p;
	for (int i = 0; i < getDim(&memory); i++)
	{
		p = getProdus(&memory, i);
		if ( getPret(&p)== pret)
		{
			append(&listaNoua, &p);
		}
	}

	return listaNoua;
}

Vector getCantitateList(Service * serv, int cantitate)
{
	Vector memory, listaNoua;
	memory = iaTot(serv);
	listaNoua = creareVector();
	Produs p;
	for (int i = 0; i < getDim(&memory); i++)
	{
		p = getProdus(&memory, i);
		if (getCantitate(&p) == cantitate)
		{
			append(&listaNoua, &p);
		}
	}

	return listaNoua;
}

void test_adaugaProdus()
{
	Service serv = creareService();
	Produs prod = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "50");
	assert(adaugare(&serv, &prod) == 1);
	for (int i = 1; i < getCapacitate(&serv.r.v); i++)
		assert(adaugare(&serv, &prod) == 1);
	assert(adaugare(&serv, &prod) == 0);
}

void test_stergeProdus()
{
	Service serv = creareService();
	Produs prod = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "50");
	adaugare(&serv, &prod);
	assert(stergere(&serv, &prod) == 1);
	assert(stergere(&serv, &prod) == 0);
}

void test_cautaProdus()
{
	Service serv = creareService();
	Produs prod1,prod2;
	prod1 = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "50");
	prod2 = creazaProdus("102", "Mouse", "Alfa", "Hama", "35", "20");
	adaugare(&serv, &prod1);
	assert(cauta(&serv, &prod1) == 0);
	assert(cauta(&serv, &prod2) == -1);
}

void test_actualizarePret()
{
	Service serv = creareService();
	Produs prod1, prod2;
	prod1 = creazaProdus("101", "Casti", "Bass", "Sony", "20", "50");
	prod2 = creazaProdus("102", "Mouse", "Alfa", "Hama", "35", "20");
	adaugare(&serv, &prod1);
	assert(actualizarePret(&serv, &prod1, 15) == 1);
	assert(actualizarePret(&serv, &prod2, 15) == 0);
}

void test_actualizareCantitate()
{
	Service serv = creareService();
	Produs prod1, prod2;
	prod1 = creazaProdus("101", "Casti", "Bass", "Sony", "20", "50");
	prod2 = creazaProdus("102", "Mouse", "Alfa", "Hama", "35", "20");
	adaugare(&serv, &prod1);
	assert(actualizareCantitate(&serv, &prod1, 10) == 1);
	assert(actualizareCantitate(&serv, &prod2, 10) == 0);
}

void test_iaTot()
{
	Service serv = creareService();
	Vector lista = creareVector();
	Produs prod1, prod2, prod3,p1,p2,p3;
	prod1 = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "50");
	prod2 = creazaProdus("102", "Mouse", "Alfa", "Hama", "35", "20");
	prod3 = creazaProdus("103", "Tastatura", "Luna", "HP", "80", "10");
	adaugare(&serv, &prod1);
	adaugare(&serv, &prod2);
	adaugare(&serv, &prod3);
	lista = getAll(&serv);
	assert(getDim(&lista) == 3);
	
	p1 = getProdus(&lista, 0);
	p2 = getProdus(&lista, 1);
	p3 = getProdus(&lista, 2);

	assert(comparaProduse(&p1,&prod1) == 0);
	assert(comparaProduse(&p2, &prod2) == 0);
	assert(comparaProduse(&p3, &prod3) == 0);
}

void test_getProducatorList()
{
	/*Test pentru functia getProducator*/
	Vector lista = creareVector();
	Service serv = creareService();
	Produs prod1, prod2, prod3;
	prod1 = creazaProdus("101", "Casti", "Bass", "Sony", "20", "50");
	adaugare(&serv, &prod1);
	prod2 = creazaProdus("101", "Mouse", "001", "Hp", "55", "30");
	adaugare(&serv, &prod2);
	lista = getProducatorList(&serv, "Sony");
	assert(getDim(&lista) == 1);
	prod3 = creazaProdus("101", "Mouse", "Yoda", "Sony", "40", "10");
	adaugare(&serv, &prod3);
	lista = getProducatorList(&serv, "Sony");
	assert(getDim(&lista) == 2);
}

void test_getPretList()
{
	Vector lista = creareVector();
	Service serv = creareService();
	Produs prod1, prod2, prod3;
	prod1 = creazaProdus("101", "Casti", "Bass", "Sony", "20", "50");
	adaugare(&serv, &prod1);
	prod2 = creazaProdus("101", "Mouse", "001", "Hp", "40", "30");
	adaugare(&serv, &prod2);
	lista = getPretList(&serv, 40);
	assert(getDim(&lista) == 1);
	prod3 = creazaProdus("101", "Mouse", "Yoda", "Sony", "40", "10");
	adaugare(&serv, &prod3);
	lista = getPretList(&serv, 40);
	assert(getDim(&lista) == 2);
}

void test_getCantitateList()
{
	Vector lista = creareVector();
	Service serv = creareService();
	Produs prod1, prod2, prod3;
	prod1 = creazaProdus("101", "Casti", "Bass", "Sony", "20", "50");
	adaugare(&serv, &prod1);
	prod2 = creazaProdus("101", "Mouse", "001", "Hp", "40", "30");
	adaugare(&serv, &prod2);
	lista = getCantitateList(&serv, 50);
	assert(getDim(&lista) == 1);
	prod3 = creazaProdus("101", "Mouse", "Yoda", "Sony", "45", "30");
	adaugare(&serv, &prod3);
	lista = getCantitateList(&serv, 50);
	assert(getDim(&lista) == 1);
	lista = getCantitateList(&serv, 30);
	assert(getDim(&lista) == 2);
}

void test_listaSortata()
{
	Service serv = creareService();
	Vector lista, lista1,lista2,lista3,lista4;
	Produs prod1, prod2, prod3;
	prod1 = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "20");
	prod2 = creazaProdus("102", "Mouse", "Alfa", "Hama", "45", "30");
	prod3 = creazaProdus("103", "Tastatura", "Luna", "HP", "40", "10");
	adaugare(&serv, &prod1);
	adaugare(&serv, &prod2);
	adaugare(&serv, &prod3);

	lista1 = listaSortata(&serv, 3);
	lista = iaTot(&serv);
	sortareCantitate(&lista,&serv, 0);
	for (int k = 0; k < getDim(&lista); k++)
	{
		prod1 = getProdus(&lista, k);
		prod2 = getProdus(&lista1, k);
		assert(getCantitate(&prod1)==getCantitate(&prod2));
		assert(comparaProduse(&prod1,&prod2)==0);
	}

	lista2 = listaSortata(&serv, 4);
	lista = iaTot(&serv);
	sortareCantitate(&lista, &serv, 1);
	for (int k = 0; k < getDim(&lista); k++)
	{
		prod1 = getProdus(&lista, k);
		prod2 = getProdus(&lista2, k);
		assert(getCantitate(&prod1) == getCantitate(&prod2));
		assert(comparaProduse(&prod1, &prod2) == 0);
	}

	lista3 = listaSortata(&serv, 1);
	lista = iaTot(&serv);
	sortarePret(&lista, &serv, 0);
	for (int k = 0; k < getDim(&lista); k++)
	{
		prod1 = getProdus(&lista, k);
		prod2 = getProdus(&lista3, k);
		assert(getPret(&prod1) == getPret(&prod2));
		assert(comparaProduse(&prod1, &prod2) == 0);
	}

	lista4 = listaSortata(&serv, 2);
	lista = iaTot(&serv);
	sortarePret(&lista, &serv, 1);
	for (int k = 0; k < getDim(&lista); k++)
	{
		prod1 = getProdus(&lista, k);
		prod2 = getProdus(&lista4, k);
		assert(getPret(&prod1) == getPret(&prod2));
		assert(comparaProduse(&prod1, &prod2) == 0);
	}
}

void test_sortarePret()
{
	Service serv = creareService();
	Vector lista1, lista2;
	lista1 = creareVector();
	lista2 = creareVector();
	Produs prod1, prod2, prod3,p1,p2,p3,p4,p5,p6;
	prod1 = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "20");
	prod2 = creazaProdus("102", "Mouse", "Alfa", "Hama", "45", "30");
	prod3 = creazaProdus("103", "Tastatura", "Luna", "HP", "40", "10");
	adaugare(&serv, &prod1);
	adaugare(&serv, &prod2);
	adaugare(&serv, &prod3);

	lista1 = iaTot(&serv);
	sortarePret(&lista1,&serv, 0);

	p1 = getProdus(&lista1, 0);
	p2 = getProdus(&lista1, 1);
	p3 = getProdus(&lista1, 2);

	assert(getId(&p1) == 101);
	assert(getId(&p2) == 103);
	assert(getId(&p3) == 102);
	
	lista2 = iaTot(&serv);
	sortarePret(&lista2,&serv, 1);
	p4 = getProdus(&lista2, 0);
	p5 = getProdus(&lista2, 1);
	p6 = getProdus(&lista2, 2);
	//printf("%d:%f \n%d:%f \n%d:%.1f \n", getId(&p4), getPret(&p4),getId(&p5), getPret(&p5), getId(&p6), getPret(&p6));
	assert(getId(&p4) == 103);
	assert(getId(&p5) == 102);
	assert(getId(&p6) == 101);

}

void test_sortareCantitate()
{
	Service serv = creareService();
	Vector lista1, lista2;
	lista1 = creareVector();
	lista2 = creareVector();
	Produs prod1, prod2, prod3;
	prod1 = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "40");
	prod2 = creazaProdus("102", "Mouse", "Alfa", "Hama", "35", "30");
	prod3 = creazaProdus("103", "Tastatura", "Luna", "HP", "80", "10");
	adaugare(&serv, &prod1);
	adaugare(&serv, &prod2);
	adaugare(&serv, &prod3);

	lista1 = iaTot(&serv);
	lista2 = iaTot(&serv);

	sortareCantitate(&lista1, &serv, 0);
	prod1 = getProdus(&lista1, 0);
	prod2 = getProdus(&lista1, 1);
	prod3 = getProdus(&lista1, 2);

	assert(getId(&prod1) == 103);
	assert(getId(&prod2) == 102);
	assert(getId(&prod3) == 101);

	sortareCantitate(&lista2, &serv, 1);
	prod1 = getProdus(&lista2, 0);
	prod2 = getProdus(&lista2, 1);
	prod3 = getProdus(&lista2, 2);

	assert(getId(&prod1) == 101);
	assert(getId(&prod2) == 102);
	assert(getId(&prod3) == 103);
}