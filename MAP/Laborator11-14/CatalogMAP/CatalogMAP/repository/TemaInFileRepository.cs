using CatalogMAP.domain;
using CatalogMAP.validator;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CatalogMAP.repository
{
    class TemaInFileRepository : InFileRepository<string, Tema>
    {
        public TemaInFileRepository(IValidator<Tema> vali, string fileName) : base(vali, fileName, EntitiyToFileMapping.CreateTema)
        {
            loadFromFile();
        }

    }
}
