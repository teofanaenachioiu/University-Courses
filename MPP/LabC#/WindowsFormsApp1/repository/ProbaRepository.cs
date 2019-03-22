using Concurs.model;
using Concurs.repository.utils;
using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Concurs.repository
{
    public class ProbaRepository : IRepositoryProba
    {
        private static readonly ILog log = LogManager.GetLogger("ProbaRepository");

        IDictionary<String, string> props;
        public ProbaRepository(IDictionary<String, string> props)
        {
            log.Info("Creating ProbaRepository... ");
            this.props = props;
        }

        public Proba FindOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id, denumire, categorie from Probe where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idP = dataR.GetInt32(0);
                        string denumire = dataR.GetString(1);
                        string categorie = dataR.GetString(2);
                        Proba proba = new Proba(idP, denumire, categorie);
                        log.InfoFormat("Exiting findOne with value {0}", proba);
                        return proba;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public IEnumerable<Proba> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            log.InfoFormat("Entering FindAll...");
            IList<Proba> probe = new List<Proba>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id, denumire, categorie from Probe";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idP = dataR.GetInt32(0);
                        string denumire = dataR.GetString(1);
                        string categorie = dataR.GetString(2);
                        Proba proba = new Proba(idP, denumire, categorie);
                        probe.Add(proba);
                    }
                }
            }
            log.InfoFormat("Exiting findAll");
            return probe;
        }
        public void Save(Proba entity)
        {
            log.InfoFormat("Entering Save with new value {0}...", entity);
            var con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Probe(denumire,categorie) values (@denumire, @categorie)";

                var paramDenumire = comm.CreateParameter();
                paramDenumire.ParameterName = "@denumire";
                paramDenumire.Value = entity.Denumire;
                comm.Parameters.Add(paramDenumire);

                var paramCategorie = comm.CreateParameter();
                paramCategorie.ParameterName = "@categorie";
                paramCategorie.Value = entity.Categorie;
                comm.Parameters.Add(paramCategorie);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("Error: Nu s-a putut adauga proba!");
                log.InfoFormat("Exiting Save");
            }

        }
        public void Delete(int id)
        {
            log.InfoFormat("Entering Delete with new value {0}...", id);
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Probe where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var rez = comm.ExecuteNonQuery();
                if (rez == 0)
                    throw new RepositoryException("Error: Proba nu s-a putut sterge!");
                log.InfoFormat("Exiting Delete");
            }
        }

        public void Update(int id, Proba entity)
        {
            log.InfoFormat("Entering Update with value {0}", id);
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Probe set  denumire=@denumire, categorie=@categorie where id=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                var paramDenumire = comm.CreateParameter();
                paramDenumire.ParameterName = "@denumire";
                paramDenumire.Value = entity.Denumire;
                comm.Parameters.Add(paramDenumire);

                var paramCategorie = comm.CreateParameter();
                paramCategorie.ParameterName = "@categorie";
                paramCategorie.Value = entity.Categorie;
                comm.Parameters.Add(paramCategorie);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("Error: Nu s-a putut actualiza proba!");
                log.InfoFormat("Exiting Update");
            }
        }

        public int Size()
        {
            log.InfoFormat("Calculating database size...");
            IDbConnection con = DBUtils.getConnection(props);
            int size = -1;
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select count(*) from Probe";

                var rez = comm.ExecuteNonQuery();
                if (rez == 0)
                    throw new RepositoryException("Size error !");

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        size = dataR.GetInt32(0);
                    }
                }
            }
            log.InfoFormat("Finished calculation");
            return size;
        }

        public IEnumerable<string> ListaCategorii()
        {
            IDbConnection con = DBUtils.getConnection(props);
            log.InfoFormat("Entering ListaCategorii...");
            IList<string> categorii = new List<string>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select distinct categorie from Probe";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        string categorie = dataR.GetString(0);
                        categorii.Add(categorie);
                    }
                }
            }
            log.InfoFormat("Exiting ListaCategorii");
            return categorii;
        }

        public IEnumerable<string> ListaProbeNume()
        {
            IDbConnection con = DBUtils.getConnection(props);
            log.InfoFormat("Entering ListaProbeNume...");
            IList<string> denumiri = new List<string>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select distinct denumire from Probe";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        string denum = dataR.GetString(0);
                        denumiri.Add(denum);
                    }
                }
            }
            log.InfoFormat("Exiting ListaCategorii");
            return denumiri;
        }
    }
}
