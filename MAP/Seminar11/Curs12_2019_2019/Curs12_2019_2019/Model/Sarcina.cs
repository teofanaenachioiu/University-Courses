using Curs12.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Curs12.Model
{
    enum Dificultate { Usoara, Medie, Grea }
    class Sarcina : IHadID<string>
    {
        public String ID { get; set; }
        public Dificultate TipDificultate { get; set; }
        public int NrOreEstimate { get; set; }
        public override string ToString()
        {
            return ID + " " + TipDificultate + " " + NrOreEstimate;
        }
    }

}
