using Sem10_MAP_223.model;
using Sem10_MAP_223.repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Sem10_MAP_223.service
{
    class MessageTaskService
    {
        private IRepository<string, MessageTask> repo;

        public MessageTaskService(IRepository<string, MessageTask> repo)
        {
            this.repo = repo;
        }

        public MessageTask SaveMessage(MessageTask m)
        {
            return repo.Save(m);
        }

        public IList<MessageTask> FindAllMessages()
        {
            return repo.FindAll().ToList();
        }
    }
}
