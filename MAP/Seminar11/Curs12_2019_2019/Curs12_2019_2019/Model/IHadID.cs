using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Curs12.Model
{
    interface IHadID<TID>
    {
        TID ID { get; set; }
    }
}
