using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking
{
    [Serializable]
    public class ListaProbeNumeResponse : Response
    {
        private string[] numeProbe;

        public ListaProbeNumeResponse(string[] nume)
        {
            this.numeProbe = nume;
        }

        public virtual string[] NumeProbe
        {
            get
            {
                return numeProbe;
            }
        }
    }
}
