#pragma once
template<typename T>class IteratorC;
template<typename T>class Colectie;

/******************
* NOD IN COLECTIE *
*******************/
template <typename T>
class NodC {
private:
	T e;
	NodC * urm;
	NodC * prec;
public:
	NodC<T>(const T& e) :e{ e }, urm{ nullptr }, prec{ nullptr } {}
	friend class Colectie<T>;
	friend class IteratorC<T>;
};

/***********
* COLECTIE *
************/
template<typename T>
class Colectie {
private:
	T v;
	NodC<T> * _prim;
	NodC<T> * _ultim;
	unsigned _size;

	NodC<T>* creeazaNod(const T & v) {
		NodC<T> *p = new NodC<T>(v);
		return p;
	}
	friend class NodC<T>;
	friend class IteratorC<T>;
	class DictionarOrdonat;

public:
	Colectie() noexcept {
		_size = 0;
		_prim = nullptr;
		_ultim = nullptr;
	}
	//adaugarea in colectie;
	void adauga(const T& v) {
		NodC<T> *p = creeazaNod(v);
		_size += 1;
		//colectia e vida
		if (this->_prim == nullptr) {
			this->_prim = p;
			this->_ultim = p;
		}
		//nodul trebuie adaugat la sfarsit
		else{
			this->_ultim->urm = p;
			p->prec = this->_ultim;
			this->_ultim = p;
		}
	}
	//dimensiunea colectiei
	const unsigned& dim() const {
		return this->_size;
	}
	//iterator pe colectie
	IteratorC<T> iteratorC() {
		return IteratorC<T> (*this);
	}
	//destructor colectie
	~Colectie() {
		while (this->_prim != nullptr) {
			NodC<T>* to_delete = this->_prim;
			(*this->_prim).prec = nullptr;
			this->_prim = (*this->_prim).urm;
			delete to_delete;
		}
	}
};

/***********************
* ITERATOR PE COLECTIE *
************************/
template<typename T>
class IteratorC {
private:
	const Colectie<T>& c;
	NodC<T>* curent;
public:
	friend class Colectie<T>;
	friend class NodC<T>;
	//Constructor colectie. 
	//Se refera primul element din dictionar
	IteratorC(const Colectie<T>& c) :c{ c } {
		curent = c._prim;
	}
	//refera urmatorul element dupa cel curent
	void urmator() {
		curent = curent->urm;
	}
	//precizeaza(true/false) daca pozitia curenta este o pozitie valida
	bool valid() const {
		return curent != nullptr;
	}
	//returneaza elementul de pe pozitia curenta
	T element() const {
		return curent->e;
	}
};