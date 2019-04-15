using Concurs.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
namespace Services
{
    public interface IServer
    {
        void Login(String username, String password, IObserver client);
        User Cauta(String username);
        void Logout(User user, IObserver client);
        IEnumerable<Participant> ListaParticipanti();
        IEnumerable<String> ListaProbeNume();
        IEnumerable<String> ListaCategorii();
        IEnumerable<Participant> FiltreazaParticipantiKeyword(string proba, string categorie);
        IEnumerable<Proba> ListaProbe();
        void InscriereParticipant(string nume, int varsta, List<Proba> listaProbe, string usernameOperator);
    }
}
