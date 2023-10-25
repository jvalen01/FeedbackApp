import React, { useState, useEffect } from 'react';
import Button from '../../Components/Button';
import { Link, useNavigate } from 'react-router-dom';
import Firebase from '../../firebaseConfig';
import '../../styles/components.css';
import './Home.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPoll } from '@fortawesome/free-solid-svg-icons';

const firebaseInstance = new Firebase();

function Home() {
    const navigate = useNavigate();

    const [userEmail, setUserEmail] = useState('');
    const [pollCode, setPollCode] = useState('');
    const [polls, setPolls] = useState([]); // To store the fetched polls

    useEffect(() => {
        if (firebaseInstance.auth.currentUser) {
            setUserEmail(firebaseInstance.auth.currentUser.email);
        }
    }, []);

    // Fetch polls specific to the user on component mount
    useEffect(() => {
        const fetchPollsByUser = async () => {
            const idToken = await firebaseInstance.auth.currentUser.getIdToken(true);
            try {
                const response = await fetch(`http://localhost:8080/api/polls/user`, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${idToken}`
                    }
                });
                const data = await response.json();
                console.log('Raw API response:', data); // Logs raw data from the API

                if (response.ok) {
                    setPolls(data);
                    console.log('Polls state after setting:', polls); // Logs state after setting it

                } else {
                    console.error('Error fetching polls by user:', data);
                }
            } catch (error) {
                console.error('Error fetching polls by user:', error);
            }
        };

        fetchPollsByUser();
    }, []);





    const fetchPollByCode = async () => {
        const idToken = await firebaseInstance.auth.currentUser.getIdToken(true);
        try {
            const response = await fetch(`http://localhost:8080/api/polls/code/${pollCode}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${idToken}`,
                    'Cache-Control': 'no-cache' // To avoid caching of the API response
                }
            });
            const data = await response.json();
            if (response.ok) {
                navigate(`/poll/${pollCode}`);
            } else {
                alert('Invalid poll code!');
            }
        } catch (error) {
            console.error('Error fetching poll:', error);
        }
    };

    const handleLogout = async () => {
        try {
            await firebaseInstance.signOut();
            alert('Logged out successfully!');
            navigate('/login', { replace: true });
        } catch (error) {
            alert('Error logging out: ' + error.message);
        }
    };

    return (
        <div className="home-background min-h-screen flex flex-col items-center justify-center bg-gray-200">
            <div className="homeContainer md:w-2/3 lg:w-1/2 xl:w-1/3 bg-white p-4 md:p-6 lg:p-8 rounded-xl shadow-lg flex flex-col justify-between items-center">
                <h1 className="mb-10 text-4xl font-bold text-gray-800">FeedbackApp Dashboard</h1>

                <div className="pollSection w-full mb-6">
                    <h2 className="text-2xl font-bold mb-4">My Polls</h2>
                    {polls.length === 0 ? (
                        <p className="text-xl text-gray-600">No active polls available.</p>
                    ) : (
                        polls.map(poll => (
                            <div key={poll.id}>
                                <h3>{poll.name}</h3>
                                <Link to="/pollPage">
                                    <button>Manage Poll</button>
                                </Link>
                                {/* Add more details about each poll as needed */}
                            </div>
                        ))
                    )}
                </div>
                <Link to="/createPoll">
                    <Button text="Create New Poll" />
                </Link>

                <div className="activitySection w-full mb-6 p-4 bg-gray-100 rounded-md shadow-md">
                    <h2 className="text-3xl font-bold mb-6 text-gray-700">Participate in a poll</h2>
                    <div className="relative mb-4">
                        <input
                            type="text"
                            value={pollCode}
                            onChange={e => setPollCode(e.target.value)}
                            placeholder="Enter poll code"
                            className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-300 focus:border-transparent"
                        />
                        <div className="absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none">
                            <FontAwesomeIcon icon={faPoll} className="text-gray-400" />
                        </div>
                    </div>
                    <Button
                        text="Participate in Poll"
                        onClick={fetchPollByCode}
                        className="bg-blue-500 text-white px-6 py-2 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-300 focus:border-transparent"
                    />
                </div>

                <div className="userSection w-full flex justify-between items-center">
                    <div>
                        <h3 className="text-xl font-medium mb-2">{userEmail}</h3>
                    </div>
                    <Button text="Logout" onClick={handleLogout} />
                </div>
            </div>
        </div>
    );
}

export default Home;

