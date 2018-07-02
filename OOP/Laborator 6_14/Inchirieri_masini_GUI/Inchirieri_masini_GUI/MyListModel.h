#pragma once
#include <QAbstractListModel>
#include "Masina.h"
#include <vector>
#include <qdebug.h>
#include "Service.h"
#include "Repository.h"

class MyListModel :public QAbstractListModel {
	std::vector<Masina> masini;
public:
	MyListModel(){	}
	MyListModel(const vector<Masina>& masini) :masini{ masini } {
	}

	int rowCount(const QModelIndex &parent = QModelIndex()) const override {
		return masini.size();
	}

	QVariant data(const QModelIndex & index, int role = Qt::DisplayRole) const override {
		if (role == Qt::DisplayRole) {
			auto m = masini[index.row()].getNrInmatr();
			return QString::fromStdString(m);
		}

		return QVariant{};
	}

	Masina getMasini(const QModelIndex & index) {
		return masini[index.row()];
	}

	void setMasini(const vector<Masina>& masini) {
		this->masini = masini;
		QModelIndex topLeft = createIndex(0, 0);
		QModelIndex bottomRight = createIndex(rowCount(),0);
		emit dataChanged(topLeft, bottomRight);
		emit layoutChanged();
	}
};
