#pragma once
#include "StateMachine.h"
class Console
{
private:
	StateMachine stateMachine;
	State initialState;
	void startSubmenu();
	void printMenu();
	void printMainMenu();
	void readFromKeyboard();
	void printStates();
	void printAlphabet();
	void printTransitions();
	void printFinalStates();
	int getInitialState(string line, vector<State> & states);
	void setFinalStates(string line, vector<State> &states, Alphabet alphabet);
	void setTransitions(string line, vector<State> &states, Alphabet alphabet);
	void setStateTransition(string line, vector<State> &states, Alphabet alphabet);
	vector<State> getStatesFromLine(string line);
	Alphabet getAlphabetFromLine(string line);
	void checkSequence();
	void findPrefix();
public:
	Console();
	~Console();
	void readFromFile(string filename);
	bool checkSequence(string input);
	
};

