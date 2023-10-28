import React from 'react';
import Button from '../../Components/Button';
import { Link } from 'react-router-dom'; // Import Link
import './Welcome.css';
import '../../styles/components.css';

function Welcome() {
    return (
        <div className="welcome-background min-h-screen flex flex-col items-center justify-center">
            <div className="beigeBox">
                <h1 className="mb-10 text-4xl font-bold text-gray-800">Welcome to FeedbackApp</h1>
                <p className="mb-4 text-xl text-gray-600 text-center mx-auto w-3/4">
  Your comprehensive platform for creating polls, casting votes, and diving into detailed statistics. Engage with peers, gather opinions, and share insights like never before.
</p>
                <div className="buttonContainer space-x-4">
                    <Link to="/login">
                        <Button text="Login" type="submit"/>
                    </Link>
                    <Link to="/register">
                        <Button text="Register" type="submit"/>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default Welcome;
