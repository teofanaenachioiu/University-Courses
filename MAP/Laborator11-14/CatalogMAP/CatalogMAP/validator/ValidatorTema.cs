using CatalogMAP.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace CatalogMAP.validator
{
    public class ValidatorTema : IValidator<Tema>
    {
        private void ValidateId(string id)
        {
            Match mtch = Regex.Match(id, @"^[1-9][0-9]*$");
            if (!mtch.Success)
            {
                throw new ValidationException("Id incorect!");
            }
        }

        private void ValidateDate(string dataS)
        {
            Match mtch = Regex.Match(dataS, @"^[1-9]|1[0-4]$");
            if (!mtch.Success)
            {
                throw new ValidationException("Data incorecta!");
            }
        }

        
        private void ValidateInterval(string predare, string deadline)
        {
            if (Int32.Parse(predare) > Int32.Parse(deadline))
                throw new ValidationException("Date incorecte!");
        }

        public void Validate(Tema entity)
        {
            ValidateId(entity.ID);
            ValidateDate(entity.Deadline);
            ValidateDate(entity.DataPredare);
            ValidateInterval(entity.DataPredare, entity.Deadline);
        }
    }
}
