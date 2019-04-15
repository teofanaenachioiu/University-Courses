using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking
{
    [Serializable]
    public class ListaCategoriiResponse: Response
    {
        private string[] categorii;

        public ListaCategoriiResponse(string[] part)
        {
            this.categorii = part;
        }

        public virtual string[] Categorii
        {
            get
            {
                return categorii;
            }
        }
    }
}
