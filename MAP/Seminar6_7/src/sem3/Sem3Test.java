package sem3;

import java.util.*;

public class Sem3Test {
    public static List<Student> getList() {
        List<Student> l = new ArrayList<Student>();
        l.add(new Student("1", 9.7f));
        l.add(new Student("2", 7.3f));
        l.add(new Student("3", 6f));
        l.add(new Student("4", 6.9f));
        l.add(new Student("5", 9.5f));
        l.add(new Student("6", 9.9f));
        return l;
    }
        public static void main(String[] args) {
            Student  s1= new    Student("Dan", 4.5f);
            Student  s2= new    Student("Ana", 8.5f);
            Student  s3= new    Student("Dan", 4.5f);
            System.out.println(s1.equals(s3));
//            Set<Student> students=new HashSet<>();
//            students.add(s1);
//            students.add(s2);
//            students.add(s3);
//            for (Student s: students) {
//                System.out.println(s);
//            }

//            Set<Student> students2=new TreeSet<>();
//            students2.add(s1);
//            students2.add(s2);
//            students2.add(s3);
//            for (Student s: students2) {
//                System.out.println(s);
//            }

//            Set<Student> students3=new TreeSet<>(new Comparator<Student>() {
//                @Override
//                public int compare(Student o1, Student o2) {
//                    //return o1.nume.compareTo(o2.getNume());
//                    return -(int)(o1.getMedia() - o2.getMedia());
//                }
//            });
//            students3.add(s1);
//            students3.add(s2);
//            students3.add(s3);
//            for (Student s: students3) {
//                System.out.println(s);
//            }
//            Map<String,Student> studentMap = new TreeMap<>();
////            studentMap.put(s1.getNume(),s1);
////            studentMap.put(s2.getNume(),s2);
////            studentMap.put(s3.getNume(),s3);
////            for (Map.Entry<String,Student> s:studentMap.entrySet()) {
////                System.out.println(s);
////            }
////
////            List<Student> l=getList();
////            Collections.sort(l);
////            l.sort(new Comparator<Student>() {
////                @Override
////                public int compare(Student o1, Student o2) {
////                    return 0;
////                }
////            });

            MyMap map = new MyMap();
            List<Student> l = getList();
            for (Student s : l
                 ) {
                map.addStudent(s);
            }

            for (Map.Entry<Integer, List<Student>> studentListEntry: map.getEntries()
                 ) {

                System.out.println("Studentii cu media : " + studentListEntry.getKey());

                for (Student s: studentListEntry.getValue()
                     ) {
                    System.out.println(s);
                }

            }
    }
}
