using Concurs.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Concurs.repository
{
    public interface IRepository<ID,E> where E: HasId<ID>
    {
        E FindOne(ID id);

        IEnumerable<E> FindAll();

        ID Save(E entity);

        void Delete(ID id);

        void Update(ID id, E entity);

        int Size();
    }
}
