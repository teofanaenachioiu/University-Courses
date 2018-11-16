package sem3;

import java.util.*;

public class MyMap {
    private TreeMap<Integer, List<Student>> map;
    public MyMap (){
        map=new TreeMap<Integer,List<Student>>(new ComparatorStudentMedie());

    }
    public class ComparatorStudentMedie implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2-o1;
        }
    }
    public void addStudent(Student s)
    {
        int media=Math.round(s.getMedia());
        List<Student> list = map.get(media);
        if (list == null)
        {
            list=new ArrayList<Student>();
            map.put(media,list);
        }
        list.add(s);
    }

    public Set<Map.Entry<Integer, List<Student>>> getEntries() {
        return  map.entrySet();
    }
}
