#pragma once
#include "Repository.h"
#include"Observer.h"
#include <random>
#include <chrono>
#include <fstream>
#include <Windows.h>
#include <shellapi.h>
class Obesevabileeee {
public:
	vector<Observer*> obsss;
	void notify(Observer* obs) {
		obs->update();
	}
	void notifyObsss() {
		for (auto o : obsss)
			notify(o);
	}
	void addObs(Observer *obs) {
		obsss.push_back(obs);
	}
};
using namespace std;
class Cos: public Obesevabileeee {
protected:
	vector <Masina> listaLucru;
	Repository& repo;

public:
	Cos(Repository &r) :repo{ r } {};
	void adaugaLista(const string &nr);
	void golireLista() noexcept;
	void adaugaRandomLista(const unsigned& nr);
	const int dimLista() const noexcept;
	const vector <Masina>& getListaLucru()const noexcept;
	const Masina& cautaLista(const Masina& m);
	virtual void scrie_in_fisier() = 0;
	virtual void deschide_fisier() = 0;
	~Cos() = default;
};

class ListaLucruException {
	string mesaj;
public:
	ListaLucruException(string m) : mesaj{ m } {}
	friend ostream& operator<<(ostream& out, const ListaLucruException& ex);
};

ostream& operator<<(ostream& out, const ListaLucruException& ex);
