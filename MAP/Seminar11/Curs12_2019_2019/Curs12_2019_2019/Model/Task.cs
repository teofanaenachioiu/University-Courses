using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Curs12.Model
{
    abstract class Task : IHadID<string>
    {
        private string description;

        public Task()
        {
        }

        public string Description
        {
            get { return description; }
            set { description = value; }
        }

        public string ID { get; set ; }
        //public string Description { get => description; set => description = value; }

        public abstract void Execute();

        public override string ToString()
        {
            return "id=" + ID + "|description=" + Description;
        }
    }
}
