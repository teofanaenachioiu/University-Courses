#pragma once
#include "Draw.h"
#include "Observer.h"
#include "Service.h"
#include "Masina.h"
#include "Cos.h"
#include "CosGUI.h"
#include <qwidget.h>
#include <qdialog.h>
#include <qpushbutton.h>
#include <qlineedit.h>
#include <qboxlayout.h>
#include <vector>
#include <qlabel.h>
#include <qlist.h>
#include <QListWidget> 

class Obesevabile {
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

class ConsoleGUI : public QWidget,public Obesevabile {
private:
	std::vector<CosGUI*> cosWindows;
	std::vector<ViewCos*> deseneWindows;
	//QListWidget *lstMasini = new QListWidget;

	QListView* lstV;
	MyListModel* listModel = new MyListModel;

	QPushButton *btnAdd = new QPushButton("Adauga");
	QPushButton *btnDel = new QPushButton("Sterge");
	QPushButton *btnUpd = new QPushButton("Actualizare");
	QPushButton *btnFnd = new QPushButton("Cauta");
	QPushButton *btnRef = new QPushButton("Refresh");
	QPushButton *btnFil = new QPushButton("Filtare");
	QPushButton *btnSort = new QPushButton("Sortare");
	QPushButton *btnUndo = new QPushButton("Undo");
	QPushButton *btnClose = new QPushButton("Close");
	QPushButton *btnCos = new QPushButton("Lucru cu lista");
	QPushButton *btnAddCos= new QPushButton("Adauga in cos");
	QPushButton *btnDelCos= new QPushButton("Sterge din cos");
	QPushButton *btnGenereazaCos= new QPushButton("Genereaza in cos");
	QPushButton *btnCosCrudGUI= new QPushButton("Cos nou");
	QPushButton *btnCosReadOnly= new QPushButton("Deseneaza");

	
	QLabel *sort = new QLabel("Sortati dupa:");
	QLabel *filtrare = new QLabel("Filtrati dupa:");

	QLabel *totalRepoDim = new QLabel;

	QWidget* widSort = new QWidget;
	QWidget* widFiltrare = new QWidget;

	QPushButton *btnSortNr = new QPushButton("Nr. Inmatr.");
	QPushButton *btnSortProd = new QPushButton("Producator");
	QPushButton *btnSortTip = new QPushButton("Tip");

	QLabel *filProd = new QLabel("Producator:");
	QLabel *filTip = new QLabel("Tip:");

	QLineEdit *txtTipFil = new QLineEdit;
	QLineEdit *txtProducatorFil = new QLineEdit;
	
	QLineEdit *txtNumar = new QLineEdit;
	QLineEdit *txtProducator = new QLineEdit;
	QLineEdit *txtModel = new QLineEdit;
	QLineEdit *txtTip = new QLineEdit;
	//QSlider *slPr = new QSlider(Qt::Horizontal);
	Service & serv;
	Cos& cos ;
	CosGUI * cosgui=new CosGUI{serv, cos };
	
	void initGUICmps();
	void initSemnalSlot();//conectez butoane
	void setInitialState();//interfata initiala a ferestrei
	void loadListaMasini(const vector<Masina> &masini);
	void adaugaMasina();
	void stergeMasina();
	void cautaMasina();
	void actualizareMasina();
	void sorteazaMasini();
	void filtreazaMasini();
	void filtreazaProducator();
	void filtreazaTip();
	void desenareObiecte();
	void adaugaCos();
	void genereazaCos();
	void stergeCos();
	void creareCos();
	void functiaUndo();
	void functiaRefresh();
	void campNumar();
	void campProducator();
	void campModel();
	void campTip();
	void onSelectionChanged();

public:
	ConsoleGUI(Service & serv, Cos &cos) :serv{ serv }, cos{cos} {
		initGUICmps();
		initSemnalSlot();
		setInitialState();
	}
};


