using System;
using System.Collections.Generic;
using System.ComponentModel;
                             // using System.Data;   Sters!
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

using Tao.OpenGl;
using Tao.Platform.Windows;

namespace Vf_OpGl
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            simpleOpenGlControl1.InitializeContexts();
            Gl.glClearColor(0, 0, 0.5f, 0);   // ~ Blue  
            int height = simpleOpenGlControl1.Height;
            int width = simpleOpenGlControl1.Width;
            Gl.glViewport(0, 0, width, height);
            Gl.glMatrixMode(Gl.GL_PROJECTION);
            Gl.glLoadIdentity();
            Glu.gluPerspective(45.0f, (double)width / (double)height, 0.01f, 500.0f);
        }

        double xrot, yrot, zrot = 0;

        private void Gl_Paint(object sender, PaintEventArgs e)
        {

            Gl.glClear(Gl.GL_COLOR_BUFFER_BIT | Gl.GL_DEPTH_BUFFER_BIT); //clear buffers to preset values
            Gl.glMatrixMode(Gl.GL_MODELVIEW);
            Gl.glLoadIdentity();                 // load the identity matrix
            Gl.glTranslated(0, 0, -4);           //moves our figure (x,y,z)
            
            Gl.glRotated(xrot += 3.25, 1, 0, 0);  //rotate on x
            Gl.glRotated(yrot += 23.23, 0, 1, 0); //rotate on y
            Gl.glRotated(zrot += 0.92, 0, 0, 1);  //rotate on z

            Gl.glBegin(Gl.GL_LINE_LOOP);       // Drawing GL_LINE_LOOP 
            Gl.glColor4d(255, 0, 255, 100);    // Magenta  _ Jos
            Gl.glVertex3d(-1, -1, -1);
            Gl.glVertex3d(1, -1, -1);
            Gl.glVertex3d(1, -1, 1);
            Gl.glVertex3d(-1, -1, 1);
            Gl.glEnd();

            Gl.glBegin(Gl.GL_LINE_LOOP);
            Gl.glColor4d(255, 0, 255, 100);     // Magenta  _ Sus
            Gl.glVertex3d(-1, 1, -1);
            Gl.glVertex3d(-1, 1, 1);
            Gl.glVertex3d(1, 1, 1);
            Gl.glVertex3d(1, 1, -1);
            Gl.glEnd();

            Gl.glBegin(Gl.GL_LINE_LOOP);    
            Gl.glColor4d(255,0,0, 100);        // Red  _ Spate
            Gl.glVertex3d(1, 1, -1);
            Gl.glVertex3d(1, -1, -1);
            Gl.glVertex3d(-1, -1, -1);
            Gl.glVertex3d(-1, 1, -1);
            Gl.glEnd();
        
            Gl.glBegin(Gl.GL_LINE_LOOP);
            Gl.glColor4d(255, 255, 0, 100);   // Yellow   ~ Fata
            Gl.glVertex3d(-1, 1, 1);
            Gl.glVertex3d(-1, -1, 1);
            Gl.glVertex3d(1, -1, 1);
            Gl.glVertex3d(1, 1, 1);
            Gl.glEnd();
            
        }
        

        private void Rs(object sender, EventArgs e)
        {
           
        }
    }
}
