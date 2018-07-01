#include<iostream>
#include<fstream>
#include<vector>
using namespace std;
ifstream f("input1.txt");

#define MAXIM 999999

void init(vector<vector<int>>& matrice, int n, int m) {
	vector<int> aux;
	aux.assign(m, 0);
	for (int i = 0; i < n; i++)
		matrice.push_back(aux);
}

void citire(vector<vector<int>> &adiac) {
	int n, m, extr1, extr2, w;
	f >> n >> m;
	init(adiac, n, n);
	while (!f.eof()) {
		f >> extr1 >> extr2 >> w;
		adiac[extr1 - 1][extr2 - 1] = 1;
	}
	f.close();
}

int max(int a, int b) {
	if (a > b) return a;
	else return b;
}

void afisare(vector<vector<int>>& matrice) {
	for (int i = 0; i < matrice.size(); i++) {
		for (int j = 0; j < matrice[i].size(); j++)
			if (matrice[i][j] == MAXIM) cout << "* ";
			else cout << matrice[i][j] << " ";
			cout << endl;
	}
}
void Floyd_Wharshall(vector<vector<int>>& drum, vector<vector<int>>&  matrice) {
	init(drum, matrice.size(), matrice.size());
	int i, j, k;
	//initializare
	for (i = 0; i < drum.size(); i++) {
		for (j = 0; j < drum.size(); j++)
			drum[i][j] = matrice[i][j];
		//drum[i][i] = 1; //matricea tranz
	}
	for (i = 0; i < drum.size(); i++)
	{
		for (j = 0; j < drum.size(); j++)
			cout << drum[i][j] << " ";
		cout << endl;
	}
	cout << endl;
	for (k = 0; k < drum.size(); k++) {
		for (i = 0; i < drum.size(); i++)
			for (j = 0; j < drum.size(); j++)
				//drum[i][j] = mim(drum[i][j], drum[i][k] + drum[k][j]); //warshall ponderile
				//drum[i][j] = drum[i][j] || drum[i][k] && drum[k][j]; //tranz
				drum[i][j] = drum[i][j] + drum[i][k] * drum[k][j]; //numarul de drumuri
		for (i = 0; i < drum.size(); i++)
		{
			for (j = 0; j < drum.size(); j++)
				cout << drum[i][j] << " ";
			cout << endl;
		}
	}
}

int main() {
	vector<vector<int>> adiac, drum;
	citire(adiac);
	Floyd_Wharshall(drum, adiac);
	afisare(drum);
	return 0;
}