using Sem10_MAP_223.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Sem10_MAP_223.repository
{
    class InMemoryRepository<ID, E> : IRepository<ID, E> where E:IHasID<ID>
    {

        private IDictionary<ID, E> entities = new Dictionary<ID, E>();
        private IValidator<E> validator;


        public InMemoryRepository(IValidator<E> vali)
        {
            this.validator = vali;
        }
        public E Delete(ID id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<E> FindAll()
        {
            return entities.Values.ToList();
        }

        public E FindOne(ID id)
        {
            throw new NotImplementedException();
        }

        public E Save(E entity)
        {
            if (entity == null)
                throw new ArgumentNullException("entity must not be null");
            validator.Validate(entity);
            if (entities.ContainsKey(entity.ID))
                return entity;
            entities[entity.ID] = entity;
            return default(E);
        }

        public E Update(E entity)
        {
            throw new NotImplementedException();
        }
    }
}
