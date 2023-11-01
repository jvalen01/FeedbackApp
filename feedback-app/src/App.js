import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './Pages/Login/Login';
import Register from './Pages/Register/Register';
import Welcome from './Pages/Welcome/Welcome';
import Home from './Pages/Home/Home';
import PrivateRoute from "./Components/PrivateRoute";
import CreatePoll from "./Pages/CreatePoll/CreatePoll";
import PollVoteDisplay from "./Pages/PollVoteDisplay/PollVoteDisplay";
import PollData from "./Pages/PollData/PollData";
import ThankYou from "./Pages/ThankYou/ThankYou";



function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Welcome />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/home" element={<PrivateRoute><Home /></PrivateRoute>} />
                <Route path="/createPoll" element={<CreatePoll />} />
                <Route path="/poll/:code" element={<PollVoteDisplay />} />
                <Route path="/pollPage/:pollID" element={<PollData />} />
                <Route path="/ThankYou" element={<ThankYou />} />
            </Routes>
        </Router>
    );
}

export default App;