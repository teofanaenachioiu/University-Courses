#pragma once
#include "State.h"
#include "Alphabet.h"
#include<string>
#include<set>
#include<vector>

using namespace std;

class StateMachine
{
private:
	string filename;

	State initialState;
	vector<State> states = vector<State>();
	Alphabet alphabet = Alphabet();
	bool finiteAutomata;

	bool checkFiniteAutomata();

	// settere
	void setInitialState(string line);
	void setStates(string line);
	void setFinalStates(string line);
	void setTransitions(string line);
	void setStateTransition(string line);
	void setAlphabet(string line);
	
	void readFromFile();

	bool checkIfAlphabetContainsLetters(string str);

public:
	StateMachine(string filename);
	bool checkSequence(string input);
	string findPrefix(string input);
	~StateMachine();
};

