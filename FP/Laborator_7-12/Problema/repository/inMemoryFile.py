'''
Created on 4 dec. 2017

@author: TEO
'''
from domain.students import Student
from domain.subjects import Subject
from domain.notes import Note
from domain.validation import IdNotFound,RepositoryError,DuplicateDataError
import copy

class inMemoryStudentsFile:
    def __init__(self,fileName):
        self.__fileName=fileName
        self.__undo=[]
        self.__listStudents=[]
        self.__loadFromFile()
        
    def __loadFromFile(self):
        """
        Load students from file.
        :return rez: list of "student object"
        """
        try:
            f=open(self.__fileName,"r")
        except IOError:
            raise RepositoryError()
        linie = f.readline().strip()
        while linie!="":
            part=linie.split(",")
            st=Student(part[0],part[1])
            self.__listStudents.append(st)
            linie=f.readline().strip()
        f.close()
     
    def __storeInFile(self):
        """
        Store a list of students in file.
        """
        with open(self.__fileName,"w") as f:
            for st in self.__listStudents:
                stf=st.getID()+","+st.getName()+'\n'
                f.write(stf)
            
    def clearFile(self):
        """
        Remove all the students from the repository
        """
        self.__listStudents=[]
        self.__storeInFile()
    
    def getAll(self):
        """
        Return all students stored in file.
        :return: list of students object
        """
        return self.__listStudents[:]
         
    def size(self):
        """
        Get the size of file
        :return: integer
        """
        return len(self.__listStudents)
            
    def store(self,st):
        """
        Store all students.
        :param st: student
        :raise DuplicateDataError: we have a student with the same id in file
        :return st: student
        """
        
        if st in self.__listStudents:
            raise DuplicateDataError()
        self.__listStudents.append(st)
        self.__storeInFile()
        item=self.__listStudents[:]
        self.__undo.append(item)
        return st
    
    
    def update(self,idGiven, newName):
        """
        Update student's name. 
        :param idGiven: string
        :param newName: string
        :return stf: student
        """
        sts=self.__listStudents
        gasitID=False
        for i in range (0,len(sts)):
            if idGiven == sts[i].getID():
                gasitID=True
                poz=i
        if gasitID==False:
            raise IdNotFound()
        stf=sts[poz]
        del sts[poz]
        stf.setName(newName)
        sts.append(stf)
        self.__listStudents=sts
        self.__storeInFile()
        item=sts[:]
        self.__undo.append(item)
        return stf
    
    def delete(self,idGiven):
        """
        Delete a student in the repository.
        :param idGiven: string
        :raise IdNotFound: ID isn't assigned to any student
        :return: student
        """
        sts=self.__listStudents
        gasitID=False
        for i in range (0,len(sts)):
            if idGiven == sts[i].getID():
                gasitID=True
                poz=i
        if gasitID==False:
            raise IdNotFound()
        stf=sts[poz]
        del sts[poz]
        self.__listStudents=sts
        self.__storeInFile()
        item=sts[:]
        self.__undo.append(item)
        return stf
     
    def searchIterativ(self,idGiven):
        """
        Search a student in the repository.
        :param idGiven: string
        :raise IdNotFound: ID isn't assingend to any student
        :return: student
        """   
    
        gasitID=False
        for st in self.__listStudents:
            if idGiven == st.getID():
                return st             
        if gasitID==False:
            raise IdNotFound()
        
    #**********************************************
    #*implementare recursiva a functiei de cautare*
    #**********************************************
    def search(self,idGiven,students):
        """
        Search a student in the repository.
        :param idGiven: string
        :raise IdNotFound: ID isn't assingend to any student
        :return: student
        """   
        
        if students==[]:
            raise IdNotFound()
        if students[0].getID()==idGiven:
            return students[0]
        return self.search(idGiven, students[1:])
            

    def undo(self):
        """
        Undo function.
        :return: list of students.
        """

        if len(self.__undo)== 0 :
            return []
        if len(self.__undo)== 1 :
            del self.__undo[-1]
            del self.__listStudents[-1]
            self.__storeInFile()
            return []
        del self.__undo[-1]
        self.__listStudents=copy.deepcopy(self.__undo[-1])
        self.__storeInFile()
        return self.__listStudents
    

class inMemorySubjectFile():
    
    def __init__(self,fileName):
        self.__fileName=fileName
        self.__loadFromFile()
        self.__undo=[]
        
    def __loadFromFile(self):
        """
        Load students from file.
        :return rez: list of "subject object"
        """
        try:
            f=open(self.__fileName,"r")
        except IOError:
            raise RepositoryError()
        linie = f.readline().strip()
        rez = []
        while linie!="":
            part=linie.split(",")
            sub=Subject(part[0],part[1],part[2])
            rez.append(sub)
            linie=f.readline().strip()
        f.close()
        return rez
     
    def __storeInFile(self,subs):
        """
        Store a list of students in file.
        :param subs: list of "subject object"
        """
        with open(self.__fileName,"w") as f:
            for sub in subs:
                subf=sub.getID()+","+sub.getName()+","+sub.getTeacher()+'\n'
                f.write(subf)
    
    def clearFile(self):
        """
        Remove all the subjects from the repository
        """
        self.__storeInFile([])  
            
            
    def store(self,sub):
        """
        Store all subjects.
        :param sub: subject
        :raise DuplicateDataError: we have a subject with the same id in file
        :return sub: subject
        """
        subs=self.__loadFromFile()
        if sub in subs:
            raise DuplicateDataError()
        subs.append(sub)
        self.__storeInFile(subs)
        item=subs[:]
        self.__undo.append(item)
        return sub
    
    
    def size(self):
        """
        Give the number of subjects in the repository.
        :return: an integer number
        """
        return len(self.__loadFromFile())
    
    
    def updateName(self,idGiven, newName):
        """
        Update the name of subject. 
        :param idGiven: string
        :param newName: string
        :return: subject
        """
        subs=self.__loadFromFile()
        gasitID=False
        for i in range (0,len(subs)):
            if idGiven == subs[i].getID():
                gasitID=True
                poz=i
        if gasitID==False:
            raise IdNotFound()
        sub=subs[poz]
        del subs[poz]
        sub.setName(newName)
        subs.append(sub)
        self.__storeInFile(subs)
        item=subs[:]
        self.__undo.append(item)
        return sub
    
    
    def updateTeacher(self,idGiven, newTeacher):
        """
        Update the name of subject. 
        :param idGiven: string
        :param newTecher: string
        :return: subject
        """
        subs=self.__loadFromFile()
        gasitID=False
        for i in range (0,len(subs)):
            if idGiven == subs[i].getID():
                gasitID=True
                poz=i
        if gasitID==False:
            raise IdNotFound()
        sub=subs[poz]
        del subs[poz]
        sub.setTeacher(newTeacher)
        subs.append(sub)
        self.__storeInFile(subs)
        item=subs[:]
        self.__undo.append(item)
        return sub

    
    def delete(self,idGiven):
        """
        Delete a subject in the repository.
        :param idGiven: string
        :raise IdNotFound: ID isn't assigned to any student
        :return: subject
        """
        subs=self.__loadFromFile()
        gasitID=False
        for i in range (0,len(subs)):
            if idGiven==subs[i].getID():
                gasitID=True
                poz=i
        if gasitID==False:
            raise IdNotFound()
        sub=subs[poz]
        del subs[poz]
        self.__storeInFile(subs)
        item=subs[:]
        self.__undo.append(item)
        return sub      
        
        
    def search(self,idGiven):
        """
        Search a subject in the repository.
        :param idGiven: string
        :raise IdNotFound: ID isn't assingend to any subject
        :return: subject
        """    
        subs=self.__loadFromFile()
        gasitID=False
        for sub in subs:
            if idGiven == sub.getID():
                gasitID=True
                subf=sub
        if gasitID==False:
            raise IdNotFound()
        return subf
        
        
    def getAll(self):
        """
        Give the list of all students.
        :return: list of "student" objects.
        """
        return self.__loadFromFile()

    def undo(self):
        """
        Undo function.
        :return: list of subjects
        """
        if len(self.__undo)== 0 :
            return []
        if len(self.__undo)== 1 :
            del self.__undo[-1]
            subs=self.__loadFromFile()
            del subs[-1]
            self.__storeInFile(subs)
            return []
        del self.__undo[-1]
        subs=copy.deepcopy(self.__undo[-1])
        self.__storeInFile()
        return subs

    
class inMemoryNotesFile():
    
    def __init__(self,fileName,repoS,repoD):
        self.__fileName=fileName
        self.__repoSt=repoS
        self.__repoSub=repoD
        self.__listNote=[]
        self.__loadFromFile()

    def __loadFromFile(self):
        """
        Load notes from file.
        :return rez: list of "noteDTO object"
        """ 
        
        try:
            f=open(self.__fileName,"r")
        except IOError:
            raise RepositoryError()
        linie = f.readline().strip()
        while linie!="":
            part=linie.split(",")
            idStudent=part[0]
            try:
                st=self.__repoSt.search(idStudent,self.__repoSt.getAll())
                idSubject=part[1]
                sub=self.__repoSub.search(idSubject)
                note=int(part[2])
                grade=Note(st, sub, note)
                self.__listNote.append(grade) 
            except IdNotFound:
                pass
            linie=f.readline().strip()
        f.close()
     
     
    def __storeInFile(self):
        """
        Store a list of notes in file.
        :param subs: list of "noteDTO object"
        """
        with open(self.__fileName,"w") as f:
            for gr in self.__listNote:
                grf=gr.getStudent().getID()+","+gr.getSubject().getID()+","+str(gr.getNote())+'\n'
                f.write(grf)
    
    def clearFile(self):
        """
        Remove all the notes from the repository
        """
        self.__listNote=[]
        self.__storeInFile()  
        
    """def createNoteDTO(self,mark):

        gr=NoteDTO(mark.getStudent().getID(),mark.getSubject().getID(),mark.getNote())
        return gr"""
        
    def store(self,mark):
        """
        Store all notes.
        :param mark: note
        :raise NoteError: note already assigned
        :return: mark
        """
        notes=self.__listNote
        if mark in notes:
            raise DuplicateDataError()
        notes.append(mark)
        self.__listNote=notes
        self.__storeInFile()
        return mark 
    
        
    def size(self):
        """
        Give the number of notes in the repository.
        :return: an integer number
        """
        return len(self.__listNote) 
       
       
    def search(self,mark):
        """
        Search a note in the repository.
        :param mark: note object
        :return: True/False
        """  
        for gr in self.__listNote:
            if gr.getStudent().getID()==mark.getStudent().getID() and gr.getSubject().getID()==mark.getSubject().getID():
                return True
        return False
    
    
    def delStudentIterativ(self,st):
        """
        Remove the note for a student. (Student was also removed.)
        :param st: student
        :return: note object
        """
        grades=self.__listNote
        for gr in grades:
            if gr.getStudent().getID()==st.getID():
                grades.remove(gr)
                copy=gr
        self.__listNote=grades
        self.__storeInFile()
        return copy.getStudent().getID()
    
    #**********************************
    #Implementare recursiva a functiei*
    #**********************************
    def delStudent(self,st,grades):
        """
        Remove the note for a student. (Student was also removed.)
        :param st: student
        :return: note object
        """
        if grades==[]:
            return st.getID()
        if grades[0].getStudent()==st:
            self.__listNote.remove(grades[0])
            self.__storeInFile()
        return self.delStudent(st, grades[1:])        
    
    
    def updateStudent(self,st,name):
        """
        Update the student for a note.(Student name was updated)
        :param st: student
        :param name: string
        :return: note object 
        """
        grades=self.__listNote
        for gr in grades:
            if gr.getStudent().getID()==st.getID():
                copy=gr
        return copy.getIdStudent()
    
    
    def delSubject(self,sub):
        """
        Remove notes for a subject. (Subject was also removed.)
        :param sub: subject
        :return: note object
        """
        grades=self.__listNote
        for gr in grades:
            if gr.getSubject().getID()==sub.getID():
                grades.remove(gr)
                copy=gr
        self.__listNote
        self.__storeInFile()
        return copy.getIdSubject()
    
    
    def getStudents(self,sub):
        """
        Give the list of all notes.
        :return: list of "note" objects.
        """
        rez = []
        for gr in self.__listNote:
            if gr.getSubject().getID()==sub.getID():
                rez.append(gr)
        return rez
    

    def getAllMemory(self):
        """
        Get all from memory
        """
        return self.__listNote[:]    