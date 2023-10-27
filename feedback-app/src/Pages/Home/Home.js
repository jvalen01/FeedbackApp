
import Button from '../../Components/Button';
import '../../styles/components.css';
import './Home.css'
import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Firebase from '../../firebaseConfig';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faPoll } from '@fortawesome/free-solid-svg-icons';
import { faGear } from '@fortawesome/free-solid-svg-icons';
import axios from "axios";


const firebaseInstance = new Firebase();

function Home() {
    const navigate = useNavigate();

    const [userEmail, setUserEmail] = useState('');
    const [pollCode, setPollCode] = useState('');
    const [polls, setPolls] = useState([]);
    const [activePolls, setActivePolls] = useState([]);
    const [inactivePolls, setInactivePolls] = useState([]);

    useEffect(() => {
        if (firebaseInstance.auth.currentUser) {
            setUserEmail(firebaseInstance.auth.currentUser.email);
        }
    }, []);

    // Fetch polls specific to the user on component mount
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
                    const activePolls = data.filter(poll => poll.active);
                    const inactivePolls = data.filter(poll => !poll.active); // Filter only inactive polls
                    setActivePolls(activePolls);
                    setInactivePolls(inactivePolls);
                    console.log('Active polls state after setting:', activePolls);
                    console.log('Inactive polls state after setting:', inactivePolls);

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

    const deactivatePoll = async (pollId) => {
        const deactivatedPoll = activePolls.find(poll => poll.id === pollId);
        if (!deactivatedPoll) return; // Return early if the poll isn't found

        // Update local states first
        const updatedActivePolls = activePolls.filter(poll => poll.id !== pollId);
        setActivePolls(updatedActivePolls);
        setInactivePolls(prev => [...prev, deactivatedPoll]);

        const idToken = await firebaseInstance.auth.currentUser.getIdToken(true);
        try {
            const response = await axios.put(`http://localhost:8080/api/polls/${pollId}/deactivate`, {}, {
                headers: {
                    'Authorization': `Bearer ${idToken}`
                }
            });

            if (response.status === 200) {
                console.log('Poll deactivated successfully!');
            } else {
                console.error('Error deactivating poll:', response.data);
                // Revert the state changes if API call fails
                setActivePolls(prev => [...prev, deactivatedPoll]);
                setInactivePolls(prev => prev.filter(poll => poll.id !== pollId));
            }
        } catch (error) {
            console.error('Error deactivating poll:', error);
            // Revert the state changes if API call fails
            setActivePolls(prev => [...prev, deactivatedPoll]);
            setInactivePolls(prev => prev.filter(poll => poll.id !== pollId));
        }
    };

    const activatePoll = async (pollId) => {
        const activatedPoll = inactivePolls.find(poll => poll.id === pollId);
        if (!activatedPoll) return; // Return early if the poll isn't found

        // Update local states first
        const updatedInactivePolls = inactivePolls.filter(poll => poll.id !== pollId);
        setInactivePolls(updatedInactivePolls);
        setActivePolls(prev => [...prev, activatedPoll]);

        const idToken = await firebaseInstance.auth.currentUser.getIdToken(true);
        try {
            const response = await axios.put(`http://localhost:8080/api/polls/${pollId}/activate`, {}, {
                headers: {
                    'Authorization': `Bearer ${idToken}`
                }
            });

            if (response.status === 200) {
                console.log('Poll activated successfully!');
            } else {
                console.error('Error activating poll:', response.data);
                // Revert the state changes if API call fails
                setInactivePolls(prev => [...prev, activatedPoll]);
                setActivePolls(prev => prev.filter(poll => poll.id !== pollId));
            }
        } catch (error) {
            console.error('Error activating poll:', error);
            // Revert the state changes if API call fails
            setInactivePolls(prev => [...prev, activatedPoll]);
            setActivePolls(prev => prev.filter(poll => poll.id !== pollId));
        }
    };

    const deletePoll = async (pollId) => {
        const idToken = await firebaseInstance.auth.currentUser.getIdToken(true);
        try {
            const response = await axios.delete(`http://localhost:8080/api/polls/${pollId}`, {
                headers: {
                    'Authorization': `Bearer ${idToken}`
                }
            });

            if (response.status === 200) {
                console.log('Poll deleted successfully!');
                // Update the state to remove the deleted poll
                setInactivePolls(prev => prev.filter(poll => poll.id !== pollId));
            } else {
                console.error('Error deleting poll:', response.data);
            }
        } catch (error) {
            console.error('Error deleting poll:', error);
        }
    };


    const renderActivePolls = (pollsList) => {
        return pollsList.map((poll, index) => (
            <div key={poll.id} className="mb-4">
                <div className="flex items-center justify-between bg-gray-100 p-4 rounded-md shadow-sm">
                    <div>
                        <h3 className="text-lg font-semibold">{poll.name}</h3>
                    </div>
                    <div className="flex items-center space-x-2">
                        <Link to={`/pollPage/${poll.id}`}>
                            <button className="focus:outline-none focus:ring-2 focus:border-transparent">
                                <FontAwesomeIcon icon={faEye} className="text-gray-400" />
                            </button>
                        </Link>
                        <button
                            onClick={() => deactivatePoll(poll.id)}
                            className="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-300"
                        >
                            End Poll
                        </button>
                    </div>
                </div>
                {index < pollsList.length - 1 && <div className="border-b border-gray-300 my-4"></div>}
            </div>
        ));
    };



    const renderInactivePolls = (pollsList) => {
        return pollsList.map((poll, index) => (
            <div key={poll.id} className="mb-4">
                <div className="flex items-center  justify-between bg-gray-100 p-4 rounded-md shadow-sm">
                    <div>
                        <h3 className="text-lg font-semibold">{poll.name}</h3>
                    </div>
                    <div className="flex items-center space-x-2">
                        <Link to={`/pollPage/${poll.id}`}>
                            <button className="focus:outline-none focus:ring-2 focus:border-transparent">
                                <FontAwesomeIcon icon={faEye} className="text-gray-400" />
                            </button>
                        </Link>
                        <button
                            onClick={() => activatePoll(poll.id)} // Assuming you will define an activatePoll function
                            className="bg-green-500 text-white px-4 py-2 hover:bg-green-600 focus:outline-none focus:ring-2 focus:ring-green-300 rounded-lg"
                        >
                            Start Poll
                        </button>
                        <button
                            onClick={() => deletePoll(poll.id)}
                            className="bg-red-500 text-white px-4 py-2 hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-300 rounded-lg"
                        >
                            Delete Poll
                        </button>
                    </div>
                </div>
                {index < pollsList.length - 1 && <div className="border-b border-gray-300 my-4"></div>}
            </div>
        ));
    };




    return (
        <div className="gradient-background home-background min-h-screen flex flex-col items-center justify-center bg-gray-200 p-4 md:p-8">
            <div className=" homeContainer max-w-5xl w-full bg-white p-6 rounded-xl shadow-lg flex flex-col justify-between elevated-card">

                <div className="text-center mb-10 header-style"> {/* Added text-center class */}
                    <div className="mb-0 text-right">
                        <Link to="/createPoll">
                            <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg">
                                Create New Poll
                            </button>
                        </Link>
                    </div>
                    <h1 className="text-4xl font-bold text-gray-800 dashboard-title">FeedbackApp Dashboard</h1>
                </div>

                <div className="flex w-full mb-6 flex-col md:flex-row justify-between">
                    <div className="pollSection  w-full md:w-1/2 pr-2 mb-4 md:mb-0">
                        <h2 className="text-2xl font-bold mb-4">My Active Polls</h2>
                        {renderActivePolls(activePolls)}
                    </div>

                    <div className="pollSection w-full md:w-1/2 pl-2">
                        <h2 className="text-2xl font-bold mb-4">My Inactive Polls</h2>
                        {renderInactivePolls(inactivePolls)}
                    </div>
                </div>

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
                    <button
                        onClick={fetchPollByCode}
                        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg">
                        Participate in Poll
                    </button>
                </div>

                <div className="userSection w-full flex justify-between items-center">
                    <div>
                        <h3 className="text-xl font-medium mb-2">{userEmail}</h3>
                    </div>
                    <button onClick={handleLogout} className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-lg">
                        Logout
                    </button>
                </div>
            </div>
        </div>
    );
}

export default Home;








