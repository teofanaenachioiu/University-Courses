using Concurs.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace Model
{
    [Serializable]
    public class ProbaDTO : HasId<int>
    {
        private int id;
        private string denumire;
        private string categorie;
        private int nrParticipanti;


        public ProbaDTO(int id, string denumire, string categorie, int nrParticipanti)
        {
            this.id = id;
            this.denumire = denumire;
            this.categorie = categorie;
            this.nrParticipanti = nrParticipanti;
        }

        [XmlAttribute]
        public int Id
        {
            get { return id; }
            set { id = value; }
        }

        public string Denumire
        {
            get { return denumire; }
            set { denumire = value; }
        }

        public string Categorie
        {
            get { return categorie; }
            set { categorie = value; }
        }

        public int NrParticipanti
        {
            get { return nrParticipanti; }
            set { nrParticipanti = value; }
        }

        public override bool Equals(object obj)
        {
            var proba = obj as ProbaDTO;
            return proba != null &&
                   id == proba.id &&
                   denumire == proba.denumire &&
                   categorie == proba.categorie &&
                   nrParticipanti == proba.nrParticipanti;
        }

        public override int GetHashCode()
        {
            var hashCode = 234180136;
            hashCode = hashCode * -1521134295 + id.GetHashCode();
            hashCode = hashCode * -1521134295 + nrParticipanti.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(denumire);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(categorie);
            return hashCode;
        }

        public override string ToString()
        {
            return base.ToString();
        }
    }
}
