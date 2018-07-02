#include "produs.h"
#include "vector.h"
#include "repository.h"
#include "validare.h"
#include "service.h"
#include "consola.h"
#include <stdio.h>
#include <stdlib.h>

Consola creazaConsola()
{
	Consola cons;
	cons.s = creareService();
	return cons;
}

Service getService(Consola cons)
{
	return cons.s;
}

void meniu()
{
	/*
	Meniul principal al aplicatiei
	*/
	printf("-----------------------------------\n");
	printf("-      Magazin electronice        -\n");
	printf("-----------------------------------\n");
	printf("- Alegeti o comanda din meniu:    -\n");
	printf("-----------------------------------\n");
	printf("- 1. Adauga produs                -\n");
	printf("- 2. Actualizare produs           -\n");
	printf("- 3. Sterge produs                -\n");
	printf("- 4. Vizualizare produse din stoc -\n");
	printf("- 5. Vizualizare produse filtrate -\n");
	printf("- 0. Iesire                       -\n");
	printf("-----------------------------------\n");
}

void citireId(char id[20])
{
	do {
		printf("Id: ");
		scanf("%s", id);
	} while (validareNrIntreg(id)==0);
}

void citirePret(char str[20])
{
	do {
		printf("Pret: ");
		scanf("%s", str);
	} while (validareNrReal(str) == 0);
}

void citireProducator(char str[20])
{
	do {
		printf("Producator: ");
		scanf("%s", str);
	} while (validareCuvant(str) == 0);
}

void citireCantitate(char cantitate[20])
{
	do {
		printf("Cantitate: ");
		scanf("%s", cantitate);
	} while (validareNrIntreg(cantitate) == 0);
}

void citire(Produs *prod)
{
	{
		char id[10], tip[20], model[20], producator[20], pret[20], cantitate[20];

		do {
			printf("Id: ");
			scanf("%s", id);
		} while (validareNrIntreg(id) == 0);

		do {
			printf("Tip: ");
			scanf("%s", tip);
		} while (validareCuvant(tip) == 0);

		do {
			printf("Model: ");
			scanf("%s", model);
		} while (validareCuvant(model) == 0);

		do {
			printf("Producator: ");
			scanf("%s", producator);
		} while (validareCuvant(producator) == 0);

		do {
			printf("Pret: ");
			scanf("%s", pret);
		} while (validareNrReal(pret) == 0);

		do {
			printf("Cantitate: ");
			scanf("%s", cantitate);
		} while (validareNrIntreg(cantitate) == 0);

		*prod = creazaProdus(id, tip, model, producator, pret, cantitate);
	}
}
void filtrare(Service *serv, int  criteriu)
{
	/*
	Functia generica apeleaza convenabil o alta functie de filtrare si afiseaza elementele rezultate.
	memory - Lista Produse
	criteriu - nr inreg
	*/
	int i;
	char producator[20], cantitate[20], pret[20];
	Vector lista;
	Produs p;
	switch (criteriu)
	{
	case 1:
	{
		citireProducator(producator);
		lista = getProducatorList(serv, producator);
		break;
	}
	case 2:
	{
		citirePret(pret);
		lista = getPretList(serv, atof(pret));
		break;
	}
	case 3:
	{
		citireCantitate(cantitate);
		lista = getCantitateList(serv, atoi(cantitate));
		break;
	}
	default:
		break;
	}
	printf("Numar produse: %d\n", getDim(&lista));
	if (getDim(&lista) > 0)
	{
		printf("Produse in stoc: \n");
		for (i = 0; i < getDim(&lista); i++)
		{
			p = getProdus(&lista, i);
			printf("Produs: %d | %s | %s | %s | %.2f | %d\n", getId(&p), getTip(&p), getModel(&p), getProducator(&p), getPret(&p), getCantitate(&p));

		}
	}
	else printf("Nu exista produse in stoc.\n");
}

void vizualizareProduse(Service *serv, int  mod)
{
	/*
	Functia apeleaza convenabil o alta functie de sortare si afiseaza elementele .
	memory - Lista Produse
	mod - nr inreg (modul de sortare)
	*/
	int i;
	Produs p;
	Vector lista = listaSortata(serv, mod);
	if (getDim(&lista) > 0)
	{
		printf("Produse in stoc: \n");
		for (i = 0; i < getDim(&lista); i++)
		{
			p = getProdus(&lista,i);
			printf("Produs: %d | %s | %s | %s | %.2f | %d\n", getId(&p), getTip(&p), getModel(&p), getProducator(&p), getPret(&p), getCantitate(&p));
		}
	}
	else printf("Nu exista produse in stoc.\n");
}
void start()
{
	/*Interfata cu utilizatorul*/
	Consola cons = creazaConsola();

	Service serv = getService(cons);
	Produs prod;
	char id[20], cantitate[20], pret[20];
	int cmd, mod,  poz;

	while (1)
	{
		meniu();
		printf("Comanda: ");
		scanf("%d", &cmd);
		if (cmd == 1) {
			citire(&prod);
			if (cauta(&serv,&prod) == -1)
				adaugare(&serv, &prod);
			else {
				actualizareCantitate(&serv, &prod, getCantitate(&prod));
				actualizarePret(&serv, &prod, getPret(&prod));
				printf("ID-ul exista deja. Produsul a fost actualizat.\n");
			}
			continue;
		}
		if (cmd == 2) {
			citireId(id);
			prod = creazaProdus(id, "fara", "fara", "fara", "1", "1");
			poz = cauta(&serv, &prod);
			if (poz == -1)
				printf("Nu exista produs cu id-ul specificat!\n");
			else {
				printf("Actualizati:\n");
				printf("1. Pret\n");
				printf("2. Cantitate\n");
				printf("Alegeti optiunea:\n");
				scanf("%d", &mod);
				if (mod == 2)
				{
					citireCantitate(cantitate);
					actualizareCantitate(&serv, &prod, atoi(cantitate));
					printf("Stocul a fost actualizat!\n");
				}
				if (mod == 1)
				{
					citirePret(pret);
					actualizarePret(&serv, &prod, atof(pret));
					printf("Pretul a fost actualizat!\n");
				}
				if ((mod != 1) && (mod != 2))
					printf("Optiune inexistenta!\n");
			}
		}
		if (cmd == 3) {
			citireId(id);
			prod = creazaProdus(id, "fara", "fara", "fara", "1", "1");
			if (cauta(&serv, &prod) != -1)
			{
				stergere(&serv, &prod);
				printf("Produsul a fost sters!\n");
			}
			else printf("Nu exista produs cu id-ul specificat!\n");
		}
		if (cmd == 4) {
			printf("Ordonare dupa:\n");
			printf("1. Pret - crescator\n");
			printf("2. Pret - descrescator\n");
			printf("3. Cantitate - crescator\n");
			printf("4. Cantitate - descrescator\n");
			printf("Alegeti optiunea: ");
			scanf("%d", &mod);
			if ((mod == 1) || (mod == 2) || (mod == 3) || (mod == 4))
				vizualizareProduse(&serv, mod);
			else printf("Optiune inexistenta!\n");
		}

		if (cmd == 5) {
			printf("Filtrare dupa:\n");
			printf("1. Producator\n");
			printf("2. Pret\n");
			printf("3. Cantitate\n");
			printf("Alegeti optiunea: ");
			scanf("%d", &mod);
			if ((mod == 1) || (mod == 2) || (mod == 3))
				filtrare(&serv, mod);
			else printf("Optiune inexistenta!\n");

		}

		if (cmd == 0) {
			printf("Aplicatia a fost inchisa cu succes!\n");
			printf("___________________________________\n");
			break;
		}
		if ((cmd<0) || (cmd>5))
		{
			printf("Comanda gresita!\n");
			continue;
		}
	}
}