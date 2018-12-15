using CatalogMAP.domain;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace CatalogMAP.validator
{
    public class ValidatorNota : IValidator<Nota>
    {
        private void ValidateDate(string data)
        {
            Match mtch = Regex.Match(data, @"^[1-9]|1[0-4]$");
            if (!mtch.Success)
            {
                throw new ValidationException("Data incorecta!");
            }
        }

        private void ValidateNota(string nota)
        {
            try
            {
                float notaF=float.Parse(nota, CultureInfo.InvariantCulture);
                if (notaF < 1 || notaF > 10)
                    throw new ValidationException("Data incorecta!");
            }
            catch (FormatException)
            {
                throw new ValidationException("Nota incorecta!");
            }
        }

        public void Validate(Nota entity)
        {
            ValidateNota(entity.NotaProf);
            ValidateDate(entity.DataCurenta);
        }
    }
}
