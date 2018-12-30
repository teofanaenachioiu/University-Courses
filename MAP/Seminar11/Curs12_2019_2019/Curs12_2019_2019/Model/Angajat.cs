using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Curs12.Model
{
    enum KnowledgeLevel
    {
        Junior=1, Medium, Senior
    }
    class Angajat : IHadID<string>
    {
        public String ID { get; set; }
        public String Nume { get; set; }
        public double VenitPeOra { get; set; }
        public KnowledgeLevel Nivel { get; set; }

        public override string ToString()
        {
            return ID+" "+Nume+" "+VenitPeOra+" "+Nivel;
        }
    }
}
