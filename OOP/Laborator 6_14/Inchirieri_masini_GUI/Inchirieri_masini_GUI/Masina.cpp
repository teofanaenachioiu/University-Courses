#include "Masina.h"
#include <assert.h>
//compara numerele de inmatriculare a doua masini
bool comparaNrInmatr(const Masina & m1, const Masina & m2)
{
	if (m1.getNrInmatr() == m2.getNrInmatr())
		return true;
	return false;
}
//compara producatorii a doua masini
bool comparaProducator(const Masina & m1, const Masina & m2)
{
	if (m1.getProducator() == m2.getProducator())
		return true;
	return false;
}
//compara modelele a doua masini
bool comparaModel(const Masina & m1, const Masina & m2)
{
	if (m1.getModel() == m2.getModel())
		return true;
	return false;
}
//compara tipul a doua masini
bool comparaTip(const Masina & m1, const Masina & m2)
{
	if (m1.getTip() == m2.getTip())
		return true;
	return false;
}
//teste pentru dumain
void testMasina()
{
	Masina m = { "SV10XJX","Mercedes","Viano","Van" };
	Masina m1 = { "SV10SBG","Mercedes-Benz","A17","Sedan" };
	Masina m2 = { "SV10XJX","Mercedes-Benz","A17","Sedan" };
	assert(m.getNrInmatr() == "SV10XJX");
	assert(m.getProducator() == "Mercedes");
	assert(m.getModel() == "Viano");
	assert(m.getTip() == "Van");
	assert(comparaProducator(m, m1) == false);
	assert(m.setProducator("Mercedes-Benz") == "Mercedes-Benz");
	assert(m.setModel("Vito") == "Vito");
	assert(m.setTip("Vann") == "Vann");
	assert(comparaModel(m, m1) == false);
	assert(comparaNrInmatr(m, m1) == false);
	assert(comparaProducator(m, m1) == true);
	assert(comparaTip(m, m1) == false);
	assert(m.setModel("A17") == "A17");
	assert(m.setTip("Sedan") == "Sedan");
	assert(comparaModel(m, m1) == true);
	assert(m == m2);
	assert(comparaTip(m, m1) == true);
}