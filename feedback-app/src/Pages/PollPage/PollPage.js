import React, { useState, useEffect } from 'react';
import {useParams} from "react-router-dom";

function PollPage() {
    const [pollData, setPollData] = useState(null);
    const { pollID } = useParams();

    useEffect(() => {
        const fetchPollData = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/polls/${pollID}`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (response.ok) {
                    const data = await response.json();
                    setPollData(data);
                } else {
                    // Handle error when the response is not OK
                    console.error('Failed to fetch poll data');
                }
            } catch (error) {
                console.error('Error fetching poll data:', error);
            }
        };

        fetchPollData();
    }, [pollID]);

    if (!pollData) {
        return <div>Loading...</div>;
    }

    // Render poll data
    return (
        <div>
            <h2>Poll name: {pollData.name}</h2>
            <h2>Created By: {pollData.user.username}</h2>
            <h2>Yes votes: {pollData.question.yesVotes}</h2>
            <h2>No votes: {pollData.question.noVotes}</h2>
            <h2>Total votes: {pollData.question.totalVotes}</h2>
            {/* Render other poll details here */}
        </div>
    );
}

export default PollPage;
