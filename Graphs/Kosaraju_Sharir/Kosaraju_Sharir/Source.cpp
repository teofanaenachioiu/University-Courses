#include<iostream>
#include<fstream>
#include<vector>
#include<string>
#include<stack>

using namespace std;
ifstream f("input1.txt");

#define MAXIM 999999;

typedef struct {
	string culoare;
	int parinte;
}varf;

void init(vector<vector<int>>& adiac, int& n) {
	vector<int> aux;
	aux.assign(n, 0);
	for (int i = 0; i < n; i++)
		adiac.push_back(aux);
}

void citire(vector<vector<int>>& adiac) {
	int n, m, extr1, extr2;
	f >> n >> m;
	init(adiac, n);
	while (!f.eof()) {
		f >> extr1 >> extr2;
		adiac[extr1][extr2] = 1;
	}
	f.close();
}

void dfs_visit(stack<int>& s,vector<vector<int>>& adiac, vector<varf>& varfuri, int u) {
	varfuri[u].culoare = "gri";
	for(int v=0;v<adiac.size();v++)
		if(adiac[u][v]==1)
			if (varfuri[v].culoare == "alb") {
				varfuri[v].parinte = u;
				dfs_visit(s,adiac, varfuri, v);
			}
	varfuri[u].culoare = "negru";
	s.push(u);
}

void dfs(stack<int>& s,vector<vector<int>>& adiac, vector<varf>& varfuri) {
	int i,u;
	varf vf;
	for (i = 0; i < adiac.size();i++) {
		vf.culoare = "alb";
		vf.parinte = -1;
		varfuri.push_back(vf);
	}
	for (u = 0; u < adiac.size(); u++)
		if (varfuri[u].culoare == "alb")
			dfs_visit(s,adiac, varfuri, u);
}

void dfs_visit_stack(vector<int>& rez,stack<int>& s, vector<vector<int>>& adiac, vector<varf>& varfuri, int u) {
	varfuri[u].culoare = "gri";
	for (int v = 0; v<adiac.size(); v++)
		if (adiac[v][u] == 1)
			if (varfuri[v].culoare == "alb") {
				varfuri[v].parinte = u;
				dfs_visit_stack(rez,s, adiac, varfuri, v);
			}
	varfuri[u].culoare = "negru";
	rez.push_back(u);
}

vector<vector<int>> dfs_stack(stack<int>& s, vector<vector<int>>& adiac, vector<varf>& varfuri) {
	int i, u;
	varf vf;
	vector<vector<int>> conexe;
	vector<int> rez;
	for (i = 0; i < adiac.size(); i++) {
		vf.culoare = "alb";
		vf.parinte = -1;
		varfuri.push_back(vf);
	}
	while (!s.empty()) {
		u = s.top();
		s.pop();
		if (varfuri[u].culoare == "alb") {
			rez.clear();
			dfs_visit_stack(rez,s, adiac, varfuri, u);
			conexe.push_back(rez);
		}
	}
	return conexe;
}

void afisare(vector<vector<int>>& conexe) {
	int i, j;
	for (i = 0; i < conexe.size(); i++) {
		cout << "Componenta " << i + 1 << endl;
		for (j = 0; j < conexe[i].size(); j++)
			cout << conexe[i][j] << " ";
		cout << endl;
	}
	cout << endl;
}

int main() {
	vector<vector<int>> adiac;
	vector<varf> v,v1;
	stack <int> s;
	vector<vector<int>> cconexe;
	citire(adiac);
	dfs(s, adiac, v);
	cconexe=dfs_stack(s, adiac, v1);
	afisare(cconexe);
	cout << endl;
	return 0;
}
