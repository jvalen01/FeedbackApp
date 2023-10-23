package dat250.feedApp.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import dat250.feedApp.model.User;
import dat250.feedApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users"; // This is the name of the view (template)
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestHeader("Authorization") String idToken) {
        try {
            // Verify the ID token
            String actualToken = idToken.startsWith("Bearer ") ? idToken.substring(7) : idToken;
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(actualToken);
            String uid = decodedToken.getUid();
            String userEmail = decodedToken.getEmail();

            // Check if a user with this UID exists in your DB
            User user = userService.findByFirebaseUID(uid).orElse(null);
            if (user == null) {
                // If not, create a new user entry in your DB with this UID and email
                user = new User();
                user.setFirebaseUID(uid);
                user.setUsername(userEmail);  // Assuming username is an email in this context
                userService.save(user);
            }

            // For this example, I'm just returning a success message.
            return ResponseEntity.ok(Map.of("success", true, "message", "Successfully registered or updated!"));

        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Invalid token"));
        }
    }


    // GET a single user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST (create) a user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    // PUT (update) a user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        if (userService.existsById(id)) {
            updatedUser.setId(id);  // Ensure the ID is set to the one from the path
            return ResponseEntity.ok(userService.save(updatedUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.existsById(id)) {
            userService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
