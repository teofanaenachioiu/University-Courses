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

//creeare lista de vecini
void vecini(vector<int> v[30], pair <int, int> muchie[30], int m) {
	int vf1, vf2;
	for (int i = 0; i < m; i++) {
		vf1 = muchie[i].first;
		vf2 = muchie[i].second;
		v[vf1 - 1].push_back(vf2);
	}
}


//vectorului Q i se atribuie initial toate varfurile grafului
void init_Q(vector <int> &Q, int n) {
	for (int i = 0; i < n; i++) {
		Q.push_back(i + 1);
	}
}

//elimina minimul din vector
int extract_min(vector <int> &Q, pair <int, int> info[30]) {
	int poz = 0, varf = Q.at(0), min = info[Q.at(0) - 1].first;
	for (unsigned i = 1; i < Q.size(); i++)
		if (info[Q.at(i) - 1].first < min)
		{
			varf = Q.at(i);
			min = info[Q.at(i) - 1].first;
			poz = i;
		}
	Q.erase(Q.begin() + poz);
	return varf;
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
void Dijkstra(int n, vector<int> v[30], int start, int w[30][30], pair <int, int> info[30]) {
	vector <int> Q, S;
	int u, vf;
	initializare_info(n, start, info);
	init_Q(Q, n);
	while (Q.size()>0) {
		u = extract_min(Q, info);
		S.push_back(u);
		if (info[u - 1].first != INT_MAX) {
			for (unsigned i = 0; i < v[u - 1].size(); i++) {
				vf = v[u - 1].at(i);
				relax(u, info[u - 1], info[vf - 1], w[u - 1][vf - 1]);
			}
		}
	}
}

//afisare matricea costurilor
void afisareCost(int w[30][30], int n) {
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			cout << w[i][j] << " ";
		}
		cout << endl;
	}
}

//algoritmul determina drumul minim de la un varf start la celelalte varfuri
bool Bellman_Ford(int n, int m, pair <int, int> muchie[30], int start, int w[30][30], pair <int, int> info[30]) {
	int i, j, cost;
	initializare_info(n, start, info);
	for (i = 1; i <= n - 1; i++) {
		for (j = 0; j < m; j++) {
			cost = w[muchie[j].first - 1][muchie[j].second - 1];
			relax(muchie[j].first, info[muchie[j].first - 1], info[muchie[j].second - 1], cost);
		}
	}

	for (j = 0; j < m; j++) {
		cost = w[muchie[j].first - 1][muchie[j].second - 1];
		if (info[muchie[j].first - 1].first > info[muchie[j].second - 1].first + cost)
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

//adauga un nou varf grafului si creeaza muchii de la noul varf la toate varfuri
void creeaza_graf(int& n, int& m, pair <int, int> muchie[30], int w[30][30])
{
	n++;
	for (int i = 0; i < n; i++) {
		muchie[m].first = n;
		muchie[m].second = i + 1;
		w[i][n - 1] = INT_MAX;
		w[n - 1][i] = 0;
		m++;
	}
}

//se revine la graful initial
void graf_initial(int &n, int& m) {
	n--;
	m = m - n-1;
}

//afiseaza muchiile (extremitatea 1 si extremitatea 2)
void afisareMuchii(pair <int, int> muchie[30], int m) {
	for (int i = 0; i < m; i++) {
		cout << muchie[i].first << " " << muchie[i].second << endl;
	}
}

//implementare Johnson
void Johnson(int n, int m, int start, pair <int, int> muchie[30], vector<int> v[30], pair <int, int> info[30],int w[30][30], int d[30][30]) {
	int i, h[30], extr1,extr2,_u,_v, weight[30][30];
	creeaza_graf(n, m, muchie, w);
	if (Bellman_Ford(n, m, muchie, n, w, info) == false)
	{
		cout << "Circuit negativ" << endl;
	}
	else {
		for (i = 0; i < n; i++) {
			h[i] = info[i].first;
		}
		initCost(weight, n);
		for (i = 0; i < m; i++) {
			extr1 = muchie[i].first;
			extr2 = muchie[i].second;
			weight[extr1-1][extr2-1] = w[extr1 - 1][extr2 - 1] + h[extr1 - 1] - h[extr2 - 1];
		}
		afisareCost(weight, n);
		graf_initial(n, m);
		initCost(d, n);
		
		for (_u = 1; _u <= n; _u++) {
			Dijkstra(n, v, _u, weight, info);
			for (_v = 1; _v <= n; _v++) {
				d[_u - 1][_v - 1] = info[_v - 1].first + h[_v - 1] - h[_u - 1];
			}
		}
	}
}

//afisare distante de la un nod la altul
void afisareDistante(int start, int n, int d[30][30])
{
	for (int i = 0; i < n; i++) {
		cout << "Drumul minim " << start << "-" << i + 1 << ": " << d[start - 1][i] << endl;;
	}
}

int main() {
	vector<int> v[30];
	pair <int, int> info[30], muchie[30];
	int n, w[30][30], m, start, cmd, d[30][30];
	file.open("input.txt");
	citire(n, muchie, w, m);
	vecini(v, muchie, m);
	while (1) {
		cout << "1. Aplicati algoritmul Johnson" << endl;
		cout << "0. Exit" << endl;
		cout << "Comanda: ";
		cin >> cmd;
		if (cmd == 1) {
			cout << "Dati un varf de start: "; cin >> start;
			if (start<1 || start >n)
				cout << "Nu exista acest nod!" << endl;
			else {
				//cout << "Dijkstra:" << endl;
				//Dijkstra(n, v, start, w, info);
				//afisareDrum(n, start, info);
				//cout << "Johnson:" << endl;
				Johnson(n, m, start, muchie, v, info, w,d);
				//afisareDistante(start, n, d);
				
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
