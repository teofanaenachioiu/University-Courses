using System;
using System.Collections.Generic;
using Concurs.model;
using Concurs.repository.utils;
using log4net.Config;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace UnitTest
{
    [TestClass]
    public class ParticipantRepositoryTest
    {
        [TestMethod]
        public void TestMethod()
        {
            XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));

            IDictionary<String, string> props = new SortedList<String, String>();

            props.Add("ConnectionString", Concurs.repository.DBUtils.GetConnectionStringByName("catalogTest"));

            ParticipantRepository repo = new ParticipantRepository(props);

            Participant participant = new Participant("Teodora", 11);
            Participant lastParticipant;

            //size

            Assert.AreEqual(repo.Size(),5);

            //save
            repo.Save(participant);
            Assert.AreEqual(6, repo.Size());
            List<Participant> lista =(List<Participant>) repo.FindAll();
            lastParticipant = lista[lista.Count - 1];
            participant.Id=lastParticipant.Id;

            //findone
            Assert.AreEqual(participant, repo.FindOne(lastParticipant.Id));
            Assert.IsNull(repo.FindOne(-1));

            //update
            repo.Update(lastParticipant.Id, new Participant("Teodora", 6));

            lista = (List<Participant>)repo.FindAll();
            lastParticipant = lista[lista.Count - 1];

            participant.Varsta=6;
            Assert.AreEqual(participant, lastParticipant);

            try
            {
                repo.Update(-1, participant);
                Assert.Fail();
            }
            catch (RepositoryException e)
            {
                Assert.AreEqual(e.Message, "Error: Nu s-a putut actualiza participantul!");
            }

            //delete
            repo.Delete(lastParticipant.Id);
            Assert.AreEqual(repo.Size(), 5);

            try
            {
                repo.Delete(lastParticipant.Id);
                Assert.Fail();
            }
            catch (RepositoryException e)
            {
                Assert.AreEqual(e.Message, "Error: Nu s-a putut sterge participantul!");
            }
        }
    }
}
