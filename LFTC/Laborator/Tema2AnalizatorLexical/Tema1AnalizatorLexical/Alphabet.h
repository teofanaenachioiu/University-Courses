#pragma once
#include <vector>
#include <string>
#include <algorithm>

using namespace std;

class Alphabet
{
private:
	vector<string> alphabet;
public:
	Alphabet();
	~Alphabet();

	vector<string> getAlphabet();
	void setAlphabet(vector<string> alphabet);

	bool checkIfLetterIsInAlphabet(char ch);
};

