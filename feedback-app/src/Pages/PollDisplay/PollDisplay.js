import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import styles from './PollDisplay.css';

function PollDisplay(props) {
    const { code } = useParams();
    const [pollData, setPollData] = useState(null);

    useEffect(() => {
        axios.get(`http://localhost:8080/api/polls/code/${code}`)
            .then(response => {
                setPollData(response.data);
            })
            .catch(error => {
                console.error("Error fetching poll:", error);
            });
    }, [code]);

    if (!pollData) {
        return <p>Loading...</p>;
    }

    return (
        <div className={styles.pollContainer}>
            <h1 className={styles.pollTitle}>{pollData.name}</h1>
            <h2 className={styles.pollQuestion}>{pollData.question}</h2>

        </div>
    );
}

export default PollDisplay;

