#pragma once
class Circle
{
private:
	int radius = 0;
public:
	Circle(int radius);
	Circle();
	~Circle();
	double getPerimeter();
	double getArea();
};

