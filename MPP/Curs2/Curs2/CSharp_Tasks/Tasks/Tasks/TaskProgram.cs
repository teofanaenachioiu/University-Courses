using System;
using System.Collections.Generic;
using tasks.model;
using tasks.utils;
using tasks.runner;
using tasks.repository;
using log4net;
using log4net.Config;

using System.Configuration;

namespace Sem10
{
	class MainClass
	{
		public static void Main (string[] args)
		{
			//configurare jurnalizare folosind log4net
			XmlConfigurator.Configure(new System.IO.FileInfo(args[0]));
			Console.WriteLine("Configuration Settings for tasksDB {0}",GetConnectionStringByName("tasksDB"));
			IDictionary<String, string> props = new SortedList<String, String>();
			props.Add("ConnectionString", GetConnectionStringByName("tasksDB"));

			Console.WriteLine("Sorting Tasks Repository DB ...");
			SortingTaskDbRepository repo = new SortingTaskDbRepository(props);

			Console.WriteLine("Taskurile din db");
			foreach (SortingTask t in repo.findAll())
			{
				Console.WriteLine(t);
			}
			SortingTask task = repo.findOne(4);
			repo.delete(4);
			task.Description = "Ana are pere";
			repo.save(task);

			Console.WriteLine("Taskurile din db dupa stergere/adaugare");
			foreach (SortingTask t in repo.findAll())
			{
				Console.WriteLine(t);
			}


			
		}

		static string GetConnectionStringByName(string name)
		{
			// Assume failure.
			string returnValue = null;

			// Look for the name in the connectionStrings section.
			ConnectionStringSettings settings =ConfigurationManager.ConnectionStrings[name];

			// If found, return the connection string.
			if (settings != null)
				returnValue = settings.ConnectionString;

			return returnValue;
		}

	}


}
