using System;
using System.Windows.Forms;
using WindowsFormsApp1;

namespace Concurs
{
    class Program
    {
        [STAThread]
        static void Main(string[] args)
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        }    
    }
}