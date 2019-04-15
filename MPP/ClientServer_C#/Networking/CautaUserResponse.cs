using Concurs.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking
{
    [Serializable]
    public class CautaUserResponse: Response
    {
        private User user;

        public CautaUserResponse(User user)
        {
            this.user = user;
        }

        public virtual User User
        {
            get
            {
                return user;
            }
        }
    }
}
