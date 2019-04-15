using Concurs.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking
{
    [Serializable]
    public class ListaProbeResponse : Response
    {
        private Proba[] probe;

        public ListaProbeResponse(Proba[] part)
        {
            this.probe = part;
        }

        public virtual Proba[] Probe
        {
            get
            {
                return probe;
            }
        }
    }
}
