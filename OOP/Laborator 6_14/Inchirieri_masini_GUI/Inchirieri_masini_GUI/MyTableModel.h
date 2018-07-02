#pragma once
#include<QAbstractTableModel>
#include "Service.h"
#include <vector>

class MyTableModel :public QAbstractTableModel {
	vector<Masina> masini;
public:
	MyTableModel() {}

	int rowCount(const QModelIndex & parent = QModelIndex()) const override {
		return masini.size();
	}
	int columnCount(const QModelIndex & parent = QModelIndex()) const override {
		return 4;
	}
	QVariant data(const QModelIndex & index, int role = Qt::DisplayRole) const override {
		if (role == Qt::DisplayRole) {
			Masina p = masini[index.row()];
			if (index.column() == 0) {
				return QString::fromStdString(p.getNrInmatr());
			}
			else if (index.column() == 1) {
				return QString::fromStdString(p.getProducator());
			}
			else if (index.column() == 2) {
				return QString::fromStdString(p.getModel());
			}
			else if (index.column() == 3) {
				return QString::fromStdString(p.getTip());
			}
		}
		return QVariant{};
	}

	Masina getMasini(const QModelIndex & index) {
		return masini[index.row()];
	}

	void setMasini(const vector<Masina>& masini) {
		this->masini = masini;
		QModelIndex topLeft = createIndex(0, 0);
		QModelIndex bottomRight = createIndex(rowCount(), columnCount());
		emit dataChanged(topLeft, bottomRight);//notify ca la observer
		emit layoutChanged();
	}
};