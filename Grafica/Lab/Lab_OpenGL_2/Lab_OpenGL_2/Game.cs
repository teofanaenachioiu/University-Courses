using System;
using OpenTK.Graphics.OpenGL;
using System.Drawing;
using OpenTK;
using OpenTK.Input;

namespace Lab_OpenGL_2
{
    class Game : GameWindow
    {
        double theta = 0.0;
        int texture;
        float x = 0, y = 0, z = 0;

        public Game() : base(512, 512, new OpenTK.Graphics.GraphicsMode(32, 24, 0, 4))
        {

        }

        protected override void OnKeyPress(KeyPressEventArgs e)
        {
            KeyboardState k = OpenTK.Input.Keyboard.GetState();
        }

        protected override void OnResize(EventArgs e)
        {
            GL.Viewport(0, 0, Width, Height);
            GL.MatrixMode(MatrixMode.Projection);
            GL.LoadIdentity();
            Matrix4 matrix4 = Matrix4.Perspective(45.0f, Width / Height, 1.0f, 100.0f);
            GL.LoadMatrix(ref matrix4);
            GL.MatrixMode(MatrixMode.Modelview);
        }


        protected override void OnRenderFrame(FrameEventArgs e)
        {
            GL.LoadIdentity();
            GL.Clear(ClearBufferMask.ColorBufferBit | ClearBufferMask.DepthBufferBit);

            GL.PushMatrix();
            GL.Translate(0.0, 0.0, -50.0);
            //Rotatia e mereu dupa translatie
            // GL.Rotate(theta, 1.0, 0.0, 0.0);

            x = OpenTK.Input.Mouse.GetCursorState().X;
            y = OpenTK.Input.Mouse.GetCursorState().Y;
            GL.Rotate(theta, x, y, 0.0);

            GL.Scale(0.7, 0.7, 0.7);

            draw_cube();

            GL.PopMatrix();

            SwapBuffers();

            theta += 1.0;
            if (theta > 360) theta -= 360;
        }

        protected override void OnLoad(EventArgs e)
        {
            Title = "Cube OpenTK";

            GL.ClearColor(Color.CornflowerBlue);
            GL.Enable(EnableCap.DepthTest);

            // Light
            GL.Enable(EnableCap.Lighting);

            float[] light_position = { 20, 20, 80 };
            float[] light_diffuse = { 1.0f, 0.0f, 0.0f };
            float[] light_ambient = { 0.5f, 0.5f, 0.5f };

            GL.Light(LightName.Light0, LightParameter.Position, light_position);
            GL.Light(LightName.Light0, LightParameter.Diffuse, light_diffuse);
            GL.Light(LightName.Light0, LightParameter.Ambient, light_ambient);

            GL.Enable(EnableCap.Light0);
            
            //texturing
            GL.Enable(EnableCap.Texture2D);
            GL.GenTextures(1, out texture);
            GL.BindTexture(TextureTarget.Texture2D, texture);

            System.Drawing.Imaging.BitmapData bitmapData = loadImage(@"D:\University-Courses\Grafica\Lab\Lab_OpenGL_2\Lab_OpenGL_2\create.bmp");
            GL.TexImage2D(TextureTarget.Texture2D, 0, PixelInternalFormat.Rgb, bitmapData.Width, bitmapData.Height, 0, PixelFormat.Bgr, PixelType.UnsignedByte, bitmapData.Scan0);
            GL.GenerateMipmap(GenerateMipmapTarget.Texture2D);
        }

        private void draw_cube()
        {
            GL.Begin(BeginMode.Quads);

            GL.Color3(1.0, 1.0, 1.0);
            GL.BindTexture(TextureTarget.Texture2D, texture);


            //front
            GL.Normal3(0.0, 0.0, 1.0);

            GL.Vertex3(-10.0, -10.0, 10.0);
            GL.TexCoord2(0, 0);

            GL.Vertex3(10.0, -10.0, 10.0);
            GL.TexCoord2(1, 0);

            GL.Vertex3(10.0, 10.0, 10.0);
            GL.TexCoord2(1, 1);

            GL.Vertex3(-10.0, 10.0, 10.0);
            GL.TexCoord2(0, 1);


            //back
            GL.Normal3(0.0, 0.0, -1.0);

            GL.Vertex3(-10.0, -10.0, -10.0);
            GL.TexCoord2(0, 0);

            GL.Vertex3(10.0, -10.0, -10.0);
            GL.TexCoord2(1, 0);

            GL.Vertex3(10.0, 10.0, -10.0);
            GL.TexCoord2(1, 1);

            GL.Vertex3(-10.0, 10.0, -10.0);
            GL.TexCoord2(0, 1);


            //top
            GL.Normal3(0.0, 1.0, 0.0);

            GL.Vertex3(10.0, 10.0, 10.0);
            GL.TexCoord2(0, 0);

            GL.Vertex3(10.0, 10.0, -10.0);
            GL.TexCoord2(1, 0);

            GL.Vertex3(-10.0, 10.0, -10.0);
            GL.TexCoord2(1, 1);

            GL.Vertex3(-10.0, 10.0, 10.0);
            GL.TexCoord2(0, 1);


            //bottom
            GL.Normal3(0.0, -1.0, 0.0);

            GL.Vertex3(10.0, -10.0, 10.0);
            GL.TexCoord2(0, 0);

            GL.Vertex3(10.0, -10.0, -10.0);
            GL.TexCoord2(1, 0);

            GL.Vertex3(-10.0, -10.0, -10.0);
            GL.TexCoord2(1, 1);

            GL.Vertex3(-10.0, -10.0, 10.0);
            GL.TexCoord2(0, 1);


            //right
            GL.Normal3(1.0, 0.0, 0.0);

            GL.Vertex3(10.0, -10.0, 10.0);
            GL.TexCoord2(0, 0);

            GL.Vertex3(10.0, -10.0, -10.0);
            GL.TexCoord2(1, 0);

            GL.Vertex3(10.0, 10.0, -10.0);
            GL.TexCoord2(1, 1);

            GL.Vertex3(10.0, 10.0, 10.0);
            GL.TexCoord2(0, 1);


            //left
            GL.Normal3(-1.0, 0.0, -1.0);

            GL.Vertex3(-10.0, -10.0, 10.0);
            GL.TexCoord2(0, 0);

            GL.Vertex3(-10.0, -10.0, -10.0);
            GL.TexCoord2(1, 0);

            GL.Vertex3(-10.0, 10.0, -10.0);
            GL.TexCoord2(1, 1);

            GL.Vertex3(-10.0, 10.0, 10.0);
            GL.TexCoord2(0, 1);

            GL.End();
        }

        private System.Drawing.Imaging.BitmapData loadImage(string filename)
        {
            Bitmap bmp = new Bitmap(filename);

            Rectangle rectangle = new Rectangle(0, 0, bmp.Width, bmp.Height);
            System.Drawing.Imaging.BitmapData bmpdata = bmp.LockBits(rectangle, System.Drawing.Imaging.ImageLockMode.ReadOnly, System.Drawing.Imaging.PixelFormat.Format24bppRgb);
            bmp.UnlockBits(bmpdata);

            return bmpdata;
        }
    }
}