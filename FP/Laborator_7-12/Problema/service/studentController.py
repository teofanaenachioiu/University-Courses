'''
Created on 9 nov. 2017

@author: TEO
'''
from domain.students import Student
import random

class StudentList():
     
    def __init__(self,stRepo,stVal):
        self.__table=stRepo
        self.__valid=stVal
        
        
    def createStudent(self,idStudent,name): 
        """
        Create a new student.
        :param idStudent: string(llcccc)
        :param name: string
        """   
        st=Student(idStudent,name)
        self.__valid.validate(st)
        return st
        
        
    def addStudent(self,st):
        """
        Add student read from the console
        :param st: student
        :return: student added
        """
        return self.__table.store(st)
       
       
    def updateStudent(self,idGiven,newName):   
        """
        Update student's name
        :param idGiven: string
        :param newName: string
        :return: student
        """
        
        return self.__table.update(idGiven, newName)
            
            
    def delStudent(self,idGiven):
        """"
        Remove a student from the catalog.
        :param idGiven: string
        :return: student removed
        """
        return self.__table.delete(idGiven)
        
    
    def searchStudent(self,idGiven):
        """
        Search student in the catalog.
        :param idGiven: string
        :return: student
        """    
        students=self.__table.getAll()
        return self.__table.search(idGiven,students)
    
    
    def showStudents(self):
        """
        View all student from the catalog. 
        :return: list of students
        """
        return self.__table.getAll()
    
    def undoStudents(self):
        """
        :return: list of students   
        """
        return self.__table.undo()
         
    def randomStudent(self):
        """
        Give a random student.
        :return: st (student)
        """    
        idList=["et2287","aa1234","ss2230","ml2239","si2268","et2258","mm2563","mp2563","er6956","tl1236","al2269","tj2236"]
        idStudent=random.choice(idList)
        nameList=["Teo","Ana","Stefan","Ioana","Vlad"]
        name=random.choice(nameList) 
        st=Student(idStudent,name)
        self.__valid.validate(st)
        return st  