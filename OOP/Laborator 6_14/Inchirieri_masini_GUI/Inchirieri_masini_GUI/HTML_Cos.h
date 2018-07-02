#pragma once
#include "Cos.h"
class HTML_Cos :public Cos {
public:
	HTML_Cos(Repository &r) :Cos{ r } {};
	void scrie_in_fisier() override {
		std::ofstream fout("Cos.html");

		std::string rez = "<!DOCTYPE html>\n";
		rez += "<html>\n";
		rez += "<head>\n";
		rez += "<title>Lista de lucru</title>\n";
		rez += "</head>\n";
		rez += "<body>\n";
		rez += "<table border=\"1\">\n";
		rez += "<tr>\n";
		rez += "<td>Numar inmatriculare</td>\n";
		rez += "<td>Producator</td>\n";
		rez += "<td>Model</td>\n";
		rez += "<td>Tip</td>\n";
		rez += "</tr>\n";

		for (auto el : listaLucru) {
			rez += "<tr>\n";
			rez += "<td>" + el.getNrInmatr() + "</td>\n";
			rez += "<td>" + el.getProducator() + "</td>\n";
			rez += "<td>" + el.getModel() + "</td>\n";
			rez += "<td>" + el.getTip() + "</td>\n";
			rez += "</tr>\n";
		}

		rez += "</table>";
		rez += "</body>\n";
		rez += "</html>\n";

		fout << rez;
	}
	void deschide_fisier() override {
		this->scrie_in_fisier();
		ShellExecuteA(NULL, NULL, "chrome.exe", "Cos.html", NULL, SW_SHOWMAXIMIZED);
	}
	~HTML_Cos() = default;
};

void test_HTML_Cos() {
	std::stringstream ss;
	RepositoryFile repo("test_in.txt", "test_out.txt");
	HTML_Cos lista{ repo };
	lista.adaugaLista("SV11TEO");
	lista.adaugaLista("SV11TEO");
	assert(lista.getListaLucru().size() == 1);
	int nr = 0;
	try { lista.adaugaRandomLista(nr); assert(false); }
	catch (ListaLucruException& ex) {
		ss << ex;
		string str;
		str = ss.str();
		assert(str == "! Dati un numar strict pozitiv.\n");
		assert(true);
	}
	assert(lista.dimLista() == 1);
	lista.golireLista();
	assert(lista.dimLista() == 0);
	lista.scrie_in_fisier();
	nr = 1;
	lista.adaugaRandomLista(nr);
	nr = 6;
	try { lista.adaugaRandomLista(nr); assert(false); }
	catch (ListaLucruException&) { assert(true); }
	assert(lista.dimLista() == 1);
	lista.scrie_in_fisier();
}