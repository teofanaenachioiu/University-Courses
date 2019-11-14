// Main.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include "AnalizatorLexical.h"


using namespace std;


int main()
{
	AnalizatorLexical analizator = AnalizatorLexical();
	analizator.analizaLinie("input1.txt","code.txt","fip.txt", "ts_const.txt", "ts_id.txt");

}