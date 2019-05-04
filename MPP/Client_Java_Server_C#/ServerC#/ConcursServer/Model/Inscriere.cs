using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace Concurs.model
{
    [Serializable]
    public class Inscriere
    {
        private int idParticipant;
        private int idProba;
        private DateTime data;
        private string usernameOperator;

        public Inscriere(int idParticipant, int idProba, string usernameOperator)
        {
            this.idParticipant = idParticipant;
            this.idProba = idProba;
            this.data = DateTime.Now.Date;
            this.usernameOperator = usernameOperator;
        }

        public Inscriere(int idParticipant, int idProba, DateTime data, string usernameOperator)
        {
            this.idParticipant = idParticipant;
            this.idProba = idProba;
            this.data = data;
            this.usernameOperator = usernameOperator;
        }


        [XmlAttribute]
        public KeyValuePair<int, int> Id
        {
            get { return new KeyValuePair<int,int>(idParticipant,idProba); }
            set { idParticipant = value.Key; idProba = value.Key; }
        }

        public DateTime Data
        {
            get { return data; }
            set { data = value; }
        }

        public string UsernameOperator
        {
            get { return usernameOperator; }
            set { usernameOperator = value; }
        }

        public override bool Equals(object obj)
        {
            var inscriere = obj as Inscriere;
            return inscriere != null &&
                   idParticipant == inscriere.idParticipant &&
                   idProba == inscriere.idProba &&
                   data == inscriere.data &&
                   usernameOperator == inscriere.usernameOperator;
        }

        public override int GetHashCode()
        {
            var hashCode = -893746367;
            hashCode = hashCode * -1521134295 + idParticipant.GetHashCode();
            hashCode = hashCode * -1521134295 + idProba.GetHashCode();
            hashCode = hashCode * -1521134295 + data.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(usernameOperator);
            return hashCode;
        }

        public override string ToString()
        {
            return idParticipant+ " " + idProba + " " + data + " " + usernameOperator;
        }
    }
}
