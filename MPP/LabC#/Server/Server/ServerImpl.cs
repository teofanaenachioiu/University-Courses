using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System;
using chat.services;
using Concurs.repository;

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
    }
}
