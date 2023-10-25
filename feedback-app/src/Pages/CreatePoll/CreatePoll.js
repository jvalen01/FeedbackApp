import React, { useState } from 'react';
import Firebase from '../../firebaseConfig';
import './CreatePoll.css';
import { Link, useNavigate } from "react-router-dom"; // Import useHistory

const firebaseInstance = new Firebase();

function CreatePoll() {
    const [pollName, setPollName] = useState('')
    const [pollQuestion, setPollQuestion] = useState('');
    const [pollAccessMode, setPollAccessMode] = useState('');

    const navigate = useNavigate(); // Use the useHistory hook

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
                    accessMode: pollAccessMode,
                    question: pollQuestion
                    // ... add other poll details
                })
            });

            const responseData = await response.json();
            if (response.ok) {
                alert('Poll created successfully!');

                // Navigate to home after successful creation
                navigate("/home");

            } else {
                alert('Error creating poll: ' + responseData.message);
            }
        } catch (error) {
            console.error("There was an error creating the poll", error);
        }
    };

    return (
        <div className="container">
            <h2 className="title">Create A New Poll</h2>
            <form className="form" onSubmit={handleSubmit}>
                <div className="input-group">
                    <label className="label">Poll Name:</label>
                    <input
                        className="input"
                        value={pollName}
                        onChange={(e) => setPollName(e.target.value)}
                        required
                    />
                </div>
                <div className="input-group">
                    <label className="label">Poll Question:</label>
                    <input
                        className="input"
                        value={pollQuestion}
                        onChange={(e) => setPollQuestion(e.target.value)}
                        required
                    />
                </div>
                <div className="input-group">
                    <label className="label">Access Mode:</label>
                    <div>
                        <label className="radio-label">
                            <input
                                type="radio"
                                value="public"
                                checked={pollAccessMode === 'public'}
                                onChange={() => setPollAccessMode('public')}
                            />
                            Public
                        </label>
                        <label className="radio-label">
                            <input
                                type="radio"
                                value="private"
                                checked={pollAccessMode === 'private'}
                                onChange={() => setPollAccessMode('private')}
                            />
                            Private
                        </label>
                    </div>
                </div>
                {/* Add other input fields for additional poll details */}
                <button className="submit-button" type="submit">Create</button>
                <Link to="/home">
                    <button className="home-button" type="button">Home</button>
                </Link>
            </form>
        </div>
    );
}

export default CreatePoll;
