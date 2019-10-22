#pragma once
#include <string>
using namespace std;
class Codare
{
public:
	Codare();
	~Codare();
	int cod;
	string dataCod;
	bool operator <(const Codare& d) {
		if (dataCod < d.dataCod) {
			return true;
		}
		return false;
	}
	bool operator >(const Codare& d) {
		if (dataCod > d.dataCod) {
			return true;
		}
		return false;
	}
	bool operator ==(const Codare& d) {
		if (dataCod == d.dataCod) {
			return true;
		}
		return false;
	}
	friend ostream& operator<<(ostream& os, const Codare& dt);
};

