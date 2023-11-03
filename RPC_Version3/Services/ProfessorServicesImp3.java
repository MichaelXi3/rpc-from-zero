package RPC_Version3.Services;

import RPC_Version3.Common.Professor3;

public class ProfessorServicesImp3 implements ProfessorServices3 {
    @Override
    public Professor3 getProfessorById(Integer id) {
        System.out.println("[ProfessorServicesImp] Get Professor by id: " + id);
        // Simulation of database query
        Professor3 professor = new Professor3("Fulp (V3)", id, "Computer Science", true);
        return professor;
    }

    @Override
    public Professor3 promoteTenured(Professor3 professor) {
        professor.promote();
        System.out.println("[ProfessorServicesImp] Professor " + professor.getName() + " has been promoted \n");
        return professor;
    }
}