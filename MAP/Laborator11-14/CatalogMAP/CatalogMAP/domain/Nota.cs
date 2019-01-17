using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CatalogMAP.domain
{
    public class Nota : IHasID<KeyValuePair<string, string>>
    {
        public string DataCurenta { get; set; }
        public string NotaProf { get; set; }
        public string TemaID { get; set; }
        public string StudentID { get; set; }
        public KeyValuePair<string, string> ID
        {
            get => new KeyValuePair<string, string>(StudentID, TemaID);
            set
            {
                StudentID = value.Key;
                TemaID = value.Value;
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
