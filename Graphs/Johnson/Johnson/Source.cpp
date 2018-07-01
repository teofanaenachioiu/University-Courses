#include <iostream>
#include <fstream>
#include <vector>
#include <string>
using namespace std;
ifstream f("input.txt");
#define MAXIM 999999

typedef struct {
	int distanta, parinte;
}varf;

void init(vector<vector<int>>& matrice, int &n, int val) {
	vector<int> aux;
	aux.assign(n, val);
	matrice.assign(n, aux);
	if (val == MAXIM) {//pentru initierea matricei costurilor
		for (int i = 0; i < matrice.size(); i++)
			matrice[i][i] = 0;
	}
}

void citire(vector<vector<int>>& adiac, vector<vector<int>>& weight) {
	int extr1, extr2, w, n, m;
	f >> n >> m;
	init(adiac, n, 0);
	init(weight, n, MAXIM);
	while (!f.eof()) {
		f >> extr1 >> extr2 >> w;
		adiac[extr1][extr2] = 1;
		weight[extr1][extr2] = w; 
	}
	f.close();
}

int extract_min(vector<int>& q, vector<varf>& varfuri) {
	int k,poz=-1, val=-1, min=MAXIM;
	for (k = 0; k < q.size(); k++) {
		if (varfuri[q[k]].distanta < min) {
			min = varfuri[q[k]].distanta;
			poz = k;
			val = q[k];
		}
	}
	q.erase(q.begin() + poz);
	return val;
}

void initializare_varfuri(vector<varf>& varfuri, int n) {
	varf vf;
	for (int i = 0; i < n; i++) {
		vf.distanta = MAXIM;
		vf.parinte = -1;
		varfuri.push_back(vf);
	}
}

void relax(vector<varf>&varfuri, vector<vector<int>>&  weight,int& u,int& v) {
	if (varfuri[v].distanta > varfuri[u].distanta + weight[u][v]) {
		varfuri[v].distanta = varfuri[u].distanta + weight[u][v];
		varfuri[v].parinte = u;
	}
}

void dijkstra(vector<vector<int>>& adiac, vector<vector<int>>& weight, vector<varf>& varfuri, int start) {
	varf vf;
	int i,u,v;
	vector<int> q;
	varfuri.clear();//sterg vectorul de varfuri!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	initializare_varfuri(varfuri, adiac.size());
	varfuri[start].distanta = 0;
	for (i = 0; i < varfuri.size(); i++)
		q.push_back(i);
	while (!q.empty()) {
		u = extract_min(q, varfuri);
		for (v = 0; v < adiac.size(); v++) {
			if (adiac[u][v] == 1) {
				relax(varfuri, weight, u, v);
			}
		}
	}
}

void afisare(vector<varf>& varfuri) {
	int i,p;
	for (i = 0; i < varfuri.size(); i++) {
		p = i;
		while (p != -1) {
			cout << p << " ";
			p = varfuri[p].parinte;
		}
		cout << endl;
	}
}

bool Bellman_Ford(vector<vector<int>>& adiac, vector<vector<int>>& weight, vector<varf>& varfuri, int start) {
	varfuri.clear();//sterg vectorul de varfuri!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	initializare_varfuri(varfuri, adiac.size());
	varfuri[start].distanta = 0;
	int k,i,j;
	for (k = 0; k < adiac.size() - 1; k++)
		for (i = 0; i < adiac.size(); i++)
			for (j = 0; j < adiac.size(); j++)
				if (adiac[i][j] == 1)
					relax(varfuri, weight, i, j);
	for (i = 0; i < adiac.size(); i++)
		for (j = 0; j < adiac.size(); j++)
			if (adiac[i][j] == 1)
				if (varfuri[j].distanta > varfuri[i].distanta + weight[i][j])
					return false;
	return true;
}

void creez_graf_nou(vector<vector<int>>& adiac, vector<vector<int>>& weight, vector<vector<int>>& adiac_nou, vector<vector<int>>& weight_nou) {
	int n = adiac.size() + 1;
	init(weight_nou, n, MAXIM);
	init(adiac_nou, n, 0);
	for(int i=0;i<n-1;i++)
		for (int j = 0; j < n-1; j++) {
			adiac_nou[i][j] = adiac[i][j];
			weight_nou[i][j] = weight[i][j];
		}
	for (int i = 0; i < n; i++) {
		weight_nou[n - 1][i] = 0;
		adiac_nou[n - 1][i] = 1;
		adiac_nou[i][n - 1] = 0;
	}

}

void Johnson(vector<vector<int>>& adiac, vector<vector<int>>& weight) {
	vector<vector<int>> adiac_nou, weight_nou,drum;
	vector<varf> varfuri;
	vector<int> h;
	int u,v;
	initializare_varfuri(varfuri, adiac.size());
	varfuri[0].distanta = 0;
	creez_graf_nou(adiac, weight, adiac_nou, weight_nou);
	if (Bellman_Ford(adiac_nou, weight_nou, varfuri, 0) == false)
		cout << "Nu exista" << endl;
	else {
		//retin distantele
		for (u = 0; u < varfuri.size(); u++)
			h.push_back(varfuri[u].distanta);

		//reponderez
		for (u = 0; u < adiac.size(); u++)
			for (v = 0; v < adiac.size(); v++)
				if (adiac[u][v] == 1)
					weight[u][v] = weight_nou[u][v] + h[u] - h[v];
		int n = adiac.size();
		//initializez matricea drumurilor
		init(drum, n, 0);

		//retulez dijkstra pentru fiecare nod
		for (u = 0; u < adiac.size(); u++) {
			dijkstra(adiac, weight, varfuri, u);

			//pentru fiecare nod adaug in matrice distanta de la start_dijkstra la nod
			for (v = 0; v < adiac.size(); v++)
				drum[u][v] = varfuri[v].distanta + h[u] - h[v];
		}
	}
}

int main() {
	vector<vector<int>> adiac, weight;
	vector<varf> varfuri;
	citire(adiac, weight);
	Johnson(adiac, weight);
	return 0;
}
