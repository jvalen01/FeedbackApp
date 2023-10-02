package dat250.feedApp.dao;

import dat250.feedApp.IoTDevice;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IoTDeviceDAO extends AbstractDAO<IoTDevice, Long> {

    @Autowired
    public IoTDeviceDAO(EntityManager em) {
        super(em);
    }

    @Override
    protected Class<IoTDevice> getEntityClass() {
        return IoTDevice.class;
    }
}

