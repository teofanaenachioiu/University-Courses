using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Concurs.repository.utils
{
    public class RepositoryException : ApplicationException
    {
        public RepositoryException() { }
        public RepositoryException(String mess) : base(mess) { }
        public RepositoryException(String mess, Exception e) : base(mess, e) { }
    }
}
