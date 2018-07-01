#include<iostream>
#include<fstream>
#include<vector>
#include<queue>
#include<algorithm>
using namespace std;
ifstream f("input.txt");

//citire
void citire(vector<pair<int, int>>& muchie, int& start) {
	int ex1, ex2;
	f >> start;
	while (!f.eof()) {
		f >> ex1 >> ex2;
		muchie.push_back(make_pair(ex1, ex2));
	}
	f.close();
}

//det grade
vector<int> grade(vector<pair<int, int>>& muchie) {
	vector<int> degree;
	degree.assign(muchie.size()+ 1, 0);
	for (int i = 0; i < muchie.size(); i++) {
		degree[muchie[i].first - 1] += 1;
		degree[muchie[i].second - 1] += 1;
	}
	return degree;
}

//creare coada
struct compara {
	bool operator() (pair<int, int>& lhs, pair<int, int>&rhs) {
		return lhs > rhs;
	}
};

priority_queue<pair<int, int>, vector<pair<int, int>>, compara> creare_coada(vector<int>&d,int start) {
	priority_queue<pair<int, int>, vector<pair<int, int>>, compara> q;
	for (int i = 0; i < d.size(); i++)
		if (d[i] != 0 && i+1 != start)
			q.push(make_pair(d[i],i+1));
	return q;
}

//cauta tata
int tatal(vector<pair<int, int>>& muchie,int& son) {
	for (int i = 0; i < muchie.size(); i++)
		if (muchie[i].second == son)
			return muchie[i].first;
}

queue<int> Codare_Prufer(vector<pair<int, int>>& muchie, int start) {
	queue<int> k;
	vector<int> d = grade(muchie);
	int son, father;
	priority_queue<pair<int, int>, vector<pair<int, int>>, compara> q = creare_coada(d, start);
	while (!q.empty()) {
		son = q.top().second;
		father = tatal(muchie, son);
		k.push(father);
		d[son - 1]-=1;
		d[father - 1]-=1;
		q = creare_coada(d, start);
	}
	return k;
}

int minim(queue <int>k,int n) {
	int min = 1;
	vector<int> copy;
	while (!k.empty()) {
		copy.push_back(k.front());
		k.pop();
	}
	while (min<=n)	{
		auto it = find(copy.begin(), copy.end(), min);
		if (it == copy.end()) return min;
		else min++;
	}
}

vector<pair<int,int>> Decodare_Prufer(queue <int>&k,int& n) {
	int x, y;
	vector<pair<int, int>> rez;
	for (int i = 1; i < n;i++) {
		x = k.front();
		y = minim(k, n);
		k.pop();
		k.push(y);
		rez.push_back(make_pair(x, y));
	}
	return rez;
}

int main() {
	vector<pair<int, int>> muchie;
	int start;
	int n;
	citire(muchie, start);
	n = muchie.size() + 1;
	queue<int> k=Codare_Prufer(muchie, start);

	vector<pair<int, int>> d= Decodare_Prufer(k, n);
	for (int i = 0; i < d.size(); i++)
		cout << d[i].first << " " << d[i].second << endl;
	return 0;
}