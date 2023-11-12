import React, { useState, useEffect } from 'react';
import {Link, useNavigate, useParams} from "react-router-dom";
import {CopyToClipboard} from 'react-copy-to-clipboard';
import HomeButton from "../../Components/HomeButton";
import Button from "../../Components/Button";
import "../../styles/components.css"
import WebSocket from '../../Components/WebSocket';

function PollData() {
    const [pollData, setPollData] = useState(null);
    const { pollID } = useParams();
    const webSocketData = WebSocket();
    const navigate = useNavigate();
    const [wsMessage, setWsMessage] = useState("");
    const [shareableLink, setShareableLink] = useState(null);
    useEffect(() => {
        console.log("WebSocket data in component:", webSocketData);

    // State variable to hold the link

        if (webSocketData) {
            if (webSocketData === "Hello World") {
                setWsMessage(webSocketData);
            } else {
                const updatedPollData = JSON.parse(webSocketData);
                setPollData(updatedPollData);
            }
        }
    }, [webSocketData]);

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

                    // Check if poll is public and generate shareable link
                    if (data.accessMode === "public") {
                        const link = `${window.location.origin}/poll/${data.code}`;
                        setShareableLink(link);
                    }

                } else {
                    console.error('Failed to fetch poll data');
                }
            } catch (error) {
                console.error('Error fetching poll data:', error);
            }
        };

        fetchPollData();
    }, [pollID]);

    const calculateHeight = (votes) => {
        if (votes === 0) return '5px';
        const baseHeight = 50; // Starting height for 1 vote
        return `${baseHeight * Math.log(votes + 1)}px`; // Adding 1 to avoid log(1) = 0
    };


    if (!pollData) {
        return <div className="text-center text-xl mt-8">Loading...</div>;
    }
    const beigeBoxStyle = {
        height: 'auto', // This will make the box's height adjust to the content
        maxHeight: 'none', // Override if there's any maxHeight set in your CSS
    };

    const yesVotesHeight = calculateHeight(pollData.question.yesVotes);
    const noVotesHeight = calculateHeight(pollData.question.noVotes);

    return (
        <div className="welcome-background min-h-[80vh] flex items-center justify-center bg-gray-200">
            <div className="beigeBox" style={beigeBoxStyle}>
                <div className="absolute top-0 left-0 p-8">
                    <HomeButton />
                </div>

                {wsMessage && <div className="mb-4 text-green-600 w-full text-center">{wsMessage}</div>}

                <div className="w-full text-center mb-6">
                    <h2 className="text-xl font-bold">Poll name: {pollData.name}</h2>
                </div>

                <div className="border-b border-gray-300 w-full mb-6"></div>

                <div className="text-xl font-semibold mb-6">Question: {pollData.question.question}</div>
                <div className="text-xl font-semibold mb-6">Join Code: {pollData.code}</div>
                <div className="border-b border-gray-300 w-full mb-6"></div>

                {shareableLink && (
                    <div className="flex items-center mb-6">
                        <div className="text-xl font-semibold mr-2">Shareable Link:</div>
                        <span className="text-xl mr-2">{shareableLink}</span>
                        <CopyToClipboard text={shareableLink}>
                            <Button text="Copy" />
                        </CopyToClipboard>
                    </div>
                )}
                <div className="border-b border-gray-300 w-full mb-6"></div>

                <div className="flex justify-center items-end w-full mb-10">
                    <div className="flex flex-col items-center mr-8">
                        <div style={{ height: yesVotesHeight }} className="bg-green-500 w-16 rounded-lg shadow-sm"></div>
                        <div className="text-xl font-semibold mt-2">Yes votes: {pollData.question.yesVotes}</div>
                    </div>

                    <div className="flex flex-col items-center">
                        <div style={{ height: noVotesHeight }} className="bg-red-500 w-16 rounded-lg shadow-sm"></div>
                        <div className="text-xl font-semibold mt-2">No votes: {pollData.question.noVotes}</div>
                    </div>
                </div>

                <div className="border-b border-gray-300 w-full mb-6"></div>

                <div className="text-xl font-semibold mb-10">Total votes: {pollData.question.totalVotes}</div>
                <div className="text-xl font-semibold">Created By: {pollData.question.poll.user.username}</div>
            </div>
        </div>
    );
}

export default PollData;

