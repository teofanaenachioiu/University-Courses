using Curs12.Model;
using Curs12.Model.Validator;
using Curs12.Repository;
using Curs12.Service;
using Curs12.Model;
using Curs12.OtherLINQEx;
using Curs12.Repository;
using Curs12.Service;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Curs12.Repository;
using Curs12.Model.Validator;
using System.Globalization;

namespace Curs12
{
    class A
    {
        public virtual void M()
        { Console.WriteLine("M din A"); }
    }
    class B : A
    {
        public new void M()
        { Console.WriteLine("new M din B"); }
    }


    class Program
    {
        public static void AngajatiOperations()
        {
            AngajatService service = GetAngajatService();
            service.FindAllAngajati().ForEach(x => Console.WriteLine(x));
            //service.SortByKnowledgeLevel().ForEach(x=>Console.WriteLine(x));

            //service.SortByKnowledgeLevel_Query().ForEach(x => Console.WriteLine(x));
            //service.FilterByKnowledgeLevel(KnowledgeLevel.Junior).ForEach(x => Console.WriteLine(x));
            //service.FilterByKnowledgeLevel_Query(KnowledgeLevel.Junior).ForEach(x => Console.WriteLine(x));

            //service.MediaVenitPeOraPeNivel().ForEach(x => Console.WriteLine(x));
            //service.MediaVenitPeOraPeNivel_Query().ForEach(x => Console.WriteLine(x));

            //sort a list using a Comparison - delegate

            //service.SortByKnowledgeLevel().ForEach(x => Console.WriteLine(x));

            //Console.WriteLine("Filter by Level");

            //service.FilterByKnowledgeLevel(KnowledgeLevel.Junior).ForEach(x => Console.WriteLine(x));

            //service
            //    .MediaVenitPeOraPeNivel()
            //    .ForEach(x => Console.WriteLine(x.Key+" "+x.Value));
            Console.WriteLine("--------------------------------");

        }

        public static void SarciniOperations()
        {
            SarcinaService serSarcina = GetSarcinaService();
            serSarcina.FindAllSarcini().ForEach(x => Console.WriteLine(x));
            Console.WriteLine("--------------------------------");
        }

        public static void PontajeOperations()
        {
            PontajService serPontaj = GetPontajService();
            serPontaj.FindAllPontaje().ForEach(x => Console.WriteLine(x));

            //foreach (var x in serPontaj.ValoareOreLucrate())
            //{
            //    Console.WriteLine($"{x.Key.Nume} {x.Value}");
            //}
            Console.WriteLine("--------------------------------");
            Console.WriteLine("Salarul luna 3");
            (from a in serPontaj.Salar(3)
             orderby a.Nivel, a.Salar descending
             select a).ToList().ForEach(x => Console.WriteLine(x));
            Console.WriteLine("--------------------------------");
        }

        private static PontajService GetPontajService()
        {
            string fileName = ConfigurationManager.AppSettings["pontajeFileName"];
            IValidator<Pontaj> vali = new PontajValidator();
            IRepository<string, Pontaj> repo = new PontajInFileRepository(vali, fileName);

            PontajService service = new PontajService(repo);
            return service;
        }

        static void Main(string[] args)
        {
            var a = new B();
            a.M();

            int[] numbers = { 1, 2, 3, 5, 2, 1, 2, 3, 6, 2, 2, 4, 1, 2, 1, 4, 6, 2, 4, 1, 2, 5, 7 };

            String text = "ACESTA Este UN Text MARE";

            string[] words = { "one", "two", "three" };

            //var rez = from w in text.Split(' ')
            //          where w.ToUpper().Equals(w)
            //          select w;

            //rez.ToList().ForEach(x => Console.WriteLine(x));

            (from w in text.Split(' ')
                      where w.ToUpper().Equals(w)
                      select w).ToList().ForEach(x => Console.WriteLine(x));

            text.Split(' ')
                .ToList()
                .Where(x => x.ToUpper().Equals(x))
                .ToList()
                .ForEach(x => Console.WriteLine(x));

            var result = from number in numbers
                         group number by number into g
                         select new
                         {
                             Valoare = g.Key,
                             Frecventa = g.Count()
                         };
            result.ToList().ForEach(x => Console.WriteLine($"Valoare {x.Valoare} :  Frecventa {x.Frecventa}"));

            Console.WriteLine("fara linq");

            numbers
                .ToList()
                .GroupBy(x => x)
                .ToList()
                .Select(gr =>
                {
                    return new
                    {
                        Valoare = gr.Key,
                        Frecventa = gr.Count()
                    };
                }).ToList()
                .ForEach(x => Console.WriteLine(
                    $"Valoare {x.Valoare} :  Frecventa {x.Frecventa}"));

            //AngajatiOperations();
            //PontajeOperations();
            //SarciniOperations();


            //DateTime dt = DateTime.Now;
            //Console.WriteLine(dt);

            //string s = dt.ToString("dd/M/yyyy", System.Globalization.CultureInfo.InvariantCulture);
            //Console.WriteLine(s);
        }

        private static SarcinaService GetSarcinaService()
        {
            string fileName = ConfigurationManager.AppSettings["sarciniFileName"];
            IValidator<Sarcina> vali = new SarcinaValidator();

            //IRepository<string, Sarcina> repo = new InFileRepository<string, Sarcina>(vali, fileName,
            //    line =>
            //    {
            //        string[] fields = line.Split(','); // new char[] { ',' } 
            //        Sarcina sarcina = new Sarcina()
            //        {

            //            ID = fields[0],
            //            TipDificultate = (Dificultate)Enum.Parse(typeof(Dificultate), fields[1]),
            //            NrOreEstimate = Int32.Parse(fields[2])
            //        };
            //        return sarcina;
            //    });
            IRepository<string, Sarcina> repo = new SarcinaInFileRepository(vali, fileName);

            SarcinaService service = new SarcinaService(repo);
            return service;
        }

        private static AngajatService GetAngajatService()
        {
            string fileName = ConfigurationManager.AppSettings["angajatiFileName"];
            IValidator<Angajat> vali = new AngajatValidator();

            //IRepository<string, Angajat> repo1 = new InFileRepository<string, Angajat>(vali, fileName,
            //    line =>
            //    {
            //        string[] fields = line.Split(','); // new char[] { ',' } 
            //        Angajat angajat = new Angajat()
            //        {

            //            ID = fields[0],
            //            Nume = fields[1],
            //            VenitPeOra = Double.Parse(fields[2]),
            //            Nivel = (KnowledgeLevel)Enum.Parse(typeof(KnowledgeLevel), fields[3])
            //        };
            //        return angajat;
            //    });

            IRepository<string, Angajat> repo1 = new AngajatInFileRepository(vali, fileName);
            AngajatService service = new AngajatService(repo1);
            return service;
        }

        //public static IList<MessageTask> CreateMessageTask()
        //{
        //    MessageTask m1 = new MessageTask() { ID = "1", Description = "d1", From = "f1", To = "t1", Message = "m1", Date = DateTime.Now };
        //    MessageTask m2 = new MessageTask() { ID = "2", Description = "d2", From = "f1", To = "t1", Message = "m1", Date = DateTime.Now };
        //    MessageTask m3 = new MessageTask() { ID = "3", Description = "d1", From = "f1", To = "t1", Message = "m1", Date = DateTime.Now };
        //    MessageTask m4 = new MessageTask() { ID = "4", Description = "d1", From = "f1", To = "t1", Message = "m1", Date = DateTime.Now };
        //    IList<MessageTask> msj = new List<MessageTask>();
        //    msj.Add(m1);
        //    msj.Add(m2);
        //    msj.Add(m3);
        //    msj.Add(m4);
        //    return msj;
        //}
        //private static List<MessageTask> GetMessages()
        //{
        //string fileName = ConfigurationManager.AppSettings["messagesFileName"];

        //IList<MessageTask> list = CreateMessageTask();

        //IValidator<MessageTask> vali = new MessageTaskValidator();
        //IRepository<string, MessageTask> memoryRepo = new InMemoryRepository<string, MessageTask>(vali);

        //IRepository<string, MessageTask> repo = new InFileRepository<string, MessageTask>(vali, fileName,
        //    line =>
        //    {
        //        string[] fields = line.Split(','); // new char[] { ',' } 
        //        MessageTask messageTask = new MessageTask()
        //        {
        //            ID = fields[0],
        //            Message = fields[1],
        //            To = fields[2],
        //            From = fields[3],
        //            Description = fields[4],
        //            Date = DateTime.Parse(fields[5])
        //        };
        //        return messageTask;
        //    });

        //MessageTaskService service = new MessageTaskService(repo);

        ////list.ToList().ForEach(messageTask => service.SaveMessage(messageTask));

        //// list.ToList().ForEach(messageTask => Console.WriteLine(messageTask));
        ////WriteToFile(service.FindAllMessages(), fileName);

        ////   ReadFromFile(fileName).ToList().ForEach(msg => Console.WriteLine(msg));

        ////repo.FindAll().ToList().ForEach(msg => Console.WriteLine(msg));

        ////WriteToFile(list, fileName);
        //return service.FindAllMessages().ToList();
    }

    //private static IList<MessageTask> ReadFromFile(string fileName)
    //{
    //    IList<MessageTask> list = new List<MessageTask>();
    //    using (StreamReader sr = new StreamReader(fileName))
    //    {
    //        string s;
    //        while ((s = sr.ReadLine()) != null)
    //        {
    //            string[] fields = s.Split(','); // new char[] { ',' } 
    //            MessageTask messageTask = new MessageTask()
    //            {
    //                ID = fields[0],
    //                Message = fields[1],
    //                To = fields[2],
    //                From = fields[3],
    //                Description = fields[4],
    //                Date = DateTime.Parse(fields[5])
    //            };
    //            list.Add(messageTask);
    //        }
    //    }

    //    return list;
    //}

    //private static void WriteToFile(IList<MessageTask> list, string fileName)
    //{
    //    using (StreamWriter sw = new StreamWriter(fileName))
    //    {
    //        list.ToList().ForEach(messageTask =>
    //        {
    //            string msg = messageTask.ID + "," + messageTask.Message + ","
    //                + messageTask.To + "," + messageTask.From + "," + messageTask.Description
    //                + "," + messageTask.Date;

    //            sw.WriteLine(msg);
    //        });
    //    }
    //}


    //    }


}
