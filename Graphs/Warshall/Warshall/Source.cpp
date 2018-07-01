#include<iostream>
#include<fstream>
#include<vector>
using namespace std;
ifstream f("input1.txt");

#define MAXIM 999999

void init(vector<vector<int>>& matrice,int n,int m) {
	vector<int> aux;
	aux.assign(m, 0);
	for (int i = 0; i < n; i++)
		matrice.push_back(aux);
}

void initW(vector<vector<int>>& matrice, int n) {
	vector<int> aux;
	aux.assign(n, MAXIM);
	for (int i = 0; i < n; i++)
		matrice.push_back(aux);
	for (int i = 0; i < n; i++) {
		matrice[i][i] = 0;
	}
}

void citire(vector<vector<int>> &adiac, vector<vector<int>> &weight) {
	int n, m, extr1, extr2, w;
	f >> n >> m;
	init(adiac, n, n);
	initW(weight, n);
	while (!f.eof()) {
		f >> extr1 >> extr2 >> w;
		adiac[extr1 - 1][extr2 - 1] = 1;
		adiac[extr2 - 1][extr1 - 1] = 1;
		weight[extr1 - 1][extr2 - 1] = w;
		weight[extr2 - 1][extr1 - 1] = w;
	}
	f.close();
}

int min(int a, int b) {
	if (a < b) return a;
	else return b;
}

void Wharshall(vector<vector<int>>& drum,  vector<vector<int>>&  weight) {
	init(drum, weight.size(), weight.size());
	int i, j, k;
	//initializare drum
	for (i = 0; i < drum.size(); i++)
		for (j = 0; j < drum.size(); j++)
			drum[i][j] = weight[i][j];
	
	for (k = 0; k < drum.size(); k++)
		for (i = 0; i < drum.size(); i++)
			for (j = 0; j < drum.size(); j++)
				drum[i][j] = min(drum[i][j],drum[i][k]+drum[k][j]);
}

void afisare(vector<vector<int>>& matrice) {
	for (int i = 0; i < matrice.size(); i++){
		for (int j = 0; j < matrice[i].size(); j++)
			if (matrice[i][j] == MAXIM) cout << "* ";
			else cout << matrice[i][j] << " ";
		cout << endl;
	}
}

int main() {
	vector<vector<int>> adiac, weight,drum;
	citire(adiac, weight);
	Wharshall(drum, weight);
	afisare(drum);
	return 0;
}