/*
Write a recursive program (Python or C++ programming language) for next requirements. You can use and
extend for implementation the C++/Python model available in /Lab/R1, this model already containing recursive
implementations for creating, printing and destroying of a List.
It is mandatory to work with a structure/class List.
For a List will be used a linked representation. Do not use containers from STL or predefined operations on
lists in Python (append, len, slicing, etc.).
*/
#include "Lista.h"
#include <iostream>
using namespace std;


void tiparireRec(Iterator& iterator) {
	if (iterator.valid()) {
		int val = iterator.element();
		std::cout << val << " ";
		iterator.urmator();
		tiparireRec(iterator);
	}
}

void tiparire(const Lista& l) {
	Iterator iterator{ l };
	tiparireRec(iterator);
	cout << endl;
}

void reunire(Lista& l,Iterator& iterator) {
	if (iterator.valid()) {
		int val = iterator.element();
		if (l.cauta(val) == nullptr) l.adaugare(val);
		iterator.urmator();
		reunire(l,iterator);
	}
}

void diferenta(Lista&l, Iterator&iterator) {
	if (iterator.valid()) {
		int val = iterator.element();
		if (l.cauta(val) != nullptr) l.sterge(val);
		iterator.urmator();
		diferenta(l, iterator);
	}
	}

void problema1() {
	/*
	a. Transform a list in a set.
	b. Determine the union of two sets. The sets are represented as lists
	*/

	//Transformare liste in multimi
	cout << "Multimea1: " << endl;
	Lista m1;
	m1.makeSet();
	cout << "M1: "; tiparire(m1);
	cout << "Multimea2: " << endl;
	Lista m2;
	m2.makeSet();
	cout << "M2: "; tiparire(m2);

	//Reunire multimi 
	Iterator i{ m2 };
	reunire(m1, i);
	cout << "Reunire multimi: ";
	tiparire(m1);

}
 
void problema2() {
	/*
	a. Substitute the i-th element from a list, with a value v.
	b. Determine difference of two sets represented as lists.
	*/

	//Substituire element
	/*int nr,v;
	Lista l;
	cout << "Dati un numar intre 1 si " << l.dim() << endl;
	do {
		cout << "nr="; cin >> nr;
	} while (nr<1 || nr>l.dim());
	cout << "v="; cin >> v;
	l.modifica(nr, v);
	tiparire(l);*/

	//Diferenta multimi !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Si m2-m1!
	cout << "Multimea1: "<<endl;
	Lista m1;
	m1.makeSet();
	cout << "Multimea2: "<<endl;
	Lista m2;
	m2.makeSet();
	
	Iterator i{ m2 };
	diferenta(m1, i);
	cout << "Diferenta multimi: ";
	tiparire(m1);
}

void problema3() {
	/*
	a. Check if a list is a set.
	b. Determine the number of distinct elements from a list.
	*/
}


int main()
{
	//problema1();
	//problema2();
	problema3();
	return 0;
}
