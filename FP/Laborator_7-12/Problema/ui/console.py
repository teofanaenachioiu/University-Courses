'''
Created on 9 nov. 2017

@author: TEO
'''
from domain.validation import InputError,IdError,IdNotFound, NoteError,DuplicateDataError,RepositoryError
from Sorting.Sortare import bingoSort, mergeSort

class Consola():
    
    def __init__(self,stServ,subServ,grServ):
        self.__lista=stServ
        self.__table=subServ
        self.__notes=grServ
    
    def __callAddStudent(self):
        """
        Add a new student. If the entered data is correct or ID is not assigned, raise InputError/IdError.
        """
        idSubject=input("  Give ID:")
        name=input("  Give name:")   
        try:
            st=self.__lista.createStudent(idSubject, name)
            self.__lista.addStudent(st)
            print("Student "+st.getName()+" has been successfully added.")
        except InputError as ex:
            print(ex.getErrors())
        except  IdError as ex:
            print(ex.getErrors())
        except DuplicateDataError as ex:
            print(ex.getErrors())
        except RepositoryError() as ex:
            print(ex.getErrors())
           
    
    def __callUpdateStudent(self): 
        """
        Update student's name if he is in the list. Otherwise, throw IdNotFound Error.
        """ 
        idGiven=input("  Give student's ID:")
        newName=input("  Give a new name:")  
        try:
            self.__lista.createStudent(idGiven, newName)
            st=self.__lista.updateStudent(idGiven, newName)
            self.__notes.updateStudentNote(st, newName)
            print("Student's name has been successfully changed.")
        except InputError as ex:
            print(ex.getErrors())
        except IdNotFound as ex:
            print(ex.getErrors())
        except RepositoryError() as ex:
            print(ex.getErrors())
            
            
    def __callDelStudent(self):
        """
        Delete a student from list of students. Otherwise, throw IdNotFound Error.
        """
        idGiven=input("  Give student's ID:")
        try:
            st=self.__lista.delStudent(idGiven)
            self.__notes.delStudentNote(st)
            print("Student " +st.getName() +" has been removed from the catalog.")
        except IdNotFound as ex:
            print(ex.getErrors())
        except RepositoryError() as ex:
            print(ex.getErrors())
            
    
    def __callSearchStudent(self):
        """
        Search a student after a given id. If ID doesn't exist, throw IdNotFound Error.
        """
        idGiven=input("  Give student's ID:")
        try:
            st=self.__lista.searchStudent(idGiven)
            print("Student with this ID is: "+st.getName())
        except IdNotFound as ex:
            print(ex.getErrors())
        except RepositoryError() as ex:
            print(ex.getErrors())
    
                    
    def __callShowStundets(self):
        """
        View (print) all student from the catalog. If there's no student, a message will be displayed on screen. 
        """
        sts = self.__lista.showStudents()
        if len(sts) == 0:
            print("There's no students in the catalog.")
        else:
            print("*List of students*")
            for st in sts:
                print(st.getID()+" | "+st.getName())
            
        
    
    def __callUndoStudent(self):
        """
        Undo function for list of students
        """
        sts=self.__lista.undoStudents()
        if len(sts)>0:
            print("Command successfully performed.")
        else:
            print("Finish!")
    
    
    def __callRandomStudents(self):
        """
        Put random students in student list.
        """
        while True:
            n=int(input("n="))
            if n<=12:
                break
        i=0
        while i<n:
            try:
                st=self.__lista.randomStudent()
                st=self.__lista.addStudent(st)
                print("Student "+st.getName()+" with id " +st.getID()+" has been successfully added.")
                i=i+1
            except InputError:
                continue
            except  IdError:
                continue
    
    def __callAddSubject(self):
        """
        Add a new subject. If the entered data is correct or ID is not assigned, raise InputError/IdError.
        """
        idSubject=input("  Give ID:")
        name=input("  Give subject name:")
        teacher=input("  Give teacher's name:")   
        try:
            sub=self.__table.createSubject(idSubject,name,teacher)
            self.__table.addSubject(sub)
            print("Subject "+sub.getName()+" has been successfully added.")
        except InputError as ex:
            print(ex.getErrors())
        except  IdError as ex:
            print(ex.getErrors())
        
    
    def __callUpdateSubjectName(self): 
        """
        Update subject name if it's in the list. Otherwise, throw IdNotFound Error.
        """ 
        idGiven=input("  Give ID:")
        newName=input("  Give a new name:")  
        try:
            self.__table.createSubject(idGiven, newName, "teacher")
            self.__table.updateSubjectName(idGiven, newName)
            print("Subject name has been successfully changed.")
        except InputError as ex:
            print(ex.getErrors())
        except IdNotFound as ex:
            print(ex.getErrors())
     
     
    def __callUpdateSubjectTeacher(self): 
        """
        Update teacher's name if it's in the list. Otherwise, throw IdNotFound Error.
        """ 
        idGiven=input("  Give ID:")
        newTeacher=input("  Give a new teacher:")  
        try:
            self.__table.createSubject(idGiven, "subject", newTeacher)
            self.__table.updateSubjectTeacher(idGiven, newTeacher)
            print("Teacher's name has been successfully changed.")
        except InputError as ex:
            print(ex.getErrors())
        except IdNotFound as ex:
            print(ex.getErrors())        
           
            
    def __callDelSubject(self):
        """
        Delete a subject from list of subjects. Otherwise, throw IdNotFound Error.
        """
        idGiven=input("  Give ID:")
        try:
            sub=self.__table.delSubject(idGiven)
            print("Subject " +sub.getName() +" has been removed.")
            self.__notes.delSubjectNote(sub)
        except IdNotFound as ex:
            print(ex.getErrors())
            
    
    def __callSearchSubject(self):
        """
        Search subject after a given id. If ID doesn't exist, throw IdNotFound Error.
        """
        idGiven=input("  Give ID:")
        try:
            sub=self.__table.searchSubject(idGiven)
            print("Subject with this ID is: "+sub.getName())
        except IdNotFound as ex:
            print(ex.getErrors())
    
                    
    def __callShowSubjects(self):
        """
        View all subjects. If there's no subjects, a message will be displayed on screen. 
        """
        subs = self.__table.showSubjects()
        if len(subs) == 0:
            print("There's no subjects.")
        else:
            print("*List of subjects*")
            for sub in subs:
                print(sub.getID()+" | "+sub.getName()+ " : "+ sub.getTeacher())
            
   
    
    def __callRandomSubjects(self):
        """
        Put random subject in subjects list.
        """
        while True:
            n=int(input("n="))
            if n<=10:
                break
        i=0
        while i<n:
            try:
                sub=self.__table.randomSubject()
                sub=self.__table.addSubject(sub)
                print("Subject "+sub.getName()+" with id " +sub.getID()+" and teacher "+sub.getTeacher()+" has been successfully added.")
                i=i+1
            except InputError:
                continue
            except  IdError:
                continue
      
    def __callUndoSubject(self):
        """
        Undo function for list of Subjects
        """
        subs=self.__table.undoSubject()
        if len(subs)>0:
            print("Command successfully performed")
        else: 
            print("Finish!")      
         
    def __callAssignNote(self):
        """
        Assign a note to a student.
        """
        try:
            idStudent=input("  Give id student:")
            st=self.__lista.searchStudent(idStudent) 
            idSubject=input("  Give id subject:")        
            sub=self.__table.searchSubject(idSubject)
            note=int(input("  Give note:"))
            mark=self.__notes.createNote(st, sub, note)  
            self.__notes.addNote(mark)
            print("Student "+st.getName()+ " get "+str(note)+ " at " + sub.getName()+ ".")
        except InputError as ex:
            print(ex.getErrors())
        except IdNotFound as ex:
            print(ex.getErrors())
        except NoteError as ex:
            print(ex.getErrors())
        
        
    def __callShowAlpha(self):
        """
        Show student list and their notes at a given subject  - alphabetically
        """  
        idSubject=input("  Give id subject:")        
        try:
            sub=self.__table.searchSubject(idSubject)
            marks=self.__notes.getSubNote(sub)
            if len(marks)>0:
                newlist = bingoSort(marks, key=lambda k: k['name']) 
                for el in newlist:
                    print(el['name'] + " - " + str(el['note']))      
            else:
                print("No students.")    
        except IdNotFound as ex:
            print(ex.getErrors())
                   
        
    def __callShowNote(self):
        """
        Show student list and their notes at a given subject  - from lowest to highest note
        """  
        idSubject=input("  Give id subject:")        
        try:
            sub=self.__table.searchSubject(idSubject)
            marks=self.__notes.getSubNote(sub)
            if len(marks)>0:
                newlist = mergeSort(marks, key=lambda k: k['note']) 
                for el in newlist:
                    print(el['name'] + " - " + str(el['note']))      
            else:
                print("No students.") 
        except IdNotFound as ex:
            print(ex.getErrors())
    
    
    def __noulRaport(self):
        medii=self.__notes.medieStudent()
        if len(medii)>0:
            nrSts=int(len(medii)/5+1)
            medii=medii[0:nrSts]
            for grade in medii:
                print(grade.getNameStudent()+" - "+str(grade.getMedia()))
        else:
            print("No students.") 
    
    
    def __callLastStudents(self):
        grades=self.__notes.getLastStudents()
        if len(grades)>0:
            nrSts=int(len(grades)/5+1)
            grades=grades[0:nrSts]
            for grade in grades:
                print(grade.getNameStudent()+" - "+str(grade.getMin()))
        else:
            print("No students.")
    
            
    def __menu(self):
        print("-----------------------------------")
        print("| a | Management list of students |")
        print("| b | Management list of subjects |")
        print("| c | Assign notes                |")
        print("| d | Reports                     |")
        print("| x | Exit                        |")  
        print("-----------------------------------")
        
        
    def __submenuStudents(self):
        print("1: Add student")
        print("2: Change the name for a student")
        print("3: Delete student")    
        print("4: Search student")
        print("5: View all students")
        print("u: Undo")
        print("x: Exit")
        print("r: Random students")
        
        
    def __submenuSubjects(self):
        print("1: Add subject")
        print("2: Change the name for a subject")
        print("3: Change the teacher for a subject")
        print("4: Delete subject")
        print("5: Search subject") 
        print("6: View all subjects")
        print("u: Undo")
        print("x: Exit") 
        print("r: Random subjects")   
        
                 
    def __submenuNote(self):
        print("1: Assign note")
        print("x: Exit") 
        
         
    def __submenuReport(self):
        print("1: Show students and notes at a given subject - alphabetically")
        print("2: Show students and notes at a given subject - from lowest to highest note ")
        print("3: Show first 20% students - by average of notes")
        print("4: Show last 20% students which have the smallest note")
        print("x: Exit")
                     
                     
    def startUI(self):
        """
        Create a graphical interface.
        """
        self.__menu()
        while True:
            order=input("Give the main command: ")
            if order in {'a','b','c','d','x','A','B','C','D','x'}:
                if order in {'x','X'}:
                    print("Thank you for using the application!")
                    break
                if order in {'a','A'}:
                    self.__submenuStudents()
                    while True:
                        cmd=input(" Give command: ")
                        if cmd in {'1','2','3','4','5','u','x','r'}:
                            if cmd=='1':
                                self.__callAddStudent()
                            if cmd=='2':
                                self.__callUpdateStudent()
                            if cmd=='3':
                                self.__callDelStudent()    
                            if cmd=='4':
                                self.__callSearchStudent()    
                            if cmd=='5':
                                self.__callShowStundets()    
                            if cmd=='u':
                                self.__callUndoStudent()    
                            if cmd=='x':
                                break
                            if cmd=='r':
                                self.__callRandomStudents()
                        else:
                            print(" Wrong command!")
                if order in {'b','B'}:
                    self.__submenuSubjects()
                    while True:
                        cmd=input(" Give command: ")
                        if cmd in {'1','2','3','4','5','6','u','x','r'}:    
                            if cmd=='1':
                                self.__callAddSubject()
                            if cmd=='2':
                                self.__callUpdateSubjectName()   
                            if cmd=='3':
                                self.__callUpdateSubjectTeacher()
                            if cmd=='4':
                                self.__callDelSubject()
                            if cmd=='5':
                                self.__callSearchSubject()
                            if cmd=='6':
                                self.__callShowSubjects()
                            if cmd=='u':
                                self.__callUndoSubject()
                            if cmd=='x':
                                break
                            if cmd=='r':
                                self.__callRandomSubjects()
                        else:
                            print(" Wrong command!")
                if order in {'c','C'}:
                    self.__submenuNote()
                    while True:
                        cmd=input(" Give command: ")
                        if cmd in {'1','x'}:
                            if cmd=='1':
                                self.__callAssignNote()
                            if cmd=='x':
                                break  
                        else:
                            print(" Wrong command!") 
                if order in {'d','D'}:
                    self.__submenuReport()
                    while True:
                        cmd=input(" Give command: ")
                        if cmd in {'1','2','3','4','x'}:
                            if cmd=='1':
                                self.__callShowAlpha()
                            if cmd=='2':   
                                self.__callShowNote()
                            if cmd=='3':
                                #self.__call20perc()
                                self.__noulRaport()
                            if cmd=='4':
                                self.__callLastStudents()
                            if cmd=='x':
                                break 
                        else:
                            print(" Wrong command!")   
            else:
                print("Wrong command!")