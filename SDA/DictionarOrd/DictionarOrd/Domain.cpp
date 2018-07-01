#include "Domain.h"
#include<assert.h>

ostream & operator<<(ostream & os, const Camera & c)
{
	os <<c.getNrCamera()<<" "<< c.getNume() << " " << c.getPret();
	return os;
}

bool operator==(const Camera & l, const Camera & r)
{
	return l.getNrCamera()==r.getNrCamera();
}

void testeDomain()
{
	Camera c = {5, "Alex",200 };
	assert(c.getNume() == "Alex");
	assert(c.getPret() == 200);
	assert(c.getNrCamera() == 5);
	assert(c.setNume("Alexandru") == "Alexandru");
	assert(c.setPret(180) == 180);
	Camera c1 = c;
	assert(c1.getNrCamera() == 5);
	assert(c1.getNume() == "Alexandru");
	assert(c1.getPret() == 180);
}
