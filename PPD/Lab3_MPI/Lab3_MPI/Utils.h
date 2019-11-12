#pragma once
#include <string>
#include <vector>
using namespace std;

static class Utils
{
public:
	static int* createNewFile(string file_name, int size, int min, int max);
	static int** readBigNumbersFromFile(string file_name);
	static void writeBigNumberInFile(int* bigNumber, int size, string file_name);
	static bool compareFiles(string file_name1, string file_name2);
};