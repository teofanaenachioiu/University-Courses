#include "pch.h"
#include "StateMachine.h"
#include<vector>

using namespace std;

bool StateMachine::checkIfAlphabetContainsLetters(string str)
{
	for (int i = 0; i < str.size(); i++) {
		char ch = str.at(i);
		if (!alphabet.checkIfLetterIsInAlphabet(ch)) {
			return false;
		}
	}
	return true;
}

StateMachine::StateMachine()
{
}

StateMachine::StateMachine(State stateStart, vector<State> states, Alphabet alphabet)
{
	this->startState = stateStart;
	this->states = states;
	this->usedStates = vector<State>();
	this->alphabet = alphabet;
	this->finiteAutomata = finiteAutomate();
}


bool StateMachine::checkInput(string sequence)
{
	if (sequence=="" && startState.getIsFinal()) {
		return true;
	}

	if (checkIfAlphabetContainsLetters(sequence)) {
		State s = startState;
		string letter;
		State next;
		bool found;
		for (int i = 0; i < sequence.size(); i++) {
			letter = sequence.at(i);
			found = false;
			auto tr = s.getTransitions();
			for (auto it = tr.begin(); it != tr.end(); ++it) {
				if ((*it).first == letter) {
					next = (*it).second;
					found = true;
					auto iter = find(states.begin(), states.end(), next);

					s = (*iter);

					if (s.getIsFinal() && i == sequence.size() - 1) {
						return true;
					}
				}
			}
			if (!found) {
				return false;
			}
		}
	}
	return false;
}

vector<State> StateMachine::getStates()
{
	return this->states;
}

Alphabet StateMachine::getAlphabet()
{
	return this->alphabet;
}

bool StateMachine::getFiniteAutomata()
{
	return this->finiteAutomata;
}

bool StateMachine::finiteAutomate()
{
	for (int i = 0; i < states.size(); i++) {
		auto tr = states[i].getTransitions();
		
		for (auto it = tr.begin(); it != tr.end(); ++it) {
			auto curent = *it;
			if (tr.count(curent.first) > 1) {
				return false;
			}
		}
	}
	return true;
}

StateMachine::~StateMachine()
{
}
