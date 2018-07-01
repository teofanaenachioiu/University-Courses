#include"Dictionar.h"
#include"Domain.h"
#include "Repository.h"
#include "Service.h"
#include "Console.h"
#define _CRTDBG_MAP_ALLOC  
#include <stdlib.h>  
#include <crtdbg.h>

int main() {
	{
		//testeDomain();
		//testeRepo();
		//testeService();
		//test();
	}
	Repository repo = { "input.txt","outpu.txt" };
	Validator val;
	Service serv = { repo,val };
	Console cons = { serv };
	cons.run();
	_CrtDumpMemoryLeaks();
	return 0;
}