using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab_Texturi
{
    public partial class Form1 : Form
    {
        private int u1, v1, u2, v2;   	    //  ViewPort
        private double a, b, c, d;     	    //  Window
        private int Tip;                    //  Pr. Par.=1, Perp.=2
        private double Raza, Alfa;
        private Bitmap Im1;
        private int dimTextura;
        Punct[] PuncteTextura;
        private Image loadedImage;

        Varf[] V;           // Varfuri
        int nrVarfuri;              // Numar varfuri 
        Muchie[] M;         // Muchii
        int nrMuchii;              // Numar muchii
        int[] Vf;           // Fete
        int nrVarfuriColorat;             // o Fata

        int NrFetevizibile;
        string[] Fete = new string[10];

        int u(double x) { return (int)((x - a) / (b - a) * (u2 - u1) + u1); }

        int v(double y) { return (int)((y - d) / (c - d) * (v2 - v1) + v1); }

        void ViewPort(int x1, int y1, int x2, int y2) { u1 = x1; v1 = y1; u2 = x2; v2 = y2; }
        void Window(double x1, double y1, double x2, double y2) { a = x1; d = y1; b = x2; c = y2; }

        void DefPr(double r, double a) { Raza = r; Alfa = a; }   //   r=1; a=0.8;   // = Pi/4  

        double PrX(double x, double z) { return x + Raza * z * Math.Cos(Alfa); }
        double PrY(double y, double z) { return y + Raza * z * Math.Sin(Alfa); }



        double Px(Varf P) { return PrX(P.x, P.z); }
        double Py(Varf P) { return PrY(P.y, P.z); }

        Pen myPen;
        Graphics formGraphics;

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            myPen = new Pen(Color.Blue);
            formGraphics = CreateGraphics();

            ViewPort(25, 100, 25 + 350, 100 + 250);

            double Pi = 3.1416;

            DefPr(0, Pi / 4);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            openFileDialog1.ShowDialog();
            loadedImage = Image.FromFile(openFileDialog1.FileName);
            Im1 = new Bitmap(loadedImage);

            PuncteTextura = new Punct[5];

            dimTextura = Math.Min(Im1.Height, Im1.Width) - 1;            // Dim. Text.

            PuncteTextura[1] = new Punct()
            {
                x = 0,
                y = 0
            };

            PuncteTextura[4] = new Punct()
            {
                x = 0,
                y = dimTextura
            };
            PuncteTextura[2] = new Punct()
            {
                x = dimTextura,
                y = 0
            };
            PuncteTextura[3] = new Punct()
            {
                x = dimTextura,
                y = dimTextura
            };
        }

        private void button2_Click(object sender, EventArgs e)
        {
            StreamReader streamReader = new StreamReader("Cub.txt");

            string line = streamReader.ReadLine();
            string[] Split = line.Split(' ');
            nrVarfuri = Convert.ToInt32(Split[0]);

            V = new Varf[nrVarfuri + 1];
            for (int i = 1; i <= nrVarfuri; i++)
            {
                line = streamReader.ReadLine();
                Split = line.Split(' ');
                V[i] = new Varf()
                {
                    x = Convert.ToInt32(Split[0]),
                    z = Convert.ToInt32(Split[1]),
                    y = Convert.ToInt32(Split[2])
                };
            }

            line = streamReader.ReadLine();
            Split = line.Split(' ');
            nrMuchii = Convert.ToInt32(Split[0]);

            M = new Muchie[nrMuchii + 1];
            for (int j = 1; j <= nrMuchii; j++)
            {
                line = streamReader.ReadLine();
                Split = line.Split(' ');
                M[j] = new Muchie
                {
                    stanga = Convert.ToInt32(Split[0]),
                    dreapta = Convert.ToInt32(Split[1]),
                    caracteristica = Convert.ToInt32(Split[2])
                };
            }

            line = streamReader.ReadLine();
            Split = line.Split(' ');

            Tip = Convert.ToInt32(Split[0]);
            Raza = Convert.ToDouble(Split[1]);
            Alfa = Convert.ToDouble(Split[2]);

            line = streamReader.ReadLine();                               // Nr.Fete
            Split = line.Split(' ');
            NrFetevizibile = Convert.ToInt32(Split[0]);

            for (int f = 1; f <= NrFetevizibile; f++)
                Fete[f] = streamReader.ReadLine();

            streamReader.Close();

            DefPr(Raza, Alfa); // Persp.(d,q);

            a = b = Px(V[1]); c = d = Py(V[1]);     //  Window ...

            for (int i = 2; i <= nrVarfuri; i++)
            {
                double px = Px(V[i]);
                if (px < a)
                {
                    a = px;
                }
                else if (px > b)
                {
                    b = px;
                }

                double py = Py(V[i]);
                if (py < c)
                {
                    c = py;
                }
                else if (py > d)
                {
                    d = py;
                }
            }

            Window(a, d, b, c);

            for (int j = 1; j <= nrMuchii; j++)        //  Corp ... [Muchii]
            {
                myPen.Color = Color.Black;
                formGraphics.DrawLine(myPen, u(Px(V[M[j].stanga])), v(Py(V[M[j].stanga])), u(Px(V[M[j].dreapta])), v(Py(V[M[j].dreapta])));
            }

            Fata(1, 0, 0, 0, 1, 0, 1);
            Fata(2, 0, 0, 0, 1, 0, 1);
            Fata(3, 0, 0, 0, 1, 0, 1);
        }


        Punct Afin(Punct A, Punct B, double a)
        {
            return new Punct()
            {
                x = a * A.x + (1 - a) * B.x,
                y = a * A.y + (1 - a) * B.y
            };
        }

        Punct Afin4(Punct A, Punct B, Punct C, Punct D, double a, double b, double c)
        {
            return Afin(Afin(Afin(A, B, a), C, b), D, c);
        }

        void Fata(int f, double a1, double a2, double b1, double b2, double c1, double c2)
        {
            string[] Split = Fete[f].Split(' ');

            nrVarfuriColorat = Convert.ToInt32(Split[0]);
            Vf = new int[nrVarfuriColorat + 1];

            for (int k = 1; k <= nrVarfuriColorat; k++)
            {
                Vf[k] = Convert.ToInt32(Split[k]);
            }
            Vf[0] = Vf[nrVarfuriColorat];

            Punct[] Puncte = new Punct[nrVarfuriColorat + 1];

            for (int i = 1; i <= nrVarfuriColorat; i++)
            {
                Puncte[i] = new Punct()
                {
                    x = u(PrX(V[Vf[i]].x, V[Vf[i]].z)),
                    y = v(PrY(V[Vf[i]].y, V[Vf[i]].z))
                };
            }
            

            Bitmap bm = new Bitmap(1, 1);

            int h = u(PrY(V[Vf[1]].y, V[Vf[1]].z)) - u(PrY(V[Vf[4]].y, V[Vf[4]].z));

            int dT = u(PrY(V[Vf[2]].y, V[Vf[2]].z));

            double Pas = Math.Abs(1.0 / h);

            Punct T, P;
            for (double a = a1; a <= a2; a += Pas)
                for (double b = b1; b <= b2; b += Pas)
                    for (double c = c1; c <= c2; c += Pas)
                    {
                        T = Afin4(PuncteTextura[1], PuncteTextura[2], PuncteTextura[3], PuncteTextura[4], a, b, c);
                        bm.SetPixel(0, 0, Im1.GetPixel((int)T.x, Im1.Height - (int)T.y - 1));

                        P = Afin4(Puncte[1], Puncte[2], Puncte[3], Puncte[4], a, b, c);
                        formGraphics.DrawImageUnscaled(bm, (int)P.x, (int)P.y);

                        T = Afin4(PuncteTextura[4], PuncteTextura[1], PuncteTextura[2], PuncteTextura[3], a, b, c);
                        bm.SetPixel(0, 0, Im1.GetPixel((int)T.x, Im1.Height - (int)T.y - 1));

                        P = Afin4(Puncte[4], Puncte[1], Puncte[2], Puncte[3], a, b, c);
                        formGraphics.DrawImageUnscaled(bm, (int)P.x, (int)P.y);

                        T = Afin4(PuncteTextura[3], PuncteTextura[4], PuncteTextura[1], PuncteTextura[2], a, b, c);
                        bm.SetPixel(0, 0, Im1.GetPixel((int)T.x, Im1.Height - (int)T.y - 1));

                        P = Afin4(Puncte[3], Puncte[4], Puncte[1], Puncte[2], a, b, c);
                        formGraphics.DrawImageUnscaled(bm, (int)P.x, (int)P.y);

                        T = Afin4(PuncteTextura[2], PuncteTextura[3], PuncteTextura[4], PuncteTextura[1], a, b, c);
                        bm.SetPixel(0, 0, Im1.GetPixel((int)T.x, Im1.Height - (int)T.y - 1));

                        P = Afin4(Puncte[2], Puncte[3], Puncte[4], Puncte[1], a, b, c);
                        formGraphics.DrawImageUnscaled(bm, (int)P.x, (int)P.y);
                    }
        }

    }
}

