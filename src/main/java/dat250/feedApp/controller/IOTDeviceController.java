package dat250.feedApp.controller;

import dat250.feedApp.model.IoTDevice;
import dat250.feedApp.service.IoTDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/iotdevices")
public class IOTDeviceController {

    @Autowired
    private IoTDeviceService iotDeviceService;

    // GET all IOTDevices
    @GetMapping
    public List<IoTDevice> getAllIOTDevices() {
        return iotDeviceService.getAllIoTDevices();
    }

    // GET a single IOTDevice by ID
    @GetMapping("/{id}")
    public ResponseEntity<IoTDevice> getIOTDeviceById(@PathVariable Long id) {
        return iotDeviceService.getIoTDeviceById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST (create) an IOTDevice
    @PostMapping
    public IoTDevice createIOTDevice(@RequestBody IoTDevice iotDevice) {
        return iotDeviceService.saveIoTDevice(iotDevice);
    }

    // PUT (update) an IOTDevice
    @PutMapping("/{id}")
    public ResponseEntity<IoTDevice> updateIOTDevice(@PathVariable Long id, @RequestBody IoTDevice updatedIOTDevice) {
        if (iotDeviceService.existsById(id)) {
            updatedIOTDevice.setId(id); // Ensure the ID is set to the one from the path
            return ResponseEntity.ok(iotDeviceService.saveIoTDevice(updatedIOTDevice));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE an IOTDevice
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIOTDevice(@PathVariable Long id) {
        if (iotDeviceService.existsById(id)) {
            iotDeviceService.deleteIoTDevice(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


