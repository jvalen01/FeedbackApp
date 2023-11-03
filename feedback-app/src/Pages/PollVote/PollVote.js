import React, { useState, useEffect } from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import axios from 'axios';

function PollVote(props) {
    const { code } = useParams();
    const [pollData, setPollData] = useState(null);
    const [answer, setAnswer] = useState(''); // state to hold the answer input value
    const navigate = useNavigate();

    useEffect(() => {
        axios.get(`http://localhost:8080/api/polls/code/${code}`)
            .then(response => {
                setPollData(response.data);
            })
            .catch(error => {
                console.error("Error fetching poll:", error);
            });
    }, [code]);

    // Handle the form submission
    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("Submitted Answer:", answer);

        const vote = {
            answer: answer === 'Yes',
            question: {
                id: pollData.question.id,
                question: pollData.question.question
            },
            user: {
                username: pollData.question.poll.user.username
            }
        };
        console.log('Sending vote to backend:', vote);


        axios.post('http://localhost:8080/api/votes', vote)
            .then(response => {
                console.log('Vote submitted successfully!', response.data);
                navigate('/home')

                // You may want to fetch the updated pollData again or redirect the user, etc.
            })
            .catch(error => {
                console.error("Error submitting vote:", error);
            });
    };

    if (!pollData) {
        return <p>Loading...</p>;
    }

    return (
        <div className="bg-gray-100 p-8 rounded-md shadow-lg max-w-lg mx-auto mt-10">
            <h1 className="text-2xl font-bold mb-4">{pollData.name}</h1>
            <h2 className="text-xl mb-6">{pollData.question.question}</h2>
            <form onSubmit={handleSubmit} className="space-y-4">
                <div className="space-x-4">
                    <button
                        type="button"
                        className={`text-white p-4 rounded-md ${answer === 'Yes' ? 'bg-green-600' : 'bg-gray-300 hover:bg-gray-400'}`}
                        onClick={() => setAnswer("Yes")}>
                        Yes
                    </button>
                    <button
                        type="button"
                        className={`text-white p-4 rounded-md ${answer === 'No' ? 'bg-red-600' : 'bg-gray-300 hover:bg-gray-400'}`}
                        onClick={() => setAnswer("No")}>
                        No
                    </button>
                </div>
                <button type="submit" className="bg-blue-500 text-white p-4 w-full rounded-md hover:bg-blue-600">
                    Submit Answer
                </button>
            </form>
        </div>
    );
}

export default PollVote;
