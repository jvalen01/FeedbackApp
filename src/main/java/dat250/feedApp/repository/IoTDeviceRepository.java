package dat250.feedApp.repository;

import dat250.feedApp.model.IoTDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IoTDeviceRepository extends JpaRepository<IoTDevice, Long> {
    // Any additional custom methods, if required, can be defined here
}


