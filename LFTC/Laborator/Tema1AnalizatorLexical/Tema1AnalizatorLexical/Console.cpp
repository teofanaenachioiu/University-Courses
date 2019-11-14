#include "pch.h"
#include "Console.h"
#include "Utils.h"
#include <iostream>
#include <fstream>
#include <string>

using namespace std;

Console::Console()
{
}


Console::~Console()
{
}


void Console::printMainMenu()
{
	cout << "Read data from: " << endl;
	cout << "1: File" << endl;
	cout << "2: Keyboard" << endl;
	cout << "Press 0 to close" << endl;
}




void Console::printMenu()
{
	cout << "Show: " << endl;
	cout << "1: States" << endl;
	cout << "2: Alphabet" << endl;
	cout << "3: Transitions" << endl;
	cout << "4: Final states" << endl;
	cout << "5: Check sequence" << endl;
	cout << "6: Find prefix" << endl;
	cout << "Press 0 to close" << endl;
}


void Console::startSubmenu()
{
	string command;
	while (true)
	{
		printMenu();
		getline(cin, command);
		if (command == "1") {
			cout << "STATES" << endl;
			printStates();
			continue;
		}
		else if (command == "2") {
			cout << "ALPHABET" << endl;
			printAlphabet();
			continue;
		}
		else if (command == "3") {
			cout << "TRANSITIONS" << endl;
			printTransitions();
			continue;
		}
		else if (command == "4") {
			cout << "FINAL STATES" << endl;
			printFinalStates();
			continue;
		}
		else if (command == "5") {
			cout << "CHECK SEQUENCE" << endl;
			checkSequence();
			continue;
		}
		else if (command == "6") {
			cout << "FIND PREFIX" << endl;
			findPrefix();
			continue;
		}
		else if (command == "0") {
			cout << "EXIT" << endl;
			break;
		}
		else {
			cout << "Wrong command" << endl;
		}
	}
}


void Console::readFromKeyboard()
{
	string linie;

	cout << "States: " << endl; getline(cin, linie);
	vector<State> states = getStatesFromLine(linie);

	cout << "Alphabet: " << endl; getline(cin, linie);
	Alphabet alphabet = getAlphabetFromLine(linie);

	cout << "Transitions: " << endl; getline(cin, linie);
	setTransitions(linie, states, alphabet);

	cout << "Initial state: " << endl; getline(cin, linie);
	State stateInitial = states[getInitialState(linie, states)];

	cout << "Final states:" << endl; getline(cin, linie);
	setFinalStates(linie, states, alphabet);

	stateMachine = StateMachine(stateInitial, states, alphabet);
}

void Console::readFromFile(string filename)
{
	ifstream file;
	string line;

	file.open(filename);

	vector<State>  states;
	Alphabet alphabet;
	State stateInitial;

	while (getline(file, line)) {
		cout << line << endl;

		switch (line[0]) {
		case 'I': {
			stateInitial = states[getInitialState(line.substr(3, line.size()), states)];
			break;
		}
		case 'Q': {
			states = getStatesFromLine(line.substr(2, line.size()));
			break;
		}
		case 'F': {
			setFinalStates(line.substr(2, line.size()), states, alphabet);
			break;
		}
		case 'A': {
			alphabet = getAlphabetFromLine(line.substr(2, line.size()));
			break;
		}
		case 'T': {
			setTransitions(line.substr(2, line.size()), states, alphabet);
			break;
		}
		default: {
			cout << "Invalid input" << endl;
			break;
		}
		}
	}
	file.close();
	stateMachine = StateMachine(stateInitial, states, alphabet);
}


void Console::printStates()
{
	vector<State> states = stateMachine.getStates();
	State currentState;
	for (auto it = states.begin(); it != states.end(); ++it) {
		currentState = *it;
		cout << currentState.getName() << " ";
	}
	cout << endl;
}

void Console::printAlphabet()
{
	Alphabet alphabet = stateMachine.getAlphabet();
	vector<string> letters = alphabet.getAlphabet();

	for (int i = 0; i < letters.size(); i++) {
		cout << letters[i] << " ";
	}
	cout << endl;
}

void Console::printTransitions()
{
	vector<State> states = stateMachine.getStates();
	State currentState;
	multimap<string, string> trans;
	for (auto it = states.begin(); it != states.end(); ++it) {
		currentState = *it;
		trans = currentState.getTransitions();

		for (auto itTrans = trans.begin(); itTrans != trans.end(); ++itTrans) {
			cout << currentState.getName() << "--" << (*itTrans).first << "--" << (*itTrans).second << endl;
		}
	}
	cout << endl;
}

void Console::printFinalStates()
{
	vector<State> states = stateMachine.getStates();
	State currentState;
	multimap<string, State> trans;
	for (auto it = states.begin(); it != states.end(); ++it) {
		currentState = *it;
		if (currentState.getIsFinal()) {
			cout << currentState.getName() << " ";
		}
	}
	cout << endl;
}

void Console::checkSequence()
{
	string linie;

	cout << "Sequence: " << endl; getline(cin, linie);

	if (stateMachine.getFiniteAutomata()) {
		bool rez = stateMachine.checkInput(linie);
		if (rez == true) {
			cout << "Secventa acceptata" << endl;
		}
		else {
			cout << "Secventa nu e acceptata" << endl;
		}
	}
	else {
		cout << "Automatul nu e finit" << endl;
	}
}

bool Console::checkSequence(string input)
{
	if (stateMachine.getFiniteAutomata()) {
		bool rez = stateMachine.checkInput(input);
		return rez;
	}
	return false;
}

void Console::findPrefix()
{
	string linie;

	cout << "Sequence: " << endl; getline(cin, linie);

	if (stateMachine.getFiniteAutomata()) {
		string secventa = linie;
		for (int i = secventa.length() - 1; i >= 0; i--) {
			bool rez = stateMachine.checkInput(secventa);
			if (rez == true) {
				cout << "Prefix: " << secventa << endl;
				return;
			}
			secventa = secventa.substr(0, i);
		}
		cout << "Nu exista prefix" << endl;
	}
	else {
		cout << "Automatul nu e finit" << endl;
	}
}

int Console::getInitialState(string line, vector<State> & states)
{
	auto it = find(states.begin(), states.end(), State(line));
	if (it != states.end()) {
		int index = distance(states.begin(), it);
		return index;
	}

	return 0;
}

void Console::setFinalStates(string line, vector<State>& states, Alphabet alphabet)
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

void Console::setTransitions(string line, vector<State>& states, Alphabet alphabet)
{
	size_t pos = 0;
	string token, delimiter = ";";

	while ((pos = line.find(delimiter)) != std::string::npos) {
		token = line.substr(0, pos);
		setStateTransition(token, states, alphabet);
		line.erase(0, pos + delimiter.length());
	}
	setStateTransition(line, states, alphabet);

}

void Console::setStateTransition(string line, vector<State>& states, Alphabet alphabet)
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

vector<State> Console::getStatesFromLine(string line)
{
	vector<State> states = vector<State>();

	vector<string> statesName = Utils::split(line);

	for (int i = 0; i < statesName.size(); i++) {
		states.push_back(State(statesName[i]));
	}

	return states;
}

Alphabet Console::getAlphabetFromLine(string line)
{
	Alphabet alphabet = Alphabet();

	vector<string> alpha = Utils::split(line);
	alphabet.setAlphabet(alpha);

	return alphabet;
}
