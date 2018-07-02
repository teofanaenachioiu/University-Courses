#include "GUI.h"
#include <QtWidgets/QApplication>
//#define _CRTDBG_MAP_ALLOC  
//#include <stdlib.h>  
//#include <crtdbg.h>
#include "Console.h"
#include "Lista.h"
#include "RepositoryMemory.h"
#include "RepositoryFile.h"
#include "CSV_Cos.h"
#include "HTML_Cos.h"
#include "ConsoleGUI.h"
#include "CosGUI.h"

void teste() {
	testMasina();
	testRepoMemory();
	testRepoFile();
	testService();
	testVector();
	test_CSV_Cos();
	test_HTML_Cos();
}

int main(int argc, char *argv[]) {
	teste();
	{
		QApplication a(argc, argv);
		RepositoryFile repo("input.txt", "input.txt");
		MasinaValidator val;
		Service serv = { repo, val };
		HTML_Cos lista = { repo };
		ConsoleGUI cons = { serv,lista };
		
	
		cons.show();
		a.exec();
	}
	//_CrtDumpMemoryLeaks();
	return 0;
}