#include<iostream>
#include<fstream>
#include<vector>
using namespace std;
ifstream f("input.txt");

typedef struct {
	string culoare;
	int parinte;
}varf;

void init(vector<vector<int>>& adiac, int n) {
	vector<int> aux;
	aux.assign(n, 0);
	for (int i = 0; i < n; i++)
		adiac.push_back(aux);
}

void citire(vector<vector<int>>& adiac) {
	int extr1, extr2, n, m;
	f >> n >> m;
	init(adiac, n);
	while (!f.eof()) {
		f >> extr1 >> extr2;
		adiac[extr1][extr2] = 1;
	}
	f.close();
}

void dfs_vizit(vector<vector<int>>&adiac, vector<varf>& varfuri, int  u, vector<int> &sort) {
	int v;
	varfuri[u].culoare = "gri";
	for (v = 0; v < adiac.size(); v++)
		if (adiac[u][v] == 1) 
			if (varfuri[v].culoare == "alb") {
				varfuri[v].parinte = u;
				dfs_vizit(adiac, varfuri, v, sort);
			}
	varfuri[u].culoare = "negru";
	sort.push_back(u);
}
void sortare_topologica(vector<vector<int>>& adiac, vector<varf>& varfuri) {
	varf vf;
	int i;
	vector<int> sort;
	for (i = 0; i < adiac.size(); i++) {
		vf.culoare = "alb";
		vf.parinte = -1;
		varfuri.push_back(vf);
	}
	for (i = 0; i < varfuri.size(); i++)
		if (varfuri[i].culoare == "alb")
			dfs_vizit(adiac, varfuri,i,sort);

	for (i = sort.size()-1; i >=0 ; i--)
		cout << sort[i] << " ";
	cout << endl;
}

int main() {
	vector<vector<int>> adiac;
	vector<varf> varfuri;
	citire(adiac);
	sortare_topologica(adiac,varfuri);
	return 0;
}

