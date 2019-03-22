using Concurs.model;
using Concurs.repository.utils;
using log4net.Config;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Configuration;
using System.Windows.Forms;
using WindowsFormsApp1;

namespace Concurs
{
    class Program
    {
        [STAThread]
        static void Main(string[] args)
        {
            XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));
            
            IDictionary<String, string> props = new SortedList<String, String>();

            props.Add("ConnectionString", repository.DBUtils.GetConnectionStringByName("concurs"));
            
            ParticipantRepository repo = new ParticipantRepository(props);

            Console.WriteLine("Dimensiune baza de date: "+repo.Size());
        
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        

    }

        
    }

}