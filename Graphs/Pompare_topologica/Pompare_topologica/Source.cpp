#include <iostream>
#include <fstream>
#include <vector>
#include <list>
#include <algorithm>

using namespace std;

ifstream f("input.txt");
class Edge {
public:
	int start;
	int stop;
	int capacity;
	int flow = 0;
	Edge(int start, int stop, int c) :start{ start }, stop{ stop }, capacity{ c } {};
	bool operator == (const Edge& oth) {
		return stop == oth.stop;
	}
};

class Node {
public:
	int name;
	int h = 0;
	int e = 0;
	vector<Edge> v;
	Node(int n) :name{ n } {};
};

void citire(vector<Node>&gf) {
	int n;
	f >> n;
	for (int i = 0; i < n; gf.push_back(Node(i)), i++);
	int x, y, z;
	while (f >> x >> y >> z) {
		gf[x].v.push_back(Edge(x, y, z));
	}
}

void deleteE(vector<Node>& gf, Edge& e) {
	gf[e.start].v.erase(find(gf[e.start].v.begin(), gf[e.start].v.end(), e));
}
void updateE(vector<Node>& gf, Edge& e) {
	auto eaux = Edge(e.stop, e.start, e.flow);
	for (auto& edge : gf[e.stop].v) {
		if (edge == eaux) {
			edge.capacity += eaux.capacity;
			return;
		}
	}
	gf[e.stop].v.push_back(eaux);
}

void initilizare(vector<Node>& gf, int s, int t) {
	for (auto& edge : gf[s].v) {
		edge.flow = edge.capacity;
		gf[edge.stop].e += edge.flow;
		gf[edge.start].e -= edge.flow;
		updateE(gf, edge);
		deleteE(gf, edge);
		gf[s].h = gf.size();
	}
}

bool Pompare(vector<Node>& gf, Node& n) {
	if (n.e > 0) {
		for (auto& edge : n.v) {
			if (gf[edge.stop].h == n.h - 1) {
				auto m = min(n.e, edge.capacity - edge.flow);
				edge.flow += m;
				n.e -= m;
				gf[edge.stop].e += m;
				updateE(gf, edge);
				if (edge.flow == edge.capacity) {
					deleteE(gf, edge);
				}
				return true;
			}
		}
	}
	return false;
}
bool inaltare(vector<Node>& gf, Node& n, int t)
{
	if (n.name != t) {
		for (auto& edge : n.v)
			if (gf[edge.stop].e > 0 and gf[edge.stop].h > n.h)
				return false;
		n.h++;
		return true;
	}
	return false;
}

void discharge(vector<Node>& gf, Node& n, int t) {
	while (gf[n.name].e > 0 && (Pompare(gf, n) || inaltare(gf, n, t)));
}

int PompareTopologica(vector<Node>& gf, int s, int t) {
	initilizare(gf, s, t);
	list<int> n;
	for (auto& node : gf) {
		if (node.name != s && node.name != t)
			n.emplace_back(node.name);
	}
	auto iterator = begin(n);
	int nod;
	while (iterator != n.end()) {
		nod = *iterator;
		int h = gf[nod].h;
		discharge(gf, gf[nod], t);

		if (gf[nod].h > h) {
			n.erase(iterator);
			n.push_front(nod);
			iterator = begin(n);
		}
		advance(iterator, 1);
	}
	return gf[t].e;
}

int main() {
	vector<Node> gf;
	citire(gf);
	cout<<PompareTopologica(gf, 0, 5)<<endl;
	return 0;
	
}