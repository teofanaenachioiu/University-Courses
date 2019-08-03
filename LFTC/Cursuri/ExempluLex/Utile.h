#include <map>

#include <iterator>
#include <vector>
#include <iostream>
#include <fstream>
using namespace std;

std::vector<std::string> TS;  




typedef struct {
 int n;
 int elem[20][2];
} FIP;




void addFIP(int cod, int posTS, FIP& f){
 f.elem[f.n][0]=cod;
 f.elem[f.n++][1]=posTS;
}

FIP fip;
int pozTS=0;

void printFIP(FIP& f){
	ofstream fipFile;
    fipFile.open("FIP.txt");
	cout<<"nr elem fip"<<f.n<<endl;
	for(int i=0;i<f.n;i++)
		fipFile<<f.elem[0]<<" "<<f.elem[1]<<endl;
	fipFile.close();
}