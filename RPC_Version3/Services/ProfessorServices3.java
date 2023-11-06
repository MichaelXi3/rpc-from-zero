package RPC_Version3.Services;

public interface ProfessorServices3 {

    Professor3 getProfessorById(Integer id);

    Professor3 promoteTenured(Professor3 professor);
}