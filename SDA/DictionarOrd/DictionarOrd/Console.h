#pragma once
#include "Service.h"

class Console {
private:
	Service &serv;
public:
	Console(Service &s):serv{s}{}
	Console(const Console &ot) = delete;
	void meniu();
	void apel_inchiriere();
	void apel_efectuarePlata();
	void apel_adaugarePlata();
	void apel_elibereare();
	void apel_showNeplatit();
	void apel_showPlatit();
	void apel_showCamere();
	void run(); 
};