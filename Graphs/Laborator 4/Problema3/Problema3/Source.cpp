#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <algorithm>
using namespace std;
ifstream f;
#define MAX 32600;

typedef struct {
	char extr1, extr2;
	int w;
}Nod;

typedef struct {
	int key;
	char parinte,varf;
}Nod_prim;

void citire(vector<Nod>& muchie, vector <char>& varf, int& n) {
	Nod nod;
	f.open("input.txt");
	f >> n;
	while (!f.eof()) {
		f >> nod.extr1>>nod.extr2>>nod.w;
		muchie.push_back(nod);
		auto it1 = find_if(varf.begin(), varf.end(), [nod](char & c) { return nod.extr1 == c; });
		if (it1 == varf.end()) varf.push_back(nod.extr1);
		auto it2 = find_if(varf.begin(), varf.end(), [nod](char & c) { return nod.extr2 == c; });
		if (it2 == varf.end()) varf.push_back(nod.extr2);
	}
	f.close();
}

void make_set(vector<pair<int, char>>& q, char &c) {
	q.push_back(make_pair(q.size()+1,c));
}

vector <Nod> Kruskal(vector<Nod>& muchie, vector<char>& varf) {
	vector <Nod> A;
	vector<pair<int, char>> q;
	for (auto c : varf) {
		make_set(q,c); //varfurile formeaza cate un graf
	}
	sort(muchie.begin(), muchie.end(), [](Nod &m1, Nod &m2) {return m1.w < m2.w; }); //sortare muchii crescator dupa pondere
	for (auto m : muchie) {
		auto it1 = find_if(q.begin(), q.end(), [&m](pair<int, char>& c) {return m.extr1 == c.second; });
		auto it2 = find_if(q.begin(), q.end(), [&m](pair<int, char>& c) {return m.extr2 == c.second; });
		if ((*it1).first != (*it2).first) {
			A.push_back(m);
			*it2 = make_pair((*it1).first, (*it2).second);
		}
	}
	return A;
}

Nod_prim extract_min(vector<Nod_prim>& varf_prim) {
	Nod_prim min = varf_prim.at(0);
	int poz = 0;
	for (int i=1;i<varf_prim.size();i++)
		if (varf_prim.at(i).key < min.key) {
			min = varf_prim.at(i);
			poz = i;
		}
	varf_prim.erase(varf_prim.begin()+poz);
	return min;
}

void initVect(vector<Nod_prim> &vect,vector<char>& varf, char & start) {
	Nod_prim nod;
	for (auto u : varf) {
		nod.varf = u;
		nod.parinte = NULL;
		if (u != start) { nod.key = MAX; }
		else { nod.key = 0; }
		vect.push_back(nod);
	}
}

vector<Nod_prim> Prim(vector<Nod>& muchie, vector<char>& varf, char& start) {
	vector<Nod_prim> vect,Q;
	Nod_prim u;
	char v;
	initVect(vect, varf, start); //initializam vectorul
	Q = vect;
	while (!Q.empty()) {
		u = extract_min(Q);
		for (auto m : muchie) {
			if (m.extr1 == u.varf || m.extr2 == u.varf) {
				if (m.extr1 == u.varf) v = m.extr2;
				if (m.extr2 == u.varf) v = m.extr1;
				auto it = find_if(Q.begin(), Q.end(), [&v](Nod_prim & nod) {return v == nod.varf; });
				if (it != Q.end() && m.w < (*it).key) {
					auto itv = find_if(vect.begin(), vect.end(), [&v](Nod_prim & nod) {return v == nod.varf; });
					(*itv).key = m.w;
					(*itv).parinte = u.varf;
					(*it).key = m.w;
					(*it).parinte = u.varf;
				}
			}
		}
	}
	return vect;
}

void afisare(vector<Nod> K) {
	for (auto el : K)
		cout << "(" << el.extr1 << "," << el.extr2 << ")" << endl;
}

void afisarePrim(vector<Nod_prim> P) {
	for (auto el : P)
		if(el.key!=0)
			cout << "(" << el.parinte << "," << el.varf << ")" << endl;
}

int main() {
	vector<Nod> muchie, K;
	vector<Nod_prim>P;
	vector<char> varf;
	int n;
	char start='A';
	citire(muchie,varf, n);
	cout << "Kruskal:" << endl;
	K=Kruskal(muchie, varf);
	afisare(K);
	cout << "Prim:" << endl;
	//cout << "Start: "; cin >> start;
	P = Prim(muchie, varf, start);
	afisarePrim(P);
	return 0;
}