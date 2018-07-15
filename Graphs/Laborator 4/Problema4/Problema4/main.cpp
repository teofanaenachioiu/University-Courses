#include<iostream>
#include<fstream>
#include<vector>
#include<utility>
using namespace std;
ifstream f;
//initiaza cu 0 o matrice nxn
void initVect(vector<vector<int>>& a, int& n) {
	vector <int> aux;
	for (int i = 0; i < n; i++) {
		aux.assign(n, 0);
		a.push_back(aux);
	}
}

//citeste muchiile din fisier si determina matricea de adiacenta
void citire(vector<vector<int>>& a, int& n){
	int x, y;
	f.open("input.txt");
	f >> n;
	initVect(a,n);
	while(!f.eof()){
		f >> x >> y;
		a[x - 1][y - 1] = a[y - 1][x - 1] = 1;
	}
	f.close();
}

//determina gradele varfurilor
void grade(vector<int>& g, vector<vector<int>>& a) {
	unsigned i, j;
	g.assign(a.size(), 0);
	for (i = 0; i < a.size(); i++) {
		for (j = 0; j < a.size(); j++) {
			if (a[i][j] == 1)
				g[i]++;
		}
	}
}

//marcheaza varfurile vizitate (pentru determinarea conexitatii)
void df(int nod, vector<vector<int>>& a, vector<int>& viz){
	viz[nod] = 1;
	for (unsigned k = 0; k <a.size(); k++)
		if (a[nod][k] == 1 && viz[k] == 0)
			df(k,a,viz);
}

int verific(vector<int>&g, vector<vector<int>>& a){
	vector<int> viz;
	//verifica daca exista un ciclu eulerian
	viz.assign(g.size(), 0);
	for (auto el:g)
		if (el % 2 == 1)
			return 0;
	//verific daca graful e conex
	df(0,a,viz);
	for (auto el:viz)
		if (el == 0)
			return 0;
	return 1;
}

//determina ciclu eulerian
void ciclu_eulerian(vector<int> &lista,vector<vector<int>>& a, vector<int>& g, int k){
	int max = 0, nod = 0;
	lista.push_back(k);
	for (unsigned i = 0; i < a.size(); i++){
		if (a[k-1][i] == 1)
			if (g[i]>max){
				max = g[i];//grad maxim
				nod = i+1;//nodul cu grad maxim
			}
	}
	if (nod != 0){
		a[k-1][nod-1] = a[nod-1][k-1] = 0;//sterge muchia k-nod
		g[k-1]--;//decrementeaza gradul nodului k
		g[nod-1]--;//decrementeaza gradul nodului nod
		ciclu_eulerian(lista,a,g,nod);//merge in varful cu grad maxim determinat in functia curenta
	}
}

//afiseaza un vector
void afisare(vector<int>& v) {
	for (auto el : v)
		cout << el << " ";
	cout << endl;
}

int main() {
	vector<vector<int>> a;
	vector<int> g,ciclu;
	int n;
	citire(a, n);
	grade(g, a);
	if (verific(g, a) == 1) {
		cout << "Graf eulerian:" << endl;
		ciclu_eulerian(ciclu, a,g,1);
		afisare(ciclu);
	}
	else cout << "Graful nu e eulerian" << endl;;
	return 0;
}