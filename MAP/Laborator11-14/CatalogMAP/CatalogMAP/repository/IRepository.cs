using CatalogMAP.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CatalogMAP.repository
{
    public interface IRepository<ID, E> where E : IHasID<ID>
    {
      
        E FindOne(ID id);

        IEnumerable<E> FindAll();

        E Save(E entity);

        E Delete(ID id);


        E Update(E entity);


    }
}
