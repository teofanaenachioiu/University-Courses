using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CatalogMAP.domain
{
    public class Student : IHasID<string>
    {
        private string idStudent;

        private string nume;

        private string grupa;

        private string email;

        private string indrumatorLab;

        public Student() {}
        public string ID { get => idStudent; set => idStudent = value; }
        public string Nume { get => nume; set => nume = value; }
        public string Grupa { get => grupa; set => grupa = value; }
        public string Email { get => email; set => email = value; }
        public string IndrumatorLab { get => indrumatorLab; set => indrumatorLab = value; }
     

        public override bool Equals(object obj)
        {
            var student = obj as Student;
            return student != null &&
                   ID == student.ID &&
                   Nume == student.Nume &&
                   Grupa == student.Grupa &&
                   Email == student.Email &&
                   IndrumatorLab == student.IndrumatorLab;
        }

        public override int GetHashCode()
        {
            var hashCode = 1235948908;
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(ID);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Nume);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Grupa);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Email);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(IndrumatorLab);
            return hashCode;
        }

        public override string ToString()
        {
            return ID+"/"+Nume+"/"+Grupa+"/"+Email+"/"+IndrumatorLab;
        }
    }
}
