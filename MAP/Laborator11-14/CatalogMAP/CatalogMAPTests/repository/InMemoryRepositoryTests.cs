using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CatalogMAP.domain;
using CatalogMAP.validator;
using CatalogMAP.repository;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace CatalogMAP.repository.Tests
{
    [TestClass()]
    public class InMemoryRepositoryTests
    {
        //Repository de Teme
        /*private static IValidator<Tema> validator = new ValidatorTema();
        private IRepository<string, Tema> repo = new InMemoryRepository<string, Tema>(validator);

        [TestMethod()]
        public void DeleteTest()
        {
            Tema tema = new Tema()
            {
                ID = "101",
                Descriere = "lab frumos",
                Deadline = "13",
                DataPredare = "12"
            };

            repo.Save(tema);

            Assert.AreEqual(tema, repo.Delete("101"));
            Assert.AreEqual(default(Tema), repo.Delete("101"));
        }

        [TestMethod()]
        public void FindAllTest()
        {
            Tema tema = new Tema()
            {
                ID = "101",
                Descriere = "lab frumos",
                Deadline = "13",
                DataPredare = "12"
            };

            repo.Save(tema);
            Assert.AreEqual(1, repo.FindAll().Count());
        }

        [TestMethod()]
        public void FindOneTest()
        {
            Tema tema = new Tema()
            {
                ID = "101",
                Descriere = "lab frumos",
                Deadline = "13",
                DataPredare = "12"
            };

            repo.Save(tema);

            Assert.AreEqual(tema, repo.FindOne("101"));
            Assert.AreEqual(default(Tema), repo.FindOne("100"));
        }

        [TestMethod()]
        public void SaveTest()
        {
            Tema tema = new Tema()
            {
                ID = "101",
                Descriere = "lab frumos",
                Deadline = "13",
                DataPredare = "12"
            };

            Assert.AreEqual(default (Tema),repo.Save(tema));
            Assert.AreEqual(tema, repo.Save(tema));
        }

        [TestMethod()]
        public void UpdateTest()
        {
            Tema tema = new Tema()
            {
                ID = "101",
                Descriere = "lab frumos",
                Deadline = "13",
                DataPredare = "12"
            };

            repo.Save(tema);

            Tema tema1 = new Tema()
            {
                ID = "101",
                Descriere = "lab cat de cat",
                Deadline = "13",
                DataPredare = "12"
            };

            Assert.AreEqual(default(Tema), repo.Update(tema1));

            tema1.ID = "100";

            Assert.AreEqual(tema1, repo.Update(tema1));
        }*/
    }
}

