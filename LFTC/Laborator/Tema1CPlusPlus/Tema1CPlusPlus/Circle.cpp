#include "pch.h"
#define _USE_MATH_DEFINES
#include <cmath>
#include "Circle.h"

using namespace std;

Circle::Circle(int radius)
{
	this->radius = radius;
}

Circle::Circle()
{
}

Circle::~Circle()
{
}

double Circle::getArea()
{
	return M_PI * radius * radius;
}

double Circle::getPerimeter()
{
	return 2 * M_PI * radius;
}
