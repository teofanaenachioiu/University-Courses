package sem3;

import java.util.*;

public class Sem3Test {
    public static void main(String[] args) {
        Student  s1= new    Student("Dan", 4.5f);
        Student  s2= new    Student("Zana", 8.5f);
        Student  s3= new    Student("Aprogramatoarei", 6.5f);
       // Student  s3= new    Student("Dan", 4.5f);
//        System.out.println(s1.equals(s3));
//        Set<Student> studentSet=new HashSet<Student>();
//        studentSet.add(s1);
//        studentSet.add(s2);
//        studentSet.add(s3);
//        for (Student s :studentSet
//             ) {
//            System.out.println(s);
//
//        }

//        Set<Student> studentTreeSet = new TreeSet<Student>();
//////        studentTreeSet.add(s1);
//////        studentTreeSet.add(s2);
//////        studentTreeSet.add(s3);
//////        for (Student s:studentTreeSet) {
//////            System.out.println(s);
//////        }
       Set<Student> studentTreeSet = new TreeSet<>(new Comparator<Student>() {
           @Override
           public int compare(Student o1, Student o2) {
               return (int)(o2.getMedia()-o1.getMedia());
           }
       });
        studentTreeSet.add(s1);
        studentTreeSet.add(s2);
        studentTreeSet.add(s3);
//        for (Student s:studentTreeSet) {
////            System.out.println(s);
////        }

        TreeMap<String,Student> studentTreeMap = new TreeMap<>();

        studentTreeMap.put(s1.getNume(),s1);
        studentTreeMap.put(s2.getNume(),s2);
        studentTreeMap.put(s3.getNume(),s3);

//        for (Map.Entry<String,Student> e:studentTreeMap.entrySet()) {
//            System.out.println(e.getValue());
//        }
        List<Student> l = getList();
        MyMap map = new MyMap();
        for (Student s : l
             ) {
            map.add(s);
        }
        for (Map.Entry<Integer, List<Student>> e: map.getEntries()
             ) {
            System.out.println("Studentii cu media:" + e.getKey() + " sunt");
            for (Student s: e.getValue()
                 ) {
                System.out.println(s);
            }
        }
    }
    public static List<Student> getList(){
        List<Student> l=new ArrayList<Student>();
        l.add(new Student("1",9.7f));
        l.add(new Student("2",7.3f));
        l.add(new Student("3",6f));
        l.add(new Student("4",6.9f));
        l.add(new Student("5",9.5f));
        l.add(new Student("6",9.9f));
        return l;
    }

}
