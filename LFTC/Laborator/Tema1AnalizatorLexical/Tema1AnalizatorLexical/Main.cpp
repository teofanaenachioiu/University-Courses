// Main.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include "AnalizatorLexical.h"


using namespace std;


int main()
{
	//regex integerr("(\\+|-)?[[:digit:]]+");

	AnalizatorLexical analizator = AnalizatorLexical();
	analizator.analiza("input1.txt","code.txt","fip.txt", "ts_const.txt", "ts_id.txt");

}