using System;
using CatalogMAP.domain;
using CatalogMAP.validator;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace CatalogMAP.validator.Tests
{
    [TestClass()]
    public class ValidatorStudentTests
    {
        [TestMethod()]
        public void ValidateTest()
        {
            Student student = new Student()
            {
                ID = "101a",
                Nume = "Teofana",
                Grupa = "223",
                Email = "teofana@yahoo.com",
                IndrumatorLab = "Guran A"
            };

            IValidator<Student> validator = new ValidatorStudent();
            try
            {
                validator.Validate(student);
                Assert.IsTrue(false);
            }
            catch (ValidationException)
            {
                Assert.IsTrue(true);
            }

            student.ID = "1011";
            try
            {
                validator.Validate(student);
                Assert.IsTrue(true);
            }
            catch (ValidationException)
            {
                Assert.IsTrue(false);
            }
        }
    }
}