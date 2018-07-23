'''
Created on 9 nov. 2017

@author: TEO
'''
from ui.console import Consola
from repository.inMemory import inMemoryStudents,inMemorySubject,inMemoryNotes
from repository.inMemoryFile import inMemoryStudentsFile,inMemorySubjectFile,inMemoryNotesFile
from domain.validation import StudentValidator, SubjectValidator, NoteValidator
from service.studentController import StudentList
from service.subjectController import SubjectTable
from service.noteController import NoteList

#stRepo=inMemoryStudents()
stRepo=inMemoryStudentsFile("students.txt")
stVal=StudentValidator()
stServ=StudentList(stRepo,stVal)

#subRepo=inMemorySubject()
subRepo=inMemorySubjectFile("subjects.txt")
subVal=SubjectValidator()
subServ=SubjectTable(subRepo,subVal)

#grRepo=inMemoryNotes()
grRepo=inMemoryNotesFile("notes.txt",stRepo,subRepo)
grVal=NoteValidator()
grServ=NoteList(grRepo,stRepo,subRepo,grVal)

if __name__ == '__main__':
    UI=Consola(stServ,subServ,grServ)
    UI.startUI()
