using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Curs12.Model;
namespace Curs12.Model.Validator
{
    class MessageTaskValidator : IValidator<MessageTask>
    {
        public void Validate(MessageTask e)
        {
            bool valid = true;
            if (valid == false)
            {
                throw new ValidationException("Obiectul nu e valid");
            }
        }
    }
}
