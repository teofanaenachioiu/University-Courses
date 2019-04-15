using chat.services;
using Concurs.model;
using System;
using System.Collections.Generic;

namespace Client{
    

    public class ClientCtrl: IObserver
    {
        public event EventHandler<ChatUserEventArgs> updateEvent; //ctrl calls it when it has received an update
        private readonly IServer server;
        private User currentUser;
        public ClientCtrl(IServer server)
        {
            this.server = server;
            currentUser = null;
        }

        public void login(String userId, String pass)
        {
            User user=new User(userId,pass);
            //server.login(user,this);
            Console.WriteLine("Login succeeded ....");
            currentUser = user;
            Console.WriteLine("Current user {0}", user);
        }
        

        protected virtual void OnUserEvent(ChatUserEventArgs e)
        {
            if (updateEvent == null) return;
            updateEvent(this, e);
            Console.WriteLine("Update Event called");
        }
    }
}
