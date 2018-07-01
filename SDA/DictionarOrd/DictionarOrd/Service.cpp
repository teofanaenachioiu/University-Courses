#include "Service.h"
#include <assert.h>
#include <iostream>
ostream& operator<<(ostream& out, const CameraException& ex) {
	out << ex.msg;
	return out;
}
/*
Validarea datelor de intrare
Numele camerei trebuie sa fie nevid
Pretul camerei trebuie sa fie numar intreg strict pozitiv
Numarul camerei trebuie sa fie numar intreg strict pozitiv
*/
void Validator::validate(const Camera & cam)
{
	string errors = "";
	if (cam.getNrCamera() <= 0) errors += "Dati un numar natural nenul pentru camera! ";
	if (cam.getPret() <= 0) errors += "Dati un numar natural nenul pentru pret chirie! ";
	if (cam.getNume() == "") errors += "Nume vid! ";
	if (errors.size() > 0)
		throw CameraException(errors);
}
/*
Se inchiriaza o camera
*/
void Service::inchiriere(Camera & cam)
{
	val.validate(cam);
	repo.store(cam);
}
/*
Se face plata chiriei.
Pretul camerei devine 0
*/
void Service::faPlata(int nrCam)
{
	Camera fake = Camera{ nrCam,"fara",1 };
	val.validate(fake);
	Camera cam = repo.findd(fake);
	cam.setPret(0);
	repo.update(cam);
}
/*
Se adauga plata pe o luna per camera
Se actualizeaza pretul cu un nr citit de la tastatura
*/
void Service::adaugaPlata(int nrCam, int pret)
{
	Camera fake = Camera{ nrCam,"fara",1 };
	val.validate(fake);
	Camera cam = repo.findd(fake);
	cam.setPret(pret);
	repo.update(cam);
}
/*
Camera se elibereaza
Se sterge camera din repo
*/
void Service::eliberare(int nr)
{
	Camera fake = Camera{ nr,"fara",1 };
	val.validate(fake);
	Camera cam = repo.findd(fake);
	repo.deletee(cam);
}
/*
Returneaza toate camerele ce au pretul >0
*/
void Service::showPlataNeefectuata(DictionarOrdonat & dictionar) const
{
	Iterator iterator = repo.getAll().iterator();
	iterator.prim();
	while (iterator.valid()) {
		TElement pereche = iterator.element();
		if (pereche.getValoare().getPret() > 0) {
			dictionar.adauga(pereche.getCheie(), pereche.getValoare());
		}
		iterator.urmator();
	}
}
/*
Returneaza toate camerele ce au pretul =0
*/
void Service::showPlataLaZi(DictionarOrdonat & dictionar) const
{

	Iterator iterator = this->repo.getAll().iterator();
	iterator.prim();
	while (iterator.valid()) {
		TElement pereche = iterator.element();
		if (pereche.getValoare().getPret() == 0) {
			dictionar.adauga(pereche.getCheie(), pereche.getValoare());
		}
		iterator.urmator();
	}
}

/*
Returneaza toate camerele inchiriate
*/
const DictionarOrdonat & Service::showCamere() const
{
	return this->repo.getAll();
}

void testeService()
{
	Repository repo("test.txt", "testOut.txt");
	Validator val;
	Service serv = { repo,val };
	Camera c = { 19,"Ionut",260 };
	DictionarOrdonat dictionarZi{ fun }, dictionarNeef{ fun }, dictionarNeef1{ fun };

	serv.inchiriere(c);
	assert(serv.showCamere().dim() == 7);

	serv.faPlata(8);
	serv.showPlataLaZi(dictionarZi);
	assert(dictionarZi.dim() == 1);

	serv.showPlataNeefectuata(dictionarNeef);
	assert(dictionarNeef.dim() == 6);

	serv.adaugaPlata(8, 300);
	serv.showPlataNeefectuata(dictionarNeef1);
	assert(dictionarNeef1.dim() == 7);

	serv.eliberare(19);
	assert(serv.showCamere().dim() == 6);
}
