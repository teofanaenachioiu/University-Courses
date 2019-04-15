using Concurs.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking
{
    [Serializable]
    public class ListaParticipantiFiltratiRequest : Request
    {
        private string proba;
        private string categorie;
        public ListaParticipantiFiltratiRequest(string p, string c)
        {
            proba = p;
            categorie = c;
        }
        public virtual string Proba
        {
            get
            {
                return proba;
            }
        }
        public virtual string Categorie
        {
            get
            {
                return categorie;
            }
        }
    }
}
