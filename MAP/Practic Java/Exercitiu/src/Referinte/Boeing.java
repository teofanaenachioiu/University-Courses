package Referinte;

public class Boeing implements Comparable<Boeing> {
    int height;

    public Boeing(int height) {
        this.height = height;
    }

    @Override
    public int compareTo(Boeing o) {
        return this.height-o.height;
    }

    public int getHeight() {
        return height;
    }
}
