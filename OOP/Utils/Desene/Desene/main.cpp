#include "Desene.h"
#include <QtWidgets/QApplication>

int main(int argc, char *argv[])
{
	QApplication a(argc, argv);
	Desene w;
	//w.paintEvent(nullptr);
	w.show();
	return a.exec();
}
