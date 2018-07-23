#pragma once
#include<vector>
#include<qregion.h>
#include<qevent.h>
#include<qpainter.h>
#include<qwidget.h>
using namespace std;
class Desene: public QWidget{
private:
	vector<QColor> colors{ Qt::red, Qt::black, Qt::green,Qt::blue};
public:
	QRegion * region = new QRegion(0, 0, 1000, 1000);
	QPaintEvent * e = new QPaintEvent(*region);
	
	void paintEvent(QPaintEvent *) override {
		QPainter painter(this);
		painter.fillRect(20,30,50,60, Qt::red);
		//repaint();
	}
	Desene() { paintEvent(nullptr); }
	
};
