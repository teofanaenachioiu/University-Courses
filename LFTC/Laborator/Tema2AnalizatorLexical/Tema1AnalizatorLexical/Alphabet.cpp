#include "pch.h"
#include "Alphabet.h"


Alphabet::Alphabet()
{
}


Alphabet::~Alphabet()
{
}

vector<string> Alphabet::getAlphabet()
{
	return this->alphabet;
}

void Alphabet::setAlphabet(vector<string> alphabet)
{
	this->alphabet = alphabet;
}

bool Alphabet::checkIfLetterIsInAlphabet(char ch)
{
	string s(1, ch);
	return find(this->alphabet.begin(), this->alphabet.end(),s)!= this->alphabet.end();
}
