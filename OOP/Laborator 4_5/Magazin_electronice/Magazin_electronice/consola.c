#include "validare.h"
#include "consola.h"
#include <stdio.h>
#include <malloc.h>
#include <stdlib.h>
#include <assert.h>

Consola* creazaConsola()
{
	/*Se creaza Consola
	return: pointer la consola*/
	Consola* cons = (Consola*)malloc(sizeof(Consola));
	cons->s = creareService();
	return cons;
}


Service* getService(Consola* cons)
{
	/*Getter service*/
	return cons->s;
}

void distrugeConsola(Consola *cons)
{
	/*Functia de distrugere consola*/
	distrugeService(cons->s);
	free(cons);
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

int Id()
{
	/*Citire id produs
	return: pointer la char*/
	char * id = (char*)malloc(sizeof(char)*50);
	int nr;
	//while (getchar() != '\n');
	while (validareNrIntreg(id) == 0)
	{
		printf("Id: "); //getchar();
		
		//fgets(id, 50, stdin);
		//getchar();
		scanf_s("%s",id,50);
	}
	nr = atoi(id);
	free(id);
	return nr;
}

int Cantitate()
{
	/*Citire stoc produs
	return: pointer la char*/
	char * cantitate = (char*)malloc(sizeof(char)*50);
	int nr;
	while (getchar() != '\n');
	while (validareNrIntreg(cantitate) == 0)
	{
		printf("Cantitate: ");
		
		scanf_s("%s", cantitate, 50);
		//getchar();
	}
	nr = atoi(cantitate);
	free(cantitate);
	return nr;
}

double Pret()
{
	/*Citire pret produs
	return: pointer la char*/
	char * pret = (char*)malloc(sizeof(char)*50);
	double nr;
	while (getchar() != '\n');
	while (validareNrReal(pret) == 0)
	{
		printf("Pret: ");
		
		scanf_s("%s", pret, 50);
		//getchar();
	}
	nr = atof(pret);
	free(pret);
	return nr;
}

char *Producator()
{
	/*Citire producatorul produsului
	return: pointer la char*/
	char * producator = (char*)malloc(sizeof(char)*50);
	while (getchar() != '\n');
	while (validareCuvant(producator) == 0)
	{
		printf("Producator: "); 
		
		scanf_s("%s", producator, 50);
		//getchar();
		//while (getchar() != '\n');
	}
	return producator;
}

char* Tip()
{
	/*Citire tip produs
	return: pointer la char*/
	char * tip = (char*)malloc(sizeof(char)*50);
	while (getchar() != '\n');
	while (validareCuvant(tip) == 0)
	{
		printf("Tip: "); //getchar();	
		scanf_s("%s", tip, 50);
		//getchar();
		//while (getchar() != '\n');
	}
	return tip;
}
char* Model()
{
	/*Citire model produs
	return: pointer la char*/
	char * model = (char*)malloc(sizeof(char)*50);
	while (getchar() != '\n');
	while (validareCuvant(model) == 0)
	{
		printf("Model: "); //getchar();
		//while (getchar() != '\n');
		scanf_s("%s", model, 50);
		//getchar();
		//while (getchar() != '\n');
	}
	return model;
}


Produs * input()
{
	int id = Id();
	char *tip = Tip(), *model = Model(), *producator = Producator();
	int cantitate = Cantitate();
	double pret = Pret();
	Produs* prod = creazaProdus(id, tip, model, producator, pret, cantitate);
	return prod;
}

void filtrare(Service *serv, char  criteriu)
{
	/*
	Se apeleaza convenabil functiile de filtrare si se afiseaza elementele rezultate.
	criteriu - nr inreg
	criteriu: 1 (filtrare dupa producator)
	2 (filtrare dupa pret)
	3 (filtrare dupa cantitate)
	*/
	int i;
	Vector* lista = creareVector();
	switch (criteriu)
	{
	case '1':
	{
		getProducatorList(lista, serv, Producator());
		break;
	}
	case '2':
	{
		getPretList(lista, serv, Pret());
		break;
	}
	case '3':
	{
		getCantitateList(lista, serv, Cantitate());
		break;
	}
	default:
		break;
	}
	printf("|  Numar produse: %d\n", getDim(lista));
	if (getDim(lista) > 0)
	{
		printf("|  Produse in stoc: \n");
		for (i = 0; i < getDim(lista); i++)
		{

			printf("|  Produs: %d | %s | %s | %s | %.2f | %d\n", getId(getProdus(lista, i)), getTip(getProdus(lista, i)), getModel(getProdus(lista, i)), getProducator(getProdus(lista, i)), getPret(getProdus(lista, i)), getCantitate(getProdus(lista, i)));

		}
	}
	else printf("|  Nu exista produse in stoc.\n");
	free(lista->prods);
	free(lista);
}

void vizualizareProduse(Service *serv, char  mod)
{
	/*
	Se apeleaza convenabil functiile de sortare si se afiseaza elementele .
	mod - nr inreg (modul de sortare)
	mod: 1(sortare crescatoare dupa pret),
	2(sortare descrescatoare dupa pret),
	3(sortare crescatoare dupa cantitate),
	4(sortare descrescatoare dupa cantitate)
	*/
	int i;
	if (getDim(listaSortata(serv, mod)) > 0)
	{
		printf("|  Produse in stoc: \n");
		for (i = 0; i < getDim(listaSortata(serv, mod)); i++)
		{

			printf("|  Produs: %d | %s | %s | %s | %.1f | %d\n", getId(getProdus(listaSortata(serv, mod), i)), getTip(getProdus(listaSortata(serv, mod), i)), getModel(getProdus(listaSortata(serv, mod), i)), getProducator(getProdus(listaSortata(serv, mod), i)), getPret(getProdus(listaSortata(serv, mod), i)), getCantitate(getProdus(listaSortata(serv, mod), i)));
		}
	}
	else printf("|  Nu exista produse in stoc.\n");
}

void adauga(Consola * cons)
{
	/*Se adauga un nou produs in service.
	In cazul in care id-ul sau exista deja, se vor actualiza cantitatea si pretul sau.*/
	
	
	Produs* prod = input();
	if (cauta(getService(cons), prod) == -1)
	{
		adaugare(getService(cons), prod);
		printf("|  Produsul a fost adaugat.\n");
		//distrugeProdus(prod);
	}
	else {
		printf("|  ID-ul exista deja. Produsul a fost actualizat.\n");
		if(actualizareCantitate(getService(cons), prod, getCantitate(prod))==1)
			printf("|  Stocul produsului a fost actualizat.\n");
		if (actualizarePret(getService(cons), prod, getPret(prod))==1)
			printf("|  Pretul produsul a fost actualizat.\n");
	}
}

void sterge(Consola * cons)
{
	/*Se sterge un produs din service.
	Se citeste un id si cu acesta se creaza un produs fals, ce trebuie, mai departe, cautat in service.*/
	int id = Id();
	Produs * prod = creazaProdus(id, "fara", "fara", "fara", 1, 1);
	if (cauta(getService(cons), prod) != -1)
	{
		stergere(getService(cons), prod);
		printf("|  Produsul a fost sters!\n");
	}
	else printf("|  Nu exista produs cu id-ul specificat!\n");
	distrugeProdus(prod);
}

void actualizeaza(Consola * cons)
{
	/*Se actualizeaza un produs.
	Se citeste un id si cu acesta se creaza un produs fals, ce trebuie, mai departe, cautat in service.*/
	int poz, mod,id = Id();
	Produs *prod = creazaProdus(id, "fara", "fara", "fara", 1, 1);

	poz = cauta(getService(cons), prod);
	if (poz == -1)
		printf("|  Nu exista produs cu id-ul specificat!\n");
	else {
		printf("Actualizati:\n");
		printf("1. Pret\n");
		printf("2. Cantitate\n");
		printf("Alegeti optiunea:\n");
		scanf_s("%d", &mod);
		if (mod == 2)
		{
			int cantitate = Cantitate();
			if(actualizareCantitate(getService(cons), prod, cantitate)==1)
				printf("|  Stocul a fost actualizat!\n");
		}
		if (mod == 1)
		{
			double pret = Pret();
			if(actualizarePret(getService(cons), prod, pret)==1)
				printf("|  Pretul a fost actualizat!\n");
		}
		if ((mod != 1) && (mod != 2))
			printf("|  Optiune inexistenta!\n");
	}
}

void filtreaza(Consola * cons)
{
	/*Submeniu pentru filtrarea produselor
	Apelul functiei de filtrare*/
	char mod;
	printf("Filtrare dupa:\n");
	printf("1. Producator\n");
	printf("2. Pret\n");
	printf("3. Cantitate\n");
	printf("Alegeti optiunea: ");
	if (scanf_s(" %c", &mod,sizeof(char)) == 1) {
		if ((mod == '1') || (mod == '2') || (mod == '3'))
			filtrare(getService(cons), mod);
		else printf("|  Optiune inexistenta!\n");
	}
	else printf("Cititi un numar intreg!\n");

}

void afiseaza(Consola * cons)
{
	/*Submeniu pentru afisarea produselor
	Apelul functiei de afisare*/
	char mod;
	
	printf("Ordonare dupa:\n");
	printf("1. Pret - crescator\n");
	printf("2. Pret - descrescator\n");
	printf("3. Cantitate - crescator\n");
	printf("4. Cantitate - descrescator\n");
	printf("Alegeti optiunea: ");
	if (scanf_s(" %c", &mod, sizeof(char)) == 1) {
		if ((mod == '1') || (mod == '2') || (mod == '3') || (mod == '4'))
			vizualizareProduse(getService(cons), mod);
		else printf("|  Optiune inexistenta!\n");
	}
	else printf("Cititi un numar intreg!\n");
}


void start()
{
	/*Interfata cu utilizatorul*/
	Consola* cons = creazaConsola();
	char cmd;
	while (1)
	{
		meniu();
		printf("Comanda: ");
		if (scanf_s(" %c", &cmd, sizeof(char)) == 1)
		{
			//getchar();
			if (cmd == '1') {
				adauga(cons);
				continue;
			}
			if (cmd == '2') {
				actualizeaza(cons);
				continue;
			}
			if (cmd == '3') {
				sterge(cons);
				continue;
			}
			if (cmd == '4') {
				afiseaza(cons);
				continue;
			}

			if (cmd == '5') {
				filtreaza(cons);
			}

			if (cmd == '0') {
				printf("Aplicatia a fost inchisa cu succes!\n");
				printf("___________________________________\n");
				distrugeConsola(cons);
				break;
			}
			if ((cmd < '0') || (cmd > '5'))
			{
				printf("Comanda gresita!\n");
				continue;
			}
		}
	}
}

void test_getService()
{
	/*Teste pentru functiile creazaConsola, getService, distrugeConsola*/
	Consola *cons = creazaConsola();
	assert(getDim(iaTot(getService(cons))) == 0);
	distrugeConsola(cons);
}