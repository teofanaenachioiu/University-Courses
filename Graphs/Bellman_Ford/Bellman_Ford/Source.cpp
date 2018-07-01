#include<iostream>
#include<fstream>
#include<vector>
using namespace std;
ifstream f("input1.txt");
#define MAXIM 999999

typedef struct {
	int distanta, parinte;
}varf;

void init(vector<vector<int>>& matrice, int& n, int val) {
	vector<int> aux;
	aux.assign(n, val);
	matrice.assign(n, aux);
}

void citire(vector<vector<int>>& adiac, vector<vector<int>>& weight) {
	int extr1, extr2, w, n, m;
	f >> n >> m;
	init(adiac, n,0);
	init(weight, n, MAXIM);
	while (!f.eof()) {
		f >> extr1 >> extr2 >> w;
		adiac[extr1][extr2] = 1;
		weight[extr1][extr2] = w;
	}
	f.close();
}

void relax(vector<vector<int>>& weight, vector<varf>& varfuri, int& u, int &v) {
	if (varfuri[v].distanta > varfuri[u].distanta + weight[u][v]) {
		varfuri[v].distanta = varfuri[u].distanta + weight[u][v];
		varfuri[v].parinte = u;
	}
}

bool Bellman_Ford(vector<vector<int>>& adiac, vector<vector<int>>& weight, vector<varf>& varfuri,int start) {
	varf vf;
	int i,j,k;
	for (i = 0; i < adiac.size(); i++) {
		vf.distanta = MAXIM;
		vf.parinte = -1;
		varfuri.push_back(vf);
	}
	varfuri[start].distanta = 0;
	for (i = 0; i < adiac.size() - 1; i++) {
		//pentru fiecare muchie
		for(j=0;j<adiac.size();j++)
			for(k=0;k<adiac.size();k++)
				if (adiac[j][k] == 1) {
					relax(weight, varfuri, j, k);
				}
	}
	//pentru fiecare muchie
	for (j = 0; j < adiac.size(); j++)
		for (k = 0; k < adiac.size(); k++)
			if (adiac[j][k] == 1)
				if (varfuri[k].distanta > varfuri[j].distanta + weight[j][k])
					return false;
	return true;

}

int main() {
	vector<vector<int>> adiac, weight;
	vector<varf> varfuri;
	citire(adiac, weight);
	if (Bellman_Ford(adiac,weight,varfuri,0))
		cout << "DA" << endl;
	else cout << "NU" << endl;
	return 0;
}
