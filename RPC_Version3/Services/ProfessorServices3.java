package RPC_Version3.Services;

import RPC_Version3.Common.Professor3;

public interface ProfessorServices3 {

    Professor3 getProfessorById(Integer id);

    Professor3 promoteTenured(Professor3 professor);
}