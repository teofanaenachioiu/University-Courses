#include "pch.h"
#include "Codare.h"
#include <iostream>
#include <string>
using namespace std;

Codare::Codare()
{
}


Codare::~Codare()
{
}

ostream & operator<<(ostream & os, const Codare & dt)
{
	os << dt.dataCod << " " << dt.cod<<endl;
	return os;
}