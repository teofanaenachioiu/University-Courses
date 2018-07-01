#pragma once
#include "Multime.h"
#include "Colectie.h"
#include "Domain.h"
typedef int TCheie;
typedef Camera TComparabil;
using relatie = bool(*)(const TCheie& c1, const TCheie& c2);
class Iterator;
template<typename T>class Multime;
template<typename T>class Colectie;

/**********
* ELEMENT *
***********/
class TElement {
private:
	TCheie c;
	TComparabil v;
public:
	TElement() = default;
	TElement(const TCheie& c, const TComparabil& v) :c{ c }, v{ v } {}
	//getter pentru cheie element
	const TCheie & getCheie() const {
		return c;
	}
	//getter pentru valoare element
	const TComparabil & getValoare() const {
		return v;
	}
	//suprascriere operator !=
	friend bool operator!=(const TElement& l, const TElement& r)
	{
		return l.getCheie() != r.getCheie();
	}
	friend class Nod;
	friend class DictionarOrdonat;
	friend class Iterator;
};

/*******************
* NOD PE DICTIONAR *
********************/
class Nod {
private:
	TElement e;
	Nod * urm;
	Nod * prec;
public:
	Nod(const TElement e) :e{ e }, urm{ nullptr }, prec{ nullptr } {}
	friend class TElement;
	friend class DictionarOrdonat;
	friend class Iterator;
};

/********************
* DICTIONAR ORDONAT *
*********************/

class DictionarOrdonat {
private:
	TCheie c;
	TComparabil v;
	Nod * _prim;
	Nod * _ultim;
	unsigned _size;
	relatie _rel;

	Nod* creeazaNod(const TCheie& c, const TComparabil& v) {
		TElement e = { c,v };
		Nod *p = new Nod(e);
		return p;
	}
	friend class Nod;
	friend class TElement;
	friend class Iterator;
	friend class Multime<TCheie>;
	friend class Multime<TElement>;
	friend class Colectie<TComparabil>;

public:
	DictionarOrdonat(relatie r) noexcept;
	void adauga(const TCheie& c, const TComparabil& v);
	bool cauta(const TCheie& c, TComparabil& v);
	void sterge(TCheie c, TComparabil v);
	const unsigned& dim() const;
	const bool vid();
	const Multime<TCheie>& chei(Multime<TCheie> &m) const;
	const Colectie<TComparabil>& valori(Colectie<TComparabil> &col) const;
	const Multime<TElement>& perechi(Multime<TElement> &m) const;
	Iterator iterator();
	Iterator iterator() const;
	~DictionarOrdonat();
};

/************************
* ITERATOR PE DICTIONAR *
*************************/
class Iterator {
private:
	const DictionarOrdonat& d;
	Nod* curent;
public:
	friend class DictionarOrdonat;
	Iterator(const DictionarOrdonat& d) :d{ d } {}
	//refera primul element din dictionar
	void prim() {
		curent = d._prim;
	}
	//refera urmatorul element dupa cel curent
	void urmator() {
		curent = curent->urm;
		if (valid())
		{
			curent = curent->urm;
			if(valid())curent = curent->urm;
		}
		

	}
	//precizeaza(true/false) daca pozitia curenta este o pozitie valida
	bool valid() const {
		return curent != nullptr;
	}
	//returneaza elementul de pe pozitia curenta
	TElement element() const {
		return curent->e;
	}
};

void test();