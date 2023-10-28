import React from 'react';
import '../styles/tailwind.css';

const LogOutButton = ({ text, onClick, type = "button", delay = 0 }) => {
    const handleClick = (e) => {
        if (type === "submit") {
            e.preventDefault(); // Prevent form submission until delay is finished
        }

        // Wait for the animation to finish before executing the main function
        setTimeout(() => {
            if (onClick) {
                onClick(e);

                // If it's a submit type, submit after the delay
                if (type === "submit" && e.target.form) {
                    e.target.form.submit();
                }
            }
        }, delay);
    };

    return (
        <button
            type={type}
            onClick={handleClick}
            className='button w-40 h-16 bg-red-500 cursor-pointer select-none
      active:translate-y-2 active:[box-shadow:0_0px_0_0_#e53e3e,0_0px_0_0_#e53e3e80]
      transition-all duration-150 [box-shadow:0_10px_0_0_#e53e3e,0_15px_0_0_#e53e3e80]
      rounded-full border-[1px] border-red-400'>
            <span className='flex flex-col justify-center items-center h-full text-white font-bold text-lg'>{text}</span>
        </button>
    );
}

export default LogOutButton;