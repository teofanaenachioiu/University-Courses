#include "Cos.h"
#include "RepositoryFile.h"
using namespace std;

const Masina& Cos::cautaLista(const Masina& m) {
	auto it = find_if(listaLucru.begin(), listaLucru.end(), [&m](const Masina& el) {return el == m; });
	if (it == listaLucru.end())
		throw ListaLucruException("! Nu exista masina in firma.\n");
	return *it;
}
//adauga masina in lista de lucru
void Cos::adaugaLista(const string& nrInmatr) {
	Masina m_fake(nrInmatr, "fara", "fara", "fara");
	Masina m = repo.find(m_fake);
	
	try {
		cautaLista(m);
		
	}
	catch (ListaLucruException &) {
		listaLucru.push_back(m); 
	}
	notifyObsss();
}

//goleste lista de lucru
void Cos::golireLista() noexcept {
	listaLucru.clear();
	notifyObsss();
}

//adauga masini random in lista de lucru
void Cos::adaugaRandomLista(const unsigned& nr)
{
	if (repo.getAll().size() - dimLista() < nr) throw ListaLucruException("! Nu exista atatea masini in firma.\n");
	if (nr == 0) throw ListaLucruException("! Dati un numar strict pozitiv.\n");
	Masina m;
	vector<Masina> list = repo.getAll();
	unsigned i = 0;
	while (i<nr) {
		//auto seed = chrono::system_clock::now().time_since_epoch().count();
		//shuffle(list.begin(), list.end(), default_random_engine(seed));
		//m = Masina(list.at(0));
		m = Masina(list.at(rand()%list.size()));
		try {
			cautaLista(m); continue;
		}
		catch (ListaLucruException&)
		{
			listaLucru.push_back(m);
			i++;
		}
	}
	notifyObsss();
}

//getter dimensiune lista de lucru
const int Cos::dimLista() const noexcept {
	return listaLucru.size();

}

//getter lista de lucru
const vector<Masina>& Cos::getListaLucru() const noexcept
{
	return listaLucru;
}

//suprascriere operator <<
ostream& operator<<(ostream& out, const ListaLucruException& ex) {
	out << ex.mesaj;
	return out;
}

