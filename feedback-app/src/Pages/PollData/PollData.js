import React, { useState, useEffect } from 'react';
import {Link, useNavigate, useParams} from "react-router-dom";
import {CopyToClipboard} from 'react-copy-to-clipboard';


function PollData() {
    const [pollData, setPollData] = useState(null);
    const { pollID } = useParams();

    const navigate = useNavigate();

    // State variable to hold the link
    const [shareableLink, setShareableLink] = useState(null);

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


    const yesVotesHeight = calculateHeight(pollData.question.yesVotes);
    const noVotesHeight = calculateHeight(pollData.question.noVotes);

    // Render poll data with Tailwind styling
    return (
        <div className="w-4/5 max-w-6xl min-h-screen mx-auto p-8 bg-gray-100 shadow-md rounded-lg">
            <div className="flex items-center mb-6">
                <h2 className="text-xl font-bold mr-2">Poll name:</h2>
                <span className="text-xl">{pollData.name}</span>
            </div>

            <div className="border-b border-gray-300 mb-6"></div>

            <div className="flex items-center mb-6">
                <h2 className="text-xl font-semibold mr-2">Question:</h2>
                <span className="text-xl">{pollData.question.question}</span>
            </div>

            <div className="flex items-center mb-6">
                <h2 className="text-xl font-semibold mr-2">Join Code:</h2>
                <span className="text-xl">{pollData.code}</span>
            </div>

            {shareableLink && (
                <div className="flex items-center mb-6">
                    <h2 className="text-xl font-semibold mr-2">Shareable Link:</h2>
                    <span className="text-xl mr-2">{shareableLink}</span>
                    <CopyToClipboard text={shareableLink}>
                        <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                            Copy Link
                        </button>
                    </CopyToClipboard>
                </div>
            )}

            <div className="flex items-end mb-10">
                <div>
                    <div style={{ height: yesVotesHeight }} className="bg-green-500 w-16 rounded-lg shadow-sm"></div>
                    <div className="flex items-center mt-2">
                        <h4 className="text-xl font-semibold mr-2">Yes votes:</h4>
                        <span className="text-xl">{pollData.question.yesVotes}</span>
                    </div>
                </div>

                <div className="ml-8">
                    <div style={{ height: noVotesHeight }} className="bg-red-500 w-16 rounded-lg shadow-sm"></div>
                    <div className="flex items-center mt-2">
                        <h4 className="text-xl font-semibold mr-2">No votes:</h4>
                        <span className="text-xl">{pollData.question.noVotes}</span>
                    </div>
                </div>
            </div>

            <div className="border-b border-gray-300 mb-6"></div>

            <div className="flex items-center mb-10">
                <h2 className="text-xl font-semibold mr-2">Total votes:</h2>
                <span className="text-xl">{pollData.question.totalVotes}</span>
            </div>

            <div className="border-b border-gray-300 mb-6"></div>

            <div className="flex items-center mb-4">
                <h2 className="text-xl font-semibold mr-2">Created By:</h2>
                <span className="text-xl">{pollData.user.username}</span>
            </div>

            <div>
                <Link to="/home">
                    <button className="home-button" type="button">Home</button>
                </Link>
            </div>
        </div>
    );
}

export default PollData;
