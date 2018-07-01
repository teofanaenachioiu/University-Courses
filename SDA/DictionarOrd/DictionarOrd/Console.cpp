#include "Console.h"
#include <iostream>
/*
Meniul aplicatiei si comenzile care pot fi folosite
*/
void Console::meniu()
{
	cout << "**************** Administrare camin privat ***************" << endl;
	cout << "* 1) Inchiriere camera                                   *" << endl;
	cout << "* 2) Efectuare plata pe camera                           *" << endl;
	cout << "* 3) Adaugare suma de plata                              *" << endl;
	cout << "* 4) Eliberare camera                                    *" << endl;
	cout << "* 5) Vizualizare camere ce au plata chiriei neefectuate  *" << endl;
	cout << "* 6) Vizualizare camere ce sunt la zi cu plata           *" << endl;
	cout << "* 7) Vizualizare camere inchiriate                       *" << endl;
	cout << "* 0) Iesire                                              *" << endl;
	cout << "**********************************************************" << endl;

}
/*
Citire date pentru inchiriere camera si apelul functiei
*/
void Console::apel_inchiriere()
{
	int nrC, pret;
	string nume;
	cout << "Numar camera: "; cin >> nrC;
	cout << "Persoana de contact: "; cin >> nume;
	cout << "Pret camera: "; cin >> pret;
	Camera c = Camera(nrC, nume, pret);
	serv.inchiriere(c);
	cout << ">> Camera inchiriata!" << endl;
}
/*
Citire date pentru efectuare plata pe camera 
*/
void Console::apel_efectuarePlata()
{
	int nr;
	cout << "Numar camera: "; cin >> nr;
	serv.faPlata(nr);
	cout << ">> Plata efectuata! " << endl;
}
/*
Citire date pentru adaugare plata pe camera
*/
void Console::apel_adaugarePlata()
{
	int nr,pret;
	cout << "Numar camera: "; cin >> nr;
	cout << "Suma de plata: "; cin >> pret;
	serv.adaugaPlata(nr,pret);
	cout << ">> Suma de plata adaugata!" << endl;
}
/*
Citire date pentru elminare camera si apelul functiei
*/
void Console::apel_elibereare()
{
	int nr;
	cout << "Numar camera: "; cin >> nr;
	serv.eliberare(nr);
	cout << ">> Camera eliberata!" << endl;
}
/*
Afisare camere cu plata neefectuata
*/
void Console::apel_showNeplatit()
{
	DictionarOrdonat dictionar = { fun };
	serv.showPlataNeefectuata(dictionar);
	Iterator iterator = dictionar.iterator();
	iterator.prim();
	while (iterator.valid()) {
		TElement pereche = iterator.element();
		cout <<">> " << pereche.getValoare() << endl;
		iterator.urmator();
	}
	if (dictionar.dim() == 0)
		cout << ">> Nu exista camere" << endl;
}
/*
Afisare camere cu plata efectuata
*/
void Console::apel_showPlatit()
{
	DictionarOrdonat dictionar1 = { fun };
	this->serv.showPlataLaZi(dictionar1);
	Iterator iterator = dictionar1.iterator();
	iterator.prim();
	while (iterator.valid()) {
		TElement pereche = iterator.element();
		cout << ">> "<< pereche.getValoare() << endl;
		iterator.urmator();
	}
	if (dictionar1.dim() == 0)
		cout << ">> Nu exista camere" << endl;
}
/*
Afisare camere
*/
void Console::apel_showCamere()
{
	Iterator iterator = serv.showCamere().iterator();
	iterator.prim();
	while (iterator.valid()) {
		TElement pereche = iterator.element();
		std::cout << ">> " << pereche.getValoare() << std::endl;
		iterator.urmator();
	}
}
/*
Punctul de start al aplicatiei
*/
void Console::run()
{
	int cmd;
	while (true) {
		meniu();
		cout << "Dati comanda: "; cin >> cmd;
		try {
			switch (cmd)
			{
			case 1:
				apel_inchiriere(); break;
			case 2:
				apel_efectuarePlata(); break;
			case 3:
				apel_adaugarePlata(); break;
			case 4:
				apel_elibereare(); break;
			case 5:
				apel_showNeplatit(); break;
			case 6:
				apel_showPlatit(); break;
			case 7:
				apel_showCamere(); break;
			/*case 8: {
				Multime<int> m;
				serv.Chei(m);

				IteratorM<int> iterator = m.iteratorM();
				
				while (iterator.valid()) {
					int el = iterator.element();
					std::cout << ">> " << el << std::endl;
					iterator.urmator();
				}
				
			}break;*/
			default:
				break;
			}
		}
		catch (RepoException& ex) {
			cout << ex << endl;
		}
		catch (CameraException &ex) {
			cout << ex << endl;
		}
		if (cmd == 0) break;
	}
}
