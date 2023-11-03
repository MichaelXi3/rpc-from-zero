package RPC_Version2.Services;

import RPC_Version2.Common.Professor2;

/**
 *  This class is visible to both client and server
 *  - Client calls functions defined in this interface
 *  - Server implements functions defined in this interface
 */

public interface ProfessorService2 {

    Professor2 getProfessorById(Integer id);

    Professor2 promoteTenured(Professor2 professor);
}
