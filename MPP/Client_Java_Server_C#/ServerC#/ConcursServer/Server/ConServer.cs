using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Concurs;
using Concurs.repository;
using Concurs.utils;

namespace ConcursServer
{
    class ConServer : Concurs.ConcursService.Iface
    {
        private IRepositoryUser userRepository;
        private ISet<string> loggedClients = new HashSet<string>();

        public ConServer(IRepositoryUser repositoryUser)
        {
            userRepository = repositoryUser;
        }

        public User cauta(string username)
        {
            return userRepository.FindOne(username);
        }

        public void login(string username, string password)
        {
            User user = userRepository.FindOne(username);


            if (user != null)
            {
                string userHash = user.Password;
                if (userHash == PasswordStorage.CreateHash(password))
                {
                    if (loggedClients.Contains(user.Username))
                        throw new MyAppExecption("User already logged in.");
                    loggedClients.Add(user.Username);
                    Console.WriteLine("New user: " + user.Username);
                }
                else
                {
                    throw new MyAppExecption("Invalid password.");
                }
            }
            else
            {
                throw new MyAppExecption("Authentication failed.");
            }
        }

        public void logout(User user)
        {
            loggedClients.Remove(user.Username);
            Console.WriteLine("User log out: " + user.Username);
        }
    }
}
