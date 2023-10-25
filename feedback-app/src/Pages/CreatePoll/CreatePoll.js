import React, { useState } from 'react';
import Firebase from '../../firebaseConfig';
import './CreatePoll.css';
import {Link, useNavigate} from "react-router-dom";

const firebaseInstance = new Firebase();

function CreatePoll() {
    const [pollName, setPollName] = useState('')
    const [pollQuestionText, setPollQuestionText] = useState('');
    const [pollAccessMode, setPollAccessMode] = useState('public');

    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Get user's ID token
        const idToken = await firebaseInstance.auth.currentUser.getIdToken(true);

        // Create the question object
        const questionObject = {
            question: pollQuestionText, // Use the question text input
            yesVotes: 0,
            noVotes: 0,
            totalVotes: 0,
        };

        // Send a POST request to create the question
        const questionResponse = await fetch('http://localhost:8080/api/questions', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${idToken}`
            },
            body: JSON.stringify(questionObject)
        });

        const questionData = await questionResponse.json();
        if (!questionResponse.ok) {
            console.error('Error creating question:', questionData.message);
            return;
        }

        // Now, you have the ID of the newly created question
        const questionId = questionData.id;



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
                    question: { id: questionId } // Pass the question ID


                    // ... add other poll details
                })
            });

            const responseData = await response.json();
            console.log("creating poll data: ", responseData);
            if (response.ok) {
                alert('Poll created successfully!');
                navigate('/home')
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
                        value={pollQuestionText}
                        onChange={(e) => setPollQuestionText(e.target.value)}
                        required
                    />
                </div>
                <div className="input-group">
                    <label className="label">Access Mode:</label>
                    <div className="radio-group">
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
