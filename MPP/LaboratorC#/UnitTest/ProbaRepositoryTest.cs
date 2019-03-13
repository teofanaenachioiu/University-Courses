using System;
using System.Collections.Generic;
using Concurs.model;
using Concurs.repository;
using Concurs.repository.utils;
using log4net.Config;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace UnitTest
{
    [TestClass]
    public class ProbaRepositoryTest
    {
        [TestMethod]
        public void TestMethod1()
        {
            XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));

            IDictionary<string, string> props = new SortedList<String, String>();

            props.Add("ConnectionString", Concurs.repository.DBUtils.GetConnectionStringByName("catalogTest"));

            ProbaRepository repo = new ProbaRepository(props);
            Proba proba = new Proba("tenis", "CATEGORIE_9_11");
            Proba lastProba;

            //size
            Assert.AreEqual(5, repo.Size());

            //save
            repo.Save(proba);
            Assert.AreEqual(6, repo.Size());
            List<Proba> lista = (List<Proba>)repo.FindAll();
            lastProba = lista[lista.Count - 1];
            proba.Id = lastProba.Id;

            //findone
            Assert.AreEqual(proba, repo.FindOne(lastProba.Id));
            

            //update
            repo.Update(lastProba.Id, new Proba("tenis", "CATEGORIE_12_15"));

            lista = (List<Proba>)repo.FindAll();
            lastProba = lista[lista.Count - 1];

            proba.Categorie="CATEGORIE_12_15";
            Assert.AreEqual(proba, lastProba);

            try
            {
                repo.Update(0, new Proba("dans", "CATEGORIE_12_15"));
                Assert.Fail();
            }
            catch (RepositoryException e)
            {
                Assert.AreEqual(e.Message, "Error: Nu s-a putut actualiza proba!");
            }

            //delete
            repo.Delete(lastProba.Id);
            Assert.AreEqual(5, repo.Size());
            try
            {
                repo.Delete(0);
                Assert.Fail();
            }
            catch(RepositoryException e)
            {
                Assert.AreEqual(e.Message, "Error: Proba nu s-a putut sterge!");
            }
        }
    }
}
