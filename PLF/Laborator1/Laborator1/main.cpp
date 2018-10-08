#include "lista.h"
#include <iostream>

int main()
{
	Lista l;
	l = creare();
	tipar(l);
	std::cout << std::endl;
	//Substitutie lista
	/*Lista l1=creare();
	substituie(l, 5, l1);
	tipar(l);
	*/
	
	//Elementul de pe pozitia k
	/*int el=element(l, 3);
	std::cout << el << std::endl;
	*/
	//distruge(l);
	//distruge(l1);

	/*if (eMultime(l) == true)std::cout << "E multime" << std::endl;
	else std::cout << "Nu e multime" << std::endl;

	std::cout << "Sunt " << nrDistincte(l) << " elemente distincte" << std::endl;
	*/
	Lista ll = invers(l);
	tipar(ll);
	std::cout << std::endl;

	return 0;
}