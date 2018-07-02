#include "validator.h"
//functie de validare pentru o masina


void MasinaValidator::validate(const Masina& m) {
	Vector <string> msgs;
	bool ok = false;
	if (m.getNrInmatr().size() != 7) msgs.add("Numar de inmatriculare invalid!!! Respectati sablonul: <LLCCLLL>");
	if (m.getNrInmatr().size() == 7) {
		int i = 0;
		for (auto c : m.getNrInmatr()) {
			if (i <= 1 || i >= 4)
				if (!(c >= 'A'&& c <= 'Z' || c >= 'a' && c <= 'z')) ok = true;
			if (i == 3 || i == 2)
				if (!(c >= '0'&& c <= '9')) ok = true;
			i++;
		}
		if (ok == true) msgs.add("Numar de inmatriculare invalid!!! Respectati sablonul: <LLCCLLL>");
	}
	if (m.getTip().size() == 0) msgs.add("Tip vid!!!");
	if (m.getProducator().size() == 0) msgs.add("Producator vid!!!");
	if (m.getModel().size() == 0) msgs.add("Model vid!!!");
	if (msgs.size() > 0) {
		throw ValidateException(msgs);
	}
}

ostream& operator<<(ostream& out, const ValidateException& ex) {
	for (const auto& mesaj : ex.mesaje) {
		out << mesaj << " ";
	}
	return out;
}