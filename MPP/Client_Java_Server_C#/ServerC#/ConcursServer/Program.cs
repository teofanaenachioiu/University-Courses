﻿using Concurs;
using Concurs.repository;
using Concurs.repository.utils;
using log4net.Config;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Thrift.Server;
using Thrift.Transport;

namespace ConcursServer
{
    class Program
    {
        static void Main(string[] args)
        {
            IRepositoryUser userRepository;
            IRepositoryParticipant participantRepository;
            IRepositoryProba probaRepository;
            IRepositoryInscrieri inscrieriRepository;

            try
            {
                XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));

                IDictionary<String, string> props = new SortedList<String, String>();

                props.Add("ConnectionString", DBUtils.GetConnectionStringByName("concurs"));
                userRepository = new UserRepository(props);
                participantRepository = new ParticipantRepository(props);
                probaRepository = new ProbaRepository(props);
                inscrieriRepository = new InscrieriRepository(props);

                ConServer handler = new ConServer(userRepository, participantRepository, probaRepository, inscrieriRepository);

                ConcursService.Processor processor = new ConcursService.Processor(handler);
                TServerTransport serverTransport = new TServerSocket(9095);
                TServer server = new TThreadPoolServer(processor, serverTransport);
                Console.WriteLine("Starting the server...");
                server.Serve();

            }
            catch (Exception x)
            {
                Console.WriteLine(x.StackTrace);
            }
            Console.WriteLine("done.");
        }
    }
}