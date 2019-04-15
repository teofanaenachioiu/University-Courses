using System;
using System.Collections.Generic;
using System.Threading;
using System.Net;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using Concurs.model;
using Services;
using System.Collections;

namespace Networking
{ 
	public class ServerProxy : IServer
	{
		private string host;
		private int port;

		private IObserver client;

		private NetworkStream stream;
		
        private IFormatter formatter;
		private TcpClient connection;

		private Queue<Response> responses;
		private volatile bool finished;
        private EventWaitHandle _waitHandle;
		public ServerProxy(string host, int port)
		{
			this.host = host;
			this.port = port;
			responses=new Queue<Response>();
		}

		public virtual void Login(String username, String parola, IObserver client)
		{
			initializeConnection();
            User user = new User(username, parola);
			sendRequest(new LoginRequest(user));
			Response response =readResponse();
			if (response is OkResponse)
			{
				this.client=client;
				return;
			}
			if (response is ErrorResponse)
			{
				ErrorResponse err =(ErrorResponse)response;
				closeConnection();
				throw new MyAppException(err.Message);
			}
		}

        public virtual User Cauta(string username)
        {
            sendRequest(new CautaUserRequest(username));
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new MyAppException(err.Message);
            }
            CautaUserResponse resp = (CautaUserResponse)response;
            User user = resp.User;
            return user;
        }

        public virtual void Logout(User user, IObserver client)
        {
            sendRequest(new LogoutRequest(user));
            Response response = readResponse();
            closeConnection();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new MyAppException(err.Message);
            }
        }

        public virtual IEnumerable<Participant> ListaParticipanti()
        {
            sendRequest(new ListaParticipantiRequest());
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new MyAppException(err.Message);
            }
            ListaParticipantiResponse resp = (ListaParticipantiResponse)response;
            Participant[] parts = resp.Participants;
            IEnumerable<Participant> enume = new LinkedList<Participant>(parts);
            return enume;
        }

        public IEnumerable<string> ListaProbeNume()
        {
            sendRequest(new ListaProbeNumeRequest());
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new MyAppException(err.Message);
            }
            ListaProbeNumeResponse resp = (ListaProbeNumeResponse)response;
            string[] nume = resp.NumeProbe;
            IEnumerable<string> enume = new LinkedList<string>(nume);
            return enume;
        }

        public IEnumerable<string> ListaCategorii()
        {
            sendRequest(new ListaCategoriiRequest());
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new MyAppException(err.Message);
            }
            ListaCategoriiResponse resp = (ListaCategoriiResponse)response;
            string[] nume = resp.Categorii;
            IEnumerable<string> enume = new LinkedList<string>(nume);
            return enume;
        }

        public IEnumerable<Participant> FiltreazaParticipantiKeyword(string proba, string categorie)
        {
            sendRequest(new ListaParticipantiFiltratiRequest(proba,categorie));
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new MyAppException(err.Message);
            }
            ListaParticipantiFiltratiResponse resp = (ListaParticipantiFiltratiResponse)response;
            Participant[] parts = resp.Participants;
            IEnumerable<Participant> enume = new LinkedList<Participant>(parts);
            return enume;
        }

        public IEnumerable<Proba> ListaProbe()
        {
            sendRequest(new ListaProbeRequest());
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new MyAppException(err.Message);
            }
            ListaProbeResponse resp = (ListaProbeResponse)response;
            Proba[] parts = resp.Probe;
            IEnumerable<Proba> enume = new LinkedList<Proba>(parts);
            return enume;
        }

        public void InscriereParticipant(string nume, int varsta, List<Proba> listaProbe, string usernameOperator)
        {

            Proba[] parts;
            int dim = listaProbe.Count;
            parts = new Proba[dim];
            dim = 0;
            foreach (Proba p in listaProbe)
            {
                parts[dim] = p;
                dim += 1;
            }
            sendRequest(new InscriereRequest(nume,varsta,parts,usernameOperator));
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new MyAppException(err.Message);
            }
        }

        private void closeConnection()
		{
			finished=true;
			try
			{
				stream.Close();
				//output.close();
				connection.Close();
                _waitHandle.Close();
				client=null;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}

		}

		private void sendRequest(Request request)
		{
			try
			{
                formatter.Serialize(stream, request);
                stream.Flush();
			}
			catch (Exception e)
			{
				throw new MyAppException("Error sending object "+e);
			}

		}

		private Response readResponse()
		{
			Response response =null;
			try
			{
                _waitHandle.WaitOne();
				lock (responses)
				{
                    //Monitor.Wait(responses); 
                    response = responses.Dequeue();
                
				}
				

			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
			return response;
		}
		private void initializeConnection()
		{
			 try
			 {
				connection=new TcpClient(host,port);
				stream=connection.GetStream();
                formatter = new BinaryFormatter();
				finished=false;
                _waitHandle = new AutoResetEvent(false);
				startReader();
			}
			catch (Exception e)
			{
                Console.WriteLine(e.StackTrace);
			}
		}
		private void startReader()
		{
			Thread tw =new Thread(run);
			tw.Start();
		}

		public virtual void run()
			{
				while(!finished)
				{
                try
                {
                    object response = formatter.Deserialize(stream);
                    Console.WriteLine("response received " + response);
              
						/*if (response is UpdateResponse)
						{
							 handleUpdate((UpdateResponse)response);
						}
						else
						{*/
							
							lock (responses)
							{
                                					
								 
                                responses.Enqueue((Response)response);
                               
							}
                            _waitHandle.Set();
						//}
					}
					catch (Exception e)
					{
						Console.WriteLine("Reading error "+e);
					}
					
				}
			}

       
    }

}