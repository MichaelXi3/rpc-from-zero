package RPC_Version2.Server;

import RPC_Version2.Services.*;

public class RPCServerMain2 {
    public static void main(String[] args) {
        // register server side services
        ServiceProvider2 serviceProvider = new ServiceProvider2();
        CourseService2 courseService = new CourseServiceImp2();
        ProfessorService2 professorService = new ProfessorServiceImp2();

        serviceProvider.provideService(courseService);
        serviceProvider.provideService(professorService);

        // create rpc server, there are two implementations available
        RPCServerInterface2 rpcServer = new RPCServerThreadPool2(serviceProvider);
        rpcServer.start(8080);
    }
}
