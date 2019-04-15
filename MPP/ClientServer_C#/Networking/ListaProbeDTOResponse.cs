using Concurs.model;
using Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking
{
    [Serializable]
    public class ListaProbeDTOResponse : Response
    {
        private ProbaDTO[] probe;

        public ListaProbeDTOResponse(ProbaDTO[] part)
        {
            this.probe = part;
        }

        public virtual ProbaDTO[] Probe
        {
            get
            {
                return probe;
            }
        }
    }
}
