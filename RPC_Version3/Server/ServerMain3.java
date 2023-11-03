package RPC_Version3.Server;


import RPC_Version3.Services.*;

public class ServerMain3 {
    public static void main(String[] args) {
        // service objects
        CourseServices3 courseServices = new CourseServicesImp3();
        ProfessorServices3 professorServices = new ProfessorServicesImp3();

        // add to serviceProvider object
        ServiceProvider3 serviceProvider = new ServiceProvider3();
        serviceProvider.provideService(courseServices);
        serviceProvider.provideService(professorServices);

        // initialize rpc server
        RPCServer3 server = new NettyRPCServer3(serviceProvider);
        server.start(8080);
    }

}
