package util;

public class LengthOfNumber {

    private Integer length;
    private Integer count;

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "LengthOfNumber{" +
                "length=" + length +
                ", count=" + count +
                '}';
    }
}
