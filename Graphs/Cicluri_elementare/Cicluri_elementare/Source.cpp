#include<iostream>
#include<fstream>
#include<vector>
using namespace std;
ifstream f("input1.txt");


void init(vector<vector<int>>& matrice, int n) {
	vector<int> aux;
	aux.assign(n+2, 0);
	for (int i = 0; i <=n; i++)
		matrice.push_back(aux);
}

void citire(vector<vector<int>> &adiac) {
	int n, m, extr1, extr2;
	f >> n >> m;
	init(adiac, n);
	while (!f.eof()) {
		f >> extr1 >> extr2;
		adiac[extr1][extr2] = 1;
		adiac[extr2 ][extr1] = 1;
	}
	f.close();
}

bool valid(vector<vector<int>> &adiac,vector<int>& solutie, int k) {
	if (solutie[0] == solutie[1]) return false;
	for (int i = 1; i < k; i++)
		if (solutie[k] == solutie[i])
			return false;
	if (k > 1) {
		if (adiac[solutie[k]-1][solutie[k-1]-1] == 0)
			return false;
	}
	return true;
}

bool solutieee(vector<int>& solutie,int k) {
	if (k >=2)
	{
		if (solutie[0] == solutie[k])
			return true;
		else return false;
	}
	return false;
}

void tiparire(vector<int>& solutie,int k) {
	for (int i=0;i<=k;i++)
		cout <<solutie[i] << " ";
	cout << endl;
}


void bkt(vector<vector<int>>& adiac,vector<int>& solutie,int k) {
	for (int varf = 1; varf <=adiac.size(); varf++) {
		solutie[k]=varf;
		if (valid(adiac,solutie,k))
			if (solutieee(solutie,k))
				tiparire(solutie,k);
			else { bkt(adiac, solutie, k+1); }
	}
}


int main() {
	vector<vector<int>> adiac;
	vector<int> solutie;
	citire(adiac);
	solutie.assign(adiac.size()+3, -1);
	bkt(adiac, solutie,0);
	return 0;
}
