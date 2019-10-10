// Tema1CPlusPlus.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include "Circle.h"
#include <iostream>

using namespace std;

void gcd()
{
	int a, b;

	cout << "a="; cin >> a;
	cout << "b="; cin >> b;


	while (a != b) {
		if (a > b)
			a = a - b;
		else
			b = b - a;
	}

	cout << "GCD:" << a << endl;
}

void numberSum() {
	double element, sum = 0;
	int n, index;

	for (index = 0; index < n; index=index+1) {
		cout << "element" << index << ": ";
		cin >> element;
		sum = sum + element;
	}

	cout << "SUM: " << sum << endl;
}

void circleCalcul() {
	Circle circle;
	int radius;
	double perimeter, area;

	cout << "r="; cin >> radius;

	circle = Circle(radius);

	perimeter = circle.getPerimeter();
	area = circle.getArea();

	cout << "PERIMETER: " << perimeter << endl;
	cout << "AREA: " << area << endl;
}


int main()
{
	circleCalcul();
}