using System;
using System.Net.Sockets;

using System.Threading;
namespace chat
{
    using chat.services;
    using Concurs.repository;
    using Concurs.repository.utils;
    using log4net.Config;
    using Networking;
    using server;
    using ServerTemplate;
    using System.Collections.Generic;

    class StartServer
    {
        public static void Main(string[] args)
        {
            IRepositoryUser userRepository;
            IRepositoryParticipant participantRepository;
            IRepositoryProba probaRepository;
            IRepositoryInscrieri inscrieriRepository;

            XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));

            IDictionary<String, string> props = new SortedList<String, String>();

            props.Add("ConnectionString", DBUtils.GetConnectionStringByName("concurs"));
            userRepository = new UserRepository(props);
            participantRepository = new ParticipantRepository(props);
            probaRepository = new ProbaRepository(props);
            inscrieriRepository = new InscrieriRepository(props);

            

            IServer serviceImpl = new ServerImpl(userRepository,participantRepository,probaRepository,inscrieriRepository);

           // IChatServer serviceImpl = new ChatServerImpl();
			SerialServer server = new SerialServer("127.0.0.1", 55555, serviceImpl);
            server.Start();
            Console.WriteLine("Server started ...");
            //Console.WriteLine("Press <enter> to exit...");
            Console.ReadLine();
            
        }
    }

    public class SerialServer: ConcurrentServer 
    {
        private IServer server;
        private ClientWorker worker;
        public SerialServer(string host, int port, IServer server) : base(host, port)
            {
                this.server = server;
                Console.WriteLine("SerialChatServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new ClientWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }
    
}
