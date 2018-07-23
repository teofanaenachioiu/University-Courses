'''
Created on 6 dec. 2017

@author: USER
'''
from domain.students import Student
from domain.subjects import Subject
from domain.notes import Note,NoteDTO,Media
from domain.validation import NoteValidator,SubjectValidator,StudentValidator,InputError
from repository.inMemory import inMemoryNotes,inMemoryStudents,inMemorySubject
from repository.inMemoryFile import inMemoryNotesFile,inMemoryStudentsFile,inMemorySubjectFile
from service.noteController import NoteList
from service.studentController import StudentList
from service.subjectController import SubjectTable
from Sorting.Sortare import bingoSort,mergeSort,swap

import unittest

class TestCase(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.st=Student("et2287","Teo")
        self.sub=Subject("f112","FP","Pop")
        self.mark=Note(self.st,self.sub,8)
        self.st1=Student("et2287","Teo")
        self.sub1=Subject("f112","FP","Pop")
        self.mark1=Note(self.st1,self.sub1,8)
        
    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def test_Note(self):
        self.assertEqual(self.mark.getStudent(), self.st, "Students aren't identical")
        self.assertEqual(self.mark.getSubject(), self.sub, "Subjects aren't identical")
        self.assertEqual(self.mark.getNote(), 8, "Grades aren't identical")
        self.assertEqual(self.mark, self.mark1, "Grade objects are not identical")


class TestCaseMedii(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.st1=Student("et2287","Teo")
        self.st2=Student("et2288","Ana")
        self.sub1=Subject("f112","FP","Popescu")
        self.sub2=Subject("a112","ASC","Gherman")
        self.mark1=Note(self.st1,self.sub1,9)
        self.mark2=Note(self.st1,self.sub2,10)
        self.mark3=Note(self.st2,self.sub1,8)
        self.media1=Media("et2287","Teo")
        self.media2=Media("et2288","Ana")
        
    def tearDown(self):
        unittest.TestCase.tearDown(self)
    
    def test_Media1Stundet(self):
        self.media1.addNote(self.mark1.getNote())
        self.assertEqual(self.media1.getMedia(), 9.00, "Incorrect average.")
        self.media1.addNote(self.mark2.getNote())
        self.assertEqual(self.media1.getMedia(), 9.50, "Incorrect average.")
        
    def test_Media2Students(self):
        self.media1.addNote(self.mark1.getNote())
        self.media1.addNote(self.mark2.getNote())
        self.media2.addNote(self.mark3.getNote())
        self.assertEqual(self.media1.getMedia(), 9.50, "Incorrect average.")
        self.assertEqual(self.media2.getMedia(), 8.00, "Incorrect average.")


class TestCaseCreateNote(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.note=NoteDTO("et2287","f112",10)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testCreateNote(self):
        self.assertEqual(self.note.getIdStudent(), "et2287", "Incorrect id student")
        self.assertEqual(self.note.getNote(), 10, "Incorrect note")


class TestCaseCreateStudent(unittest.TestCase):
    
    def setUp(self):
        unittest.TestCase.setUp(self)
        self.st=Student("et2287","Teofana")
        self.ot=Student("et2287","Ioana")

    def tearDown(self):
        unittest.TestCase.tearDown(self)
    
    def testCreateStudent(self):
        self.assertEqual(self.st.getID(), "et2287", "Id-urile nu sunt egale")
        self.assertEqual(self.st.getName(), "Teofana", "Numele nu sunt identice")
        self.assertEqual(self.st.setName("Teo"), "Teo", "Numele nu a fost setat corect")
        
    def testSameStudent(self):
        self.assertEqual(self.st, self.ot, "Studentii nu sunt identici")


class TestCaseCreateSubject(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.sub=Subject("f112","Fundamentele Programarii","Pop")

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testCreateSubject(self):
        self.assertEqual(self.sub.getID(), "f112", "Incorrect ID")
        self.assertEqual(self.sub.getName(), "Fundamentele Programarii", "Incorrect subject name")
        self.assertEqual(self.sub.getTeacher(), "Pop", "Incorrect teacher name")
    
    def testSetName(self):    
        self.assertEqual(self.sub.setName("FP"), "FP", "Name was set incorrectly")
        
    def testSetTeacher(self):
        self.assertEqual(self.sub.setTeacher("Popescu"), "Popescu", "Teacher name was set incorrectly") 
        
        
class TestCaseValidation(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.validSt=StudentValidator()
        self.validSub=SubjectValidator()
        self.validNote=NoteValidator()
        self.st=Student("","Teofana")
        self.sub=Subject("a112","Analiza","")
        
    def tearDown(self):
        unittest.TestCase.tearDown(self)
        
    def test_StudentValidator(self):
        self.assertRaises(InputError, self.validSt.validate,self.st)
        
    def test_SubjectValidator(self):
        self.assertRaises(InputError, self.validSub.validate,self.sub)
            
    def test_NoteValidator(self):
        mark=Note(self.st,self.sub,11)
        self.assertRaises(InputError,self.validNote.validate, mark) 
        
        
class TestCaseInMemory(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.memorySts=inMemoryStudents()
        self.memorySubs=inMemorySubject()
        self.memoryNotes=inMemoryNotes()
        self.st=Student("et2287","Teofana") 
        self.sub=Subject("f112","FP","Pop")
        self.mark=Note(self.st,self.sub,9)
        
    def tearDown(self):
        unittest.TestCase.tearDown(self)
        
    def test_AddStudent(self):
        self.assertEqual(self.memorySts.store(self.st),self.st,"Student was not added")
        
    def test_DelStudent(self):
        self.memorySts.store(self.st)
        self.assertEqual(self.memorySts.delete("et2287"), self.st, "Student was not removed")
        
    def test_SizeMemoryStundet(self): 
        self.memorySts.store(self.st)
        self.assertEqual(self.memorySts.size(), 1, "Wrong size")
        
    def test_UpdateStudent(self):
        self.memorySts.store(self.st)
        self.assertEqual(self.memorySts.update("et2287", "Teofana"), Student("et2287","Teofana"), "Student name was not updated")
        
    def test_SearchStudent(self):   
        self.memorySts.store(self.st)
        self.assertEqual(self.memorySts.search("et2287",self.memorySts.getAll()), self.st, "Student was not found")
        
    def test_AddSubject(self):
        self.assertEqual(self.memorySubs.store(self.sub),self.sub,"Subject was not added")
        
    def test_DelSubject(self):
        self.memorySubs.store(self.sub)
        self.assertEqual(self.memorySubs.delete("f112"), self.sub, "Subject was not removed")
        
    def test_SizeMemorySubjects(self): 
        self.memorySubs.store(self.sub)
        self.assertEqual(self.memorySubs.size(), 1, "Wrong size")
        
    def test_UpdateSubject(self):
        self.memorySubs.store(self.sub)
        self.assertEqual(self.memorySubs.updateName("f112", "Fp"), Subject("f112","Fp","Pop"), "Subject name was not updated")
        
    def test_SearchSubject(self):   
        self.memorySubs.store(self.sub)
        self.assertEqual(self.memorySubs.search("f112"), self.sub, "Subject was not found")
            
    def test_StoreNote(self):
        self.assertEqual(self.memoryNotes.store(self.mark),self.mark,"Grade was not stored")
                
    def test_GetAllMemory(self):
        self.memoryNotes.store(self.mark)
        self.assertEqual(len(self.memoryNotes.getAllMemory()),1,"Wrong list")
        
        
class TestCaseinMemoryFile(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.repoS = inMemoryStudentsFile("studentsTest.txt")
        self.repoD = inMemorySubjectFile("subjectsTest.txt")
        self.repo = inMemoryNotesFile("notesTest.txt",self.repoS,self.repoD)  
        self.repo.clearFile()
        self.repoD.clearFile()
        self.repoS.clearFile()
        
    def tearDown(self):
        unittest.TestCase.tearDown(self)
    
    def testNoteSize(self):
        self.repoS.store(Student("et2219","Ana"))
        self.assertEqual(self.repoS.size(), 1, "Wrong size of repository file") 
        
    def testNoteStore(self):
        #repoS.store(Student("et2299","Ana"))   
        mark1=Note(Student("et2287","Teo"), Subject("f112","FP","Pop"), 10)
        self.assertEqual(self.repo.store(mark1), mark1, "Mark wasn't stored in file")
        
    def testNoteSearch(self):
        self.repo.clearFile()
        mark1=Note(Student("et2287","Teo"), Subject("f112","FP","Pop"), 10)
        self.repo.store(mark1) 
        self.assertEqual(self.repo.search(mark1), True, "Grade wasn't found")
        
    def testNoteGetAll(self):      
        self.repo.clearFile()
        mark1=Note(Student("et2287","Teo"), Subject("f112","FP","Pop"), 10)
        self.repo.store(mark1) 
        self.assertEqual(len(self.repo.getAllMemory()), 1, "Nu e dimensiunea corecta")
        
    def testStRepoSize(self):
        self.repo.clearFile()
        self.assertEqual(self.repo.size(), 0, "Wrong size of repository file")
        
    def testStStore(self):
        self.assertEqual(self.repoS.store(Student("et2287","Teo")), Student("et2287","Teo"), "Same students")
        
    def testStSearch(self):
        self.repoS.store(Student("et2287","Teo"))
        self.assertEqual(self.repoS.search("et2287",self.repoS.getAll()), Student("et2287","Teo"), "Student wasn't found")  
        
    def testSubSize(self):
        self.assertEqual(self.repoD.size(), 0, "Wrong size of repository file")
        
    def testSubStore(self):
        sub=Subject("f112","Fp","Pop")
        self.assertEqual(self.repoD.store(sub), sub, "Subject was not stored")
    
    def testSubDel(self):
        sub=Subject("f112","Fp","Pop")
        self.repoD.store(sub)
        self.assertEqual(self.repoD.delete("f112"), sub, "Subject was not removed")  
        self.assertEqual(self.repoD.size(), 0, "Wrong size of repository file")
      
      
class TestCaseNoteController(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.catalog=NoteList(inMemoryNotes(),inMemoryStudents(),inMemorySubject(),NoteValidator())
        self.st1=Student("et2287","Teo")
        self.st2=Student("et2288","Ana")
        self.sub1=Subject("f112","FP","Pop")
        self.sub2=Subject("a112","ASC","Moldovan")
        self.grade1=Note(self.st1, self.sub1, 10)
        self.grade2=Note(self.st1, self.sub2, 9)
        self.grade3=Note(self.st2, self.sub1, 9)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def test_medieStudent(self):
        
        self.catalog.addNote(self.grade1)
        self.assertEqual(self.catalog.medieStudent()[0].getMedia(), 10.00, "Incorrect average of grades")
        
        self.catalog.addNote(self.grade2)
        self.assertEqual(self.catalog.medieStudent()[0].getMedia(), 9.50, "Incorrect average of grades")
        
    def test_listaMedii(self):
        self.catalog.addNote(self.grade1)
        self.catalog.addNote(self.grade2)
        self.catalog.addNote(self.grade3)
        self.assertEqual(len(self.catalog.medieStudent()), 2, "Lungime gresita")

    def test_getLastStudents(self):
        self.catalog.addNote(self.grade1)
        self.assertEqual(self.catalog.getLastStudents()[0].getMin(),10, "Lista gresita")
        
        self.catalog.addNote(self.grade2)
        self.assertEqual(self.catalog.getLastStudents()[0].getMin(),9, "Lista gresita")
        
    def test_createNote(self):
        note=9
        mark=self.catalog.createNote(self.st1, self.sub1, note)
        self.assertEqual(mark.getNote(), 9, "Incorrect note")
        self.assertEqual( mark.getStudent().getName(),"Teo","Incorrect student name")
        self.assertEqual(mark.getSubject().getTeacher(), "Pop", "Incorrect teacher name")
    
    def test_addNote(self):
        self.assertEqual(self.catalog.addNote(self.grade1), self.grade1, "Incorrect notes")
        self.assertEqual(self.catalog.addNote(self.grade2), self.grade2, "Incorrect notes")
        
    def test_getSubNoteSameStudent(self):
        self.catalog.addNote(self.grade1)
        self.assertEqual(self.catalog.getSubNote(self.sub1), [{'name':self.st1.getName(),'note':self.grade1.getNote()}], "Lista gresita")
        self.catalog.addNote(self.grade2)
        self.assertEqual(self.catalog.getSubNote(self.sub1), [{'name':self.st1.getName(),'note':self.grade1.getNote()}], "Lista gresita")
    
    def test_getSubNoteDifferentStudent(self):
        self.catalog.addNote(self.grade1)
        self.assertEqual(self.catalog.getSubNote(self.sub1), [{'name':self.st1.getName(),'note':self.grade1.getNote()}], "Lista gresita")
        self.catalog.addNote(self.grade3)
        self.assertEqual(self.catalog.getSubNote(self.sub1), [{'name':self.st1.getName(),'note':self.grade1.getNote()},{'name':self.st2.getName(),'note':self.grade3.getNote()}], "Lista gresita")
       
    def test_delStudentNote(self):    
        self.catalog.addNote(self.grade1)
        self.catalog.addNote(self.grade2)
        self.catalog.addNote(self.grade3)
        self.assertEqual(self.catalog.delStudentNote(self.st1), self.grade2.getStudent().getID(), "Nota nu a fost stearsa")
     
    def test_delSubjectNote(self):    
        self.catalog.addNote(self.grade1)
        self.catalog.addNote(self.grade2)
        self.catalog.addNote(self.grade3)
        self.assertEqual(self.catalog.delSubjectNote(self.sub1), self.grade3.getSubject().getID(), "Nota nu a fost stearsa")
               

class TestCaseStudentController(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.catalog=StudentList(inMemoryStudents(),StudentValidator())
        
    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testCreateStudent(self):
        st=self.catalog.createStudent("et2287", "Teofana")
        self.assertEqual(self.catalog.addStudent(st), st, "Student was not created.")
        
    def testAddStudent(self):
        st=self.catalog.createStudent("et2287", "Teofana")
        self.assertEqual(self.catalog.addStudent(st), st, "Student was not added.") 
        
    def testUpdateStudent(self):
        st=self.catalog.createStudent("et2287", "Teofana")
        self.catalog.addStudent(st)
        self.assertEqual(self.catalog.updateStudent("et2287","Maria"), Student("et2287","Maria"), "Student was not updated.") 
        
    def testDelStudent(self):
        st=self.catalog.createStudent("et2287", "Teofana")
        self.catalog.addStudent(st)
        self.assertEqual(self.catalog.delStudent("et2287"), st, "Student was not removed.") 
            
    def testSearchStudent(self):
        st=self.catalog.createStudent("et2287", "Teofana")
        self.catalog.addStudent(st)
        self.assertEqual(self.catalog.searchStudent("et2287"), st, "Student was not found.")        
        
    def testShowStudents(self):    
        st=self.catalog.createStudent("et2287", "Teofana")
        self.catalog.addStudent(st)
        self.assertEqual(len(self.catalog.showStudents()), 1, "Wrong list of students")
         

class TestCaseSubjectController(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.table=SubjectTable(inMemorySubject(),SubjectValidator())
        
    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testCreateSubject(self):
        self.assertEqual(self.table.createSubject("f112", "FP", "Pop"), Subject("f112","FP","Pop"), "Subject was not created.")
        
    def testAddStudent(self):
        sub=self.table.createSubject("f112", "FP", "Pop")
        self.assertEqual(self.table.addSubject(sub), sub, "Subject was not added.")    
        
    def testUpdateSubjectName(self):
        sub=self.table.createSubject("f112", "FP", "Pop")  
        self.table.addSubject(sub)  
        self.assertEqual(self.table.updateSubjectName("f112", "Fundamentele programarii"), Subject("f112","Fundamentele programarii","Pop"), "Subject name was not updated.")
        
    def testUpdateTeacherName(self):
        sub=self.table.createSubject("f112", "FP", "Pop")  
        self.table.addSubject(sub)  
        self.assertEqual(self.table.updateSubjectTeacher("f112", "Popescu"), Subject("f112","Fundamentele programarii","Popescu"), "Teacher name was not updated.")
            
    def testDelStudent(self):
        sub=self.table.createSubject("f112", "FP", "Pop")  
        self.table.addSubject(sub) 
        self.assertEqual(self.table.delSubject("f112"), sub, "Subject was not removed.")
        
    def testSearchSubject(self):
        sub=self.table.createSubject("f112", "FP", "Pop")  
        self.table.addSubject(sub) 
        self.assertEqual(self.table.searchSubject("f112"), sub, "Subject was not found.")         
        
    def testShowSubjects(self):
        sub=self.table.createSubject("f112", "FP", "Pop")  
        self.table.addSubject(sub) 
        self.assertEqual(len(self.table.showSubjects()), 1, "Wrong list of subjects") 

class TestCaseSortare(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.alist=[27,19,25,1,19,5,6,8,99,18,84,65,15,158,6,9,45,68]

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testSwap(self):
        self.assertEqual(swap(self.alist,1,2), [27, 25, 19, 1, 19, 5, 6, 8, 99, 18, 84, 65, 15, 158, 6, 9, 45, 68], "Elementele nu au putut fi interschimbate.")

    def testBingoSort(self):
        self.assertEqual(bingoSort(self.alist), [1, 5, 6, 6, 8, 9, 15, 18, 19, 19, 25, 27, 45, 65, 68, 84, 99, 158], "Lista nu a putut fi sortata.")
        
    def testMergeSort(self):
        self.assertEqual(mergeSort(self.alist), [1, 5, 6, 6, 8, 9, 15, 18, 19, 19, 25, 27, 45, 65, 68, 84, 99, 158], "Lista nu a putut fi sortata.")
        
if __name__ == '__main__':
    unittest.main()           
               
                  
if __name__ == '__main__':
    unittest.main()