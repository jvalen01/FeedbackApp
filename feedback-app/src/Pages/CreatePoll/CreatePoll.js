import React, { useState } from 'react';
import Firebase from '../../firebaseConfig';
import './CreatePoll.css';
import {Link, useNavigate} from "react-router-dom";
import "../../styles/components.css"
import Button from "../../Components/Button";
import HomeButton from "../../Components/HomeButton";
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
            totalVotes: 0
        };

        const pollObject = {
            name: pollName,
            active: true,
            accessMode: pollAccessMode,
            question: questionObject
        }

        // Send POST request to Spring Boot backend with poll details
        try {
            const response = await fetch('http://localhost:8080/api/polls', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${idToken}`  // Passing ID token in header
                },
                body: JSON.stringify(pollObject)
            });

            const responseData = await response.json();
            console.log("creating poll data: ", responseData);
            if (response.ok) {
                navigate('/home')
            } else {
                alert('Error creating poll: ' + responseData.message);
            }
        } catch (error) {
            console.error("There was an error creating the poll", error);
        }


        fetch('http://localhost:8080/api/sendToDweet', {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
              },
              body: JSON.stringify(pollObject),
            })
              .then(response => response.json())
              .then(data => {
                console.log('Data sent to Dweet via proxy:', data);
              })
              .catch(error => {
                console.error('Error sending data to Dweet via proxy:', error);
              });

    };










    return (
<div className="welcome-background min-h-[80vh] flex items-center justify-center bg-gray-200"> 
        <div className="beigeBox md:w-1/2 lg:w-1/3 xl:w-1/4 p-4 md:p-6 lg:p-8 rounded-xl shadow-lg flex flex-col justify-center items-center">
           <HomeButton />
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
                <div className="flex items-center justify-center space-x-4 mt-4">
                <Button type="submit" text="Create" />
            </div>
            </form>
            </div>
        </div>
    );
}

export default CreatePoll;