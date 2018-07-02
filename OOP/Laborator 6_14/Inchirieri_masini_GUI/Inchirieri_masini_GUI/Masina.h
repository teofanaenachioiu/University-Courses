#pragma once
#include <string>
using namespace std;

class Masina {
	std::string nrInmatr, producator, model, tip;
public:
	Masina() = default;
	Masina(const string nr, const string p, const string m, const string t) :nrInmatr{ nr }, producator{ p }, model{ m }, tip{ t } {};
	Masina(const Masina& ot) = default;//: nrInmatr{ ot.getNrInmatr() }, producator{ ot.getProducator() }, model{ ot.getModel() }, tip{ ot.getModel() } {};;
	string getNrInmatr() const {
		return nrInmatr;
	}
	string getProducator() const {
		return producator;
	}
	string getModel() const {
		return model;
	}
	string getTip() const {
		return tip;
	}
	string setProducator(string p) {
		this->producator = p;
		return this->producator;
	}
	string setModel(string m) {
		this->model = m;
		return this->model;
	}
	string setTip(string t) {
		this->tip = t;
		return this->tip;
	}
};

bool comparaNrInmatr(const Masina& m1, const Masina& m2);

bool comparaProducator(const Masina& m1, const Masina& m2);

bool comparaModel(const Masina& m1, const Masina& m2);

bool comparaTip(const Masina& m1, const Masina& m2);

inline bool operator==(const Masina& lhs, const Masina& rhs) { return comparaNrInmatr(lhs, rhs); }

void testMasina();