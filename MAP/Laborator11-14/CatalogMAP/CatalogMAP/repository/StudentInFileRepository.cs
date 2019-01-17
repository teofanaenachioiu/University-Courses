

using CatalogMAP.domain;
using CatalogMAP.validator;
using System.Collections.Generic;
using System.IO;

namespace CatalogMAP.repository
{
    class StudentInFileRepository:InFileRepository<string, Student>
    {
        public StudentInFileRepository(IValidator<Student> vali, string fileName) : base(vali, fileName, EntitiyToFileMapping.CreateStudent)
            {
            loadFromFile();
        }

    }
}
