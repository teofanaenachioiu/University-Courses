#pragma once
#include <vector>
class Observer {
public:
	virtual void update() = 0;
};
/*
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

};*/