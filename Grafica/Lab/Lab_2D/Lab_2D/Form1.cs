using System.Windows.Forms;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;

namespace Lab_2D
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

            MessageGr.DrawString(s, myFont, myBrush, u(M.x)-10, v(M.y)-25);
        }

        void LineOnGr(Graphics LineGr, Pen Pen, Point P, Point Q) 
        {
            LineGr.DrawLine(Pen, u(P.x), v(P.y), u(Q.x), v(Q.y));
        }

        void PointOnGr(Graphics PointGr, Pen Pen, Point P) 
        {
            PointGr.DrawEllipse(Pen, u(P.x), v(P.y),1,1);
        }

        private void Form1_MouseClick(object sender, MouseEventArgs e)
        {
            Graphics Punct = CreateGraphics();

            Pen myPen = new Pen(Color.Red, 1);

            double x = u_1(e.X), y = v_1(e.Y);
            Rectangle myRectangle = new Rectangle(u(x)-2, v(y) - 2, 4, 4);

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
        }

        private void Form1_Paint(object sender, PaintEventArgs e)
        {
            ViewPort(0, 0, 500, 250); // (u1,v1,u2,v2)
            Window(-10, 5, 10, -5); // (a,d, b,c)

            Graphics SinusGr = CreateGraphics();

            // Axele de coordonate
            Pen penAxes = new Pen(Color.Black, 1);
            penAxes.DashStyle = System.Drawing.Drawing2D.DashStyle.Dash;

            LineOnGr(SinusGr, penAxes, new Point(-9.5,0), new Point(9.5,0));
            LineOnGr(SinusGr, penAxes, new Point(0, -1.5), new Point(0, 1.5));

            // Functia sinus
            Pen penSinus = new Pen(Color.Blue, 2);
            float y;

            for (double i=-3*Math.PI; i <= 3*Math.PI; i=i+Math.PI/100)
            {
                y = (float)Math.Sin(i);
                PointOnGr(SinusGr, penSinus, new Point(i, y));
            }
        }
    }
}

