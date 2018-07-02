#pragma once
#include "Repository.h"

class RepositoryMemory :public Repository {
public:
	void loadData() override;
	void saveData() override;
	RepositoryMemory() {
		loadData();
	}
	~RepositoryMemory() {
		saveData();
	}
};

void testRepoMemory();