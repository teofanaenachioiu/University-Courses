using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Corp_3D
{
    public partial class Form1 : Form
    {
        int u1, v1, u2, v2;   	          //  ViewPort
        double a, b, c, d;     	          //  Window
        int Tip; double Raza, Alfa;    	  //  Pr. Par.=1, Perp.=2
        Bitmap Im1;
        private Image loadedImage;

        class muchie { public int st, dr, co; }

        class varf
        {
            public double x, y, z;
            public varf(int X, int Y, int Z) { x = X; y = Y; z = Z; }
        }

        varf[]   V; int n;  // Varfuri
        muchie[] M; int m;   // Muchii
        int[] Vf;   int nV;   // o Fata

        int NrF;  String[] Fete=new String[10];

        int u(double x) { return (int)((x - a) / (b - a) * (u2 - u1) + u1); }
        int v(double y) { return (int)((y - d) / (c - d) * (v2 - v1) + v1); }

        void ViewPort(int x1, int y1, int x2, int y2) { u1 = x1; v1 = y1; u2 = x2; v2 = y2; }
        void Window(double x1, double y1, double x2, double y2) { a = x1; d = y1; b = x2; c = y2; }

        void DefPr(double r, double a) { Raza = r; Alfa = a; }   //   r=1; a=0.8;   // = Pi/4  

        double PrX(double x, double z) { return x + Raza * z * Math.Cos(Alfa); }
        double PrY(double y, double z) { return y + Raza * z * Math.Sin(Alfa); }

        double Px(varf P) { return PrX(P.x, P.z); }
        double Py(varf P) { return PrY(P.y, P.z); }


        System.Drawing.Pen myPen;
        System.Drawing.Graphics formGraphics;
        Color[] Cul = { Color.Black, Color.Red, Color.Green, Color.Blue, Color.Yellow };

        public Form1()
        {
            InitializeComponent();
            myPen = new System.Drawing.Pen(System.Drawing.Color.Chocolate);
            formGraphics = this.CreateGraphics();

            ViewPort(25, 100, 25 + 350, 100 + 250);
            double Pi = 3.1416; DefPr(0, Pi / 4);
        }

        private void button1_Click(object sender, EventArgs e)   // Corp
        {

            openFileDialog1.ShowDialog();
            System.IO.StreamReader Fc = new System.IO.StreamReader(openFileDialog1.FileName);
            String Line = Fc.ReadLine();
            String[] Split = Line.Split(new Char[] { ' ', ',', '\t' });
            n = Convert.ToInt32(Split[0]);
            V = new varf[n + 1];
            for (int i = 1; i <= n; i++)
            {
                Line = Fc.ReadLine();
                Split = Line.Split(new Char[] { ' ', ',', '\t' });
                int X = Convert.ToInt32(Split[0]);
                int Z = Convert.ToInt32(Split[1]);
                int Y = Convert.ToInt32(Split[2]);   // y <--->z
                V[i] = new varf(X, Y, Z);                    //   V V V   !!!

            }

            Line = Fc.ReadLine();
            Split = Line.Split(new Char[] { ' ', ',', '\t' });
            m = Convert.ToInt32(Split[0]);
            M = new muchie[m + 1];
            for (int j = 1; j <= m; j++)
            {
                Line = Fc.ReadLine();
                Split = Line.Split(new Char[] { ' ', ',', '\t' });
                M[j] = new muchie();
                M[j].st = Convert.ToInt32(Split[0]);
                M[j].dr = Convert.ToInt32(Split[1]);
                M[j].co = Convert.ToInt32(Split[2]);
            }

            Line = Fc.ReadLine();
            Split = Line.Split(new Char[] { ' ', ',', '\t' });
            Tip = Convert.ToInt32(Split[0]);
            Raza = Convert.ToDouble(Split[1]);
            Alfa = Convert.ToDouble(Split[2]);

            Line = Fc.ReadLine();                               // Nr.Fete
            Split = Line.Split(new Char[] { ' ', ',', '\t' });
            NrF = Convert.ToInt32(Split[0]);
            
            for (int f=1; f<=NrF; f++)
                Fete[f]=Fc.ReadLine();
            /*
            Line = Fc.ReadLine();
            Split = Line.Split(new Char[] { ' ', ',', '\t' });
            nV = Convert.ToInt32(Split[0]);
            Vf = new int[nV+1];

            for (int k = 1; k <= nV; k++)
            {
                Vf[k] = Convert.ToInt32(Split[k]);
            }
            Vf[0] = Vf[nV];
           */
            Fc.Close();
           
            DefPr(Raza, Alfa); // Persp.(d,q);

            a = b = Px(V[1]); c = d = Py(V[1]);     //  Window ...
            for (int i = 2; i <= n; i++)
            {
                double px = Px(V[i]);
                if (px < a) a = px; else if (px > b) b = px;
                double py = Py(V[i]);
                if (py < c) c = py; else if (py > d) d = py;
            }
            Window(a, d, b, c);    
 
            for (int j = 1; j <= m; j++)        //  Corp ... [Muchii]
            {
                myPen.Color = Cul[M[j].co];
                formGraphics.DrawLine(myPen, u(Px(V[M[j].st])), v(Py(V[M[j].st])), u(Px(V[M[j].dr])), v(Py(V[M[j].dr])));
            }

            myPen.Color = Cul[0];               //  O fata ... [Muchii]
            for (int k = 1; k <= nV; k++)
            {
                formGraphics.DrawLine(myPen, u(Px(V[Vf[k - 1]])), v(Py(V[Vf[k - 1]])), u(Px(V[Vf[k]])), v(Py(V[Vf[k]])));
            }

            button5.Visible = true; //button1.Visible = false;
        }

        private void button2_Click(object sender, EventArgs e)   // Load Textura
        {
            openFileDialog1.ShowDialog();
            loadedImage = Image.FromFile(openFileDialog1.FileName);
            Im1 = new Bitmap(loadedImage);
            pictureBox1.Image = Im1;
            pictureBox1.Refresh();
            button1.Visible = true; //button2.Enabled = false;
        }

        void PutPixel(System.Drawing.Bitmap I, int x, int y, Color c)
        {
            I.SetPixel(0, 0, c);
            formGraphics.DrawImageUnscaled(I, x, y);
        }

        class Pct  { public double x,y; 
                     public Pct(double X, double Y) { x = X; y = Y; }
        }

        int Min(int a, int b)
        {
            return a < b ? a : b;
        }

        Pct Afin(Pct A, Pct B, double a)  // Afin(Pct A, Pct B, Pct C, Pct D, double a, double b, double c)
        {
            // Pct P= new Pct(a * A.x + b * B.x + c * C.x + (1.0 - a - b - c) * D.x, a * A.y + b * B.y + c * C.y + (1.0 - a - b - c) * D.y); return P;
            return new Pct(a * A.x + (1-a) * B.x, a * A.y + (1-a) * B.y);
        }
        Pct Afin4(Pct A, Pct B, Pct C, Pct D, double a, double b, double c)
        {
            return Afin(Afin(Afin(A, B, a), C, b), D, c);
        }

        void Fata(int f, double a1, double a2, double b1, double b2, double c1, double c2)
        {
            String[] Split = Fete[f].Split(new Char[] { ' ', ',', '\t' });

            nV = Convert.ToInt32(Split[0]);
            Vf = new int[nV + 1];

            for (int k = 1; k <= nV; k++)
            {
                Vf[k] = Convert.ToInt32(Split[k]);
            }
            Vf[0] = Vf[nV];

            Pct[] Pt = new Pct[nV + 1];

            for (int i = 1; i <= nV; i++)
            {
                Pt[i] = new Pct(u(PrX(V[Vf[i]].x, V[Vf[i]].z)), v(PrY(V[Vf[i]].y, V[Vf[i]].z)));
            }
            Pct[] Tt = new Pct[nV + 1];

            int dt = Min(Im1.Height, Im1.Width) - 1;            // Dim. Text.
            Tt[1] = new Pct(0, 0); Tt[4] = new Pct(0, dt);
            Tt[2] = new Pct(dt, 0); Tt[3] = new Pct(dt, dt);

            System.Drawing.Bitmap bm = new System.Drawing.Bitmap(1, 1);

            int h = u(PrY(V[Vf[1]].y, V[Vf[1]].z)) - u(PrY(V[Vf[4]].y, V[Vf[4]].z));
            int dT = u(PrY(V[Vf[2]].y, V[Vf[2]].z));
            double Pas = Math.Abs(1.0 / h);
            for (double a = a1; a <= a2; a += Pas)
                for (double b = b1; b <= b2; b += Pas)
                    for (double c = c1; c <= c2; c += Pas)
                    {
                        Pct T = Afin4(Tt[1], Tt[2], Tt[3], Tt[4], a, b, c);
                        int xt = (int)(T.x);
                        int yt = (int)(T.y);
                        bm.SetPixel(0, 0, Im1.GetPixel(xt, Im1.Height - yt - 1));

                        Pct P = Afin4(Pt[1], Pt[2], Pt[3], Pt[4], a, b, c);
                        xt = (int)(P.x);
                        yt = (int)(P.y);

                        formGraphics.DrawImageUnscaled(bm, xt, yt);

                        T = Afin4(Tt[4], Tt[1], Tt[2], Tt[3], a, b, c);
                        xt = (int)(T.x);
                        yt = (int)(T.y);
                        bm.SetPixel(0, 0, Im1.GetPixel(xt, Im1.Height - yt - 1));

                        P = Afin4(Pt[4], Pt[1], Pt[2], Pt[3], a, b, c);
                        xt = (int)(P.x);
                        yt = (int)(P.y);

                        formGraphics.DrawImageUnscaled(bm, xt, yt);

                        T = Afin4(Tt[3], Tt[4], Tt[1], Tt[2], a, b, c);
                        xt = (int)(T.x);
                        yt = (int)(T.y);
                        bm.SetPixel(0, 0, Im1.GetPixel(xt, Im1.Height - yt - 1));

                        P = Afin4(Pt[3], Pt[4], Pt[1], Pt[2], a, b, c);
                        xt = (int)(P.x);
                        yt = (int)(P.y);

                        formGraphics.DrawImageUnscaled(bm, xt, yt);

                        T = Afin4(Tt[2], Tt[3], Tt[4], Tt[1], a, b, c);
                        xt = (int)(T.x);
                        yt = (int)(T.y);
                        bm.SetPixel(0, 0, Im1.GetPixel(xt, Im1.Height - yt - 1));

                        P = Afin4(Pt[2], Pt[3], Pt[4], Pt[1], a, b, c);
                        xt = (int)(P.x);
                        yt = (int)(P.y);

                        formGraphics.DrawImageUnscaled(bm, xt, yt);

                    }

            //myPen.Dispose();
            //formGraphics.Dispose();
        }
        
        private void button3_Click(object sender, EventArgs e)    //  Dreapta
        {
            Fata(1, 0, 0, 0, 1, 0, 1);
            button4.Visible = true;
        }

        private void button4_Click(object sender, EventArgs e)   // Sus
        {
            Fata(2, 0, 1, 0, 1, 0, 0.75);
            button3.Visible = true; //button4.Visible = false;
        }

        private void button5_Click(object sender, EventArgs e)   // Fata
        {
            Fata(3, 0, 0, 0, 1, 0, 1);
            button3.Visible = true; //button5.Visible = false;
        }
    }
}

