using Concurs.model;
using Concurs.repository.utils;
using log4net.Config;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Configuration;

namespace Concurs
{
    class Program
    {
        static void Main(string[] args)
        {//configurare jurnalizare folosind log4net
            XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));

            Console.WriteLine("Configuration Settings for tasksDB {0}", GetConnectionStringByName("catalogTest"));
            IDictionary<String, string> props = new SortedList<String, String>();
            props.Add("ConnectionString", GetConnectionStringByName("catalogTest"));

            Console.WriteLine("Participant Repository DB ...");
            ParticipantRepository repo = new ParticipantRepository(props);

            Console.WriteLine("Participantii din db");
            foreach (Participant p in repo.FindAll())
            {
                Console.WriteLine(p);
            }

        }

        static string GetConnectionStringByName(string name)
        {
            // Assume failure.
            string returnValue = null;

            // Look for the name in the connectionStrings section.
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];

            // If found, return the connection string.
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }
    }

}