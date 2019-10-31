#pragma once
#include <string>
#include <map>

using namespace std;

class State
{
private:
	string name;
	multimap<string, string> transitions;
	bool isFinal =false;
public:
	State();
	State(string name, multimap<string, string> transitions);
	State(string name);

	void setIsFinal(bool isFinal);
	bool getIsFinal();

	string getName();

	void setTransitions(multimap<string, string> transitions);
	multimap<string, string> getTransitions();
	void addTransition(string symbol, string state);

	bool operator ==(const State &d) const {
		if (name == d.name) {
			return true;
		}

		return false;
	}

	bool operator <(const State &d) const {
		if (name < d.name) {
			return true;
		}

		return false;
	}

	bool operator >(const State &d) const{
		if (this->name > d.name) {
			return true;
		}

		return false;
	}

	~State();
};

