using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CatalogMAP.domain
{
    public class Nota : IHasID<KeyValuePair<string, string>>
    {
        private String studentID;

        private String temaID;

        private String dataCurenta;

        private String notaProf;

        public string DataCurenta { get => dataCurenta; set => dataCurenta = value; }
        public string NotaProf { get => notaProf; set => notaProf = value; }
        public string TemaID { get => temaID; set => temaID = value; }
        public string StudentID { get => studentID; set => studentID = value; }
        public KeyValuePair<string, string> ID
        {
            get => new KeyValuePair<string, string>(studentID, temaID);
            set
            {
                studentID = value.Key;
                temaID = value.Value;
            }
        }

        public override bool Equals(object obj)
        {
            var nota = obj as Nota;
            return nota != null &&
                   DataCurenta == nota.DataCurenta &&
                   NotaProf == nota.NotaProf &&
                   TemaID == nota.TemaID &&
                   StudentID == nota.StudentID;
        }

        public override int GetHashCode()
        {
            var hashCode = 2028197421;
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(DataCurenta);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(NotaProf);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(TemaID);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(StudentID);
            return hashCode;
        }

        public override string ToString()
        {
            return StudentID+"/"+TemaID+"/"+DataCurenta+"/"+NotaProf;
        }
    }
}
