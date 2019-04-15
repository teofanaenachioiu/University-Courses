using chat.services;
using Networking;
using System.Windows.Forms;
using System;
using WindowsFormsApp1;

namespace Client
{
    class ClientStart
    {
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);


            //IChatServer server=new ChatServerMock();          
            IServer server = new ServerProxy("127.0.0.1", 55555);
            ClientCtrl ctrl = new ClientCtrl(server);
            Application.Run(new Form1(ctrl));
        }
    }
}
