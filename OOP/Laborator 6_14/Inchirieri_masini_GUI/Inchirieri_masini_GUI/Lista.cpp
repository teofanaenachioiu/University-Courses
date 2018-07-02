#include "lista.h"
#include <assert.h>

//Teste pentru vector dinamic

void testVector() {
	Vector<int> vect;
	const int elem = 1;

	//test begin, end, size
	assert(vect.begin() == nullptr);
	assert(vect.last() == nullptr);
	assert(vect.size() == 0);
	assert(vect.end() == nullptr);

	//test add_elem
	vect.add(elem);
	assert(vect.begin() == vect.last());
	assert(vect.end() == nullptr);
	assert(vect.size() == 1);
	vect.add(elem);
	vect.add(elem);
	vect.add(elem);
	vect.add(elem);
	assert(vect.begin() != nullptr);
	assert(vect.last() != vect.begin());
	assert(vect.last() != nullptr);
	assert(vect.end() == nullptr);
	assert(vect.size() == 5);

	//test [] operator
	assert(vect[0] == 1);
	vect[1] = 2;
	assert(vect[1] == 2);

	//test find
	assert(vect.find(2) == 1);
	assert(vect.find(1) == 0);
	assert(vect.find(3) == -1);


	//test erase_elem
	vect.erase(5);
	vect.erase(-1);
	assert(vect.size() == 5);
	vect.erase(0);
	assert(vect.size() == 4);
	vect.erase(3);
	vect.erase(1);
	assert(vect.size() == 2);

	//test copy constructor
	Vector<int> copy(vect);
	assert(copy.begin() != vect.begin());
	assert(copy.last() != vect.last());
	assert(copy.end() == nullptr);
	assert(copy.size() == vect.size());
	Iterator <int> it_vect = vect.begin(), it_copy = copy.begin();
	for (; it_vect != vect.end() && it_copy != copy.end(); ++it_vect, ++it_copy) {
		assert(*it_copy == *it_vect);
		*it_copy = 2;
	}

	//test copy assignment
	copy = vect;
	assert(copy.begin() != vect.begin());
	assert(copy.last() != vect.last());
	assert(copy.end() == nullptr);
	assert(copy.size() == vect.size());
	it_vect = vect.begin();
	it_copy = copy.begin();
	//(it_copy++) [C26444] kept only for testing postfix iterator increment
	for (; it_vect != vect.end() && it_copy != copy.end(); ++it_vect, it_copy++) {
		assert(*it_vect == *it_copy);
	}

}