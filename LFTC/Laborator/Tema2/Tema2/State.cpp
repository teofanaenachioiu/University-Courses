#include "pch.h"
#include "State.h"


State::State()
{
}

State::State(string name, multimap<string, string> transitions)
{
	this->name = name;
	this->transitions = transitions;
	this->isFinal = false;
}

State::State(string name)
{
	this->name = name;
}

void State::setIsFinal(bool isFinal)
{
	this->isFinal = isFinal;
}

bool State::getIsFinal()
{
	return this->isFinal;
}

string State::getName()
{
	return name;
}

void State::setTransitions(multimap<string, string> transitions)
{
	this->transitions = transitions;
}

multimap<string, string> State::getTransitions()
{
	return this->transitions;
}

void State::addTransition(string symbol, string state)
{
	this->transitions.insert(pair<string, string>(symbol, state));
}



State::~State()
{
}
