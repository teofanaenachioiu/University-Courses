#include "lista.h"
#include <iostream>

using namespace std;

int main(){
	Lista l;
	l = creare();
	tipar(l);
	cout << endl;

	if (nrParElem(l) == true) cout << "Numar par de elemente" << endl;
	else cout << "Numar impar de elemente" << endl;

	int el;
	cout << "Numarul de sters: "; cin >> el;
	Lista ll=stergeElem(l, el);
	cout << "Noua lista: ";
	tipar(ll);
	return 0;
}