using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Sem10_MAP_223.model
{
     abstract class Task : IHasID<string>
    {
        public string ID { get ; set; }

        private string description;

        public string Description
        {
            get { return description; }
            set { description = value; }
        }

        public abstract void Execute();

        public override string ToString()
        {
            return ID +" "+Description;
        }

       
    }
}
