'''
Created on 20 nov. 2017

@author: TEO
'''
class Note():
    
    def __init__(self, st, sub, note):
        self.__st=st
        self.__sub=sub
        self.__note=note
    
    def getStudent(self):
        """
        Return student
        """
        return self.__st
    
    def getSubject(self):
        """
        Return the subject 
        """
        return self.__sub
    
    def getNote(self):
        """
        Return the note
        """
        return self.__note
    
    def __eq__(self,ot):
        """
        Set when two objects note are equals.
        """
        return self.getStudent().getID()==ot.getStudent().getID() and self.getSubject().getID()==ot.getSubject().getID()


class Media():
    
    def __init__(self,idStudent,nameStudent):
        self.__idStudent=idStudent
        self.__nameStudent=nameStudent
        self.__suma=0
        self.__nr=0
        self.__media=0
        self.__min=11
        
    def getIdStudent(self):
        """
        Get student id
        """
        return self.__idStudent
    
    def getNameStudent(self):
        """
        Get student name
        """
        return self.__nameStudent
    
    def getSuma(self):
        """
        Get sum of grades
        """
        return self.__suma
    
    def getNr(self):
        """
        Get number of grades
        """
        return self.__nr
    
    def getMedia(self):
        """
        Get the average of grades
        """
        self.__media=self.getSuma()/self.getNr()
        return float("%.2f"%self.__media)
    
    def addNote(self,note):
        """
        Add a note. It will be added to sum of grades.
        :param note: integer
        :return: sum of notes
        """
        self.__suma+=note
        self.__nr+=1
        if self.__min>note:
            self.__min=note
        return self.__suma
    
    def getMin(self):
        return self.__min
  
class NoteDTO():
    def __init__(self, idStudent, idSubject, note):
        self.__idStudent=idStudent
        self.__idSubject=idSubject
        self.__note=note
    
    def getIdStudent(self):
        """
        Return id student
        """
        return self.__idStudent
    
    def getIdSubject(self):
        """
        Return the id subject 
        """
        return self.__idSubject
    
    def getNote(self):
        """
        Return the note
        """
        return self.__note
    
    def __eq__(self,ot):
        """
        Set when two objects noteDTO are equals.
        """
        return self.getIdStudent()==ot.getIdStudent() and self.getIdSubject()==ot.getIdSubject()