import React from 'react';
import '../styles/tailwind.css';

const Button = ({ text, onClick, type = "button", delay = 0 }) => {
    const handleClick = (e) => {
        // If it's a submit type, we want to let the form handle it without interference.
        if (type !== "submit") {
            e.preventDefault();

            // Wait for the animation to finish before executing the main function
            setTimeout(() => {
                if (onClick) {
                    onClick(e);
                }
            }, delay);
        }
    };

    return (
        <button
            type={type}
            onClick={handleClick}
            className='button w-40 h-16 bg-blue-500 cursor-pointer select-none
      active:translate-y-2 active:[box-shadow:0_0px_0_0_#1b6ff8,0_0px_0_0_#1b70f841]
      active:border-b-[0px]
      transition-all duration-150 [box-shadow:0_10px_0_0_#1b6ff8,0_15px_0_0_#1b70f841]
      rounded-full border-[1px] border-blue-400'>
            <span className='flex flex-col justify-center items-center h-full text-white font-bold text-lg'>{text}</span>
        </button>
    );
}

export default Button;