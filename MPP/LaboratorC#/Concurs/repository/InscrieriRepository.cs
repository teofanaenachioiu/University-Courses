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
    public class InscrieriRepository : IRepository<KeyValuePair<int,int>, Inscriere>
    {
        private static readonly ILog log = LogManager.GetLogger("ProbaRepository");

        IDictionary<String, string> props;
        public InscrieriRepository(IDictionary<String, string> props)
        {
            log.Info("Creating InscrieriRepository... ");
            this.props = props;
        }

        public Inscriere FindOne(KeyValuePair<int,int> id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select idParticipant, idProba, data_op, operator from Inscrieri where idParticipant=@idPart and idProba=@idProba";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@idPart";
                paramId.Value = id.Key;
                comm.Parameters.Add(paramId);

                IDbDataParameter paramId1 = comm.CreateParameter();
                paramId1.ParameterName = "@idProba";
                paramId1.Value = id.Value;
                comm.Parameters.Add(paramId1);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idPa = dataR.GetInt32(0);
                        int idPr = dataR.GetInt32(1);
                        DateTime data_op = dataR.GetDateTime(2);
                        string operator_name = dataR.GetString(3);
                        Inscriere inscriere = new Inscriere(idPa,idPr,data_op,operator_name);
                        log.InfoFormat("Exiting findOne with value {0}", inscriere);
                        return inscriere;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public IEnumerable<Inscriere> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            log.InfoFormat("Entering FindAll...");
            IList<Inscriere> insc = new List<Inscriere>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select idParticipant, idProba, data_op, operator from Inscrieri";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idPa = dataR.GetInt32(0);
                        int idPr = dataR.GetInt32(1);
                        DateTime data_op = dataR.GetDateTime(2);
                        string operator_name = dataR.GetString(3);
                        Inscriere inscriere = new Inscriere(idPa, idPr, data_op, operator_name);
                        insc.Add(inscriere);
                    }
                }
            }
            log.InfoFormat("Exiting findAll");
            return insc;
        }

        private int countProbeParticipant(int idP)
        {
            IDbConnection con = DBUtils.getConnection(props);
            int size = -1;
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select count(*) from Inscrieri where idParticipant=@id";

                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = idP;
                comm.Parameters.Add(paramId);

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
            return size;
        }


        public void Save(Inscriere entity)
        {
            log.InfoFormat("Entering Save with new value {0}...", entity);
            var con = DBUtils.getConnection(props);

            if (countProbeParticipant(entity.Id.Key) >= 2)
                throw new RepositoryException("Participantul e deja inscris la doua probe");

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Inscrieri values (@idParticipant, @idProba, @data_op, @operator)";

                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@idParticipant";
                paramId.Value = entity.Id.Key;
                comm.Parameters.Add(paramId);

                IDbDataParameter paramId1 = comm.CreateParameter();
                paramId1.ParameterName = "@idProba";
                paramId1.Value = entity.Id.Value;
                comm.Parameters.Add(paramId1);

                var paramData = comm.CreateParameter();
                paramData.ParameterName = "@data_op";
                paramData.Value = entity.Data;
                comm.Parameters.Add(paramData);

                var paramOperator = comm.CreateParameter();
                paramOperator.ParameterName = "@operator";
                paramOperator.Value = entity.UsernameOperator;
                comm.Parameters.Add(paramOperator);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("Error: Nu s-a putut adauga inscrierea!");
                log.InfoFormat("Exiting Save");
            }

        }
        public void Delete(KeyValuePair<int,int> id)
        {
            log.InfoFormat("Entering Delete with new value {0}...", id);
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Inscrieri where idParticipant=@idPartic and idProba=@idProba";

                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@idPartic";
                paramId.Value = id.Key;
                comm.Parameters.Add(paramId);

                IDbDataParameter paramId1 = comm.CreateParameter();
                paramId1.ParameterName = "@idProba";
                paramId1.Value = id.Value;
                comm.Parameters.Add(paramId1);

                var rez = comm.ExecuteNonQuery();
                if (rez == 0)
                    throw new RepositoryException("Error: Inscrierea nu s-a putut sterge!");
                log.InfoFormat("Exiting Delete");
            }
        }

        public void Update(KeyValuePair<int,int> id, Inscriere entity)
        {
            log.InfoFormat("Entering Update with value {0}", id);
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Inscrieri set  data_op=@data_op, operator=@operator where idParticipant=@idPartic and idProba=@idProba";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@idPartic";
                paramId.Value = id.Key;
                comm.Parameters.Add(paramId);

                IDbDataParameter paramId1 = comm.CreateParameter();
                paramId1.ParameterName = "@idProba";
                paramId1.Value = id.Value;
                comm.Parameters.Add(paramId1);

                var paramData = comm.CreateParameter();
                paramData.ParameterName = "@data_op";
                paramData.Value = entity.Data;
                comm.Parameters.Add(paramData);

                var paramOperator = comm.CreateParameter();
                paramOperator.ParameterName = "@operator";
                paramOperator.Value = entity.UsernameOperator;
                comm.Parameters.Add(paramOperator);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("Error: Nu s-a putut actualiza inscrierea!");
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
                comm.CommandText = "select count(*) from Inscrieri";

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
