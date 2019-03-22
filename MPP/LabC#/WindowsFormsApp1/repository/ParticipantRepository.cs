using Concurs.model;
using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Concurs.repository.utils
{
    public class ParticipantRepository : IRepositoryParticipant
    {
        private static readonly ILog log = LogManager.GetLogger("ParticipantRepository");

        IDictionary<String, string> props;
        public ParticipantRepository(IDictionary<String, string> props)
        {
            log.Info("Creating ParticipantRepository... ");
            this.props = props;
        }

        public Participant FindOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Participanti where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idP = dataR.GetInt32(0);
                        string nume = dataR.GetString(1);
                        int varsta = dataR.GetInt32(2);
                        Participant participant = new Participant(idP, nume, varsta);
                        log.InfoFormat("Exiting findOne with value {0}", participant);
                        return participant;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public IEnumerable<Participant> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            log.InfoFormat("Entering FindAll...");
            IList<Participant> partic = new List<Participant>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id, nume, varsta from Participanti";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idP = dataR.GetInt32(0);
                        String nume = dataR.GetString(1);
                        int varsta = dataR.GetInt32(2);
                        Participant participant = new Participant(idP, nume, varsta);
                        partic.Add(participant);
                    }
                }
            }
            log.InfoFormat("Exiting findAll");
            return partic;
        }
        public void Save(Participant entity)
        {
            log.InfoFormat("Entering Save with new value {0}...", entity);
            var con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Participanti(nume,varsta) values (@nume, @varsta)";

                var paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@nume";
                paramNume.Value = entity.Nume;
                comm.Parameters.Add(paramNume);

                var paramVarsta = comm.CreateParameter();
                paramVarsta.ParameterName = "@varsta";
                paramVarsta.Value = entity.Varsta;
                comm.Parameters.Add(paramVarsta);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("Error: Nu s-a putut adauga participantul!");
                log.InfoFormat("Exiting Save");
            }

        }
        public void Delete(int id)
        {
            log.InfoFormat("Entering Delete with new value {0}...", id);
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Participanti where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var rez = comm.ExecuteNonQuery();
                if (rez == 0)
                    throw new RepositoryException("Error: Nu s-a putut sterge participantul!");
                log.InfoFormat("Exiting Delete");
            }
        }

        public void Update(int id, Participant entity)
        {
            log.InfoFormat("Entering Update with value {0}", id);
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Participanti set  nume=@nume, varsta=@varsta where id=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                var paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@nume";
                paramNume.Value = entity.Nume;
                comm.Parameters.Add(paramNume);

                var paramVarsta = comm.CreateParameter();
                paramVarsta.ParameterName = "@varsta";
                paramVarsta.Value = entity.Varsta;
                comm.Parameters.Add(paramVarsta);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("Error: Nu s-a putut actualiza participantul!");
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
                comm.CommandText = "select count(*) from Participanti";

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
    }
}
