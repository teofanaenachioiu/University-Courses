using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace Sem10_MAP_223.model
{
    class MessageTask : Task
    {

        public string Message { get; set; }
        public string From { get; set; }
        public string To { get; set; }
        public DateTime Date { get; set; }

        public override void Execute()
        {
            throw new NotImplementedException();
        }

        public override string ToString()
        {
            return Message+" "+From+" "+To+" "+ Date+".";
        }
    }
}
