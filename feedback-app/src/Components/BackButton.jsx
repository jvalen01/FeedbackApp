import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { BiArrowBack } from 'react-icons/bi';
import '../styles/components.css';

const BackButton = () => {
  
  return (
   <Link to="/">
    <button className="back-button">
      <BiArrowBack size={30} />
    </button>
    </Link>
  );
};

export default BackButton;
