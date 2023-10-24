import React, { useState, useEffect }  from 'react';
import Button from '../../Components/Button';
import { Link, useNavigate } from 'react-router-dom'; // Import Link and useHistory
import Firebase from '../../firebaseConfig';
import '../../styles/components.css';
import './Home.css'



const firebaseInstance = new Firebase();

function Home() {
    const navigate = useNavigate();

    // State to store user's email
    const [userEmail, setUserEmail] = useState('');
    const [pollCode, setPollCode] = useState('');


    // Fetch user email on component mount
    useEffect(() => {
        if (firebaseInstance.auth.currentUser) {
            setUserEmail(firebaseInstance.auth.currentUser.email);
        }
    }, []); // The empty dependency array ensures this runs only once when the component mounts


    const fetchPollByCode = async () => {
        const idToken = await firebaseInstance.auth.currentUser.getIdToken(true);
        try {
            const response = await fetch(`http://localhost:8080/api/polls/code/${pollCode}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${idToken}`
                }
            });
            const data = await response.json();
            if (response.ok) {
                // Redirect to poll participation page, maybe using `useNavigate`
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
                    <h2 className="text-2xl font-bold mb-4">Active Polls</h2>
                    {/* Here you can map through your polls and display them */}
                    <p className="text-xl text-gray-600">No active polls available.</p>
                    <Link to="/createPoll">
                        <Button text="Create New Poll" />
                    </Link>
                </div>

                <div className="activitySection w-full mb-6">
                    <h2 className="text-2xl font-bold mb-4">Participate in a poll</h2>
                    <input
                        type="text"
                        value={pollCode}
                        onChange={e => setPollCode(e.target.value)}
                        placeholder="Enter poll code"
                    />
                    <Button text="Participate in Poll" onClick={fetchPollByCode} />
                </div>

                <div className="userSection w-full flex justify-between items-center">
                    <div>
                        <h3 className="text-xl font-medium mb-2">{userEmail}</h3> {/* Display user's email */}
                        <Link to="/profile">
                            <Button text="Profile & Settings" />
                        </Link>
                    </div>
                    <Button text="Logout" onClick={handleLogout} />
                </div>
            </div>
        </div>
    );
}

export default Home;
