// Tema1AnalizatorLexical.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include "Utils.h"
#include <iostream>
#include <string>
#include <vector>
#include <regex>
#include <sstream>

using namespace std;

vector<string> split(string const &input) {
	istringstream buffer(input);
	vector<string> ret((istream_iterator<string>(buffer)),
		istream_iterator<string>());
	return ret;
}

int main()
{
	regex integerr("(\\+|-)?[[:digit:]]+");
	regex naturell("[1-9][0/*-9]*");
	regex naturellpoz("(?:0)|[1-9][0-9]*");
	regex identificator_rx("[a-za-z_][a-za-z1-9_]{0,7}");

	vector<string> operators = Utils::getStringsLine("operators.txt");
	vector<string> separators = Utils::getStringsLine("separators.txt");
	vector<string> keywords = Utils::getStringsLine("keywords.txt");

	//if (regex_match("_a", identificator_rx))
	//	cout << "valid" << endl;
	//else
	//{
	//	cout << "Invalid input" << endl;
	//}


	string text = "Let me   split this into words";
	auto rez = split(text);
	for (int i = 0; i < rez.size(); i++) {
		cout << rez[i] << "*"<<endl;
	}
}