
using Concurs.model;
using Model;
using Services;
using System;
using System.Collections.Generic;

namespace Client{
    

    public class ClientCtrl: IObserver
    {
        public event EventHandler<UserEventArgs> updateEvent; //ctrl calls it when it has received an update
        private readonly IServer server;
        private User currentUser;
        public ClientCtrl(IServer server)
        {
            this.server = server;
            currentUser = null;
        }

        public void Login(String userId, String pass)
        {
            
            server.Login(userId,pass,this);
            Console.WriteLine("Login succeeded ....");
            currentUser = server.Cauta(userId);
        }

        public void Logout()
        {

            server.Logout(currentUser, this);
            Console.WriteLine("Logout succeeded ....");
        }


        public User Cauta(String userId)
        {
            return currentUser;
        }

        public User GetUser()
        {
            return currentUser;
        }

        public IEnumerable<Participant> ListaParticipanti()
        {
            return server.ListaParticipanti();
        }

        public IEnumerable<Participant> FiltreazaParticipantiKeyword(string proba, string categorie)
        {
            return server.FiltreazaParticipantiKeyword(proba, categorie);
        }

        public IEnumerable<Proba> ListaProbe()
        {
            return server.ListaProbe();
        }

        public IEnumerable<string> ListaProbeNume()
        {
            return server.ListaProbeNume();
        }

        public IEnumerable<string> ListaCategorii()
        {
            return server.ListaCategorii();
        }

        public void InscriereParticipant(string nume, int varsta, List<Proba> listaProbe, string usernameOperator)
        {
            server.InscriereParticipant(nume, varsta, listaProbe, usernameOperator);
           // this.Update();
        }

        public IEnumerable<ProbaDTO> ListaProbeDTO()
        {
            return server.ListaProbeDTO();
        }

        protected virtual void OnUserEvent(UserEventArgs e)
        {
            if (updateEvent == null) return;
            updateEvent(this, e);
            Console.WriteLine("Update Event called");
        }

        public void Update()
        {
            Console.WriteLine("I have to update tables.");
            UserEventArgs userArgs = new UserEventArgs(UserEvent.Update, null);
            OnUserEvent(userArgs);
        }
    }
}
