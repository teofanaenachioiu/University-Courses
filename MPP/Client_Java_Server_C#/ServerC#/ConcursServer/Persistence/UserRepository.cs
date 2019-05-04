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
    public class UserRepository : IRepositoryUser
    {
        private static readonly ILog log = LogManager.GetLogger("UserRepository");

        IDictionary<String, string> props;
        public UserRepository(IDictionary<String, string> props)
        {
            log.Info("Creating UserRepository... ");
            this.props = props;
        }

        public User FindOne(string id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Users where username=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        string username = dataR.GetString(0);
                        string hash = dataR.GetString(1);
                        string tip = dataR.GetString(2);
                        User user;
                        if (tip == "OPERATOR")
                        {
                            user = new User(username, hash, TipUser.OPERATOR);
                        }
                        else
                        {
                            user = new User(username, hash, TipUser.ADMIN);
                        }
                        log.InfoFormat("Exiting findOne with value {0}", user);
                        return user;
                    }
                }
            }
            log.InfoFormat("Exiting findOne");
            return null;
        }

        public IList<User> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            log.InfoFormat("Entering FindAll...");
            IList<User> users = new List<User>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Users";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        string username = dataR.GetString(0);
                        string hash = dataR.GetString(1);
                        string tip = dataR.GetString(2);
                        User user;
                        if(tip == "OPERATOR")
                        {
                            user = new User(username, hash, TipUser.OPERATOR);
                        }
                        else
                        {
                            user = new User(username, hash, TipUser.ADMIN);
                        }
                            
                        users.Add(user);
                    }
                }
            }
            log.InfoFormat("Exiting findAll");
            return users;
        }
        public string Save(User entity)
        {
            log.InfoFormat("Entering Save with new value {0}...", entity);
            var con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Users values (@username, @hash, @tip)";

                var paramUsername = comm.CreateParameter();
                paramUsername.ParameterName = "@username";
                paramUsername.Value = entity.Username;
                comm.Parameters.Add(paramUsername);

                var paramHash = comm.CreateParameter();
                paramHash.ParameterName = "@hash";
                paramHash.Value = entity.Password;
                comm.Parameters.Add(paramHash);

                var paramTip = comm.CreateParameter();
                paramTip.ParameterName = "@tip";
                paramTip.Value = entity.TipUser.ToString();
                comm.Parameters.Add(paramTip);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("Error: Nu s-a putut adauga userul!");
                log.InfoFormat("Exiting Save");
                
            }
            return entity.Username;
        }
        public void Delete(string id)
        {
            log.InfoFormat("Entering Delete with new value {0}...", id);
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Users where username=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

           
                var rez = comm.ExecuteNonQuery();
                if (rez == 0)
                    throw new RepositoryException("Error: Userul nu s-a putut sterge!");
                log.InfoFormat("Exiting Delete");
            }
        }

        public void Update(string id, User entity)
        {
            log.InfoFormat("Entering Update with value {0}", id);
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Users set hash=@hash, tip=@tip where username=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                var paramHash = comm.CreateParameter();
                paramHash.ParameterName = "@hash";
                paramHash.Value = entity.Password;
                comm.Parameters.Add(paramHash);

                var paramTip = comm.CreateParameter();
                paramTip.ParameterName = "@tip";
                paramTip.Value = entity.TipUser.ToString();
                comm.Parameters.Add(paramTip);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("Error: Nu s-a putut actualiza userul!");
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
                comm.CommandText = "select count(*) from Users";

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
