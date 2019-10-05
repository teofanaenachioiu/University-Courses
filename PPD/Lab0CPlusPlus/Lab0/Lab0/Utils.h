#pragma once
#include <string>
class Utils
{
public:
	Utils();
	~Utils();

	void static createNewFile(std::string file_name, int size, int min, int max);

	bool static checkIsSameContentInFile(std::string file_name1, std::string file_name2);
};
