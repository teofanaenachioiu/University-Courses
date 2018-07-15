#include <iostream>
#include <string>
#include <queue>
#include <fstream>
using namespace std;
ifstream f;
ofstream g;

struct Nod {
	char caracter;
	unsigned frecventa;
	Nod *left, *right;
	Nod(char data, unsigned freq){
		left = right = NULL;
		this->caracter = data;
		this->frecventa = freq;
	}
};

//cauta un caracter in vector
int cauta(vector <pair <char, int>>& vector, char& c) {
	for (unsigned i = 0; i < vector.size(); i++)
		if (c == vector.at(i).first)
			return i;
	return -1;
}

//functia de citire
void citire(vector <pair <char, int>>& vector) {
	f.open("input_file.txt");
	char c;
	int poz;
	int dim;
	string line;
	while (!f.eof()) {
		getline(f, line);
		while (line.size() != 0) {
			c = line.at(0);
			poz = cauta(vector, c);
			if (poz == -1) vector.push_back(make_pair(c, 1));
			else {
				dim = vector.at(poz).second + 1;
				vector.erase(vector.begin() + poz);
				vector.push_back(make_pair(c, dim));
			}
			line.erase(line.begin());
		}
	}
	f.close();
}

//Functia de comparare nod
struct compare {
	bool operator()(Nod* l, Nod* r) {
		return (l->frecventa > r->frecventa);
	}
};

//Codificare Huffman
priority_queue<Nod*, vector<Nod*>, compare>  HuffmanCodes(vector <pair <char, int>>& vect){
	struct Nod *left, *right, *top;
	priority_queue<Nod*, vector<Nod*>, compare> coada;
	for (unsigned i = 0; i < vect.size(); ++i)
		coada.push(new Nod(vect[i].first, vect[i].second));
	while (coada.size() != 1) {
		left = coada.top();
		coada.pop();

		right = coada.top();
		coada.pop();

		top = new Nod('^', left->frecventa + right->frecventa);
		top->left = left;
		top->right = right;
		coada.push(top);
	}
	return coada;
}


// Afiseaza codificarea Huffman
void printCodes(vector<pair<char, string>>&cod, struct Nod* root, string str) {
	if (root==NULL)
		return;
	if (root->caracter != '^') {

		cout << root->caracter << ": " << str << "\n";
		cod.push_back(make_pair(root->caracter, str));

	}
	printCodes(cod, root->left, str + "0");
	printCodes(cod, root->right, str + "1");
}

void printInFile(vector<pair<char, string>>&cod) {
	f.open("input_file.txt");
	g.open("input_file_huffman.txt");
	char c;
	string line;
	while (!f.eof()) {
		getline(f, line);
		while (line.size() != 0) {
			c = line.at(0);
			auto it = find_if(cod.begin(), cod.end(), [&c](pair<char, string> arg) {return c == arg.first; });
			g << (*it).second;
			line.erase(line.begin());
		}
	}
	g.close();
	f.close();
}

int main() {
	vector <pair <char, int>> v;
	vector<pair<char, string>>cod;
	priority_queue<Nod*, vector<Nod*>, compare> h;
	citire(v);
	h=HuffmanCodes(v);
	
	printCodes(cod,h.top(), "");
	printInFile(cod);
	return 0;
}