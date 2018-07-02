#pragma once
#include "Masina.h"
#include "Repository.h"
class ActiuneUndo {
public:
	virtual void doUndo() = 0;
	//virtual ~ActiuneUndo() = default;
};

class UndoAdauga : public ActiuneUndo {
	Masina masinaAdaugata;
	Repository& repo;
public:
	UndoAdauga(Repository& r, const  Masina& m) :repo{ r }, masinaAdaugata{ m } {}
	void doUndo() override {
		repo.deletee(masinaAdaugata);
	}
};

class UndoSterge : public ActiuneUndo {
	Masina masinaStearsa;
	Repository& repo;
public:
	UndoSterge(Repository& rep, const  Masina& m) :repo{ rep }, masinaStearsa{ m } {}
	void doUndo() override {
		repo.store(masinaStearsa);
	}
};

class UndoActualizare : public ActiuneUndo {
	Masina masinaActualizata;
	Repository& repo;
public:
	UndoActualizare(Repository& rep, const  Masina& m_a) :repo{ rep }, masinaActualizata{ m_a } {}
	void doUndo() override {
		repo.update(masinaActualizata);
	}
};