using System.Windows.Forms;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;

namespace Lab_2D_Histograma
{
    public partial class Form1 : Form
    {
        // Punct pe grafic
        class Point
        {
            public double x, y;
            public Point(double X, double Y) { x = X; y = Y; }
        }

        public Form1()
        {
            InitializeComponent();

        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        int u1, v1, u2, v2; // ViewPort - Fereastra Ecran
        double a, b, c, d; // Window - Fereastra Reala

        int u(double x)
        {
            return (int)((x - a) / (b - a) * (u2 - u1) + u1);
        }

        int v(double y)
        {
            return (int)((y - d) / (c - d) * (v2 - v1) + v1);
        }

        double u_1(int u)
        {
            return (u - u1) * (b - a) / (u2 - u1) + a;
        }

        double v_1(int v)
        {
            return (v - v1) * (c - d) / (v2 - v1) + d;
        }

        void ViewPort(int x1, int y1, int x2, int y2)
        {
            u1 = x1;
            v1 = y1;
            u2 = x2;
            v2 = y2;
        }

        void Window(double x1, double y1, double x2, double y2) { a = x1; d = y1; b = x2; c = y2; }


        void TextOnGr(Graphics MessageGr, string s, Point M)
        {
            Font myFont = new Font("Arial", 10, FontStyle.Regular);
            Brush myBrush = new SolidBrush(Color.Black);

            MessageGr.DrawString(s, myFont, myBrush, u(M.x) - 10, v(M.y) - 25);
        }

        void LineOnGr(Graphics LineGr, Pen Pen, Point P, Point Q)
        {
            LineGr.DrawLine(Pen, u(P.x), v(P.y), u(Q.x), v(Q.y));
        }

        private void button2_Click(object sender, EventArgs e)
        {
            ViewPort(0, 0, 600, 300); // (u1,v1,u2,v2)
            Window(-10, 25, 50, -5); // (a,d, b,c)

            createAxes();
            // x, y top
            Random rnd = new Random();
            List<Color> list = new List<Color>()
            {
                Color.Red,
                Color.Green,
                Color.Blue,
            };
            for (int i = 1; i <= 12; i++)
            {
                int temp = rnd.Next(-5, 20);
                createRectangle((i - 1) * 2, temp, i * 2, 0, list[(i - 1) % 3]);
            }
        }

        void PointOnGr(Graphics PointGr, Pen Pen, Point P)
        {
            PointGr.DrawEllipse(Pen, u(P.x), v(P.y), 1, 1);
        }

        private void Form1_MouseClick(object sender, MouseEventArgs e)
        {
            Graphics Punct = CreateGraphics();

            Pen myPen = new Pen(Color.Red, 1);

            double x = u_1(e.X), y = v_1(e.Y);
            Rectangle myRectangle = new Rectangle(u(x) - 2, v(y) - 2, 4, 4);

            Punct.DrawEllipse(myPen, myRectangle);
        }

        private void Form1_MouseHover(object sender, MouseEventArgs e)
        {
            Graphics Punct = CreateGraphics();

            double x = u_1(e.X), y = v_1(e.Y);

            label2.Text = "x = " + x.ToString();
            label3.Text = "y = " + y.ToString();

            // Verific daca punctul apartine grafigului
            // 0.05 marja de eroare
            if (Math.Abs(y - Math.Sin(x)) < 0.05)
            {
                label2.Font = new Font(label2.Font.Name, label2.Font.Size, FontStyle.Bold);
                label3.Font = new Font(label3.Font.Name, label3.Font.Size, FontStyle.Bold);
            }
            else
            {
                label2.Font = new Font(label2.Font.Name, label2.Font.Size, FontStyle.Regular);
                label3.Font = new Font(label3.Font.Name, label3.Font.Size, FontStyle.Regular);
            }

            if (x >= 0 && x <= 24)
            {
                int val = (int)x/ 2 % 12 + 1;
                string va = "Luna " + getLuna(val);
                label6.Text = va;
                label7.Text = y + " Celsius";
            }
            else
            {
                label6.Text = "N/A";
                label7.Text = "N/A";
            }
        }

        private string getLuna(int val)
        {
            switch (val)
            {
                case 1:
                    return "Ianuarie";
                case 2:
                    return "Februarie";
                case 3:
                    return "Martie";
                case 4:
                    return "Aprilie";
                case 5:
                    return "Mai";
                case 6:
                    return "Iunie";
                case 7:
                    return "Iulie";
                case 8:
                    return "August";
                case 9:
                    return "Septembrie";
                case 10:
                    return "Octombrie";
                case 11:
                    return "Noiembrie";
                case 12:
                    return "Decembrie";

            }
            return "N/A";
        }

        private void createAxes()
        {
            Graphics Ax = CreateGraphics();

            Pen myPen = new Pen(Color.Black);
            myPen.DashStyle = System.Drawing.Drawing2D.DashStyle.Dash;

            LineOnGr(Ax, myPen, new Point(a + 1, 0), new Point(b - 1, 0));
            LineOnGr(Ax, myPen, new Point(0, c + 1), new Point(0, d - 1));
        }

        private void createRectangle(int x1, int y1, int step, int bottom, Color color)
        {
            int x = u(x1);
            int y = v(y1);
            if (y1 < 0)
            {
                y = v(0);
            }
            int width = Math.Abs(u(step) - x);
            int height = Math.Abs(v(bottom) - y);
            if (y1 < 0)
            {
                height = v(y1) - v(0);
            }

            Graphics Drept = CreateGraphics();
            SolidBrush solidbrush = new SolidBrush(color);
            Pen myPen = new Pen(solidbrush);
            Rectangle myRectangle = new Rectangle(x, y, width, height);
            Drept.FillRectangle(solidbrush, myRectangle);
        }


    }
}

