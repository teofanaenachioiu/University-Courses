#pragma once
#include "Masina.h"
#include <vector>
#include <assert.h>
#include <sstream>
#include <algorithm>
#include <iterator>
#include <iostream>

using namespace std;

class Repository
{
protected:
	vector <Masina> repo;
public:
	Repository() = default;
	Repository(const Repository& ot) = delete;//nu permitem copierea de obiecte
	void store(const Masina& m);
	void deletee(const Masina &m);
	void update(const Masina& m);
	const Masina find(const Masina& m) const;
	bool exist(const Masina& m);
	const vector<Masina>& getAll() const noexcept;
	virtual void loadData() = 0;
	virtual void saveData() = 0;
	~Repository() = default;
};

class MasinaRepoException {
	string msg;
public:
	MasinaRepoException(string m) :msg{ m } {}
	friend ostream& operator<<(ostream& out, const MasinaRepoException& ex);
	string getMsg() {
		return msg;
	}
};

ostream& operator<<(ostream& out, const MasinaRepoException& ex);
