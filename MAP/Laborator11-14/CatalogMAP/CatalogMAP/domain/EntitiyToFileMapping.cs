using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CatalogMAP.domain
{
    class EntitiyToFileMapping
    {
        public static Student CreateStudent(string line)
        {
            string[] fields = line.Split('/'); // new char[] { ',' } 
            Student student = new Student()
            {

                ID = fields[0],
                Nume = fields[1],
                Grupa = fields[2],
                Email = fields[3],
                IndrumatorLab = fields[4]
            };
            return student;
        }



        public static Tema CreateTema(string line)
        {
            string[] fields = line.Split('/'); // new char[] { ',' } 
            Tema tema = new Tema()
            {

                ID = fields[0],
                Descriere = fields[1],
                Deadline = fields[2],
                DataPredare = fields[3]
            };
            return tema;
        }

        public static Nota CreateNota(string line)
        {
            string[] fields = line.Split('/'); // new char[] { ',' } 
            Nota nota = new Nota()
            {

                StudentID = fields[0],
                TemaID = fields[1],
                NotaProf = fields[2],
                DataCurenta = fields[3],
                ID= new KeyValuePair<string, string>(fields[0], fields[1])
        };
            return nota;
        }
    }
}
