#include<iostream>
#include<fstream>
#include<vector>
#include<utility>
#include<algorithm>
using namespace std;
ifstream f("input.txt");

typedef struct {
	int extr1, extr2, w;
}varf;

void citire(vector<varf>& muchii, int &n) {
	varf v;
	int m;
	f >> n>>m;
	while (!f.eof()) {
		f >> v.extr1 >> v.extr2 >> v.w;
		muchii.push_back(v);
	}
	f.close();
}


vector<pair<int, int>> Kruskal(vector<varf>& muchii, int &n) {
	vector<pair<int, int>> set,rez;
	//initiem setul
	for (int i = 0; i < n; i++)
		set.push_back(make_pair(set.size() + 1, i));
	//sortam muchiile dupa ponderi
	sort(muchii.begin(), muchii.end(), [](varf &m1, varf &m2) {return m1.w < m2.w; });
	for (auto m : muchii) {
		auto it1 = find_if(set.begin(), set.end(), [&m](pair<int,int>& c) {return c.second == m.extr1; });
		auto it2 = find_if(set.begin(), set.end(), [&m](pair<int,int> &c) {return c.second == m.extr2; });
		if ((*it1).first != (*it2).first) {
			rez.push_back(make_pair(m.extr1,m.extr2));
			(*it2) = make_pair((*it1).first, (*it2).second);
		}
	}
	return rez;
}

void afisare(vector<pair<int, int>>& K) {
	for (int i = 0; i < K.size(); i++)
		cout << "(" << K[i].first << "," << K[i].second << ") ";
	cout << endl;
}

int main() {
	vector<varf> muchii;
	vector<pair<int, int>> K;
	int n;
	citire(muchii, n);
	K = Kruskal(muchii, n);
	afisare(K);
	return 0;
}