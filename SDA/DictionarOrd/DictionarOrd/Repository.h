#pragma once
#include "Dictionar.h"
#include <fstream>
using namespace std;
 
class RepoException {
private:
	string msg;
public:
	RepoException(const string& m) :msg{ m } {}
	string getMessage()const {
		return this->msg;
	}
	friend ostream& operator<<(ostream &os, const RepoException &ex);
};

ostream& operator<<(ostream &os, const RepoException &ex);

static bool fun(const TCheie& c1, const TCheie& c2) {
	return c1 < c2;
}

class Repository {
private:
	DictionarOrdonat repo{ fun };
	string fisierIn, fisierOut;
public:
	Repository(string fi, string fo) :fisierIn{ fi }, fisierOut{ fo } {
		loadData();
	}
	void store(Camera &cam);
	void deletee(Camera &cam);
	void update(Camera &cam);
	Camera findd(Camera &cam);
	void loadData();
	void saveData();
	/*const Multime<int>& getChei(Multime <int>& m) const {
		repo.chei(m);
		return repo.chei(m);
	}*/
	const DictionarOrdonat & getAll() const;
	~Repository() {
		saveData();
	}
};

void testeRepo();


