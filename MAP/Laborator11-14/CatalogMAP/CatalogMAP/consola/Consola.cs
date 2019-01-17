using CatalogMAP.domain;
using CatalogMAP.service;
using CatalogMAP.validator;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CatalogMAP.consola
{
    class Consola
    {
        private Service service;

        public Consola(Service service)
        {
            this.service = service;
        }

        private void Meniu()
        {
            Console.WriteLine("----------- Meniu -----------");
            Console.WriteLine("1. Adauga student");
            Console.WriteLine("2. Sterge student");
            Console.WriteLine("3. Actualizare date student");
            Console.WriteLine("4. Cauta student");
            Console.WriteLine("5. Vizualizare lista studenti");
            Console.WriteLine("6. Adauga tema");
            Console.WriteLine("7. Prelungire deadline");
            Console.WriteLine("8. Vizualizare lista teme");
            Console.WriteLine("9. Asignare nota");
            Console.WriteLine("10. Filtrare dupa grupa");
            Console.WriteLine("11. Filtrare teme dupa interval");

            Console.WriteLine("12. Media studentilor la laborator");
            Console.WriteLine("13. Studentii care intra in examen");
            Console.WriteLine("14. Studentii care au predat toate temele la timp");
           // Console.WriteLine("15. Media per laborator");
            
            Console.WriteLine("0. Exit");
            Console.WriteLine("-----------------------------");
        }

        private void CallAdaugaStudent()
        {
            try
            {
                Console.Write("ID: ");
                string id = Console.ReadLine();
                Console.Write("Nume: ");
                string nume = Console.ReadLine();
                Console.Write("Grupa: ");
                string grupa = Console.ReadLine();
                Console.Write("Email: ");
                string email = Console.ReadLine();
                Console.Write("Profesor indrumator: ");
                string prof = Console.ReadLine();
                Student student = new Student()
                {
                    ID = id,
                    Nume = nume,
                    Grupa = grupa,
                    Email = email,
                    IndrumatorLab = prof
                };
                bool adaugat = service.AdaugaStudent(student);

                if (adaugat)
                    Console.WriteLine(">> Studentul a fost adaugat!");
                else
                    Console.WriteLine(">> Studentul nu a putut fi adaugat!");
            }
            catch (ValidationException e)
            {
                Console.WriteLine(">> " + e.Message);
            }
        }

        private void CallStergeStudent()
        {
            try
            {
                Console.Write("ID student: ");
                String id = Console.ReadLine();
                bool sters = service.StergeStudent(id);
                if (sters)
                    Console.WriteLine(">> Studentul a fost sters");
                else Console.WriteLine(">> Nu exista niciun student cu id-ul " + id);
            }
            catch (ValidationException e)
            {
                Console.WriteLine(">> " + e.Message);
            }
        }

        private void CallActualizareStudent()
        {
            try
            {
                Console.Write("ID student: ");
                String id = Console.ReadLine();
                Student student = service.CautaStudent(id);
                if (student == default(Student))
                    Console.Write("Nu exista niciun student cu acest id");
                else {
                    bool updated = false;

                    Console.Write("Nume: ");
                    String nume = Console.ReadLine();
                    if (nume == "") nume = student.Nume;
                    else updated = true;

                    Console.Write("Grupa: ");
                    String grupa = Console.ReadLine();
                    if (grupa == "") grupa = student.Grupa;
                    else updated = true;

                    Console.Write("Email: ");
                    String email = Console.ReadLine();
                    if (email == "") email = student.Email;
                    else updated = true;

                    Console.Write("Profesor indrumator: ");
                    String prof = Console.ReadLine();
                    if (prof == "") prof = student.IndrumatorLab;
                    else updated = true;

                    if (!updated) Console.WriteLine(">> Nu s-au introdus date noi");
                    else
                    {
                        Student studentNou = new Student
                        {
                            ID = id,
                            Nume = nume,
                            Grupa = grupa,
                            Email = email,
                            IndrumatorLab = prof
                        };
                        service.ActualizeazaStudent(studentNou);
                        Console.WriteLine(">> Datele studentului au fost modificate");
                    }
                }
            }
            catch (ValidationException e)
            {
                Console.WriteLine(">> " + e.Message);
            }
        }

        

        private void CallCautaStudent()
        {
            try
            {
                Console.Write("ID student: ");
                string id = Console.ReadLine();
                Student student = service.CautaStudent(id);
                if (student == default(Student))
                    Console.WriteLine(">> Nu exista niciun student cu numarul matricol " + id);
                else Console.WriteLine(">> Studentul cautat este " + student);

            }
            catch (ValidationException e)
            {
                Console.WriteLine(">> " + e.Message);
            }
        }

        private void CallListaStudenti()
        {
            Console.WriteLine(">> Lista de studenti: ");
            int nrc = 1;
            foreach (Student s in service.ListaStudenti())
            {
                Console.WriteLine(nrc.ToString() + ". " + s);
                nrc++;
            }
        }

        private void CallAdaugaTema()
        {
            Console.Write("ID: ");
            string id = Console.ReadLine();
            Console.Write("Descriere: ");
            string descriere = Console.ReadLine();
            Console.Write("Deadline: ");
            string deadline = Console.ReadLine();
            Console.Write("Predare: ");
            string predare = Console.ReadLine();
            Tema tema = new Tema()
            {
                ID = id,
                Descriere = descriere,
                Deadline = deadline,
                DataPredare = predare
            };
            try
            {
                if (service.AdaugaTema(tema))
                    Console.WriteLine(">> Tema adaugata");
                else Console.WriteLine(">> Tema existenta!");
            }
            catch (ValidationException e)
            {
                Console.WriteLine(">> " +e.Message);
            }

        }
        private void CallPrelungireDeadline()
        {
            Console.Write("ID: ");
            string id = Console.ReadLine();
            Console.Write("Deadline nou: ");
            string deadline = Console.ReadLine();
            if (service.PrelungireDeadline(id, deadline))
                Console.WriteLine("Deadline actualizat");
            else Console.WriteLine("Deadline-ul nu a fost modificat!");

        }

        private void CallListaTeme()
        {
            Console.WriteLine(">> Lista de teme: ");
            int nrc = 1;
            foreach (Tema t in service.ListaTeme())
            {
                Console.WriteLine(nrc.ToString() + ". " + t);
                nrc++;
            }
        }

        private void CallAsignareNota()
        {
            try
            {
                Console.Write("ID student: ");
                string idS = Console.ReadLine();
                Console.Write("ID tema: ");
                string idT = Console.ReadLine();
                Console.Write("Nota: ");
                string notaProf = Console.ReadLine();
                Console.Write("Data: ");
                string data = Console.ReadLine();

                bool motivat = false;
                Console.WriteLine("Studentul a lipsit motivat? (y/n)");
                string ans = Console.ReadLine();
                if (ans.Equals("y") || ans.Equals("Y"))
                    motivat = true;

                Nota nota = new Nota()
                {
                    StudentID = idS,
                    TemaID = idT,
                    DataCurenta = data,
                    NotaProf = notaProf
                };
                if (!service.AdaugaNota(nota, motivat))
                    Console.WriteLine(">> Nota nu a putut fi adaugata!");
                else
                {
                    if (motivat)
                    {
                        Console.WriteLine(">> Nota maxima " + nota);
                        Console.WriteLine(">> Studentul a primit nota " + nota);
                    }
                    else
                    {
                        Tema t = service.CautaTema(idT);
                        Console.WriteLine(">> Nota maxima " + service.CalculeazaNota(data, "10", t));
                        Console.WriteLine(">> Studentul a primit nota " + service.CalculeazaNota(data, notaProf, t));
                    }
                }
            }
            catch (ValidationException e)
            {
                Console.WriteLine(">> " + e.Message);
            }
        }
    
        private void CallFiltrareGrupa()
        {
            Console.Write("Grupa: ");
            string grupa = Console.ReadLine();
            foreach (Student student in service.FilterByGrupa(grupa))
            {
                Console.WriteLine(student.ID+" "+student.Nume+" "+student.Email);
                
            }
        }

        private void CallFiltrareInterval()
        {
            Console.Write("Start: ");
            string start = Console.ReadLine();
            Console.Write("End: ");
            string end = Console.ReadLine();
            foreach (Tema t in service.FilterByInterval(start, end)) 
            {
                Console.WriteLine(t);

            }
        }

        private void CallMedieStudenti()
        {
            List<KeyValuePair<Student, double>> lista = service.MediaStudentilor();
            foreach (KeyValuePair<Student, double> pair in lista)
            {
                Console.WriteLine(pair.Key.Nume + " " + pair.Value);
            }
        }

        private void CallExamen()
        {
            List<KeyValuePair<Student, double>> lista = service.ListaExamen();
            foreach (KeyValuePair<Student, double> pair in lista)
            {
                Console.WriteLine(pair.Key.Nume + " - media:" + pair.Value);
            }
        }

        private void CallStudentiLaTimp()
        {
            List<Student> lista = service.ListaStudentiTemeLaTimp();
            foreach (Student s in lista)
            {
                Console.WriteLine(s.Nume);
            }
        }


        public void Run()
        {
            while (true)
            {
                Meniu();
                Console.Write("Comanda: ");
                string cmd;
                cmd = Console.ReadLine();
                
                if (cmd.Equals("0"))
                {
                    Console.WriteLine("Bye!");
                    break;
                }
                if (cmd.Equals("1"))
                {
                    CallAdaugaStudent();
                    continue;
                }
                if (cmd.Equals("2"))
                {
                    CallStergeStudent();
                    continue;
                }
                if (cmd.Equals("3"))
                {
                    CallActualizareStudent();
                    continue;
                }
                if (cmd.Equals("4"))
                {
                    CallCautaStudent();
                    continue;
                }
                if (cmd.Equals("5"))
                {
                    CallListaStudenti();
                    continue;
                }
                if (cmd.Equals("6"))
                {
                    CallAdaugaTema();
                    continue;
                }
                if (cmd.Equals("7"))
                {
                    CallPrelungireDeadline();
                    continue;
                }
                if (cmd.Equals("8"))
                {
                    CallListaTeme();
                    continue;
                }
                if (cmd.Equals("9"))
                {
                    CallAsignareNota();
                    continue;
                }
                if (cmd.Equals("10"))
                {
                    CallFiltrareGrupa();
                    continue;
                }
                if (cmd.Equals("11"))
                {
                    CallFiltrareInterval();
                    continue;
                }
                if (cmd.Equals("12"))
                {
                    CallMedieStudenti();
                    continue;
                }
                if (cmd.Equals("13"))
                {
                    CallExamen();
                    continue;
                }
                if (cmd.Equals("14"))
                {
                    CallStudentiLaTimp();
                    continue;
                }
                if (cmd.Equals("15"))
                {
                    
                    continue;
                }
                Console.WriteLine("Comanda invalida!");
            }

        }
    
    }
}
