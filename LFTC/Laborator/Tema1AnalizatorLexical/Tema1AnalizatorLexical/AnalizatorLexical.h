#pragma once
#include <string>
#include "BST.h"
#include "Codare.h"
#include "Console.h"
using namespace std;
class AnalizatorLexical
{

public:
	AnalizatorLexical();
	~AnalizatorLexical();
	void analiza(string input, string codeFile, string outputFip, string outputTsConst, string outputTsId);

private:
	Console conIntregi = Console();
	Console conIdentificatori = Console();
	BST treeConst = BST();
	BST treeId = BST();
	BST treeCode = BST();
	void generareFip(int cod, int codTs, string outputFile);
	void clearFile(string fileName);
	void writeTreeInFile(BST* tree, string fileName);
	void codificare(string fileName);
	int pozitie(string atom, BST* tree);
	int adaugaInTree(string atom, BST* tree);
};


