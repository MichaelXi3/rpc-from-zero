package RPC_Version3.Services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 *  This class abstracts the services lists that server side provides
 *  - provideService method: record new services
 */

@AllArgsConstructor
@Getter
public class ServiceProvider3 {
    private Map<String, Object> servicesList;

    public ServiceProvider3() {
        this.servicesList = new HashMap<>();
    }

    public void provideService(Object service) {
        Class<?>[] interfaceNames = service.getClass().getInterfaces();
        for (Class interfaceName : interfaceNames) {
            servicesList.put(interfaceName.getName(), service);
        }
    }

    public Object getServiceByName(String serviceName) {
        return servicesList.get(serviceName);
    }
}
