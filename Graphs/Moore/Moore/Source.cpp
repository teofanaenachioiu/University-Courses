#include<iostream>
#include<fstream>
#include<vector>
#include<string>
#include<queue>
using namespace std;
ifstream f("input1.txt");
#define MAXIM 999999
typedef struct {
	int parinte, distanta;
}varf;

void init(vector<vector<int>>& matrice, int &n) {
	vector<int> aux;
	aux.assign(n, 0);
	for (int i = 0; i < n; i++)
		matrice.push_back(aux);
}

void citire(vector<vector<int>> &adiac) {
	int n, m, extr1, extr2;
	f >> n >> m;
	init(adiac, n);
	while (!f.eof()) {
		f >> extr1 >> extr2;
		adiac[extr1][extr2] = 1;
		adiac[extr2][extr1] = 1;
	}
	f.close();
}

void Moore(vector<vector<int>> &adiac, vector<varf>& varfuri,int start) {
	varf vf;
	int i,u,v;
	queue<int> q;
	for (i = 0; i < adiac.size(); i++){
		vf.distanta = MAXIM;
		vf.parinte = -1;
		varfuri.push_back(vf);
	}
	varfuri[start].distanta= 0;
	q.push(start);
	while (!q.empty()) {
		u = q.front();
		q.pop();
		for(v=0;v<adiac.size();v++)
			if(adiac[u][v]==1)
				if (varfuri[v].distanta == MAXIM) {
					varfuri[v].distanta = varfuri[u].distanta + 1;
					varfuri[v].parinte = u;
					q.push(v);
				}
	}
}

void afisare(vector<varf>& varfuri) {
	int i, p,d;
	for (i = 0; i < varfuri.size(); i++) {
		if (varfuri[i].parinte != -1) {
			p = i;
			d = varfuri[i].distanta;
			while (d >= 0) {
				cout << p  << " ";
				p = varfuri[p].parinte;
				d--;
			}
			cout << endl;
		}
	}
}

int main() {
	vector<vector<int>> adiac;
	vector<varf> varfuri;
	citire(adiac);
	Moore(adiac, varfuri,0);
	afisare(varfuri);
	return 0;
}