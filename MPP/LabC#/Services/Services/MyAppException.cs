using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace chat.services
{
    public class MyAppException : Exception
    {
        public MyAppException():base() { }

        public MyAppException(String msg) : base(msg) { }

        public MyAppException(String msg, Exception ex) : base(msg, ex) { }

    }
}
