using CatalogMAP.consola;
using CatalogMAP.domain;
using CatalogMAP.repository;
using CatalogMAP.service;
using CatalogMAP.validator;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CatalogMAP
{
    class Program
    {
        static void Main(string[] args)
        {
            IValidator<Student> validatorS = new ValidatorStudent();
            IValidator<Tema> validatorT = new ValidatorTema();
            IValidator<Nota> validatorN = new ValidatorNota();

            string fileNameStudenti = "..\\..\\Data\\studenti.txt";
            string fileNameTeme = "..\\..\\Data\\teme.txt";
            string fileNameNote = "..\\..\\Data\\note.txt";
            IRepository<string, Student> repoS = new StudentInFileRepository(validatorS, fileNameStudenti);
            //   = new InMemoryRepository<string,Student>(validatorS);
            IRepository<string, Tema> repoT = new TemaInFileRepository(validatorT, fileNameTeme);
            //   = new InMemoryRepository<string,Tema>(validatorT);
            IRepository<KeyValuePair<string, string>, Nota> repoN = new NotaInFileRepository(validatorN, fileNameNote);
             //   = new InMemoryRepository<KeyValuePair<string, string>, Nota>(validatorN);

            Service service = new Service(repoS, repoT, repoN);
            Consola consola = new Consola(service);
            consola.Run();
        }
    }
}
