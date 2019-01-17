using CatalogMAP.domain;
using CatalogMAP.validator;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CatalogMAP.repository
{
    class InMemoryRepository<ID, E> : IRepository<ID, E> where E : IHasID<ID>
    {
        protected IDictionary<ID, E> entities = new Dictionary<ID, E>();
        private IValidator<E> validator;


        public InMemoryRepository(IValidator<E> vali)
        {
            this.validator = vali;
        }
        public E Delete(ID id)
        {
            if (id == null)
                throw new ArgumentNullException("Parametrul este null");
            if (!entities.ContainsKey(id))
                return default(E);
            else
            {
                E temp = entities[id];
                entities.Remove(id);
                return temp;
            }
        }

        public IEnumerable<E> FindAll()
        {
            return entities.Values.ToList();
        }

        public E FindOne(ID id)
        {
            if (id == null)
                throw new ArgumentNullException("Parametrul este null");
            if (!entities.ContainsKey(id))
                return default(E);
            else return entities[id];
        }

        public E Save(E entity)
        {
            if (entity == null)
                throw new ArgumentNullException("Parametrul este null");
            validator.Validate(entity);
            if (entities.ContainsKey(entity.ID))
                return entity;
            entities[entity.ID] = entity;
            return default(E);
        }

        public E Update(E entity)
        {
            if (entity == null)
                throw new ArgumentNullException("Parametrul este null");
            validator.Validate(entity);
            if (!entities.ContainsKey(entity.ID))
                return entity;
            entities[entity.ID] = entity;
            return default(E);
        }
    }
}
