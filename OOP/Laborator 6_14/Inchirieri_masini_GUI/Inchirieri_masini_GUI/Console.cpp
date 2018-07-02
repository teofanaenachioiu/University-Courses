#include "Console.h"
#include <iostream>
using namespace std;
//meniul principal al aplicatiei
void meniu() {
	cout << "*************** Inchiriere masini ***************" << endl;
	cout << "* 1. Adauga masina                              *" << endl;
	cout << "* 2. Sterge masina                              *" << endl;
	cout << "* 3. Actualizare informatii masina              *" << endl;
	cout << "* 4. Cauta masina dupa numar de inmatriculare   *" << endl;
	cout << "* 5. Afisare lista de masini                    *" << endl;
	cout << "* 6. Filtrare lista de masini                   *" << endl;
	cout << "* 7. Sortare lista de masini                    *" << endl;
	cout << "* 8. Lucru cu lista de masini                   *" << endl;
	cout << "* 9. Undo                                       *" << endl;
	cout << "* 0. Iesire                                     *" << endl;
	cout << "*************************************************" << endl;
}
//submeniu pentru functia de actualizare
void submeniuActualizare() {
	cout << "***************** Actualizari *******************" << endl;
	cout << "* 1. Actualizati producatorul                   *" << endl;
	cout << "* 2. Actualizati modelul                        *" << endl;
	cout << "* 3. Actualizati tipul                          *" << endl;
	cout << "*************************************************" << endl;
}
//submeniu pentru functia de filtrare
void submeniuFiltrare() {
	cout << "****************** Filtrari *********************" << endl;
	cout << "* 1. Filtrare dupa producator                   *" << endl;
	cout << "* 2. Filtrare dupa tip                          *" << endl;
	cout << "*************************************************" << endl;
}
//submeniu pentru functia de sortare
void submeniuSortare() {
	cout << "******************** Sortari ********************" << endl;
	cout << "* 1. Sortare dupa numar de inmatriculare        *" << endl;
	cout << "* 2. Sortare dupa tip                           *" << endl;
	cout << "* 3. Sortare dupa producator + model            *" << endl;
	cout << "*************************************************" << endl;
}
//submeniu pentru functia de lucru cu masini
void submeniuLucru() {
	cout << "**************** Lista de lucru *****************" << endl;
	cout << "* 1. Goleste lista                              *" << endl;
	cout << "* 2. Adauga in lista                            *" << endl;
	cout << "* 3. Genereaza lista                            *" << endl;
	cout << "* 4. Vizualizare lista de masini                *" << endl;
	cout << "* 5. Salveaza lista in fisier CSV/HTML          *" << endl;
	cout << "*************************************************" << endl;
}
//functia de adaugare
void Console::adaugaMasina() {
	string nrInmatr, prod, model, tip;
	cout << "Numar de inmatriculare: "; cin >> nrInmatr;
	cout << "Producator: "; cin >> prod;
	cout << "Model: "; cin >> model;
	cout << "Tip: "; cin >> tip;
	serv.adauga(Masina(nrInmatr, prod, model, tip));
	cout << ">> Masina adaugata in lista." << endl;
}
//functia de actualizare
void Console::actualizareMasina() {
	string caracteristica, nrInmatr;
	int opt;
	cout << "Numar de inmatriculare: "; cin >> nrInmatr;
	Masina m = serv.cauta(nrInmatr);
	submeniuActualizare();
	cout << "Optiune: "; cin >> opt;
	if (opt == 1) {
		cout << "Noul producator: "; cin >> caracteristica;
		serv.actualizare(nrInmatr, caracteristica, opt);
		cout << " >> Producatorul a fost actualizat." << endl;
	}
	if (opt == 2) {
		cout << "Noul model: "; cin >> caracteristica;
		serv.actualizare(nrInmatr, caracteristica, opt);
		cout << " >> Modelul a fost actualizat." << endl;
	}
	if (opt == 3) {
		cout << "Noul tip: "; cin >> caracteristica;
		serv.actualizare(nrInmatr, caracteristica, opt);
		cout << " >> Tipul a fost actualizat." << endl;
	}
	if (opt != 1 && opt != 2 && opt != 3)
		cout << "Nu exista aceasta optiune!!!" << endl;
}
//functia de stergere
void Console::stergeMasina() {
	string nrInmatr;
	cout << "Numar de inmatriculare: "; cin >> nrInmatr;
	serv.sterge(nrInmatr);
	cout << " >> Masina a fost eliminata." << endl;
}

void Console::undoCall() {
	serv.undo();
	cout << "Operatie efectuata" << endl;
}

//functia de cautare
void Console::cautaMasina() {
	string nrInmatr;
	cout << "Numar de inmatriculare: "; cin >> nrInmatr;
	Masina m = serv.cauta(nrInmatr);
	cout << " >> " << m.getNrInmatr() << " " << m.getProducator() << " " << m.getModel() << " " << m.getTip() << endl;
}
//functia de afisare 
void Console::afisareMasini() {
	if (this->serv.listaMasini().size() == 0)
		cout << " >> Nu exista masini de inchiriat." << endl;
	else {
		for (Masina const &m : serv.listaMasini())
			cout << " >> " << m.getNrInmatr() << " " << m.getProducator() << " " << m.getModel() << " " << m.getTip() << endl;
	}
}
//functia de filtrare
void Console::filtrareMasini() {
	string caracteristica;
	int opt;
	vector <Masina> lista;
	submeniuFiltrare();
	cout << "Optiune: "; cin >> opt;
	if (opt == 1) {
		cout << "Producator: "; cin >> caracteristica;
		lista = serv.filtrare(caracteristica, 1);
		if (lista.size() == 0)
			cout << " >> Nu exista masini de la producatorul dat." << endl;
		else {
			cout << " >> Numar masini: " << lista.size() << endl;
			for (auto const& m : lista)
				cout << " >> " << m.getNrInmatr() << " " << m.getProducator() << " " << m.getModel() << " " << m.getTip() << endl;
		}
	}
	if (opt == 2) {
		cout << "Tip: "; cin >> caracteristica;
		lista = serv.filtrare(caracteristica, 2);
		if (lista.size() == 0)
			cout << " >> Nu exista masini de tipul dat." << endl;
		else {
			cout << " >> Numar masini: " << lista.size() << endl;
			for (auto const& m : lista)
				cout << " >> " << m.getNrInmatr() << " " << m.getProducator() << " " << m.getModel() << " " << m.getTip() << endl;
		}
	}
	if (opt != 1 && opt != 2)
		cout << "Nu exista aceasta optiune!!!" << endl;
}
//functia de sortare
void Console::sortareMasini()
{
	int opt;
	vector <Masina> lista;
	submeniuSortare();
	cout << "Optiune: "; cin >> opt;
	if (opt == 1 || opt == 2 || opt == 3) {
		lista = serv.sortare(opt, 0);
		for (auto const& m : lista)
			cout << " >> " << m.getNrInmatr() << " " << m.getProducator() << " " << m.getModel() << " " << m.getTip() << endl;
	}
	else cout << "Nu exista aceasta optiune!!!" << endl;
}

//afisare lista de lucru cu masini
void Console::afisareListaLucru() {
	for (auto m : lista.getListaLucru())
		cout << " >> " << m.getNrInmatr() << " " << m.getProducator() << " " << m.getModel() << " " << m.getTip() << endl;
}

//operatiile disponibile pe lista de lucru
void Console::lucruMasini() {
	int opt;
	submeniuLucru();
	cout << "Optiune: "; cin >> opt;
	if (opt == 1) {
		lista.golireLista();
		cout << " >> Lista de lucru a fost golita" << endl;
		cout << " >> Dimensiune lista: " << lista.dimLista() << endl;
	}
	if (opt == 2) {
		string nrInmatr;
		cout << "Numar de inmatriculare: "; cin >> nrInmatr;
		lista.adaugaLista(nrInmatr);
		cout << " >> Masina a fost adaugata in lista" << endl;
		cout << " >> Dimensiune lista: " << lista.dimLista() << endl;
		afisareListaLucru();
	}
	if (opt == 3) {
		unsigned nr = 0;
		cout << "Numar de masini de adaugat: "; cin >> nr;
		lista.adaugaRandomLista(nr);
		cout << " >> Masinile au fost adaugate" << endl;
		cout << " >> Dimensiune lista: " << lista.dimLista() << endl;
		afisareListaLucru();
	}
	if (opt == 4) {
		if (lista.dimLista() == 0) cout << " >> Nu exista masini" << endl;
		else afisareListaLucru();
	}
	if (opt == 5) {
		lista.scrie_in_fisier();
		lista.deschide_fisier();
	}
	if (opt != 1 && opt != 2 && opt != 3 && opt != 4 && opt != 5)
		cout << "Nu exista aceasta optiune!!!" << endl;
}

//entry point 
void Console::run() {
	int cmd = { 0 };
	while (true) {
		meniu();
		cout << "Comanda: "; cin >> cmd;
		try {
			if (cmd == 1) adaugaMasina();
			if (cmd == 2) stergeMasina();
			if (cmd == 3) actualizareMasina();
			if (cmd == 4) cautaMasina();
			if (cmd == 5) afisareMasini();
			if (cmd == 6) filtrareMasini();
			if (cmd == 7) sortareMasini();
			if (cmd == 8) lucruMasini();
			if (cmd == 9) undoCall();
			if (cmd == 0) {
				cout << "Multumim ca ati utilizat aplicatia!" << endl; break;
			}
			if (cmd != 0 && cmd != 1 && cmd != 2 && cmd != 3 && cmd != 4 && cmd != 5 && cmd != 6 && cmd != 7 && cmd != 8 && cmd != 9) {
				cout << "Comanda gresita!" << endl;
			}
		}
		catch (MasinaRepoException& ex) {
			cout << ex << endl;
		}
		catch (ValidateException& ex) {
			cout << ex << endl;
		}
		catch (ListaLucruException& ex) {
			cout << ex << endl;
		}
	}
}