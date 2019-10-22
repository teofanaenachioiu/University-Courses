#pragma once
#include <string>
#include "BigNumber.h"
using namespace std;

static class Utils
{
public:
	static void createNewFile(string file_name, int size, int min, int max);
	static vector<BigNumber> readBigNumbersFromFile(string file_name);
	static void writeBigNumberInFile(BigNumber bigNumber, string file_name);
	static bool compareFiles(string file_name1, string file_name2);
};