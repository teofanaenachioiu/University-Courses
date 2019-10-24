using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Laborator1
{
    public partial class Form1 : Form
    {


        List<PointF> points = new List<PointF>();
        bool canDraw = true;
        public Form1()
        {
            InitializeComponent();
            points = getPoints(16, 20, 600);
        }

        private void Form1_Load(object sender, EventArgs e)
        {
        }

        List<PointF> getPoints(float a, float b, int count)
        {
            List<PointF> points = new List<PointF>();
       
            for (int i = -count; i < count; i++)
            {
                float x = i;
                float y = (float)Math.Sqrt((((x - 300) * (x - 300)) / (a * a) - 1) * b * b);
                points.Add(new PointF(x, y + 125));
                points.Add(new PointF(x, -y + 125));
            }

            return points;
        }

        private void panel1_Paint_1(Graphics g)
        {
            if (points.Count < 2) return;  // no lines to draw, yet

            using (Pen pen = new Pen(Color.Blue)
            {
                Width = 1,
                LineJoin = LineJoin.Round
            })
            {
                var arr = points.ToArray();
                for (int i = 0; i < arr.Length; i++)
                {
                    try
                    {
                        g.DrawEllipse(pen, arr[i].X, arr[i].Y, 5, 5);
                    }
                    catch(Exception e)
                    {
                        Debug.WriteLine(e.Message);
                    }
                }
                //e.Graphics.DrawLines(pen, points.ToArray());
            }
        }

        private void buttonCircle_Click(object sender, EventArgs e)
        {
            Graphics g = panel1.CreateGraphics();
            Pen pen = new Pen(Color.Blue)
            {
                Width = 1,
                LineJoin = LineJoin.Round
            };
            if (canDraw)
            {
                var arr = points.ToArray();
                for (int i = 0; i < arr.Length; i++)
                {
                    try
                    {
                        g.DrawEllipse(pen, arr[i].X, arr[i].Y, 1, 1);
                    }
                    catch (Exception ex)
                    {
                        Debug.WriteLine(ex.Message);
                    }
                }
            }
            else
            {
                Invalidate();
                canDraw = true;
                panel1.Refresh();
            }
           
        }
    }
}
