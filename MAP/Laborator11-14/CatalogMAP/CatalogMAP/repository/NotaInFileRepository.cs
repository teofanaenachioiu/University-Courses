using CatalogMAP.domain;
using CatalogMAP.validator;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CatalogMAP.repository
{
    class NotaInFileRepository: InFileRepository<KeyValuePair<string, string>, Nota>
    {
        public NotaInFileRepository(IValidator<Nota> vali, string fileName) : base(vali, fileName, EntitiyToFileMapping.CreateNota)
        {
            loadFromFile();
        }
    }
}
