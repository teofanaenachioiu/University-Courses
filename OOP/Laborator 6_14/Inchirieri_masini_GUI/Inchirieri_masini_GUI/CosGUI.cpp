#include "CosGUI.h"
#include <qformlayout.h>

void CosGUI::initGUICmps()
{
	QHBoxLayout* ly = new QHBoxLayout;
	setLayout(ly);

	QWidget *widMasiniListRepo = new QWidget;
	QVBoxLayout *masiniRepoLy = new QVBoxLayout;
	widMasiniListRepo->setLayout(masiniRepoLy);
	widMasiniListRepo->setFixedWidth(200);

	lstV = new QListView;
	lstV->setUniformItemSizes(true);
	lstV->setModel(listModel);

	masiniRepoLy->addWidget(new QLabel("MASINI: "));
	masiniRepoLy->addWidget(lstV);
	
	ly->addWidget(widMasiniListRepo);

	QWidget *widBtns = new QWidget;
	QVBoxLayout *btnLy = new QVBoxLayout;
	widBtns->setLayout(btnLy);
	btnLy->addWidget(btnGen);
	spin->setMaximum(serv.listaMasini().size()-lista.dimLista());
	btnLy->addWidget(spin);
	btnLy->addWidget(btnAdd);
	btnLy->addWidget(btnGol);
	btnLy->addWidget(btnExporta);
	btnLy->addWidget(btnClose);
	ly->addWidget(widBtns);

	QWidget *widMasiniList = new QWidget;
	QVBoxLayout *masiniLy = new QVBoxLayout;
	widMasiniList->setLayout(masiniLy);
	masiniLy->addWidget(new QLabel("Lista masini: "));

	//int linii = serv.listaMasini().size();
	//tabel= new QTableWidget{linii,4};
	//masiniLy->addWidget(tabel);

	tblV = new QTableView;
	tblV->setModel(tableModel);
	masiniLy->addWidget(tblV);
	ly->addWidget(widMasiniList);

	//total cos
	QWidget* widTotalMasini = new QWidget;
	QFormLayout *total = new QFormLayout;
	widTotalMasini->setLayout(total);
	int dim = lista.dimLista();
	QLabel *totalRepoText = new QLabel("Total masini:");
	totalRepoDim->setNum(dim);
	total->addRow(totalRepoText, totalRepoDim);
	masiniLy->addWidget(widTotalMasini);
}

void CosGUI::initSemnalSlot()
{
	//lista repo
	/*QObject::connect(lstMasiniRepo, &QListWidget::itemSelectionChanged, this, &CosGUI::afisareCos);*/

	//Buton Adauga
	QObject::connect(btnAdd, &QPushButton::clicked, this, &CosGUI::adaugaCos);

	//lista
	QObject::connect(lstV->selectionModel(), &QItemSelectionModel::selectionChanged, this, &CosGUI::onSelectionChanged);

	//Buton Goleste
	QObject::connect(btnGol, &QPushButton::clicked,this, &CosGUI::golireCos);

	//Buton Exporta
	QObject::connect(btnExporta, &QPushButton::clicked, [this]() {
		lista.scrie_in_fisier();
		lista.deschide_fisier();
	});

	//Buton Close
	QObject::connect(btnClose, SIGNAL(clicked()), this, SLOT(close()));

	//Buton Genereaza
	QObject::connect(btnGen, &QPushButton::clicked, this, &CosGUI::genereazaCos);

}

void CosGUI::setInitialState()
{
	loadListaMasiniRepo(serv.listaMasini());
	loadListaMasiniTabel(lista.getListaLucru());
	btnAdd->setEnabled(false);
}


void CosGUI::loadListaMasiniRepo(const vector<Masina>& masini)
{
	/*lstMasiniRepo->clear();
	for (const auto &m : masini) {
		QListWidgetItem *item = new QListWidgetItem{ QString::fromStdString(m.getNrInmatr()) };
		lstMasiniRepo->addItem(item);
	}*/
	listModel->setMasini(masini);
}

void CosGUI::update()
{
	vector<Masina> masiniList, masini = serv.listaMasini();
	loadListaMasiniRepo(masini);
	Masina mas;
	for (const auto &m : lista.getListaLucru()) {
		try {
			mas = serv.cauta(m.getNrInmatr());
			masiniList.push_back(mas);
		}
		catch(MasinaRepoException &ex){}
	}
	int dim = lista.dimLista();
	totalRepoDim->setNum(dim);
	loadListaMasiniTabel(masiniList);
}

void CosGUI::onSelectionChanged()
{
	auto sel = lstV->selectionModel()->selectedIndexes();
	if (sel.isEmpty()) {
		//nu este nimic selectat (golesc detaliile)
		btnAdd->setEnabled(false);
		return;
	}
	/*auto firstSel = sel.at(0);
	Masina m = serv.cauta(listModel->data(firstSel).toString().toStdString());
	lista.adaugaLista(m.getNrInmatr());
	int dim = lista.dimLista();
	totalRepoDim->setNum(dim);

	spin->setMaximum(serv.listaMasini().size() - lista.dimLista());

	loadListaMasiniTabel(lista.getListaLucru());*/
	btnAdd->setEnabled(true);
}

void CosGUI::genereazaCos()
{
	try {
		lista.adaugaRandomLista(spin->value());
		totalRepoDim->setNum(lista.dimLista());
		spin->setMaximum(serv.listaMasini().size() - lista.dimLista());
		loadListaMasiniTabel(lista.getListaLucru());
	}
	catch (ListaLucruException &ex) {}
}

void CosGUI::golireCos()
{
	lista.golireLista();
	spin->setMaximum(serv.listaMasini().size() - lista.dimLista());
	int dim = lista.dimLista();
	totalRepoDim->setNum(dim);
	loadListaMasiniTabel(lista.getListaLucru());
}

void CosGUI::adaugaCos()
{
	//list.adaugaLista(lstMasiniRepo->selectedItems().at(0)->data(Qt::UserRole).toString);

	auto sel = lstV->selectionModel()->selectedIndexes();
	auto firstSel = sel.at(0);
	Masina m = serv.cauta(listModel->data(firstSel).toString().toStdString());
	lista.adaugaLista(m.getNrInmatr());
	int dim = lista.dimLista();
	totalRepoDim->setNum(dim);

	spin->setMaximum(serv.listaMasini().size() - lista.dimLista());

	loadListaMasiniTabel(lista.getListaLucru());
}

/*void CosGUI::afisareCos()
{
	//--fara model--
	bool eCevaSelectat = !lstMasiniRepo->selectedItems().isEmpty();//este ceva selectat. mere butonu
	if (eCevaSelectat) {
		this->btnAdd->setEnabled(true);
		QObject::connect(btnAdd, &QPushButton::clicked, [this]() {
			//list.adaugaLista(lstMasiniRepo->selectedItems().at(0)->data(Qt::UserRole).toString);

			QListWidgetItem *masinaItem = lstMasiniRepo->selectedItems().at(0);
			QString nrInmatr = masinaItem->text();
			Masina m = serv.cauta(nrInmatr.toStdString());
			lista.adaugaLista(m.getNrInmatr());
			int dim = lista.dimLista();
			totalRepoDim->setNum(dim);

			spin->setMaximum(serv.listaMasini().size() - lista.dimLista());

			loadListaMasiniTabel(lista.getListaLucru());
		});
	}
	else
	{
		btnAdd->setEnabled(false);
	}
}*/

void CosGUI::loadListaMasiniTabel(const vector<Masina>& masini)
{
	/*
	//--fara model--
	tabel->clear();
	int nr = 0;
	for (const auto &m : masini) {
		tabel->setItem(nr,0,new QTableWidgetItem(QString::fromStdString(m.getNrInmatr())));
		tabel->setItem(nr, 1, new QTableWidgetItem(QString::fromStdString(m.getProducator())));
		tabel->setItem(nr, 2, new QTableWidgetItem(QString::fromStdString(m.getModel())));
		tabel->setItem(nr, 3, new QTableWidgetItem(QString::fromStdString(m.getTip())));
		nr++;
	}*/
	tableModel->setMasini(masini);
}
