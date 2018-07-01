#include<iostream>
#include<vector>
#include<stack>
#include<unordered_map>
using namespace std;

void printCircuit(vector<vector<int>> adj) {
	unordered_map<int, int> grade;
	vector<int> circuit;
	stack<int> curent_path;
	int vf_curent, vf_urm;
	for (int i = 0; i < adj.size(); i++)
		grade[i] = adj[i].size();

	vf_curent = 0;
	curent_path.push(0);
	while (!curent_path.empty()) {
		if (grade[vf_curent]) {
			curent_path.push(vf_curent);
			vf_urm = adj[vf_curent].back();
			grade[vf_curent] -= 1;
			adj[vf_curent].pop_back();
			vf_curent = vf_urm;
		}
		else {
			circuit.push_back(vf_curent);
			vf_curent = curent_path.top();
			curent_path.pop();
		}
	}
	for (int i = circuit.size() - 1; i >= 0; i--)
		cout << circuit[i] << " ";
	cout << endl;
}

int main() {
	vector< vector<int> > adj1, adj2;

	// Input Graph 1
	adj1.resize(3);

	// Build the edges
	adj1[0].push_back(1);
	adj1[1].push_back(2);
	adj1[2].push_back(0);
	printCircuit(adj1);
	cout << endl;

	// Input Graph 2
	adj2.resize(7);
	adj2[0].push_back(1);
	adj2[0].push_back(6);
	adj2[1].push_back(2);
	adj2[2].push_back(0);
	adj2[2].push_back(3);
	adj2[3].push_back(4);
	adj2[4].push_back(2);
	adj2[4].push_back(5);
	adj2[5].push_back(0);
	adj2[6].push_back(4);
	printCircuit(adj2);

	return 0;
}