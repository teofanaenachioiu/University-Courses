using Curs12.Repository;
using Curs12.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Curs12.Service
{
    class PontajService
    {
        private IRepository<string, Pontaj> repo;

        public PontajService(IRepository<string, Pontaj> repo)
        {
            this.repo = repo;
        }

        public List<Pontaj> FindAllPontaje()
        {
            return repo.FindAll().ToList();
        }

        public Dictionary<Angajat,Double> ValoareOreLucrate()
        {
            //return (from p in FindAllPontaje()
            //       // where p.Date.Month==3
            //group p by p.Angajat into g
            //select new KeyValuePair<Angajat, Double>(
            //    g.Key,
            //    g.Sum(x => x.Sarcina.NrOreEstimate) * g.Key.VenitPeOra))
            //    .OrderByDescending(x => x.Value)
            //   .ToDictionary(x=>x.Key,x=>x.Value);

            return FindAllPontaje().GroupBy(x => x.Angajat)
                .Select(g => new KeyValuePair<Angajat, Double>(g.Key, g.Sum(x => x.Sarcina.NrOreEstimate * x.Angajat.VenitPeOra)))
                .ToDictionary(x=>x.Key,x=>x.Value);

        }

        public List<PontajDTO> Salar(int luna)
        {
            var res = from p in FindAllPontaje()
                      where p.Date.Month == luna
                      group p by p.Angajat into g
                      select new PontajDTO()
                              {
                                  NumeAngajat=g.Key.Nume,
                                  Nivel=g.Key.Nivel,
                                  Salar=g.Sum(x=>x.Sarcina.NrOreEstimate*x.Angajat.VenitPeOra)
                              };
            return res.ToList();
        }


    }
}
