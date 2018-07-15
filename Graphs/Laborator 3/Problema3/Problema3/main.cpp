#include <iostream>
#include <fstream>
#include <utility>
#include <vector>
#include <algorithm>
using namespace std;
ifstream file;

//Initializare matricea costurilor
void initCost(int w[30][30], int n) {
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			w[i][j] = INT_MAX;
		}
	}
}

//Citirea din fisier, extremitatile muchiei + ponderea
void citire(int &n, pair <int, int> muchie[30], int w[30][30], int &m) {
	file >> n;
	initCost(w, n);
	int i = 0;
	while (!file.eof()) {
		file >> muchie[i].first >> muchie[i].second;
		file >> w[muchie[i].first - 1][muchie[i].second - 1];
		//w[muchie[i].second - 1][muchie[i].first - 1] = w[muchie[i].first - 1][muchie[i].second - 1];
		i++;
	}
	m = i;
}

//initializare informatii despre varf: distanta de la start la varf + parintele varfului 
void initializare_info(int n, int start, pair <int, int> info[30]) {
	for (int i = 0; i < n; i++) {
		info[i].first = INT_MAX;
		info[i].second = NULL;
	}
	info[start - 1].first = 0;
}

//functia actualizeaza drumul minim dintre doua varfuri
void relax(int parinte, pair <int, int> &u_pereche, pair <int, int> & v_pereche, int  cost) {
	if (v_pereche.first > u_pereche.first + cost) {
		v_pereche.first = u_pereche.first + cost;
		v_pereche.second = parinte;
	}
}

//algoritmul determina drumul minim de la un varf start la celelalte varfuri
bool Bellman_Ford(int n,int m, pair <int, int> muchie[30], int start, int w[30][30], pair <int, int> info[30]) {
	int i, j, cost;
	initializare_info(n, start, info);
	for ( i = 1; i <= n - 1; i++) {
		for ( j = 0; j < m; j++) {
			cost = w[muchie[j].first - 1][muchie[j].second - 1];
			relax(muchie[j].first, info[muchie[j].first - 1], info[muchie[j].second - 1], cost);
		}
	}

	for (j = 0; j < m; j++) {
		cost = w[muchie[j].first - 1][muchie[j].second - 1];
		if (info[muchie[j].first - 1].first > info[muchie[j].second - 1].first +cost)
			return false;
		else return true;
	}
}

//afisare drum minim
void afisareDrum(int n, int start, pair <int, int> info[30]) {
	for (int i = 0; i < n; i++) {
		cout << "Drumul minim " << start << "-" << i + 1 << ": ";
		if (info[i].first != INT_MAX)
			cout << info[i].first << endl;
		else cout << "nedeterminat" << endl;
	}
}

int main() {
	pair <int, int> info[30], muchie[30];
	int n, w[30][30], m, start, cmd;
	file.open("input.txt");
	citire(n, muchie, w, m);
	while (1) {
		cout << "1. Aplicati algoritmul Bellman-Ford" << endl;
		cout << "0. Exit" << endl;
		cout << "Comanda: ";
		cin >> cmd;
		if (cmd == 1) {
			cout << "Dati un varf de start: "; cin >> start;
			if (start<1 || start >n)
				cout << "Nu exista acest nod!" << endl;
			else {
				if (Bellman_Ford(n,m,muchie, start, w, info)==true)
					afisareDrum(n, start, info);
				else cout << "Circuit negativ" << endl;
			}
			continue;
		}
		if (cmd == 0) {
			cout << "Multumit ca ati utilizat aplicatia!" << endl;
			break;
		}
		if (cmd != 0 || cmd != 1) {
			cout << "Comanda gresita!" << endl;
		}
	}
	file.close();
	return 0;
}
