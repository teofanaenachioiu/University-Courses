#include "pch.h"
#include "AnalizatorLexical.h"
#include "Utils.h"
#include "Console.h"
#include <fstream>
#include <regex>
#include <algorithm>

using namespace std;

void AnalizatorLexical::generareFip(int cod, int codTs, string outputFile)
{
	std::ofstream outfile;

	outfile.open(outputFile, std::ios_base::app);
	outfile << cod << " " << codTs << endl;
}

void AnalizatorLexical::clearFile(string fileName)
{
	std::ofstream outfile;
	outfile.open(fileName, std::ofstream::out | std::ofstream::trunc);
	outfile.close();
}

void AnalizatorLexical::writeTreeInFile(BST * tree, string fileName)
{
	ofstream myfile;
	myfile.open(fileName);

	tree->display(myfile);
	myfile.close();
}

void AnalizatorLexical::codificare(string fileName)
{
	vector<string> lines = Utils::getStringsLine(fileName);
	vector<string> keys;
	for (unsigned i = 0; i < lines.size(); i++) {
		keys = Utils::split(lines[i]);
		Codare codare = Codare();

		if (keys.size() == 2) {
			codare.dataCod = keys[0];
			codare.cod = atoi(keys[1].c_str());
		}
		else {
			codare.dataCod = lines[i][0];
			string restul = lines[i].substr(2);
			codare.cod = atoi(restul.c_str());
		}
		treeCode.insert(codare);
	}
}

int AnalizatorLexical::pozitie(string atom, BST* tree)
{
	return tree->find(atom);
}

int AnalizatorLexical::adaugaInTree(string atom, BST * tree)
{
	return tree->insert(atom);
}

AnalizatorLexical::AnalizatorLexical()
{
}


AnalizatorLexical::~AnalizatorLexical()
{
}

void AnalizatorLexical::analiza(string input, string codeFile, string outputFip, string outputTsConst, string outputTsId)
{
	vector<string> operators = Utils::getStringsLine("operators.txt");
	vector<string> separators = Utils::getStringsLine("separators.txt");
	vector<string> keywords = Utils::getStringsLine("keywords.txt");

	//regex naturell_rx("[1-9][0/*-9]*");
	//regex naturellpoz_rx("(?:0)|[1-9][0-9]*");
	//regex identificator_rx("[a-zA-Z_][a-zA-Z1-9_]{0,7}");
	//regex string_const_rx("[\"].*[\"]");

	AnalizatorLexical::codificare(codeFile);
	AnalizatorLexical::clearFile(outputFip);

	vector<string> lines = Utils::getStringsLine(input);
	vector<string> words;
	string atom;

	for (unsigned i = 0; i < lines.size(); i++) {
		words = Utils::split(lines[i]);
		for (unsigned j = 0; j < words.size(); j++) {
			atom = words[j];

			if (find(operators.begin(), operators.end(), atom) != operators.end() ||
				find(separators.begin(), separators.end(), atom) != separators.end() ||
				find(keywords.begin(), keywords.end(), atom) != keywords.end()) {

				int cod = AnalizatorLexical::pozitie(atom, &treeCode);
				AnalizatorLexical::generareFip(cod, 0, outputFip);
			}
			else
				//if (regex_match(atom, identificator_rx)) {
				if (automatID.checkSequence(atom)) {
					int codTs = AnalizatorLexical::pozitie(atom, &treeId);
					if (codTs == 0) {
						codTs = AnalizatorLexical::adaugaInTree(atom, &treeId);
					}
					AnalizatorLexical::generareFip(1, codTs, outputFip);
				}
				else
					//if (regex_match(atom, naturellpoz_rx) || regex_match(atom, string_const_rx)) {
					if (automatConstInt.checkSequence(atom) || automatConstString.checkSequence(atom)) {
						int codTs = AnalizatorLexical::pozitie(atom, &treeConst);
						if (codTs == 0) {
							codTs = AnalizatorLexical::adaugaInTree(atom, &treeConst);
						}
						AnalizatorLexical::generareFip(2, codTs, outputFip);
					}
					else {
						cout << "Eroare lexicala la linia " << i << " la atomul: " << atom << endl;
					}
		}
	}

	AnalizatorLexical::writeTreeInFile(&treeId, outputTsId);
	AnalizatorLexical::writeTreeInFile(&treeConst, outputTsConst);
}

void AnalizatorLexical::analizaLinie(string input, string codeFile, string outputFip, string outputTsConst, string outputTsId)
{
	vector<string> operators = Utils::getStringsLine("operators.txt");
	vector<string> separators = Utils::getStringsLine("separators.txt");
	vector<string> keywords = Utils::getStringsLine("keywords.txt");

	AnalizatorLexical::codificare(codeFile);
	AnalizatorLexical::clearFile(outputFip);

	vector<string> lines = Utils::getStringsLine(input);
	string line;
	string prefixID = "";
	string prefixINT = "";
	string prefixSTRING = "";
	string prefix = "";
	string prefixMore = "";

	for (unsigned i = 0; i < lines.size(); i++) {

		line = lines[i];
		while (line.size() > 0) {
			prefixID = automatID.findPrefix(line);
			prefixINT = automatConstInt.findPrefix(line);
			prefixSTRING = automatConstString.findPrefix(line);
			prefix = line[0];

			if (prefixID != "") {
				if (find(keywords.begin(), keywords.end(), prefixID) != keywords.end()) {

					cout << prefixID << " e cuvant rezervat" << endl;

					// scriere in fip
					int cod = AnalizatorLexical::pozitie(prefixID, &treeCode);
					AnalizatorLexical::generareFip(cod, 0, outputFip);
				}
				else {
					cout << prefixID << " e identificator" << endl;

					// scriere in tabela identificator (cod 1)
					int codTs = AnalizatorLexical::pozitie(prefixID, &treeId);
					if (codTs == 0) {
						codTs = AnalizatorLexical::adaugaInTree(prefixID, &treeId);
					}

					// scriere in fip
					AnalizatorLexical::generareFip(1, codTs, outputFip);
				}
				line = line.substr(prefixID.size());
			}
			else if (prefixSTRING != "") {
				cout << prefixSTRING << " e constanta string" << endl;
				line = line.substr(prefixSTRING.size());

				// scriere in tabela constante (cod 2)
				int codTs = AnalizatorLexical::pozitie(prefixSTRING, &treeConst);
				if (codTs == 0) {
					codTs = AnalizatorLexical::adaugaInTree(prefixSTRING, &treeConst);
				}

				// scriere in fip
				AnalizatorLexical::generareFip(2, codTs, outputFip);

			}
			else if (prefixINT != "") {
				cout << prefixINT << " e constanta intreaga" << endl;
				line = line.substr(prefixINT.size());

				// scriere in tabela constante (cod 2)
				int codTs = AnalizatorLexical::pozitie(prefixINT, &treeConst);
				if (codTs == 0) {
					codTs = AnalizatorLexical::adaugaInTree(prefixINT, &treeConst);
				}

				// scriere in fip
				AnalizatorLexical::generareFip(2, codTs, outputFip);
			}
			else if (find(separators.begin(), separators.end(), prefix) != separators.end()) {
				cout << prefix << " e separator" << endl;
				line = line.substr(1);

				// scriere in fip
				int cod = AnalizatorLexical::pozitie(prefix, &treeCode);
				AnalizatorLexical::generareFip(cod, 0, outputFip);
			}
			else {
				int index = 1;
				prefixMore = prefix;
				while (find(operators.begin(), operators.end(), prefixMore) != operators.end()) {
					prefixMore += line[index];
					index++;
				}
				if (find(operators.begin(), operators.end(), prefix) != operators.end()) {
					prefix = prefixMore.substr(0,prefixMore.size()-1);
					cout << prefix << " e operator" << endl;

					// scriere in fip
					int cod = AnalizatorLexical::pozitie(prefix, &treeCode);
					AnalizatorLexical::generareFip(cod, 0, outputFip);
				}
				else {
					cout << "Eroare lexicala la linia " << i << " caracter " << prefix << endl;
				}
				line = line.substr(prefix.size());
			}

		}
	}

	AnalizatorLexical::writeTreeInFile(&treeId, outputTsId);
	AnalizatorLexical::writeTreeInFile(&treeConst, outputTsConst);
}

