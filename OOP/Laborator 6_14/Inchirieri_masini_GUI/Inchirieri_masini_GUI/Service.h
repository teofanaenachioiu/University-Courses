#pragma once
#include "Repository.h"
#include "Validator.h"
#include "Observer.h"
#include "Undo.h"

using namespace std;
class Obesevabileee {
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
class Service:public Obesevabileee
{
	Repository& repo;
	MasinaValidator& val;
	vector<unique_ptr<ActiuneUndo>> undoActions;
public:
	Service(Repository& r, MasinaValidator& v) noexcept : repo{ r }, val{ v } {};
	Service(const Service & ot) = delete;

	void adauga(const Masina& m);
	void sterge(const string& nr);
	void actualizareTot(const string &nr, const string& prod, const string& model, const string& tip);
	void actualizare(const string &nr, const string& caracteristica, const int &criteriu);
	Masina cauta(const string& nr) const;
	const vector <Masina>& listaMasini() const;
	vector <Masina> filtrare(const string& caracteristica, const int &criteriu) const;
	vector <Masina> sortare(const int& optiune, const int& crtSuplim) const;
	void undo();
};

void testService();


