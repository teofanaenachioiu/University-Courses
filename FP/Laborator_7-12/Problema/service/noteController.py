'''
Created on 20 nov. 2017

@author: TEO
'''
from domain.notes import Note,Media
from Sorting.Sortare import bingoSort, mergeSort


class NoteList():
     
    def __init__(self,grRepo,stRepo,subRepo,grVal):
        self.__table=grRepo
        self.__tableSt=stRepo
        self.__tableSub=subRepo
        self.__valid=grVal
        
        
    def createNote(self,st,sub,note): 
        """
        Create a new student.
        :param idStudent: string(llcccc)
        :param name: string
        """   
        mark=Note(st,sub,note)
        self.__valid.validate(mark)
        return mark
        
        
    def delStudentNote(self,st):
        """
        Remove notes for a student. Student was also removed.
        :param st: student
        :return: note
        """
        grades=self.__table.getAllMemory()
        return self.__table.delStudent(st,grades)   
     
    
    def updateStudentNote(self,st,name):
        """
        Remove notes for a student. Student was also removed.
        :param st: student
        :param name: string
        :return: note
        """
        return self.__table.updateStudent(st, name)  
    
    def delSubjectNote(self,sub):
        """
        Remove notes for a subject. Subject was also removed.
        :param sub: subject
        :return: note
        """
        return self.__table.delSubject(sub)
      
     
    def addNote(self,mark):
        """
        Add student read from the console
        :param st: student
        :return: student added
        """
        return self.__table.store(mark)
       
       
    def getSubNote(self,sub):
        """
        View all student from the catalog. 
        :return: list of students
        """
        gradeSubject=[]
        grades=self.__table.getStudents(sub) #list: st,sub,note
        for grade in grades:
            if grade.getSubject().getID()==sub.getID():
                d={}
                d['name']=grade.getStudent().getName()
                d['note']=grade.getNote()
                gradeSubject.append(d)
        return gradeSubject
    
    
    def getLastStudents(self):
        """
        Ia nota cea mai mica a unui student indiferent de disciplina.
        """
        id2medii ={}
        grades=self.__table.getAllMemory()
        for grade in grades:
            idS = grade.getStudent().getID()
            if idS in id2medii:
                id2medii[idS].addNote(int(grade.getNote()))
            else:
                id2medii[idS] = Media(grade.getStudent().getID(),grade.getStudent().getName())
                id2medii[idS].addNote(int(grade.getNote()))         
        rez= list( id2medii.values())
        newlist = bingoSort(rez, key=lambda grade: grade.getMin())
        return newlist
        
    
    def medieStudent(self):
        """
        Calculeaza media notelor pentru fiecare student.(Varianta 2)
        """
        id2medii ={}
        grades=self.__table.getAllMemory()
        for grade in grades:
            idS = grade.getStudent().getID()
            if idS in id2medii:
                id2medii[idS].addNote(int(grade.getNote()))
            else:
                id2medii[idS] = Media(grade.getStudent().getID(),grade.getStudent().getName())
                id2medii[idS].addNote(int(grade.getNote()))         
        rez= list( id2medii.values())
        rez=mergeSort(rez, key=lambda grade:grade.getMedia(),reverse=True)
        return rez       