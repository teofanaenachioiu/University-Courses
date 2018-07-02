#include <vector>
#include "Service.h"
#include "ConsoleGUI.h"
#include "Masina.h"
#include <qboxlayout.h>
#include <qlistwidget.h>
#include <qpushbutton.h>
#include <qdebug.h>
#include <qcolor.h>
#include <qslider.h>
#include <QGroupBox>
#include <qformlayout.h>
#include <qmessagebox.h>
void ConsoleGUI::initGUICmps() {
	QHBoxLayout* ly = new QHBoxLayout;
	setLayout(ly);

	//dreapta
	QWidget *widDr = new QWidget;
	QVBoxLayout* functLy = new QVBoxLayout;
	widDr->setLayout(functLy);
	

	//lista masini
	QWidget *widMasiniList = new QWidget;
	QVBoxLayout *masiniLy = new QVBoxLayout;
	widMasiniList->setLayout(masiniLy);
	masiniLy->addWidget(new QLabel("MASINI: "));

	lstV = new QListView;
	lstV->setUniformItemSizes(true);
	lstV->setModel(listModel);
	masiniLy->addWidget(lstV);

	//afisare total masini
	QWidget* widTotalMasini = new QWidget;
	QFormLayout *total = new QFormLayout;
	widTotalMasini->setLayout(total);
	int dim = serv.listaMasini().size();
	QLabel *totalRepoText = new QLabel("Total masini:");
	totalRepoDim->setNum(dim);	
	total->addRow(totalRepoText,totalRepoDim);
	total->setFormAlignment(Qt::AlignLeft);
	masiniLy->addWidget(widTotalMasini);
	functLy->addWidget(widMasiniList);

	//butoane functionalitati
	functLy->addWidget(new QLabel("Functionalitati: "));
	QHBoxLayout *btnFunctLy = new QHBoxLayout;
	
	QWidget* widFunct = new QWidget;
	widFunct->setLayout(btnFunctLy);

	btnFunctLy->addWidget(btnRef);
	widFunct->setLayout(btnFunctLy);
	btnFunctLy->addWidget(btnSort);
	widFunct->setLayout(btnFunctLy);
	btnFunctLy->addWidget(btnFil);

	functLy->addWidget(widFunct);


	//Lab 13-14
	functLy->addWidget(new QLabel("Lista mea de masini: "));
	QHBoxLayout *cosLy = new QHBoxLayout;

	QWidget* widCos= new QWidget;
	widCos->setLayout(cosLy);

	cosLy->addWidget(btnAddCos);
	widCos->setLayout(cosLy);
	cosLy->addWidget(btnDelCos);
	widCos->setLayout(cosLy);
	cosLy->addWidget(btnGenereazaCos);
	widCos->setLayout(cosLy);
	cosLy->addWidget(btnCosCrudGUI);
	widCos->setLayout(cosLy);
	cosLy->addWidget(btnCosReadOnly);

	functLy->addWidget(widCos);

	//sortarile
	QHBoxLayout *btnSortLy = new QHBoxLayout;
	
	widSort->setLayout(btnSortLy);

	btnSortLy->addWidget(btnSortNr);
	widSort->setLayout(btnSortLy);
	btnSortLy->addWidget(btnSortProd);
	widSort->setLayout(btnSortLy);
	btnSortLy->addWidget(btnSortTip);
	widSort->setLayout(btnSortLy);

	functLy->addWidget(sort);
	functLy->addWidget(widSort);

	sort->hide();
	widSort->hide();

	//filtrarile
	QFormLayout *btnFilLy = new QFormLayout;

	widFiltrare->setLayout(btnFilLy);

	btnFilLy->addRow(filProd, txtProducatorFil);
	btnFilLy->addRow(filTip, txtTipFil);


	functLy->addWidget(filtrare);
	functLy->addWidget(widFiltrare);

	filtrare->hide();
	widFiltrare->hide();


	//stanga
	QWidget *widStg = new QWidget;
	QVBoxLayout* btnLy = new QVBoxLayout;
	widStg->setLayout(btnLy);
	btnLy->addWidget(new QLabel("Optiuni: "));


	//form detalii
	QWidget *widDetalii = new QWidget;
	QFormLayout *formDetalii = new QFormLayout;
	widDetalii->setLayout(formDetalii);
	formDetalii->addRow(new QLabel("Nr. inmatr.:"), txtNumar);
	formDetalii->addRow(new QLabel("Producator:"), txtProducator);
	formDetalii->addRow(new QLabel("Model:"), txtModel);
	formDetalii->addRow(new QLabel("Tip:"), txtTip);
	btnLy->addWidget(widDetalii);
	
	//butoane optiuni
	widDetalii->setLayout(btnLy);
	btnLy->addWidget(btnAdd);
	widDetalii->setLayout(btnLy);
	btnLy->addWidget(btnDel);
	widDetalii->setLayout(btnLy);
	btnLy->addWidget(btnUpd);
	widDetalii->setLayout(btnLy);
	btnLy->addWidget(btnFnd);
	
	//undo - close
	QWidget *btnFin = new QWidget;
	QHBoxLayout* btnFinLy = new QHBoxLayout;
	btnFin->setLayout(btnFinLy);
	btnFinLy->addWidget(btnUndo);
	btnFinLy->addWidget(btnClose);
	btnLy->addWidget(btnFin);

	//legare
	ly->addWidget(widDr);
	ly->addWidget(widStg);
}

void ConsoleGUI::initSemnalSlot(){
	//lista de masini
	/*
	--fara model--
	QObject::connect(lstMasini, &QListWidget::itemSelectionChanged, [this]() {
	bool eCevaSelectat = !lstMasini->selectedItems().isEmpty();//este ceva selectat. mere butonu

	this->btnUpd->setEnabled(true);
	if (eCevaSelectat) {
	this->btnAdd->setEnabled(false);
	this->btnFnd->setEnabled(false);
	this->btnDel->setEnabled(true);
	this->btnAddCos->setEnabled(true);
	this->btnDelCos->setEnabled(true);
	btnUpd->setEnabled(false);
	auto prod = lstMasini->selectedItems().at(0)->data(Qt::UserRole); //UserRole???
	QListWidgetItem *masinaItem = lstMasini->selectedItems().at(0);
	QString nrInmatr = masinaItem->text();
	Masina m = serv.cauta(nrInmatr.toStdString());
	txtNumar->setText(nrInmatr);
	txtNumar->setEnabled(false);
	txtProducator->setText(QString::fromStdString(m.getProducator()));
	txtModel->setText(QString::fromStdString(m.getModel()));
	txtTip->setText(QString::fromStdString(m.getTip()));
	}
	else
	{
	txtNumar->setText("");
	txtProducator->setText("");
	txtModel->setText("");
	txtTip->setText("");
	btnDel->setEnabled(false);
	btnUpd->setEnabled(false);
	txtNumar->setEnabled(true);
	}
	});*/

	//Lista de masini - varianta cu model
	QObject::connect(lstV->selectionModel(), &QItemSelectionModel::selectionChanged, this, &ConsoleGUI::onSelectionChanged);

	//Buton Close
	QObject::connect(btnClose, SIGNAL(clicked()), this, SLOT(close()));

	//Buton Cautare
	QObject::connect(btnFnd, &QPushButton::clicked, this, &ConsoleGUI::cautaMasina);

	//Buton Adauga
	QObject::connect(btnAdd, &QPushButton::clicked, this, &ConsoleGUI::adaugaMasina);

	//Buton Sterge
	QObject::connect(btnDel, &QPushButton::clicked, this, &ConsoleGUI::stergeMasina);

	//Buton Actualizare
	QObject::connect(btnUpd, &QPushButton::clicked, this, &ConsoleGUI::actualizareMasina);

	//Buton Refresh
	QObject::connect(btnRef, &QPushButton::clicked, this, &ConsoleGUI::functiaRefresh);

	//Buton Undo
	QObject::connect(btnUndo, &QPushButton::clicked, this, &ConsoleGUI::functiaUndo);

	//Buton Cos
	QObject::connect(btnCosCrudGUI, &QPushButton::clicked, this, &ConsoleGUI::creareCos);

	//Buton Genereaza Random in Cos
	QObject::connect(btnGenereazaCos, &QPushButton::clicked, this, &ConsoleGUI::genereazaCos);

	//Buton Sterge din cos
	QObject::connect(btnDelCos, &QPushButton::clicked, this, &ConsoleGUI::stergeCos);

	//Buton Adauga in cos
	QObject::connect(btnAddCos, &QPushButton::clicked, this, &ConsoleGUI::adaugaCos);

	//Buton Desenare
	QObject::connect(btnCosReadOnly, &QPushButton::clicked, this, &ConsoleGUI::desenareObiecte);

	//Field numar
	QObject::connect(txtNumar, &QLineEdit::textEdited, this, &ConsoleGUI::campNumar);

	//Field producator
	QObject::connect(txtProducator, &QLineEdit::textEdited, this, &ConsoleGUI::campProducator);

	//Field model
	QObject::connect(txtModel, &QLineEdit::textEdited, this, &ConsoleGUI::campModel);

	//Field tip
	QObject::connect(txtTip, &QLineEdit::textEdited, this, &ConsoleGUI::campTip);

	//Butoane filtrare
	QObject::connect(btnFil, &QPushButton::clicked, this, &ConsoleGUI::filtreazaMasini);
	QObject::connect(txtProducatorFil, &QLineEdit::textEdited, this, &ConsoleGUI::filtreazaProducator);
	QObject::connect(txtTipFil, &QLineEdit::textEdited, this, &ConsoleGUI::filtreazaTip);

	//Butoane sortare
	QObject::connect(btnSort, &QPushButton::clicked, this, &ConsoleGUI::sorteazaMasini);
	QObject::connect(btnSortNr, &QPushButton::clicked, [this]() {
		loadListaMasini(serv.sortare(1, 0));
	});
	QObject::connect(btnSortTip, &QPushButton::clicked, [this]() {
		loadListaMasini(serv.sortare(2, 0));
	});
	QObject::connect(btnSortProd, &QPushButton::clicked, [this]() {
		loadListaMasini(serv.sortare(3, 4));
	});
}

void ConsoleGUI::setInitialState() {
	loadListaMasini(serv.listaMasini());
	btnDel->setEnabled(false);
	btnDel->setToolTip("Stergere dupa numar de inmatriculare");
	btnAdd->setEnabled(false);
	btnAdd->setToolTip("Adaugare masina noua");
	btnUpd->setEnabled(false);
	btnUpd->setToolTip("Actualizare producator, model sau tip masina");
	btnFnd->setEnabled(false);
	btnFnd->setToolTip("Cautare dupa numar de inmatriculare");
	btnUndo->setEnabled(false);
	btnUndo->setToolTip("Undo");
	btnAddCos->setEnabled(false);
	btnDelCos->setEnabled(false);
}

void ConsoleGUI::loadListaMasini(const std::vector<Masina>& masini) {
	/*
	--fara model--
	lstMasini->clear();
	for (const auto &m : masini) {
	QListWidgetItem *item = new QListWidgetItem{ QString::fromStdString(m.getNrInmatr())};
	item->setData(Qt::UserRole, QString::fromStdString(m.getProducator()));
	lstMasini->addItem(item);
	}*/
	listModel->setMasini(masini);
}

void ConsoleGUI::adaugaMasina() 
{
	try {
		Masina m{ txtNumar->text().toStdString(),txtProducator->text().toStdString(),txtModel->text().toStdString(),txtTip->text().toStdString() };
		serv.adauga(m);
		int dim = serv.listaMasini().size();
		totalRepoDim->setNum(dim);
		loadListaMasini(serv.listaMasini());
		notifyObsss();
		btnUndo->setEnabled(true);
	}
	catch (MasinaRepoException &ex){
		QMessageBox::warning(this, "Warning!", QString::fromStdString(ex.getMsg()));
	}
	catch (ValidateException &ex) {
		QMessageBox::warning(this, "Warning!", QString::fromStdString(ex.getMsg()));
	}
}

void ConsoleGUI::stergeMasina()
{
	serv.sterge(txtNumar->text().toStdString());
	int dim = serv.listaMasini().size();
	totalRepoDim->setNum(dim);
	loadListaMasini(serv.listaMasini());
	btnUndo->setEnabled(true);
	notifyObsss();
}

void ConsoleGUI::cautaMasina()
{
	string nrInmatr = txtNumar->text().toStdString();
	try {
		Masina m = serv.cauta(nrInmatr);
		txtProducator->setText(QString::fromStdString(m.getProducator()));
		txtModel->setText(QString::fromStdString(m.getModel()));
		txtTip->setText(QString::fromStdString(m.getTip()));
	}
	catch (MasinaRepoException &ex) {
		QMessageBox::warning(this, "Warning!", QString::fromStdString(ex.getMsg()));
	}
	catch (ValidateException &ex) {
		QMessageBox::warning(this, "Warning!", QString::fromStdString("Numar de inmatriculare invalid!!! Respectati sablonul: <LLCCLLL>"));
	}
}

void ConsoleGUI::actualizareMasina()
{
	serv.actualizareTot(txtNumar->text().toStdString(), txtProducator->text().toStdString(), txtModel->text().toStdString(), txtTip->text().toStdString());
	loadListaMasini(serv.listaMasini());
	notifyObsss();
}

void ConsoleGUI::sorteazaMasini()
{
	widSort->setVisible(!widSort->isVisible());
	sort->setVisible(widSort->isVisible());
	widFiltrare->setVisible(false);
	filtrare->setVisible(false);
}

void ConsoleGUI::filtreazaMasini()
{
	widFiltrare->setVisible(!widFiltrare->isVisible());
	widSort->setVisible(false);
	filtrare->setVisible(widFiltrare->isVisible());
	sort->setVisible(false);
}

void ConsoleGUI::filtreazaProducator()
{
	string caract = txtProducatorFil->text().toStdString();
	if (caract.size()>0)txtTipFil->setEnabled(false);
	else txtTipFil->setEnabled(true);
	txtProducatorFil->setEnabled(true);
	try {
		/*
		--fara model--
		lstMasini->clear();
		for (const auto &m : serv.listaMasini()) {
		QListWidgetItem *item = new QListWidgetItem{ QString::fromStdString(m.getNrInmatr()) };
		item->setData(Qt::UserRole, QString::fromStdString(m.getProducator()));
		if (m.getProducator() == caract) {
		item->setData(Qt::BackgroundColorRole, QColor{ 169,197,213 });
		}
		lstMasini->addItem(item);
		listModel->setMasini(serv.listaMasini());
		}*/
		listModel->setMasini(serv.filtrare(caract,1));
	}
	catch (ValidateException &ex) {}
}

void ConsoleGUI::filtreazaTip()
{
	string caract = txtTipFil->text().toStdString();
	txtTipFil->setEnabled(true);
	if (caract.size()>0)txtProducatorFil->setEnabled(false);
	else txtProducatorFil->setEnabled(true);
	try {
		/*
		--fara model--
		lstMasini->clear();
		for (const auto &m : serv.listaMasini()) {
		QListWidgetItem *item = new QListWidgetItem{ QString::fromStdString(m.getNrInmatr()) };
		item->setData(Qt::UserRole, QString::fromStdString(m.getProducator()));
		if (m.getTip() == caract) {
		item->setData(Qt::BackgroundColorRole, QColor{ 169,197,213 });
		}
		lstMasini->addItem(item);
		listModel->setMasini(serv.listaMasini());
		}*/
		listModel->setMasini(serv.filtrare(caract, 2));
	}
	catch (ValidateException &ex) {}
}

void ConsoleGUI::desenareObiecte()
{
	ViewCos* desen = new ViewCos(cos);
	deseneWindows.push_back(desen);
	cos.addObs(desen);
	serv.addObs(desen);
	deseneWindows.back()->show();

	notifyObsss();
}

void ConsoleGUI::adaugaCos()
{
	auto sel = lstV->selectionModel()->selectedIndexes();
	auto firstSel = sel.at(0);
	Masina m = serv.cauta(listModel->data(firstSel).toString().toStdString());
	cos.adaugaLista(m.getNrInmatr());
	cos.notifyObsss();
}

void ConsoleGUI::genereazaCos()
{
	try {
		cos.adaugaRandomLista(rand() % (serv.listaMasini().size() - cos.dimLista()));
		cos.notifyObsss();
		btnDelCos->setEnabled(true);
	}
	catch (ListaLucruException &ex) {}
}

void ConsoleGUI::stergeCos()
{
	cos.golireLista();
	cos.notifyObsss();
}

void ConsoleGUI::functiaUndo()
{
	try {
		serv.undo();
		loadListaMasini(serv.listaMasini());
		notifyObsss();
	}
	catch (MasinaRepoException&ex) {
		btnUndo->setEnabled(false);
	}
}

void ConsoleGUI::functiaRefresh()
{
	txtNumar->setText("");
	txtProducator->setText("");
	txtModel->setText("");
	txtTip->setText("");
	btnDel->setEnabled(false);
	btnUpd->setEnabled(false);
	txtNumar->setEnabled(true);

	btnAdd->setEnabled(false);
	loadListaMasini(serv.listaMasini());
}

void ConsoleGUI::campNumar()
{
	string text_input = txtNumar->text().toStdString();
	if (text_input.size() == 0) {
		this->btnAdd->setEnabled(false);
		this->btnDel->setEnabled(false);
		this->btnFnd->setEnabled(false);
	}
	else {
		this->btnAdd->setEnabled(true);
		this->btnFnd->setEnabled(true);
	}
}

void ConsoleGUI::campProducator()
{
	btnUpd->setEnabled(true);
	btnUndo->setEnabled(true);
}

void ConsoleGUI::campModel()
{
	btnUpd->setEnabled(true);
	btnUndo->setEnabled(true);
}

void ConsoleGUI::campTip()
{
	btnUpd->setEnabled(true);
	btnUndo->setEnabled(true);
}

void ConsoleGUI::creareCos()
{
	/*
	--un singur cos--
	if(cosgui->isVisible()) cosgui->hide();
	else cosgui->show();*/
	CosGUI* cosulet = new CosGUI{ serv,cos };
	cosWindows.push_back(cosulet);
	cos.addObs(cosulet);
	serv.addObs(cosulet);
	cosWindows.back()->show();
}

void ConsoleGUI::onSelectionChanged()
{
	auto sel = lstV->selectionModel()->selectedIndexes();
	if (sel.isEmpty()) {
		//nu este nimic selectat (golesc detaliile)
		txtNumar->setText("");
		txtProducator->setText("");
		txtModel->setText("");
		txtTip->setText("");
		btnDel->setEnabled(false);
		btnUpd->setEnabled(false);
		txtNumar->setEnabled(true);
		btnFnd->setEnabled(true);
		return;
	}
	
	this->btnAdd->setEnabled(false);
	this->btnFnd->setEnabled(false);
	this->btnDel->setEnabled(true);
	this->btnAddCos->setEnabled(true);
	this->btnDelCos->setEnabled(true);
	this->btnUpd->setEnabled(false);

	auto firstSel = sel.at(0);
	Masina m = listModel->getMasini(firstSel);
	txtNumar->setText(QString::fromStdString(m.getNrInmatr()));
	txtNumar->setEnabled(false);
	txtProducator->setText(QString::fromStdString(m.getProducator()));
	txtModel->setText(QString::fromStdString(m.getModel()));
	txtTip->setText(QString::fromStdString(m.getTip()));
}