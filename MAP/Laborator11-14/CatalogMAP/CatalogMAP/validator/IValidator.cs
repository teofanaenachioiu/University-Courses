using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CatalogMAP.validator
{
    public interface IValidator<E>
    {
        void Validate(E entity);
    }
}
