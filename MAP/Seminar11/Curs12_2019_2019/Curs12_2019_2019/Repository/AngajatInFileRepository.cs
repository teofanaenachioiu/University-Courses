using Curs12.Model;
using Curs12.Model.Validator;
using Curs12.Repository;
using Curs12.Model;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Curs12.Repository
{
    class AngajatInFileRepository : InFileRepository<string, Angajat>
    {

        public AngajatInFileRepository(IValidator<Angajat> vali, string fileName) : base(vali, fileName, EntityToFileMapping.CreateAngajat)           
        {
           
        }

    }
}
