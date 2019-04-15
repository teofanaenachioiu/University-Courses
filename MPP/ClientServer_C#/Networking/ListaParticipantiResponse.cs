using Concurs.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking
{
    [Serializable]
    public class ListaParticipantiResponse : Response
    {
        private Participant[] participants;

        public ListaParticipantiResponse(Participant[] part)
        {
            this.participants = part;
        }

        public virtual Participant[] Participants
        {
            get
            {
                return participants;
            }
        }
    }
}
