import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './Pages/Login/Login';
import Register from './Pages/Register/Register';
import Welcome from './Pages/Welcome/Welcome';
import Home from './Pages/Home/Home';
import PrivateRoute from "./Components/PrivateRoute";
import CreatePoll from "./Pages/CreatePoll/CreatePoll";
import PollDisplay from "./Pages/PollDisplay/PollDisplay";
import PollData from "./Pages/PollData/PollData";



function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Welcome />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/home" element={<PrivateRoute><Home /></PrivateRoute>} />
                <Route path="/createPoll" element={<PrivateRoute><CreatePoll /></PrivateRoute>} />
                <Route path="/poll/:code" element={<PrivateRoute><PollDisplay /></PrivateRoute>} />
                <Route path="/pollPage/:pollID" element={<PrivateRoute><PollData /></PrivateRoute>} />
            </Routes>
        </Router>
    );
}

export default App;