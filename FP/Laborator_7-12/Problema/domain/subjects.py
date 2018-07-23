'''
Created on 14 nov. 2017

@author: TEO
'''
class Subject():
    def __init__(self,idSubject,name,teacher):
        """
        Create a new subject with the given idSubject, name and teacher
        :param idSubject: string (lccc)
        :param name: string
        :param teacher: string
        """
        self.__id=idSubject
        self.__name=name
        self.__teacher=teacher
        
    def __eq__(self,ot):
        """
        Set when two objects subject are equals.
        """
        return self.__id==ot.__id    
            
    def getID(self):
        """
        Return subject Id.
        """
        return self.__id
    
    def getName(self):
        """
        Return subject name
        """
        return self.__name
    
    def getTeacher(self):
        """
        Return subject teacher.
        """
        return self.__teacher
    
    def setName(self,newName):
        """
        Set a new name for a subject
        :param newName: string
        """
        self.__name=newName
        return self.__name
    
    def setTeacher(self,newTeacher):
        """
        Set a new teacher for a subject
        :param newTeacher: string
        """
        self.__teacher=newTeacher
        return self.__teacher