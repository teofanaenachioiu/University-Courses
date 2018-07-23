'''
Created on 13 nov. 2017

@author: TEO
'''
from domain.validation import IdError,IdNotFound, NoteError
import copy

class inMemoryStudents():
    def __init__(self):
        """
        Initialize a dictionary. There will be stored students.
        """
        self.__memory={}
        self.__undo=[]
              
    def store(self,st):
        """
        Store all students.
        :param st: student
        :raise IdError: we have a student with the same id
        :return: student
        """
        if st.getID() in self.__memory:
            raise IdError()
        self.__memory[st.getID()]=st
        item=copy.deepcopy(self.__memory)
        self.__undo.append(item)
        return st
       
    def size(self):
        """
        Give the number of students in the repository.
        :return: an integer number
        """
        return len(self.__memory)
    
    def update(self,idGiven, newName):
        """
        Update student's name. 
        :param idGiven: string
        :param newName: string
        :return: student
        """
        if not idGiven in self.__memory:
            raise IdNotFound()
        st=self.__memory[idGiven]
        st.setName(newName)
        item=copy.deepcopy(self.__memory)
        self.__undo.append(item)
        return st
        
    def delete(self,idGiven):
        """
        Delete a student in the repository.
        :param idGiven: string
        :raise IdNotFound: ID isn't assigned to any student
        :return: student
        """
        if not idGiven in self.__memory:
            raise IdNotFound()
        item=copy.deepcopy(self.__memory)
        self.__undo.append(item)
        st=self.__memory[idGiven]
        self.__memory.pop(idGiven)
        item=copy.deepcopy(self.__memory)
        self.__undo.append(item)
        return st
    
    def searchIterativ(self,idGiven,students):
        """
        Search a student in the repository.
        :param idGiven: string
        :raise IdNotFound: ID isn't assingend to any student
        :return: student
        """    
        if not idGiven in self.__memory:
            raise IdNotFound()
        st=self.__memory[idGiven]
        return st        
    
    
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
    
        """
        if not idGiven in self.__memory:
            raise IdNotFound()
        st=self.__memory[idGiven]
        return st    
        """      
    def getAll(self):
        """
        Give the list of all students.
        :return: list of "student" objects.
        """
        return list(self.__memory.values())
      
    def undo(self):
        """
        Undo function.
        :return: list of students.
        """
        if len(self.__undo)== 0 :
            return []
        if len(self.__undo)== 1 :
            del self.__undo[-1]
            self.__memory={}
            return list(self.__memory.values())
        del self.__undo[-1]
        self.__memory=copy.deepcopy(self.__undo[-1])
        return list(self.__memory.values())


class inMemorySubject():
    def __init__(self):
        """
        Initialize a dictionary. There will be stored the subjects.
        """
        self.__memory={}
        self.__undo=[]
        
        
    def store(self,sub):
        """
        Store all subjects.
        :param sub: subject
        :raise IdError: we have a subject with the same id
        :return: subject
        """
        if sub.getID() in self.__memory:
            raise IdError()
        self.__memory[sub.getID()]=sub
        item=copy.deepcopy(self.__memory)
        self.__undo.append(item)
        return sub
    
    
    def size(self):
        """
        Give the number of students in the repository.
        :return: an integer number
        """
        return len(self.__memory)
    
    
    def updateName(self,idGiven, newName):
        """
        Update the name of subject. 
        :param idGiven: string
        :param newName: string
        :return: subject
        """
        if not idGiven in self.__memory:
            raise IdNotFound()
        sub=self.__memory[idGiven]
        sub.setName(newName)
        item=copy.deepcopy(self.__memory)
        self.__undo.append(item)
        return sub
    
    
    def updateTeacher(self,idGiven, newTeacher):
        """
        Update the name of subject. 
        :param idGiven: string
        :param newTecher: string
        :return: subject
        """
        if not idGiven in self.__memory:
            raise IdNotFound()
        sub=self.__memory[idGiven]
        sub.setTeacher(newTeacher)
        item=copy.deepcopy(self.__memory)
        self.__undo.append(item)
        return sub
        
    
    def delete(self,idGiven):
        """
        Delete a subject in the repository.
        :param idGiven: string
        :raise IdNotFound: ID isn't assigned to any student
        :return: subject
        """
        if not idGiven in self.__memory:
            raise IdNotFound()
        sub=self.__memory[idGiven]
        self.__memory.pop(idGiven)
        item=copy.deepcopy(self.__memory)
        self.__undo.append(item)
        return sub
        
     
    def search(self,idGiven):
        """
        Search a subject in the repository.
        :param idGiven: string
        :raise IdNotFound: ID isn't assingend to any subject
        :return: subject
        """    
        if not idGiven in self.__memory:
            raise IdNotFound()
        sub=self.__memory[idGiven]
        return sub
        
    def getAll(self):
        """
        Give the list of all students.
        :return: list of "student" objects.
        """
        return list(self.__memory.values())

    def undo(self):
        """
        Undo function.
        :return: list of subjects
        """
        if len(self.__undo)== 0 :
            return []
        if len(self.__undo)== 1 :
            del self.__undo[-1]
            self.__memory={}
            return list(self.__memory.values())
        del self.__undo[-1]
        self.__memory=copy.deepcopy(self.__undo[-1])
        return list(self.__memory.values())
 

class inMemoryNotes():
    
    def __init__(self):
        self.__memory=[]
        
        
    def store(self,mark):
        """
        Store all notes.
        :param mark: note
        :raise NoteError: note already assigned
        :return: mark
        """
        if self.search(mark)==True:
            raise NoteError()
        self.__memory.append(mark)
        return mark    
        
        
    def size(self):
        """
        Give the number of notes in the repository.
        :return: an integer number
        """
        return len(self.__memory) 
       
       
    def search(self,mark):
        """
        Search a note in the repository.
        :param mark: note object
        :return: True/False
        """  
        for gr in self.__memory:
            if gr.getStudent()==mark.getStudent() and gr.getSubject()==mark.getSubject():
                return True
        return False
    
    
    def delStudentIterativ(self,st):
        """
        Remove the note for a student. (Student was also removed.)
        :param st: student
        :return: note object
        """
        for gr in self.__memory:
            if gr.getStudent()==st:
                self.__memory.remove(gr)
        return st.getID()
    
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
            self.__memory.remove(grades[0])
        return self.delStudent(st, grades[1:])
    
    
    def updateStudent(self,st,name):
        """
        Update the student for a note.(Student name was updated)
        :param st: student
        :param name: string
        :return: note object
        """
        for gr in self.__memory:
            if gr.getStudent()==st:
                gr.getStudent().setName(name)
                copy=gr
        return copy.getStudent().getID()
    
    
    def delSubject(self,sub):
        """
        Remove notes for a subject. (Subject was also removed.)
        :param sub: subject
        :return: note object
        """
        for gr in self.__memory:
            if gr.getSubject()==sub:
                self.__memory.remove(gr)
                copy=gr
        return copy.getSubject().getID()
    
    
    def getStudents(self,sub):
        """
        Give the list of all notes.
        :return: list of "note" objects.
        """
        rez = []
        for gr in self.__memory:
            if gr.getSubject()==sub:
                rez.append(gr)
        return rez

    def getAllMemory(self):
        """
        Get all from memory
        """
        rez = []
        for gr in self.__memory:
                rez.append(gr)
        return rez