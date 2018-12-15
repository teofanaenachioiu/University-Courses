using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace CatalogMAP.validator
{
    [Serializable]
    public class ValidationException : Exception
    {
        public ValidationException()
        {
        }

        public ValidationException(string message) : base(message)
        {
        }

        public ValidationException(string message, Exception innerException) : base(message, innerException)
        {
        }

        protected ValidationException(SerializationInfo info, StreamingContext context) : base(info, context)
        {
        }
    }
}
