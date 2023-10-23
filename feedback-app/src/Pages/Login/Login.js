import React, { useState } from 'react';
import Firebase from '../../firebaseConfig';
import "../../styles/components.css";
import Button from '../../Components/Button';

const firebaseInstance = new Firebase();

const Register = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await firebaseInstance.signUp(email, password);
            alert("Registered successfully!");
            // You can also redirect to another page, if required.
        } catch (error) {
            console.error("Error registering:", error.message);
            alert("Error registering: " + error.message);
        }
    }

    return (
        <div className="welcome-background min-h-[80vh] flex items-center justify-center bg-gray-200">
            <div className="beigeBox md:w-1/2 lg:w-1/3 xl:w-1/4 bg-white p-4 md:p-6 lg:p-8 rounded-xl shadow-lg flex flex-col justify-center items-center">
                <h2 className="text-xl md:text-2xl lg:text-3xl font-bold mb-4">Register</h2>
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label className="block text-sm md:text-base lg:text-lg font-medium text-gray-600 mb-2">Email</label>
                        <input
                            type="email"
                            placeholder="Email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            className="w-full p-2 md:p-2.5 lg:p-3 border rounded-md text-sm md:text-base lg:text-lg"
                        />
                    </div>
                    <div className="mb-6">
                        <label className="block text-sm md:text-base lg:text-lg font-medium text-gray-600 mb-2">Password</label>
                        <input
                            type="password"
                            placeholder="Password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            className="w-full p-2 md:p-2.5 lg:p-3 border rounded-md text-sm md:text-base lg:text-lg"
                        />
                    </div>
                    <div className="flex items-center justify-center">
                        <Button text="Register" onClick={handleSubmit} />
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Register;