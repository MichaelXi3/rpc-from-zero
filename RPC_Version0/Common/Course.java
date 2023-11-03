package RPC_Version0.Common;

import java.io.Serializable;

/**
 *  This Course class is visible to both client and server
 *  - Client needs to get this object data
 *  - Server needs to modify this object data
 */

// Java Serialization: for network transmission used in RPC
public class Course implements Serializable {
    private String name;
    private Integer id;
    private Integer size;
    private Boolean restricted;

    public Course() {
        this.name = "default";
        this.id = 1;
        this.size = 0;
        this.restricted = false;
    }

    public Course(String name, Integer size, Integer id, Boolean restricted) {
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
