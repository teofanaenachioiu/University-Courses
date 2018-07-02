#pragma once
#include "MyTableModel.h"
#include "MyListModel.h"
#include "Observer.h"
#include "RepositoryFile.h"
#include "Masina.h"
#include "Service.h"
#include "Cos.h"
#include <qwidget.h>
#include <qdialog.h>
#include <qpushbutton.h>
#include <qlineedit.h>
#include <qboxlayout.h>
#include <vector>
#include <qlabel.h>
#include <qlist.h>
#include <QListWidget>
#include <qmessagebox.h>
#include <qspinbox.h>
#include <qtablewidget.h>
class Obesevabilee {
public:
	vector<Observer*> obsss;
	void notify(Observer* obs) {
		obs->update();
	}
	void notifyObsss() {
		for (auto o : obsss)
			notify(o);
	}
	void addObs(Observer *obs) {
		obsss.push_back(obs);
	}
};

class CosGUI : public QWidget,public Observer{
private:
	//QTableWidget *tabel;

	QTableView* tblV;
	QListView* lstV;
	MyTableModel* tableModel = new MyTableModel;
	MyListModel* listModel = new MyListModel;

	//QListWidget * lstMasiniRepo = new QListWidget;
	QPushButton *btnAdd = new QPushButton("Adauga");
	QPushButton *btnGen = new QPushButton("Genereaza");
	QPushButton *btnGol = new QPushButton("Goleste");
	QPushButton *btnClose = new QPushButton("Close");
	QPushButton *btnExporta = new QPushButton("Exporta");

	QLabel *totalRepoDim = new QLabel;
	QSpinBox *spin = new QSpinBox();

	Service &serv;
	Cos & lista;
	void initGUICmps();
	void initSemnalSlot();//conectez butoane
	void setInitialState();//interfata initiala a ferestrei
	void loadListaMasiniTabel(const vector<Masina>& masini);
	void loadListaMasiniRepo(const vector<Masina> &masini);
	void update() override;
	void onSelectionChanged();
	void genereazaCos();
	void golireCos();
	void adaugaCos();
	//void afisareCos(); //fara model
public:
	CosGUI(Service & serv, Cos & l) : serv{serv}, lista { l } {
		initGUICmps();
		initSemnalSlot();
		setInitialState();
		
	}
};