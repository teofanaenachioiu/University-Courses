#include <iostream>
#include <fstream>
#include <vector>
#include <string>
using namespace std;
#define MAXIM 99999
ifstream f("input.txt");

typedef struct{
	string culoare;
	int parinte,distanta;
}varf;

void init(vector<vector<int>>& matrice, int &n, int val) {
	vector<int> aux;
	aux.assign(n, val);
	matrice.assign(n, aux);
}

void citire(vector<vector<int>>& adiac, vector<vector<int>>& weight) {
	int extr1, extr2, w, n, m;
	f >> n >> m;
	init(adiac, n, 0);
	init(weight, n, MAXIM);
	while (!f.eof()) {
		f >> extr1 >> extr2 >> w;
		adiac[extr1][extr2] = 1;
		weight[extr1][extr2] = 1;
	}
	f.close();
}

void dfs_visit(vector<vector<int>>& adiac, vector<vector<int>>& weight, vector<varf>& varfuri, int u, vector<int>& rez) {
	varfuri[u].culoare = "gri";
	int v;
	for (v = 0; v < adiac.size(); v++)
		if (adiac[u][v] == 1)
			if (varfuri[v].culoare == "alb") {
				varfuri[v].parinte = u;
				dfs_visit(adiac, weight, varfuri, v, rez);
			}
	varfuri[u].culoare = "negru";
	rez.push_back(u);
}

vector<int> sortare_topologica(vector<vector<int>>& adiac, vector<vector<int>>& weight,vector<varf>& varfuri) {
	varf vf;
	int i,u;
	vector<int> rez;
	for (i = 0; i < adiac.size(); i++) {
		vf.culoare = "alb";
		vf.parinte = -1;
		vf.distanta = MAXIM;
		varfuri.push_back(vf);
	}
	for (u = 0; u < adiac.size(); u++)
		if (varfuri[u].culoare == "alb") {
			dfs_visit(adiac,weight,varfuri,u,rez);
		}
	return rez;
}

void relax(vector<vector<int>>& weight, vector<varf>& varfuri, int &u, int &v) {
	if (varfuri[v].distanta > varfuri[u].distanta + weight[u][v]) {
		varfuri[v].distanta = varfuri[u].distanta + weight[u][v];
		varfuri[v].parinte = u;
	}
}

void det_drum_dag(vector<vector<int>>& adiac, vector<vector<int>>& weight, vector<varf>& varfuri) {
	vector<int> sort = sortare_topologica(adiac, weight, varfuri);
	for (int u = 0; u < sort.size(); u++)
		for (int v = 0; v < adiac.size(); v++)
			if (adiac[sort[u]][v] == 1)
				relax(weight, varfuri, sort[u], v);
}


void afisare(vector<varf>& varfuri) {
	int p, u;
	for (u = 0; u < varfuri.size(); u++) {
		p = u;
		while (p != -1) {
			cout << p + 1 << " " ;
			p = varfuri[p].parinte;
		}
		cout << endl;
	}
}

int main() {
	vector<vector<int>> adiac, weight;
	vector<varf> varfuri;
	vector<int> rez;
	citire(adiac, weight);
	det_drum_dag(adiac, weight, varfuri);
	afisare(varfuri);
	return 0;
}