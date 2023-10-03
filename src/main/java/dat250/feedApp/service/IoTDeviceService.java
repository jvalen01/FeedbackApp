package dat250.feedApp.service;

import dat250.feedApp.model.IoTDevice;
import dat250.feedApp.repository.IoTDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IoTDeviceService {

    @Autowired
    private IoTDeviceRepository ioTDeviceRepository;

    public List<IoTDevice> getAllIoTDevices() {
        return ioTDeviceRepository.findAll();
    }

    public Optional<IoTDevice> getIoTDeviceById(Long id) {
        return ioTDeviceRepository.findById(id);
    }

    public IoTDevice saveIoTDevice(IoTDevice ioTDevice) {
        return ioTDeviceRepository.save(ioTDevice);
    }

    public void deleteIoTDevice(Long id) {
        ioTDeviceRepository.deleteById(id);
    }
    public boolean existsById(Long id) {
        return ioTDeviceRepository.existsById(id);
    }
}


