#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>

#include<iostream>
#include<string>
#include<vector>
#include<assert.h>
#include<algorithm>
#include<deque>
using namespace std;

//Afisare -> Polimorfism pentru tiparire: Exercitiu din curs
/*class B {
private:
	int b;
public:
	B(int b) :b{ b } {	}
	virtual void tiparire() {
		cout << b<<" ";
	}
	virtual ~B(){}
};

class D :public B {
private:
	string d;
public:
	D(int b, string d):B(b),d{d}{}
	void tiparire() override {
		B::tiparire();
		cout << d << " ";
	}
	~D()override{}
};

vector<B*> fct1() {
	vector<B*> lista;
	lista.push_back(new B(8));
	lista.push_back(new D(5, "D5"));
	lista.push_back(new B(-3));
	lista.push_back(new D(9, "D9"));
	return lista;
}

void fct2() {
	vector<B*> lista = fct1();
	for (const auto &el : lista) {
		el->tiparire();
		cout << endl;
	}
	for (const auto &el : lista) {
		delete el;
	}
}*/

//UML -> Employe, Manager: Exercitiu din curs(SIMPLU)
/*class Employe {
private:
	string nume;
public:
	Employe(string nume):nume{nume}{}
	virtual void toString() {
		cout << nume;
	}
	virtual ~Employe() {}
};

class Manager :public Employe {
private:
	int bonus;
public:
	Manager(string nume, int bonus):Employe(nume),bonus{bonus}{}
	void toString() override {
		cout << "Manager: ";
		Employe::toString();
	}
	~Manager() override{}
};

void tipareste() {
	vector<Employe*> lista;
	lista.push_back(new Employe("Ion"));
	lista.push_back(new Manager("Andrei", 1000));
	lista.push_back(new Employe("Vasile"));
	for (const auto &el : lista) {
		el->toString();
		cout << endl;
	}
	for (const auto &el : lista) {
		delete el;
	}
}*/

//Afisare -> Subiect 10.06.2015
/*class B {
public:
	virtual void f() {
		cout << "f";
	}
	virtual ~B() {
		cout << "~B";
	}
};
class D : public B {
private:
	B * b;
public:
	D(B* b=0):B(*b){}
	void f() {
		b->f(); cout << "g";
	}
	virtual ~D() { 
		cout << "~D"; 
	}
};

void fct() {
	B* b[] = { new B(), new D() };
	b[0]->f();
	B&c = *b[1];
	c.f();
}
*/

//Supracriere -> Note
/*template<typename T>
class grades {
private:
	vector<T> note;
public:
	grades(){}
	grades operator+(int n) {
		note.push_back(n);
		return *this;
	}
	const int gretNRGrades() const {
		return note.size();
	}
	auto begin() {
		return note.begin();
	}
	auto end() {
		return note.end();
	}
	~grades(){}
};

void fct() {
	grades<int> myg;
	myg = myg + 10;
	myg = myg + 9;
	double avg = 0.0;
	for (auto g : myg) {
		avg += g;
	}
	cout << avg / myg.gretNRGrades() << endl;
}*/

//Afisare -> vector dimanic de clase A, copy constructor-> constructor,constructor, copyc, copyc, distruge,distrugee, print,distruge,distruge
/*class A {
public:
	A() { cout << "A" << endl; }
	A(const A& ot) { cout << "copy" << endl; }
	~A() { cout << "~A" << endl; }
	void print() { cout << "print" << endl; }
};
void f() {
	vector<A> a{A(),A()};
	a[1].print();
}*/

//UML -> University, payable 
/*class Payable;
class DuplicateIDException {
private:
	string msg;
public:
	DuplicateIDException(string msg):msg{msg}{}
	const string & getMessage()const {
		return msg;
	}
	~DuplicateIDException(){}
};

class Payable {
private:
	string id;
public:
	Payable(string id):id{id}{}
	const string &getId() const{
		return id;
	}
	virtual double monthlyIncome() = 0;
	string toString() {
		string rez = id;
		rez += " ";
		rez += to_string(monthlyIncome());
		cout << rez << endl;
		return rez;
	}
	virtual ~Payable(){}
};

class Student :public Payable {
private:
	double scholarship;
public:
	Student(string id,double scholarship):Payable(id),scholarship{scholarship}{}
	double monthlyIncome() override {
		return scholarship;
	}
	virtual ~Student(){}
};

class Teacher :public Payable {
private:
	double salary;
public:
	Teacher(string id, double salary) :Payable(id), salary{ salary } {}
	double monthlyIncome() override {
		return salary;
	}
	virtual ~Teacher(){}
};

class University {
private:
	string nume;
	vector<Payable*> persoane;
public:
	University(string nume):nume{nume}{}
	Payable * findPayableById(const string& id) {
		for (const auto& el : persoane) {
			if (el->getId() == id)
				return el;
		}
		return nullptr;
	}
	void addPayable(Payable * p) {
		if (findPayableById(p->getId()) == nullptr) {
			persoane.push_back(p);
		}
		else throw DuplicateIDException("Id " + p->getId() + " existent");
	}
	const vector<Payable *> getAllPayables()const {
		return persoane;
	}
	const double totalAmountToPay() const {
		double S = 0;
		for (const auto &p : persoane) {
			S += p->monthlyIncome();
		}
		return S;
	}
	~University() {
		for (const auto &el : persoane) {
			delete el;
		}
	}
};

void teste() {
	University u("UBB");
	assert(u.getAllPayables().size() == 0);
	Student *s = new Student("2287", 1000.0);
	u.addPayable(s);
	assert(u.getAllPayables().size() == 1);
	assert(u.totalAmountToPay() == 1000.0);
	Teacher *t = new Teacher("2287", 2000.0);
	try {
		u.addPayable(t);
		assert(false);
	}
	catch (DuplicateIDException &ex) {
		assert(true);
		assert(ex.getMessage() == "Id 2287 existent");
		delete t;
	}
}*/

//UML -> Mancare
/*class Mancare {
private:
	int pret;
public:
	Mancare(int pret):pret{pret}{}
	Mancare() = default;
	virtual string descriere() = 0;
	virtual int getPret() {
		return pret;
	}
	virtual ~Mancare(){}
};

class Burger : public Mancare {
private:
	string nume;
public:
	Burger(string nume, int pret):nume{nume}, Mancare(pret){}
	string descriere() override {
		return nume;
	}
	virtual ~Burger(){}
};

class CuSos : public Mancare {
private:
	Mancare* m;
public:
	CuSos(Mancare* m) :m{ m } { }
	string descriere() override {
		return m->descriere()+" cu sos";
	}
	int getPret() override {
		return m->getPret()+2;
	}
	virtual ~CuSos() { delete m; }
};

class CuCartofi : public Mancare {
private:
	Mancare * m;
public:
	CuCartofi(Mancare *m) :m{ m } {  }
	string descriere() override {
		return m->descriere() + " cu cartofi";
	}
	 int getPret() override {
		return m->getPret() + 3;
	}
	virtual ~CuCartofi() { delete m;}
};

void mancare() {
	vector<Mancare*> lista;
	Mancare *m1 = new Burger("burger Big Mac", 10);
	Mancare *m2 = new Burger("burger Big Mac", 10);
	Mancare *m3 = new CuSos(m2);
	Mancare *m4 = new CuCartofi(m3);
	Mancare *m5 = new Burger("burger Zinger", 13);
	Mancare *m6 = new CuCartofi(m5);
	lista.push_back(m1);
	lista.push_back(m4);
	lista.push_back(m6);
	sort(lista.begin(), lista.end(), [](Mancare* m1, Mancare*m2) {return m1->getPret() < m2->getPret(); });
	for (const auto&el : lista) {
		cout << el->descriere() <<" "<< el->getPret()<< endl;
	}
	for (const auto &el : lista) {
		delete el;
	}
}*/

//Suprascriere -> Carnet
/*template<typename T>
class Carnet {
private:
	vector<pair<string, T>> note;
public:
	Carnet() = default;
	Carnet& add(string m, T n) {
		note.push_back(make_pair(m, n));
		return *this;
	}
	Carnet& removeLast() {
		note.pop_back();
		return *this;
	}
	T operator[](string mat) {
		for (const auto& el : note) {
			if (el.first == mat)
				return el.second;
		}
		throw exception("Nu exista");
	}
};

void anscolar() {
	Carnet<int> cat;
	cat.add("SDA", 9);
	cat.add("OOP", 7).add("FP", 10);
	cout << cat["OOP"];
	cat.removeLast().removeLast();
	try {
		cout << cat["OOP"];
	}
	catch (exception& ex) {
		cout << "Nu exista nota pentru OOP";
	}
}*/

//Afisare -> contine eroare la initializarea vectorului !!!
/*class A {
public:
	virtual void print() = 0;
};

class B : public A {
public:
	virtual void print() {
		cout << "printB";
	}
};

class C : public B {
public:
	virtual void print() {
		cout << "printC";
	}
};

void verif() {
	vector<A&> v;//vector de referinte nu se poate!!! o referinta nu mai poate fi modificata odata asignata. Obiectele din container trebuie sa fie asignabile
	B b;
	C c;
	v.push_back(b);
	v.push_back(c);
	for (auto e : v) {
		e.print();
	}
}*/

//Afisare -> exceptii, erori
/*void f(bool b) {
	cout << "1";
	if (b) {
		throw exception("Error");
	}
	cout << "3";
}

void g() {
	try {
		f(false);
		f(true);
		f(false);
	}
	catch (exception &ex) {
		cout << "4";
	}
}*/

//UML -> Channel, contact
/*
class Channel {
public:
	Channel() = default;
	virtual void send(string msg) = 0;
	virtual ~Channel(){}
};

class Telefon :public Channel {
private:
	int nrTel;
public:
	Telefon(int nrTel):nrTel{nrTel}{}
	virtual void send(string msg) {
		int nr = rand() % 2;
		if (nr == 0) {
			cout << "dail: " << nrTel << endl;
		}
		else throw exception("Ocupat. Incerca altceva");
	}
	virtual ~Telefon(){}
};

class Sms :public Telefon {
public:
	Sms(int nr):Telefon(nr){}
	void send(string msg) override {
		Telefon::send(msg);
		cout << "sending sms" << endl;
	}
	~Sms(){}
};

class Fax :public Telefon {
public:
	Fax(int nr) :Telefon(nr) {}
	void send(string msg) override {
		Telefon::send(msg);
		cout << "sending fax"<< endl;
	}
	~Fax() {}
};

class Failover : public Channel {
private:
	Channel * ch1;
	Channel * ch2;
public:
	Failover(Channel *ch1,Channel *ch2):ch1{ch1},ch2{ch2}{}
	void send(string msg) override {
		try {
			ch1->send(msg);
		}
		catch (exception &){
			try {
				ch2->send( msg);
			}
			catch (exception &) {}
		}
		
	}
	~Failover() {
		delete ch1;
		delete ch2;
	}
};

class Contact {
private:
	Channel * ch1;
	Channel * ch2;
	Channel * ch3;
public:
	Contact(Channel *ch1, Channel *ch2, Channel *ch3):ch1{ch1},ch2{ch2},ch3{ch3}{}
	void sendAlarm(string msg) {
		while (true) {
			try {
				ch1->send(msg);
				break;
			}
			catch (exception &) {}
			try {
				ch2->send(msg);
				break;
			}
			catch (exception &) {}
			try {
				ch3->send(msg);
				break;
			}
			catch (exception &) {}
		}
	}
	~Contact() {
		delete ch1;
		delete ch2;
		delete ch3;
	}
};

void canale() {
	Channel *c1 = new Telefon(123);
	Channel *c2 = new Failover(new Sms(234), new Fax(345));
	Channel *c3 = new Failover(new Failover(new Telefon(456), new Sms(567)),new Fax(678));
	Contact c{ c1,c2,c3 };
	c.sendAlarm("incearca");
}*/

//UML -> ONG, participant, angajat
/*class Participant {
public:
	Participant() {}
	virtual void tipareste() = 0;
	virtual bool eVoluntar() {
		return true;
	}
	virtual ~Participant(){}
};

class Personal : public Participant {
private:
	string nume;
public:
	Personal (string nume):nume{nume}{}
	virtual void tipareste() {
		cout << nume;
	}
	virtual ~Personal(){}
};

class Admin :public Personal {
public:
	Admin(string nume):Personal(nume){}
	void tipareste() override {
		cout << "Administrator ";
		Personal::tipareste();
	}
	~Admin(){}
};

class Director :public Personal {
public:
	Director(string nume) :Personal(nume) {}
	void tipareste() override {
		cout << "Director ";
		Personal::tipareste();
	}
	~Director() {}
};

class Angajat: public Participant {
private:
	Participant * p;
public:
	Angajat (Participant *p):p{p}{}
	void tipareste() override {
		cout << "Angajat ";
		p->tipareste();
	}
	bool eVoluntar() override {
		return false;
	}
	~Angajat() { delete p; }
};

class ONG {
private:
	vector<Participant*> pps;
public:
	ONG() = default;
	void add(Participant *p) {
		pps.push_back(p);
	}
	vector<Participant*> getAll(bool voluntari) {
		vector<Participant*> rez;
		if (voluntari) {
			for (const auto & el : pps) {
				if (el->eVoluntar())
					rez.push_back(el);
			}
		}
		else {
			for (const auto & el : pps) {
				if (el->eVoluntar()==false)
					rez.push_back(el);
			}
		}
		return rez;
	}
	~ONG() {
		for (const auto& el : pps) {
			delete el;
		}
	}
};

void ong_uri() {
	ONG o;
	o.add(new Admin("Ionut"));
	o.add(new Angajat(new Admin("Ana")));
	o.add(new Director("Maria"));
	o.add(new Angajat(new Director("Vlad")));
	cout << "Voluntarii:" << endl;
	for (const auto& el : o.getAll(true)) {
		el->tipareste();
		cout << endl;
	}
	cout << "Angajatii:" << endl;
	for (const auto& el : o.getAll(false)) {
		el->tipareste();
		cout << endl;
	}
}*/

//Suprascriere -> Cos
/*template<typename T>
class Cos {
private:
	vector<T> lista;
public:
	Cos() = default;
	Cos & operator+(const T&el){
		lista.push_back(el);
		return *this;
	}
	Cos & undo() {
		lista.pop_back();
		return *this;
	}
	void tipareste(ostream& stream) {
		for (const auto & el : lista) {
			stream << el<<endl;
		}
		return;
	}
	~Cos(){}
};

void cos_functie(){
	Cos<string> cos;
	cos = cos + "Mere";
	cos.undo();
	cos + "Mere";
	cos = cos + "Paine" + "Lapte";
	cos.undo().undo();
	cos.tipareste(cout);
}*/

//Afisare -> constructori, destructori, vector static de pointeri, functii fara virtual/override (apeleaza functiile din clasa de baza)
/*class B {
public:
	B() {
		cout << "B()";
	}
	void print() {
		cout << "B";
	}
	virtual ~B() {
		cout << "~B()";
	}
};

class D: public B {
public:
	D() {
		cout << "D()";
	}
	void print() {
		cout << "D";
	}
	virtual ~D() {
		cout << "~D()";
	}
};

void functieBD() {
	B b[2];
	//D();
	//cout << endl;
	//B();
	//b[1] = D();
	//b[1].print();
	B* b[] = { new B(),new D() };
	b[0]->print();
	b[1]->print();
	delete b[0];
	delete b[1];
}*/

//Afisare -> exceptie
/*class E {
public:
	E() {
		cout << "E()";
	}
	E(const E& e) {
		cout << "E(const E&)";
	}
	~E() {
		cout << "~E()";
	}
};
int fun(int a[], int n) {
	if (n < 0) throw 1;
	else if (n == 0) throw E();
	return 0;
}

void fct() {
	int v[2] = {};
	try {
		cout << fun(v, 0);
	}
	catch (E &e){
		cout << "Aici prinde exceptia";
		}
	catch (...) { cout << "*"; }
}*/

//Afisare ->  referinte, pointeri 1
/*void functie(){
	int a = 1, b[] = { a,1 };
	int &c = *b;
	*b = 2;
	int*d = &c;
	*d = 3;
	cout << a << " " << b[0] << " " << c << " " << *d << endl;
}*/

//Afisare -> pointeri, referinte 2
/*void functie(){
	int a = 1;
	int b = 2;
	int *c = &a;
	int &d = *c;
	c = &b;
	d++;
	cout << a << " " << b << " " << *c << " "<< d << endl;
}*/

//Suprascriere -> Integer, Stack
/*class Integer {
private:
	int nr;
public:
	Integer(int nr):nr{nr}{}
	~Integer(){}
	const int get() const {
		return nr;
	}
	friend ostream& operator<<(ostream &os, const Integer & nr);
};

ostream& operator<<(ostream &os, const Integer & nr) {
	os << nr.get();
	return os;
}

class Stack {
private:
	vector<Integer> v;
public:
	Stack(){}
	~Stack(){}
	void push(const Integer & el) {
		v.push_back(el);
	}
	const Integer pop() {
		Integer nr =v.back();
		v.pop_back();
		return nr;
	}
	const bool emplty() const {
		return v.empty();
	}
};

void stackint() {
	Stack stack;
	stack.push(Integer(1));
	stack.push(Integer(2));
	while (!stack.emplty()) {
		cout << stack.pop()<<" ";
	}
}*/

//UML -> Quackable Simulator (nu merge)
/*class Quackable {
public:
	Quackable() {}
	virtual ~Quackable(){}
	virtual string quack() = 0;
};

class RedHeadDuck:public Quackable {
public:
	RedHeadDuck(){}
	virtual ~RedHeadDuck(){}
	string quack() override {
		return "quack";
	}
};

class QuackableCounter :public Quackable {
private:
	Quackable * q;
	int quackcount;
public:
	QuackableCounter(Quackable *q) :q{ q } { quackcount = 0; }
	virtual ~QuackableCounter() { 
		delete q; 
		cout << "Counter: "<<quackcount << endl;
	}
	string quack() override {
		if (q->quack() == "quack")
		{
			quackcount++;
			q->quack();
		}
		return "";
	}
};

class Flock : public Quackable {
private:
	vector<Quackable*> v;
public:
	Flock(){}
	virtual ~Flock() { for (const auto & q : v) delete q; }
	string quack() override {
		bool gasit = false;
		for (const auto& q : v) {
			cout<<q->quack()<<endl;
			gasit = true;
		}
		if (gasit) return "quack";
		return "";
	}
	void add(Quackable *q) {
		v.push_back(q);
	}
};

class Simulator {
public:
	Simulator() = default;
	~Simulator() = default;
	void simulate(Quackable *q) {
		 q->quack();
	}
};

void rate() {
	Simulator s;
	RedHeadDuck *r = new RedHeadDuck();
	s.simulate(r);
	delete r;

	Flock *f1 = new Flock;
	f1->add(new RedHeadDuck);
	f1->add(new RedHeadDuck);
	s.simulate(f1);
	delete f1;

	Flock *f2 = new Flock;
	RedHeadDuck *r1 = new RedHeadDuck();
	RedHeadDuck *r2 = new RedHeadDuck();
	RedHeadDuck *r3 = new RedHeadDuck();
	f2->add(r1);
	f2->add(r2);
	f2->add(r3);
	s.simulate(f2);
	QuackableCounter *counter = new QuackableCounter(f2);
	delete counter;
}*/

//UML -> Fructe, citrice
/*class Fruit {
private:
	double price;
public:
	Fruit(double price):price{price}{}
	Fruit(){}
	virtual ~Fruit(){}
	virtual bool isWithSeeds() {
		return true;
	}
	virtual double getPrice() {
		return price;
	}
	virtual void description() = 0;
};

class Citrus:public Fruit {
private:
	string name;
public:
	Citrus(string name,double price):name{name},Fruit(price){}
	virtual ~Citrus(){}
	void description() override {
		cout << name;
	}
};

class FruitWithoutSeeds :public Fruit {
private:
	Fruit * f;
public:
	FruitWithoutSeeds(Fruit *f1):f{f1}{}
	virtual ~FruitWithoutSeeds() { delete f; }
	bool isWithSeeds() override{
		return false;
	}
	double getPrice()override {
		return f->getPrice()*6/5 ;
	}
	void description() override {
		f->description();
		cout << " fara seminte";
	}
};

class ShoppingList {
private:
	vector<Fruit*> v;
public:
	ShoppingList(){}
	void add(Fruit *f) {
		v.push_back(f);
	}
	vector<Fruit*> getAll(bool withSeeds) {
		vector<Fruit*> rez;
		if (withSeeds) {
			for (const auto &el : v) {
				if (el->isWithSeeds())
					rez.push_back(el);
			}
		}
		else {
			for (const auto &el : v) {
				if (!el->isWithSeeds())
					rez.push_back(el);
			}
		}
		return rez;
	}
};

void fructe() {
	ShoppingList s;
	s.add(new FruitWithoutSeeds(new Citrus("mandarina", 2)));
	s.add(new Citrus("portocala", 3));
	s.add(new FruitWithoutSeeds(new Citrus("clementina", 1)));
	s.add(new Citrus("grapefruit", 5));
	vector<Fruit*> rez = s.getAll(false);
	sort(rez.begin(), rez.end(), [](Fruit *f1, Fruit*f2) {return f1->getPrice() > f2->getPrice(); });
	for (const auto& el : rez) {
		el->description();
		cout << " " << el->getPrice() <<" RON"<< endl;
	}
	rez = s.getAll(true);
	sort(rez.begin(), rez.end(), [](Fruit *f1, Fruit*f2) {return f1->getPrice() > f2->getPrice(); });
	for (const auto& el : rez) {
		el->description();
		cout << " " << el->getPrice() << " RON" << endl;
	}
}*/

//Suprascriere -> Buchet, Flori
/*class Floare {
private:
	string nume;
public:
	Floare(string nume) :nume{ nume } {}
	~Floare() {}
	const string& getNume() const {
		return nume;
	}
	bool operator==(Floare r) {
		if (getNume() == r.getNume())
			return true;
		else return false;
	}
	bool operator!=(Floare r) {
		if (getNume() != r.getNume())
			return true;
		else return false;
	}
	friend ostream& operator<<(ostream& os, const Floare& f);
};
ostream & operator<<(ostream & os, const Floare & f)
{
	return os << f.getNume();
}

template<typename T>
class Buchet {
private:
	vector<T> b;
public:
	Buchet() {}
	~Buchet() {}
	auto begin() {
		return b.begin();
	}
	auto end() {
		return b.end();
	}
	auto size() {
		return b.size();
	}
	T at(int poz) {
		return b.at(poz);
	}
	Buchet& operator+(const T& f) {
		b.push_back(f);
		return *this;
	}
	Buchet& operator-(const T& f) {
		auto it = find_if(b.begin(), b.end(), [&f](T ff) {return ff == f; });
		if (it != b.end()) {
			b.erase(it);
		}
		return *this;
	}
	bool operator==( Buchet<T> l) {
		if (size() != l.size()) return false;
		else {
			for (auto i = 0; i < size(); i++)
				if (at(i) != Floare(l.at(i)))
					return false;
		}
		return true;
	}
	friend Buchet<T> operator*(const T& f, int nr);
	Buchet<T>& operator+(const vector<T>& ot) {
		for (const auto& f : ot)
			*this+f;
		return *this;
	}
	Buchet<T> operator=(Buchet<T> ot) {
		if (*this == ot)
			return *this;
		Buchet<T> rez;
		for (const auto& f : ot) {
			rez + f;
		}
		return rez;
	}
	void tiparire_flori(ostream & os) {
		vector<T> c;
		for (const auto& f : b) {
			auto it = find_if(c.begin(), c.end(), [&f](T f1) {return f.getNume() == f1.getNume(); });
			if (it == c.end()) c.push_back(f);
		}
		for (const auto & f : c)
			os << f << endl;
	}
};

template<typename T>
vector<T> operator*(const T & f, int nr)
{
	vector<T> bb;
	for(int i=0;i<nr;i++) bb.push_back(f);
	return bb;
}

void buchet_flori() {
	Buchet<Floare> b, bb;
	b = b + Floare("Frezie");
	b = b + Floare("Lalea") * 6;
	b - Floare("Lalea");
	b = b + Floare("Frezie") * 4 + Floare("Iris") * 3;
	bb = b;
	b.tiparire_flori(cout);
}*/

//Suprascriere -> Nr
/*class Nr{
private:
	vector<int> nrv;
public:
	Nr(long int n) {
		int cifra;
		do {
			cifra = n % 10;
			n = n / 10;
			nrv.push_back(cifra);
		} while (n>0);
		sort(nrv.begin(), nrv.end());
	}
	~Nr() {}
	auto begin() {
		return nrv.begin();
	}
	auto end() {
		return nrv.end();
	}
	auto size() {
		return nrv.size();
	}
	Nr& operator++() {
		nrv.push_back(nrv.back() + 1);
		nrv.erase(nrv.begin());
		return *this;
	}
	void adauga(long int n) {
		int cifra;
		do {
			cifra = n % 10;
			n = n / 10;
			nrv.push_back(cifra);
		} while (n>0);
		sort(nrv.begin(),nrv.end());
	}
	int operator[](string poz) {
		string nrm = "";
		int s,e,i = 0;
		if (poz[0] == '-') s = 0;
		else {
			while (poz[i] != '-') {
				nrm = nrm + poz[i];
				i++;
			}
			s = stoi(nrm);//nr start
			nrm = "";
		}
		i++;//spar peste liniuta
		if (poz[i] == NULL) e = poz.size() - 1;
		else {
			while (poz[i] != NULL) {
				nrm = nrm + poz[i];
				i++;
			}
			e = stoi(nrm);
		}
		int toReturn = 0,p = 1;
		for (int i = e; i >= s; i--) {
			toReturn = toReturn + nrv[i] * p;
			p *= 10;
		}
		return toReturn;
	}
	bool operator==(int a) {
		int p = 1;
		for (int i = nrv.size() - 1; i >= 0; i--) {
			if ((a / p) % 10 != nrv[i])
				return false;
			p = p * 10;
		}
		return true;
	}
	int operator*() {
		int nrm = nrv.front();
		nrv.erase(nrv.begin());
		return nrm;
	}
	Nr& operator/(int nr) {
		for (int i = 0; i<nr; i++)
			nrv.erase(nrv.end() - 1);
		return *this;
	}
	friend ostream& operator<<(ostream &os,  Nr n);
};

ostream& operator<<(ostream &os, Nr n) {
	for (const auto& el : n)
		os << el;
	return os;
}
void numere() {
	Nr n{ 123456 };
	++n; //n=234567
	n.adauga(123456);//n=122334455667
	int a = n["3-6"];//a=3344
	if (n == a) {
		cout <<"a: "<< a;
	}
	else {
		int b = *n;//b=1, n=22334455667
		cout << "b: "<<b << endl;
	}
	n = n / 5; //n=223344
	cout <<"n: "<< n;//afiseaza 223344
}*/

//Afisare -> exception, what()
/*void exc(const int& n) {
	if (n == 0)
		throw exception("Am primit 0");
	else if(n < 0) throw exception("Am primit <0");
	else throw exception("Am primit >0");
}

void toExc() {
	try {
		exc(0);
		assert(false);
	}
	catch (exception &e) {
		string str = e.what();
		assert(str == "Am primit 0");
	}
}*/

//Afisare -> IO
/*void testFormatOutput() {
	cout.width(5);
	cout << "a";
	cout.width(5);
	cout << "bb" << endl;
	const double PI = 3.1415926535897;
	cout.precision(3);
	cout << PI << endl;
	cout.precision(8);
	cout << PI << endl;
}

void testManipulators() {
	cout << oct << 9 << endl << dec << 9 << endl;
	oct(cout);
	cout << 9;
	dec(cout);
}*/

//Suprascriere -> Vector dinamic
template<typename T>
class Iterator;
template<typename T>
class VectorD {
private:
	int dim;
	int capacity;
	T *elems;
	void mareste() {
		int i;
		this->capacity = this->capacity * 2;
		T * elemss = new T[this->capacity];
		for (i = 0; i<this->dim; i++) {
			elemss[i] = elems[i];
		}
		delete[] elems;
		this->elems = elemss;
	}
public:
	VectorD() {
		dim = 0;
		capacity = 3;
		elems = new T[capacity];
	}
	~VectorD() {
		delete[] elems;
	}

	void push_back(const T& el) {
		if (dim == capacity) mareste();
		elems[dim] = el;
		dim++;
	}
	void clear() {
		dim = 0;
	}
	const bool empty() const {
		return dim == 0;
	}
	const T& back() const {
		return elems[dim - 1];
	}
	const T& front() const {
		return elems[0];
	}
	const int& size() const {
		return this->dim;
	}
	const T& operator[](int poz) const {
		return elems[poz];
	}

	friend class Iterator<T>;
	Iterator<T> begin() {
		return Iterator<T>(*this);
	}
	Iterator<T> end() {
		return Iterator<T>(*this, dim);
	}
};

template<typename T>
class Iterator {
private:
	const VectorD<T>& vect;
	int curent;
public:
	Iterator(const VectorD<T>& v, int index = 0) :vect{ v }, curent{ index } {}
	~Iterator() {}
	friend class VectorD<T>;
	bool valid() {
		return curent < vect.size();
	}
	const T& element() const {
		return vect[curent];
	}
	void urmator() {
		curent++;
	}
	const T& operator*() {
		return element();
	}
	bool operator!=(const Iterator<T>& ot) {
		return ot.element() != vect[curent];
	}
	const Iterator& operator++() {
		++curent;
		return *this;
	}
	const Iterator& operator++(int) {
		Iterator<T> tmp(vect,curent);
		++*this;
		return tmp;
	}
};

void testVector() {
	VectorD<int> v;
	v.push_back(1);
	assert(v.front() == 1);
	v.push_back(6);
	assert(v.back() == 6);
	v.push_back(9);
	v.push_back(2);
	assert(v.size() == 4);
	v.push_back(2);
	assert(v[2] == 9);
	for (const auto&el : v) {
		cout << el << endl;
	}
	Iterator<int> it{ v };
	it++;
	cout <<"cu it++: " *it << endl;
	v.clear();
	assert(v.empty() == true);
}

//Afisare -> deque
/*void fct() {
	deque<string> s;
	s.push_back("A");
	s.push_back("B");
	s.push_back("C");
	s.push_back("D");
	auto it = s.begin();
	auto end = s.end();
	end--;//ma duce la D(exclusiv)
	end--;//ma duce la C(exclusiv)
	while (it != end) {
		cout << *it << endl;
		it++;
	}
}*/

//Suprascriere -> expresie, undo
/*template<typename T>
class Expresie {
private:
	vector<T> e;
public:
	Expresie(T nr) { e.push_back(nr); }
	~Expresie() {}
	Expresie& operator+(const T& nr) {
		e.push_back(e.back() + nr);
		return *this;
	}
	Expresie& operator-(const T& nr) {
		e.push_back(e.back() - nr);
		return *this;
	}
	Expresie& undo() {
		e.pop_back();
		return *this;
	}
	const T& result() const {
		return e.back();
	}
	friend ostream& operator<<(ostream &os, const Expresie<T>& e);
};
template<typename T>
ostream & operator<<(ostream & os, const Expresie<T>& e)
{
	os << e.result();
	return os;
}

void calcul(){
	Expresie<int> e{ 3 };
	e = e + 7;
	e = e + 3 - 8;
	cout << e << endl;
	e.undo();
	cout << e << endl;
	e.undo().undo();
	cout << e << endl;
}*/

//Suprascriere -> Cos, paine,lapte
/*template<typename T>
class Cos {
private:
	vector<T> v;
public:
	Cos(T str) { v.push_back(str); }
	~Cos() {}
	auto begin() {
		return v.begin();
	}
	auto end() {
		return v.end();
	}
	Cos& operator+(const T& el) {
		v.push_back(el);
		return *this;
	}
	Cos& operator-(const T&el) {
		auto it = find_if(v.begin(), v.end(), [&el](const T& e) {return el == e; });
		if (it != v.end()) v.erase(it);
		return *this;
	}
};

void cumparaturi() {
	Cos<string> cos{ "Ion" };
	cos = cos + "Mere";
	cos = cos + "Paine" + "Lapte";
	cos - "Paine";
	for (auto i : cos)
		cout << i << endl;
}*/

//UML -> Masini
/*class Vehicul {
private:
	int pret;
public:
	Vehicul() = default;
	Vehicul(const int& pret) :pret{ pret } {}
	virtual ~Vehicul() {}
	virtual void descriere() = 0;
	virtual int getPret() {
		return this->pret;
	}
};

class Masina :public Vehicul {
private:
	string nume;
public:
	Masina(const string& nume, const int& pret) :nume{ nume }, Vehicul(pret){}
	virtual ~Masina() {}
	void descriere() override {
		cout << nume;
	}
};

class SchimbatorAutomat :public Vehicul {
private:
	Vehicul * v;
public:
	SchimbatorAutomat(Vehicul *v) :v{ v } {}
	virtual ~SchimbatorAutomat() { delete v; }
	void descriere() override {
		v->descriere();
		cout << " cu schimbator automat";
	}
	int getPret() override {
		return v->getPret() + 4000;
	}
};

class SenzoriParcare :public Vehicul {
private:
	Vehicul * v;
public:
	SenzoriParcare(Vehicul *v) :v{ v } {}
	virtual ~SenzoriParcare() { delete v; }
	void descriere() override {
		v->descriere();
		cout << " cu senzori de parcare";
	}
	int getPret() override {
		return v->getPret() + 2500;
	}
};

vector<Vehicul*> getLista() {
	vector<Vehicul*> rez;
	rez.push_back(new Masina("Audi", 60000));
	rez.push_back(new SenzoriParcare(new SchimbatorAutomat(new Masina("Audi", 60000))));
	rez.push_back(new SchimbatorAutomat(new Masina("Mercedes", 55000)));
	rez.push_back(new SenzoriParcare(new Masina("Opel", 30000)));
	return rez;
}

void tiparire() {
	vector<Vehicul*> v = getLista();
	sort(v.begin(), v.end(), [](Vehicul*v1, Vehicul*v2) {return v1->getPret()>v2->getPret(); });
	for (const auto& el : v) {
		el->descriere();
		cout << " " << el->getPret() << endl;
	}
	for (const auto&el : v)
		delete el;
}*/

int main() {
	/*//fct2();
	//tipareste();
	//fct();
	//f();
	//teste();
	//mancare();
	//anscolar();
	//verif();
	//g();
	//canale();
	//ong_uri();
	//cos_functie();
	//functieBD();
	//stackint();
	//rate();
	//fructe();
	//buchet_flori();
	//numere();
	//toExc();
	//testFormatOutput();
	//testManipulators();
	//testVector();
	//calcul();
	//cumparaturi();
	//tiparire();
	//functie();*/
	testVector();
	_CrtDumpMemoryLeaks();
	return 0;
}

