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
namespace Lab_3D
{
    public partial class Form1 : Form
    {

        class Muchie
        {
            public int st, dr, tip_linie;
        } // Pot fi si caracteristici: Culoare, TipLinie(…)

        class Varf
        {
            public double x, y, z;
            public Varf(int X, int Y, int Z) { x = X; y = Y; z = Z; }
        }

        int u1, v1, u2, v2; // ViewPort
        double a, b, c, d; // Window

        int tip;
        double raza, alfa; // Pr. Par.=1, Perp.=2

       
        int u(double x) { return (int)((x - a) / (b - a) * (u2 - u1) + u1); }
        int v(double y) { return (int)((y - d) / (c - d) * (v2 - v1) + v1); }

        void ViewPort(int x1, int y1, int x2, int y2) { u1 = x1; v1 = y1; u2 = x2; v2 = y2; }
        void Window(double x1, double y1, double x2, double y2) { a = x1; d = y1; b = x2; c = y2; }

        void DefPr(double r, double a) { raza = r; alfa = a; } 

        double PrX(double x, double z) { return x - raza * z * Math.Cos(alfa); }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        double PrY(double y, double z) { return y - raza * z * Math.Sin(alfa); }
        double Px(Varf P) { return PrX(P.x, P.z); }
        double Py(Varf P) { return PrY(P.y, P.z); }

        public Form1()
        {
            InitializeComponent();
        }

        void LineOnGr(Graphics LineGr, Pen Pen, Varf P, Varf Q)
        {
            LineGr.DrawLine(Pen, u(Px(P)), v(Py(P)), u(Px(Q)), v(Py(Q)));
        }

        private void CreateAxes()
        {
            Graphics Ax = CreateGraphics();

            Pen myPen = new Pen(Color.Black)
            {
                DashStyle = System.Drawing.Drawing2D.DashStyle.Dash
            };

            Ax.DrawLine(myPen, u(0), v(0), u(b), v(0)); // Ox
            Ax.DrawLine(myPen, u(0), v(0), u(0), v(d)); //Oy
            Ax.DrawLine(myPen, u(0), v(0), u(PrX(0,-a)), v(PrY(0,-c))); //Oz
        }

        private void button1_Click(object sender, EventArgs e) 
        {

            ViewPort(50, 50, 550, 550);
            Window(-25, 25, 25, -25); // Fereasta Reală

            Pen myPen = new Pen(Color.Blue, 2);

            Pen blackPen = new Pen(Color.Blue,2)
            {
                DashStyle = System.Drawing.Drawing2D.DashStyle.Dash
            };

            Graphics formGraphics = CreateGraphics();

            StreamReader streamReader = new StreamReader("cub.txt"); 

            string line = streamReader.ReadLine();
            string[] elements = line.Split(new char[] { ' '});

            int n = Convert.ToInt32(elements[0]);

            Varf[] varfuri = new Varf[n + 1];
            for (int i = 1; i <= n; i++) // Cit. Vf.
            {
                line = streamReader.ReadLine();
                elements = line.Split(new char[] { ' ' });

                int X = Convert.ToInt32(elements[0]);
                int Z = Convert.ToInt32(elements[1]);
                int Y = Convert.ToInt32(elements[2]) ; 

                varfuri[i] = new Varf(X, Y, Z); 
            }

            line = streamReader.ReadLine();
            elements = line.Split(new char[] { ' '});

            int m = Convert.ToInt32(elements[0]);
            Muchie[] muchii = new Muchie[m + 1];

            for (int j = 1; j <= m; j++) 
            {
                line = streamReader.ReadLine();
                elements = line.Split(new char[] { ' '});

                muchii[j] = new Muchie
                {
                    st = Convert.ToInt32(elements[0]),
                    dr = Convert.ToInt32(elements[1]),
                    tip_linie = Convert.ToInt32(elements[2])
                };
            }

            line = streamReader.ReadLine(); // tip proiectie, raza, alfa
            elements = line.Split(new char[] { ' '});

            tip = Convert.ToInt32(elements[0]);
            raza = Convert.ToDouble(elements[1]);
            alfa = Convert.ToDouble(elements[2]);

            streamReader.Close();

            CreateAxes();

            for (int j = 1; j <= m; j++) // Desenare muchii
            {
                Varf P = varfuri[muchii[j].st];
                Varf Q = varfuri[muchii[j].dr];
                if (muchii[j].tip_linie == -1)
                {
                    LineOnGr(formGraphics, blackPen, P, Q);

                }
                else
                {
                    LineOnGr(formGraphics, myPen, P, Q);
                }
            }
            myPen.Dispose();
            formGraphics.Dispose();
        }
    }
}
