#include<iostream>
#include<vector>
#include<fstream>
#include<algorithm>

using namespace std;
ifstream f("input.txt");

#define MAXIM 999999

typedef struct {
	int distanta, parinte;
}varf;

void init(vector<vector<int>> &matrice, int&n, int val) {
	vector<int> aux;
	aux.assign(n, val);
	matrice.assign(n, aux);
	if (val == MAXIM)
		for (int i = 0; i < n; i++)
			matrice[i][i] = 0;
}

void citire(vector<vector<int>>&adiac, vector<vector<int>>& weight) {
	int extr1, extr2,n,m,w;
	f >> n >> m;
	init(adiac, n, 0);
	init(weight, n, MAXIM);
	while (!f.eof()) {
		f >> extr1 >> extr2 >> w;
		adiac[extr1][extr2] = 1;
		adiac[extr2][extr1] = 1;
		weight[extr1][extr2] = w;
		weight[extr2][extr1] = w;
	}
	f.close();
}

int extract_min(vector<varf>& varfuri, vector<int>& q) {
	int poz = -1, min = MAXIM, val;
	for(int k=0;k<q.size();k++)
		if (varfuri[q[k]].distanta < min) {
			min = varfuri[q[k]].distanta;
			poz = k;
			val = q[k];
		}
	q.erase(q.begin() + poz);
	return val;
}

void Prim(vector<vector<int>>& adiac, vector<vector<int>>& weight) {
	vector<varf> varfuri;
	varf vf;
	vector<int> q;
	int u,v ;
	vf.distanta = MAXIM;
	vf.parinte = -1;
	varfuri.assign(adiac.size(), vf);
	varfuri[0].distanta = 0;

	for (int i = 0; i < adiac.size(); i++)
		q.push_back(i);
	while (!q.empty()) {
		u = extract_min(varfuri, q);
		for(v=0;v<adiac.size();v++)
			if (adiac[u][v] == 1) {
				auto it = find(q.begin(), q.end(), v);
				if (it != q.end() && weight[u][v] < varfuri[v].distanta) {
					varfuri[v].distanta = weight[u][v];
					varfuri[v].parinte = u;
				}
			}
	}
	for (int i = 1; i < varfuri.size(); i++)
		cout << "(" << i << "," << varfuri[i].parinte << ") ";
	cout << endl;
}


int main() {
	vector<vector<int>> adiac, weight;
	citire(adiac, weight);
	Prim(adiac, weight);
	return 0;
}