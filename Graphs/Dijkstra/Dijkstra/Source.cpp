#include<iostream>
#include<fstream>
#include<vector>
#include<string>
#include<queue>
#include<algorithm>
using namespace std;
ifstream f("input1.txt");
#define MAXIM 999999

typedef struct {
	int parinte, distanta;
}varf;

void init(vector<vector<int>>& matrice, int &n,int val) {
	vector<int> aux;
	aux.assign(n, val);
	for (int i = 0; i < n; i++)
		matrice.push_back(aux);
}

void citire(vector<vector<int>> &adiac, vector<vector<int>> &weight) {
	int n, m, extr1, extr2,w;
	f >> n >> m;
	init(adiac, n,0);
	init(weight, n,MAXIM);
	while (!f.eof()) {
		f >> extr1 >> extr2>>w;
		adiac[extr1][extr2] = 1;
		weight[extr1][extr2] = w;
	}
	f.close();
}

void initializare(vector<varf>& varfuri, int n) {
	varf vf;
	for (int i = 0; i < n; i++) {
		vf.distanta = MAXIM;
		vf.parinte = -1;
		varfuri.push_back(vf);
	}
}

int extract_min(vector<int> &s, vector<varf>& varfuri) {
	int min = MAXIM,poz=-1,val; //initializam valorile min si poz
	for(int k=0;k<s.size();k++) //cautam in vectorul s varful ce are distanta minima
		if(varfuri[s[k]].distanta < min){ //distanta varfului din s este mai mica decat min curent
			min = varfuri[s[k]].distanta; //actualizam minimul
			val = s[k]; //varful cu valoarea minima a distantei
			poz = k; //pozitia pe care se afla varful in vectorul s
		}
	s.erase(s.begin()+poz); //stergem varful cu valoarea minima
	return val; //returnam nodul cu valoarea minima
}

void relax(vector<varf>& varfuri, vector<vector<int>> &weight, int& v, int& u) {
	if (varfuri[v].distanta > varfuri[u].distanta + weight[u][v]) {
		varfuri[v].distanta = varfuri[u].distanta + weight[u][v];
		varfuri[v].parinte = u;
	}
}

void Dijkstra(vector<vector<int>> &adiac, vector<vector<int>> &weight, vector<varf>& varfuri, int start) {
	vector<int> s;
	int i, u, v;

	initializare(varfuri, adiac.size());
	varfuri[start].distanta = 0; //varful de start
	//initializam s cu varfurile grafului
	for (i = 0; i < adiac.size(); i++) {
		s.push_back(i);
	}

	while (!s.empty()) {
		u = extract_min(s, varfuri);
		for (v = 0; v < adiac.size(); v++)
			if (adiac[u][v] == 1)
				relax(varfuri, weight, v, u);
	}

	
}

void afisare(vector<varf>& varfuri) {
	for (int i = 0; i < varfuri.size(); i++) {
		int p = i;
		while (p != -1) {
			cout << p << " ";
			p = varfuri[p].parinte;
		}
		cout << endl;
	}
}

int main() {
	vector<vector<int>> adiac,weight;
	vector<varf> varfuri;
	citire(adiac,weight);
	Dijkstra(adiac, weight, varfuri, 0);
	afisare(varfuri);
	return 0;
}