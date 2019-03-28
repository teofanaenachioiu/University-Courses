using Concurs.model;
using Concurs.repository;
using Concurs.repository.utils;
using Concurs.utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Concurs.service
{
    public class ServiceOperator: Subject
    {
        private IRepositoryParticipant repoParticipant;
        private IRepositoryProba repoProba;
        private IRepositoryInscrieri repoInscriere;

        public ServiceOperator(IRepositoryParticipant repoParticipant, IRepositoryProba repoProba, IRepositoryInscrieri repoInscriere)
        {
            this.repoParticipant = repoParticipant;
            this.repoProba = repoProba;
            this.repoInscriere = repoInscriere;
        }

        public IEnumerable<Participant> FiltreazaParticipantiKeyword(string proba, string categorie)
        {
            if (proba == null)
                return repoInscriere.CautaParticipantiDupaCategorie(categorie);
            if (categorie == null)
                return repoInscriere.cautaParticipantiDupaProba(proba);

            return repoInscriere.CautaParticipantDupaProbaCategorie(proba, categorie);
        }

        public IEnumerable<Proba> ListaProbe()
        {
            return repoProba.FindAll();
        }

        public IEnumerable<Participant> ListaParticipanti()
        {
            return repoParticipant.FindAll();
        }

        public IEnumerable<string> ListaCategorii()
        {
            return repoProba.ListaCategorii();
        }

        public IEnumerable<String> ListaProbeNume()
        {
            return repoProba.ListaProbeNume();
        }

        public int NrParticipantiProba(Proba proba)
        {
            return repoInscriere.NrParticipantiProba(proba);
        }

        public int NrParticipantiCategorie(String categorie)
        {
            return repoInscriere.NrParticipantiCategorie(categorie);
        }

        public void InscriereParticipant(string nume, int varsta, List<Proba> listaProbe, string usernameOperator)
        {
            foreach (Proba p in listaProbe)
            {
                if (!VerificaCtg(varsta, p))
                    throw new RepositoryException("Participantul nu se poate inscrie la aceasta categorie de varsta");
            }
            if (listaProbe.Count > 2)
                throw new RepositoryException("Participantul nu se poate inscrie la mai mult de 2 probe");
            int idPartic = repoParticipant.Save(new Participant(nume, varsta));
            listaProbe.ForEach(pr=>repoInscriere.Save(new Inscriere(idPartic, pr.Id, usernameOperator)));
            base.Notify();
        }

        public void StergeToateInregistrarile()
        {
            repoInscriere.DeleteAll();
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
    }
}
