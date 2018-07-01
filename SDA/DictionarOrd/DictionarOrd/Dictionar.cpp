#include "Dictionar.h"
#include <assert.h>
#include <iostream>
/*
Descrierea relatiei de ordine dintre chei
*/
bool functie(const int& c1, const int& c2) {
	return c1 < c2;
}
/*
Se creeaza dictionarul ordonat cu relatia de ordine rel
Initial dictionarul este vid(fara elemente)
*/
DictionarOrdonat::DictionarOrdonat(relatie r) noexcept
{
	_rel = r;
	_size = 0;
	_prim = nullptr;
	_ultim = nullptr;
}
/*
Se adauga perechea (cheie, valoare) in dictionar, respectand relatia de ordine rel
Cheia este de tip TCheie, valoarea este de tip TComparabil
*/
inline void DictionarOrdonat::adauga(const TCheie& c, const TComparabil& v)
{

	Nod *p = creeazaNod(c, v);
	TComparabil vv = v;
	if (cauta(c, vv) == false) {
		_size += 1;
		//dictionarul e vid
		if (this->_prim == nullptr) {
			this->_prim = p;
			this->_ultim = p;
			return;
		}
		//nodul trebuie adaugat pe prima pozitie
		if (_rel(c, this->_prim->e.c) == true) {
			p->urm = this->_prim;
			this->_prim->prec = p;
			this->_prim = p;
			return;
		}
		//nodul trebuie adaugat pe ultima pozitie
		if (_rel(c, this->_ultim->e.c) == false) {
			this->_ultim->urm = p;
			p->prec = this->_ultim;
			this->_ultim = p;
			return;
		}
		//nodul trebuie adaugat undeva in dictionar
		Nod* r = this->_prim;
		while (r != nullptr && (_rel(c, r->e.c) == false))
			r = r->urm;
		p->prec = r->prec;
		p->urm = r;
		r->prec->urm = p;
		r->prec = p;
	}
}
/*
Se cauta cheia in dictionar.
Functia returneaza true in cazul in care cheia exista, false altfel
Daca exista cheia, valoarea lui v corespunzatoare se actualizeaza
*/
inline bool DictionarOrdonat::cauta(const TCheie& c, TComparabil& v)
{
	Nod *p = this->_prim;
	v = TComparabil();
	while (p != nullptr && c != p->e.c)
		p = p->urm;
	if (p != nullptr) {
		v = p->e.v;
		return true;
	}
	return false;
}
/*
Sterge un element din dictionar.
Cheia este de tip TCheie, valoarea este de tip TComparabil
*/
inline void DictionarOrdonat::sterge( TCheie c, TComparabil v)
{
	Nod *p = this->_prim;
	if (p->e.c == c) {
		this->_prim = this->_prim->urm;
		if (this->_prim == nullptr)
			this->_ultim = nullptr;
		else this->_prim->prec = nullptr;
		delete p;
		this->_size--;
		return;
	}
	while (p != nullptr && p->e.c != c)
		p = p->urm;
	if (p == this->_ultim) {
		this->_ultim = this->_ultim->prec;
		this->_ultim->urm = nullptr;
	}
	else {
		p->prec->urm = p->urm;
		p->urm->prec = p->prec;
	}
	delete p;
	this->_size--;
}

/*
Dimensiunea dictionarului
*/
inline const unsigned& DictionarOrdonat::dim() const
{
	return this->_size;
}
/*
Se vefica daca dictionarul este vid
Se returneaza true daca dictionarul este vid, false altfel
*/
inline const bool DictionarOrdonat::vid()
{
	if (this->_size == 0)
		return true;
	return false;
}
/*
Multimea cheilor din dicionar
*/
const Multime<TCheie>& DictionarOrdonat::chei(Multime<TCheie>& m) const
{
	Iterator iterator = Iterator(*this);
	iterator.prim();
	while (iterator.valid()) {
		TCheie key = iterator.element().c;
		m.adauga(key);
		iterator.urmator();
	}
	return m;
}
/*
Colectia valorilor din dictionar
*/
const Colectie<TComparabil>& DictionarOrdonat::valori(Colectie<TComparabil>& col) const
{
	Iterator iterator = Iterator(*this);
	iterator.prim();
	while (iterator.valid()) {
		TComparabil val = iterator.element().v;
		col.adauga(val);
		iterator.urmator();
	}
	return col;
}
/*
Multimea perechilor din dictionar
*/
const Multime<TElement>& DictionarOrdonat::perechi(Multime<TElement>& m) const
{
	Iterator iterator = Iterator(*this);
	iterator.prim();
	while (iterator.valid()) {
		TElement elem = iterator.element();
		m.adauga(elem);
		iterator.urmator();
	}
	return m;
}
/*
Se creeaza un iterator pe dictionar
*/
Iterator DictionarOrdonat::iterator()
{
	return Iterator(*this);
}
/*
Se creeaza un iterator pe dictionar(alta varianta)
*/
Iterator DictionarOrdonat::iterator() const
{
	return Iterator(*this);
}
/*
Destructorul dictionarului
*/
inline DictionarOrdonat::~DictionarOrdonat()
{
	while (this->_prim != nullptr) {
		Nod* to_delete = this->_prim;//elementul de sters
		(*this->_prim).prec = nullptr;//refac legaturile
		this->_prim = (*this->_prim).urm;
		delete to_delete;//sterg elementul
	}
}

/*void tiparire(DictionarOrdonat& dictionar) {
	Iterator iterator = dictionar.iterator();
	iterator.prim();
	while (iterator.valid()) {
		TElement pereche = iterator.element();
		std::cout << pereche.getCheie() << " " << pereche.getValoare() << std::endl;
		iterator.urmator();
	}
}

void tiparireValori(Colectie<TComparabil>& col) {
	IteratorC<TComparabil> iterator = col.iteratorC();
	while (iterator.valid()) {
		TComparabil val = iterator.element();
		std::cout << val << std::endl;
		iterator.urmator();
	}
}

void tiparireChei(Multime<TCheie>& col) {
	IteratorM<TCheie> iterator = col.iteratorM();
	while (iterator.valid()) {
		TCheie val = iterator.element();
		std::cout << val << std::endl;
		iterator.urmator();
	}
}

void tiparirePerechi(Multime<TElement>& m) {
	IteratorM<TElement> iterator = m.iteratorM();
	while (iterator.valid()) {
		TElement val = iterator.element();
		std::cout << val.getCheie() << " " << val.getValoare() << std::endl;
		iterator.urmator();
	}
}*/

void test()
{
	DictionarOrdonat d(functie);
	Camera c;

	//teste adauga + size
	assert(d.dim() == 0);
	d.adauga(2, Camera(2,"Bogdan",150));
	assert(d.dim() == 1);
	d.adauga(1, Camera(1,"Ana", 100));
	assert(d.dim() == 2);
	d.adauga(4, Camera(4,"Diana", 250));
	assert(d.dim() == 3);
	d.adauga(3, Camera(3,"Carmen", 200));
	assert(d.dim() == 4);
	d.adauga(3, Camera(3,"Eva", 150));
	assert(d.dim() == 4);
	//tiparire(d);

	//test cauta. Daca nu exista elem: TValoare()
	assert(d.cauta(4, c) == true);
	assert(c == Camera(4,"Diana", 250));
	assert(d.cauta(5, c) == false);

	//test valori
	Colectie<Camera> col;
	d.valori(col);
	assert(col.dim() == 4);
	//tiparireValori(col);

	//test chei
	Multime<int> m;
	d.chei(m);
	assert(m.dim() == 4);
	//tiparireChei(m);

	//test perechi
	Multime<TElement> mp;
	d.perechi(mp);
	assert(mp.dim() == 4);
	//tiparirePerechi(mp);

	//test sterge + size
	d.sterge(1, Camera(1,"Ana", 100));//la inceput
	assert(d.dim() == 3);
	d.sterge(3, Camera(3,"Carmen", 200));//la mijloc
	assert(d.dim() == 2);
	d.sterge(4, Camera(4,"Diana", 250));//la sfarsit
	assert(d.dim() == 1);
	d.sterge(2, Camera(2,"Bogdan", 150));//singurul elem
	assert(d.dim() == 0);
}