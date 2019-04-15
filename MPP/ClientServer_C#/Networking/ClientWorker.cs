using System;
using System.Threading;
using System.Net;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using Services;
using Concurs.model;
using System.Collections.Generic;

namespace Networking
{

	public class ClientWorker :  IObserver //, Runnable
	{
		private IServer server;
		private TcpClient connection;

		private NetworkStream stream;
		private IFormatter formatter;
		private volatile bool connected;
		public ClientWorker(IServer server, TcpClient connection)
		{
			this.server = server;
			this.connection = connection;
			try
			{
				
				stream=connection.GetStream();
                formatter = new BinaryFormatter();
				connected=true;
			}
			catch (Exception e)
			{
                Console.WriteLine(e.StackTrace);
			}
		}

		public virtual void run()
		{
			while(connected)
			{
				try
				{
                    object request = formatter.Deserialize(stream);
					object response =handleRequest((Request)request);
					if (response!=null)
					{
					   sendResponse((Response) response);
					}
				}
				catch (Exception e)
				{
                    Console.WriteLine(e.StackTrace);
				}
				
				try
				{
					Thread.Sleep(1000);
				}
				catch (Exception e)
				{
                    Console.WriteLine(e.StackTrace);
				}
			}
			try
			{
				stream.Close();
				connection.Close();
			}
			catch (Exception e)
			{
				Console.WriteLine("Error "+e);
			}
		}

		private Response handleRequest(Request request)
		{
			Response response =null;
			if (request is LoginRequest)
			{
				Console.WriteLine("Login request ...");
				LoginRequest logReq =(LoginRequest)request;
				User user =logReq.User;
				try
                {
                    lock (server)
                    {
                        server.Login(user.Id, user.Hash, this);
                    }
					return new OkResponse();
				}
				catch (MyAppException e)
				{
					connected=false;
					return new ErrorResponse(e.Message);
				}
			}
            if (request is LogoutRequest)
            {
                Console.WriteLine("Logout request");
                LogoutRequest logReq = (LogoutRequest)request;
                User user = logReq.User;
                try
                {
                    lock (server)
                    {

                        server.Logout(user, this);
                    }
                    connected = false;
                    return new OkResponse();

                }
                catch (MyAppException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }
            if (request is CautaUserRequest)
            {
                Console.WriteLine("CautaUser Request ...");
                CautaUserRequest getReq = (CautaUserRequest)request;
                String username = getReq.Username;
                try
                {
                    User user;
                    lock (server)
                    {

                        user = server.Cauta(username);
                        Console.WriteLine("User: " + user);
                    }
                    return new CautaUserResponse(user);
                }
                catch (MyAppException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }
            if (request is ListaParticipantiRequest)
            {
                Console.WriteLine("ListaParticipanti Request ...");
                try
                {
                    Participant[] parts;
                    lock (server)
                    {
                        IEnumerable < Participant > part = server.ListaParticipanti();
                        int dim = 0;
                        foreach(Participant p in part)
                        {
                            dim += 1;
                        }
                        parts = new Participant[dim];
                        dim = 0;
                        foreach (Participant p in part)
                        {
                            parts[dim] = p;
                            dim += 1;
                        }
                    }
                    return new ListaParticipantiResponse(parts);
                }
                catch (MyAppException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is ListaParticipantiFiltratiRequest)
            {
                Console.WriteLine("ListaParticipantiFiltrati Request ...");
                ListaParticipantiFiltratiRequest getReq = (ListaParticipantiFiltratiRequest)request;
                String proba = getReq.Proba;
                String categorie = getReq.Categorie;
                try
                {
                    Participant[] parts;
                    lock (server)
                    {
                        IEnumerable<Participant> part = server.FiltreazaParticipantiKeyword(proba,categorie);
                        int dim = 0;
                        foreach (Participant p in part)
                        {
                            dim += 1;
                        }
                        parts = new Participant[dim];
                        dim = 0;
                        foreach (Participant p in part)
                        {
                            parts[dim] = p;
                            dim += 1;
                        }
                    }
                    return new ListaParticipantiFiltratiResponse(parts);
                }
                catch (MyAppException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

                if (request is ListaProbeNumeRequest)
            {
                Console.WriteLine("ListaProbeNume Request ...");
                try
                {
                    string[] parts;
                    lock (server)
                    {
                        IEnumerable<string> part = server.ListaProbeNume();
                        int dim = 0;
                        foreach (string p in part)
                        {
                            dim += 1;
                        }
                        parts = new string[dim];
                        dim = 0;
                        foreach (string p in part)
                        {
                            parts[dim] = p;
                            dim += 1;
                        }
                    }
                    return new ListaProbeNumeResponse(parts);
                }
                catch (MyAppException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is ListaCategoriiRequest)
            {
                Console.WriteLine("ListaCategorii Request ...");
                try
                {
                    string[] parts;
                    lock (server)
                    {
                        IEnumerable<string> part = server.ListaCategorii();
                        int dim = 0;
                        foreach (string p in part)
                        {
                            dim += 1;
                        }
                        parts = new string[dim];
                        dim = 0;
                        foreach (string p in part)
                        {
                            parts[dim] = p;
                            dim += 1;
                        }
                    }
                    return new ListaCategoriiResponse(parts);
                }
                catch (MyAppException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is ListaProbeRequest)
            {
                Console.WriteLine("ListaProbe Request ...");
                try
                {
                    Proba[] parts;
                    lock (server)
                    {
                        IEnumerable<Proba> part = server.ListaProbe();
                        int dim = 0;
                        foreach (Proba p in part)
                        {
                            dim += 1;
                        }
                        parts = new Proba[dim];
                        dim = 0;
                        foreach (Proba p in part)
                        {
                            parts[dim] = p;
                            dim += 1;
                        }
                    }
                    return new ListaProbeResponse(parts);
                }
                catch (MyAppException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is InscriereRequest)
            {
                Console.WriteLine("Inscriere request ...");
                InscriereRequest logReq = (InscriereRequest)request;
                string nume = logReq.Nume;
                int varsta = logReq.Varsta;
                Proba[] probe = logReq.Probe;
                string username = logReq.UsernameOperator;
                try
                {
                    lock (server)
                    {
                        List<Proba> enume = new List<Proba>(probe);
                        server.InscriereParticipant(nume,varsta,enume,username);
                    }
                    return new OkResponse();
                }
                catch (MyAppException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }


            return response;
		}

	private void sendResponse(Response response)
		{
			Console.WriteLine("sending response "+response);
            formatter.Serialize(stream, response);
            stream.Flush();
			
		}
	}

}