import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

function PollDisplay(props) {
    const { code } = useParams();
    const [pollData, setPollData] = useState(null);
    const [answer, setAnswer] = useState(''); // state to hold the answer input value

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
        // You can handle the answer submission logic here
    };

    if (!pollData) {
        return <p>Loading...</p>;
    }

    return (
        <div className="bg-gray-100 p-8 rounded-md shadow-lg max-w-lg mx-auto mt-10">
            <h1 className="text-2xl font-bold mb-4">{pollData.name}</h1>
            <h2 className="text-xl mb-6">{pollData.question}</h2>
            <form onSubmit={handleSubmit} className="space-y-4">
                <input
                    type="text"
                    placeholder="Your answer..."
                    className="w-full p-4 rounded-md border focus:outline-none focus:ring focus:border-blue-300"
                    value={answer}
                    onChange={(e) => setAnswer(e.target.value)}
                />
                <button type="submit" className="bg-blue-500 text-white p-4 w-full rounded-md hover:bg-blue-600">
                    Submit Answer
                </button>
            </form>
        </div>
    );
}

export default PollDisplay;




