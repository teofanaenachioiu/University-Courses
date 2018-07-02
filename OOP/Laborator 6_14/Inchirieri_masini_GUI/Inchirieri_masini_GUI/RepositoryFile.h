#pragma once
#include "Repository.h"

class RepositoryFile : public Repository {
	string fisier_in, fisier_out;
public:
	void loadData() override;
	void saveData() override;
	RepositoryFile(string fisier_in, string fisier_out) :fisier_in{ fisier_in }, fisier_out{ fisier_out } {
		loadData();
	}
	~RepositoryFile() {
		saveData();
	}
};

void testRepoFile();