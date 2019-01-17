using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CatalogMAP.domain
{
    public class Tema : IHasID<string>
    {
        public string ID { get; set; }
        public string Descriere { get; set; }
        public string Deadline { get; set; }
        public string DataPredare { get; set; }

        public override bool Equals(object obj)
        {
            var tema = obj as Tema;
            return tema != null &&
                   ID == tema.ID &&
                   Descriere == tema.Descriere &&
                   Deadline == tema.Deadline &&
                   DataPredare == tema.DataPredare;
        }

        public override int GetHashCode()
        {
            var hashCode = -1071946961;
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(ID);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Descriere);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Deadline);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(DataPredare);
            return hashCode;
        }

        public override string ToString()
        {
            return ID+"/"+Descriere+"/"+Deadline+"/"+DataPredare;
        }
    }
}
