using Microsoft.VisualStudio.TestTools.UnitTesting;
using CatalogMAP.service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CatalogMAP.validator;
using CatalogMAP.domain;
using CatalogMAP.repository;

namespace CatalogMAP.service.Tests
{
    [TestClass()]
    public class ServiceTests
    {
        /*static IValidator<Student> validatorS = new ValidatorStudent();
        static IValidator<Tema> validatorT = new ValidatorTema();
        static IValidator<Nota> validatorN = new ValidatorNota();

        static IRepository<string, Student> repoS
            = new InMemoryRepository<string, Student>(validatorS);
        static IRepository<string, Tema> repoT
            = new InMemoryRepository<string, Tema>(validatorT);
        static IRepository<KeyValuePair<string, string>, Nota> repoN
            = new InMemoryRepository<KeyValuePair<string, string>, Nota>(validatorN);

        Service service = new Service(repoS, repoT, repoN);

        [TestMethod()]
        public void ServiceTest()
        {
            //Assert.Fail();
        }

        [TestMethod()]
        public void AdaugaStudentTest()
        {
            //Assert.Fail();
        }

        [TestMethod()]
        public void StergeStudentTest()
        {
            //Assert.Fail();
        }

        [TestMethod()]
        public void ActualizeazaStudentTest()
        {
            //Assert.Fail();
        }

        [TestMethod()]
        public void CautaStudentTest()
        {
            //Assert.Fail();
        }

        [TestMethod()]
        public void ListaStudentiTest()
        {
            //Assert.Fail();
        }

        [TestMethod()]
        public void AdaugaTemaTest()
        {
            //Assert.Fail();
        }

        [TestMethod()]
        public void CautaTemaTest()
        {
            //Assert.Fail();
        }

        [TestMethod()]
        public void ListaTemeTest()
        {
            //Assert.Fail();
        }

        [TestMethod()]
        public void GetWeekNumberTest()
        {
            //Assert.Fail();
        }

        [TestMethod()]
        public void GetLabNumberTest()
        {
            //Assert.Fail();
        }

        [TestMethod()]
        public void PrelungireDeadlineTest()
        {
            //Assert.Fail();
        }

        [TestMethod()]
        public void CalculeazaNotaTest()
        {
            //Assert.Fail();
        }

        [TestMethod()]
        public void AdaugaNotaTest()
        {
            Student student = new Student()
            {
                ID = "1001",
                Nume = "Teofana",
                Grupa = "223",
                Email = "teo@yahoo.com",
                IndrumatorLab = "Guran A"
            };
            repoS.Save(student);

            Tema tema1 = new Tema()
            {
                ID = "1",
                Descriere = "Prima tema",
                Deadline = "10",
                DataPredare = "9"
            };
            Tema tema2 = new Tema()
            {
                ID = "2",
                Descriere = "A doua tema",
                Deadline = "14",
                DataPredare = "10"
            };
            repoT.Save(tema1);
            repoT.Save(tema2);

            Nota nota1 = new Nota()
            {
                StudentID = "1001",
                TemaID = "1",
                NotaProf = "10",
                DataCurenta = "11"
            };

            Assert.IsTrue(service.AdaugaNota(nota1, true));
            Assert.AreEqual("10", repoN.FindAll().ElementAt(0).NotaProf);
            Assert.IsFalse(service.AdaugaNota(nota1, false));

            Nota nota2 = new Nota()
            {
                StudentID = "1001",
                TemaID = "2",
                NotaProf = "9",
                DataCurenta = "12"
            };

            Assert.IsTrue(service.AdaugaNota(nota2, false));
            Assert.AreEqual("9", repoN.FindAll().ElementAt(1).NotaProf);

            Student student1 = new Student()
            {
                ID = "1002",
                Nume = "Anda",
                Grupa = "223",
                Email = "anda@yahoo.com",
                IndrumatorLab = "Guran A"
            };
            repoS.Save(student1);

            Nota nota3 = new Nota()
            {
                StudentID = "1002",
                TemaID = "1",
                NotaProf = "10",
                DataCurenta = "12"
            };

            Assert.IsTrue(service.AdaugaNota(nota3, false));
            Assert.AreEqual("5", repoN.FindAll().ElementAt(2).NotaProf);
        }*/
    }
}