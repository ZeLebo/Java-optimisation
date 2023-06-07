package task.work;

class MyClassComparable implements Comparable<MyClassComparable> {
    public String name;
    public int value;

    public MyClassComparable(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public int compareTo(MyClassComparable other) {
        return Integer.compare(this.value, other.value);
    }
}
