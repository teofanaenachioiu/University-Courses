using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace Concurs.model
{
    public class Proba:HasId<int>
    {
        private int id;
        private string denumire;
        private string categorie;

        public Proba(string denumire, string categorie)
        {
            this.denumire = denumire;
            this.categorie = categorie;
        }

        public Proba(int id,string denumire, string categorie)
        {
            this.id = id;
            this.denumire = denumire;
            this.categorie = categorie;
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

        public override bool Equals(object obj)
        {
            var proba = obj as Proba;
            return proba != null &&
                   id == proba.id &&
                   denumire == proba.denumire &&
                   categorie == proba.categorie;
        }

        public override int GetHashCode()
        {
            var hashCode = 234180136;
            hashCode = hashCode * -1521134295 + id.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(denumire);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(categorie);
            return hashCode;
        }

        public override string ToString()
        {
            string[] nr = categorie.Split('_'); 
            return denumire + " (ctg. " + nr[1]+"-"+nr[2]+")";
        }
    }
}
