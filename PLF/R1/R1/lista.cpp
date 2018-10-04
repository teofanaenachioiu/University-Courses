#include "lista.h"
#include <iostream>

using namespace std;


PNod creare_rec() {
	TElem x;
	cout << "x=";
	cin >> x;
	if (x == 0)
		return NULL;
	else {
		PNod p = new Nod();
		p->e = x;
		p->urm = creare_rec();
		return p;
	}
}

Lista creare() {
	Lista l;
	l._prim = creare_rec();
	return l;
}

void tipar_rec(PNod p) {
	if (p != NULL) {
		cout << p->e << " ";
		tipar_rec(p->urm);
	}
}

void tipar(Lista l) {
	tipar_rec(l._prim);
}

void distrug_rec(PNod p) {
	if (p != NULL) {
		distrug_rec(p->urm);
		delete p;
	}
}

void distruge(Lista l) {
	//se elibereaza memoria alocata nodurilor listei
	distrug_rec(l._prim);
}

bool eListaVida(Lista l){
	return l._prim==NULL;
}

Lista creeazaListaVida(){
	Lista l;
	l._prim = NULL;
	return l;
}

int primElement(Lista l){
	return l._prim->e;
}

Lista sublista(Lista l){
	l._prim = l._prim->urm;
	return l;
}

Lista adaugaInceput(int elem, Lista l){
	PNod p = new Nod();
	p->e = elem;
	p->urm = l._prim;
	l._prim = p;
	return l;
}

bool nrParElemAux(Lista l, bool ok) {
	if (eListaVida(l)) return ok;
	else return nrParElemAux(sublista(l),!ok);
}

bool nrParElem(Lista l){
	return nrParElemAux(l,true);
}

Lista stergeElem(Lista l, int elem){
	if (eListaVida(l) == true)
		return creeazaListaVida();
	if (primElement(l) == elem) 
		return stergeElem(sublista(l), elem);
	return adaugaInceput(primElement(l),stergeElem(sublista(l),elem));
}


