using Curs12.Model;
using Curs12.Repository;

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Curs12.Service
{
    class AngajatService
    {
        private IRepository<string, Angajat> repo;

        public AngajatService(IRepository<string, Angajat> repo)
        {
            this.repo = repo;
        }

        public List<Angajat> SortByKnowledgeLevel()
        {
            List<Angajat> angajati = repo.FindAll().ToList();
            angajati.Sort((x, y) => {
                if (x.Nivel.ToString().Equals(y.Nivel.ToString()))
                    return -x.VenitPeOra.CompareTo(y.VenitPeOra);
                else
                    return x.Nivel.ToString().CompareTo(y.Nivel.ToString()); });

            return angajati;
        }

        public List<Angajat> SortByKnowledgeLevel_Query()
        {
            var result =
                from a in repo.FindAll()
                orderby a.Nivel ascending, a.VenitPeOra descending
                select a;
            return result.ToList();
        }

        public List<KeyValuePair<KnowledgeLevel, double>> MediaVenitPeOraPeNivel()
        {
            List<Angajat> angajati = repo.FindAll().ToList();
            // IEnumerable methods extensions
            return angajati
                .GroupBy(a => a.Nivel)
                .Select(a =>
                         new KeyValuePair<KnowledgeLevel, double>(a.Key, a.Average(x => x.VenitPeOra))).ToList();
        }

        public List<KeyValuePair<KnowledgeLevel, double>> MediaVenitPeOraPeNivel_Query()
        {
            // IQueryable methods extensions
            List<Angajat> angajati = repo.FindAll().ToList();
            var rez = from a in angajati 
                      group a by a.Nivel into g
                      select new KeyValuePair<KnowledgeLevel, double>(g.Key, g.Average(x => x.VenitPeOra));
            return rez.ToList();
        }

        private List<Angajat> FilterBy_Querry(Predicate<Angajat> predicate)
        {
            List<Angajat> angajati = repo.FindAll().ToList();
            var result =
                from a in repo.FindAll()
                where predicate(a)
                select a;
            return result.ToList();
        }

        private List<Angajat> FilterBy(Predicate<Angajat> predicate)
        {
            List<Angajat> angajati = repo.FindAll().ToList();
            return angajati.Where((x, y) => { return predicate(x); }).ToList();
        }

        public List<Angajat> FilterByKnowledgeLevel(KnowledgeLevel level)
        {
            return FilterBy(x => x.Nivel.Equals(level));
        }

        public List<Angajat> FilterByKnowledgeLevel_Query(KnowledgeLevel level)
        {
            return FilterBy_Querry(x => x.Nivel.Equals(level));
        }

        public List<Angajat> FindAllAngajati()
        {
            return repo.FindAll().ToList();
        }
    }
}

