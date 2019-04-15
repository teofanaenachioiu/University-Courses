using Concurs.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking
{
    [Serializable]
    public class CautaUserRequest : Request
    {
        private String username;

        public CautaUserRequest(String user)
        {
            this.username = user;
        }

        public virtual String Username
        {
            get
            {
                return username;
            }
        }
    }
}
