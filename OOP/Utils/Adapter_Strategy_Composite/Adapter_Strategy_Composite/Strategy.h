/*#pragma once
#include<assert.h>
#include<iostream>
#include<vector>
#include <string>
using namespace std;

class Product {
private:
	int code;
	double price;
	string desc, type;
public:
	Product(int c, double p, string d, string t) :code{ c }, price{ p }, desc{ d }, type{ t } {}
	const int& getCode()const {
		return code;
	}
	const double& getPrice()const {
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

class Sale;

class DiscountPolicy {
public:
	virtual double getDiscount(Sale* s, SaleItem si) = 0;
};

class CreditCardDiscount :public DiscountPolicy {
	double getDiscount(Sale* s, SaleItem si){
		return si.getQuantity()*si.prod.getPrice()*0.02;
	}
};

class NoDiscount :public DiscountPolicy {
	double getDiscount(Sale*s, SaleItem si) {
		return 0;
	}
};

class Sale {
private:
	vector<SaleItem> date;
	DiscountPolicy* dis;
public:
	Sale(DiscountPolicy *dis) : dis{ dis } {}

	double getToatal() {
		double total = 0;
		for (auto e : date)
			total += e.getQuantity()*e.prod.getPrice()-dis->getDiscount(this,e);
		return total;
	}

	void addItem(int q, Product p) {
		date.push_back(SaleItem(p, q));
	}
};

void testStrategy() {
	Sale s(new NoDiscount());
	assert(s.getToatal() == 0);

	Product p1{ 1,2.0,"food","Apple" };
	Product p2{ 1,2000.0,"electronics","TV" };

	s.addItem(3, p1);
	assert(s.getToatal() == 6);

	s.addItem(1, p2);
	assert(s.getToatal() == 2006);

	Sale s1(new CreditCardDiscount());
	s1.addItem(3, p1);
	s1.addItem(1, p2);
	assert(s1.getToatal() == 1965.88);
}*/