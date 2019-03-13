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
    public class InscrieriRepositoryTest
    {
        [TestMethod]
        public void TestMethod1()
        {
            XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));

            IDictionary<string, string> props = new SortedList<String, String>();

            props.Add("ConnectionString", Concurs.repository.DBUtils.GetConnectionStringByName("catalogTest"));

            InscrieriRepository repo = new InscrieriRepository(props);
            Inscriere inscriere = new Inscriere(1, 10, "maria_avram");

            //size
            Assert.AreEqual(0, repo.Size());

            //save
            repo.Save(inscriere);
            Assert.AreEqual(1, repo.Size());
            try
            {
                repo.Save(inscriere);
                Assert.Fail();
            }
            catch (System.Data.SQLite.SQLiteException e)
            {
                Assert.IsTrue(true);
            }
            catch (RepositoryException e)
            {
                Assert.AreEqual(e.Message, "Error: Nu s-a putut adauga inscrierea!");
                Assert.Fail();
            }
            //findone
            Assert.AreEqual(inscriere, repo.FindOne(new KeyValuePair<int, int>(1, 10)));

            //update
            repo.Update(new KeyValuePair<int, int>(1, 10), new Inscriere(1, 10, "ioana_avram"));

            List<Inscriere> lista = (List<Inscriere>)repo.FindAll();

            inscriere.UsernameOperator="ioana_avram";
            Assert.AreEqual(inscriere, lista[lista.Count - 1]);

            try
            {
                repo.Update(new KeyValuePair<int,int>(0,0),inscriere);
                Assert.Fail();
            }
            catch (RepositoryException e)
            {
                Assert.AreEqual(e.Message, "Error: Nu s-a putut actualiza inscrierea!");
            }

            repo.Delete(inscriere.Id);

            //nr Probe/Participant
            repo.Save(new Inscriere(1, 10, "maria_avram"));
            repo.Save(new Inscriere(1, 11, "maria_avram"));


    
            //max 2 probe

            try
            {
                repo.Save(new Inscriere(1, 12, "maria_avram"));
                Assert.AreEqual(2, repo.Size());
            }
            catch (RepositoryException e)
            {
                Assert.AreEqual("Participantul e deja inscris la doua probe", e.Message);
                Assert.AreEqual(2, repo.Size());
                repo.Delete(new KeyValuePair<int, int>(1, 10));
                repo.Delete(new KeyValuePair<int, int>(1, 11));
            }
        }
    
    }
}
