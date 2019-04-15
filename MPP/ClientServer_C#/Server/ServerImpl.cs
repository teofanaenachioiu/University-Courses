using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System;
using Concurs.repository;
using Services;
using Concurs.model;
using Concurs.utils;

namespace chat.server
{
    public class ServerImpl: IServer
    {
        private IRepositoryUser userRepository;
        private IRepositoryParticipant participantRepository;
        private IRepositoryProba probaRepository;
        private IRepositoryInscrieri inscrieriRepository;
        private readonly IDictionary <String, IObserver> loggedClients;

    public ServerImpl(IRepositoryUser repositoryUser, IRepositoryParticipant repositoryParticipant, IRepositoryProba repositoryProba, IRepositoryInscrieri repositoryInscrieri) {
        userRepository = repositoryUser;
        participantRepository = repositoryParticipant;
        probaRepository = repositoryProba;
        inscrieriRepository = repositoryInscrieri;
        loggedClients=new Dictionary<String, IObserver>();
    }

        public void Login(string username, string password, IObserver client)
        {
            User user = userRepository.FindOne(username);
           

            if (user != null)
            {
                string userHash = user.Hash;
                if (userHash == PasswordStorage.CreateHash(password))
                {
                    if (loggedClients.ContainsKey(user.Id))
                        throw new MyAppException("User already logged in.");
                    loggedClients[user.Id] = client;
                }
                else
                {
                    throw new MyAppException("Invalid password.");
                }
            }
            else
            {
                throw new MyAppException("Authentication failed.");
            }               
        }

        public User Cauta(string username)
        {
            return userRepository.FindOne(username);
        }

        public void Logout(User user, IObserver client)
        {
            IObserver localClient = loggedClients[user.Id];
            if (localClient == null)
                throw new MyAppException("User " + user.Id + " is not logged in.");
            loggedClients.Remove(user.Id);
        }

        public IEnumerable<Participant> ListaParticipanti()
        {
            return participantRepository.FindAll();
        }

        public IEnumerable<string> ListaCategorii()
        {
            return probaRepository.ListaCategorii();
        }

        public IEnumerable<String> ListaProbeNume()
        {
            return probaRepository.ListaProbeNume();
        }

        public IEnumerable<Participant> FiltreazaParticipantiKeyword(string proba, string categorie)
        {
            if (proba == null)
                return inscrieriRepository.CautaParticipantiDupaCategorie(categorie);
            if (categorie == null)
                return inscrieriRepository.cautaParticipantiDupaProba(proba);

            return inscrieriRepository.CautaParticipantDupaProbaCategorie(proba, categorie);
        }

        public IEnumerable<Proba> ListaProbe()
        {
            return probaRepository.FindAll();
        }

        private bool VerificaCtg(int varsta, Proba proba)
        {
            string categorie = proba.Categorie.ToString();
            string variab = categorie.Substring(10);
            string[] varste = variab.Split('_');
            int min = int.Parse(varste[0]);
            int max = int.Parse(varste[1]);
            if (varsta >= min && varsta <= max)
                return true;
            return false;
        }

        public void InscriereParticipant(string nume, int varsta, List<Proba> listaProbe, string usernameOperator)
        {
            foreach (Proba p in listaProbe)
            {
                if (!VerificaCtg(varsta, p))
                    throw new MyAppException("Participantul nu se poate inscrie la aceasta categorie de varsta");
            }
            if (listaProbe.Count > 2)
                throw new MyAppException("Participantul nu se poate inscrie la mai mult de 2 probe");
            int idPartic = participantRepository.Save(new Participant(nume, varsta));
            listaProbe.ForEach(pr => inscrieriRepository.Save(new Inscriere(idPartic, pr.Id, usernameOperator)));
        }
    }
}
