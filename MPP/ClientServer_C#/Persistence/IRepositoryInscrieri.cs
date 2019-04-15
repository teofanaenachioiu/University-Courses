using Concurs.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Concurs.repository
{
    public interface IRepositoryInscrieri: IRepository<KeyValuePair<int,int>, Inscriere>
    {
        IEnumerable<Inscriere> FindProbeDupaParticipant(int id);

        IEnumerable<Participant> CautaParticipantiDupaCategorie(string categorie);
        IEnumerable<Participant> cautaParticipantiDupaProba(string proba);
        IEnumerable<Participant> CautaParticipantDupaProbaCategorie(string proba, string categorie);
        int NrParticipantiProba(Proba proba);
        int NrParticipantiCategorie(string categorie);
        void DeleteAll();
    }
}
