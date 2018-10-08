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
	l._prim = NULL;
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

PNod cautaRec(TElem el, PNod p) {
	if (p != NULL)
		if (p->e == el)
			return p;
		else cautaRec(el, p->urm);
	else return NULL;
}

PNod cauta(TElem e, Lista l){
	return cautaRec(e,l._prim);
}


bool isSetRec(PNod p,Lista l) {
	if (p != NULL) {
		if (cauta(p->e, l) != p)
			return false;
		else return isSetRec(p->urm,l);
	}
	else return true;
}

bool isSet(Lista l){
	return isSetRec(l._prim,l);
}

int nrDistRec(PNod p, Lista l) {
	if (p != NULL) {
		if (cauta(p->e, l) == p)
			return 1 + nrDistRec(p->urm, l);
		else return nrDistRec(p->urm, l);
	}
	else return 0;
}

int nrDist(Lista l){
	return nrDistRec(l._prim,l);
}

int sizee(PNod p) {
	if (p != NULL)
		return 1 + sizee(p->urm);
	else return 0;
}

bool evenNrElems(Lista l){
	if (sizee(l._prim) % 2 == 0)
		return true;
	else return false;
}

PNod anterior(PNod ant, PNod curent, PNod nod) {
	if (curent != NULL) {
		if (curent != nod) {
			ant = curent;
			anterior(ant, curent->urm, nod);
		}
		else {
			return ant;
		}
	}
	else return NULL;
}

Lista deleteElemRec(TElem el, PNod p, Lista l) {
	if (p != NULL) {
		PNod next = p->urm;
		if (p->e == el) {
			PNod ant = anterior(NULL, l._prim,p);
			if (ant == NULL) {
				l._prim = next;
			}
			else {
				ant->urm = next;
			}
			delete p;
		}
		return deleteElemRec(el, next, l);
	}
}

Lista deleteElem(TElem e, Lista l){
	return deleteElemRec(e, l._prim,l);
}

int gcd(int a, int b) {
	if (a == 0)return 1;
	if (b == 0) return 1;
	if (a < b) return gcd(a, b - a);
	else if (a > b) return gcd(a-b,b);
	else return a;
}

int cmmdcRec(int cmmdc, PNod p) {
	if (p != NULL) {
		return cmmdcRec(gcd(cmmdc, p->e), p->urm);
	}
	else return cmmdc;
}

int cmmdc(Lista l){
	return cmmdcRec(l._prim->e,l._prim);
}

PNod cautPointer(int i,int n, PNod p) {
	if (p != NULL)
		if (i < n) return cautPointer(i + 1, n, p->urm);
		else return p;
	else return NULL;
}

PNod creareNod(int el) {
	PNod p = new Nod;
	p->e = el;
	p->urm = NULL;
	return p;
}

void insertElem(int e, int poz, Lista l){
	PNod point = cautPointer(1, poz, l._prim);
	PNod toInsert = creareNod(e);
	if (point == l._prim) {
		toInsert->urm = l._prim;
		l._prim = toInsert;
	}
	else {
		PNod ant = anterior(NULL, l._prim, point);
		toInsert->urm = point;
		ant->urm = toInsert;
	}
	
}

PNod ultim(PNod p) {
	if (p->urm != NULL)
		ultim(p->urm);
	else return p;
}

void adaugaSfarsit(int el, Lista l){
	PNod p = creareNod(el);
	if(l._prim==NULL){
		l._prim == p;
	}
	else {
		PNod sf = ultim(l._prim);
		sf->urm = p;
	}
}

PNod addLista(PNod pntl, PNod p) {
	if (p != NULL) {
		PNod r = creareNod(p->e);
		pntl->urm = r;
		addLista(pntl->urm, p->urm);
		return pntl;
	}
}

Lista concatenare(Lista l1, Lista l2)
{
	Lista lc;
	PNod p = creareNod(l1._prim->e);
	lc._prim=addLista(p, l1._prim->urm);
	PNod u = ultim(lc._prim);
	addLista(u, l2._prim);//Dupa "u" (ultimul elem) adaug element, nu dupa lc._prim!!
	return lc;
}

bool egaleRec(PNod p1, PNod p2) {
	if (p1 != NULL && p2 == NULL || p1 == NULL && p2 != NULL)
		return false;
	else if (p1!=NULL &&p2!=NULL)
			if (p1->e == p2->e)
				return egaleRec(p1->urm, p2->urm);
			else return false;
	else return true;
}

bool egale(Lista l1, Lista l2)
{
	return egaleRec(l1._prim,l2._prim);
}

PNod intersectieRec(PNod padd,Lista l, PNod p) {
	if (p != NULL) {
		if (cauta(p->e, l) != NULL) {
			PNod r = creareNod(p->e);
			padd->urm = r;
			intersectieRec(r, l, p->urm);
			return padd;
		}
		else {
			intersectieRec(padd, l, p->urm);
			return padd;
		}
	}
	else return padd;
}

Lista intersectie(Lista l1, Lista l2)
{
	Lista lf;
	PNod primul = creareNod(0);//Pun un nod random la inceputul listei
	lf._prim = intersectieRec(primul, l1, l2._prim);
	lf._prim = lf._prim->urm;//Elimin nodul random
	return lf;
}

int produs(PNod p) {
	if (p != NULL)
		return p->e*produs(p->urm);
	else return 1;
}

int cmmmc(Lista l){
	return produs(l._prim) / cmmdc(l);
}

void substituireRec(PNod p, int e1, int e2) {
	if (p != NULL) {
		if (p->e == e1) p->e = e2;
		substituireRec(p->urm, e1, e2);
	}
}

Lista substituire(Lista l, int e, int e1){
	substituireRec(l._prim, e, e1);
	return l;
}

Lista inversare(Lista l)
{
	return Lista();
}

int elemMaxRec(PNod p,int max) {
	if (p != NULL)
		if (p->e > max) return elemMaxRec(p->urm, p->e);
		else return elemMaxRec(p->urm, max);
	else return max;
}

int elemMax(Lista l){
	return elemMaxRec(l._prim,l._prim->e);
}

int detNumarRec(PNod p, int nr) {
	if (p != NULL)
		if (p->e % 2 == 0) return detNumarRec(p->urm, nr + p->e);
		else return detNumarRec(p->urm, nr - p->e);
	else return nr;
}

int detNumar(Lista l){
	return detNumarRec(l._prim,0);
}

PNod diferentaRec(PNod padd, Lista l, PNod p) {
	if (p != NULL) {
		if (cauta(p->e, l) == NULL) {
			PNod r = creareNod(p->e);
			padd->urm = r;
			diferentaRec(r, l, p->urm);
			return padd;
		}
		else {
			diferentaRec(padd, l, p->urm);
			return padd;
		}
	}
	else return padd;
}

Lista diferenta(Lista l1, Lista l2)
{
	Lista lf;
	PNod primul = creareNod(0);//Pun un nod random la inceputul listei
	lf._prim = diferentaRec(primul, l2, l1._prim);
	lf._prim = lf._prim->urm;//Elimin nodul random
	return lf;
}

int dimensiune(Lista l)
{
	return sizee(l._prim);
}