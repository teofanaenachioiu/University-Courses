using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking
{
    [Serializable]
    public class ErrorResponse : Response
    {
        private string message;

        public ErrorResponse(string message)
        {
            this.message = message;
        }

        public virtual string Message
        {
            get
            {
                return message;
            }
        }
    }
}
