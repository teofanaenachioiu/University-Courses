#pragma once
#include"Repository.h"

class CameraException {
private:
	string msg;
public:
	CameraException(const string& m) :msg{ m } {}
	friend ostream& operator<<(ostream& out, const CameraException& ex);
	const string& getMessage()const {
		return msg;
	}
};

class Validator {
public:
	void validate(const Camera& cam);
};

ostream& operator<<(ostream& out, const CameraException& ex);

class Service {
private:
	Repository & repo;
	Validator & val;
public:
	Service(Repository& r,Validator& v):repo{r},val{v}{}
	Service(const Service &ot) = delete;
	void inchiriere(Camera &cam);
	void faPlata(int nrCam);
	void adaugaPlata(int nrCam, int pret);
	void eliberare(int nr);
	void showPlataNeefectuata(DictionarOrdonat& dictionar)const;
	void showPlataLaZi(DictionarOrdonat& dictionar) const;
	const DictionarOrdonat& showCamere() const;
	/*const Multime<int>& Chei(Multime<int>&m) const {
		repo.getChei(m);
		return m;
	}*/
	~Service() {};
};

void testeService();