using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Concurs;
using Concurs.model;
using Concurs.repository;
using Concurs.utils;
using Thrift.Protocol;
using Thrift.Transport;

namespace ConcursServer
{
    class ConServer : ConcursService.Iface, ObserverService.Iface
    {
        private IRepositoryUser userRepository;
        private IRepositoryParticipant participantRepository;
        private IRepositoryProba probaRepository;
        private IRepositoryInscrieri inscrieriRepository;
        private Dictionary<string,int> loggedClients;
        private int portForClients;

        public ConServer(IRepositoryUser repositoryUser, IRepositoryParticipant repositoryParticipant, 
            IRepositoryProba repositoryProba, IRepositoryInscrieri repositoryInscrieri)
        {
            userRepository = repositoryUser;
            participantRepository = repositoryParticipant;
            probaRepository = repositoryProba;
            inscrieriRepository = repositoryInscrieri;
            loggedClients = new Dictionary<string,int>();
            portForClients = 9900;
        }

        public User cauta(string username)
        {
            return userRepository.FindOne(username);
        }

        public List<Participant> filtreazaParticipantiKeyword(string proba, string categorie)
        {
            if (proba == null)
                return (List<Participant>)inscrieriRepository.CautaParticipantiDupaCategorie(categorie);
            if (categorie == null)
                return (List<Participant>)inscrieriRepository.CautaParticipantiDupaProba(proba);

            return (List<Participant>)inscrieriRepository.CautaParticipantDupaProbaCategorie(proba, categorie);
        }

        public void inscriereParticipant(string nume, int varsta, List<Proba> listaProbe, string usernameOperator)
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
            notifyClient();
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

        public List<string> listaCategorii()
        {
            return (List<string>)probaRepository.listaCategorii();
        }

        public List<Participant> listaParticipanti()
        {
            return (List<Participant>)participantRepository.FindAll();
        }

        public List<Proba> listaProbe()
        {
            return (List<Proba>)probaRepository.FindAll();
        }

        public List<ProbaDTO> listaProbeDTO()
        {
            List<Proba> pr = this.listaProbe();
            List<ProbaDTO> probeDTO = new List<ProbaDTO>();

            foreach (Proba p in pr)
            {
                int nrP = this.NrParticipantiProba(p);
                ProbaDTO proba = new ProbaDTO(p.Id, p.Denumire, p.Categorie, nrP);
                probeDTO.Add(proba);
            }
            return probeDTO;
        }

        private int NrParticipantiProba(Proba proba)
        {
            return inscrieriRepository.NrParticipantiProba(proba);
        }

        public List<string> listaProbeNume()
        {
            return (List<string>)probaRepository.listaProbeNume();
        }

        public void logout(User user)
        {
            loggedClients.Remove(user.Username);
            Console.WriteLine("User log out: " + user.Username);
        }

        public void notifyClient()
        {
            for (int i = 0; i < loggedClients.Values.Count; i++)
            {
                int port = loggedClients.Values.ElementAt(i);
                TTransport transport = new TSocket("localhost", port);
                TProtocol protocol = new TBinaryProtocol(transport);
                ObserverService.Client client = new ObserverService.Client(protocol);

                transport.Open();
                client.notifyClient();
                Console.WriteLine("Notific clientul cu portul "+ port);
                transport.Close();
            }
            
        }

        int ConcursService.ISync.login(string username, string password)
        {
            User user = userRepository.FindOne(username);
            if (user != null)
            {
                string userHash = user.Password;
                if (userHash == PasswordStorage.CreateHash(password))
                {
                    if (loggedClients.ContainsKey(user.Username))
                        throw new MyAppException("User already logged in.");
                    portForClients += 1;
                    loggedClients.Add(user.Username, portForClients);
                    Console.WriteLine("New user: " + user.Username);
                    return portForClients;
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
    }
}
