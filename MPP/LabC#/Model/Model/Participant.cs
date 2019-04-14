using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace Concurs.model
{
    public class Participant:HasId<int>
    {
        private int id;
        private string nume;
        private int varsta;

        public Participant(string nume, int varsta)
        {          
            this.nume = nume;
            this.varsta = varsta;
        }

        public Participant(int id, string nume, int varsta)
        {
            this.id = id;
            this.nume = nume;
            this.varsta = varsta;
        }

        [XmlAttribute]
        public int Id
        {
            get { return id; }
            set { id = value; }
        }

        public string Nume
        {
            get { return nume; }
            set { nume = value; }
        }

        public int Varsta
        {
            get { return varsta; }
            set { varsta = value; }
        }
        

        public override bool Equals(object obj)
        {
            var participant = obj as Participant;
            return participant != null &&
                   id == participant.id &&
                   nume == participant.nume &&
                   varsta == participant.varsta;
        }

        public override int GetHashCode()
        {
            var hashCode = 990734938;
            hashCode = hashCode * -1521134295 + id.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(nume);
            hashCode = hashCode * -1521134295 + varsta.GetHashCode();
            return hashCode;
        }

        public override string ToString()
        {
            return id + " " + nume + " " + varsta;
        }
    }
}
