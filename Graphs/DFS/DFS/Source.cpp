#include<iostream>
#include<fstream>
#include<vector>
#include <string>
using namespace std;
ifstream f("input1.txt");
#define MAXIM 999999
typedef struct {
	string culoare;
	int parinte, distanta,final;
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

void dfs_visit(vector<vector<int>> &adiac, vector<varf>& varfuri,int u,int& time) {
	time +=1;
	varfuri[u].distanta = time;
	varfuri[u].culoare = "gri";
	for (int v = 0; v < adiac.size(); v++)
		if (adiac[v][u] == 1) 
			if(varfuri[v].culoare=="alb"){
				varfuri[v].parinte = u;
				dfs_visit(adiac, varfuri, v, time);
			}
	time +=1;
	varfuri[u].final = time;
	varfuri[u].culoare = "negru";
}

void dfs(vector<vector<int>> &adiac, vector<varf>& varfuri) {
	varf vf;
	int i;
	//initializare
	for (i = 0; i < adiac.size(); i++) {
		vf.culoare = "alb";
		vf.distanta = MAXIM;
		vf.parinte = -1;
		varfuri.push_back(vf);
	}
	int time = 0;
	for (i = 0; i < adiac.size(); i++) {
		if (varfuri[i].culoare == "alb")
		{
			dfs_visit(adiac, varfuri, i, time);
		}
	}
}

void afisare(vector<varf>& varfuri) {
	int i, p;
	for (i = 0; i < varfuri.size(); i++) {
		p = i;
		while (p!=-1) {
			cout << p+1<< " ";
			p = varfuri[p].parinte;
		}
		cout << endl;
	}
}

void table(vector<varf>& varfuri) {
	for (int i = 0; i < varfuri.size(); i++) {
		cout << varfuri[i].culoare << " " << varfuri[i].distanta << " " << varfuri[i].final << " " << varfuri[i].parinte << endl;
	}
}

int main() {
	vector<vector<int>> adiac;
	vector<varf> v;
	citire(adiac);
	dfs(adiac, v);
	afisare(v);
	//table(v);
	return 0;
}