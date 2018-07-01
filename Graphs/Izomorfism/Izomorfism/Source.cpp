#include<iostream>
#include<fstream>
#include<vector>
using namespace std;
ifstream f("input1.txt");
ifstream g("input2.txt");

void init(vector<vector<int>>& matrice, int n) {
	vector<int> aux;
	aux.assign(n, 0);
	for (int i = 0; i < n; i++)
		matrice.push_back(aux);
}

void citire(vector<vector<int>> &matrice) {
	int n, m, extr1, extr2;
	f >> n >> m;
	init(matrice, n);
	while (!f.eof()) {
		f >> extr1 >> extr2;
		matrice[extr1 - 1][extr2 - 1] = 1;
		matrice[extr2 - 1][extr1 - 1] = 1;
	}
	f.close();
}

void pordus() {

}

void egale() {

}

int main() {
	vector<vector<int>> matrice1, matrice2, permutare, m1perm, m2perm;
	return 0;
}