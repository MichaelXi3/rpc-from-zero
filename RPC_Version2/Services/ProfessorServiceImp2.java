package RPC_Version2.Services;

import RPC_Version2.Common.Professor2;

public class ProfessorServiceImp2 implements ProfessorService2 {
    @Override
    public Professor2 getProfessorById(Integer id) {
        System.out.println("Client requests Professor with id: " + id);
        // Simulation of database query
        Professor2 professor = new Professor2("Fulp (V2)", id, "Computer Science", true);
        return professor;
    }

    @Override
    public Professor2 promoteTenured(Professor2 professor) {
        professor.promote();
        System.out.println("Professor " + professor.getName() + " has been promoted \n");
        return professor;
    }
}
