#pragma once
#include "vector.h"

typedef struct
{
	Vector* v;
}Repo;

Repo* makeRepo();

void destroyRepo(Repo* ropo);

int store(Repo* memory, Produs* prod);

int find(Repo* memory, int id);

int updatePret(Repo* memory, Produs* prod, double pret);

int updateCantitate(Repo* memory, Produs* prod, int cantitate);

int del(Repo* memory, Produs* prod);

Vector* getAll(Repo* memory);

void test_store();

void test_del();

void test_find();

void test_update();

void test_getAll();