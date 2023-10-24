import React, { useState } from 'react';
import Firebase from '../../firebaseConfig';

const firebaseInstance = new Firebase();

function CreatePoll() {
    const [pollName, setPollName] = useState('')
    const [pollQuestion, setPollQuestion] = useState('');


    const handleSubmit = async (e) => {
        e.preventDefault();

        // Get user's ID token
        const idToken = await firebaseInstance.auth.currentUser.getIdToken(true);

        // Send POST request to Spring Boot backend with poll details
        try {
            const response = await fetch('http://localhost:8080/api/polls', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${idToken}`  // Passing ID token in header
                },
                body: JSON.stringify({
                    name: pollName,
                    active: true,
                    accessMode: "PUBLIC",
                    question: pollQuestion

                    // ... add other poll details
                })
            });

            const responseData = await response.json();
            if (response.ok) {
                alert('Poll created successfully!');
                // Navigate to home or another relevant page after successful creation
            } else {
                alert('Error creating poll: ' + responseData.message);
            }
        } catch (error) {
            console.error("There was an error creating the poll", error);
        }
    };

    return (
        <div>
            <h2>Create Poll</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Poll Name: </label>
                    <input
                        value={pollName}
                        onChange={(e) => setPollName(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Poll Question: </label>
                    <input
                        value={pollQuestion}
                        onChange={(e) => setPollQuestion(e.target.value)}
                        required
                    />
                </div>
                {/* Add other input fields for additional poll details */}
                <button type="submit">Create</button>
            </form>
        </div>
    );
}

export default CreatePoll;
