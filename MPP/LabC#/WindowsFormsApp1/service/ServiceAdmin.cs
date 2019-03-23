using Concurs.model;
using Concurs.repository;
using Concurs.utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Concurs.service
{
    class ServiceAdmin: Subject
    {
        private IRepositoryUser repository;

        public ServiceAdmin(IRepositoryUser repository)
        {
            this.repository = repository;
        }

        public User CreateUser(string username, string parola)
        {  
            string hash= PasswordStorage.CreateHash(parola);
            User user = new User(username, hash, "OPERATOR");
            repository.Save(user);
            return user;
        }

        public User CreateUser() 
        {
            string hash= PasswordStorage.CreateHash("admin");
            User user=new User("admin", hash, "ADMIN");
            repository.Save(user);
            return user;
        }

        public bool VerificareParola(string username, string password)
        {
            User user = repository.FindOne(username);

            if (user != null)
            {
                string userHash = user.Hash;
                if (userHash == PasswordStorage.CreateHash(password))
                {
                    return true;
                }             
            }
            return false;
        }

        public User Cauta(string username)
        {
            User entity = repository.FindOne(username);
            return entity;
        }
    }
}
