public class DataBuffer {
    private int data;

    /**
     * 无参构造器
     */
    public DataBuffer() {

    }

    /**
     * 构造器
     * @param data
     */
    public DataBuffer(int data) {
        this.data = data;
    }

    /**
     * gtter,setter方法
     * @return
     */
    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
