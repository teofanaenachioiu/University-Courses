using Sem10_MAP_223.model;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Sem10_MAP_223.repository
{
    public delegate E StringToEntity<E>(string line);
    public delegate string EntityToString<E>(E entity);
    class InFileRepository<ID, E> : InMemoryRepository<ID, E> where E : IHasID<ID>
    {
        private string filename;
        public InFileRepository(IValidator<E> vali, string filename,
            StringToEntity<E> delegateCreate) : base(vali)
        {
            this.filename = filename;
            
            LoadFromFile(delegateCreate);
        }

        private void LoadFromFile(StringToEntity<E> stringToEntity)
        {
            using (StreamReader sr = new StreamReader(filename))
            {
                String line;
                while ((line = sr.ReadLine()) != null)
                {
                    //E entity = stringToEntity(line);
                    E entity = stringToEntity.Invoke(line);
                    base.Save(entity);
                }
            }
        }
    }
}
