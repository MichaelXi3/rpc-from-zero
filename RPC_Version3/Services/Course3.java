package RPC_Version3.Services;

import java.io.Serializable;

public class Course3 implements Serializable {
    private String name;
    private Integer id;
    private Integer size;
    private Boolean restricted;

    public Course3() {
        this.name = "default";
        this.id = 1;
        this.size = 0;
        this.restricted = false;
    }

    public Course3(String name, Integer size, Integer id, Boolean restricted) {
        this.name = name;
        this.size = size;
        this.id = id;
        this.restricted = restricted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------\n");
        sb.append("course name: ").append(name).append("\n");
        sb.append("course id: ").append(id).append("\n");
        sb.append("course size: ").append(size).append("\n");
        sb.append("course restricted: ").append(restricted).append("\n");
        sb.append("---------------------------------");
        return sb.toString();
    }
}


