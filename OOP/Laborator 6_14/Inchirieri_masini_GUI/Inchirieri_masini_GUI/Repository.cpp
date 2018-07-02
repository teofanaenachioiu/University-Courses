#include "Repository.h"


//adauga o masina in repo
void Repository::store(const Masina & m)
{
	if (exist(m)) { throw MasinaRepoException("Numar de inmatriculare existent!!!"); }
	this->repo.push_back(m);
}

//sterge o masina din repo
void Repository::deletee(const Masina & m)
{
	if (!exist(m)) { throw MasinaRepoException("Numar de inmatriculare inexistent!!!"); }

	auto it = find_if(repo.begin(), repo.end(), [&m](const Masina& el) {return el == m; });
	repo.erase(it);
}

//actualizeaza o masina in repo dupa numarul de inmatriculare
void Repository::update(const Masina& m)
{
	if (!exist(m)) { throw MasinaRepoException("Numar de inmatriculare inexistent!!!"); }

	auto it = find_if(repo.begin(), repo.end(), [&m](const Masina& el) {return el == m; });
	*it = m;
}

//cauta o masina in repo dupa numarul de inmatriculare
const Masina Repository::find(const Masina& m) const
{
	auto it = find_if(repo.begin(), repo.end(), [&m](const Masina& el) {return el.getNrInmatr() == m.getNrInmatr(); });
	if (it == repo.end())
		throw MasinaRepoException("Nu exista masina cu numarul " + m.getNrInmatr());
	return *it;
}

//verifica daca o masina exista in repository
bool Repository::exist(const Masina& m) {
	try {
		find(m);
		return true;
	}
	catch (MasinaRepoException&) { return false; }
}

//get all repo
const vector<Masina>& Repository::getAll() const noexcept {
	return this->repo;
}

//suprascriere operator <<
ostream& operator<<(ostream& out, const MasinaRepoException& ex) {
	out << ex.msg;
	return out;
}

