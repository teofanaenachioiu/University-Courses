#include"Repository.h"
#include<assert.h>

ostream& operator<<(ostream &os, const RepoException &ex) {
	os << ex.msg;
	return os;
}
/*
Adauga un element de tip Camera in repo
*/
void Repository::store(Camera & cam)
{
	int nrCam = cam.getNrCamera();
	Camera c = cam;
	if (repo.cauta(nrCam, c) == false)
		repo.adauga(cam.getNrCamera(), cam);
	else throw RepoException("Camera ocupata");
}
/*
Sterge o camera din repo
*/
void Repository::deletee(Camera & cam)
{
	int nrCam = cam.getNrCamera();
	Camera c = cam;
	if (repo.cauta(nrCam, c) == true)
		repo.sterge(cam.getNrCamera(), cam);
	else throw RepoException("Camera nu exista");
}
/*
Actualizeaza datele despre o camera
*/
void Repository::update(Camera & cam)
{
	int nrCam = cam.getNrCamera();
	Camera c = cam;
	if (repo.cauta(nrCam, c) == true) {
		repo.sterge(cam.getNrCamera(), cam);
		repo.adauga(cam.getNrCamera(), cam);
	}
	else throw RepoException("Camera nu exista");
}
/*
Cauta un element de tip camera in repo
*/
Camera Repository::findd(Camera & cam)
{
	int nrCam = cam.getNrCamera();
	Camera c = cam;
	bool gasit = repo.cauta(nrCam, c);
	if (gasit == true) {
		if (cam.getNume() == "fara") cam = c;
		return cam;
	}
	else  throw RepoException("Camera nu exista");
}
/*
Returneaza toate elementele din repo
*/
const DictionarOrdonat & Repository::getAll() const
{
	return this->repo;
}
/*
Citirea datelor din fisier
*/
void Repository::loadData()
{
	ifstream f(fisierIn);
	int nr, pret;
	string nume;
	if (!f.is_open())
		throw RepoException("Fisierul nu s-a deschis\n");
	while (!f.eof()) {
		f >> nr >> nume >> pret;
		Camera cam = { nr, nume, pret };
		repo.adauga(nr, cam);
	}
	f.close();
}
/*
Salvarea datelor in fisier
*/
void Repository::saveData()
{
	ofstream g(fisierOut);
	Iterator iterator = repo.iterator();
	iterator.prim();
	while (iterator.valid()) {
		TElement pereche = iterator.element();
		g << pereche.getCheie() << " " << pereche.getValoare().getNume() << " " << pereche.getValoare().getPret() << std::endl;
		iterator.urmator();
	}
}

void testeRepo()
{
	Repository repo = { "test.txt","testOut.txt" };
	assert(repo.getAll().dim() == 6);
	Camera c = { 10,"Paula",500 };
	Camera c1 = { 10,"Paula",250 };
	try {
		repo.findd(c);
		assert(false);
	}
	catch (RepoException &) {
		assert(true);
	}
	try {
		repo.update(c1);
		assert(false);
	}
	catch (RepoException &) {
		assert(true);
	}
	try {
		repo.deletee(c1);
		assert(false);
	}
	catch (RepoException &) {
		assert(true);
	}
	repo.store(c);
	try {
		repo.store(c);
		assert(false);
	}
	catch (RepoException&) { assert(true); }
	assert(repo.getAll().dim() == 7);
	repo.findd(c);
	repo.update(c1);
	assert(repo.getAll().dim() == 7);
	repo.deletee(c1);
	assert(repo.getAll().dim() == 6);
}