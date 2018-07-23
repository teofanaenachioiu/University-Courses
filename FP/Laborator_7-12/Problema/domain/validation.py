'''
Created on 11 nov. 2017

@author: TEO
'''
class IdNotFound(Exception):
    """
    Return hidden message of error IdError. (Can't find the id)
    """
    def __init__(self):
        self.__error="ID not found"
    def getErrors(self):
        return self.__error


class IdError(Exception):
    """
    Return hidden message of error IdError (Id assigned)
    """
    def __init__(self):
        self.__error="ID is assigned"
    def getErrors(self):
        return self.__error


class NoteError(Exception):
    """
    Return hidden message of error NoteError (Grade assigned)
    """
    def __init__(self):
        self.__error="Grade already assigned"
    def getErrors(self):
        return self.__error
    

class InputError(Exception):
    """
    Return hidden messages of error InputError (Errors for input data)
    """
    def __init__(self,errors):
        self.__errors=errors
        
    def getErrors(self):
        return self.__errors
    
class RepositoryError(Exception):
    """
    Return hidden message of error RepositoryError
    """
    def __init__(self):
        self.__errors="Unable to read from file"
        
    def getErrors(self):
        return self.__errors

class DuplicateDataError(Exception):
    """
    Return hidden message of error DuplicateDataError (Can't put the object in file.)
    """   
    def __init__(self):
        self.__errors="Duplicate data. Can't load it in file."
        
    def getErrors(self):
        return self.__errors
    
class StudentValidator():
    """
    Validates input data for a student
    """    
    def validate(self, st):
        """
        Throw InputError if data is entered incorrectly.
        """
        errors = []
        if st.getID()=="": 
            errors.append("ID can not be empty!")
        if st.getID()[:2].isalpha() ==False or st.getID()[2:].isdigit() ==False or len(st.getID())!=6 :
            errors.append("ID format is incorrect! Please follow the template: llcccc") 
        if st.getName()=="": 
            errors.append("Name can not be empty!")
        if not st.getName().isalpha():
            errors.append("Name can contain only letters!")
        if len(errors)>0:
            raise InputError(errors)
    

class SubjectValidator():
    """
    Validates input data for a subject.
    """    
    def validate(self, sub):
        """
        Throw InputError if data is entered incorrectly.
        """
        errors = []
        if sub.getID()=="": 
            errors.append("ID can not be empty!")
        if sub.getID()[0].isalpha() ==False or sub.getID()[1:4].isdigit() ==False or len(sub.getID())!=4 :
            errors.append("ID format is incorrect! Please follow the template: lccc") 
        if sub.getName()=="": 
            errors.append("Subject name can not be empty!")
        if sub.getTeacher()=="": 
            errors.append("Teacher's name can not be empty!")
        if not sub.getTeacher().isalpha:
            errors.append("Teacher's name can contain only letters!")
        if len(errors)>0:
            raise InputError(errors)


class NoteValidator():
    """
    Validate input data for a note
    """
    def validate(self,mark):
        errors=[]
        if mark.getNote()<1 or mark.getNote()>10:
            errors.append("The note has to be between 1 and 10!")
        if len(errors)>0:
            raise InputError(errors)