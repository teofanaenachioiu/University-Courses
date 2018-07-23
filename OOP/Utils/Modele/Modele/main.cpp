#include "Modele.h"
#include <QtWidgets/QApplication>
#include <qtableview.h>
#include<qlistview.h>

int main(int argc, char *argv[])
{
	QApplication a(argc, argv);

	/*QTableView *tbl = new QTableView;
	myTableModel *model = new myTableModel;
	tbl->setModel(model);
	tbl->show();*/

	QListView *lst = new QListView;
	myListModel *model = new myListModel;
	lst->setModel(model);
	lst->show();
	return a.exec();
}
