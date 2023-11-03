package RPC_Version2.Services;

import java.util.HashMap;
import java.util.Map;

/**
 *  This class abstracts the services lists that server side provides
 *  - provideService method: record new services
 *  - getService method: get service object by service name
 */

public class ServiceProvider2 {
    private Map<String, Object> servicesList;

    public ServiceProvider2() {
        this.servicesList = new HashMap<>();
    }

    public void provideService(Object service) {
        Class<?>[] interfaceNames = service.getClass().getInterfaces();
        for (Class interfaceName : interfaceNames) {
            servicesList.put(interfaceName.getName(), service);
        }
    }

    public Object getService(String serviceName) {
        return servicesList.get(serviceName);
    }
}
