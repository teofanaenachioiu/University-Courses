using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CatalogMAP.domain
{
    public class Tema : IHasID<string>
    {
        private string idTema;
        private string descriere;
        private string deadline;
        private string dataPredare;

        public string ID { get => idTema; set => idTema = value; }
        public string Descriere { get => descriere; set => descriere = value; }
        public string Deadline { get => deadline; set => deadline = value; }
        public string DataPredare { get => dataPredare; set => dataPredare = value; }

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
