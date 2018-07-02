#pragma once
#include <memory>

// Implementare vector dinamic

template <typename T> class Iterator;
template <typename T> struct Nod;

template <typename T>

class  Vector {
public:
	//constructor
	Vector() noexcept;
	//dimensiune vector
	int size() const noexcept;
	//adauga element la sfarsit
	void add(const T &value);
	//sterge element de pe pozitie
	void erase(int poz) noexcept;
	//returneaza pozitia unui element in vector sau -1 in caz contrar
	int find(const T & element) noexcept;
	//suprascrie operatorul []
	T &operator[](int poz);
	const T & operator[](int poz) const;
	//copiaza vector
	Vector(const Vector & v);
	//muta constuctor
	Vector(Vector && v)noexcept;
	//aperatorul de atibuire;
	Vector & operator=(Vector && v) noexcept;
	//deepcopy
	Vector & operator=(const Vector & v);
	//destructor
	~Vector();
	friend class Iterator<T>;

	Iterator<T> begin() const noexcept;
	Iterator<T> last() const noexcept;
	Iterator<T> end() const noexcept;
private:
	void delete_list() noexcept;
	struct Nod {
		T val;
		Nod *next;
		Nod(const T & value) noexcept: val{ value }, next{ nullptr } {}
	};
	Nod * _first;
	Nod * _last;
	int _size;
};

template <typename T>
Vector<T>::Vector() noexcept {
	_size = 0;
	_first = nullptr;
	_last = nullptr;
}

template <typename T>
Vector<T>::Vector(const Vector<T> & v) {
	_size = 0;
	_first = nullptr;
	_last = nullptr;
	for (Nod * it = v._first; it != nullptr; it = it->next) {
		this->add(it->val);
	}
}

template <typename T>
Vector<T>::Vector(Vector<T> && v) noexcept {
	_size = 0;
	_first = nullptr;
	_last = nullptr;
	if (this != nullptr) {
		this->_first = v._first;
		this->_size = v._size;
		this->_last = v._last;
		v._first = nullptr;
		v._size = 0;
		v._last = nullptr;
	}
}
template <typename T>
Vector<T> & Vector<T>::operator=(Vector<T> && v) noexcept
{
	if (this == &v) return *this;
	delete_list();
	this->_first = v._first;
	this->_size = v._size;
	this->_last = v._last;
	v._first = nullptr;
	v._size = 0;
	v._last = nullptr;
	return *this;
}

template <typename T>
Vector<T> & Vector<T>::operator=(const Vector<T> & v) {
	if (this == &v) return *this;
	delete_list();
	this->_size = 0;
	this->_last = nullptr;
	for (Nod *it = v._first; it != nullptr; it = it->next) {
		this->add(it->val);
	}
	return *this;
}

template <typename T>
Vector<T>::~Vector() {
	//delete_list();
	while (this->_first != nullptr) {
		Nod * to_delete = this->_first;
		this->_first = this->_first->next;
		delete to_delete;
	}
}

template <typename T>
int Vector<T>::size() const noexcept {
	return this->_size;
}

template <typename T>
void Vector<T>::add(const T & value) {
	Nod *tmp = new Nod(value);
	if (this->_first == nullptr) {
		this->_first = this->_last = tmp;
	}
	else {
		this->_last->next = tmp;
		this->_last = this->_last->next;
	}
	++this->_size;
}

template <typename T>
void Vector<T>::erase(int poz) noexcept {
	if (poz < 0 || poz >= this->_size) return;
	//if it's the first element - delete the first node
	if (poz == 0) {
		Nod * to_delete = this->_first;
		this->_first = this->_first->next;
		delete to_delete;
	}
	//else move to the node before the one to be deleted
	//set the links and delete the former
	else {
		Nod * prev = this->_first;
		for (int i = 0; i < poz - 1; ++i, prev = prev->next);
		Nod * to_delete = prev->next;
		prev->next = to_delete->next;
		if (poz == this->_size - 1) {
			this->_last = prev;
		}
		delete to_delete;
	}
	--this->_size;
}

template <typename T>
int Vector<T>::find(const T & element) noexcept {
	int pos = 0;
	for (auto iter = this->begin(); iter != this->end(); ++iter) {
		if (*iter == element) return pos;
		++pos;
	}
	return -1;
}

template <typename T>
T & Vector<T>::operator []  (int poz)
{
	Iterator<T> iter = this->begin();
	for (int i = 0; i < poz && iter != this->end(); ++i)
		++iter;
	return *iter;
}

template <typename T>
const T & Vector<T>::operator[](int poz) const
{
	Iterator <T> iter = this->begin();
	for (int i = 0; i < poz && iter != this->end(); ++i)
		++iter;
	return *iter;
}

template<typename T>
Iterator<T> Vector<T>::begin() const noexcept {
	return Iterator<T>(this->_first);
}

template <typename T>
Iterator<T> Vector<T>::last() const noexcept {
	return Iterator<T>(this->_last);
}

template <typename T>
Iterator<T> Vector<T>::end() const noexcept {
	return Iterator<T>(nullptr);
}

template <typename T>
void Vector<T>::delete_list() noexcept
{
	while (this->_first != nullptr) {
		Nod * to_delete = this->_first;
		this->_first = this->_first->next;
		delete to_delete;
	}
}

/////// Iteratorul
template <typename T>
class Iterator {
public:
	friend class Vector<T>;
	//dereference operator overload
	T & operator*() noexcept;

	//prefix increment operator
	Iterator & operator++() noexcept;

	//postfix increment operator
	Iterator operator++(int) noexcept;

	//equality operator for other iterator comparison
	bool operator==(const Iterator & rhs) const noexcept;

	//equality operator for ref comparison
	bool operator==(const typename Vector<T>::Nod * ref) const noexcept;

	//inequality operator for other iterator comparison
	bool operator!=(const Iterator & rhs) const noexcept;

	//inequality operator for other ref comparison
	bool operator!=(const typename Vector<T>::Nod * ref) const noexcept;

	//default constructor
	Iterator() noexcept : current_{ nullptr } {};

	//constructor overload for pointer parameter
	Iterator(typename Vector<T>::Nod * other) noexcept;

	//copy constructor
	Iterator(const Iterator & rhs) noexcept;

	//copy assingment operator
	Iterator & operator=(const Iterator & rhs) = default;

	//default move constructor
	Iterator(Iterator && rhs) = default;

	//default move operator
	Iterator & operator=(Iterator && rhs) = default;

	//default destructor
	~Iterator();
private:
	typename Vector<T>::Nod * current_;
};


template<typename T>
Iterator<T>::Iterator(typename Vector<T>::Nod * other) noexcept : current_{ other } {

}

template<typename T>
Iterator<T>::Iterator(const Iterator<T> & rhs) noexcept : current_{ rhs.current_ } {

}

template<typename T>
Iterator<T>::~Iterator() {

}

template<typename T>
T & Iterator<T>::operator*() noexcept {
	return this->current_->val;
}

template<typename T>
Iterator<T> & Iterator<T>::operator++() noexcept {
	this->current_ = this->current_->next;
	return *this;
}

template<typename T>
Iterator<T> Iterator<T>::operator++(int) noexcept {
	Iterator<T> temp(*this);
	operator++();
	return temp;
}

template<typename T>
bool Iterator<T>::operator==(const Iterator<T> & rhs) const noexcept {
	return this->current_ == rhs.current_;
}

template<typename T>
bool Iterator<T>::operator==(const typename Vector<T>::Nod * ref) const noexcept {
	return this->current_ == ref;
}

template<typename T>
bool Iterator<T>::operator!=(const Iterator<T> & rhs) const noexcept {
	return this->current_ != rhs.current_;
}

template<typename T>
bool Iterator<T>::operator!=(const typename Vector<T>::Nod * ref) const noexcept {
	return this->current_ != ref;
}

void testVector();