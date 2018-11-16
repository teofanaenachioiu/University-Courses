package sem3;

import java.util.*;

public class MyMap {
    Map<Integer, List<Student>> studentMap;


    public MyMap() {
        this.studentMap = new TreeMap<>(new ComparatorStudentMedie());
    }

    private class ComparatorStudentMedie implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }
    public void add(Student st){
        int medie = Math.round(st.getMedia());
        List<Student> list = studentMap.get(medie);
        if (list == null){
            list = new ArrayList<Student>();
            studentMap.put(medie, list);
        }
        list.add(st);

        }

    public Set<Map.Entry<Integer, List<Student>>> getEntries (){
        return studentMap.entrySet();
    }
}
