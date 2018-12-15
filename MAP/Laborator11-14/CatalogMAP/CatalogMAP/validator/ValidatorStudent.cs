using CatalogMAP.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace CatalogMAP.validator
{
    public class ValidatorStudent : IValidator<Student>
    {
        private void ValidareNume(string name)
        {
            Match match = Regex.Match(name, @"^[A-Za-z ,.'-]+$");
            if (!match.Success)
                throw new ValidationException("Nume incorect!");
        }

        private void ValidareID(string id)
        {
            Match match = Regex.Match(id, @"^[1-9][0-9]{1,}$");
            if (!match.Success)
                throw new ValidationException("ID incorect!");
        }

        private void ValidareGrupa(string grupa)
        {
            Match match = Regex.Match(grupa, @"^[1-9]{3}$");
            if (!match.Success)
                throw new ValidationException("Grupa incorecta!");
        }

        private void ValidareEmail(string email)
        {
            Match match = Regex.Match(email, @"^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$");
            if (!match.Success)
                throw new ValidationException("Email incorect!");
        }

        public void Validate(Student entity)
        {
            ValidareNume(entity.Nume);
            ValidareID(entity.ID);
            ValidareGrupa(entity.Grupa);
            ValidareEmail(entity.Email);
            ValidareNume(entity.IndrumatorLab);
        }
    }
}
