// Labirint.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <queue>
#include <utility>
#include <iostream>
#include <fstream>
#include <string>
using namespace std;
ifstream file;

pair <int,int> creareMatrice(int matrice[200][200], pair <int,int> &start, pair <int,int> &finish)
{
	char c;
	int rows, cols,i,j;
	i = 1; j = 1; //n=linie; m=coloana
	rows = cols = 1;
	while (!file.eof())
	{
		file.get(c);
		switch (c)
		{case '1':
		{	
			matrice[i][j] = -1;
			j = j + 1;
			break;
		}
		case ' ':
		{
			matrice[i][j] = 0;
			j = j + 1;
			break;
		}
		case 'S':
		{
			matrice[i][j] = 1;
			start = make_pair(i, j);
			j = j + 1;
			break;
		}
		case 'F':
		{
			matrice[i][j] = 0;
			finish = make_pair(i, j);
			j = j + 1;
			break;
		}
		case '\n':
		{
			i = i+1;
			rows = i;
			cols = j;
			j = 1;
			break;
		}
		default:
			break;
		}
	}
	rows+=2;
	cols+=1;
	return make_pair(rows, cols);
}

void bordare(int matrice[200][200], int n, int m)
{
	for (int i = 0; i < m; i++)
	{
		matrice[0][i] = -1;
		matrice[n - 1][i] = -1;
	}
	for (int i = 0; i < n; i++)
	{
		matrice[i][0] = -1;
		matrice[i][m-1] = -1;
	}
}
void afisareMartice(int matrice[200][200], int n, int m)
{
	int i, j;
	for (i = 0; i <n; i++)
	{
		for (j = 0; j <m; j++)
			cout << matrice[i][j] << " ";
		cout << endl;
	}
}

int lee(int matrice[200][200], int n, int m, pair <int,int> start, pair <int, int> finish)
{
	int dx[4] = { 0,-1,0,1 }, dy[4] = {-1,0,1,0};
	queue <pair <int, int>> Q;
	pair <int, int> nod,vecin;
	Q.push(start);
	while (!Q.empty() && matrice[finish.first][finish.second] == 0)
	{
		nod = Q.front();
		Q.pop();
		for (int k = 0; k < 4; k++)
		{
			vecin = make_pair(nod.first + dx[k], nod.second + dy[k]);
			if (matrice[vecin.first][vecin.second] == 0)
			{
				matrice[vecin.first][vecin.second] = matrice[nod.first][nod.second] + 1;
				Q.push(vecin);
			}
		}
	}
	if (matrice[finish.first][finish.second] == 0)
		return 0;
	else return 1;
}

void determinareTraseu(int matrice[200][200], int n, int m,  pair <int, int> finish)
{
	int dx[4] = { 0,-1,0,1 }, dy[4] = { -1,0,1,0 };
	int time,k;
	pair <int, int> nod, vecin;

	time = matrice[finish.first][finish.second]-1;
	matrice[finish.first][finish.second] = -2;
	nod = make_pair(finish.first, finish.second);
	while (time>1)
	{
		k = 0;
		while( k < 4) 
		{
			vecin = make_pair(nod.first + dx[k], nod.second + dy[k]);
			if (matrice[vecin.first][vecin.second] == time)
			{
				matrice[vecin.first][vecin.second] = -2;
				nod = vecin;
				break;
			}
			k++;
		}
		time = time - 1;
	}
}

void reconstruireLabirint(char lab[200][200],int matrice[200][200], int n, int m)
{
	for (int i=0;i<n;i++)
		for (int j = 0; j < m; j++)
		{
			switch (matrice[i][j])
			{
			case -1:
			{
				lab[i][j] = '1';
				break;
			}
			case -2:
			{
				lab[i][j] = '*';
				break;
			}
			case 1:
			{
				lab[i][j] = '*';
				break;
			}
			default:
			{
				lab[i][j] = ' ';
				break;
			}
			}
		}
}

void afisareLabirint(char lab[200][200], int n, int m)
{
	int i, j;
	for (i = 1; i <n - 1; i++)
	{
		for (j = 1; j <m - 1; j++)
			cout << lab[i][j];
		cout << endl;
	}
}

int main() 
{
	pair <int, int> start, finish, dim;
	int n = -1, m = -1, matrice[200][200];
	char lab[200][200];

	file.open("labirint.txt");

	dim = creareMatrice(matrice,start,finish);
	n = dim.first;
	m = dim.second;
	bordare(matrice, n, m);
	
	if (lee(matrice, n, m, start, finish) == 1)
	{
		cout << "Labirint rezolvabil." << endl;
		cout << endl;
		//afisareMartice(matrice, n, m);
		determinareTraseu(matrice, n, m, finish);
		//cout << endl;
		//afisareMartice(matrice, n, m);
		reconstruireLabirint(lab, matrice, n, m);
		afisareLabirint(lab, n, m);
	}
	else cout << "Fara solutii." << endl;

	file.close();
	return 0;
}