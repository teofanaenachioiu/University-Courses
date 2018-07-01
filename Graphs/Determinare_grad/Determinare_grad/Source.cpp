//Determinare grad nod
#include<iostream>
#include<vector>
#include<fstream>
#include<queue>
using namespace std;
ifstream f;

void init(vector<vector<int>>& matrice, int n, int m) {
	vector<int> aux;
	aux.assign(m, 0);
	for (int i = 0; i < n; i++)
		matrice.push_back(aux);
}

void initGrade(vector<int>&grade, int n) {
	grade.assign(n, 0);
}

void citireMatriceAdiacenta(vector<vector<int>>& adiac, int &n, int &m) {
	f.open("input.txt", std::fstream::in);
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
	int  extr1, extr2, w, nr = 0;
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

void det_matriceAdiac(vector<vector<int>>& adiac, vector<int>&grade) {
	int i, j;
	initGrade(grade, adiac.size());
	for (i = 0; i < adiac.size(); i++)
		for (j = i; j < adiac[i].size(); j++)
			if (adiac[i][j] == 1) {
				grade[i]++;
				grade[j]++;
			}
}

void det_matriceInc(vector<vector<int>>& inc, vector<int>&grade) {
	int i, j;
	initGrade(grade, inc.size());
	for (i = 0; i < inc.size(); i++)
		for (j = 0; j < inc[i].size(); j++)
			if (inc[i][j] == 1) {
				grade[i]++;
			}
}

void det_listaAdiac(vector<vector<int>>& lista, vector<int>&grade) {
	int i;
	initGrade(grade, lista.size());
	for (i = 0; i < lista.size(); i++)
		grade[i] = lista[i].size();
}

bool graf_linie(vector<int> & grade) {
	int nr_1 = 0, nr_2 = 0;
	for (auto gr : grade)
		if (gr == 1)
			nr_1++;
		else if (gr == 2)
			nr_2++;
	if (nr_1 == 2 && nr_2 == grade.size() - 2)
		return true;
	else return false;
}

bool graf_bipartit(vector<vector<int>>& matrice) {
	vector<int> color;
	color.assign(matrice.size(), -1); //nu asignam culori
	color[0] = 1;//asignam rosu nodului 0
	queue <int> q;
	q.push(0);
	while (!q.empty()){
		int u = q.front();
		q.pop();
		// Find all non-colored adjacent vertices
		for (int v = 0; v < matrice.size();v++){
			// An edge from u to v exists and destination v is not colored
			if (matrice[u][v]==1 && color[v] == -1){
				// Assign alternate color to this adjacent v of u
				color[v] = 1 - color[u];
				q.push(v);
			}
			//  An edge from u to v exists and destination v is colored with the same color as u
			else if (matrice[u][v]==1 && color[v] == color[u])
				return false;
			}
		}

		// If we reach here, then all adjacent vertices can be colored with 
		// alternate color
		return true;
	}


bool graf_bipartitComplet(vector<vector<int>>& matrice) {
	vector<int> color;
	color.assign(matrice.size(), -1); //nu asignam culori
	color[0] = 1;//asignam rosu nodului 0
	int first = 0,second;
	for (int v = 0; v < matrice.size(); v++) {
		if (matrice[first][v] == 1 && color[v] == -1) {
				color[v] = 0;
				second = v;
			}
		}
	for (int v = 0; v < matrice.size(); v++) {
		if (matrice[second][v] == 1 && color[v] == -1) {
			color[v] = 1;
		}
	}
	for (auto c : color)
		if (c == -1) return false;
	return true;
}

void afisare(vector<int> & grade) {
	for (auto gr : grade)
		cout << gr << " ";
	cout << endl;
}

int main() {
	vector<vector<int>> lista, adiac, inc;
	vector<int> grade1,grade2,grade3;
	int n, m;
	citireMatriceAdiacenta(adiac, n, m);
	citireMatriceIncidenta(inc, n, m);
	citireListaAdiacenta(lista, n, m);

	cout << "Din matricea de adiacenta:" << endl;
	det_matriceAdiac(adiac, grade1);
	afisare(grade1);
	
	cout << "Din matricea de incidenta:" << endl;
	det_matriceInc(inc, grade2);
	afisare(grade2);

	cout << "Din lista de adiacenta:" << endl;
	det_listaAdiac(lista, grade3);
	afisare(grade3);

	if (graf_linie(grade1) == true)
		cout << "DA" << endl;
	else cout << "NU" << endl;

	if (graf_bipartit(adiac) == true)
		cout << "DA" << endl;
	else cout << "NU" << endl;

	if (graf_bipartitComplet(adiac) == true)
		cout << "DA" << endl;
	else cout << "NU" << endl;
	return 0;
}
