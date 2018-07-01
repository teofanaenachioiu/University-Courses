//#pragma once
template<typename T>class IteratorM;
template<typename T>class Multime;

/*****************
* NOD IN MULTIME *
******************/
template <typename T>
class NodM {
private:
	T e;
	NodM * urm;
	NodM * prec;
public:
	NodM<T>(const T& e) :e{ e }, urm{ nullptr }, prec{ nullptr } {}
	friend class Multime<T>;
	friend class IteratorM<T>;
};

/**********
* MULTIME *
***********/
template<typename T>
class Multime {
private:
	T v;
	NodM<T> * _prim;
	NodM<T> * _ultim;
	unsigned _size;

	NodM<T>* creeazaNod(const T& c) {
		NodM<T> *p = new NodM<T>(c);
		return p;
	}
	friend class NodM<T>;
	friend class IteratorM<T>;
	class DictionarOrdonat;

public:
	Multime() noexcept {
		_size = 0;
		_prim = nullptr;
		_ultim = nullptr;
	}
	//cautare nod dupa cheie
	bool cauta(const T& c) {
		NodM<T> *p = this->_prim;
		while (p != nullptr && c != p->e)
			p = p->urm;
		if (p != nullptr) {
			return true;
		}
		return false;
	}
	//functia de adaugare
	void adauga(const T& c) {
		NodM<T> *p = creeazaNod(c);
		
		//colectia e vida
		if (cauta(c) == false) {
			_size += 1;
			if (this->_prim == nullptr) {
				this->_prim = p;
				this->_ultim = p;
			}
			//nodul trebuie adaugat la sfarsit
			else {
				this->_ultim->urm = p;
				p->prec = this->_ultim;
				this->_ultim = p;
			}
		}
	}
	//dimensiune multime
	const unsigned& dim() const {
		return this->_size;
	}
	//iterator pe multime
	IteratorM<T> iteratorM() {
		return IteratorM<T>(*this);
	}
	//destructor multime
	~Multime() {
		while (this->_prim != nullptr) {
			NodM<T>* to_delete = this->_prim;
			(*this->_prim).prec = nullptr;
			this->_prim = (*this->_prim).urm;
			delete to_delete;
		}
	}
};

/**********************
* ITERATOR PE MULTIME *
***********************/
template<typename T>
class IteratorM {
private:
	const Multime<T>& m;
	NodM<T>* curent;
public:
	friend class Multime<T>;
	friend class NodM<T>;
	IteratorM(const Multime<T>& m) :m{ m } {
		curent = m._prim;
	}
	void urmator() {
		curent = curent->urm;
	}
	bool valid() const {
		return curent != nullptr;
	}
	T element() const {
		return curent->e;
	}
};