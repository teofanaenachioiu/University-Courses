#include "pch.h"
#include "Utils.h"
#include "StateMachine.h"
#include<vector>
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <regex>

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

bool StateMachine::checkFiniteAutomata()
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


StateMachine::StateMachine(string filename)
{
	this->filename = filename;
	readFromFile();
	this->finiteAutomata = checkFiniteAutomata();
}


void StateMachine::readFromFile()
{
	ifstream file;
	string line;

	file.open(this->filename);

	while (getline(file, line)) {
		//cout << line << endl;

		switch (line[0]) {
		case 'I': {
			setInitialState(line.substr(3, line.size()));
			break;
		}
		case 'Q': {
			setStates(line.substr(2, line.size()));
			break;
		}
		case 'F': {
			setFinalStates(line.substr(2, line.size()));
			break;
		}
		case 'A': {
			setAlphabet(line.substr(2, line.size()));
			break;
		}
		case 'T': {
			setTransitions(line.substr(2, line.size()));
			break;
		}
		default: {
			cout << "Invalid input" << endl;
			break;
		}
		}
	}
	file.close();
}


void StateMachine::setStates(string line)
{
	vector<string> statesName = Utils::split(line);

	for (int i = 0; i < statesName.size(); i++) {
		this->states.push_back(State(statesName[i]));
	}
}

void StateMachine::setAlphabet(string line)
{
	vector<string> alpha = Utils::split(line);
	this->alphabet.setAlphabet(alpha);

}

void StateMachine::setTransitions(string line)
{
	size_t pos = 0;
	string token, delimiter = ";";

	while ((pos = line.find(delimiter)) != std::string::npos) {
		token = line.substr(0, pos);
		setStateTransition(token);
		line.erase(0, pos + delimiter.length());
	}
	setStateTransition(line);

}

void StateMachine::setStateTransition(string line)
{
	vector<string> transition = Utils::split(line);

	char ch = transition[1][0];

	if (!alphabet.checkIfLetterIsInAlphabet(ch)) {
		cout << "Wrong symbol *" << ch << "*" << endl;
		return;
	}

	auto itDestination = find(states.begin(), states.end(), State(transition[2]));
	if (itDestination == states.end()) {
		cout << "Wrong state *" << transition[2] << "*" << endl;
		return;
	}

	//State destination = *itDestination;

	auto itSource = find(states.begin(), states.end(), State(transition[0]));
	if (itDestination == states.end()) {
		cout << "Wrong states *" << transition[0] << "*" << endl;
		return;
	}

	State source = *itSource;

	int index = std::distance(states.begin(), itSource);
	//int indexDest = std::distance(states.begin(), itDestination);

	states[index].addTransition(transition[1], transition[2]);
}

void StateMachine::setFinalStates(string line)
{
	vector<string> finalStates = Utils::split(line);

	for (int i = 0; i < finalStates.size(); i++) {
		auto it = find(states.begin(), states.end(), State(finalStates[i]));
		if (it != states.end()) {
			int index = distance(states.begin(), it);
			states[index].setIsFinal(true);
		}
	}
}

void StateMachine::setInitialState(string line)
{
	auto it = find(states.begin(), states.end(), State(line));

	if (it != states.end()) {
		int index = distance(states.begin(), it);
		this->initialState = this->states[index];
	}

	this->initialState = this->states[0];
}


bool StateMachine::checkSequence(string sequence)
{
	if (this->finiteAutomata) {
		if (sequence == "" && initialState.getIsFinal()) {
			return true;
		}
		if (checkIfAlphabetContainsLetters(sequence)) {
			State s = initialState;
			string letter;
			string nextStateName;
			bool found;
			for (int i = 0; i < sequence.size(); i++) {
				letter = sequence.at(i);
				found = false;
				auto tr = s.getTransitions();
				for (auto it = tr.begin(); it != tr.end(); ++it) {
					if ((*it).first == letter) {
						nextStateName = (*it).second;
						found = true;
						auto iter = find(states.begin(), states.end(), State(nextStateName));

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
	}
	return false;
}

string StateMachine::findPrefix(string input)
{
	if (this->finiteAutomata) {
		string secventa = input;

		for (int i = secventa.length() - 1; i >= 0; i--) {
			bool rez = this->checkSequence(secventa);
			if (rez == true) {
				return secventa;
			}
			secventa = secventa.substr(0, i);
		}
		return  "";
	}
	else {
		return "";
	}
}


StateMachine::~StateMachine()
{
}
