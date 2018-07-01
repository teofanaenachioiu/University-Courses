#pragma once
#include <string>
using namespace std;

class Camera {
private:
	string nume_chirias;
	int pret_chirie,nr_cam;
public:
	//constructori CAMERA
	Camera() {
		nume_chirias = "fara";
		pret_chirie = 0;
		nr_cam = 0;
	}
	Camera(const int& nr, const string &numeC, const int& pret) :nr_cam{nr}, nume_chirias{ numeC }, pret_chirie{ pret } {}
	Camera(const Camera& ot) :nr_cam{ot.nr_cam},nume_chirias { ot.nume_chirias }, pret_chirie{ ot.pret_chirie } {}

	//gettere pentru parametri
	const string& getNume() const {
		return this->nume_chirias;
	}
	const int& getPret() const {
		return this->pret_chirie;
	}
	const int& getNrCamera() const {
		return this->nr_cam;
	}

	//settere pentru parametri
	const string& setNume(const string& numeNou) {
		this->nume_chirias = numeNou;
		return this->nume_chirias;
	}
	const int& setPret(const int& pretNou) {
		this->pret_chirie = pretNou;
		return this->pret_chirie;
	}

	//suprascriere operatori
	friend ostream& operator<<(ostream &os, const Camera& c);
	friend bool operator==(const Camera& l, const Camera& r);

	~Camera() {};
};

void testeDomain();