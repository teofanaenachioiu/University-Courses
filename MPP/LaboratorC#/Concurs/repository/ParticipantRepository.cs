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
    public class ParticipantRepository : IRepository<int, Participant>
    {
        private static readonly ILog log = LogManager.GetLogger("ParticipantRepository");

        IDictionary<String, string> props;
        public ParticipantRepository(IDictionary<String, string> props)
        {
            log.Info("Creating ParticipantRepository ");
            this.props = props;
        }

        public Participant FindOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id, nume, varsta from Participanti where id=@id";
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

            return partic;
        }
        public Participant Save(Participant entity)
        {
            /*var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into SortingTasks  values (@idT, @desc, @elems, @orderC, @algo)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@idT";
                paramId.Value = entity.Id;
                comm.Parameters.Add(paramId);

                var paramDesc = comm.CreateParameter();
                paramDesc.ParameterName = "@desc";
                paramDesc.Value = entity.Description;
                comm.Parameters.Add(paramDesc);

                var paramElems = comm.CreateParameter();
                paramElems.ParameterName = "@elems";
                paramElems.Value = entity.Elems;
                comm.Parameters.Add(paramElems);

                IDbDataParameter paramOrder = comm.CreateParameter();
                paramOrder.ParameterName = "@orderC";
                paramOrder.Value = entity.Order.ToString();
                comm.Parameters.Add(paramOrder);

                IDbDataParameter paramAlgo = comm.CreateParameter();
                paramAlgo.ParameterName = "@algo";
                paramAlgo.Value = entity.Algorithm.ToString();
                comm.Parameters.Add(paramAlgo);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("No task added !");
            }*/
            return null;

        }
        public Participant Delete(int id)
        {
            /*IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from SortingTasks where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0)
                    throw new RepositoryException("No task deleted!");
            }*/
            return null;
        }

        public Participant Update(Participant entity)
        {
            throw new NotImplementedException();
        }
    }
}
