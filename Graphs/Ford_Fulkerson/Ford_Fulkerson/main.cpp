#include<iostream>
#include<fstream>
#include<queue>
#define MAX 100
using namespace std;
int n, m, sursa, dest, c[MAX][MAX];// c=matricea capacitaii
ifstream f("input.txt");

int bfs(int  rG[MAX][MAX], int sursa, int dest, int prev[MAX]) {
	bool visit[MAX];//vizitat nevizitat
	memset(visit, false, sizeof(visit));
	queue<int> q;
	q.push(sursa);
	visit[sursa];
	while (!q.empty()){
		int u = q.front();
		q.pop();
		for (int v = 0; v < n; v++) {
			if (visit[v] == false && rG[u][v] > 0) {
				q.push(v);
				prev[v] = u;
				visit[v] = true;
			}
		}
	}
	return visit[dest] == true;
}

int min(int a, int b) {
	if (a < b) return a;
	return b;
}

int minCut(int c[MAX][MAX],int sursa,int dest) {
	int u, v, flux_max = 0, rG[MAX][MAX],prev[MAX];

	//copiez matricea de capacitati
	for (u = 0; u < n; u++)
		for (v = 0; v < n; v++)
			rG[u][v] = c[u][v];

	//cat timp exsita cale reziduala
	while (bfs(rG, sursa, dest, prev)) {
		int flux = INT_MAX;

		//determinam fluxul pe drumul gasit
		for (v = dest; v != sursa; v = prev[v]) {
			u = prev[v];
			flux = min(flux, rG[u][v]);
		}

		//stabimil cat ducem, cat primim
		for (v = dest; v != sursa; v = prev[v]) {
			u = prev[v];
			rG[u][v] -= flux;//de la u la v scadem
			rG[v][u] += flux;//ducem inapoi (adunam)
		}

		flux_max += flux;
	}
	//fluxul este maxim;
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
	citire();
	cout << "Fluxul maxim " << minCut(c, sursa, dest) << endl;
	return 0;
}