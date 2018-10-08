#pragma once
#ifndef LISTA_H
#define LISTA_H
#include<iostream>
using namespace std;
typedef int TElement;
class Iterator;
class Lista;

class Nod {
private:
	TElement e;
	Nod * urm;
public:
	Nod(const TElement e) :e{ e }, urm{ nullptr }{}
	const TElement &getValoare()const {
		return e;
	}
	friend class Iterator;
	friend class Lista;
};

class Lista {
private:
	Nod * _prim;
	Nod * _ultim;
	int _size;
	
	////
	Nod * cautaRec(Nod* p, TElement e) {
		if (p == nullptr) {
			return p;
		}
		else {
			if (p != nullptr && e == p->e)
				return p;
			else cautaRec(p->urm, e);
		}
	}

	////
	Nod * anterior(Nod *ant, Nod* p, TElement e) {
		if (p == nullptr) {
			return ant;
		}
		else {
			if (p != nullptr && e == p->e)
				return ant;
			else anterior(p, p->urm, e);
		}
	}
	

	///
	void distrug_rec(Nod* p) {
		if (p != NULL) {
			distrug_rec(p->urm);
			delete p;
		}
	}
	///
	void makeASet(Nod * p) {
		if (p != nullptr) {
			int val = p->e;
			if (cauta(val) != p) sterge(val);

			makeASet(p->urm);
		}
	}

public:
	friend class Iterator;
	Lista() {
		_size = 0;
		_ultim = nullptr;
		_prim=creare();
	}


	Nod* cauta(TElement e) {
		return cautaRec(this->_prim, e);
	}

	Nod* sterge(TElement e) {
		Nod * ant =anterior(nullptr, this->_prim, e);
		Nod * curent = cauta(e);
		if (curent == this->_prim) {
			this->_prim = curent->urm;
			if (this->_prim == nullptr) 
				this->_ultim = nullptr;
		}
		else  {
			ant->urm = curent->urm;
			if (curent == this->_ultim)
				this->_ultim = ant;
		}
		delete(curent);
		this->_size--;
		return ant;
	}

	Nod * creare(){
		TElement x;
		cout << "x="; cin >> x;
		if (x == 0) return nullptr;
		else {
				Nod *p = new Nod(x);
				this->_ultim = p;
				this->_size = this->_size + 1;
				p->urm = creare();
				return p;
			}
	}

	void adaugare(TElement e) {
		Nod *p = new Nod(e);
		if (this->_prim == nullptr) this->_prim = p;
		this->_ultim->urm = p;
		this->_ultim = p;
	}

	Nod* pozitieNodRec(Nod* p,int poz,int i) {
		if (i < poz) {
			i++;
			pozitieNodRec(p->urm,poz, i);
		}
		else return p;
	}

	Nod * pozitieNod(int poz) {
		return pozitieNodRec(this->_prim, poz, 1);
	}

	Nod* modifica(int poz,TElement e) {
		Nod* nou = new Nod(e);
		Nod* p = pozitieNod(poz);
		Nod * ant = anterior(nullptr, this->_prim, p->e);
		if (poz == 1) {
			nou->urm = p->urm;
			if (p == this->_ultim) this->_ultim = nou;
			this->_prim = nou;
		}
		else 
			if (poz == this->_size) {
				ant->urm = nou;
				this->_ultim = nou;
			}
			else {
				ant->urm = nou;
				nou->urm = p->urm;
			}
		delete(p);
		return ant;

	}

	const int dim() const {
		return this->_size;
	}

	void makeSet() {
		makeASet(this->_prim);
	}

	~Lista(){
		distrug_rec(this->_prim);
	}
};

class Iterator {
private:
	const Lista& l;
	Nod* curent;
public:
	friend class Lista;
	friend class Nod;
	Iterator(const Lista& l) :l{ l } {
		curent = l._prim;
	}
	void urmator() {
		curent = curent->urm;
	}
	bool valid() const {
		return curent != nullptr;
	}
	TElement element() const {
		return curent->e;
	}
};

#endif
