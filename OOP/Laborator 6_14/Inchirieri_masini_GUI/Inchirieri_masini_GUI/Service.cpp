#include "Service.h"
#include "RepositoryMemory.h"
#include <memory>
#include <random>
#include <chrono>
using namespace std;

//adauga o masina in service
void Service::adauga(const Masina & m)
{
	val.validate(m);
	repo.store(m);
	undoActions.push_back(make_unique<UndoAdauga>(repo, m));
}

//sterge o masina din service
void Service::sterge(const string & nr)
{
	Masina m, fake = { nr,"fara","fara","fara" };
	val.validate(fake);

	m = repo.find(fake);

	repo.deletee(m);
	notifyObsss();
	undoActions.push_back(make_unique<UndoSterge>(repo, m));
}

void Service::actualizareTot(const string & nr, const string & prod, const string & model, const string & tip)
{
	Masina mVechi,m= { nr,prod,model,tip };
	val.validate(m);
	mVechi = repo.find(m);
	undoActions.push_back(make_unique<UndoActualizare>(repo, mVechi));
	
	m.setProducator(prod);
	m.setModel(model);
	m.setTip(tip); 

	repo.update(m);	
	notifyObsss();
}

//actualizeaza o masina in service
void Service::actualizare(const string &nr, const string & caracteristica, const int & criteriu)
{
	Masina m, fake = { nr,caracteristica,caracteristica,caracteristica };
	val.validate(fake);

	m = repo.find(fake);
	undoActions.push_back(make_unique<UndoActualizare>(repo, m));
	switch (criteriu) {
	case 1: {m.setProducator(caracteristica); break; }
	case 2: {m.setModel(caracteristica); break; }
	case 3: {m.setTip(caracteristica); break; }
	}
	repo.update(m);
	notifyObsss();
}

void Service::undo() {
	if (undoActions.empty()) {
		throw MasinaRepoException{ "Nu mai exista operatii" };
	}
	undoActions.back()->doUndo();
	undoActions.pop_back();
	notifyObsss();
}

//cauta o masina in service (dupa numarul de inmatriculare)
Masina Service::cauta(const string & nr) const
{
	Masina fake = { nr,"fara","fara","fara" };
	val.validate(fake);
	return repo.find(fake);
}
//ia toata lista de masini 
const vector<Masina>& Service::listaMasini() const
{
	//cout << this->repo.getAll().size()<<endl;
	return repo.getAll();
}

//filtrare masini dupa un anumit criteriu
vector<Masina> Service::filtrare(const string& caracteristica, const int &criteriu) const
{
	Masina fake = { "XX00XXX",caracteristica,caracteristica,caracteristica };
	val.validate(fake);
	vector <Masina> lista;
	if (criteriu == 1)
		copy_if(repo.getAll().begin(), repo.getAll().end(), back_inserter(lista), [&](const Masina& m) {return m.getProducator() == caracteristica; });
	if (criteriu == 2)
		copy_if(repo.getAll().begin(), repo.getAll().end(), back_inserter(lista), [&](const Masina& m) {return m.getTip() == caracteristica; });
	return lista;
}
//sortare masini in functie de o anumita carcateristica
vector <Masina> Service::sortare(const int& optiune, const int& crtSuplim) const {
	vector <Masina> lista = repo.getAll();
	if (optiune == 1 && crtSuplim == 0) {
		sort(lista.begin(), lista.end(), [](const Masina &m1, const Masina &m2) {return m1.getNrInmatr() < m2.getNrInmatr(); });
	}
	if (optiune == 2 && crtSuplim == 0) {
		sort(lista.begin(), lista.end(), [](const Masina &m1, const Masina &m2) {return m1.getTip() < m2.getTip(); });
	}
	if (optiune == 3 && crtSuplim == 4) {
		sort(lista.begin(), lista.end(), [](const Masina &m1, const Masina &m2) {return m1.getProducator() < m2.getProducator() || m1.getProducator() == m2.getProducator() && m1.getModel() < m2.getModel(); });
	}
	return lista;
}

//teste pentru service
void testService()
{
	stringstream ss;
	vector <Masina> lista;
	RepositoryMemory repo;
	MasinaValidator val;
	Service serv = { repo,val };
	Masina m1 = { "SV10XJX","Mercedes","Viano","Van" }, m2 = { "SV17SBG","Toyota","Camry","Sedan" }, m3 = { "SV11TEO","Nisan","Qashqai","Suv" };
	Masina m4 = { "SV10X","Mercedes","Viano","Van" };

	serv.adauga(m1);
	serv.adauga(m2);
	serv.adauga(m3);
	assert(serv.listaMasini().size() == 3);
	try { serv.adauga(m4); assert(false); }
	catch (ValidateException&) {
		assert(true);
	}

	//sortare dupa numarul de inmatriculare
	lista = serv.sortare(1, 0);
	assert(lista[0].getNrInmatr() == m1.getNrInmatr());
	assert(lista[1].getNrInmatr() == m3.getNrInmatr());

	//sortare dupa tip
	lista = serv.sortare(2, 0);
	assert(lista[0].getNrInmatr() == m2.getNrInmatr());

	//sortare dupa producator+model
	lista = serv.sortare(3, 4);
	assert(lista[0].getNrInmatr() == m1.getNrInmatr());
	assert(lista[1].getNrInmatr() == m3.getNrInmatr());

	//teste pentru actualizare
	serv.actualizare("SV11TEO", "Nissan", 1);
	serv.actualizare("SV11TEO", "Qashqai", 2);
	serv.actualizare("SV11TEO", "Suv", 3);
	try { serv.actualizare("SVV0TEO", "Suv", 3); }
	catch (ValidateException&) { assert(true); }
	assert(serv.listaMasini().size() == 3);
	serv.sterge("SV10XJX");
	try { serv.sterge("SVV0TEO"); }
	catch (ValidateException& ex) {
		ss << ex;
		string str;
		ss << ex;
		assert(true);
	}
	assert(serv.listaMasini().size() == 2);
	assert(serv.cauta("SV11TEO").getProducator() == "Nissan");
	assert(serv.cauta("SV11TEO").getTip() == "Suv");
	assert(serv.filtrare("Toyota", 1).size() == 1);
	assert(serv.filtrare("Suv", 2).size() == 1);
	assert(serv.listaMasini().size() == 2);
	serv.undo();
	assert(serv.listaMasini().size() == 3);
	serv.undo();
	assert(serv.listaMasini().size() == 3);

	Masina m10 = { "SV10XII","Mercedes","Viano","Van" };
	serv.adauga(m10);
	assert(serv.listaMasini().size() == 4);
	serv.undo();
	assert(serv.listaMasini().size() == 3);
}
