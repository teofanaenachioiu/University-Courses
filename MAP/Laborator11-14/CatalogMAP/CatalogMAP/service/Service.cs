using CatalogMAP.domain;
using CatalogMAP.repository;
using CatalogMAP.validator;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CatalogMAP.service
{
    public class Service
    {
        private IRepository<string, Student> repoS;
        private IRepository<string, Tema> repoT;
        private IRepository< KeyValuePair<string,string>, Nota> repoN;

        public Service(IRepository<string, Student> repoS, 
            IRepository<string, Tema> repoT, 
            IRepository<KeyValuePair<string, string>, Nota> repoN)
        {
            this.repoS = repoS;
            this.repoT = repoT;
            this.repoN = repoN;
        }

        public bool AdaugaStudent(Student student)
        {
            if (repoS.Save(student) == default(Student))
                return true;
            else return false;
        }

        public bool StergeStudent(string id)
        {
            if (repoS.Delete(id) == default(Student))
                return false;
            else return true;
        }

        public bool ActualizeazaStudent(Student student)
        {
            if (repoS.Update(student) == default(Student))
                return true;
            else return false;
        }

        public Student CautaStudent(string id)
        {
            return repoS.FindOne(id);
        }

        public IEnumerable<Student> ListaStudenti()
        {
            return repoS.FindAll();
        }

        public bool AdaugaTema(Tema tema)
        {
            if (repoT.Save(tema) == default(Tema))
                return true;
            return false;
        }

        public Tema CautaTema(string id)
        {
            return repoT.FindOne(id);
        }

        public IEnumerable<Tema> ListaTeme()
        {
            return repoT.FindAll();
        }

        public static int GetWeekNumber(DateTime now)
        {
            CultureInfo ci = CultureInfo.CurrentCulture;
            int weekNumber = ci.Calendar.GetWeekOfYear(now, CalendarWeekRule.FirstFourDayWeek, DayOfWeek.Monday);
            return weekNumber;
        }

        public int GetLabNumber()
        {
            int currentWeek = GetWeekNumber(DateTime.Now);
            int dif = currentWeek - 39;
            if (dif < 1 || dif > 16) return default(int);
            if (currentWeek.Equals(13) || currentWeek.Equals(14))
                return 12;
            if (currentWeek.Equals(15)) return dif - 1;
            if (currentWeek.Equals(16)) return dif - 2;
            return dif;
        }

        public bool PrelungireDeadline(string id, string data)
        {
            Tema t = repoT.FindOne(id);

            //Daca nu modific nimic
            if (t.Deadline == data)
                return false;

            //Nu pot sa modific deadlineul daca el deja a fost depasit
            if (Int32.Parse(t.Deadline) < GetLabNumber())
                return false;

            if (t != default(Tema))
            { 
                //In caz ca dau un deadline mai mic fata de data de predare
                try
                {
                    t.Deadline = data;
                    repoT.Update(t);
                }
                catch(ValidationException)
                {
                    return false;
                }
            }
            
            return true;
        }

        public float CalculeazaNota(String data, String notaProf, Tema tema)
        {
            float nota = float.Parse(notaProf);
            int dif = Int32.Parse(data) - Int32.Parse(tema.Deadline);
            if (dif > 0 && dif <= 2)
            {
                return nota - dif * 2.5f;
            }
            else if (dif <= 0)
            {
                return (float)nota;
            }
            else
            {
                return 1f;
            }
        }

        public bool AdaugaNota(Nota entity,bool motivat)
        {
            if (repoS.FindOne(entity.StudentID) == default(Student))
                return false;
            if (repoT.FindOne(entity.TemaID) == default(Tema))
                return false;
            if (repoN.FindOne(new KeyValuePair<string,string>(entity.StudentID,entity.TemaID)) 
                != default(Nota))
                return false;
            if (!motivat)
            {
                float nota = CalculeazaNota(entity.DataCurenta, entity.NotaProf, repoT.FindOne(entity.TemaID));
                entity.NotaProf = nota.ToString();
            }
            if (repoN.Save(entity) == default(Nota))
                return true;
            else return false;
        }

        /**
         * Media fiecarui student la laborator
         */
        /*public IDictionary<Student, float> MedieLaborator()
        {

            var rez = (from n in repoN.FindAll().Distinct()
                       group n.Student by n.Student.ID into gr
                       select gr.First()).Distinct().ToList();

            IDictionary<Student, float> dic = new Dictionary<Student, float>();
            rez.ForEach(stu =>
            {
                var media = _repoNota.FindAll().ToList()
                    .Where(n => n.Student.ID.Equals(stu.ID))
                    .Sum(no => no.NotaValue);
                dic.Add(stu, media / 13);

            });
            return dic;
        }*/

        private List<E> FilterBy<E>(Predicate<E> predicate,List<E> lista) where E : IHasID<string>
        {       
            return lista.Where((x, y) => { return predicate(x); }).ToList();
        }

        public List<Student> FilterByGrupa(string level)
        {
            Predicate<Student> pred = x => x.Grupa.Equals(level);
            List<Student> lista = repoS.FindAll().ToList();
            return FilterBy(pred,lista);
        }

        public List<Tema> FilterByInterval(String start, String end)
        {
            Predicate<Tema> pred = x => int.Parse(x.Deadline) >= int.Parse(start) && int.Parse(x.Deadline) <= int.Parse(end);
            List<Tema> lista = repoT.FindAll().ToList();
            return FilterBy(pred, lista);
        }

        private double MedieStd(string idSudent)
        {
            double suma = 0;
            foreach(Nota n in repoN.FindAll().ToList())
            {
                if (n.StudentID.Equals(idSudent))
                {
                    Tema tema = repoT.FindOne(n.TemaID);
                    int pond = int.Parse(tema.Deadline) - int.Parse(tema.DataPredare);
                    suma = suma + double.Parse(n.NotaProf) * pond;
                }
            }
            int pondere = 0;
            foreach(Tema tema in repoT.FindAll())
            {
                pondere= pondere +int.Parse(tema.Deadline) - int.Parse(tema.DataPredare);
            }

            return Math.Truncate(suma / pondere * 100) / 100; 
        }

        public List<KeyValuePair<Student, double>> MediaStudentilor()
        {
            // IQueryable methods extensions
            List<Student> students = repoS.FindAll().ToList();
            var rez = from a in students
                      select new KeyValuePair<Student, double>(a, MedieStd(a.ID));
            return rez.ToList();
        }

        public List<KeyValuePair<Student, double>> ListaExamen()
        {
            List<KeyValuePair<Student, double>> lista = MediaStudentilor();
            var rez=from a in lista
                    where a.Value>=4
                    select new KeyValuePair<Student, double>(a.Key,a.Value);
            return rez.ToList();
        }

        public List<Student> ListaStudentiTemeLaTimp()
        {
            List<Nota> lista = repoN.FindAll().ToList();
            var rez = from n in lista
                      where int.Parse(n.DataCurenta) <= int.Parse(repoT.FindOne(n.TemaID).Deadline)
                      select n;

            List<Student> students = new List<Student>();
            foreach(Student s in repoS.FindAll())
            {
                int nr = 0;
                rez.ToList().ForEach(nota => { if (nota.StudentID.Equals(s.ID))
                        nr++;
            
                        });

                if (nr == repoT.FindAll().Count())
                {
                    students.Add(s);
                }
            }
            return students;
        }
    }
}
