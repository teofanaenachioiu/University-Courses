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
        IList<Inscriere> FindProbeDupaParticipant(int id);

        IList<Participant> CautaParticipantiDupaCategorie(string categorie);
        IList<Participant> CautaParticipantiDupaProba(string proba);
        IList<Participant> CautaParticipantDupaProbaCategorie(string proba, string categorie);
        int NrParticipantiProba(Proba proba);
        int NrParticipantiCategorie(string categorie);
        void DeleteAll();
    }
}
