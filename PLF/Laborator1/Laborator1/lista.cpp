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

int elementRec(PNod p, int k) {
	if (k == 1) return p->e;
	if (p == NULL) return NULL;
	return elementRec(p->urm, k-1);
}

int element(Lista l, int k){
	return elementRec(l._prim,k);
}

bool nuExista(PNod p, int el) {
	if (p == NULL)return true;
	if (p->e == el) return false;
	return nuExista(p->urm, el);
}

bool eMultimeRec(PNod p) {
	if (p == NULL) return true;
	return nuExista(p->urm, p->e) && eMultimeRec(p->urm);
}
bool eMultime(Lista l)
{
	return eMultimeRec(l._prim);
}

int nrDistincteRec(PNod p) {
	if (p == NULL)return 0;
	if (nuExista(p->urm, p->e) == true)
		return 1 + nrDistincteRec(p->urm);
	return nrDistincteRec(p->urm);
}

int nrDistincte(Lista l){
	return nrDistincteRec(l._prim);
}

PNod  inversRec(PNod p, PNod ant) {
	if (p == NULL) return ant;
	else {
		PNod r = new Nod();
		r->e = p->e;
		r->urm = ant;
		return inversRec(p->urm, r);
	}
}

Lista invers(Lista l){
	Lista laux;
	/*PNod p = new Nod();
	p->e = 0;
	p->urm = NULL;*/
	laux._prim =inversRec(l._prim,NULL);
	return laux;
}


/*PNod ultim(PNod p) {
	if (p->urm == NULL)
		return p;
	else return ultim(p->urm);
}

PNod listaNouaRec(PNod p) {
	if (p == NULL)
		return NULL;
	else {
		PNod r = new Nod();
		r->e = p->e;
		r->urm = listaNouaRec(p->urm);
		return r;
	}
}

Lista listaNoua(PNod adg) {
	Lista l;
	l._prim = listaNouaRec(adg);
	return l;
}

PNod substitutieRec(PNod p, PNod ant, int e, Lista l2) {
	if (p == NULL)
		return NULL;
	if (p->e == e) {
		PNod r = p->urm;
		Lista lNou = listaNoua(l2._prim);
		p = lNou._prim;
		PNod u = ultim(lNou._prim);
		u->urm = r;
		return substitutieRec(r, u, e, l2);
	}
	return substitutieRec(p->urm,p, e, l2);
}

PNod substituie(Lista l1, int e, Lista l2)
{
	l1._prim=substitutieRec(l1._prim,NULL, e, l2);
	return l1._prim;
}*/
