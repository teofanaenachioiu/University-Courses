//Reprezentari grafuri + 
#include<iostream>
#include<vector>
#include<fstream>
using namespace std;
ifstream f;

void init(vector<vector<int>>& matrice,int n,int m) {
	vector<int> aux;
	aux.assign(m, 0);
	for (int i = 0; i < n; i++)
		matrice.push_back(aux);
}

void citireMatriceAdiacenta(vector<vector<int>>& adiac,int &n,int &m) {
	f.open("input.txt",std::fstream::in);
	int  extr1, extr2, w;
	f >> n >> m;
	init(adiac, n, n);
	while (!f.eof()) {
		f >> extr1 >> extr2 >> w;
		adiac[extr1 - 1][extr2 - 1] = 1;
		adiac[extr2 - 1][extr1 - 1] = 1;
	}
	f.close();
}

void citireMatriceIncidenta(vector<vector<int>>& inc, int &n, int &m) {
	f.open("input.txt", std::fstream::in);
	int  extr1, extr2, w,nr=0;
	f >> n >> m;
	init(inc, n, m);
	while (!f.eof()) {
		f >> extr1 >> extr2 >> w;
		inc[extr1 - 1][nr] = 1;
		inc[extr2 - 1][nr] = 1;
		nr++;
	}
	f.close();
}

void citireListaAdiacenta(vector<vector<int>>& lista, int &n, int &m) {
	f.open("input.txt", std::fstream::in);
	int  extr1, extr2, w;
	f >> n >> m;
	init(lista, n, 0);
	while (!f.eof()) {
		f >> extr1 >> extr2 >> w;
		lista[extr1 - 1].push_back(extr2);
		lista[extr2 - 1].push_back(extr1);
	}
	f.close();
}


void matriceAdiacenta_ListaAdiacenta(vector<vector<int>> &adiac, vector<vector<int>> & lista,int &n,int &m) {
	int i, j;
	init(lista, n, 0);
	for (i = 0; i<n; i++)
		for (j = i; j<n; j++)
			if (adiac[i][j] == 1) {
				lista[i].push_back(j + 1);
				lista[j].push_back(i + 1);
			}
}

void matriceAdiacenta_MatriceIncidenta(vector<vector<int>> &adiac, vector<vector<int>> & inc, int &n, int &m) {
	init(inc, n, m);
	int i, j, nr=0;
	for(i=0;i<n;i++)
		for(j=i;j<n;j++)
			if (adiac[i][j] == 1) {
				inc[i][nr] = 1;
				inc[j][nr] = 1;
				nr++;
			}
}

void matriceIncienta_MatriceAdiacenta(vector<vector<int>> &inc, vector<vector<int>> & adiac, int &n, int &m) {
	int i, j, nr[2], index=0;
	init(adiac, n, n);
	for (j = 0; j < m; j++){
		index = 0;
		for (i = 0; i < n; i++)
			if (inc[i][j] == 1)
				nr[index++] = i;
		adiac[nr[0]][nr[1]] = 1;
		adiac[nr[1]][nr[0]] = 1;
	}
}

void matriceIncienta_ListaAdiacenta(vector<vector<int>> &inc, vector<vector<int>> & lista, int &n, int &m) {
	int i, j, nr[2], index = 0;
	init(lista, n, 0);
	for (j = 0; j < m; j++) {
		index = 0;
		for (i = 0; i < n; i++)
			if (inc[i][j] == 1)
				nr[index++] = i;
		lista[nr[0]].push_back(nr[1] + 1);
		lista[nr[1]].push_back(nr[0] + 1);
	}
}

void listaAdiacenta_MatriceAdiacenta(vector<vector<int>> &lista, vector<vector<int>> & adiac, int &n, int &m) {
	int i, j;
	init(adiac, n, n);
	for(i=0;i<n;i++)
		for (j = 0; j < lista[i].size(); j++) {
			adiac[i][lista[i][j]-1] = 1;
			adiac[lista[i][j] - 1][i] = 1;
		}
}

void listaAdiacenta_MatriceIncidenta(vector<vector<int>> &lista, vector<vector<int>> & inc, int &n, int &m) {
	int i, j,nr=0;
	init(inc, n, m*2);
	for (i = 0; i<n; i++)
		for (j = 0; j < lista[i].size(); j++) {
			inc[i][nr] = 1;
			inc[lista[i][j] - 1][nr] = 1;
			nr++;
			
		}
}

void afisare(vector<vector<int>>& matrice) {
	for (int i = 0; i < matrice.size(); i++){
		for (int j = 0; j < matrice[i].size(); j++)
			cout << matrice[i][j] << " ";
		cout << endl;
	}
}

int main() {
	vector<vector<int>> adiac, inc, lista, adiac1,adiac2,inc1,inc2,lista1,lista2;
	int n, m;

	citireMatriceAdiacenta(adiac,n,m);
	cout << "Citire matrice adiacenta: " << endl;
	//afisare(adiac);
	cout << endl;
	cout << "Matrice incidenta:" << endl;
	matriceAdiacenta_MatriceIncidenta(adiac, inc1, n, m);
	//afisare(inc1);
	cout << endl;
	cout << "Lista adiacenta:" << endl;
	matriceAdiacenta_ListaAdiacenta(adiac, lista1, n, m);
	//afisare(lista1);
	cout << "--------------------------------" << endl;

	citireMatriceIncidenta(inc, n, m);
	cout << "Citire matrice incidenta: " << endl;
	//afisare(inc);
	cout << endl;
	cout << "Matrice adiacenta:" << endl;
	matriceIncienta_MatriceAdiacenta(inc, adiac1, n, m);
	//afisare(adiac);
	cout << endl;
	cout << "Lista adiacenta:" << endl;
	matriceIncienta_ListaAdiacenta(inc, lista2, n, m);
	//afisare(lista);
	cout << "--------------------------------" << endl;

	citireListaAdiacenta(lista, n, m);
	cout << "Citire lista adiacenta:" << endl;
	//afisare(lista);
	cout << endl;
	listaAdiacenta_MatriceAdiacenta(lista, adiac2, n, m);
	cout << "Matrice adiacenta:" << endl;
	//afisare(adiac);
	cout << endl;
	listaAdiacenta_MatriceIncidenta(lista, inc2, n, m);
	cout << "Matrice incidenta:" << endl;
	//afisare(inc);
	cout << endl;
	return 0;
}