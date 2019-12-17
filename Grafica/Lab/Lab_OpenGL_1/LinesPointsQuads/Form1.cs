using System;
using System.Drawing;
using System.Windows.Forms;
using OpenTK;
using OpenTK.Graphics.OpenGL;

namespace LinesPointsQuads
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void glControl1_Load(object sender, EventArgs e)
        {
            OnLoad();
        }

        private void glControl1_Paint(object sender, PaintEventArgs e)
        {
            OnRenderFrame();
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            OnUpdateFrame();
        }

        void OnLoad()
        {

        }

        void OnUpdateFrame()
        {
            Painting();
        }

        void OnRenderFrame()
        {
            Painting();
        }

        void Painting()
        {
            GL.Ortho(-1, 1, -1, 1, 0, 0);
            GL.LoadIdentity();
            GL.Clear(ClearBufferMask.ColorBufferBit | ClearBufferMask.DepthBufferBit);

            if (comboBox1.SelectedIndex == 0)
            {
                CreatePoint(new Vector2(0, 0), 5);
                CreatePoint(new Vector2(0.5f, 0.5f), 5);
                CreatePoint(new Vector2(0.4f, 0.4f), 5);
                CreatePoint(new Vector2(-0.4f, 0.4f), 5);
            }
            if (comboBox1.SelectedIndex == 1)
            {
                CreateLine(new Vector2(0, 0), new Vector2(0.4f, 0));
                CreateLine(new Vector2(0.4f, 0.4f), new Vector2(-0.4f, 0.4f));
            }
            if (comboBox1.SelectedIndex == 2)
            {
                CreateTriangles(new Vector2(0, 0), new Vector2(0.4f, 0), new Vector2(0.4f, 0.4f));
            }
            if (comboBox1.SelectedIndex == 3)
            {
                CreateQuad(new Vector2(0, 0), new Vector2(0.4f, 0), new Vector2(0.4f, 0.4f), new Vector2(-0.4f, 0.4f));
            }
            glControl1.SwapBuffers();
        }

        void CreatePoint(Vector2 position, float size)
        {

            GL.PointSize(size);
            GL.Begin(PrimitiveType.Points);
            GL.Color3(Color.FromArgb(trackBarRed.Value, trackBarGreen.Value, trackBarBlue.Value));
            GL.Vertex2(position);
            GL.End();
        }

        void CreateLine(Vector2 startPos, Vector2 endPos)
        {
            GL.Begin(PrimitiveType.Lines);
            GL.Color3(Color.FromArgb(trackBarRed.Value, trackBarGreen.Value, trackBarBlue.Value));
            GL.Vertex2(startPos);
            GL.Color3(Color.FromArgb(trackBarRed.Value, trackBarGreen.Value, trackBarBlue.Value));
            GL.Vertex2(endPos);
            GL.End();
        }

        void CreateTriangles(Vector2 point1, Vector2 point2, Vector2 point3)
        {
            GL.Begin(PrimitiveType.Triangles);
            GL.Color3(Color.FromArgb(trackBarRed.Value, trackBarGreen.Value, trackBarBlue.Value));
            GL.Vertex2(point1);
            GL.Color3(Color.FromArgb(trackBarRed.Value, trackBarGreen.Value, trackBarBlue.Value));
            GL.Vertex2(point2);
            GL.Color3(Color.FromArgb(trackBarRed.Value, trackBarGreen.Value, trackBarBlue.Value));
            GL.Vertex2(point3);
            GL.End();
        }
        void CreateQuad(Vector2 point1, Vector2 point2, Vector2 point3, Vector2 point4)
        {
            GL.Begin(PrimitiveType.Quads);
            GL.Color3(Color.FromArgb(trackBarRed.Value, trackBarGreen.Value, trackBarBlue.Value));
            GL.Vertex2(point1);
            GL.Color3(Color.FromArgb(trackBarRed.Value, trackBarGreen.Value, trackBarBlue.Value));
            GL.Vertex2(point2);
            GL.Color3(Color.FromArgb(trackBarRed.Value, trackBarGreen.Value, trackBarBlue.Value));
            GL.Vertex2(point3);
            GL.Color3(Color.FromArgb(trackBarRed.Value, trackBarGreen.Value, trackBarBlue.Value));
            GL.Vertex2(point4);
            GL.End();
        }

        private void trackBarRed_ValueChanged(object sender, EventArgs e)
        {
            textBoxRed.Text = trackBarRed.Value.ToString();
        }

        private void trackBarGreen_ValueChanged(object sender, EventArgs e)
        {
            textBoxGreen.Text = trackBarGreen.Value.ToString();
        }

        private void trackBarBlue_ValueChanged(object sender, EventArgs e)
        {
            textBoxBlue.Text = trackBarBlue.Value.ToString();
        }
    }
}
