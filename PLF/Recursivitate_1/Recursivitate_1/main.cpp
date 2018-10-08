#include "lista.h"
#include <iostream>
using namespace std;
int main()
{
	Lista l;
	l = creare();
	tipar(l); 
	cout << endl;

	//Problema 3
	/*if (isSet(l) == true) cout << "E multime"<<endl;
	else cout << "Nu e multime" << endl;
	cout << "Numar elemente distincte " << nrDist(l) << endl;*/

	//Problema 4
	/*if (evenNrElems(l) == true) cout << "Lista are numar par de elemente" << endl;
	else cout << "Lista nu are numar par de elemente" << endl;
	int el;
	cout << "Dati un element: "; cin >> el;
	deleteElem(el, l);
	cout << "Am sters toate aparitiile lui " << el << " din lista" << endl;
	tipar(l);*/
	
	//Problema 5 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	/*cout << "CMMDC: " << cmmdc(l) << endl;
	int poz, el;
	cout << "Elementul de inserat: "; cin >> el;
	cout << "Pozitia: "; cin >> poz;
	insertElem(el, poz, l);//nu insereaza pe prima poz;
	cout << "Am inserat" << endl;
	tipar(l);*/

	//Problema 6
	/*int el;
	cout << "Elementul de adaugat: "; cin >> el;
	adaugaSfarsit(el, l);//Crearea recursiva e facuta in asa fel incat lista sa nu fie niciodata vida!!
	tipar(l);
	Lista ll,lll;
	ll = creare();
	tipar(ll);
	cout << endl;
	lll = concatenare(l, ll);
	tipar(lll);*/

	//Problema 7
	/*Lista ll = creare();
	tipar(ll);
	if (egale(l, ll) == true)cout << "Liste egale" << endl;
	else cout << "Listele nu sunt egale" << endl;
	Lista li = intersectie(l, ll);
	tipar(li);*/

	//Problema 8
	/*cout << "CMMMC: " << cmmmc(l) << endl;
	int e1, e2;
	cout << "Elementul de inlocuit: "; cin >> e1;
	cout << "Elementul inlocuitor: "; cin >> e2;
	l = substituire(l, e1, e2);
	tipar(l);*/

	//Problema 9 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! inversarea listei
	/*cout << "Elementul maxim: " << elemMax(l) << endl;
	l = inversare(l);
	tipar(l);*/

	//Problema 10
	/*cout << "Numarul este: " << detNumar(l) << endl;
	Lista ll = creare();
	Lista lll = diferenta(l, ll);
	tipar(lll);*/

	//Problema 11
	/*int c;
	cout << "Elementul de cautat: "; cin >> c;
	if (cauta(c, l) == NULL) cout << "Elementul nu exista in lista" << endl;
	else cout << "Element existent!" << endl;
	cout << "Dimensiune lista " << dimensiune(l) << endl;*/

	//Problema 12



	distruge(l);
	return 0;
}