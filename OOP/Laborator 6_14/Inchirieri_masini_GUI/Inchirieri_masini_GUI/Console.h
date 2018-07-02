#pragma once
#include "Service.h"
#include "Cos.h"
class Console
{
	Service& serv;
	Cos &lista;
	void adaugaMasina();
	void actualizareMasina();
	void stergeMasina();
	void cautaMasina();
	void afisareMasini();
	void filtrareMasini();
	void sortareMasini();
	void lucruMasini();
	void afisareListaLucru();
	void undoCall();
public:
	Console(Service& s, Cos &l) noexcept : serv{ s }, lista{ l } {}
	Console(const Console& ot) = delete;
	void run();
};

