using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace Concurs.model
{
    public class User:HasId<string>
    {
        private string username;
        private string hash;
        private string tip;

        public User(string username, string hash, string tip)
        {
            this.username = username;
            this.hash = hash;
            this.tip = tip;
        }

        [XmlAttribute]
        public string Id
        {
            get { return username; }
            set { username = value; }
        }

        public string Hash
        {
            get { return hash; }
            set { hash = value; }
        }

        public string Tip
        {
            get { return tip; }
            set { tip = value; }
        }

        public override bool Equals(object obj)
        {
            var user = obj as User;
            return user != null &&
                   username == user.username &&
                   hash == user.hash &&
                   tip == user.tip;
        }

        public override int GetHashCode()
        {
            var hashCode = -183497012;
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(username);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(hash);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(tip);
            return hashCode;
        }

        public override string ToString()
        {
            return username + " " + tip;
        }
    }
}
