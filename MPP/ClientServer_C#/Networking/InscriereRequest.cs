using Concurs.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking
{
    [Serializable]
    public class InscriereRequest : Request
    {
        private string nume;
        private int varsta;
        private Proba[] probe;
        private string usernameOperator;

        public InscriereRequest(string n, int v, Proba[] listaProbe, string username)
        {
            this.nume = n;
            this.varsta = v;
            this.probe = listaProbe;
            this.usernameOperator = username;
        }
        
        public virtual string Nume
        {
            get
            {
                return nume;
            }
        }
        public virtual int Varsta
        {
            get
            {
                return varsta;
            }
        }

        public virtual Proba[] Probe
        {
            get
            {
                return probe;
            }
        }
        public virtual string UsernameOperator
        {
            get
            {
                return usernameOperator;
            }
        }
    }
}
