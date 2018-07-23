'''
Created on 10 nov. 2017

@author: TEO
'''
class Student():
    def __init__(self,idStudent,name):
        """
        Create a new student with the given idStudent and name
        :param idStudent: string
        :param name: string
        """
        self.__id=idStudent
        self.__name=name
        
    def __eq__(self,ot):
        """
        Set when two objects student are equals.
        """
        return self.__id==ot.getID()    
        
    def getID(self):
        """
        Return student's id.
        """
        return self.__id
    
    def getName(self):
        """
        Return student's name
        """
        return self.__name
    
    def setName(self,newName):
        """
        Set a new name for a student
        :param newName: string
        """
        self.__name=newName
        return self.__name