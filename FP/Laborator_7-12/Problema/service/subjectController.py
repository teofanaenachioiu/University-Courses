'''
Created on 9 nov. 2017

@author: TEO
'''
from domain.subjects import Subject
import random

class SubjectTable():

    def __init__(self,subRepo,subVal):
        self.__table=subRepo
        self.__valid=subVal
    
    
    def createSubject(self,idSubject,name,teacher):
        """
        Create a new subject.
        :param idSubject: string(lccc)
        :param name: string
        :param teacher: string
        """   
        sub=Subject(idSubject,name,teacher)
        self.__valid.validate(sub)
        return sub
    
    
    def addSubject(self,sub):
        """
        Add subject read from the console.
        :param sub: subject
        :return: subject added
        """
        return self.__table.store(sub)
    
    
    def updateSubjectName(self,idGiven,newName):   
        """
        Update subject name
        :param idGiven: string
        :param newName: string
        :return: subject
        """
        return self.__table.updateName(idGiven, newName)
            
     
    def updateSubjectTeacher(self,idGiven,newTeacher):   
        """
        Update teacher name
        :param idGiven: string
        :param newTeacher: string
        :return: subject
        """
        return self.__table.updateTeacher(idGiven, newTeacher)
        
            
    def delSubject(self,idGiven):
        """"
        Remove a subject from the table.
        :param idGiven: string
        :return: subject removed
        """
        return self.__table.delete(idGiven)
        
    
    def searchSubject(self,idGiven):
        """
        Search student in the catalog.
        :param idGiven: string
        :return: subject
        """    
        return self.__table.search(idGiven)
    
    def showSubjects(self):
        """
        View all subjects. 
        :return: list of subjects
        """
        return self.__table.getAll()
    
    
    def undoSubject(self):
        
        return self.__table.undo()
    
    def randomSubject(self):
        """
        Give a random student.
        :return: sub (subject)
        """    
        idList=["f112","a123","a111","l122","a152","a263","f122","r112","f256","r125","f160"]
        idSubject=random.choice(idList)
        nameList=["FP","ASC","Analiza","Algebra","Logica"]
        name=random.choice(nameList)
        teacherList=["Popescu","Andrei","Moldovan"]
        teacher=random.choice(teacherList) 
        sub=Subject(idSubject,name,teacher)
        self.__valid.validate(sub)
        return sub