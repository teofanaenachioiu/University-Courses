#pragma once

typedef struct {
	char* tip, *model, *producator;
	double pret;
	int cantitate, id;
}Produs;


Produs* creazaProdus(int id, char* tip, char* model, char* producator, double pret, int cantitate);

void distrugeProdus(Produs * prod);

Produs* copieProdus(Produs* p);

int getId(Produs* p);

char* getTip(Produs* p);

char* getModel(Produs* p);

char* getProducator(Produs* p);

double getPret(Produs* p);

int getCantitate(Produs* p);


int setId(Produs* p, int id);

char* setTip(Produs* p, char* tip);

char* setModel(Produs* p, char* model);

char* setProducator(Produs* p, char* producator);

double setPret(Produs* p, double pret);

int setCantitate(Produs* p, int cantitate);


int comparaProduse(Produs* p1, Produs* p2);

void swap(Produs* p1, Produs* p2);

void atribuire(Produs* p1, Produs* p2);


void test_creazaProdus();

void test_copieProdus();

void test_comparaProduse();

void test_swap();

void test_atribuire();

void test_set();