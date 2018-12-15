using Microsoft.VisualStudio.TestTools.UnitTesting;
using CatalogMAP.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CatalogMAP.domain.Tests
{
    [TestClass()]
    public class StudentTests
    {
        Student student = new Student()
        {
            ID = "101",
            Nume = "Teofana",
            Grupa = "223",
            Email = "teo@yahoo.com",
            IndrumatorLab = "Guran A"
        };

        [TestMethod()]
        public void StudentTest()
        {
            Student student = new Student()
            {
                ID = "101",
                Nume = "Teofana",
                Grupa = "223",
                Email = "teo@yahoo.com",
                IndrumatorLab = "Guran A"
            };

            Assert.AreEqual("101", student.ID);
            student.ID = "100";
            Assert.AreEqual("100", student.ID);
           
        }

        [TestMethod()]
        public void EqualsTest()
        {
            Student studentNou = new Student()
            {
                ID = "101",
                Nume = "Teofana",
                Grupa = "223",
                Email = "teo@yahoo.com",
                IndrumatorLab = "Guran A"
            };
            Assert.IsTrue(student.Equals(studentNou));
            studentNou.ID="100";
            Assert.IsFalse(student.Equals(studentNou));

            
        }

        [TestMethod()]
        public void GetHashCodeTest()
        {
            Student studentNou = new Student()
            {
                ID = "101",
                Nume = "Teofana",
                Grupa = "223",
                Email = "teo@yahoo.com",
                IndrumatorLab = "Guran A"
            };
            Assert.AreEqual(student.GetHashCode(), studentNou.GetHashCode());
            studentNou.ID="100";
            Assert.AreNotEqual(student.GetHashCode(), studentNou.GetHashCode());
        }

        [TestMethod()]
        public void GetIDTest()
        {
            Assert.AreEqual("101", student.ID);
        }

        [TestMethod()]
        public void SetIDTest()
        {
            student.ID="100";
            Assert.AreEqual("100", student.ID);
        }

        [TestMethod()]
        public void ToStringTest()
        {
            Assert.AreEqual("101/Teofana/223/teo@yahoo.com/Guran A",student.ToString());
        }
    }
}