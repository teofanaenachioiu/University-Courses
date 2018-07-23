#pragma once
#include <QtWidgets>
#include <qabstractitemmodel.h>
#include<vector>

class myTableModel:public QAbstractTableModel {
private:
	std::vector<std::vector<QString>> m_gridData;
public:
	myTableModel(){}
	int rowCount(const QModelIndex &parent = QModelIndex()) const {
		return 3;
	}
	int columnCount(const QModelIndex &parent = QModelIndex()) const {
		return 2;
	}
	QVariant data(const QModelIndex &index, int role = Qt::DisplayRole) const {
		if (role == Qt::DisplayRole)
			return QString("%1").arg(index.row() + index.column());
		if (role == Qt::FontRole) {
			QFont f;
			f.setBold(index.row() % 2 == 0);
			return f;
		}
		if (role == Qt::BackgroundRole) {
			QBrush bg{ Qt::red };
			return bg;
		}
		return QVariant();
	}

	QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const {
		if (role == Qt::DisplayRole) {
			if (orientation == Qt::Horizontal) return QString("Head oriz");
			else return QString("Head vertic");
		}
		return QVariant();
	}

	bool setData(const QModelIndex &index, const QVariant &value, int role = Qt::EditRole) {
		if (role == Qt::EditRole) {
			//m_gridData[index.row()][index.column()] = value.toString();
			QModelIndex topLeft = createIndex(index.row(), index.column());
			emit dataChanged(topLeft, topLeft);
		}
		return true;
	}
	Qt::ItemFlags flags(const QModelIndex &)const {
		return Qt::ItemIsSelectable | Qt::ItemIsEditable | Qt::ItemIsEnabled;
	}

};

class myListModel:public QAbstractListModel {
public:
	myListModel(){}

	int rowCount(const QModelIndex &parent = QModelIndex()) const {
		return 2;
	}

	QVariant data(const QModelIndex &index, int role = Qt::DisplayRole)const {
		if (role == Qt::DisplayRole) {
			return QString("Randul %1").arg(index.row() + 1);
		}
		return QVariant();
	}
};