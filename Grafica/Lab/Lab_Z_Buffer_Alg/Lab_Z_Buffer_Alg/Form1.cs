using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab_Z_Buffer_Alg
{
    public partial class Form1 : Form
    {
        private static int DEVIAJ = 50;
        private static int WIDTH = 350;
        private static int HEIGHT = 250;
        private int u1, v1, u2, v2;   	    //  ViewPort
        private double a, b, c, d;     	    //  Window
        private int Tip;                    //  Pr. Par.=1, Perp.=2
        private double Raza, Alfa;

        Varf[] V;           // Varfuri
        int nrVarfuri;              // Numar varfuri 
        Muchie[] M;         // Muchii
        int nrMuchii;              // Numar muchii
        int[] Vf;           // Fete
        int nrVarfuriColorat;             // o Fata

        int NrFetevizibile;
        int[][] Fete;
        int[] FeteZ;
        int[] FeteColor;

        Color[] colors = new Color[] { Color.Blue, Color.Red, Color.Green, Color.Yellow, Color.Purple, Color.Orange };

        Color[][] P = new Color[WIDTH][];
        int[][] D = new int[WIDTH][];

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

        private void colorIf(int[] fata, int nrFata)
        {
            int[] x = new int[fata.Length];
            int[] y = new int[fata.Length];

            Point[] points = new Point[fata.Length];

            for(int i=0; i<fata.Length; i++)
            {
                x[i] = u(PrX(V[fata[i]].x, V[fata[i]].z));
                y[i] = v(PrY(V[fata[i]].y, V[fata[i]].z));
                points[i] = new Point(x[i], y[i]);
            }
            GraphicsPath g = new GraphicsPath();
            g.AddPolygon(points);

            for (int i = x.Min(); i < x.Max(); i++) {
                for (int j = y.Min(); j < y.Max(); j++)
                {
                    if (g.IsVisible(new Point(i,j)) && FeteZ[nrFata] < D[i][j])
                    {
                        P[i][j] = colors[nrFata];
                        D[i][j] = FeteZ[nrFata];
                    }
                }
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Bitmap bm = new Bitmap(1, 1);
            bm.SetPixel(0, 0, Color.Gray);


            // Pentru fiecare pixel Pij din fereastra ecran execută
            // Colorează(Pij, Culoarea_de_fond);
            // Di,j:= maxima;
            for (int i = 0; i < WIDTH; i++)
            {
                Color[] tmp = new Color[HEIGHT];
                int[] tmpint = new int[HEIGHT];
                for (int j = 0; j < HEIGHT; j++)
                {
                    tmp[j] = Color.Gray;
                    tmpint[j] = 100;
                }
                P[i] = tmp;
                D[i] = tmpint;
            }


            for (int i = 0; i < NrFetevizibile; i++)
            {
                colorIf(Fete[i], i);
            }


            for (int i = 0; i < WIDTH; i++)
            {
                for (int j = 0; j < HEIGHT; j++)
                {
                    bm.SetPixel(0, 0, P[i][j]);
                    formGraphics.DrawImage(bm, i, j);
                }
            }
        }

        Graphics formGraphics;


        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            myPen = new Pen(Color.Blue);
            formGraphics = CreateGraphics();

            ViewPort(DEVIAJ, DEVIAJ, DEVIAJ + WIDTH, DEVIAJ + HEIGHT);

            double Pi = 3.1416;

            DefPr(0, Pi / 4);

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

            Fete = new int[NrFetevizibile][];
            FeteColor = new int[NrFetevizibile];
            FeteZ = new int[NrFetevizibile];

            for (int f = 0; f < NrFetevizibile; f++)
            {
                string fata = streamReader.ReadLine();
                Split = fata.Split(' ');

                nrVarfuriColorat = Convert.ToInt32(Split[0]);

                Vf = new int[nrVarfuriColorat];

                for (int k = 0; k < nrVarfuriColorat; k++)
                {
                    Vf[k] = Convert.ToInt32(Split[k + 1]);
                }
                FeteZ[f] = Convert.ToInt32(Split[nrVarfuriColorat + 1]);
                FeteColor[f] = Convert.ToInt32(Split[nrVarfuriColorat + 2]);

                Fete[f] = Vf;
            }

            streamReader.Close();

            DefPr(Raza, Alfa); // Persp.(d,q);


            a = b = Px(V[1]);
            c = d = Py(V[1]);

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

            Window(a - 1, d + 1, b + 1, c - 1);
        }


        private void button1_Click(object sender, EventArgs e)
        {
            for (int j = 1; j <= nrMuchii; j++)        //  Corp ... [Muchii]
            {
                myPen.Color = Color.Black;
                formGraphics.DrawLine(myPen, u(Px(V[M[j].stanga])), v(Py(V[M[j].stanga])), u(Px(V[M[j].dreapta])), v(Py(V[M[j].dreapta])));
            }
        }

        //void Fata(int f)
        //{
        //    //string[] Split = Fete[f].Split(' ');

        //    nrVarfuriColorat = Convert.ToInt32(Split[0]);
        //    Vf = new int[nrVarfuriColorat + 1];

        //    for (int k = 1; k <= nrVarfuriColorat; k++)
        //    {
        //        Vf[k] = Convert.ToInt32(Split[k]);
        //    }
        //    Vf[0] = Vf[nrVarfuriColorat];

        //    Punct[] Puncte = new Punct[nrVarfuriColorat + 1];

        //    for (int i = 1; i <= nrVarfuriColorat; i++)
        //    {
        //        Puncte[i] = new Punct()
        //        {
        //            x = u(PrX(V[Vf[i]].x, V[Vf[i]].z)),
        //            y = v(PrY(V[Vf[i]].y, V[Vf[i]].z))
        //        };
        //        formGraphics.DrawEllipse(myPen, (float)Puncte[i].x - 2.5f, (float)Puncte[i].y - 2.5f, 5, 5);
        //    }
        //}
    }
}
