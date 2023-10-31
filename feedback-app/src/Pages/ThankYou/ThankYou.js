import React from 'react';
import {Link} from "react-router-dom";

const ThankYou = () => {
    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 p-4 space-y-6">
            <h1 className="text-2xl font-bold text-gray-700">Thank You for Your Vote!</h1>
            <p className="text-gray-600">Your vote has been recorded. We appreciate your participation.</p>
            <div>
                <Link to="/home">
                    <button className="px-6 py-2 text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:bg-green-800 rounded">
                        Home
                    </button>
                </Link>
            </div>
        </div>
    );
}

export default ThankYou;
