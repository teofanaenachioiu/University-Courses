using System;
using System.Collections.Generic;
using Concurs.model;
using log4net.Config;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace UnitTest
{
    [TestClass]
    public class UserRepositoryTest
    {
        [TestMethod]
        public void TestMethod1()
        {
            /*XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));

            IDictionary<string, string> props = new SortedList<String, String>();

            props.Add("ConnectionString", Concurs.repository.DBUtils.GetConnectionStringByName("catalogTest"));

            UserRepository repo = new UserRepository(props);

            User user = new User("ioana_l", "hashhash");

            //size
            Assert.AreEqual(3, repo.Size());

            //save
            repo.Save(user);
            Assert.AreEqual(4, repo.Size());

            //find
            Assert.IsNull(repo.FindOne("teofana"));
            Assert.AreEqual(user, repo.FindOne("ioana_l"));

            //update
            user.Tip="OPERATOR";
            repo.Update("ioana_l", new User("ioana_l", "hashhash", "OPERATOR"));

            List<User> lista = (List<User>)repo.FindAll();
            User lastUser = lista[lista.Count - 1];
            Assert.AreEqual(user, lastUser);

            try
            {
                repo.Update("ioa",user);
                Assert.Fail();
            }
            catch (RepositoryException e)
            {
                Assert.AreEqual(e.Message, "Error: Nu s-a putut actualiza userul!");
            }

            //delete
            repo.Delete("ioana_l");
            Assert.AreEqual(3, repo.Size());
            try
            {
                repo.Delete("ioana_l");
                Assert.Fail();
            }
            catch(RepositoryException e)
            {
                Assert.AreEqual(e.Message, "Error: Userul nu s-a putut sterge!");
            }*/

        }

    }
}

