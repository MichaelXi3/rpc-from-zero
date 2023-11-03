package RPC_Version2.Common;

import java.io.Serializable;

public class Professor2 implements Serializable {
    private String name;
    private Integer id;
    private String department;
    private Boolean tenured;

    public Professor2() {
        this.name = "default";
        this.id = 1;
        this.department = "unknown";
        this.tenured = false;
    }

    public Professor2(String name, Integer id, String department, Boolean tenured) {
        this.name = name;
        this.id = id;
        this.department = department;
        this.tenured = tenured;
    }

    public void promote() {
        if (tenured) {
            return;
        }
        tenured = true;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------\n");
        sb.append("Professor name: ").append(name).append("\n");
        sb.append("Professor id: ").append(id).append("\n");
        sb.append("Department: ").append(department).append("\n");
        sb.append("Tenured: ").append(tenured).append("\n");
        sb.append("---------------------------------");
        return sb.toString();
    }
}

