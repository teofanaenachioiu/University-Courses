#include "RepositoryFile.h"
#include <fstream>
using namespace std;
//ifstream in("input.txt");
//ofstream out("output.txt");

void RepositoryFile::loadData() {
	ifstream in(fisier_in);
	Masina m;
	string nrInmatr, producator, model, tip, line;
	if (!in.is_open())throw MasinaRepoException("! Fisierul nu a putut fi deschis pentru citire!");

	while (in >> nrInmatr >> producator >> model >> tip) {
		m = Masina(nrInmatr, producator, model, tip);
		repo.push_back(m);
	}
	in.close();
}
void RepositoryFile::saveData() {
	ofstream out(fisier_in);
	for (auto e : repo)
	{
		out << e.getNrInmatr() << " " << e.getProducator() << " " << e.getModel() << " " << e.getTip() << endl;
	}
	out.close();
}

void testRepoFile()
{
	RepositoryFile repo("test_in.txt", "test_out.txt");
	std::stringstream ss;
	Masina m1 = { "SV10XJX","Mercedes","Viano","Van" }, m2 = { "SV10BLA","BMW","Camry","SUV" }, m3 = { "SV05ANA","Skoda","Octavia","Sedan" };

	//teste pentru store
	assert(repo.getAll().size() == 5);
	repo.store(m1);
	try { repo.store(m2); assert(false); }
	catch (MasinaRepoException&) { assert(true); }

	//teste pentru find
	repo.find(m1);
	try { repo.find(m3); assert(false); }
	catch (MasinaRepoException&) { assert(true); }

	//test getAll
	assert(repo.getAll().size() == 6);

	//teste pentru update
	try { repo.update(m3); assert(false); }
	catch (MasinaRepoException& ex) {
		ss << ex;
		string str;
		str = ss.str();
		assert(str == "Numar de inmatriculare inexistent!!!");
		assert(true);
	}
	m1 = { "SV10XJX","Mercedes","Vito","Van" }, m2 = { "SV17SBG","Toyota","Camry","Sedan" };
	Masina m4 = { "SV11TEO","Nissan","Qashqai","Suv" };

	repo.update(m1);
	repo.update(m2);
	repo.update(m4);

	//teste pentru delete
	try { repo.deletee(m3); assert(false); }
	catch (MasinaRepoException&) { assert(true); }
	repo.deletee(m1);
	assert(repo.getAll().size() == 5);
}
