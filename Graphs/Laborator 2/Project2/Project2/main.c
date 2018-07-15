#include <stdio.h>
FILE *f;
void citire(int adiacenta[30][30], int* n)
{
	fscanf(f, "%d", n);
	for (int i = 0; i < *n; i++)
		for (int j = 0; j < *n; j++)
			fscanf(f, "%d", &adiacenta[i][j]);
}

void afisareMatrice(int matrice[30][30], int nr)
{
	for (int i = 0; i < nr; i++)
	{
		for (int j = 0; j < nr; j++)
			printf("%d ", matrice[i][j]);
		printf("\n");
	}
}

void initializare(int tranz[30][30], int adiacenta[30][30], int n)
{
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
			tranz[i][j] = adiacenta[i][j];
		tranz[i][i] = 1;
	}
}

void inchidere(int tranz[30][30], int adiacenta[30][30], int n)
{
	int i, j, k;
	initializare(tranz, adiacenta, n);
	printf("\n");
	for (k = 0; k < n; k++)
		for (i = 0; i < n; i++)
			for (j = 0; j < n; j++)
				if (tranz[i][j]==0)
					tranz[i][j] = tranz[i][k] * tranz[k][j];
}

int main()
{
	int n, adiacenta[30][30],tranz[30][30];
	f = fopen("graf.txt","r");
	citire(adiacenta, &n);
	printf("Matricea de adiacenta:\n");
	afisareMatrice(adiacenta, n);

	inchidere(tranz, adiacenta, n);
	printf("Inchiderea tranzitiva a grafului:\n");
	afisareMatrice(tranz, n);
	fclose(f);
	return 0;
}