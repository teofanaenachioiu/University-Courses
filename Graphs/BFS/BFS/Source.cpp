#include<iostream>
#include<fstream>
#include<vector>
#include <string>
#include <queue>
using namespace std;
ifstream f("input.txt");
#define MAXIM 999999
typedef struct  {
	string culoare;
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

void bfs(vector<vector<int>> &adiac, vector<varf>& varfuri,int start) {
	int i, u, v;
	varf vf;
	queue<int> q;
	for (i = 0; i < adiac.size(); i++) {
		vf.culoare = "alb";
		vf.distanta = MAXIM;
		vf.parinte = -1;
		varfuri.push_back(vf);
	}
	varfuri[start].culoare = "gri";
	varfuri[start].distanta = 0;
	q.push(start);
	while (!q.empty()) {
		u = q.front();
		q.pop();
		for(v=0;v<adiac.size();v++)
			if(adiac[v][u]==1)
				if (varfuri[v].culoare == "alb") {
					varfuri[v].culoare = "gri";
					varfuri[v].distanta = varfuri[u].distanta + 1;
					varfuri[v].parinte = u;
					q.push(v);
				}
		varfuri[u].culoare = "negru";
	}
}

void afisare(vector<varf>& varfuri) {
	int i,p,d;
	for (i = 0; i < varfuri.size(); i++) {
		d = varfuri[i].distanta;
		p = i;
		while (d >= 0) {
			cout << p+1 << " ";
			p = varfuri[p].parinte;
			d--;
		}
		cout << endl;
	}
}

int main() {
	vector<vector<int>> adiac;
	vector<varf> v;
	citire(adiac);
	bfs(adiac, v, 0);
	afisare(v);
	return 0;
}