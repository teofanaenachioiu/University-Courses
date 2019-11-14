#include "pch.h"
#include "AnalizatorLexical.h"
#include "Utils.h"
#include "Console.h"
#include <fstream>
#include <regex>

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
		codare.dataCod = keys[0];
		codare.cod = atoi(keys[1].c_str());
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

void AnalizatorLexical::analiza(string input,  string codeFile, string outputFip, string outputTsConst, string outputTsId)
{
	vector<string> operators = Utils::getStringsLine("operators.txt");
	vector<string> separators = Utils::getStringsLine("separators.txt");
	vector<string> keywords = Utils::getStringsLine("keywords.txt");

	//regex naturell_rx("[1-9][0/*-9]*");
	//regex naturellpoz_rx("(?:0)|[1-9][0-9]*");
	regex identificator_rx("[a-zA-Z_][a-zA-Z1-9_]{0,7}");
	regex string_const_rx("[\"].*[\"]");

	conIntregi.readFromFile("inputINT.txt");
	conIdentificatori.readFromFile("inputID.txt");

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
				if (conIdentificatori.checkSequence(atom)) {
					int codTs = AnalizatorLexical::pozitie(atom, &treeId);
					if (codTs == 0) {
						codTs = AnalizatorLexical::adaugaInTree(atom, &treeId);
					}
					AnalizatorLexical::generareFip(1, codTs, outputFip);
				}
				else
					//if (regex_match(atom, naturellpoz_rx) || regex_match(atom, string_const_rx)) {
					if (conIntregi.checkSequence(atom) || regex_match(atom, string_const_rx)) {
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


