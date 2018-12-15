using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Sem10_MAP_223.repository
{
    interface  IRepository<ID,E>
    {
        /// <summary>
        /// 
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
            E FindOne(ID id);

            IEnumerable<E> FindAll();

            E Save(E entity);

            E Delete(ID id);

      
            E Update(E entity);


    }
}
