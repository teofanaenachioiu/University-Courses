#pragma once
#include "Cos.h"
#include <assert.h>

class CSV_Cos :public Cos {

public:
	CSV_Cos(Repository &r) :Cos{ r } {};
	void scrie_in_fisier() override {
		std::ofstream out("Cos.csv");
		for (auto el : listaLucru) {
			out << el.getNrInmatr() << "," << el.getProducator() << "," << el.getModel() << "," << el.getTip() << endl;
		}
	}
	void deschide_fisier() override {
		this->scrie_in_fisier();
		ShellExecuteA(NULL, NULL, "C:\\Program Files (x86)\\Microsoft Office\\Office14\\EXCEL.exe", "Cos.csv", NULL, SW_SHOWMAXIMIZED);
	}
	~CSV_Cos() = default;
};

void test_CSV_Cos() {
	std::stringstream ss;
	RepositoryFile repo("test_in.txt", "test_out.txt");
	CSV_Cos lista{ repo };
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
	nr = 1;
	lista.adaugaRandomLista(nr);
	nr = 6;
	try { lista.adaugaRandomLista(nr); assert(false); }
	catch (ListaLucruException&) { assert(true); }
	assert(lista.dimLista() == 1);
	lista.scrie_in_fisier();
}