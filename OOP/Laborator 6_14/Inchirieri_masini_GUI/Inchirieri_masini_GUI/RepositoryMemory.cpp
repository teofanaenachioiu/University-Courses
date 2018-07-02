#include "RepositoryMemory.h"

void RepositoryMemory::loadData() {

}
void RepositoryMemory::saveData() {

}

void testRepoMemory()
{
	std::stringstream ss;
	RepositoryMemory repo;
	Masina m1 = { "SV10XJX","Mercedes","Viano","Van" }, m2 = { "SV17SBG","Toyota","Camry","Berlina" }, m3 = { "SV11TEO","Nisan","Qashqai","Suv" };
	Masina m = { "SV11XXX","Dacia","Logan","Berlina" };

	//teste pentru store

	repo.store(m1);
	repo.store(m2);
	repo.store(m3);
	try { repo.store(m3); assert(false); }
	catch (MasinaRepoException&) { assert(true); }

	//teste pentru find
	repo.find(m1);
	try { repo.find(m); assert(false); }
	catch (MasinaRepoException&) { assert(true); }

	//test getAll
	assert(repo.getAll().size() == 3);

	//teste pentru update
	try { repo.update(m); assert(false); }
	catch (MasinaRepoException& ex) {
		ss << ex;
		string str;
		str = ss.str();
		assert(str == "Numar de inmatriculare inexistent!!!");
		assert(true);
	}
	m1 = { "SV10XJX","Mercedes","Vito","Van" }, m2 = { "SV17SBG","Toyota","Camry","Sedan" }, m3 = { "SV11TEO","Nissan","Qashqai","Suv" };

	repo.update(m1);
	repo.update(m2);
	repo.update(m3);

	//teste pentru delete
	try { repo.deletee(m); assert(false); }
	catch (MasinaRepoException&) { assert(true); }
	repo.deletee(m1);
	//test pentru getAll
	assert(repo.getAll().size() == 2);
}


