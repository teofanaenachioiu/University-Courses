#include <fstream>
#include <iostream>
#include <vector>
#include<queue>


using namespace std;
ifstream f("graf.in");
class Muchie {
public:
	int start;
	int stop;
	int capacity;
	int flow = 0;
	Muchie(int st, int stop, int c) :start{ st }, stop{ stop }, capacity{ c } { };

};

class Node {
public:
	int name;
	int e;
	int h;
	vector<Muchie> v;
	Node(int name) :name{ name } {
		e = 0;
		h = 0;
	};

};
//Functie pe care o folosesc sa recalculez capacitatea unei muchii din graful rezidual
void updateM(vector<Node>&gf, Muchie& m) {

	//muchia auxiliara(este egala cu muchia primita ca parametru doar ca in sens invers
	auto maux = Muchie(m.stop, m.start, m.flow);
	maux.flow = 0;

	//verific daca muchia exista deja
	for (auto& el : gf[m.stop].v) {
		if (maux.stop == el.stop) {
			//daca exista adaug la capacitate capacitatea muchiei maux si ies
			el.capacity += maux.capacity;
			return;
		}
	}
	//daca nu gasesc muchia auxiliara deloc o adaug in Graful rezidual
	gf[m.stop].v.push_back(maux);

}

void citire(vector<Node>& gf) {
	int n;
	f >> n;
	for (int i = 0; i < n; gf.push_back(i), i++);
	int x, y, c;
	while (f >> x >> y >> c) {
		gf[x].v.push_back(Muchie(x, y, c));
	}
}

//sterg o muchie pe care o primesc ca parametru
void deleteM(vector<Node>& gf, Muchie& m) {
	for (int i = 0; i < gf[m.start].v.size(); i++) {
		if (gf[m.start].v[i].stop == m.stop)
			gf[m.start].v.erase(gf[m.start].v.begin() + i);
	}
}

//parcurg fiecare nod al grafului si incerc sa pompez
bool pompeaza(vector<Node>& gf, int s, int t) {

	//pt fiecare nod
	for (auto& nod : gf) {
		if (nod.e > 0) {
	
			//cauta fiecare muchie al nodurilor supraincarcate
			for (auto& m : nod.v) {

				//daca inaltimea unui nod adiacent este hCurent-1 pompam cat permite muchia si updatam excesul/Graful rezidual
				if (gf[m.stop].h == gf[m.start].h - 1)
				{
					//updatez excesul/flow
					m.flow = min(m.capacity - m.flow, gf[m.start].e);
					gf[m.start].e -= m.flow;
					gf[m.stop].e += m.flow;

					//updatez graful rezidual
					updateM(gf, m);

					//sterg muchia daca e cazul
					if (m.flow == m.capacity)
						deleteM(gf, m);

					//daca am reusit sa pompez ceva returnez true
					return true;
				}
			}
		}
	}
	//daca nu pot pompa returnez false
	return false;
}
//parcurg fiecare nod verific daca pot inalta
bool inalta(vector<Node>& gf, int s, int t) {
	//pt fiecare nod
	for (auto& nod : gf) {

		//Care are exces si nu e destinatie
		if (nod.e > 0 && nod.name != t) {
			auto ok = 1;

			//verific daca este cel mai inalt nod cu exces
			for (auto& m : nod.v)
				if (gf[m.stop].e > 0 && gf[m.stop].h > gf[m.start].h)
				{
					ok = 0; break;
				}
			//daca da il mai inalt
			if (ok == 1) {
				nod.h++;

				//returnez true daca am inaltat
				return true;
			}
		}
	}
	//atlfel false
	return false;
}
//mut sursa la inaltimea |V|, dau flux maxim prin toate muchiile adiacente Sursei, si updatez Graful Rezidual
void initializarePF(vector<Node>& gf, int s, int t) {

	//pt fiecare muchie care pleaca din sursa
	for (auto& m : gf[s].v) {

		//pompam capacity-ul fiecarei muchii
		m.flow = m.capacity;
		gf[s].e -= m.capacity;
		gf[m.stop].e = m.flow;

		//updatez graful rezidual si sterg muchiile de la sursa spre nodurile adiacente pt ca si-au atins capacitatea
		updateM(gf, m);
		if (m.flow == m.capacity)
			deleteM(gf, m);

	}
	//initializez inaltimea sursei
	gf[s].h = gf.size();
}
int pomparePreflux(vector<Node>& gf, int s, int t) {


	initializarePF(gf, s, t);

	//cat timp pot sa fac pompare/inaltare o execut
	while (pompeaza(gf, s, t) || inalta(gf, s, t));


	return gf[t].e;
}


int main() {
	vector<Node> gf;
	citire(gf);
	cout << pomparePreflux(gf, 0, 5);




	return 0;
}
