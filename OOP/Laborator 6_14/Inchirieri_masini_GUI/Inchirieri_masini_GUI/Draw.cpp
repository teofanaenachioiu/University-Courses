#include "Draw.h"

//desenare obiecte random de dimensiune random in functie de numarul de obiecte din cos
void ViewCos::update()
{
	rands r;
	auto s = cos.dimLista();
	std::vector<Masina> vect;
	vect=cos.getListaLucru();
	if (s > v.size())
		for (int i = v.size(); i < s; i++) {
			r.x = 1 + (int)(400.0* (rand() / (RAND_MAX + 1.0)));
			r.y = 1 + (int)(400.0 * (rand() / (RAND_MAX + 1.0)));
			r.z = 1 + (int)(400.0 * (rand() / (RAND_MAX + 1.0)));
			r.a = 1 + (int)(400.0 * (rand() / (RAND_MAX + 1.0)));
			r.b = 1 + (int)(9.0 * (rand() / (RAND_MAX + 1.0)));
			v.push_back(r);
		}
	else if (s == 0) {
		v.clear();
	}
	else if (v.size() > s && s != 0) {
		v.pop_back();
	}
	repaint();
}

void ViewCos::exec() {
	this->update();
	this->show();
}