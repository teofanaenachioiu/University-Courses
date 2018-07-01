#include<iostream>
#include<fstream>
#include<queue>
#define MAX 100
using namespace std;
int n, m, sursa, dest, nr, c[MAX][MAX];//nr=nr cai, c=matricea capacitaii
ifstream f("input.txt");

int bfs(int  rG[MAX][MAX], int sursa, int dest, int prev[MAX]) {
	bool color[MAX];//vizitat nevizitat
	memset(color, 0, sizeof(color));
	queue<int> q;
	q.push(sursa);
	color[sursa];
	while (!q.empty()) {
		int u = q.front();
		q.pop();
		for (int v = 0; v < n; v++) {
			if (color[v] == false && rG[u][v] > 0) {
				q.push(v);
				prev[v] = u;
				color[v] = true;
			}
		}
	}
	return color[dest] == true;
}

void dfs(int rG[MAX][MAX], int sursa, bool color[MAX]) {
	color[sursa] = true;
	for (int v = 0; v < n; v++)
		if (rG[sursa][v] > 0 && color[v] == false)
			dfs(rG, v, color);
}

int min(int a, int b) {
	if (a < b) return a;
	return b;
}

void afisareTaietura(bool color[MAX]) {
	int u, v;
	for (u = 0; u < n; u++)
		for (v = 0; v < n; v++)
			if (color[u] && !color[v] && c[u][v])//u este accesibil din sursa, iar v nu e accesibil din sursa
				cout << u << "->" << v << " cap=" << c[u][v] << endl;
}

int minCut(int c[MAX][MAX], int sursa, int dest) {
	int u, v, flux_max = 0, rG[MAX][MAX], prev[MAX];
	for (u = 0; u < n; u++)
		for (v = 0; v < n; v++)
			rG[u][v] = c[u][v];
	//cat timp exsita cale reziduala
	while (bfs(rG, sursa, dest, prev)) {
		int flux = INT_MAX;
		for (v = dest; v != sursa; v = prev[v]) {
			u = prev[v];
			flux = min(flux, rG[u][v]);
		}
		for (v = dest; v != sursa; v = prev[v]) {
			u = prev[v];
			rG[u][v] -= flux;
			rG[v][u] += flux;
		}
		flux_max += flux;
		nr++;
	}
	//fluxul este maxim;

	bool color[MAX];
	memset(color, false, sizeof(color));
	dfs(rG, sursa, color);

	//afiseaza toate arcele ce au o extremitate accesibila din sursa si cealalta nu in reteaua initiala
	
	afisareTaietura(color);

	//return flux max
	return flux_max;
}

void citire() {
	int a, b, p, k;
	f >> n >> m >> sursa >> dest;
	for (k = 0; k < m; k++) {
		f >> a >> b >> p;
		c[a][b] = p;
	}
}

int main() {
	int flux_max;
	citire();
	cout << "Nr minim de arce ce trebuie eliminate:" << endl;
	flux_max = minCut(c, sursa, dest);
	cout << "Fluxul maxim " << flux_max << endl;
	cout << "Numarul de drumuri " << nr << endl;
	return 0;
}
