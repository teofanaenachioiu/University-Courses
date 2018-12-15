using Sem10_MAP_223.model;
using Sem10_MAP_223.repository;
using Sem10_MAP_223.service;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Linq;
using System.Text;


namespace Sem10_MAP_223
{
    class Program
    {
        static void Main(string[] args)
        {
          
            IList<MessageTask> list= GetMessages();

            IValidator<MessageTask> vali = new MessageTaskValidator();
            string filename = ConfigurationManager.AppSettings["filename"];
            //IRepository<string, MessageTask> memoryRepo = new InMemoryRepository<string, MessageTask>(vali);
            IRepository<string, MessageTask> fileRepo = new InFileRepository<string, MessageTask>
                (vali, filename, line => {
                    String[] items = line.Split(',');
                    MessageTask message = new MessageTask()
                    {
                        ID = items[0],
                        Description = items[1],
                        Message = items[2],
                        From = items[3],
                        To = items[4],
                        Date = DateTime.Parse(items[5])
                    };
                    return message;
                });
            //IRepository<string, MessageTask> repo = new MessageFileRepository(fileName, vali);

            //MessageTaskService service = new MessageTaskService(memoryRepo);
            MessageTaskService service = new MessageTaskService(fileRepo);
            list.ToList().ForEach(message => service.SaveMessage(message));
            service.FindAllMessages().ToList().ForEach(x => Console.WriteLine(x));
            
            //WriteToFile(filename, list);
            //ReadFromFile(filename).ToList().ForEach(x => Console.WriteLine(x));
            
        }

        public static IList<MessageTask> ReadFromFile(string filename)
        {
            IList<MessageTask> messageTaskList = new List<MessageTask>();
            using (StreamReader sr = new StreamReader(filename))
            {
                String line;
                while ((line = sr.ReadLine()) != null){
                    String[] items = line.Split(',');
                    MessageTask message = new MessageTask()
                    {
                        ID = items[0],
                        Description = items[1],
                        Message = items[2],
                        From = items[3],
                        To = items[4],
                        Date = DateTime.Parse(items[5])
                    };
                    messageTaskList.Add(message);
                }
            }
            return messageTaskList;
        }

        public static void WriteToFile(string filename, IList<MessageTask> list)
        {
            using (StreamWriter sw = new StreamWriter(filename))
            {
                list.ToList().ForEach(message => sw.WriteLine(message.ID + ','
                    + message.Description + ',' + message.Message + ','
                    + message.From + ',' + message.To + ',' + message.Date));
            }
        }

        public static  IList<MessageTask> GetMessages()
        {
            //IList < MessageTask > messages = new List<MessageTask>();
            //messages.Add(new MessageTask()
            //{
            //    ID = "1",
            //    Description = "d1",
            //    From = "cineva1",
            //    To = "altcineva1",
            //    Message = "hey",
            //    Date = DateTime.Now
            //});

            //messages.Add(new MessageTask()
            //{
            //    ID = "2",
            //    Description = "d2",
            //    From = "altcineva1",
            //    To = "cineva1",
            //    Message = "hey you",
            //    Date = DateTime.Now
            //});
            //messages.Add(new MessageTask()
            //{
            //    ID = "3",
            //    Description = "d3",
            //    From = "cineva1",
            //    To = "altcineva1",
            //    Message = "sup' ?",
            //    Date = DateTime.Now
            //});
            //return messages;
            MessageTask m1 = new MessageTask() { ID = "1", Description = "d1", From = "f1", To = "t1", Message = "m1" };
            MessageTask m2 = new MessageTask() { ID = "2", Description = "d2", From = "f1", To = "t1", Message = "m1" };
            MessageTask m3 = new MessageTask() { ID = "3", Description = "d1", From = "f1", To = "t1", Message = "m1" };
            MessageTask m4 = new MessageTask() { ID = "4", Description = "d1", From = "f1", To = "t1", Message = "m1" };
            IList<MessageTask> msj = new List<MessageTask>();
            msj.Add(m1);
            msj.Add(m2);
            msj.Add(m3);
            msj.Add(m4);
            return msj;
        }
    }
}
