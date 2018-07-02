#pragma once

typedef struct {
	char tip[20], model[20], producator[20];
	float pret;
	int cantitate, id;
}Produs;


Produs creazaProdus(char id[20], char tip[20], char model[20], char producator[20], char pret[20], char cantitate[20]);


int getId(Produs* p);

char* getTip(Produs* p);

char* getModel(Produs* p);

char* getProducator(Produs* p);

float getPret(Produs* p);

int getCantitate(Produs* p);


int setId(Produs* p, int id);

char* setTip(Produs* p, char tip[20]);

char* setModel(Produs* p, char model[20]);

char* setProducator(Produs* p, char producator[20]);

float setPret(Produs* p, float pret);

int setCantitate(Produs* p, int cantitate);


int comparaProduse(Produs* p1, Produs* p2);

void swap(Produs* p1, Produs* p2);

void atribuire(Produs* p1, Produs* p2);


void test_creazaProdus();

void test_comparaProduse();

void test_swap();

void test_atribuire();

void test_set();