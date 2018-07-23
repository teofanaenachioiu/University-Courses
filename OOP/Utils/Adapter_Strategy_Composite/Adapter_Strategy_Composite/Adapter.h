/*#pragma once
#include<assert.h>
#include<iostream>
#include<vector>
#include <string>
using namespace std;

class Product {
private:
	int code;
	float price;
	string desc, type;
public:
	Product(int c, float p, string d, string t) :code{ c }, price{ p }, desc{ d }, type{ t } {}
	const int& getCode()const {
		return code;
	}
	const float& getPrice()const {
		return price;
	}
	const string& getDesc()const {
		return desc;
	}
	const string& getType()const {
		return type;
	}
};

class SaleItem {
private:
	int quantity;
public:
	Product prod;
	const int& getQuantity()const {
		return quantity;
	}
	SaleItem(Product p, int q) :prod{ p }, quantity{ q } {}
};

class Sale {
private:
	vector<SaleItem> date;
public:
	Sale() {};
	float getToatal(bool isCard) {
		float total = 0;
		for (auto e : date)
			if(isCard)
				total += e.getQuantity()*(e.prod.getPrice()*0.98);
			else total += e.getQuantity()*e.prod.getPrice();
		return total;
	}
	void addItem(int q, Product p) {
		date.push_back(SaleItem(p, q));
	}
};

void testAdapter(){
	Sale s;
	assert(s.getToatal(false) == 0);

	Product p1{ 1,2.0,"food","Apple" };
	Product p2{ 1,2000.0,"electronics","TV" };

	s.addItem(3, p1);
	assert(s.getToatal(false) == 6);

	s.addItem(1, p2);
	assert(s.getToatal(false) == 2006);
	int nr = s.getToatal(true);
	assert(nr == 1965);
}*/