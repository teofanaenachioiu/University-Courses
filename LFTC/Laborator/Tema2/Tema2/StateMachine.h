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
	bool finiteAutomata;
	State startState;
	vector<State> states;
	vector<State> usedStates;
	Alphabet alphabet;
	bool checkIfAlphabetContainsLetters(string str);
	bool finiteAutomate();
public:
	StateMachine();
	StateMachine(State stateStart, vector<State> states, Alphabet alphabet);
	bool checkInput(string input);
	vector<State> getStates();
	Alphabet getAlphabet();
	bool getFiniteAutomata();
	~StateMachine();
};

