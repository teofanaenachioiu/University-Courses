#include <stdio.h>
#define inf 2<<16

FILE *f;

typedef struct
{
	int varf, nrVecini;
	int vecini[30];
}Lista;

typedef struct
{
	int extr1, extr2;
}Muchie;

typedef struct
{
	int l;
	int p;
}Drum;

int getExtremitatea1(Muchie m)
{
	return m.extr1;
}

int getExtremitatea2(Muchie m)
{
	return m.extr2;
}

int getNrVecini(Lista l)
{
	return l.nrVecini;
}

int* getVecini(Lista l)
{
	return l.vecini;
}

int getVarf(Lista l)
{
	return l.varf;
}

int getLungimeDrum(Drum d)
{
	return d.l;
}

int getParinte(Drum d)
{
	return d.p;
}

void setLungimeDrum(Drum* d, int val)
{
	(*d).l = val;
}

void setParinte(Drum *d, int parinte)
{
	(*d).p = parinte;
}

void citire(int* n, Muchie edge[30], int *m)
{
	fscanf(f, "%d", n);
	while(!feof(f))
	{
		fscanf(f, "%d", &edge[*m].extr1);
		fscanf(f, "%d", &edge[*m].extr2);
		*m = *m + 1;
	}
}


void initializare(Lista noduri[30], int n)
{
	for (int i = 0; i < n; i++)
	{
		noduri[i].nrVecini = 0;
		noduri[i].varf = i + 1;
	}
}

void listaVecini(Lista noduri[30], int m, Muchie edge[30])
{
	int vf1, vf2;
	for (int i = 0; i < m; i++)
	{
		vf1 = getExtremitatea1(edge[i]);
		vf2 = getExtremitatea2(edge[i]);

		noduri[vf1 - 1].vecini[noduri[vf1 - 1].nrVecini] = vf2;
		noduri[vf1 - 1].nrVecini = noduri[vf1 - 1].nrVecini + 1;

		noduri[vf2 - 1].vecini[noduri[vf2 - 1].nrVecini] = vf1;
		noduri[vf2 - 1].nrVecini = noduri[vf2 - 1].nrVecini + 1;
	}
}

void stergeDinCoada(int lista[30],int *n)
{
	*n = *n - 1;
	for (int i = 0; i < *n; i++)
		lista[i] = lista[i + 1];
}

void adaugaInCodada(int lista[30], int *n, int valoare)
{
	lista[*n] = valoare;
	*n = *n + 1;
}

int primulDinCoada(int lista[30])
{
	return lista[0];
}

void moore(Drum d[30], Lista nod[30], int n, int sursa)
{
	int i, queue[30], nr = 0, x, y;
	Lista vf;
	for ( i = 0; i < n; i++)
		setLungimeDrum(&d[i], inf);

	adaugaInCodada(queue, &nr, sursa);
	setLungimeDrum(&d[sursa-1],0);

	while (nr!=0)
	{
		x = primulDinCoada(queue);
		stergeDinCoada(queue,&nr);
		vf = nod[x - 1];

		for (i = 0; i < getNrVecini(vf); i++)
		{
			y = getVecini(vf)[i];
			if (getLungimeDrum(d[y-1]) == inf)
			{
				setParinte(&d[y - 1],x);
				setLungimeDrum(&d[y - 1], getLungimeDrum(d[x - 1]) + 1);
				adaugaInCodada(queue, &nr, y);
			}
		}
	}
}
void meniu()
{
	printf("1. Determinati cel mai scurt drum.\n");
	printf("0. Iesire\n");
}

void afisare(Drum d[30],Lista noduri[30],int n)
{
	int sursa,i;
	printf("Varf sursa: ");
	scanf("%d", &sursa);
	if (sursa > 0 && sursa <= n)
	{
		moore(d, noduri, n, sursa);
		for (i = 0; i < n; i++)
		{
			if (getLungimeDrum(d[i]) == inf)
				printf("%d -> %d:  infinit\n", sursa, i + 1);
			else {
				printf("%d -> %d:  %d\n", sursa, i + 1, getLungimeDrum(d[i]));
			}
		}
	}
	else {
		printf("Nu exista nodul specificat.\n");
	}
}

int main()
{
	int n = 0, m=0, i,cmd;
	Lista noduri[30];
	Muchie edge[30];
	Drum d[30];
	f=fopen("graf.txt", "r");
	citire(&n, edge, &m);

	initializare(noduri,n);
	listaVecini(noduri, m, edge);
	while (1)
	{
		meniu();
		printf("Comanda: ");
		scanf("%d", &cmd);
		if (cmd == 1)
		{
			afisare(d,noduri,n);
		}
		if (cmd == 0)
		{
			printf("Multumim ca ati utilizat aplicatia!\n");
			break;
		}
		if (cmd!=0 && cmd!=1)
		{
			printf("Comanda gresita!\n");
		}
	}
	
	fclose(f);
}