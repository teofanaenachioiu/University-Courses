using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Sem10_MAP_223.model
{
    interface IValidator<E> 
    {
        void Validate(E entity);
    }
}
